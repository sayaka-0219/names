package com.sayaka.MyBatis.demo;

import org.springframework.http.ResponseEntity;
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

    @GetMapping("/names")
    public List<Name> getNames(NameSearchRequest request){
        return nameService.findByNames(request.getStartsWith(), request.getEndsWith());
    }

    @GetMapping("/users/{id}")
    public Name getUser(@PathVariable("id") int id) {
        return nameService.findUser(id);
    }

    @PostMapping("/users")
    public ResponseEntity<NameResponse> insert(@RequestBody NameRequest userRequest, UriComponentsBuilder uriBuilder) {
        Name user = nameService.insert(userRequest.getName());
        URI location = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
        NameResponse body = new NameResponse("user created");
        return ResponseEntity.created(location).body(body);
    }
}
