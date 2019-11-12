package com.example.gameproject.api;


import com.example.gameproject.bean.Mail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MailRepository extends JpaRepository<Mail, Long> {

    List<Mail> findByMailAddress(String mailAddress);

}
