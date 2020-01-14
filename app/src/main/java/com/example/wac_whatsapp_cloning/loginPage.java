package com.example.wac_whatsapp_cloning;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class loginPage extends AppCompatActivity {

    private EditText loginEmail,loginPassword;
    private Button loginUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        loginEmail=findViewById(R.id.loginEmail);
        loginPassword=findViewById(R.id.loginPassword);
        loginUser=findViewById(R.id.loginUser);

        loginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logedInUser();
            }
        });


    }

    private void logedInUser() {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        ParseUser.logInInBackground(loginEmail.getText().toString(),
                loginPassword.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(e==null && user!=null)
                        {
                            FancyToast.makeText(loginPage.this,
                                    user.getUsername()+"",
                                    Toast.LENGTH_SHORT,FancyToast.SUCCESS,
                                    true).show();
                            transferToHomePage();
                        }
                        else
                        {
                            FancyToast.makeText(loginPage.this,
                                    ParseUser.getCurrentUser().getUsername()+"",
                                    Toast.LENGTH_SHORT,FancyToast.SUCCESS,
                                    true).show();
                        }


                    }
                });
    }

    private void transferToHomePage() {
        Intent intent=new Intent(this,homePage.class);
        startActivity(intent);
        finish();

    }
}
