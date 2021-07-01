package com.owik.company;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Delete extends AppCompatActivity{

    EditText mProduct;
    RecyclerView recview;
    myadapter2 adapter;
    Button button;
    String product;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete);
        FirebaseDatabase db = FirebaseDatabase.getInstance();

        setTitle("");

        String user;
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        mProduct = findViewById(R.id.deleteP);
        user = currentFirebaseUser.getUid();
        product = mProduct.getText().toString().trim().toLowerCase();


        DatabaseReference database = db.getReference().child(user).child("PrimeraTienda").child(product);
        recview=(RecyclerView)findViewById(R.id.recyclerView2);
        recview.setLayoutManager(new LinearLayoutManager(this));

        button = findViewById(R.id.button16);

        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child(user).child("PrimeraTienda"), Model.class)
                        .build();

        button.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             database.addListenerForSingleValueEvent(new ValueEventListener() {
                 @Override
                 public void onDataChange(DataSnapshot dataSnapshot) {
                     for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                         appleSnapshot.getRef().removeValue();
                         Toast.makeText(Delete.this,"Item Deleted",Toast.LENGTH_SHORT);
                     }
                 }

                 @Override
                 public void onCancelled(DatabaseError databaseError) {
                     Toast.makeText(Delete.this,"Error",Toast.LENGTH_SHORT);
                 }
             });
           }
        });



        adapter=new myadapter2(options);
        recview.setAdapter(adapter);
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
        String user;
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        user = currentFirebaseUser.getUid();

        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child(user).child("PrimeraTienda").orderByChild("name").startAt(s).endAt(s+"\uf8ff"), Model.class)
                        .build();


        adapter=new myadapter2(options);
        adapter.startListening();
        recview.setAdapter(adapter);

    }


}
