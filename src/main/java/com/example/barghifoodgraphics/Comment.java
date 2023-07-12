package com.example.barghifoodgraphics;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class Comment {
    private int id, commenterID, receptionId, receptionType;
    private String content, answer;

    public Comment(int id, int commenterID, int receptionId, int receptionType, String content) {
        this.id = id;
        this.commenterID = commenterID;
        this.receptionId = receptionId;
        this.receptionType = receptionType;
        this.content = content;
        // type 1  :  for food, type 2 for restaurant
    }
    @JsonCreator
    public Comment(@JsonProperty("id")int id,@JsonProperty("commenterID") int commenterID,@JsonProperty("receptionId") int receptionId,@JsonProperty("receptionType") int receptionType,@JsonProperty("content") String content,@JsonProperty("answer") String answer) {
        this.id = id;
        this.commenterID = commenterID;
        this.receptionId = receptionId;
        this.receptionType = receptionType;
        this.content = content;
        this.answer = answer;
    }

    public int getCommenterID() {
        return commenterID;
    }

    public void setCommenterID(int commenterID) {
        this.commenterID = commenterID;
    }

    public int getReceptionId() {
        return receptionId;
    }

    public void setReceptionId(int receptionId) {
        this.receptionId = receptionId;
        save();
    }

    public int getReceptionType() {
        return receptionType;
    }

    public void setReceptionType(int receptionType) {
        this.receptionType = receptionType;
        save();
    }

    public int getId() {
        return id;
    }
    public int getCommenter() {
        return commenterID;
    }
    public String getAnswer() {
        return answer;
    }
    public String getContent() {
        return content;
    }
    public void setId(int id) {
        this.id = id;
        save();
    }
    public void setCommenter(int commenterID) {
        this.commenterID = commenterID;
        save();
    }
    public void setAnswer(String answer) {
        this.answer = answer;
        save();
    }
    public void setContent(String commentText) {
        this.content = commentText;
        save();
    }
    public void save() {
        if (id == -1) {
            return;
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("src/data/comments/" + id + ".json"), this);
        } catch (Exception ignored) {}
    }
}
