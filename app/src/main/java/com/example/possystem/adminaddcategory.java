package com.example.possystem;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class adminaddcategory extends AppCompatActivity {

    EditText etCategory, etsubCategory, etBrand;
    Button btnadd,btnback;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminaddcategory);

        etCategory=findViewById(R.id.addcategory);
        etsubCategory=findViewById(R.id.subcategory);
        etBrand=findViewById(R.id.addbrand);
        btnadd=findViewById(R.id.adddata);
        btnback=findViewById(R.id.btnback);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Category");

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etCategory.getText().toString()!="" && etsubCategory.getText().toString()!="" && etBrand.getText().toString()!="")
                {
                    Category category=new Category();
                    category.setCategory(etCategory.getText().toString());
                    category.setSubcategory(etsubCategory.getText().toString());
                    category.setBrand(etBrand.getText().toString());
                    mDatabase.push().setValue(category);
                    Toast.makeText(adminaddcategory.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                    etCategory.setText("");
                    etsubCategory.setText("");
                    etBrand.setText("");

                }
                else
                    Toast.makeText(adminaddcategory.this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();


            }
        });
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(adminaddcategory.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private class Category{
        private String category;
        private String brand;
        private String subcategory;

        public Category(){
            //this constructor is required
        }

        public Category(String category, String brand,String subcategory) {
            this.category = category;
            this.brand = brand;
            this.subcategory = subcategory;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getSubcategory() {
            return subcategory;
        }

        public void setSubcategory(String subcategory) {
            this.subcategory = subcategory;
        }
    }
}