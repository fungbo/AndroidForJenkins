package com.example.bfeng.activityapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import static com.example.bfeng.activityapp.AFragment.UIChanger;

public class MainActivity extends AppCompatActivity implements UIChanger {
    public static final String TAG = MainActivity.class.getSimpleName();
    public static final int CODE_SEND_MESSAGE = 1001;

    public boolean hasChangedFragment = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, new BFragment());
        transaction.commit();
    }

    @Override
    protected void onRestart() {
        Log.i(TAG, "onRestart");
        super.onRestart();
    }

    @Override
    protected void onStart() {
        Log.i(TAG, "onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy");
        super.onDestroy();
    }

    public void sendExplicitIntent(View view) {
        Intent intent = new Intent(this, AActivity.class);
        startActivity(intent);
    }

    public void sendImplicitIntent(View view) {
        Intent intent = new Intent("android.intent.action.AActivity");
        startActivity(intent);
    }

    public void dial(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        String phone = "tel:" + "10086";
        intent.setData(Uri.parse(phone));
        startActivity(intent);
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, AActivity.class);
        intent.putExtra("msg", "From MainActivity");
        startActivityForResult(intent, CODE_SEND_MESSAGE);
    }

    public void changeFragment(View view) {
        hasChangedFragment = true;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new CFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_SEND_MESSAGE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                String replyMsg = extras.getString("reply");
                setTitle(replyMsg);
            }
        }
    }

    @Override
    public void changeTitle(String title) {
        setTitle(title);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("fragmentChanged", hasChangedFragment);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        hasChangedFragment = savedInstanceState.getBoolean("fragmentChanged");
    }
}
