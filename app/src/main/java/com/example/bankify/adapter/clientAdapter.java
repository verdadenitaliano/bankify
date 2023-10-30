package com.example.bankify.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bankify.AdminDetailActivity;
import com.example.bankify.Model;
import com.example.bankify.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

public class clientAdapter extends FirestoreRecyclerAdapter<Model, clientAdapter.ViewHolder> {
    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    Activity activity;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public clientAdapter(@NonNull FirestoreRecyclerOptions<Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull Model model) {
        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(viewHolder.getAbsoluteAdapterPosition());
        final String id = documentSnapshot.getId();
        viewHolder.nameView.setText(model.getCustomerName());
        viewHolder.cardView.setText(model.getCardNumber());
        viewHolder.phoneView.setText(model.getPhone());

        viewHolder.btnMod.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, AdminDetailActivity.class);
                i.putExtra("id_customer", id);
                activity.startActivity(i);
            }
        });

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewcard_customers, parent, false);
        activity = (Activity) parent.getContext();
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameView, cardView, phoneView;
        Button btnMod, btnreturn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameView = itemView.findViewById(R.id.customerNameDetails);
            cardView = itemView.findViewById(R.id.cardNumberDetails);
            phoneView = itemView.findViewById(R.id.phoneDetails);
            btnMod = itemView.findViewById(R.id.btnMod);
            btnreturn = itemView.findViewById(R.id.btnreturn);

        }
    }
}
