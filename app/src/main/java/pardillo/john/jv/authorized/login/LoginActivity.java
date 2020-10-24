package pardillo.john.jv.authorized.login;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.admin.AdminActivity;
import pardillo.john.jv.authorized.admin.admain.AdminLandingScreenActivity;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.pojo.User;
import pardillo.john.jv.authorized.main.LandingScreenActivity;
import pardillo.john.jv.authorized.register.anew.ANewRegisterActivity;
import pardillo.john.jv.authorized.style.MyToast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout txtInputUsername, txtInputPassword;
    private Button btnLogin, btnCreate;
//    private TextView txtForgot;
    private MyToast myToast = new MyToast();

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.db = new AppDatabase(this);

//        if(db.getAllUser().isEmpty()) {
//            db.addAdmin("content://media/external/images/media/7845", "Authoriz.ed", "Admin", "09983930468", "Male", "admin", "admin", "Sign", "ACTIVATED");
//            db.addUser("content://media/external/images/media/7845", "Authoriz.ed", "Admin", "09983930468", "Male", "admin", "admin", "Sign", "ACTIVATED");
//        }

        this.txtInputUsername = this.findViewById(R.id.textInputLayout);
        this.txtInputPassword = this.findViewById(R.id.textInputLayout2);
        this.btnLogin = this.findViewById(R.id.button);
        this.btnCreate = this.findViewById(R.id.button2);
//        this.txtForgot = this.findViewById(R.id.textView);

        this.txtInputUsername.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) {
                    txtInputUsername.setError(null);
                }
            }
        });

        this.txtInputPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) {
                    txtInputPassword.setError(null);
                }
            }
        });

        this.btnLogin.setOnClickListener(this);
        this.btnCreate.setOnClickListener(this);
//        this.txtForgot.setOnClickListener(this);
    }

    private boolean validateUsername() {
        String username = this.txtInputUsername.getEditText().getText().toString();

        if (username.isEmpty()) {
            txtInputUsername.setError("Field can't be empty");
            return false;
        } else {
            txtInputUsername.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String password = this.txtInputPassword.getEditText().getText().toString();

        if (password.isEmpty()) {
            txtInputPassword.setError("Field can't be empty");
            return false;
        } else {
            txtInputPassword.setError(null);
            return true;
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button:
                if(validateUsername() & validatePassword()) {
                    String username = this.txtInputUsername.getEditText().getText().toString().toLowerCase();
                    String password = this.txtInputPassword.getEditText().getText().toString();
                    db.deleteLoginUser();

                    /*if(TextUtils.equals(username, "admin") && TextUtils.equals(password, "admin")) {
                        Intent adminIntent = new Intent(this, AdminLandingScreenActivity.class);
                        adminIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(adminIntent);

                        ArrayList<User> adminList = db.getUserAdmin("admin");
                        db.addLoginUser(adminList.get(0).getA_id(),"content://media/external/images/media/7845", "Authoriz.ed", "Admin", "09983930468", "Male", "admin", "admin", "Sign", "ACTIVATED");
                        myToast.makeToast(this, "Hello! Admin", "SUCCESS");


                        *//*Intent adminIntent = new Intent(this, AdminActivity.class);
                        adminIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(adminIntent);*//*
                    } else*/ if(db.checkLoginUser(username, password)) {
                        if(db.checkUserStatus(username)) {
                            if(username.equals("admin") && password.equals("admin")) {
                                Intent adminIntent = new Intent(this, AdminLandingScreenActivity.class);
                                adminIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(adminIntent);
                            } else {
                                myToast.makeToast(this, "Login Success", "SUCCESS");
                                Intent mainIntent = new Intent(this, LandingScreenActivity.class);
                                startActivity(mainIntent);
                            }
                        } else {
                            myToast.makeToast(this, "Your account has been deactivated", "WARNING");
                        }
                    } else {
                        myToast.makeToast(this, "Please check your credentials", "ERROR");
                    }
                } else {
                    myToast.makeToast(this, "Please check your credentials", "ERROR");
                }

                break;
            case R.id.button2:
                Intent regIntent = new Intent(this, ANewRegisterActivity.class);
                startActivity(regIntent);

                break;
//            case R.id.textView:
//                if(db.getAllUser().isEmpty() || db.getAllUser().size() <= 1) {
//                    int jv = db.addUser("content://media/external/images/media/7474", "Jayvee John", "Pardillo", "09983930468", "Male", "user", "pass", "sign", "ACTIVATED");
//                    db.addUser("content://media/external/images/media/7474", "Authoriz.ed", "Admin", "09983930468", "Male", "admin", "admin", "sign", "ACTIVATED");
//                    db.addUser("content://media/external/images/media/7845", "University of Cebu", "Main-Campus", "091234567890", "Female", "user1", "pass", "sign", "ACTIVATED");
//                    db.addUser("content://media/external/images/media/7844", "Atorn", "Ni", "0955555555", "Male", "user2", "pass", "sign", "ACTIVATED");
//                    db.addRep("content://media/external/images/media/7622", "Duterte", "Ninjas", "0969696969", "LGBTUVWXYZ", "", jv);
//                    myToast.makeToast(this, "USER ADDED", "SUCCESS");
//                } else {
//                    myToast.makeToast(this, "ALREADY ADDED", "WARNING");
//                }
//
//                break;
        }
    }
}