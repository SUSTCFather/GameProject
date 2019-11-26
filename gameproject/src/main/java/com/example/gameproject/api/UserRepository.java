package com.example.gameproject.api;

import com.example.gameproject.bean.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    List<User> findByUserNameOrMailAddress(String userName, String mailAddress);

    List<User> findByMailAddress(String mailAddress);

    User findByUserName(String userName);

    User findByUserId(Long userId);
}
