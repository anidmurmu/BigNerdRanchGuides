package com.example.anid.geoquiz;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String TAG = "CheatActivity";

    private static final String EXTRA_ANSWER_IS_TRUE = "com.example.anid.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.example.anid.geoquiz.answer_shown";
    private static final String EXTRA_ANSWER_IS_SHOWN = "com.example.anid.qeoquiz.answer_is_shown";

    private boolean mAnswerIsTrue;

    private TextView mAnswerTextView;
    private Button mShowAnswerButton;
    private boolean mAnswerIsShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);

        if(savedInstanceState != null) {
            mAnswerIsShown = savedInstanceState.getBoolean(EXTRA_ANSWER_IS_SHOWN, false);
            mAnswerIsTrue = savedInstanceState.getBoolean(EXTRA_ANSWER_IS_TRUE, false);
            if(mAnswerIsShown) {
                if (mAnswerIsTrue) {
                    setTextForAnswer(R.string.true_button);
                } else {
                    setTextForAnswer(R.string.false_button);
                }
            }
            setAnswerShownResult(mAnswerIsShown);
        }

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);


        mShowAnswerButton = (Button) findViewById(R.id.show_answer_button);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAnswerIsTrue) {
                    setTextForAnswer(R.string.true_button);
                }else {
                    setTextForAnswer(R.string.false_button);
                }
                mAnswerIsShown = true;
                setAnswerShownResult(mAnswerIsShown);
            }
        });
    }

    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
        outState.putBoolean(EXTRA_ANSWER_IS_SHOWN, mAnswerIsShown);
        outState.putBoolean(EXTRA_ANSWER_IS_TRUE, mAnswerIsTrue);
    }

    private void setTextForAnswer(int resId) {
        mAnswerTextView.setText(resId);
    }
}
