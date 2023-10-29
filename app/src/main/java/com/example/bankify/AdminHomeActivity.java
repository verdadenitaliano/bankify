package com.example.bankify;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class AdminHomeActivity extends AppCompatActivity {

    FirebaseFirestore db;
    EditText searchName, searchCardNum, searchPhone;
    TextView searchresulttextview;
    Button btnSearch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        db = FirebaseFirestore.getInstance();

        searchName = findViewById(R.id.searchName);
        searchCardNum = findViewById(R.id.searchCardNum);
        searchPhone = findViewById(R.id.searchPhone);
        searchresulttextview = findViewById(R.id.searchresulttextview);
        btnSearch = findViewById(R.id.btnSearch);
        getinformationdb();
    }

    public void getinformationdb() {
        db.collection("accountDetails").document("VnxZJJt9q7ssLNJEkJpL").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String name = documentSnapshot.getString("customerName");
                    String cardNumber = documentSnapshot.getString("cardNumber");
                    String phone = documentSnapshot.getString("phone");

                    searchresulttextview.setText("Nombre: " + name + "\n" + "Card Number: " + cardNumber + "\n" + "Phone: " + phone);


                }
            }
        });
    }

}