package com.example.gameproject.api;

import com.example.gameproject.bean.model.Answer;
import com.example.gameproject.bean.model.Mail;
import com.example.gameproject.bean.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {


}
