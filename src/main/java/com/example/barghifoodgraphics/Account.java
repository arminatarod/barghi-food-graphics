package com.example.barghifoodgraphics;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class Account {
    private String username, password, type, recoveryQuestion, recoveryQuestionAnswer;
    private int id;
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
    public boolean checkPassword(String password) {
        return password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    static public Account getAccount(int ID) {
        Account result;
        ObjectMapper mapper = new ObjectMapper();
        try {
            result = mapper.readValue("src/data/accounts/" + ID + ".json", Account.class);
        } catch (Exception e) {
            return null;
        }
        return result;
    }
    static public void saveAccount(int ID, Account account) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("src/data/accounts/" + ID + ".json"), account);
        } catch (Exception ignored) {}
    }
}
