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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class RegisterActivity extends AppCompatActivity {

    private EditText cNum, regPCode, regPass, regCPass, expMonth, expYear;
    private Button btnRegister, btnLogin;
    String cardNumberCH,postalCodeCH,hashPassword;

    String passValidation = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    private FirebaseFirestore db;


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
        cardNumberCH = cNum.getText().toString().trim();
        postalCodeCH = regPCode.getText().toString().trim();
        db = FirebaseFirestore.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
               startActivity(intent);
               finish();
            }

        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();

            }
        });
    }


    private void checkIfCardNumberIsRegisteredInUsersCollection(final String cardNumber, final String postalCode) {
        db.collection("users")
                .whereEqualTo("cardNumber", cardNumber)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().isEmpty()) {
                                // Card number is not registered in 'users' collection
                                // You can proceed with other validations
                                // If all validations pass, you can proceed with registration
                                Toast.makeText(RegisterActivity.this, "Validated", Toast.LENGTH_SHORT).show();
                            } else {
                                // Card number is already registered in 'users' collection
                                // Handle this case, e.g., show an error message
                                Toast.makeText(RegisterActivity.this, "Card already registered", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RegisterActivity.this, "Failed to validated", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void validateCardNumberExistence(final String cardNumber, final String postalCode) {
        db.collection("accountDetails")
                .whereEqualTo("cardNumber", cardNumber)
                .whereEqualTo("customerPostalCode", postalCode)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().isEmpty()) {
                                // Card number doesn't exist in 'accountDetails'
                                // You can proceed with other validations
                                Toast.makeText(RegisterActivity.this, "Please create an account", Toast.LENGTH_SHORT).show();
                                //
                            } else {
//                                Toast.makeText(RegisterActivity.this, "Registered", Toast.LENGTH_SHORT).show();
                                // Card number and postal code match an existing document
                                checkIfCardNumberIsRegisteredInUsersCollection(cardNumber, postalCode);
                            }
                        } else {
                            Toast.makeText(RegisterActivity.this, "Unable to validate", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void registerUser() {
        String cardNumber = cNum.getText().toString().trim();
        String postalCode = regPCode.getText().toString().trim();
        String password = regPass.getText().toString().trim();
        String confPassword = regCPass.getText().toString().trim();

        // Perform the required validations

        // Check if all fields are filled
        if (cardNumber.isEmpty() || password.isEmpty() || postalCode.isEmpty() || confPassword.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate 'cardNumber' format (16 digits)
        if (!cardNumber.matches("\\d{16}")) {
            Toast.makeText(RegisterActivity.this, "Invalid card number format", Toast.LENGTH_LONG).show();
            return;
        }

        // Validate password format (you can use a separate method for this)
        if (password.length() < 8 && password != passValidation) {
            Toast.makeText(RegisterActivity.this, "Invalid password format", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate 'cardNumber' existence and 'customerPostalCode' match
        validateCardNumberExistence(cardNumber, postalCode);
        createNewUser(cardNumber,password);
    }

    private void createNewUser(String cardNumber, String hashedPassword) {
//        cardNumber = cNum.getText().toString().trim();
//        hashedPassword  = regPass.getText().toString().trim();
        User user = new User(cardNumber, hashedPassword); // Create a User object

        // Reference to the "users" collection in Firestore
        CollectionReference usersCollection = db.collection("users");

        usersCollection
                .add(user) // or you can specify a document ID using .document("customDocumentID").set(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(RegisterActivity.this, "Card registered", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    
}




