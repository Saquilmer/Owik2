package com.owik.company;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Edit2 extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();


    private Button button14;

    private EditText price, amount, name;
    String mName,mPrice,mAmount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test3);
        String user;
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        user = currentFirebaseUser.getUid().toString();
        DatabaseReference hopperRef = database.getReference().child(user).child("SegundaTienda");

        button14 = findViewById(R.id.button4);

        button14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = findViewById(R.id.editText);
                amount = findViewById(R.id.editText2);
                price = findViewById(R.id.editText3);

                String mName = name.getText().toString().trim().toLowerCase();
                String mPrice = amount.getText().toString();
                String mAmount = price.getText().toString();

                Map<String, Object> hopperUpdates = new HashMap<>();


                    hopperUpdates.put(mName + "/amount", mAmount);
                    hopperUpdates.put(mName + "/name", mName);
                    hopperUpdates.put(mName + "/price", mPrice);

                    hopperRef.updateChildren(hopperUpdates);

                    Toast.makeText(com.owik.company.Edit2.this, mName+" Editado", Toast.LENGTH_SHORT).show();
                name.setText("");
                amount.setText("");
                price.setText("");
            }
        });


    }
}
