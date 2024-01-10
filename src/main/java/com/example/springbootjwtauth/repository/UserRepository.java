package com.example.springbootjwtauth.repository;

import com.example.springbootjwtauth.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
    User findUserByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    User findUserByVerificationCode(String code);
}
