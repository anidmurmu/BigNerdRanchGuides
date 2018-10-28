package com.example.anid.geoquiz;

public class Question {

    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mAnsweredAlready;

    public Question(int textRestId, boolean answerTrue) {
        mTextResId = textRestId;
        mAnswerTrue = answerTrue;
        mAnsweredAlready = false;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public boolean isAnsweredAlready() {
        return mAnsweredAlready;
    }

    public void setAnsweredAlready(boolean answeredAlready) {
        mAnsweredAlready = answeredAlready;
    }
}
