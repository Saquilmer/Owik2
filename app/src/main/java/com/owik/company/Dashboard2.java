package com.owik.company;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Dashboard2 extends AppCompatActivity {

    FirebaseDatabase db = FirebaseDatabase.getInstance();

    Date date = new Date();

    SimpleDateFormat formatter2 = new SimpleDateFormat("MM-yyyy");

    TextView textView, textView2, textView6;
    double total, total2, total3;
    Model model;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test2);
        String user;
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        user = currentFirebaseUser.getUid().toString();

        DatabaseReference root = db.getReference().child(user).child("Sells2da").child(formatter2.format(date));

        final DatabaseReference root3 = db.getReference().child(user).child("SegundaTienda");
        textView = findViewById(R.id.textView3);
        textView2 = findViewById(R.id.textView5);
        textView6 = findViewById(R.id.textView6);

        root.limitToLast(1).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String UserUserName = String.valueOf(dataSnapshot.getValue());

                    total += Double.parseDouble(UserUserName);
                    textView.setText("Q " + String.valueOf(total));

                }
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

        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String UserUserName = String.valueOf(dataSnapshot.getValue());

                    total2 += Double.parseDouble(UserUserName);
                    textView2.setText("Q " + String.valueOf(total2));

                }
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

        root3.orderByChild("amount").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Model adapter = snapshot.getValue(Model.class);
                String sumaAmount = String.valueOf(adapter.getAmount());
                String sumaPrecio = String.valueOf(adapter.getPrice());


                total3 += Double.parseDouble(sumaAmount)*Double.parseDouble(sumaPrecio);
                textView6.setText("Q " + String.valueOf(total3));


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
}
