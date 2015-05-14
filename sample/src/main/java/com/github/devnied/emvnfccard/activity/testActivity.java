package com.github.devnied.emvnfccard.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.github.devnied.emvnfccard.R;
import com.github.devnied.emvnfccard.utils.CroutonUtils;

/**
 * Created by Sindri on 30/04/15.
 */
public class testActivity extends FragmentActivity {

    String extra;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.helloworld);


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                CroutonUtils.display(testActivity.this, "Greiðsla hefur verið framkvæmd!", CroutonUtils.CoutonColor.GREEN);


            }
        }, 1000);
    }

    public void doagain(View view) {
        Intent intent = new Intent(this, SimplePayActivity.class);
        startActivity(intent);
    }
}
