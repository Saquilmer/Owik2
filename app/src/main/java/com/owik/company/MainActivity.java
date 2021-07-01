package com.owik.company;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;

import static com.google.firebase.database.FirebaseDatabase.getInstance;

public class MainActivity extends AppCompatActivity {

    private EditText mName , mAmount, mPrice;
    private Button button , button2, button3, button5,button6, button8,button7,button13, button17 ;


    private FirebaseDatabase db = getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mName = findViewById(R.id.name);
        mAmount = findViewById(R.id.amount);
        mPrice = findViewById(R.id.price);
        button6 = findViewById(R.id.button6);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button5 = findViewById(R.id.button5);
        button8 = findViewById(R.id.button8);
        button13 = findViewById(R.id.button13);
        button17 = findViewById(R.id.button17);

        button8.setVisibility(View.INVISIBLE);
//

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this , Agregar.class));
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this , Dashboard.class));
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v2) {
                startActivity(new Intent(MainActivity.this , Search.class));
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v3) {
                startActivity(new Intent(MainActivity.this , Edit.class));
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v5) {
                startActivity(new Intent(MainActivity.this, SegundaTienda.class));
            }
        });
        button13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v5) {
                startActivity(new Intent(MainActivity.this, VentaBusqueda.class));
            }
        });
        button17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v5) {
                startActivity(new Intent(MainActivity.this, Delete.class));
            }
        });
        getSupportActionBar().hide();

    }


}