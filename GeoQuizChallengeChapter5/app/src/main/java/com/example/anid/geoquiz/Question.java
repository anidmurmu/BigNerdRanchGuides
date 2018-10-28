package com.example.anid.geoquiz;

public class Question {

    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mIsAlreadyAnswered;

    public boolean isAlreadyAnswered() {
        return mIsAlreadyAnswered;
    }

    public void setAlreadyAnswered(boolean alreadyAnswered) {
        mIsAlreadyAnswered = alreadyAnswered;
    }

    public Question(int textRestId, boolean answerTrue) {
        mTextResId = textRestId;
        mAnswerTrue = answerTrue;
        mIsAlreadyAnswered = false;
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
}
