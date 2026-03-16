package com.example.trial.data;

public class Riddle {
    private String question;
    private String[] choices;
    private int correctIndex;
    private String category;
    private String difficulty;

    public Riddle(String question, String[] choices, int correctIndex, String category, String difficulty) {
        this.question = question;
        this.choices = choices;
        this.correctIndex = correctIndex;
        this.category = category;
        this.difficulty = difficulty;
    }

    public String getQuestion() { return question; }
    public String[] getChoices() { return choices; }
    public int getCorrectIndex() { return correctIndex; }
    public String getCategory() { return category; }
    public String getDifficulty() { return difficulty; }
    public String getCorrectAnswer() { return choices[correctIndex]; }
}
