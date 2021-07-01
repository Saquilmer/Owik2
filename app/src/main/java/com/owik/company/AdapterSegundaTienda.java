package com.owik.company;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AdapterSegundaTienda extends FirebaseRecyclerAdapter<Model, AdapterSegundaTienda.myviewholder>
{
    public AdapterSegundaTienda(@NonNull FirebaseRecyclerOptions<Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull Model model) {
        holder.name.setText(model.getName());
        holder.amount.setText(model.getAmount());
        holder.price.setText(model.getPrice());
    }


    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder
    {

        TextView name,amount,price;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.nametext);
            amount=(TextView)itemView.findViewById(R.id.amounttext);
            price=(TextView)itemView.findViewById(R.id.pricetext);
        }
    }
}

