package com.example.gameproject.mapper;


import com.example.gameproject.bean.model.Mail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MailRepository extends JpaRepository<Mail, Long> {

    List<Mail> findByMailAddress(String mailAddress);

}
