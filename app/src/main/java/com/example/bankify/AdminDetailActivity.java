package com.example.bankify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AdminDetailActivity extends AppCompatActivity {

    EditText txtdetailname,txtdetailgender,txtdetailaddress,txtdetailpostal,txtdetailemail, txtdetailphone, txtdetailcardnumber, txtdetaildob;
    Button btneditprofile, btnreturn;
    private FirebaseFirestore mfirestore;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_detail);

        String id = getIntent().getStringExtra("id_customer");
        mfirestore = FirebaseFirestore.getInstance();

        txtdetailname = findViewById(R.id.txtdetailname);
        txtdetailgender = findViewById(R.id.txtdetailgender);
        txtdetailaddress = findViewById(R.id.txtdetailaddress);
        txtdetailpostal = findViewById(R.id.txtdetailpostal);
        txtdetailemail = findViewById(R.id.txtdetailemail);
        txtdetailphone = findViewById(R.id.txtdetailphone);
        txtdetailcardnumber = findViewById(R.id.txtdetailcardnumber);
        txtdetaildob = findViewById(R.id.txtdetaildob);

        btneditprofile = findViewById(R.id.btnMod);
        btnreturn = findViewById(R.id.btnreturn);

        btnreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminDetailActivity.this,AdminHomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        if (id == null || id == ""){
            btneditprofile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String customername = txtdetailname.getText().toString().trim();
                    String cardnumber = txtdetailcardnumber.getText().toString().trim();
                    String phone = txtdetailphone.getText().toString().trim();

                    if(customername.isEmpty() && cardnumber.isEmpty() && phone.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Ingresar los datos", Toast.LENGTH_SHORT).show();
                    }else{
                        postClient(customername, cardnumber, phone);
                    }
                }
            });
        }else{
            btneditprofile.setText("Update");
            getClient(id);
            btneditprofile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String customername = txtdetailname.getText().toString().trim();
                    String cardnumber = txtdetailcardnumber.getText().toString().trim();
                    String phone = txtdetailphone.getText().toString().trim();

                    if(customername.isEmpty() && cardnumber.isEmpty() && phone.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Ingresar los datos", Toast.LENGTH_SHORT).show();
                    }else{
                        updateClient(customername, cardnumber, phone, id);
                    }
                }
            });
        }
    }



    private void updateClient(String customername, String cardnumber, String phone, String id){
        Map<String, Object> map = new HashMap<>();
        map.put("customerName", customername);
        map.put("cardNumber", cardnumber );
        map.put("phone", phone);
        mfirestore.collection("accountDetails").document(id).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "actualizado exitoso", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "error al actualizar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void postClient(String customername, String cardnumber, String phone) {
        Map<String, Object> map = new HashMap<>();
        map.put("customerName", customername);
        map.put("cardNumber", cardnumber );
        map.put("phone", phone);

        mfirestore.collection("accountDetails").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                   Toast.makeText(getApplicationContext(), "creado exitoso", Toast.LENGTH_SHORT).show();
                   finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "error al ingresar", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getClient(String id){
        mfirestore.collection("accountDetails").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String customername = documentSnapshot.getString("customerName");
                String cardnumber = documentSnapshot.getString("cardNumber");
                String phone = documentSnapshot.getString("phone");
                String customerdob = documentSnapshot.getString("customerDob");
                String customergender = documentSnapshot.getString("customerGender");
                String customerpostalcode = documentSnapshot.getString("customerPostalCode");
                String email = documentSnapshot.getString("email");
                String customerAddress = documentSnapshot.getString("customerAddress");

                txtdetailname.setText(customername);
                txtdetailcardnumber.setText(cardnumber);
                txtdetailphone.setText(phone);
                txtdetailgender.setText(customergender);
                txtdetailaddress.setText(customerAddress);
                txtdetailpostal.setText(customerpostalcode);
                txtdetailemail.setText(email);
                txtdetaildob.setText(customerdob);


                Toast.makeText(getApplicationContext(), "exitoso", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "error al obtener datos", Toast.LENGTH_SHORT).show();
            }
        });
    }




}