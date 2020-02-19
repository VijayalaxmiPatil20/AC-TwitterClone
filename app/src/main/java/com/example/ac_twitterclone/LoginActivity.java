package com.example.ac_twitterclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText edtLoginEmail,edtLoginPassword;
    private Button btnLoginActiviy, btnSignUpLoginActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Log In");

        edtLoginEmail = findViewById(R.id.edtLoginEmail);
        edtLoginPassword = findViewById(R.id.edtLoginPassword);

        edtLoginPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    onClick(btnLoginActiviy);
                }
                return false;
            }
        });

        btnLoginActiviy = findViewById(R.id.btnLoginActivity);
        btnSignUpLoginActivity = findViewById(R.id.btnSignUpLoginActivity);

        btnLoginActiviy.setOnClickListener(this);
        btnSignUpLoginActivity.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null) {
//            ParseUser.getCurrentUser().logOut();
            transitionToSocialMediaActivity();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLoginActivity:
                if (edtLoginEmail.getText().toString().equals("")
                        || edtLoginPassword.getText().toString().equals("")) {
                    FancyToast.makeText(LoginActivity.this, "Email and Password is required"
                            , FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();

                } else {
                    ParseUser.logInInBackground(edtLoginEmail.getText().toString(),
                            edtLoginPassword.getText().toString(),
                            new LogInCallback() {
                                @Override
                                public void done(ParseUser user, ParseException e) {
                                    if (user != null && e == null) {
                                        FancyToast.makeText(LoginActivity.this,
                                                user.getUsername() + " is Logged in successfully",
                                                FancyToast.LENGTH_SHORT, FancyToast.SUCCESS,
                                                true).show();

                                        transitionToSocialMediaActivity();

                                    } else {
                                        FancyToast.makeText(LoginActivity.this,
                                                " There is an Error: " + e.getMessage(),
                                                FancyToast.LENGTH_SHORT, FancyToast.ERROR,
                                                true).show();
                                    }

                                }
                            });
                }

                break;

            case R.id.btnSignUpLoginActivity:
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
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

        Intent intent = new Intent(LoginActivity.this,SocialMediaActivity.class);
        startActivity(intent);
        finish();

    }
}
