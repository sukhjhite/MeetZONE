package com.sukhdeep75.meetzone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Create_acc extends AppCompatActivity {

    EditText create_name,create_email,create_password;
    Button create_new_acc,already_acc;
    FirebaseAuth auth;
    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_acc);

        already_acc=findViewById(R.id.already_acc);
        create_name=findViewById(R.id.name_box);
        create_email=findViewById(R.id.create_email);
        create_password=findViewById(R.id.create_password);
        create_new_acc=findViewById(R.id.create_new_acc);
        auth = FirebaseAuth.getInstance();
        database=FirebaseFirestore.getInstance();

        already_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Create_acc.this,Login.class));
            }
        });

        create_new_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email,pass,name;
                email = create_email.getText().toString();
                pass = create_password.getText().toString();
                name = create_name.getText().toString();
                if(email.isEmpty() || pass.isEmpty() || name.isEmpty()){
                    Toast.makeText(Create_acc.this, "Some fields are empty", Toast.LENGTH_SHORT).show();
                }else{

                User user = new User();
                user.setEmail(email);
                user.setPass(pass);
                user.setName(name);
                auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(Create_acc.this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            database.collection("Users")
                                    .document().set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    startActivity(new Intent(Create_acc.this,Login.class));
                                }
                            });
                            Toast.makeText(Create_acc.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(Create_acc.this, task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }}
        });
    }
}