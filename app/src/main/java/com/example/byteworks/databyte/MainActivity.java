package com.example.byteworks.databyte;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText fname, lname, email, phone;
    Button saveBtn;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fname = (EditText) findViewById(R.id.txtName);
        lname = (EditText) findViewById(R.id.txtLastName);
        email = (EditText) findViewById(R.id.txtEmail);
        phone = (EditText) findViewById(R.id.txtPhone);
        saveBtn = (Button) findViewById(R.id.btnSave);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            DbHandler dbHandler = new DbHandler(MainActivity.this);

            @Override
            public void onClick(View v) {
                String firstname = fname.getText().toString() + "\n";
                String lastname = lname.getText().toString();
                String mEmail = email.getText().toString();
                String mphone = phone.getText().toString();


                if ((firstname.length() <= 0 || firstname.length() < 15) && (lastname.length() <= 0 || lastname.length() < 15) && mphone.length() == 11 && (mEmail.contains("@"))) {


                    dbHandler.insertUserDetails(firstname, lastname, mEmail, mphone);
                    intent = new Intent(MainActivity.this, DetailsActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Details Inserted Successfully!", Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(MainActivity.this, "Invalid Entry!", Toast.LENGTH_SHORT).show();
                }
            }
            });
        }
    }



