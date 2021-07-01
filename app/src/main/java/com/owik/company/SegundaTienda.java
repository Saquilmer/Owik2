package com.owik.company;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SegundaTienda extends AppCompatActivity {
    FirebaseDatabase db = FirebaseDatabase.getInstance();


    private EditText mName , mAmount, mPrice;
    private Button button, button10, button12, button14;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.segundatienda);

        String user;
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        user = currentFirebaseUser.getUid().toString();
        DatabaseReference dataReference = db.getReference().child(user).child("SegundaTienda");
        mName = findViewById(R.id.name);
        mAmount = findViewById(R.id.amount);
        mPrice = findViewById(R.id.price);
        button = findViewById(R.id.button9);
        button10 = findViewById(R.id.button10);
        button12 = findViewById(R.id.button12);
        button14 = findViewById(R.id.button14);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SegundaTienda.this, Agregar.class));
            }
        });
        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v5) {
                startActivity(new Intent(SegundaTienda.this, Search2.class));
            }
        });


        button12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v5) {
                startActivity(new Intent(SegundaTienda.this, Dashboard2.class));
            }
        });
        button14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v5) {
                startActivity(new Intent(SegundaTienda.this, Edit2.class));
            }
        });



    }
}
