package com.example.barghifoodgraphics;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class Account {
    private String username, password, type, recoveryQuestion, recoveryQuestionAnswer;
    private int id;
    @JsonCreator
    public Account(String username, String password, String type, String recoveryQuestion, String recoveryQuestionAnswer, int id) {
        this.username = username;
        this.password = password;
        this.type = type;
        this.recoveryQuestion = recoveryQuestion;
        this.recoveryQuestionAnswer = recoveryQuestionAnswer;
        this.id = id;
    }

    public Account(String username, String password, String recoveryQuestion, String recoveryQuestionAnswer, int id) {
        this.username = username;
        this.password = password;
        this.recoveryQuestion = recoveryQuestion;
        this.recoveryQuestionAnswer = recoveryQuestionAnswer;
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public String getPassword() {
        return password;
    }
    public String getRecoveryQuestion() {
        return recoveryQuestion;
    }
    public String getUsername() {
        return username;
    }
    public String getRecoveryQuestionAnswer() {
        return recoveryQuestionAnswer;
    }
    static public boolean checkPassword(String password) {
        return password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRecoveryQuestionAnswer(String recoveryQuestionAnswer) {
        this.recoveryQuestionAnswer = recoveryQuestionAnswer;
    }

    public void setRecoveryQuestion(String recoveryQuestion) {
        this.recoveryQuestion = recoveryQuestion;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void save() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("src/data/accounts/" + id + ".json"), this);
        } catch (Exception ignored) {}
    }
}
