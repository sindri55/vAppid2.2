package com.github.devnied.emvnfccard.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.github.devnied.emvnfccard.R;

/**
 * Created by Sindri on 30/04/15.
 */
public class testActivity extends FragmentActivity {
    private ProgressDialog progress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.helloworld);

    }
}
