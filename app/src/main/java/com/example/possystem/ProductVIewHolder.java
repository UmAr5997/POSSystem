package com.example.possystem;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import Interface.ItemClickListener;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductVIewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtname,txtdescription;
    public ImageView productimg;
    public ItemClickListener listener;
    public ProductVIewHolder(@NonNull View itemView) {
        super(itemView);

        productimg=itemView.findViewById(R.id.product_image);
        txtname=itemView.findViewById(R.id.product_name);
        txtdescription=itemView.findViewById(R.id.product_description);

    }

    public void setItemClickListener(ItemClickListener listener)
    {
        this.listener=listener;

    }
    @Override
    public void onClick(View view) {
        listener.OnClick(view,getAdapterPosition(),false);

    }
}
