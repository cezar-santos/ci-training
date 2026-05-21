package com.example.ci_training.palindrome;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/palindrome")
public class PalindromeController {

    private final PalindromeService service;

    public PalindromeController(PalindromeService service) {
        this.service = service;
    }

    @GetMapping
    public Map<String, Object> check(@RequestParam String word) {
        return Map.of(
                "word", word,
                "isPalindrome", service.isPalindrome(word)
        );
    }
}
