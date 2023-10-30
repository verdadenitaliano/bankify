package com.example.bankify;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    EditText cardNumber,sessionPassword;
    Button btnRegister,btnLogin;
    Spinner spn;

    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        cardNumber = findViewById(R.id.txtcardNumber);
        sessionPassword = findViewById(R.id.txtSessionPassword);
        btnRegister = findViewById(R.id.registerbtn);
        btnLogin = findViewById(R.id.btnLogin);

        spn = findViewById(R.id.spn);

        ArrayList<String> services = new ArrayList<String>();
        services.add("Client");
        services.add("Admin");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,services);

        spn.setAdapter(adapter);
        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(LoginActivity.this,item + " service", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(LoginActivity.this, "Select the service", Toast.LENGTH_SHORT).show();

            }
        });
        db = FirebaseFirestore.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);

            }

        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateInput();
                loginUSer();
            }
        });
    }
    
    private void validateInput(){

        String cardNum = cardNumber.getText().toString().trim();
        String password = sessionPassword.getText().toString().trim();
       


        if (cardNum.isEmpty() || password.isEmpty() ) {
            Toast.makeText(this, "Enter credentials", Toast.LENGTH_SHORT).show();
            return;
        }
        if (cardNum.matches("\\d{16}")) {
//            Toast.makeText(this, "Invalid card number", Toast.LENGTH_SHORT).show();
        } else {
            // Input does not contain 16 digits
            Toast.makeText(this, "Enter valid credentials", Toast.LENGTH_SHORT).show();
        }
        
    }

    private void loginUSer() {
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            boolean isLoginSuccessful = false; //  if login is successful
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                String a = doc.getString("cardNumber");
                                String b = doc.getString("password");
                                String cardNum = cardNumber.getText().toString().trim();
                                String password = sessionPassword.getText().toString().trim();
                                if (a.equalsIgnoreCase(cardNum) && b.equals(password)) {
                                    isLoginSuccessful = true;
                                    break; // login is successful
                                }
                            }

                            if (isLoginSuccessful) {
                                Toast.makeText(LoginActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                                Intent home = new Intent(LoginActivity.this, home.class);
                                startActivity(home);
                            } else {
                                Toast.makeText(LoginActivity.this, "Cannot login, incorrect Email or Password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Cannot login, something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



}

