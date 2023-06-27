package com.example.barghifoodgraphics;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class Comment {
    private int id;
    private String content, answer;
    private int commenterID;

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
    }
    public void setCommenter(int commenterID) {
        this.commenterID = commenterID;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    public void setContent(String commentText) {
        this.content = commentText;
    }
    static public Comment getComment(int ID) {
        Comment result;
        ObjectMapper mapper = new ObjectMapper();
        try {
            result = mapper.readValue("src/data/comments/" + ID + ".json", Comment.class);
        } catch (Exception e) {
            return null;
        }
        return result;
    }
    static public void saveComment(int ID, Comment comment) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("src/data/comments/" + ID + ".json"), comment);
        } catch (Exception ignored) {}
    }
}
