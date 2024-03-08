package com.sayaka.MyBatis.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
}
