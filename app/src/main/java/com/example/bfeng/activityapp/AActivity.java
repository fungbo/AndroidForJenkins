package com.example.bfeng.activityapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            String msg = extras.getString("msg");
            setTitle(msg);
        }
    }

    public void reply(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("reply", "From AActivity");

        setResult(RESULT_OK, intent);
        finish();
    }


}
