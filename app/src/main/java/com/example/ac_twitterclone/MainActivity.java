package com.example.ac_twitterclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtEmail, edtUserName, edtPassword;
    private Button btnLogin, btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Sign Up");

        edtEmail = findViewById(R.id.edtEnterEmail);
        edtUserName = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtEnterPassword);

        edtPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    onClick(btnSignUp);
                }
                return false;
            }
        });

        btnLogin = findViewById(R.id.btnLogIn);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null) {
//            ParseUser.getCurrentUser().logOut();
            transitionToSocialMediaActivity();
        }

//        // Save the current Installation to Back4App
//        ParseInstallation.getCurrentInstallation().saveInBackground();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogIn :

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.btnSignUp :

                if (edtEmail.getText().toString().equals("")
                        || edtUserName.getText().toString().equals("")
                        || edtPassword.getText().toString().equals("")) {
                    FancyToast.makeText(MainActivity.this, "Email, Username, Password is required"
                            , FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();

                } else {
                    final ParseUser appUser = new ParseUser();
                    appUser.setEmail(edtEmail.getText().toString());
                    appUser.setUsername(edtUserName.getText().toString());
                    appUser.setPassword(edtPassword.getText().toString());

                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Signing Up " + edtUserName.getText().toString());
                    progressDialog.show();

                    appUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                FancyToast.makeText(MainActivity.this, appUser.getUsername() +
                                        " is signed up", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();

                                transitionToSocialMediaActivity();

                            } else {
                                FancyToast.makeText(MainActivity.this, "There is an Error: "
                                        + e.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                            }

                            progressDialog.dismiss();
                        }
                    });
                }

                break;
        }
    }

    public void rootLayoutIsTapped(View view){
        try{
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void transitionToSocialMediaActivity(){

        Intent intent = new Intent(MainActivity.this,SocialMediaActivity.class);
        startActivity(intent);
        finish();

    }
}
