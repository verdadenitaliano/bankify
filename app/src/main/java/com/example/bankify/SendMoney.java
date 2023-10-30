package com.example.bankify;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class SendMoney extends AppCompatActivity implements View.OnClickListener {

    EditText amountEditText, fullNameEditText, confirmAmountEditText,
            accountNumberEditText, confirmAccountEditText;
    Button sendMoneyButton;
    private float balance, recpientBalance;
    float amount;
    String cardnumb, recpientCardnumb,logedDocid,recpientDocid;
    FirebaseFirestore database;
    DocumentReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money);
        initialize();
    }

    private void initialize() {
        //Text Views
        amountEditText = findViewById(R.id.amountEditText);
        confirmAmountEditText = findViewById(R.id.confirmAmountEditText);
        accountNumberEditText = findViewById(R.id.accountNumberEditText);
        confirmAccountEditText = findViewById(R.id.confirmAccountEditText);
        fullNameEditText = findViewById(R.id.fullNameEditText);
        //Buttons
        sendMoneyButton = findViewById(R.id.sendMoneyButton);
        sendMoneyButton.setOnClickListener(this);
        //Database reference
        database = FirebaseFirestore.getInstance();
        // getting extras
        cardnumb = getIntent().getStringExtra("cardnum");
        balance = getIntent().getFloatExtra("balance", 0);
        logedDocid = getIntent().getStringExtra("docid");
    }

    @Override
    public void onClick(View v) {
        // convert text to float
        float confirmAmount;
        amount = Float.parseFloat(amountEditText.getText().toString());
        confirmAmount = Float.parseFloat(confirmAmountEditText.getText().toString());
        // validations
        if (amountEditText.getText().length() == 0 ||
                confirmAccountEditText.getText().length() == 0 ||
                accountNumberEditText.getText().length() == 0 ||
                confirmAccountEditText.getText().length() == 0 ||
                fullNameEditText.getText().length() == 0) {
            Toast.makeText(this, "Please fill all fields before submitting the form", Toast.LENGTH_SHORT).show();
        }
        if (confirmAmount != amount) {
            Toast.makeText(this, "The Amounts Entered Do not match", Toast.LENGTH_SHORT).show();
        }
//        if (accountNumberEditText.getText().toString().trim!= confirmAccountEditText.getText().toString().trim)) {
//            Toast.makeText(this, "The Account Numbers Entered Do not match", Toast.LENGTH_SHORT).show();
//        }
        if (amount > balance) {
            Toast.makeText(this, "Insufficient funds", Toast.LENGTH_SHORT).show();
        } else {
            recpientCardnumb = accountNumberEditText.getText().toString();
            getRecipentBal();
        }
    }

    private void getRecipentBal() {
        database.collection("accountDetails").whereEqualTo("cardNumber", recpientCardnumb).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        Log.d(TAG, " DOC DATA : " + document.getData());
                        document.getId();
                        recpientBalance = Float.parseFloat(document.get("initialBalance").toString());
                        recpientDocid = document.getId();
                        makeTransaction();
                        break;
                    }
                } else {
                    Toast.makeText(SendMoney.this, "This Account number does not exist", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void makeTransaction() {
        // validate that the currently logged in user is not trying to send money to themselves
        if (recpientCardnumb == cardnumb) {
            Toast.makeText(SendMoney.this, "You Can not transfer money to yourself", Toast.LENGTH_SHORT).show();
        }
        // create new balance for user sending money
        balance = balance - amount;
        //create new balance for user receiving money
        recpientBalance = recpientBalance + amount;
        // update logged user balance
        reference = database.collection("accountDetails").document(logedDocid);
        reference.update("initialBalance",balance).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG, "onSuccess: Value sucessfully updated");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: error");
            }
        });

        // update recipient user balance
        reference = database.collection("accountDetails").document(recpientDocid);
        reference.update("initialBalance",recpientBalance).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG, "onSuccess: Value sucessfully updated");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: error");
            }
        });
        Toast.makeText(SendMoney.this,"Money Sent",Toast.LENGTH_SHORT).show();
        this.finish();
    }
}
