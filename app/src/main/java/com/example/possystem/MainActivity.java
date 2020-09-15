package com.example.possystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private ImageView ivProductName;
    private static int RESULT_LOAD_IMAGE = 100;
    Uri imageUri;
    DatabaseReference mDatabase,categorydb;
    FirebaseStorage storage;
    StorageReference storageReference;
//    private FirebaseAuth mAuth;
    EditText etProductName, etProductPrice, etProductQuantity, etProductSupplier, etcaton,etpriccoton;
    private Product product;
    private Button adddata,btnadmin,btnsearch,btnupdate,btndelete;
    private Spinner spncategory,spnbrand;
    private ArrayList<String> arraycategory,arraybrand,productlist;
    String imageid,value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button btnSelectImage = (Button) findViewById(R.id.btnProductImage);
        adddata = findViewById(R.id.btnadd);
        btnadmin=findViewById(R.id.btnclear);
        btnupdate=findViewById(R.id.btnupdate);
        TextView tvErrorMsg = (TextView) findViewById(R.id.errorMessage);
        etProductName = (EditText) findViewById(R.id.etProductName);
        etProductPrice = (EditText) findViewById(R.id.etProductPrice);
        etProductQuantity = (EditText) findViewById(R.id.etProductQuantity);
        etProductSupplier = (EditText) findViewById(R.id.etProductSupplier);
        spncategory=findViewById(R.id.spncategory);
        spnbrand=findViewById(R.id.spnbrand);

        arraycategory=new ArrayList<>();
        arraybrand=new ArrayList<>();
        productlist=new ArrayList<>();
        final ArrayAdapter<String> adaptercat=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arraycategory);
        final ArrayAdapter<String> adapterbrand=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arraybrand);

        final TextView Catontv = (TextView) findViewById(R.id.coton);
        final TextView Catonpricetv = (TextView) findViewById(R.id.pricecoton);
        final TextView prictv = (TextView) findViewById(R.id.tvprice);
        final TextView quantv = (TextView) findViewById(R.id.tvquan);
        etcaton = (EditText) findViewById(R.id.etcotonQuantity);
        etpriccoton = (EditText) findViewById(R.id.etcotonpriceQuantity);
        final CheckBox catonch = findViewById(R.id.chkbox);
        ivProductName = (ImageView) findViewById(R.id.ivProductImage);

        product = new Product();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Product");
        categorydb = FirebaseDatabase.getInstance().getReference().child("Category");

        categorydb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds != null) {
                        String s = ds.child("category").getValue(String.class);
                        String b = ds.child("brand").getValue(String.class);
                        arraycategory.add(s);
                        arraybrand.add(b);
                    }
                }
                adaptercat.notifyDataSetChanged();
                adapterbrand.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        spncategory.setAdapter(adaptercat);
        spnbrand.setAdapter(adapterbrand);
        catonch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (catonch.isChecked()) {
                    Catontv.setVisibility(View.VISIBLE);
                    etcaton.setVisibility(View.VISIBLE);
                    Catonpricetv.setVisibility(View.VISIBLE);
                    etpriccoton.setVisibility(View.VISIBLE);
                    etProductPrice.setVisibility(View.GONE);
                    etProductQuantity.setVisibility(View.INVISIBLE);
                    prictv.setVisibility(View.GONE);
                    quantv.setVisibility(View.GONE);
                } else {
                    Catontv.setVisibility(View.GONE);
//                    etcaton.setVisibility(View.GONE);
                    Catonpricetv.setVisibility(View.GONE);
                    etpriccoton.setVisibility(View.GONE);
                    etProductPrice.setVisibility(View.VISIBLE);
                    etProductQuantity.setVisibility(View.VISIBLE);
                    prictv.setVisibility(View.VISIBLE);
                    quantv.setVisibility(View.VISIBLE);
                }
            }
        });
        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
        adddata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etProductName.getText().toString();
                String email = etProductSupplier.getText().toString();
                String coton = etcaton.getText().toString();
                String price = etProductPrice.getText().toString();
                String quantity = etProductQuantity.getText().toString();
                String pricepcoton=etpriccoton.getText().toString();

//                product.setCategory(category);
//                product.setBrand(brand);
                product.setPname(name);
                product.setSemail(email);
                product.setPprice(price);
                product.setQty(quantity);
                if(!TextUtils.isEmpty(name) && adddata.getText().equals("Update")) {
                    if(catonch.isChecked() && coton!=null && pricepcoton!=null)
                    {
//                        product.setCotonqty(coton);
//                        product.setCotonprice(pricepcoton);
                    }
                    uploadImage();
                    mDatabase.child(value).setValue(product);
                    Toast.makeText(MainActivity.this, "Product Values Updated", Toast.LENGTH_SHORT).show();
                    adddata.setText("ADD");

                }
               else if(!TextUtils.isEmpty(name)) {
                    if(catonch.isChecked() && coton!=null && pricepcoton!=null)
                    {
//                        product.setCotonqty(coton);
//                        product.setCotonprice(pricepcoton);
                    }
                    uploadImage();
                    mDatabase.push().setValue(product);
                    Toast.makeText(MainActivity.this, "New Product Added", Toast.LENGTH_SHORT).show();

                }
                else
                    Toast.makeText(MainActivity.this, "Record Not Inserted", Toast.LENGTH_SHORT).show();
                etProductName.setText("");
                etProductQuantity.setText("");
                etProductPrice.setText("");
                etpriccoton.setText("");
                etcaton.setText("");
                etProductSupplier.setText("");
                ivProductName.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_launcher));

            }
        });
        btnadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ProductActivity.class);
                startActivity(intent);
            }
        });
    }
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == RESULT_LOAD_IMAGE){
            imageUri = data.getData();
            ivProductName.setImageURI(imageUri);
        }
    }
    private void uploadImage() {

        if(imageUri != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            imageid=imageUri.toString();
            product.setImageid(imageid);
            StorageReference ref = storageReference.child("Product_image/"+ imageid );
            ref.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }

    public void btn_showDialog(View view) {
        final AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
        View mview=getLayoutInflater().inflate(R.layout.dialog_layout,null);

        final EditText edtnam=mview.findViewById(R.id.search_product);
        btnsearch= (Button) mview.findViewById(R.id.btn_search);
        btndelete=mview.findViewById(R.id.btn_delte);
         Button btncancel= (Button) mview.findViewById(R.id.btn_cancel);

        dialog.setView(mview);
        final AlertDialog alertDialog=dialog.create();
        alertDialog.setCanceledOnTouchOutside(false);

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String q= edtnam.getText().toString();
                Query querry=FirebaseDatabase.getInstance().getReference("Product")
                        .orderByChild("pname")
                        .equalTo(q);
                querry.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            if (ds != null) {
                                ds.getRef().removeValue();
                                ivProductName.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_launcher));
                                Toast.makeText(MainActivity.this, "Record Deleted Successfully", Toast.LENGTH_SHORT).show();
                            }else
                                Toast.makeText(MainActivity.this, "No Record Found", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String q= edtnam.getText().toString();
                Query querry=FirebaseDatabase.getInstance().getReference("Product")
                        .orderByChild("pname")
                        .equalTo(q);
                querry.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            if (ds != null) {
                                Product s = ds.getValue(Product.class);
                                etProductName.setText(s.getPname());
                                etProductPrice.setText(s.getPprice());
                                etProductQuantity.setText(s.getQty());
                                etProductSupplier.setText(s.getSemail());
                                Picasso.get().load(s.getImageid()).into(ivProductName);
                                value = ds.getKey();
                                adddata.setText("Update");
                                alertDialog.dismiss();
                            }else
                                Toast.makeText(MainActivity.this, "No Record Found", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        alertDialog.show();
    }
}