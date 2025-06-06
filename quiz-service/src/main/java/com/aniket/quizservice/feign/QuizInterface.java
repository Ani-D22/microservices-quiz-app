package com.aniket.quizservice.feign;

import com.aniket.quizservice.model.QuestionWrapper;
import com.aniket.quizservice.model.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("QUESTION-SERVICE")
public interface QuizInterface {
    // generate
    @GetMapping("question/generate")
    public ResponseEntity<List<Integer>> generateQuestionsForQuiz
    (@RequestParam String categoryName, @RequestParam Integer numQuestions);

    // getQuestions (questionId)
    @PostMapping("question/getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds);

    // getScore
    @PostMapping("question/getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses);
}
