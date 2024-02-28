# Read処理の実装

## Databases
namesテーブルに5つの名前のレコードが入っており、それぞれ順にIDが振り分けられています。  
![img.png](img.png)  

## 今回使用するURL
`http://localhost:8080/names` ・・・ Databases内すべてのデータを取得するURL  
`http://localhost:8080/names?startsWith=s` ・・・ 頭文字に『 s 』がつくモノだけ取得するURL  
`http://localhost:8080/names?endsWith=i` ・・・ 最後に『　i　』がつくモノだけ取得するURL  
`http://localhost:8080/names?startsWith=&endsWith=` ・・・何も指定しなかった場合のURL  
`http://localhost:8080/names?startsWith=s&endsWith=u`　・・・頭文字が『 s 』、最後の文字が『 u 』につくモノだけ取得するURL  

## 結果
・`http://localhost:8080/names`  
すべてのデータが返ってきており、ステータスコードも200で返ってきています。  
![img_2.png](img_2.png)  
  
・`http://localhost:8080/names?startsWith=s`  
頭文字に『 s 』がつく、suzuki、sasaki、saitouが返ってきており、ステータスコードも200で返ってきています。  
![img_3.png](img_3.png)
  
・`http://localhost:8080/names?endsWith=i`  
最後に『 i 』がつくsuzuki,sasakiが返ってきており、ステータスコードも200で返ってきています。  
![img_4.png](img_4.png)  
  
・`http://localhost:8080/names?startsWith=&endsWith=`  
頭文字、最後の文字どちらも指定せずに検索するとすべてのデータが返ってきており、ステータスコードも200で返ってきています。
![img_5.png](img_5.png)  
  
・`http://localhost:8080/names?startsWith=s&endsWith=u`  
頭文字が『 s 』、最後の文字が『 u 』につく、saitouが返ってきており、ステータスコードも200で返ってきています。
![img_6.png](img_6.png)