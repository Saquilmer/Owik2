package com.owik.company;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
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

public class VentaBusqueda extends AppCompatActivity
{
    RecyclerView recview;
    myadapter2 adapter;
    Button buttonEdit, buttonSell;
    Menu menu;
    FirebaseDatabase db = FirebaseDatabase.getInstance();



    Button ventaButton, select;
    private EditText price, amount, name;
    String mName,mPrice,mAmount;
    private String user;
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ventabusqueda);
        setTitle("");


        recview=(RecyclerView)findViewById(R.id.recview3);
        recview.setLayoutManager(new LinearLayoutManager(this));
        user = currentFirebaseUser.getUid();
        DatabaseReference dataReference = db.getReference().child(user).child("PrimeraTienda");
        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child(user).child("PrimeraTienda"), Model.class)
                        .build();

        // buttonEdit.setOnClickListener(new View.OnClickListener() {
        //     @Override
        //     public void onClick(View v) {
        //         startActivity(new Intent(Search.this , Edit.class));
        //     }
        // });

        adapter=new myadapter2(options);
        recview.setAdapter(adapter);


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

                        if(Double.parseDouble(String.valueOf(dataSnapshot.getValue())) >=0 && Double.parseDouble(String.valueOf(dataSnapshot.getValue())) - Double.parseDouble(mAmount) >=0){
                        try {

                            hopperUpdates.put(mName + "/amount", Double.toString(Double.parseDouble(String.valueOf(dataSnapshot.getValue())) - Double.parseDouble(mAmount)));
                            Toast.makeText(VentaBusqueda.this, "Venta Registrada", Toast.LENGTH_SHORT).show();

                        }
                        catch (Exception e){

                        }
                        dataReference.updateChildren(hopperUpdates);}
                        else { Toast.makeText(VentaBusqueda.this, "Sin Existencias", Toast.LENGTH_SHORT).show();}

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
                        DatabaseReference sellsData = db.getReference().child(user).child("Sells").child(formatter3.format(date)).child(formatter2.format(date));
                        userMap.put(formatter.format(date) , String.valueOf(Double.parseDouble(String.valueOf(snapshot.getValue()))*Double.parseDouble(mAmount)));
                        sellsData.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                name.setText("");
                                amount.setText("");
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

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.seacrhmenu,menu);

        MenuItem item= menu.findItem(R.id.search);

        SearchView searchView =(SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                processsearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processsearch(s);
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    private void processsearch(String s)
    {

        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child(user).child("PrimeraTienda").orderByChild("name").startAt(s).endAt(s+"\uf8ff"), Model.class)
                        .build();


        adapter=new myadapter2(options);
        adapter.startListening();
        recview.setAdapter(adapter);

    }
}