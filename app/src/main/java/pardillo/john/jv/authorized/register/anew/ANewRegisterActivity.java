package pardillo.john.jv.authorized.register.anew;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.style.MyToast;

public class ANewRegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int CODE_PICK_IMAGE = 2;
    private static final int CODE_GENERATE_SIGNATURE = 3;

    private LinearLayout linearLayout;
    private RelativeLayout relativeLayout;
    private ImageView iv, ivSignature;
    private EditText txtFname, txtLname, txtContact, txtUserName, txtPassword, txtEstName, txtEstAddress, txtEstContact;
    private RadioGroup rgGender;
    private Button btnCreate, btnCancel;
    private Uri imageUri = null;

    private AppDatabase db;
    private MyToast myToast;

    private String signature = "";

    // FOR OTP
    private int sentOTP = 0;
    private EditText txtOTP;
    private TextView txtResend;
    boolean isValidated;
    private Dialog otpDialog;

    private String fname, lname, contact, gender, username, password, estname, estcontact, estaddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anew_register);

        this.db = new AppDatabase(this);
        this.myToast = new MyToast();

        this.iv = this.findViewById(R.id.imageView);
        this.ivSignature = this.findViewById(R.id.ivSignature);
        this.txtEstName = this.findViewById(R.id.txtEstName);
        this.txtEstContact = this.findViewById(R.id.txtEstContact);
        this.txtEstAddress = this.findViewById(R.id.txtEstAddress);
        this.txtFname = this.findViewById(R.id.txtFname);
        this.txtLname = this.findViewById(R.id.txtLname);
        this.txtContact = this.findViewById(R.id.txtContact);
        this.txtUserName = this.findViewById(R.id.txtUserName);
        this.txtPassword = this.findViewById(R.id.txtPassword);
        this.relativeLayout = this.findViewById(R.id.relativeLayout);
        this.linearLayout = this.findViewById(R.id.linearLayout);
        this.rgGender = this.findViewById(R.id.rgGender);
        this.btnCreate = this.findViewById(R.id.btnCreate);
        this.btnCancel = this.findViewById(R.id.btnCancel);

//        if(ActivityCompat.checkSelfPermission(ANewRegisterActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{
//                    Manifest.permission.SEND_SMS}, 20);
//        }

        this.relativeLayout.setOnClickListener(this);
        this.linearLayout.setOnClickListener(this);
        this.btnCreate.setOnClickListener(this);
        this.btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.relativeLayout:
                if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE}, CODE_PICK_IMAGE);
                    }
                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    this.startActivityForResult(intent,CODE_PICK_IMAGE);
                }

                break;
            case R.id.btnCreate:
                isEmptyFields();

                break;
            case R.id.btnCancel:
                this.finish();

                break;
            case R.id.linearLayout:
                Intent generateSignature = new Intent(this, SignActivity.class);
                startActivityForResult(generateSignature, CODE_GENERATE_SIGNATURE);

                break;
        }
    }

    private void isEmptyFields() {
        estname = this.txtEstName.getText().toString();
        estaddress = this.txtEstAddress.getText().toString();
        estcontact = this.txtEstContact.getText().toString();
        fname = this.txtFname.getText().toString();
        lname = this.txtLname.getText().toString();
        contact = this.txtContact.getText().toString();
        username = this.txtUserName.getText().toString().toLowerCase();
        password = this.txtPassword.getText().toString();

        int selectedGender = this.rgGender.getCheckedRadioButtonId();
        RadioButton rbGenderSelected = this.findViewById(selectedGender);
        gender = rbGenderSelected.getText().toString();

        if(imageUri != null) {
            if(TextUtils.isEmpty(signature)) {
                myToast.makeToast(this, "Please create your digital signature", "ERROR");
            } else {
                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    myToast.makeToast(this, "Please fill all fields", "ERROR");
                } else {
                    if (!db.isTaken(username)) {
                        boolean isEmptyUser = false;
                        boolean isEmptyEst = false;

                        if (TextUtils.isEmpty(estname) || TextUtils.isEmpty(estaddress) || TextUtils.isEmpty(estcontact)) {
                            isEmptyEst = true;
                            if(isEmptyUser(fname, lname, contact)) {
                                myToast.makeToast(this, "Please fill all fields", "ERROR");
                            }
                        } else {
                            if(ActivityCompat.checkSelfPermission(ANewRegisterActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(this, new String[]{
                                        Manifest.permission.SEND_SMS}, 20);
                            } else {
                                sendSMSOTP(txtEstContact.getText().toString());
                            }

                            otpDialog = new Dialog(ANewRegisterActivity.this);
                            otpDialog.setContentView(R.layout.layout_input_otp_reg);
                            otpDialog.setCancelable(false);

                            txtOTP = otpDialog.findViewById(R.id.txtOTP);
                            txtResend = otpDialog.findViewById(R.id.txtResend);
                            Button btnVerify = otpDialog.findViewById(R.id.btnVerify);
                            Button btnCancel = otpDialog.findViewById(R.id.btnCancel);

                            txtResend.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Random rand = new Random();
                                    sentOTP = rand.nextInt(999999);
                                    android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
                                    String otpMessage = "Your verification code is " +sentOTP;
                                    smsManager.sendTextMessage(txtEstContact.getText().toString(), null, otpMessage, null, null);
                                    myToast.makeToast(ANewRegisterActivity.this, "Verification Code Sent to " +txtEstContact.getText().toString(), "SUCCESS");
                                }
                            });

                            btnVerify.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try {
                                        int otp = 0;
                                        if(!TextUtils.isEmpty(txtOTP.getText().toString())) {
                                            otp = Integer.parseInt(txtOTP.getText().toString());
                                        }

                                        if(sentOTP != 0) {
                                            if (sentOTP == otp) {
                                                db.addUser(imageUri.toString(), estname, "", estcontact, estaddress, username, password, signature, "ACTIVATED");
                                                myToast.makeToast(ANewRegisterActivity.this, "Establishment Created", "SUCCESS");
                                                ANewRegisterActivity.this.finish();
                                                otpDialog.dismiss();
                                            } else {
                                                myToast.makeToast(ANewRegisterActivity.this, "Wrong OTP", "ERROR");
                                            }
                                        } else {
                                            myToast.makeToast(ANewRegisterActivity.this, "Please submit your phone number first", "ERROR");
                                        }
                                    } catch (NumberFormatException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                            btnCancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    otpDialog.dismiss();
                                }
                            });

                            otpDialog.show();
                        }

                        if(isEmptyEst) {
                            if(TextUtils.isEmpty(fname) || TextUtils.isEmpty(lname) || TextUtils.isEmpty(contact)) {
                                myToast.makeToast(this, "Please fill all fields", "ERROR");
                            } else {
                                if(ActivityCompat.checkSelfPermission(ANewRegisterActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(this, new String[]{
                                            Manifest.permission.SEND_SMS}, 30);
                                } else {
                                    sendSMSOTP(txtContact.getText().toString());
                                }

                                otpDialog = new Dialog(ANewRegisterActivity.this);
                                otpDialog.setContentView(R.layout.layout_input_otp_reg);
                                otpDialog.setCancelable(false);

                                txtOTP = otpDialog.findViewById(R.id.txtOTP);
                                txtResend = otpDialog.findViewById(R.id.txtResend);
                                Button btnVerify = otpDialog.findViewById(R.id.btnVerify);
                                Button btnCancel = otpDialog.findViewById(R.id.btnCancel);

                                txtResend.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Random rand = new Random();
                                        sentOTP = rand.nextInt(999999);
                                        android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
                                        String otpMessage = "Your verification code is " +sentOTP;
                                        smsManager.sendTextMessage(txtContact.getText().toString(), null, otpMessage, null, null);
                                        myToast.makeToast(ANewRegisterActivity.this, "Verification Code Sent to " +txtContact.getText().toString(), "SUCCESS");
                                    }
                                });

                                btnVerify.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        try {
                                            int otp = 0;
                                            if(!TextUtils.isEmpty(txtOTP.getText().toString())) {
                                                otp = Integer.parseInt(txtOTP.getText().toString());
                                            }

                                            if(sentOTP != 0) {
                                                if (sentOTP == otp) {
                                                    db.addUser(imageUri.toString(), fname, lname, contact, gender, username, password, signature, "ACTIVATED");
                                                    myToast.makeToast(ANewRegisterActivity.this, "Account Created", "SUCCESS");
                                                    ANewRegisterActivity.this.finish();
                                                    otpDialog.dismiss();
                                                } else {
                                                    myToast.makeToast(ANewRegisterActivity.this, "Wrong OTP", "ERROR");
                                                }
                                            } else {
                                                myToast.makeToast(ANewRegisterActivity.this, "Please submit your phone number first", "ERROR");
                                            }
                                        } catch (NumberFormatException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                                btnCancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        otpDialog.dismiss();
                                    }
                                });

                                otpDialog.show();
                            }
                        }
                    } else {
                        myToast.makeToast(this, "User Name is taken. Please enter another", "ERROR");
                    }
                }
            }
        } else {
            myToast.makeToast(this, "Please click the add photo to select an image", "ERROR");
        }
    }

    public boolean isEmptyUser(String fname, String lname, String contact) {
        if(TextUtils.isEmpty(fname) || TextUtils.isEmpty(lname) || TextUtils.isEmpty(contact)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == CODE_GENERATE_SIGNATURE) {
                try {
                    Bundle b = data.getExtras();
                    signature = b.getString("signature");
                    ivSignature.setImageURI(Uri.parse(signature));
                    myToast.makeToast(this, "Signature Created", "SUCCESS");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                try {
                    imageUri = data.getData();
                    iv.setImageURI(imageUri);
                    Log.d("onActivityResult: ", imageUri.toString());
                } catch (Exception e) {
                    Log.d("ANEWREG", "IMAGE ERROR");
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case 100:
                if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    final Dialog dialog2 = new Dialog(this);
                    dialog2.setContentView(R.layout.layout_permission);

                    TextView t1 = dialog2.findViewById(R.id.txtTitle);
                    TextView t2 = dialog2.findViewById(R.id.txtMessage);
                    t1.setText("Request Permissions");
                    t2.setText("Please allow permissions if you want this application to perform the task.");

                    Button dialogButton = dialog2.findViewById(R.id.btnOk);
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog2.dismiss();
                        }
                    });

                    dialog2.show();
                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    this.startActivityForResult(intent,CODE_PICK_IMAGE);
                }

                break;
            case 20:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    final Dialog dialog1 = new Dialog(this);
                    dialog1.setContentView(R.layout.layout_permission);

                    TextView txtTitle = (TextView) dialog1.findViewById(R.id.txtTitle);
                    TextView txtMessage = (TextView) dialog1.findViewById(R.id.txtMessage);
                    txtTitle.setText("Request Permissions");
                    txtMessage.setText("Please allow permissions if you want this application to perform the task.");

                    Button dialogButton = (Button) dialog1.findViewById(R.id.btnOk);
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog1.dismiss();
                        }
                    });

                    dialog1.show();
                } else {
                    myToast.makeToast(this, "sendotp", "NORMAL");
                }

                break;
            case 30:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    final Dialog dialog1 = new Dialog(this);
                    dialog1.setContentView(R.layout.layout_permission);

                    TextView txtTitle = (TextView) dialog1.findViewById(R.id.txtTitle);
                    TextView txtMessage = (TextView) dialog1.findViewById(R.id.txtMessage);
                    txtTitle.setText("Request Permissions");
                    txtMessage.setText("Please allow permissions if you want this application to perform the task.");

                    Button dialogButton = (Button) dialog1.findViewById(R.id.btnOk);
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog1.dismiss();
                        }
                    });

                    dialog1.show();
                } else {
                    myToast.makeToast(this, "sendotp", "NORMAL");
                }

                break;
        }
    }

//    public boolean saveAccount() {
//        isValidated = false;
//
//        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                requestPermissions(new String[]{
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
//            }
//        } else {
//            otpDialog = new Dialog(ANewRegisterActivity.this);
//            otpDialog.setContentView(R.layout.layout_input_otp_reg);
//            otpDialog.setCancelable(false);
//
//            txtOTP = otpDialog.findViewById(R.id.txtOTP);
//            Button btnVerify = otpDialog.findViewById(R.id.btnVerify);
//            Button btnCancel = otpDialog.findViewById(R.id.btnCancel);
//
//            btnVerify.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    try {
//                        int otp = 0;
//                        if(!TextUtils.isEmpty(txtOTP.getText().toString())) {
//                            otp = Integer.parseInt(txtOTP.getText().toString());
//                        }
//
//                        if(sentOTP != 0) {
//                            if (sentOTP == otp) {
//                                otpDialog.dismiss();
//                                isValidated = true;
//                            } else {
//                                myToast.makeToast(ANewRegisterActivity.this, "Wrong OTP", "ERROR");
//                            }
//                        } else {
//                            myToast.makeToast(ANewRegisterActivity.this, "Please submit your phone number first", "ERROR");
//                        }
//                    } catch (NumberFormatException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//
//            btnCancel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    otpDialog.dismiss();
//                }
//            });
//
//            otpDialog.show();
//        }
//
//        return isValidated;
//    }

    public boolean checkPermission(String permission) {
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }

    public void sendSMSOTP(String phone) {
        Random rand = new Random();
        sentOTP = rand.nextInt(999999);
        android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
        String otpMessage = "Your verification code is " +sentOTP;
        smsManager.sendTextMessage(phone, null, otpMessage, null, null);
        myToast.makeToast(this, "Verification Code Sent to " +phone, "SUCCESS");

//        if(phone.equals("+639983930468")) {
//            myToast.makeToast(this, "true", "SUCCESS");
//        }
//        smsManager.sendTextMessage(phone, "+639983930475", otpMessage, null, null);
//        smsManager.sendTextMessage("+639983930468", "+639983930475", otpMessage, null, null);

//        Spanned otpMessage = Html.fromHtml("<u>" +otp+ "</u>");
//        smsManager.sendTextMessage(phone, "+639983930468", otpMessage, null, null);
//        smsManager.sendTextMessage("+639983930475", null, otpMessage, null, null);
    }
}