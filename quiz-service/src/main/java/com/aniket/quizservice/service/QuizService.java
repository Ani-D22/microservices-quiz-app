package com.aniket.quizservice.service;

import com.aniket.quizservice.dao.QuizDao;
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

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer quizId) {
//        Optional<Quiz> quiz = quizDao.findById(quizId);
//        List<Question> questionsFromDb = quiz.get().getQuestions();
        List<QuestionWrapper> questionWrappers = new ArrayList<>();
//        for (Question q : questionsFromDb) {
//            QuestionWrapper qw = new QuestionWrapper
//                    (q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
//            questionWrappers.add(qw);
//        }
        return new ResponseEntity<>(questionWrappers, HttpStatus.OK);
    }

//    @Autowired
//    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, Integer numQ, String title) {

//        List<Integer> questions = null;// call the "generate" url - RestTemplate http://localhost:8080/question/generate

//        Quiz quiz = new Quiz();
//        quiz.setTitle(title);
//        quiz.setQuestions(questions);
//        quizDao.save(quiz);
        return new ResponseEntity<>("Quiz created", HttpStatus.CREATED);
    }

    public ResponseEntity<Integer> calculateResult(Integer quizId, List<Response> responses) {
//        Quiz quiz = quizDao.findById(quizId).get();
//        List<Question> questions = quiz.getQuestions();
        int rightAnswer = 0;
//        int i = 0;
//        for (Response r : responses) {
//            if (r.getResponse().equals(questions.get(i).getCorrectAnswer())) rightAnswer++;
//            i++;
//        }
        return new ResponseEntity<>(rightAnswer, HttpStatus.OK);
    }
}
