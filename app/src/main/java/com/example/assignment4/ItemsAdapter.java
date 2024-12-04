package com.example.assignment4;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

public class ItemsAdapter extends FirebaseRecyclerAdapter<Item, ItemsAdapter.ItemsViewHolder>{
    Context context;
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ItemsAdapter(@NonNull FirebaseRecyclerOptions<Item> options, Context context) {
        super(options);
        this.context = context;

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull ItemsViewHolder holder, int position, @NonNull Item model) {
        holder.tvName.setText(model.getName());
        holder.tvQuantity.setText("Quantity:" + Integer.toString(model.getQuantity()));
        holder.tvPrice.setText("PKR " + Float.toString(model.getPrice()));

        holder.dltBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("products")
                        .child(getRef(position).getKey())
                        .removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_item_design, parent, false);
        return new ItemsViewHolder(v);
    }

    public class ItemsViewHolder extends RecyclerView.ViewHolder{

        TextView tvName, tvPrice, tvQuantity;
        Button dltBtn;
        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView .findViewById(R.id.tvItemName);
            tvQuantity = itemView .findViewById(R.id.tvItemQuantity);
            tvPrice = itemView .findViewById(R.id.tvItemPrice);
            dltBtn = itemView.findViewById(R.id.btn_delete);

        }
    }

}
