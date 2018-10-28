package com.example.anid.permission;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        mTextView = (TextView) findViewById(R.id.textView);

        if(getIntent().getExtras() != null) {
            String msg = getIntent().getExtras().getString("message");
            mTextView.setText(msg);
        }
    }
}
