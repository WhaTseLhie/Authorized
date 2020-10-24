package pardillo.john.jv.authorized.main.drawer.aprofile;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.pojo.User;
import pardillo.john.jv.authorized.register.anew.SignActivity;
import pardillo.john.jv.authorized.style.CircleTransform;
import pardillo.john.jv.authorized.style.MyToast;

public class AProfileActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private static final int CODE_GENERATE_SIGNATURE = 3;

    // MAIN CONTENT
    private ImageView iv, ivSignature;
    private TextView txtReview, txtFullName, txtContact, txtGender, txtUserName, txtPassword, txtUpdateInfo, txtUpdateSignature;
    private TextView lblGender;
    private CheckBox cbPass;
    private Button btnChangePass;

    // DATA
    private AppDatabase db;
    private ArrayList<User> userList = new ArrayList<>();

    // CHANGE PASSWORD
    private EditText txtOldPass, txtNewPass, txtConfirmPass;
    private static String errMsg = "This field is empty.";

    // UPDATE USER INFORMATION
    private EditText txtEditFname, txtEditLname, txtEditContact;
    private RadioGroup rgGender;
    private RatingBar rbRating;

    // UPDATE EST INFORMATION
    private EditText txtEditEstName, txtEditEstAddress, txtEditEstContact;

    // DIALOG BUTTONS 2
    private Button btnSave2, btnCancel2;

    // DIALOG
    private Dialog cpDialog, infoDialog, deDialog, estDialog;

    // DIALOG BUTTONS
    private Button btnSave, btnCancel;

    private MyToast myToast = new MyToast();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aprofile);

        db = new AppDatabase(this);
        userList = db.getLogInUser();

        iv = findViewById(R.id.imageView);
        ivSignature = findViewById(R.id.ivSignature);
        rbRating = findViewById(R.id.ratingBar);
        txtReview = findViewById(R.id.txtReview);
        txtFullName = findViewById(R.id.txtFullName);
        txtContact = findViewById(R.id.txtContact);
        lblGender = findViewById(R.id.lblGender);
        txtGender = findViewById(R.id.txtGender);
        txtUserName = findViewById(R.id.txtUserName);
        txtPassword = findViewById(R.id.txtPassword);
//        txtSignature = findViewById(R.id.txtSignature);
        txtUpdateInfo = findViewById(R.id.txtUpdateInfo);
        txtUpdateSignature = findViewById(R.id.txtUpdateSignature);
        cbPass = findViewById(R.id.cbPass);
        btnChangePass = findViewById(R.id.btnChangePass);

//        iv.setImageURI(userList.get(0).getA_image());
        Picasso.with(getApplicationContext()).load(userList.get(0).getA_image()).transform(new CircleTransform()).into(iv);
        float aveRating = db.getAverageRating(userList.get(0).getA_id());
        rbRating.setRating(aveRating);
        txtReview.setText("View ratings and reviews (" +aveRating+ ")");
        txtFullName.setText(userList.get(0).getA_fname() +" "+ userList.get(0).getA_lname());
        txtContact.setText(userList.get(0).getA_contact());

        if(TextUtils.isEmpty(userList.get(0).getA_lname())) {
            lblGender.setText("Address: ");
        }

        txtGender.setText(userList.get(0).getA_gender());
        txtUserName.setText(userList.get(0).getA_username());
        txtPassword.setText(userList.get(0).getA_password());
        ivSignature.setImageURI(Uri.parse(userList.get(0).getA_signature()));

        txtReview.setOnClickListener(this);
        txtUpdateInfo.setOnClickListener(this);
        txtUpdateSignature.setOnClickListener(this);
        cbPass.setOnCheckedChangeListener(this);
        btnChangePass.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.deactivate_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.deactivate);
//        menuItem.setVisible(true);

        if(userList.get(0).getA_status().equals("ACTIVATED")) {
            menuItem.setTitle("DEACTIVATE");
        } else {
            menuItem.setTitle("ACTIVATE");
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.deactivate:
                if(userList.get(0).getA_status().equals("ACTIVATED")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Confirmation");
                    builder.setMessage("Are you sure you want to deactivate your account?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.updateUserStatus(userList.get(0).getA_username(), "DEACTIVATED");
                            userList.get(0).setA_status("DEACTIVATED");
                            myToast.makeToast(AProfileActivity.this, "Account Deactivated", "SUCCESS");
                            deDialog.dismiss();

                            finish();
                            startActivity(getIntent());

                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deDialog.dismiss();
                        }
                    });

                    deDialog = builder.create();
                    deDialog.show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Confirmation");
                    builder.setMessage("Are you sure you want to activate your account?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.updateUserStatus(userList.get(0).getA_username(), "ACTIVATED");
                            userList.get(0).setA_status("ACTIVATED");
                            myToast.makeToast(AProfileActivity.this, "Account Activated", "SUCCESS");
                            deDialog.dismiss();

                            finish();
                            startActivity(getIntent());
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deDialog.dismiss();
                        }
                    });

                    deDialog = builder.create();
                    deDialog.show();
                }

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.txtReview:
                Intent viewReview = new Intent(this, ViewReviewActivity.class);
                startActivity(viewReview);

                break;
            case R.id.txtUpdateInfo:
                if(!TextUtils.isEmpty(userList.get(0).getA_lname())) {
                    infoDialog = new Dialog(this);
                    infoDialog.setContentView(R.layout.layout_edit_user_info);

                    txtEditFname = infoDialog.findViewById(R.id.txtFname);
                    txtEditLname = infoDialog.findViewById(R.id.txtLname);
                    txtEditContact = infoDialog.findViewById(R.id.txtContact);
                    rgGender = infoDialog.findViewById(R.id.rgGender);
                    btnSave = infoDialog.findViewById(R.id.btnSave);
                    btnCancel = infoDialog.findViewById(R.id.btnCancel);

                    txtEditFname.setText(userList.get(0).getA_fname());
                    txtEditLname.setText(userList.get(0).getA_lname());
                    txtEditContact.setText(userList.get(0).getA_contact());
                    if (userList.get(0).getA_gender().equals("Male")) {
                        rgGender.check(R.id.rbMale);
                    } else {
                        rgGender.check(R.id.rbFemale);
                    }

                    btnSave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String errMsg = "This field is empty.";
                            String fname = txtEditFname.getText().toString();
                            String lname = txtEditLname.getText().toString();
                            String contact = txtEditContact.getText().toString();

                            if (TextUtils.isEmpty(fname)) {
                                txtEditFname.requestFocus();
                                txtEditFname.setError(errMsg);
                            } else if (TextUtils.isEmpty(lname)) {
                                txtEditLname.requestFocus();
                                txtEditLname.setError(errMsg);
                            } else if (TextUtils.isEmpty(contact)) {
                                txtEditContact.requestFocus();
                                txtEditContact.setError(errMsg);
                            } else {
                                int selectedSex = rgGender.getCheckedRadioButtonId();
                                RadioButton selectedButton = infoDialog.findViewById(selectedSex);
                                String gender = selectedButton.getText().toString();

                                userList.get(0).setA_fname(txtEditFname.getText().toString());
                                userList.get(0).setA_lname(txtEditLname.getText().toString());
                                userList.get(0).setA_contact(txtEditContact.getText().toString());
                                userList.get(0).setA_gender(gender);

                                txtFullName.setText(userList.get(0).getA_fname() + " " + userList.get(0).getA_lname());
                                txtContact.setText(userList.get(0).getA_contact());
                                txtGender.setText(userList.get(0).getA_gender());

                                db.updateUserInfo(userList.get(0).getA_username(), fname, lname, contact, gender);
                                myToast.makeToast(AProfileActivity.this, "Information Successfully Updated", "SUCCESS");
                                infoDialog.dismiss();

                                AProfileActivity.this.finish();
                                AProfileActivity.this.startActivity(getIntent());
                            }
                        }
                    });

                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            infoDialog.dismiss();
                        }
                    });

                    infoDialog.show();
                } else {
                    estDialog = new Dialog(this);
                    estDialog.setContentView(R.layout.layout_edit_est_info);

                    txtEditEstName = estDialog.findViewById(R.id.txtEstName);
                    txtEditEstAddress = estDialog.findViewById(R.id.txtEstAddress);
                    txtEditEstContact = estDialog.findViewById(R.id.txtEstContact);
                    btnSave2 = estDialog.findViewById(R.id.btnSave);
                    btnCancel2 = estDialog.findViewById(R.id.btnCancel);

                    txtEditEstName.setText(userList.get(0).getA_fname());
                    txtEditEstAddress.setText(userList.get(0).getA_gender());
                    txtEditEstContact.setText(userList.get(0).getA_contact());

                    btnSave2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String errMsg2 = "This field is empty.";
                            String estName = txtEditEstName.getText().toString();
                            String estAddress = txtEditEstAddress.getText().toString();
                            String estContact = txtEditEstContact.getText().toString();

                            if (TextUtils.isEmpty(estName)) {
                                txtEditEstName.requestFocus();
                                txtEditEstName.setError(errMsg2);
                            } else if (TextUtils.isEmpty(estAddress)) {
                                txtEditEstAddress.requestFocus();
                                txtEditEstAddress.setError(errMsg2);
                            } else if (TextUtils.isEmpty(estContact)) {
                                txtEditEstContact.requestFocus();
                                txtEditEstContact.setError(errMsg2);
                            } else {
                                userList.get(0).setA_fname(txtEditEstName.getText().toString());
                                userList.get(0).setA_gender(txtEditEstAddress.getText().toString());
                                userList.get(0).setA_contact(txtEditEstContact.getText().toString());

                                db.updateUserInfo(userList.get(0).getA_username(), estName, "", estContact, estAddress);
                                myToast.makeToast(AProfileActivity.this, "Information Successfully Updated", "SUCCESS");
                                estDialog.dismiss();

                                AProfileActivity.this.finish();
                                AProfileActivity.this.startActivity(getIntent());
                            }
                        }
                    });

                    btnCancel2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            estDialog.dismiss();
                        }
                    });

                    estDialog.show();
                }

                break;
            case R.id.btnChangePass:
                cpDialog = new Dialog(this);
                cpDialog.setContentView(R.layout.layout_change_password);

                txtOldPass = cpDialog.findViewById(R.id.txtOldPass);
                txtNewPass = cpDialog.findViewById(R.id.txtNewPass);
                txtConfirmPass = cpDialog.findViewById(R.id.txtConfirmPass);
                btnSave = cpDialog.findViewById(R.id.btnSave);
                btnCancel = cpDialog.findViewById(R.id.btnCancel);

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String newPass = txtNewPass.getText().toString();
                        String confirmPass = txtConfirmPass.getText().toString();

                        if(TextUtils.equals(txtOldPass.getText().toString(), userList.get(0).getA_password())) {
                            if (!TextUtils.isEmpty(newPass)) {
                                if (TextUtils.equals(newPass, confirmPass)) {
                                    db.updateUserPassword(userList.get(0).getA_username(), confirmPass);
                                    txtPassword.setText(confirmPass);
                                    userList.get(0).setA_password(confirmPass);
                                    myToast.makeToast(AProfileActivity.this, "Password Changed", "SUCCESS");
                                    cpDialog.dismiss();
                                    finish();
                                    startActivity(getIntent());
                                } else {
                                    txtConfirmPass.requestFocus();
                                    txtConfirmPass.setError("New Password and Confirmation Password is not match");
                                }
                            } else {
                                txtNewPass.requestFocus();
                                txtNewPass.setError("Error! Please check again");
                            }
                        } else {
                            txtOldPass.requestFocus();
                            txtOldPass.setError(errMsg);
                        }
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cpDialog.dismiss();
                    }
                });

                cpDialog.show();

                break;
            case R.id.txtUpdateSignature:
                Intent generateSignature = new Intent(this, UpdateSignActivity.class);
                startActivityForResult(generateSignature, CODE_GENERATE_SIGNATURE);

                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(!isChecked) {
            txtPassword.setTransformationMethod(new PasswordTransformationMethod());
        } else {
            txtPassword.setTransformationMethod(null);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == CODE_GENERATE_SIGNATURE) {
                try {
                    Bundle b = data.getExtras();
                    String signature = b.getString("signature");
                    ivSignature.setImageURI(Uri.parse(signature));
                    db.updateUserSignature(userList.get(0).getA_id(), signature);
                    myToast.makeToast(this, "Signature Updated", "SUCCESS");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }
}