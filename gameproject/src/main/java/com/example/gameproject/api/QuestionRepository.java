package com.example.gameproject.api;

import com.example.gameproject.bean.model.Mail;
import com.example.gameproject.bean.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Question findByQuestionId(long id);

}
