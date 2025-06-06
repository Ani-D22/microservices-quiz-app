package com.aniket.quizservice.service;

import com.aniket.quizservice.dao.QuizDao;
import com.aniket.quizservice.feign.QuizInterface;
import com.aniket.quizservice.model.QuestionWrapper;
import com.aniket.quizservice.model.Quiz;
import com.aniket.quizservice.model.Response;
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
    QuizDao quizDao;

    @Autowired
    QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(String categoryName, Integer numQuestions, String title) {
        List<Integer> questions = quizInterface.generateQuestionsForQuiz(categoryName, numQuestions).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("Quiz created", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer quizId) {
        Quiz quiz = quizDao.findById(quizId).get();
        List<Integer> questionIds = quiz.getQuestionIds();
        List<QuestionWrapper> questionWrappers = quizInterface.getQuestionsFromId(questionIds).getBody();
        return new ResponseEntity<>(questionWrappers, HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer quizId, List<Response> responses) {
        return quizInterface.getScore(responses);
    }
}
