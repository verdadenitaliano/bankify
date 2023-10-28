package com.example.bankify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminHomeActivity extends AppCompatActivity {

    TextView admLogout;
    EditText searchName,searchCardNum, searchPhone;
    Button btnSearch, btnDetailsAccount;
    ListView lstSearchResult;
    ArrayList<String> itemList;
    ArrayAdapter<String> adapter;
    FirebaseDatabase database;

    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        reference = FirebaseDatabase.getInstance().getReference("accountDetails");

        searchName = findViewById(R.id.searchName);
        searchCardNum = findViewById(R.id.searchCardNum);
        searchPhone = findViewById(R.id.searchPhone);
        btnSearch = findViewById(R.id.btnSearch);
        btnDetailsAccount = findViewById(R.id.btnDetailsAccount);
        admLogout = findViewById(R.id.admLogout);

        lstSearchResult = findViewById(R.id.lstSearchResult);

        itemList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,itemList);

        lstSearchResult.setAdapter(adapter);

        btnDetailsAccount.setVisibility(View.GONE);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullname = searchName.getText().toString();
                String cardnumber = searchCardNum.getText().toString();
                String phonenumber = searchPhone.getText().toString();
                String contact = "Full Name: " + fullname + "\n";
                contact += "Card Number: " + cardnumber + "\n";
                contact += "Phone Number: " + phonenumber;

                if(!fullname.isEmpty() && !cardnumber.isEmpty() && !phonenumber.isEmpty() ){

                    itemList.add(contact);
                    adapter.notifyDataSetChanged();
                    searchName.setText("");
                    searchCardNum.setText("");
                    searchPhone.setText("");
                    btnDetailsAccount.setVisibility(View.VISIBLE);


                }else {
                    Toast.makeText(AdminHomeActivity.this, "please dont empty the field", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDetailsAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkUSer();}
        });

        admLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


    public void checkUSer(){
        String fullname = searchName.getText().toString();
        String cardnumber = searchCardNum.getText().toString();
        String phonenumber = searchPhone.getText().toString();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("accountDetails");
        Query checkuserData = reference.orderByChild("cardNumber").equalTo(cardnumber);
        checkuserData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String passFromDatabase = snapshot.child(cardnumber).child("password").getValue(String.class);
                    if(passFromDatabase.equals(fullname)){
                        String cardNumberdb = snapshot.child(cardnumber).child("cardNumber").getValue(String.class);
                        String customerAddressdb = snapshot.child(cardnumber).child("customerAddress").getValue(String.class);
                        String customerDoBdb = snapshot.child(cardnumber).child("customerDoB").getValue(String.class);
                        String customerGenderdb = snapshot.child(cardnumber).child("customerGender").getValue(String.class);
                        String customerNamedb = snapshot.child(cardnumber).child("customerName").getValue(String.class);
                        String customerPostalCodedb = snapshot.child(cardnumber).child("customerPostalCode").getValue(String.class);
                        String emaildb = snapshot.child(cardnumber).child("email").getValue(String.class);
                        String phonedb = snapshot.child(cardnumber).child("phone").getValue(String.class);


                        //Model model = new Mode()
                        Intent intent = new Intent(AdminHomeActivity.this,AdminDetailActivity.class);
                        intent.putExtra("cardNumber",cardNumberdb);
                        intent.putExtra("customerAddress",customerAddressdb);
                        intent.putExtra("customerDoB",customerDoBdb);
                        intent.putExtra("customerGender",customerGenderdb);
                        intent.putExtra("customerName",customerNamedb);
                        intent.putExtra("customerPostalCode",customerPostalCodedb);
                        intent.putExtra("email",emaildb);
                        intent.putExtra("phone",phonedb);

                        Toast.makeText(AdminHomeActivity.this, "Ok confirm", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }else{
                        Toast.makeText(AdminHomeActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(AdminHomeActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


    }
}