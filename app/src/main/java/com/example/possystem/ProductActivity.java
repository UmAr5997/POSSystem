package com.example.possystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ProductActivity extends AppCompatActivity {

    private DatabaseReference productref;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        productref= FirebaseDatabase.getInstance().getReference("Product");

        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Product> option=new FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(productref,Product.class)
                .build();
        FirebaseRecyclerAdapter<Product, ProductVIewHolder> adapter
                = new FirebaseRecyclerAdapter<Product, ProductVIewHolder>(option) {
            @Override
            protected void onBindViewHolder(@NonNull ProductVIewHolder productVIewHolder, int i, @NonNull Product product) {
                productVIewHolder.txtname.setText(product.getPname());
                productVIewHolder.txtdescription.setText("Price "+product.getPprice()+"Rs");
                Picasso.get().load(product.getImageid()).into(productVIewHolder.productimg);
            }

            @NonNull
            @Override
            public ProductVIewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.productitem_layout,parent,false);
                ProductVIewHolder holder=new ProductVIewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}