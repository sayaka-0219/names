# 登録の処理

## テーブル定義

| カラム名  | データ型         | NOT NULL | PRIMARY KEY | UNIQUE |
|-------|--------------|----------|-------------|--------|
| id    | INT          | 〇        | 〇           | 〇      |
| name  | VARCHAR(255) | 〇        |             |        |
| email | VARCHAR(255) | 〇        |             | 〇      |
idカラムはAUTO_INCREMENTを設定し、登録時に自動で採番されるようにする。

・VARCHAR(255) --- 255文字以内の可変長の文字列。255文字を超えると登録できない。  
・NOT NULL --- NULLが許容されない  
・PRIMARY KEY --- IDのようなもの。二人の人が同じIDを持たないようにする感じ。  
・UNIQUE --- 唯一。重複しない。他と同じものを格納できない。NULLは格納できる。

## APIの仕様定義

| メソッド | パス     | 機能      |
|------|--------|---------|
| POST | /users | ユーザーの登録 |
登録機能はユーザー名とメールアドレスを登録する。  
読み取り処理と違い、リクエストボディを送信する必要がある。

次のようなJSONでリクエストを送信する。  
当然だが、IDは登録時に生成されるものなのでリクエストボディには含まれない。  
↑オートインクリメントがあるから
```json
{
 "name": "⼭⽥太郎",
 "email": "yamada@example.com"
 }
```

登録処理をリクエストするcurlコマンドは次のようになる。  
`curl -X POST -H "Content-Type: application/json" -d '{"name": "⼭⽥太郎", "email": "yamada@example.com"}' http://localhost:8080/users`

`-X POST` --- コマンドのオプション  
`-H "Content-Type: application/json"` --- ヘッダー。どんな形式でデータを送るかを指定するときに使う。  
`-d '{"name": "⼭⽥太郎", "email": "yamada@example.com"}'` --- ボディー。実際にどんなデータを送るか。

レスポンスについてですが、ステータスコードは200ではなく201を返す。  
201はリソースの作成に成功したことを示すステータスコード。  
レスポンスボディは登録が成功したことを示すメッセージを返す。
```json
{
  "message": "user created"
}
```

APIをリクエストした側が登録したユーザーの情報にアクセスしたい場合がある。  
そのために、登録したユーザーのURLをレスポンスヘッダーに含めて返す。  
レスポンスヘッダーのLocationに登録したユーザーのURLを設定。  
`Location: http://localhost:8080/users/1`

# 登録処理の実装
### エンティティクラスを作成。  
```java
public class User {
 private Integer id;
 private String name;
 private String email;
 }
 // getter/constructorは省略  
```

### Mapperを作成
```java
@Mapper
 public interface UserMapper {
 @Insert("INSERT INTO users (name, email) VALUES (#{name}, #{email})")//#{name},#{email}はどこからとってくるかというと
 @Options(useGeneratedKeys = true, keyProperty = "id")
 void insert(User user);//ここでエンティティクラスを指定するとMyBatisがうまいこと埋め込んでくれる
 }
```
@Insert("INSERT INTO users (name, email) VALUES (#{name}, #{email})")は登録のためのSQLを記述
している。  
@Options(useGeneratedKeys = true, keyProperty = "id")は少し理解が難しいが、自動生成された
IDをUserオブジェクトに設定するための設定。  
このinsertメソッドを呼び出すと、Userオブジェクトのidに自動採番されたIDが設定される。  
  
### Serviceを作成
```java
@Service
 public class UserService {
    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User insert(String name, String email) {
        User user = new User(null, name, email);//ユーザークラスでuserインスタンスを作っている。引数は登録するためのデータ。
        userMapper.insert(user);                //引数になぜNULLが入っているかというとこの時点ではIDはわからないから
        return user;
    }
}
```
  
Serviceは普通の言葉のように読めた方がいいのでnullが出てこないようにする。  
  
エンティティとなるクラスに↓を作成  
```java
public static Name creatName(String name) {//staticメソッドは引数で受け取ったnameからNameクラスを作ってリターンする
        return new Name(null,name);
    }
```
Serviceの記述はこれ↓  
```java
public Name insert(String name) {
        Name user = Name.creatName(name);//staticのメソッドをnewをしなくても呼び出せる
        nameMapper.insert(user);
        return user;
    }
```
### Controller
Controllerを作成する前に、リクエストボディを受け取るためのクラスを作成。  
```json
{
  "name": "⼭⽥太郎",
  "email": "yamada@example.com"
}
```
フィールドはjsonのキーにあわせてnameとemailにする。  
```java
public class UserRequest {
 private String name;
 private String email;
 }
 // getter/constructorは省略
```
  
Controllerを作成。  
Spring BootではJacksonというライブラリを使って、jsonをJavaオブジェクトに変換してくれる。  
@RequestBodyアノテーションを付与することで、リクエストボディのjsonをUserRequestオブジェクトに変換してくれる。  
```java
@RestController
 public class UserController {
 private final UserService userService;
 public UserController(UserService userService) {
 this.userService = userService;
    }
 }
 @PostMapping("/users")
 public User insert(@RequestBody UserRequest userRequest) {
 User user = userService.insert(userRequest.getName(), userRequest.getEmail());
 return user;
    }
```
  
動作確認。  
```
curl -X POST -H "Content-Type: application/json" -d '{"name": "⼭⽥太郎 ", "email": "yamada@example.com"}' http://localhost:8080/users
```
レスポンスは次のようになる。  
```json
{
  "id": 1,
  "name": "⼭⽥太郎",
  "email": "yamada@example.com"
}
```
一見正しく動作しているように見えるし、ユーザーの登録自体はできるはず。  
しかし、ステータスコードは201を返す仕様になっているが、200が返ってきている。  
Locationヘッダも返ってきていないし、レスポンスボディも仕様とは異なる。  

まず、レスポンスボディを仕様に合わせて修正。  
```json
{
  "message": "user created"
}
```
このjsonに合わせたUserResponseクラスを作成。  
```java
public class UserResponse {
    private String message;

    public class UserResponse {
        private String message;
    }
}
//コンストラクタとゲッターは省略
```
  
Controllerのinsertメソッドを次のように修正。  
```java
@PostMapping("/users")
 public ResponseEntity<UserResponse> insert(@RequestBody UserRequest userRequest, UriComponentsBuilder uriBuilder) {
 User user = userService.insert(userRequest.getName(), userRequest.getEmail());
 URI location = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
 UserResponse body = new UserResponse("user created");
 return ResponseEntity.created(location).body(body);
 }
```
↑の解説  
```
public ResponseEntity<User> insert(@RequestBody UserRequest userRequest, UriComponentsBuilder uriBuilder) {
```
ResponseEntityはSpring Bootが提供しているクラスで、レスポンスのステータスコードやヘッダ、ボデ ィを設定することができる。  
これを使うことで、Controllerのメソッドの戻り値をレスポンスに変換することができる。

UriComponentsBuilderはSpring Bootが提供しているクラスで、URIを構築することができる。  
今回はLocationヘッダを設定するために使用する。  
次のようにURIを構築することができる。  
生成されるURIはたとえば  
http://localhost:8080/users/1 のようになる。  
```java
URI location = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
//URI テンプレート変数の置換: buildAndExpand メソッドを使用して、URI テンプレート変数を配列やマップの値で置き換えることができます。  
//たとえば、buildAndExpand("user_id", 123) とすると、URI 中の {user_id} が 123 に置き換わります。  
//.toUri() メソッドは、UriComponents インスタンスを構築するために使用される。
```

最後に、レスポンスボディを設定する。  
ResponseEntityのcreatedメソッドを使うことで、ステータスコード201を設定することができる。  
引数には先ほど作成したURIをわたし、次にbodyメソッドを呼び出してレスポンスボディを設定する。  
```
UserResponse body = new UserResponse("user created");
 return ResponseEntity.created(location).body(body);
```
  
動作確認。  
メールアドレスは重複を許さないので事前に山田太郎のレコードを削除しておこう。  
`curl -X POST -H "Content-Type: application/json" -d '{"name": "⼭⽥太郎", "email": "yamada@example.com"}' http://localhost:8080/users`
レスポンスは次のようになる。  
```json
{
  "message": "user created"
}
```
-i オプションをつけてヘッダを確認してみる。  
ステータスコードは201になり、Locationヘッダも返ることを確認する。  
curl -X PATCH -H "Content-Type: application/json" -d '{"name":"太郎"}' http://localhost:8080/users/5