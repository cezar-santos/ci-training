package com.example.ci_training.text;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TextAnalysisControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldReturnCorrectStatsForNormalText() throws Exception {
        mockMvc.perform(post("/api/text/analyze")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"text": "Hello world. Hello Java. Hello CI!"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.wordCount").value(6))
                .andExpect(jsonPath("$.sentenceCount").value(3))
                .andExpect(jsonPath("$.mostFrequentWord").value("hello"));
    }

    @Test
    void shouldReturnCorrectCharCountIgnoringSpaces() throws Exception {
        mockMvc.perform(post("/api/text/analyze")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"text": "Hello world"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.wordCount").value(2))
                .andExpect(jsonPath("$.charCount").value(10))
                .andExpect(jsonPath("$.sentenceCount").value(1));
    }

    @Test
    void shouldReturnBadRequestForBlankText() throws Exception {
        mockMvc.perform(post("/api/text/analyze")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"text": "   "}
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestForNullText() throws Exception {
        mockMvc.perform(post("/api/text/analyze")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"text": null}
                                """))
                .andExpect(status().isBadRequest());
    }
}
