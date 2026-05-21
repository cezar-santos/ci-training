package com.example.ci_training.text;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TextAnalysisService {

    public TextAnalysisResponse analyze(String text) {
        if (text == null || text.isBlank()) {
            return new TextAnalysisResponse(0, 0, 0, null, 0.0);
        }

        String[] words = text.trim().split("\\s+");
        int wordCount = words.length;
        int charCount = text.replaceAll("\\s", "").length();
        int sentenceCount = countSentences(text);
        String mostFrequentWord = findMostFrequent(words);
        double averageWordLength = averageWordLength(words);

        return new TextAnalysisResponse(wordCount, charCount, sentenceCount, mostFrequentWord, averageWordLength);
    }

    private int countSentences(String text) {
        String[] sentences = text.split("[.!?]+");
        return (int) Arrays.stream(sentences)
                .filter(s -> !s.isBlank())
                .count();
    }

    private double averageWordLength(String[] words) {
        return Arrays.stream(words)
                .mapToInt(w -> w.replaceAll("[^a-zA-Z0-9]", "").length())
                .average()
                .orElse(0.0);
    }

    private String findMostFrequent(String[] words) {
        return Arrays.stream(words)
                .map(w -> w.replaceAll("[^a-zA-Z0-9]", "").toLowerCase())
                .filter(w -> !w.isEmpty())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse(null);
    }
}