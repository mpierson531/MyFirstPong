package com.mygdx.Pong.Engine.Json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.ContextAttributes;
import com.mygdx.Pong.Engine.Files.FileHandler;

import java.io.File;
import java.io.IOException;

public class JsonHandler {
    private final ObjectMapper objectMapper;
    public JsonHandler() {
        objectMapper = new ObjectMapper();
        objectMapper.enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);
    }

    public void serialize(Object value, FileHandler fileHandler) {
        try {
            fileHandler.writeFile(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(value));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void serialize(Object value, File file) {
        if (file.exists()) {
            try {
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, value);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Object deserialize(String json, Object value, Object[] ignorableFields) {
        try {
            objectMapper.enable(JsonParser.Feature.IGNORE_UNDEFINED);
            return objectMapper.readValue(json, value.getClass());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Object deserialize(String json, Object value) {
        try {
                return objectMapper.readValue(json, value.getClass());
            } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Object deserialize(File file, Object value) {
        try {
            return objectMapper.readValue(file, value.getClass());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}