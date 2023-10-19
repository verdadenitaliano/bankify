package com.example.bankify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText cNum,regPCode,regPass,regCPass, expMonth,expYear ;
    private Button btnRegister,btnLogin;

    String passValidation = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    FirebaseDatabase database;
    DatabaseReference reference;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        cNum = findViewById(R.id.regCardNumber);
        regPCode = findViewById(R.id.regPostalCode);
        regPass = findViewById(R.id.regPassword);
        regCPass = findViewById(R.id.regConfPass);
        btnRegister = findViewById(R.id.btnRegister); // on click btn register opens new view with information in the DB
        btnLogin = findViewById(R.id.btnLog);
        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }

        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");
                String cardNum = cNum.getText().toString().trim();
                String postalCode = regPCode.getText().toString().trim();
                String passworduser = regPass.getText().toString().trim();
                String confPassw = regCPass.getText().toString().trim();

                /*if (cardNum.isEmpty() || postalCode.isEmpty() || passworduser.isEmpty() || confPassw.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Model model = new Model(cardNum, postalCode, passworduser);
                    reference.child(cardNum).setValue(model);
                    Toast.makeText(RegisterActivity.this, "Succesfully signup", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, home.class);
                    startActivity(intent);
                    finish();
                }*/
            }
        });

    }
}

