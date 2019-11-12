package com.example.wuziqi;

public class AnswerChoice {
    private String option;
    private String content;

    public AnswerChoice(String option, String content) {
        this.option = option;
        this.content = content;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
