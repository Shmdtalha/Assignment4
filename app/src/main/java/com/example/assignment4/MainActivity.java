package com.example.assignment4;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton btnAddNewItem;
    TextView tvItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("FirebaseInit", "Firebase initialized successfully");
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        FirebaseApp.initializeApp(this);
        Log.d("FirebaseInit", "Firebase initialized successfully");
        btnAddNewItem = findViewById(R.id.btnAddNewItem);
        tvItem = findViewById(R.id.tvItem);

        btnAddNewItem.setOnClickListener(l->{

            View v = LayoutInflater.from(MainActivity.this)
                    .inflate(R.layout.add_new_item_dialog_design, null);
            TextView tvTimeStamp = v.findViewById(R.id.tvTimeStamp);
            TextInputEditText etName = v.findViewById(R.id.etName);
            TextInputEditText etQuantity = v.findViewById(R.id.etQuantity);
            TextInputEditText etPrice = v.findViewById(R.id.etPrice);

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            Date date = new Date();
            tvTimeStamp.setText(formatter.format(date));

            AlertDialog.Builder addItemDialog = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Creating New Item")
                    .setView(v)
                    .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String productName = etName.getText().toString().trim();
                            String quantityText = etQuantity.getText().toString().trim();
                            String priceText = etPrice.getText().toString().trim();

                            if (productName.isEmpty() || quantityText.isEmpty() || priceText.isEmpty()) {
                                Toast.makeText(MainActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            try {
                                int quantity = Integer.parseInt(quantityText);
                                float price = Float.parseFloat(priceText);

                                HashMap<String, Object> data = new HashMap<>();
                                data.put("name", "idk");
                                data.put("quantity", 10);
                                data.put("price", 10);

                                Log.d("Firebase", "Attempting to save product");
                                FirebaseDatabase.getInstance().getReference().child("Products").push()
                                        .setValue(data)
                                        .addOnCompleteListener(task -> {
                                            if (task.isSuccessful()) {
                                                Log.d("Firebase", "Product saved successfully");
                                                Toast.makeText(MainActivity.this, "Product Created", Toast.LENGTH_LONG).show();
                                            } else {
                                                Log.e("Firebase", "Failed to save product", task.getException());
                                                Toast.makeText(MainActivity.this, "Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });

                            } catch (NumberFormatException e) {
                                Toast.makeText(MainActivity.this, "Invalid quantity or price", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }
                    );

            addItemDialog.create();
            addItemDialog.show();

        });
    }
}