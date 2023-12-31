package com.shaina.QuizApp.Service;

import com.shaina.QuizApp.Model.Question;
import com.shaina.QuizApp.Model.QuestionWrapper;
import com.shaina.QuizApp.Model.Quiz;
import com.shaina.QuizApp.Model.Response;
import com.shaina.QuizApp.Repository.QuestionRepository;
import com.shaina.QuizApp.Repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuestionRepository questionRepository;
    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        List<Question> questions= questionRepository.findRandomQuestionsByCategory(category,numQ);
        Quiz quiz=new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizRepository.save(quiz);
        return  new ResponseEntity<>("success", HttpStatus.CREATED);
    }


    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(int id) {
       Optional<Quiz> quiz= quizRepository.findById(id);
       List<Question>questionsFromDB=quiz.get().getQuestions();

       List<QuestionWrapper>questionsForUser=new ArrayList<>();

       for (Question q:questionsFromDB){
           QuestionWrapper qw=new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(), q.getOption4());
           questionsForUser.add(qw);
       }
       return new ResponseEntity<>(questionsForUser,HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Quiz quiz=quizRepository.findById(id).get();
        List<Question>questions=quiz.getQuestions();
        int right=0;
        int i=0;
        for(Response response:responses){
            if(response.getResponse().equals(questions.get(i).getRightAnswer())){
                right++;
            }
            i++;
        }
        return new ResponseEntity<>(right,HttpStatus.OK);
    }
}
