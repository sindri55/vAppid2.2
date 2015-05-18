package com.github.devnied.emvnfccard.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.github.devnied.emvnfccard.R;


public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }



    public void doLogIn(View view) {
        Intent intent = new Intent(this, SimplePayActivity.class);
        startActivity(intent);
        LoginActivity.this.finish();

    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        LoginActivity.this.finish();
    }

}
