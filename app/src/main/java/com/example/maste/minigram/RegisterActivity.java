package com.example.maste.minigram;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.username)
    EditText user;
    @BindView(R.id.password)
    TextInputEditText pass;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.register)
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.length() == 0 || pass == null || email.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    ParseUser create = new ParseUser();
                    create.setUsername(user.getText().toString());
                    create.setPassword(pass.getText().toString());
                    create.setEmail(email.getText().toString());
                    create.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(getApplicationContext(), "Account successfully created", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Account could not be created", Toast.LENGTH_SHORT).show();
                                Log.e("GramAccount", e.getLocalizedMessage());
                            }
                        }
                    });
                }
            }
        });
    }
}
