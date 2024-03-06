package com.example.lab1_2_ph34723;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lab1_2_ph34723.SignInEmail.Login_Email;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    Button btnLogout;
    FirebaseFirestore db;


    public static final String NAME = "name";
    public static final String STATE = "state";
    public static final String COUNTRY = "country";
    public static final String CAPITAL = "capital";
    public static final String POPULATION = "population";
    public static final String REGIONS = "regions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        btnLogout = findViewById(R.id.btnLogout);


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intent = new Intent(getApplicationContext(), SignInOption.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void dialog_them() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_add, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextInputEditText edtName, edtState, edtCountry, edtCapital, edtPopulation, edtRegions;

        edtName = view.findViewById(R.id.edtName);
        edtState = view.findViewById(R.id.edtState);
        edtCountry = view.findViewById(R.id.edtCountry);
        edtCapital = view.findViewById(R.id.edtCapital);
        edtPopulation = view.findViewById(R.id.edtPopulation);
        edtRegions = view.findViewById(R.id.edtRegions);

        Button btnAdd = view.findViewById(R.id.btnThem);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String state = edtState.getText().toString();
                String country = edtCountry.getText().toString();
                String capital = edtCapital.getText().toString();
                String population = edtPopulation.getText().toString();
                String regions = edtRegions.getText().toString();


                if (name.isEmpty() || state.isEmpty() || country.isEmpty() || capital.isEmpty() || population.isEmpty() || regions.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                    return;
                }


            }
        });
    }

    private void ghiDuLieu() {
        Toast.makeText(this, "Đã chạy vào ghi dữ liệu", Toast.LENGTH_SHORT).show();
        CollectionReference cities = db.collection("cities");

        Map<String, Object> data1 = new HashMap<>();
        data1.put(NAME, "San Francisco");
        data1.put(STATE, "CA");
        data1.put(COUNTRY, "USA");
        data1.put(CAPITAL, false);
        data1.put(POPULATION, 860000);
        data1.put(REGIONS, Arrays.asList("west_coast", "norcal"));
        cities.document("SF").set(data1);

        Map<String, Object> data2 = new HashMap<>();
        data2.put(NAME, "Los Angeles");
        data2.put(STATE, "CA");
        data2.put(COUNTRY, "USA");
        data2.put(CAPITAL, false);
        data2.put(POPULATION, 3900000);
        data2.put(REGIONS, Arrays.asList("west_coast", "socal"));
        cities.document("LA").set(data2);

        Map<String, Object> data3 = new HashMap<>();
        data3.put(NAME, "Washington D.C.");
        data3.put(STATE, null);
        data3.put(COUNTRY, "USA");
        data3.put(CAPITAL, true);
        data3.put(POPULATION, 680000);
        data3.put(REGIONS, Arrays.asList("east_coast"));
        cities.document("DC").set(data3);
    }

    private void docDulieu() {
        Log.d("MainActivity", "docDulieu: da chạy vào day");
        DocumentReference docRef = db.collection("cities").document("SF");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("HomeActivity", "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d("HomeActivity", "No such document");
                    }
                } else {
                    Log.d("HomeActivity", "get failed with ", task.getException());
                }
            }
        });
    }
}