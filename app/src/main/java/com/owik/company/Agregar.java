package com.owik.company;

import android.os.Bundle;
import android.text.TextUtils;
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

import static com.google.firebase.database.FirebaseDatabase.getInstance;

public class Agregar extends AppCompatActivity {
    private EditText mName , mAmount, mPrice;
    private Button button;
    private String us;

    private FirebaseDatabase db = getInstance();
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar);

        mName = findViewById(R.id.name);
        mAmount = findViewById(R.id.amount);
        mPrice = findViewById(R.id.price);
        button = findViewById(R.id.button);
        us = currentFirebaseUser.getUid().toString();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    String name = mName.getText().toString().trim().toLowerCase();
                    String price = mPrice.getText().toString().trim();
                    String amount = mAmount.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "Enter Product Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(price)) {
                    Toast.makeText(getApplicationContext(), "Enter Price", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(amount)) {
                    Toast.makeText(getApplicationContext(), "Enter Amount", Toast.LENGTH_SHORT).show();
                    return;
                }

                HashMap<String , String> userMap = new HashMap<>();
                DatabaseReference root = db.getReference().child(us).child("PrimeraTienda").child(name);
                userMap.put("name" , name);
                userMap.put("amount" , amount);
                userMap.put("price" , price);
                root.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(com.owik.company.Agregar.this, "Datos Guardados", Toast.LENGTH_SHORT).show();
                        mName.setText("");
                        mAmount.setText("");
                        mPrice.setText("");
                    }
                });

            }
        });
            }

    }

