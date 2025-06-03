package com.aniket.questionservice.service;

import com.aniket.questionservice.dao.QuestionDao;
import com.aniket.questionservice.model.Question;
import com.aniket.questionservice.model.QuestionWrapper;
import com.aniket.questionservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> saveQuestion(Question question) {
        try{
            questionDao.save(question);
            Logger.getLogger(QuestionService.class.getName()).log(Level.INFO, "Question created");
            return new ResponseEntity<>("Question created", HttpStatus.CREATED);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Question could not be created", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    public ResponseEntity<String> deleteQuestion(Integer id) {
        try {
            questionDao.deleteById(id);
            Logger.getLogger(QuestionService.class.getName()).log(Level.INFO, "Question deleted successfully");
            return new ResponseEntity<>("Question deleted", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Question could not be deleted", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    public ResponseEntity<String> updateQuestion(Integer id, Question question) {
        try {
            List<Question> questionList = questionDao.getQuestionsById(id);
            if (questionList.size() > 0 && questionList.get(0) != null &&  questionList.get(0).getId().equals(id)) {
                questionDao.deleteById(id);
                Logger.getLogger(QuestionService.class.getName()).log(Level.INFO, "Question deleted with id: " + id);
                questionDao.save(question);
                Logger.getLogger(QuestionService.class.getName()).log(Level.INFO, "Question saved with id: " + id);
                return new ResponseEntity<>("Question updated", HttpStatus.OK);
            }
            else throw new Exception();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Question could not be updated", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    public ResponseEntity<List<Integer>> getQuestionsforQuiz(String categoryName, Integer numQuestions) {
        List<Integer> questions = questionDao.findRandomQuestionByCategory(categoryName, numQuestions);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
        List<QuestionWrapper> wrappers = new ArrayList<>();
        List<Question> questions = questionDao.findAllById(questionIds);
        for (Question question : questions) {
            QuestionWrapper wrapper = new QuestionWrapper();
            wrapper.setId(question.getId());
            wrapper.setQuestionTitle(question.getQuestionTitle());
            wrapper.setOption1(question.getOption1());
            wrapper.setOption2(question.getOption2());
            wrapper.setOption3(question.getOption3());
            wrapper.setOption4(question.getOption4());
            wrappers.add(wrapper);
        }
        return new ResponseEntity<>(wrappers, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {
        int rightAnswer = 0;
        for (Response response : responses) {
            Question question = questionDao.findById(response.getId()).get();
            if (response.getResponse().equals(question.getCorrectAnswer())) rightAnswer++;
        }
        return new ResponseEntity<>(rightAnswer, HttpStatus.OK);
    }
}
