package com.example.bankify;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bankify.adapter.clientAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class AdminHomeActivity extends AppCompatActivity {


    RecyclerView mRecycler;
    clientAdapter mAdapter;
    FirebaseFirestore mFirestore;

    SearchView searchview;
    Query query;
    TextView btnLogout;



    private String searchResult = "";

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        mFirestore = FirebaseFirestore.getInstance();
        searchview = findViewById(R.id.searchview);
        btnLogout = findViewById(R.id.btnlogout);

        setUpRecyclerView();
        search_view();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void setUpRecyclerView() {
        mRecycler = findViewById(R.id.recyclerviewsigleN);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
//      Query query = mFirestore.collection("pet").whereEqualTo("id_user", mAuth.getCurrentUser().getUid());
        query = mFirestore.collection("accountDetails");

        FirestoreRecyclerOptions<Model> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Model>().setQuery(query, Model.class).build();

        mAdapter = new clientAdapter(firestoreRecyclerOptions);
        mAdapter.notifyDataSetChanged();
        mRecycler.setAdapter(mAdapter);
    }

    private void search_view(){
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                textSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                textSearch(s);
                return false;
            }
        });

    }


    public void textSearch(String s){
        FirestoreRecyclerOptions<Model> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Model>()
                        .setQuery(query.orderBy("customerName")
                                .startAt(s).endAt(s+"~"), Model.class).build();
        mAdapter = new clientAdapter(firestoreRecyclerOptions);
        mAdapter.startListening();
        mRecycler.setAdapter(mAdapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }

}