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

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private EditText edtSignUpUserName , edtSignUpEmail , edtSignUpPassword;
    private Button btnSignUp;
    private TextView txtTransferToLoginActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.btnSignUp);

        edtSignUpUserName = findViewById(R.id.edtSignUpUserName);
        edtSignUpEmail = findViewById(R.id.edtSignUpEmail);
        edtSignUpPassword = findViewById(R.id.edtSignUpPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        txtTransferToLoginActivity = findViewById(R.id.txtTransferToLoginActivity);

        btnSignUp.setOnClickListener(SignUp.this);
        txtTransferToLoginActivity.setOnClickListener(SignUp.this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btnSignUp:
                final ParseUser parseUser = new ParseUser();
                parseUser.setUsername(edtSignUpUserName.getText().toString());
                parseUser.setEmail(edtSignUpEmail.getText().toString());
                parseUser.setPassword(edtSignUpPassword.getText().toString());
                parseUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {

                        if (e == null){

//                           customSuccessDialog successDialog = new customSuccessDialog();
//                           successDialog.ShowSuccessDialog(SignUp_Activity.this , R.string.TxtSuccessMsg);
                            Toast.makeText(SignUp.this , R.string.TxtSuccessMsg ,
                                    Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(SignUp.this , Login.class);
                            startActivity(intent);
                            finish();

                        }else {
                            try {
                                //customErrorDialog errorDialog = new customErrorDialog();
                                //  errorDialog.ShowErrorDialog(SignUp_Activity.this , R.string.TxtErrorMsg);

                                Toast.makeText(SignUp.this , R.string.txtError ,
                                        Toast.LENGTH_SHORT).show();
                                edtSignUpUserName.getText().clear();
                                edtSignUpEmail.getText().clear();
                                edtSignUpPassword.getText().clear();

                            }catch (Exception e1){

                                e1.getStackTrace();
                            }

                        }
                    }
                });

                break;
            case R.id.txtTransferToLoginActivity:

                Intent intent = new Intent(SignUp.this , Login.class);
                startActivity(intent);
                finish();
                break;

    }}
}