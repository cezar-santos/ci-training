package com.example.ci_training.palindrome;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PalindromeControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldReturnTrueForPalindrome() throws Exception {
        mockMvc.perform(get("/api/palindrome").param("word", "racecar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.word").value("racecar"))
                .andExpect(jsonPath("$.isPalindrome").value(true));
    }

    @Test
    void shouldReturnFalseForNonPalindrome() throws Exception {
        mockMvc.perform(get("/api/palindrome").param("word", "hello"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isPalindrome").value(false));
    }

    @Test
    void shouldIgnoreCaseAndPunctuation() throws Exception {
        mockMvc.perform(get("/api/palindrome").param("word", "A man a plan a canal Panama"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isPalindrome").value(true));
    }
}
