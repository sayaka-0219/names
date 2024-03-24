package com.sayaka.MyBatis.demo.controller;

import com.sayaka.MyBatis.demo.controller.request.NameRequest;
import com.sayaka.MyBatis.demo.controller.request.NameSearchRequest;
import com.sayaka.MyBatis.demo.controller.response.NameResponse;
import com.sayaka.MyBatis.demo.entity.Name;
import com.sayaka.MyBatis.demo.service.NameService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
@RestController
public class NameController {
    private final NameService nameService;

    public NameController(NameService nameService) {
        this.nameService = nameService;
    }

    //文字を指定したRead処理
    @GetMapping("/names")
    public List<Name> getNames(NameSearchRequest request){
        return nameService.findByNames(request.getStartsWith(), request.getEndsWith());
    }

    //ID検索でのRead処理
    @GetMapping("/users/{id}")
    public Name getUser(@PathVariable("id") int id) {
        return nameService.findUser(id);
    }

    //Create処理
    @PostMapping("/users")
    public ResponseEntity<NameResponse> insert(@RequestBody @Validated NameRequest userRequest, UriComponentsBuilder uriBuilder) {
        Name user = nameService.insert(userRequest.getName());
        URI location = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
        NameResponse body = new NameResponse("user created");
        return ResponseEntity.created(location).body(body);
    }
}
