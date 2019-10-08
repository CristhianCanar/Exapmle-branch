package com.nitrocanar.multimediappcristhian.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.nitrocanar.multimediappcristhian.R;

public class MenuPricipal extends AppCompatActivity {
    Button btnNuevoR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_pricipal);

        references();
    }

    private void references() {
        btnNuevoR = findViewById(R.id.btnNuevoRepor);
        btnNuevoR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuPricipal.this,MainActivity.class));
                finish();
            }
        });
    }

}
