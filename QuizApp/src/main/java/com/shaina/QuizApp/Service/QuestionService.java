package com.shaina.QuizApp.Service;

import com.shaina.QuizApp.Model.Question;
import com.shaina.QuizApp.Repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    QuestionRepository questionRepository;
    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionRepository.findAll(), HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try {
            return new ResponseEntity<>(questionRepository.findByCategory(category),HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        questionRepository.save(question);
        return new ResponseEntity<>("success",HttpStatus.CREATED);
    }

    public ResponseEntity<String> deleteQuestion(int id) {
        try {
            questionRepository.deleteById(id);
            return new ResponseEntity<>("deleted question " + id + "successfully!", HttpStatus.ACCEPTED);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Id not found!", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> updateQuestion(int id, Question updatedQuestion) {
        // Check if the question with the given ID exists in the database
        Optional<Question> existingQuestionOptional = questionRepository.findById(id);

        if (existingQuestionOptional.isPresent()) {
            // Question with the given ID exists, update its properties
            Question existingQuestion = existingQuestionOptional.get();
            existingQuestion.setQuestionTitle(updatedQuestion.getQuestionTitle());
            existingQuestion.setOption1(updatedQuestion.getOption1());
            existingQuestion.setOption2(updatedQuestion.getOption2());
            existingQuestion.setOption3(updatedQuestion.getOption3());
            existingQuestion.setOption4(updatedQuestion.getOption4());
            existingQuestion.setRightAnswer(updatedQuestion.getRightAnswer());
            existingQuestion.setDifficultyLevel(updatedQuestion.getDifficultyLevel());
            existingQuestion.setCategory(updatedQuestion.getCategory());

            // Save the updated question to the database
            questionRepository.save(existingQuestion);

            return new ResponseEntity<>("Question updated successfully", HttpStatus.OK);
        } else {
            // Question with the given ID does not exist
            return new ResponseEntity<>("Question not found", HttpStatus.NOT_FOUND);
        }
    }
}
