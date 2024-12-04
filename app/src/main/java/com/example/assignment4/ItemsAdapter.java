package com.example.assignment4;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ItemsAdapter extends FirebaseRecyclerAdapter<Item, ItemsAdapter.ItemsViewHolder>{

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ItemsAdapter(@NonNull FirebaseRecyclerOptions<Item> options) {
        super(options);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull ItemsViewHolder holder, int position, @NonNull Item model) {
        holder.tvName.setText(model.getName());
        holder.tvQuantity.setText("Quantity:" + Integer.toString(model.getQuantity()));
        holder.tvPrice.setText("PKR " + Float.toString(model.getPrice()));
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

        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView .findViewById(R.id.tvItemName);
            tvQuantity = itemView .findViewById(R.id.tvItemQuantity);
            tvPrice = itemView .findViewById(R.id.tvItemPrice);

        }
    }

}
