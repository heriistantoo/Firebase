package com.example.firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "heriistanto";
    private DatabaseReference database;

    private EditText etNama, etEmail, etDesk;
    private ProgressDialog loading;
    private Button  btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance().getReference();

        etNama = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etDesk = findViewById(R.id.et_desk);

        btn_save = findViewById(R.id.btn_save);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Snama = etNama.getText().toString();
                String Semail = etEmail.getText().toString();
                String Sdesk = etDesk.getText().toString();


                    // perintah save
                    if (Snama.equals("")) {
                        etNama.setError("Silahkan masukkan nama");
                        etNama.requestFocus();
                    } else if (Semail.equals("")) {
                        etEmail.setError("Silahkan masukkan email");
                        etEmail.requestFocus();
                    } else if (Sdesk.equals("")) {
                        etDesk.setError("Silahkan masukkan desk");
                        etDesk.requestFocus();
                    } else {
                        loading = ProgressDialog.show(MainActivity.this,
                                null,
                                "Please wait...",
                                true,
                                false);

                        submitUser(new Requests(
                                Snama.toLowerCase(),
                                Semail.toLowerCase(),
                                Sdesk.toLowerCase()));
                    }
            }
        });

    } //onCreate

    private void submitUser(Requests requests) {
        database.child("Request") //nama child, semua data dimasukan ke child ini. child = nama table di nosql
                .push() //id random tiap data
                .setValue(requests) //ini method insert data fierbase
                .addOnSuccessListener(this, new OnSuccessListener<Void>() { //ketika success
                    @Override
                    public void onSuccess(Void aVoid) {

                        loading.dismiss();

                        etNama.setText(""); //kolom dikosongi
                        etEmail.setText("");
                        etDesk.setText("");

                        Toast.makeText(MainActivity.this,
                                "Data Berhasil ditambahkan",
                                Toast.LENGTH_SHORT).show();

                    }

                });
    }


} //MainClass