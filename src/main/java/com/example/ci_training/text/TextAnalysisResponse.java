package com.example.ci_training.text;

public record TextAnalysisResponse(
        int wordCount,
        int charCount,
        int sentenceCount,
        String mostFrequentWord
) {}