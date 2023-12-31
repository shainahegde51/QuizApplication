package com.shaina.QuizApp.Repository;

import com.shaina.QuizApp.Model.Question;
import com.shaina.QuizApp.Model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz,Integer> {

}
