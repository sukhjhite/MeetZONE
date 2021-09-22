package com.sukhdeep75.meetzone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {
    EditText login_email,login_password;
    Button login,create_acc;
    FirebaseAuth auth;
    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth=FirebaseAuth.getInstance();
        database=FirebaseFirestore.getInstance();

        create_acc=findViewById(R.id.create_acc);
        login=findViewById(R.id.login);

        login_email=findViewById(R.id.login_email);
        login_password=findViewById(R.id.login_password);
        create_acc.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(Login.this,Create_acc.class));
             }
         });

        login.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String email,pass;
                 email=login_email.getText().toString();
                 pass=login_password.getText().toString();
                 if(email.isEmpty() || pass.isEmpty()){
                     Toast.makeText(Login.this, "Email and Password fields can't be empty", Toast.LENGTH_SHORT).show();
                 }
                 else {
                     auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                         @Override
                         public void onComplete(@NonNull Task<AuthResult> task) {
                             if (task.isSuccessful()){
                                 startActivity(new Intent(Login.this,DashboardActivity.class));
                             }else{
                                 Toast.makeText(Login.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                             }
                         }
                     });
                 }
             }
         });



    }
}