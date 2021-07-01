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

public class Edit extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();


    private Button button4;

    private EditText price, amount, name;
    String mName,mPrice,mAmount;
    private String user;
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test3);
        user = currentFirebaseUser.getUid().toString();
        DatabaseReference hopperRef = database.getReference().child(user).child("PrimeraTienda");
        button4 = findViewById(R.id.button4);

        button4.setOnClickListener(new View.OnClickListener() {
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

                    Toast.makeText(com.owik.company.Edit.this, mName+" Editado", Toast.LENGTH_SHORT).show();
                name.setText("");
                amount.setText("");
                price.setText("");
            }
        });


    }
}
