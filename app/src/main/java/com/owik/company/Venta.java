package com.owik.company;

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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Venta extends AppCompatActivity {

    FirebaseDatabase db = FirebaseDatabase.getInstance();



    Button ventaButton;
    private EditText price, amount, name;
    String mName,mPrice,mAmount;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.venta);

String user;
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        user = currentFirebaseUser.getUid().toString();
        DatabaseReference dataReference = db.getReference().child(user).child("PrimeraTienda");
        name = findViewById(R.id.ventaproduct);
        amount = findViewById(R.id.ventaAmount);

        String mName = name.getText().toString();
        String mPrice = amount.getText().toString();


        ventaButton = findViewById(R.id.ventaButton);
        ventaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText1;

                name = findViewById(R.id.ventaproduct);
                amount = findViewById(R.id.ventaAmount);


                String mName = name.getText().toString();
                String mAmount = amount.getText().toString();


                Map<String, Object> hopperUpdates = new HashMap<>();

                dataReference.child(mName).limitToFirst(1).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {


try {

    hopperUpdates.put(mName + "/amount", Double.toString(Double.parseDouble(String.valueOf(dataSnapshot.getValue())) - Double.parseDouble(mAmount)));

}
catch (Exception e){

}
                            dataReference.updateChildren(hopperUpdates);

                        }


                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }


                });

                dataReference.child(mName).limitToLast(1).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        HashMap<String , Object> userMap = new HashMap<>();
                        Date date = new Date();
                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                        SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MM-yyyy");
                        SimpleDateFormat formatter3 = new SimpleDateFormat("MM-yyyy");
                        DatabaseReference sellsData = db.getReference().child("Sells").child(formatter3.format(date)).child(formatter2.format(date));
                        userMap.put(formatter.format(date) , String.valueOf(Double.parseDouble(String.valueOf(snapshot.getValue()))*Double.parseDouble(mAmount)));
                        sellsData.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(Venta.this, "Venta Registrada", Toast.LENGTH_SHORT).show();
                            }
                        });


                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

        });
    }
}
