package com.example.anid.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";

    private Button mTrueButton;
    private Button mFasleButton;
    private Button mNextButton;
    private TextView mQuestionTextView;
    private int mTotalQuestions;
    private int mCountCorrectAnswers = 0;
    private int mTotalQuestionsAnswered = 0;

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        mTotalQuestions = mQuestionBank.length;

        if(savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mTotalQuestionsAnswered = savedInstanceState.getInt("total_answered", 0);
            mCountCorrectAnswers = savedInstanceState.getInt("total_correct", 0);
        }

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);

            }
        });
        mFasleButton = (Button) findViewById(R.id.false_button);
        mFasleButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });

        mNextButton  = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        updateQuestion();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
        outState.putInt(KEY_INDEX, mCurrentIndex);
        outState.putInt("total_answered", mTotalQuestionsAnswered);
        outState.putInt("total_correct", mCountCorrectAnswers);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {

        // when all questions has been answered
        if(mTotalQuestionsAnswered == mTotalQuestions) {
            Toast.makeText(this, "Correct Percentage :" + ((double)mCountCorrectAnswers / mTotalQuestions) * 100, Toast.LENGTH_SHORT).show();
            return;
        }

        // when a question has been already answered
        if(mQuestionBank[mCurrentIndex].isAnsweredAlready()) {
            Toast.makeText(this, "Already Answered", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;
        if(userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
            mCountCorrectAnswers++;
        }else {
            messageResId = R.string.incorrect_toast;
        }
        mTotalQuestionsAnswered++;
        mQuestionBank[mCurrentIndex].setAnsweredAlready(true);
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }
}
