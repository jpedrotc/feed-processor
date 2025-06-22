package com.example.feedprocessor.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class JsonFormatter {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static Optional<String> toJson(Object obj) {
        try {
            return Optional.of(mapper.writeValueAsString(obj));
        } catch (Exception e) {
            log.error("Failed to convert object to JSON", e);
            return Optional.empty();
        }
    }
}

