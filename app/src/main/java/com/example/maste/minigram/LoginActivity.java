package com.example.maste.minigram;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    TextInputEditText password;
    @BindView(R.id.submit)
    Button login;
    @BindView(R.id.register)
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        if (ParseUser.getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (e == null) {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        } else if (e.getCode() == ParseException.USERNAME_MISSING || e.getCode() == ParseException.PASSWORD_MISSING) {
                            Log.e("LoginError", e.getLocalizedMessage());
                            Toast.makeText(LoginActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        } else if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                            Log.e("LoginError", e.getLocalizedMessage());
                            Toast.makeText(LoginActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        } else if (e.getCode() >= ParseException.OTHER_CAUSE) {
                            Log.e("LoginError", "There was a mighty error logging in");
                            Log.e("LoginError", Integer.toString(e.getCode()));
                            Log.e("LoginError", e.getLocalizedMessage());
                        }
                    }
                });
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }
}
