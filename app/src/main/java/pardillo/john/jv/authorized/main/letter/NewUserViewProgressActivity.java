package pardillo.john.jv.authorized.main.letter;

import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.pojo.NewDraft;
import pardillo.john.jv.authorized.database.pojo.Price;
import pardillo.john.jv.authorized.database.pojo.User;
import pardillo.john.jv.authorized.main.LandingScreenActivity;
import pardillo.john.jv.authorized.style.MyToast;

public class NewUserViewProgressActivity extends AppCompatActivity {

    private TextView txtLetterContent;

    private AppDatabase db;
    private MyToast myToast;

    private ArrayList<User> userList = new ArrayList<>();
    private ArrayList<NewDraft> draftList = new ArrayList<>();

    private int s_id = 0;
    private int r_id = 0;
    private int t_id = 0;

    private Uri signatureUri;

    private Dialog paypalDialog;

    private int sentOTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_view_progress);

        myToast = new MyToast();
        db = new AppDatabase(this);
        txtLetterContent = findViewById(R.id.txtLetterContent);

        try {
            Bundle b = getIntent().getExtras();
            t_id = b.getInt("t_id");
            userList = db.getLogInUser();
            draftList = db.getNewDraft(userList.get(0).getA_id());

            String strLetterContent = draftList.get(0).getD_letterContent();
            SpannableStringBuilder ssbLetterContent = new SpannableStringBuilder(strLetterContent);

            Drawable signatureDrawable = null;
            InputStream inputStream = null;
            ImageSpan imageSpan = null;
            try {
                try {
                    signatureUri = Uri.parse(userList.get(0).getA_signature());
                    inputStream = getContentResolver().openInputStream(signatureUri);
                    signatureDrawable = Drawable.createFromStream(inputStream, signatureUri.toString());
                    signatureDrawable.setBounds(0, 0, 150, 150);

                    int sign_start = strLetterContent.indexOf("[SIGN]");
                    imageSpan = new ImageSpan(signatureDrawable);
                    ssbLetterContent.setSpan(
                            imageSpan,
                            sign_start,
                            sign_start + "[SIGN]".length() + 1,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                } catch (FileNotFoundException e) {
                    myToast.makeToast(this, "Something went wrong when displaying digital signature", "ERROR");
                    e.printStackTrace();
                } finally {
                    try {
                        if(inputStream != null) {
                            inputStream.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

//            try {
//                int clc_start = strLetterContent.indexOf("[SIGN]");
//                signatureUri = Uri.parse(userList.get(0).getA_signature());
//                ssbLetterContent.setSpan(
//                        new ImageSpan(this, signatureUri),
//                        clc_start,
//                        clc_start + "[SIGN]".length() + 1,
//                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

            txtLetterContent.setText(ssbLetterContent);
            txtLetterContent.setMovementMethod(LinkMovementMethod.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.send_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.send) {
            servicePayment();
        }/* else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Cancel Confirmation");
            builder.setMessage("Do you really want to cancel?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                    Intent intent = new Intent(NewUserViewProgressActivity.this, LandingScreenActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("No", null);
            builder.setCancelable(false);

            AlertDialog dialog = builder.create();
            dialog.show();

//            Intent intent = new Intent(this, LandingScreenActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);

//            Intent intent = new Intent(NewUserViewProgressActivity.this, LandingScreenActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
        }*/

//        switch(item.getItemId()) {
//            case R.id.send:
//                servicePayment();
//
//                break;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cancel Confirmation");
        builder.setMessage("Do you really want to cancel?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                Intent intent = new Intent(NewUserViewProgressActivity.this, LandingScreenActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No", null);
        builder.setCancelable(false);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void servicePayment() {
        final Dialog paymentDialog = new Dialog(this);
        paymentDialog.setContentView(R.layout.layout_payment);
        paymentDialog.setCancelable(false);

        final TextView txtMessage = paymentDialog.findViewById(R.id.txtMessage);
        Button btnPay = paymentDialog.findViewById(R.id.btnPay);
        Button btnCancel = paymentDialog.findViewById(R.id.btnCancel);

        try {
            ArrayList<Price> priceList = db.getPrice();

            if(!priceList.isEmpty()) {
                txtMessage.setText("You need to pay \u20B1" + priceList.get(0).getP_price() + " to send this letter.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentDialog.dismiss();
                paypalDialog = new Dialog(NewUserViewProgressActivity.this);
                paypalDialog.setContentView(R.layout.layout_pay_paypal);
                paypalDialog.setCancelable(false);

                final EditText txtPaypalUser = paypalDialog.findViewById(R.id.txtPaypalUser);
                final EditText txtPaypalPassword = paypalDialog.findViewById(R.id.txtPaypalPassword);
                Button btnPay = paypalDialog.findViewById(R.id.btnPay);
                Button btnCancel = paypalDialog.findViewById(R.id.btnCancel);

                btnPay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String paypalUser = txtPaypalUser.getText().toString();
                        String paypalPassword = txtPaypalPassword.getText().toString();

                        if(TextUtils.isEmpty(paypalUser)) {
                            txtPaypalUser.setError("Field can't be empty");
                        } else if(TextUtils.isEmpty(paypalPassword)) {
                            txtPaypalPassword.setError("Field can't be empty");
                        } else {
                            int price = 20;
                            ArrayList<Price> priceList = db.getPrice();

                            if(!priceList.isEmpty()) {
                                price = priceList.get(0).getP_price();
                            }

                            AlertDialog.Builder paidBuilder = new AlertDialog.Builder(NewUserViewProgressActivity.this);
                            paidBuilder.setTitle("Payment Information");
                            paidBuilder.setMessage("Your Paypal balance has been deducted by \u20B1" +price+ ".");
                            paidBuilder.setCancelable(false);
                            paidBuilder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    sendLetter();
                                }
                            });

                            AlertDialog paidDialog = paidBuilder.create();
                            paidDialog.show();
                        }
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        paypalDialog.dismiss();
                    }
                });

                paypalDialog.show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentDialog.dismiss();
            }
        });

        paymentDialog.show();
    }

    private void sendLetter() {
        int a_id = userList.get(0).getA_id();
        int rep_id = draftList.get(0).getRep_id();
        int rec_id = draftList.get(0).getRec_id();
        int t_id = draftList.get(0).getT_id();
        Random rand = new Random();
        sentOTP = rand.nextInt(999999);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        String dateToday = sdf.format(c);

        try {
            db.addPastRecipient(a_id, rec_id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        s_id = db.addSent(
                sentOTP,
                dateToday,
                txtLetterContent.getText().toString(),
                t_id,
                a_id,
                rep_id,
                rec_id,
                0,
                0,
                "PENDING"
        );

        r_id = db.addReceive(
                sentOTP,
                dateToday,
                txtLetterContent.getText().toString(),
                t_id,
                a_id,
                rep_id,
                rec_id,
                0,
                s_id,
                "PENDING"
        );

        db.updateSentRID(s_id, r_id);
        ArrayList<User> aList = db.getUser(a_id);
        ArrayList<User> recList = db.getUser(rec_id);

        //////////////////////
        String refCode = getRefCode();
        android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
        String aotpMessage = "Letter sent to " +recList.get(0).getA_fname()+ " " +recList.get(0).getA_lname()+ " validation code is " +sentOTP;
        String recotpMessage = "Letter received from " +aList.get(0).getA_fname()+ " " +aList.get(0).getA_lname()+ " validation code is " +sentOTP;
        smsManager.sendTextMessage(aList.get(0).getA_contact()+"", "+639983930475", aotpMessage, null, null);
        smsManager.sendTextMessage(recList.get(0).getA_contact()+"", "+639983930475", recotpMessage, null, null);
//        smsManager.sendTextMessage("+639983930468", "+639983930475", otpMessage, null, null);
//        myToast.makeToast(this, "Verification Code Sent to " +phone, "SUCCESS");

        String stype = "Sent";
        String stitle = "Request Notification";
        String smessage = "You sent letter to " +recList.get(0).getA_fname()+ " " +recList.get(0).getA_lname();
        db.addNotification(stype, stitle, dateToday, smessage, a_id, 0, s_id, r_id);

        String rtype = "Receive";
        String rtitle = "Request Notification";
        String rmessage = "You received a new request from " +aList.get(0).getA_fname()+ " " +aList.get(0).getA_lname();
        db.addNotification(rtype, rtitle, dateToday, rmessage, rec_id, 0, s_id, r_id);

        ArrayList<Price> priceList = db.getPrice();
        if(priceList.isEmpty()) {
            db.addPayment(dateToday, "PayPal", "20", a_id, rec_id, s_id, r_id);
        } else {
            db.addPayment(dateToday, "PayPal", priceList.get(0).getP_price() + "", a_id, rec_id, s_id, r_id);
        }

        db.deleteAllNewDraft();

        myToast.makeToast(this, "Letter Sent", "SUCCESS");
        Intent mainScreen = new Intent(this, LandingScreenActivity.class);
        mainScreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainScreen);

        try {
//            String message = "This is a notification message example.";
            NotificationCompat.Builder builder = new NotificationCompat.Builder(
                    NewUserViewProgressActivity.this)
                    .setSmallIcon(R.drawable.authorized_logo)
                    .setContentTitle("Sent Notification")
                    .setContentText(smessage)
                    .setAutoCancel(true);

            /*Intent intent = new Intent(NewViewProgressActivity.this, ASentActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);*/
//            intent.putExtra("message", message);

            /*PendingIntent pendingIntent = PendingIntent.getActivity(NewViewProgressActivity.this,
                    0,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);*/

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, builder.build());

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public String getRefCode() {
        String ALPHANUM = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while(salt.length() < 10) {
            int index = (int) (rnd.nextFloat() * ALPHANUM.length());
            salt.append(ALPHANUM.charAt(index));
        }

        return salt.toString();
    }
}
