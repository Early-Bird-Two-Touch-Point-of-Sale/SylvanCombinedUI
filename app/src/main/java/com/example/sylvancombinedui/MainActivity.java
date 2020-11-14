package com.example.sylvancombinedui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText eUsername;
    private EditText ePassword;
    private Button btnLogin;
    private TextView eAttemptsInfo;

    private String correctUsername = "Cashier";
    private String correctPassword = "Password";
    boolean isValid = false;
    private int counter = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eUsername = (EditText) findViewById(R.id.etUsername);
        ePassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        eAttemptsInfo = (TextView) findViewById(R.id.tvAttemptsInfo);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputUsername = eUsername.getText().toString();
                String inputPassword = ePassword.getText().toString();

                if (inputUsername.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please Enter a Username", Toast.LENGTH_SHORT).show();
                }
                else if (inputPassword.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please Enter a Password", Toast.LENGTH_SHORT).show();
                }
                else{
                    isValid = validate(inputUsername, inputPassword);

                    if (!isValid){
                        //decrement counter for login attempts
                        counter--;
                        Toast.makeText(MainActivity.this, "Username and/or Password is Invalid",
                                Toast.LENGTH_SHORT).show();
                        eAttemptsInfo.setText("No. of Attempts Remaining: " + counter);
                        if (counter == 0){
                            btnLogin.setEnabled(false);
                        }
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(MainActivity.this, UIActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }

    private boolean validate(String username, String password){
        if(username.equals(correctUsername) && password.equals(correctPassword)){
            return true;
        }
        else {
            return false;
        }
    }
}
