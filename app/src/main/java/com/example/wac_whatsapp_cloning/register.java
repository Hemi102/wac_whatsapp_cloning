package com.example.wac_whatsapp_cloning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.concurrent.TimeUnit;

public class register extends AppCompatActivity {

    private EditText userEmail,userPassword,userName;
    private Button userSignup,userLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userEmail=findViewById(R.id.userEmail);
        userPassword=findViewById(R.id.userPassword);
        userSignup=findViewById(R.id.signup);
        userName=findViewById(R.id.userName);
        userLogin=findViewById(R.id.login);
        if(ParseUser.getCurrentUser()!=null)
        {transeferToHomePage();}


        userSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(userEmail.getText().toString().equals("")
                || userPassword.getText().toString().equals("")||
                userName.getText().toString().equals(""))
                {
                    FancyToast.makeText(register.this,
                            "Incorrect Email Or Password",
                            Toast.LENGTH_SHORT,FancyToast.ERROR,
                            true).show();

                    return;
                }
                else
                {
                    final ParseUser parseUser=new ParseUser();
                    parseUser.setUsername(userName.getText().toString());
                    parseUser.setEmail(userEmail.getText().toString());
                    parseUser.setPassword(userPassword.getText().toString());
                    final ProgressDialog progressDialog=new ProgressDialog(register.this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                    parseUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e==null)
                            {
                                FancyToast.makeText(register.this,ParseUser.getCurrentUser().getEmail()+"",
                                        Toast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();
                                finish();
                                transeferToHomePage();

                            }
                            else
                            {
                                FancyToast.makeText(register.this,e.getMessage()+"",
                                        Toast.LENGTH_SHORT,FancyToast.ERROR,true).show();
                            }

                            progressDialog.dismiss();
                        }
                    });
                }
            }
        });

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                transeferToLoginPage();
            }
        });




    }
    private void transeferToLoginPage() {
        Intent intent=new Intent(this,loginPage.class);
        startActivity(intent);
        finish();
    }

    private void transeferToHomePage() {
        Intent intent=new Intent(register.this,homePage.class);
        startActivity(intent);
        finish();
    }

}
