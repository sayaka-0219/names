package com.sayaka.MyBatis.demo;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NameService {
    private final NameMapper nameMapper;

    public NameService(NameMapper nameMapper) {
        this.nameMapper = nameMapper;
    }

    public List<Name> findByNames(String startsWith, String endsWith) {
        List<Name> names = nameMapper.findByNameStartingWith(startsWith, endsWith);
        return names;
    }

    public Name findUser(int id) {
        Optional<Name> user = this.nameMapper.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotExistException("user not found");
        }
    }
}
