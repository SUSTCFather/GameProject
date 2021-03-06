package com.example.gameproject.mapper;

import com.example.gameproject.bean.model.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    List<User> findByUserNameOrMailAddress(String userName, String mailAddress);

    List<User> findByMailAddress(String mailAddress);

    User findByUserId(Long userId);

    List<User> findAllBy(Pageable pageable);
}
