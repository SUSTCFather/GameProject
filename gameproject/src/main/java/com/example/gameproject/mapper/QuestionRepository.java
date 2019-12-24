package com.example.gameproject.mapper;

import com.example.gameproject.bean.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Question findByQuestionId(long id);

    @Query(value = "SELECT * FROM question WHERE question_id >= ((SELECT MAX(question_id) " +
            "FROM question)-(SELECT MIN(question_id) FROM question)) * RAND() + " +
            "(SELECT MIN(question_id) FROM question) order by question_id limit 1",nativeQuery = true)
    Question findRand();



}
