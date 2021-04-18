package com.example.vahak;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private EditText edtLoginEmail , edtLoginPassword;
    private Button btnLogin;
    private TextView txtTransferToSignUpActivity , txtResetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.loginButton);

        edtLoginEmail = findViewById(R.id.edtLoginEmail);
        edtLoginPassword = findViewById(R.id.edtLoginPassword);
        btnLogin = findViewById(R.id.loginButton);
        txtResetPassword = findViewById(R.id.txtResetPassword);
        txtTransferToSignUpActivity = findViewById(R.id.txtTransferToSignUpActivity);

        btnLogin.setOnClickListener(Login.this);
        txtTransferToSignUpActivity.setOnClickListener(Login.this);
        txtResetPassword.setOnClickListener(Login.this);



        if (ParseUser.getCurrentUser() != null){


            Intent intent = new Intent(Login.this , Chatting.class);
            startActivity(intent);
            finish();
        }





    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.loginButton:

                ParseUser.logInInBackground(edtLoginEmail.getText().toString(), edtLoginPassword.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user != null && e == null){

                            // customSuccessDialog successDialog = new customSuccessDialog();
                            //successDialog.ShowSuccessDialog(Login_Activity.this , R.string.TxtWelcmToTweet);

                            Toast.makeText(Login.this ,
                                    R.string.TxtWelcmToTweet , Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this , Chatting.class);
                            startActivity(intent);
                            finish();

                        }else {

//                            customErrorDialog errorDialog = new customErrorDialog();
//                            errorDialog.ShowErrorDialog(Login_Activity.this , R.string.TxtErrorMsg);
                            Toast.makeText(Login.this , R.string.txtError , Toast.LENGTH_SHORT).show();

                            edtLoginEmail.getText().clear();
                            edtLoginPassword.getText().clear();
                        }
                    }
                });




                break;
            case R.id.txtTransferToSignUpActivity:

                Intent intent = new Intent(Login.this , SignUp.class);
                startActivity(intent);
                finish();
                break;
            case R.id.txtResetPassword:

                // BELOW CODES TO RESET PASSWORD USING Back4app.com

                if (ParseUser.getCurrentUser() != null){


                    ParseUser.requestPasswordResetInBackground(ParseUser.getCurrentUser().
                            get("email").toString(), new RequestPasswordResetCallback() {
                        @Override
                        public void done(ParseException e) {

                            if (e == null){


//                                customSuccessDialog successDialog = new customSuccessDialog();
//                                successDialog.ShowSuccessDialog(Login_Activity.this
//                                        , R.string.txtResetPassMsg);
                                Toast.makeText(Login.this , R.string.txtSuccessfullySentEmail , Toast.LENGTH_SHORT).show();
                            }else {

//                                customErrorDialog customerrordialog = new customErrorDialog();
//                                customerrordialog.ShowErrorDialog(Login_Activity.this , R.string.TxtErrorMsg);
                                Toast.makeText(Login.this , R.string.txtError , Toast.LENGTH_SHORT).show();

                            }
                            edtLoginEmail.getText().clear();
                            edtLoginPassword.getText().clear();

                        }
                    });


                }else {

//                    customErrorDialog errorDialog = new customErrorDialog();
//                    errorDialog.ShowErrorDialog(Login_Activity.this  , R.string.txtUnableToSentEmailToResetPassMsg);
                    Toast.makeText(Login.this , R.string.txtError , Toast.LENGTH_SHORT).show();

                    edtLoginEmail.getText().clear();
                    edtLoginPassword.getText().clear();


                }

                break;
            default:
                edtLoginEmail.getText().clear();
                edtLoginPassword.getText().clear();


        }

    }
}