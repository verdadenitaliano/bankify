package com.example.bankify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AdminDetailActivity extends AppCompatActivity {

    TextView txtdetailname,txtdetailgender,txtdetailaddress,txtdetailpostal,txtdetailemail, txtdetailphone, txtdetailcardnumber, txtdetaildob;

    Button btneditprofile, btntransfer;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_detail);

        reference = FirebaseDatabase.getInstance().getReference("accountDetails");
        txtdetailname = findViewById(R.id.txtdetailname);
        txtdetailgender = findViewById(R.id.txtdetailgender);
        txtdetailaddress = findViewById(R.id.txtdetailaddress);
        txtdetailpostal = findViewById(R.id.txtdetailpostal);
        txtdetailemail = findViewById(R.id.txtdetailemail);
        txtdetailphone = findViewById(R.id.txtdetailphone);
        txtdetailcardnumber = findViewById(R.id.txtdetailcardnumber);
        txtdetaildob = findViewById(R.id.txtdetaildob);

        btneditprofile = findViewById(R.id.btneditprofile);
        btntransfer = findViewById(R.id.btntransfer);

        showalluserData();



        btntransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminDetailActivity.this,SendMoney.class);
                startActivity(intent);

            }
        });
    }


    public void showalluserData(){
        Intent intent = getIntent();

        String nameuser = intent.getStringExtra("name");
        String emailuser = intent.getStringExtra("email");
        String usernameuser = intent.getStringExtra("username");
        String passworduser = intent.getStringExtra("password");
        String detailname = intent.getStringExtra("customerName");
        String detailgender = intent.getStringExtra("customerGender");
        String detailaddress = intent.getStringExtra("customerAddress");
        String detailpostal = intent.getStringExtra("customerPostalCode");
        String detailemail = intent.getStringExtra("email");
        String detailphone = intent.getStringExtra("phone");
        String detailcardnumber = intent.getStringExtra("cardNumber");
        String detaildbo = intent.getStringExtra("customerDoB");

        txtdetailname.setText(detailname);
        txtdetailgender.setText(detailgender);
        txtdetailaddress.setText(detailaddress);
        txtdetailpostal.setText(detailpostal);
        txtdetailemail.setText(detailemail);
        txtdetailphone.setText(detailphone);
        txtdetailcardnumber.setText(detailcardnumber);
        txtdetaildob.setText(detaildbo);

    }

}