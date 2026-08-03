package com.duong.backendservice.repository;

import com.duong.backendservice.entity.Token;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository extends CrudRepository<Token, String> {
    List<Token> findByUserId(String userId);
}
