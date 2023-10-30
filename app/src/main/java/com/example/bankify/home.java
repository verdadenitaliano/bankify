package com.example.bankify;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class home extends AppCompatActivity {
    //TODO add list view things
    Button transferButton,updatebutton;
    TextView txtBalance;
    private float balance;
    String cardnumb,doccumentid;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        transferButton = findViewById(R.id.btnSendMoney);
        updatebutton = findViewById(R.id.btnUpdateBal);
        txtBalance = findViewById(R.id.lblAmount);
        cardnumb = getIntent().getStringExtra("cardnum");
        db = FirebaseFirestore.getInstance();
        getBalance();

        transferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to start the TransferActivity
                Intent intent = new Intent(home.this, SendMoney.class);
                intent.putExtra("cardnumb",cardnumb);
                intent.putExtra("balance",balance);
                intent.putExtra("docid",doccumentid);
                startActivity(intent);
            }
        });

        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBalance();
            }
        });
    }

    private void getBalance() {

        db.collection("accountDetails").whereEqualTo("cardNumber",cardnumb).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document: task.getResult()){
                        txtBalance.setText(document.get("initialBalance").toString()+"$");
                        balance = Float.parseFloat(document.get("initialBalance").toString());
                        doccumentid = document.getId();
                    }

                }
                else{
                    Log.d(TAG, "onComplete: error",task.getException());
                }

            }
        });
    }
}