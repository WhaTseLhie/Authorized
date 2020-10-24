package pardillo.john.jv.authorized.main.drawer.asent;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.pojo.NewDraft;
import pardillo.john.jv.authorized.database.pojo.Price;
import pardillo.john.jv.authorized.database.pojo.Sent;
import pardillo.john.jv.authorized.database.pojo.User;
import pardillo.john.jv.authorized.main.LandingScreenActivity;
import pardillo.john.jv.authorized.style.MyToast;

public class NewViewUserResendActivity extends AppCompatActivity {

    private TextView txtLetterContent;

    private AppDatabase db;
    private MyToast myToast;

    private ArrayList<User> userList = new ArrayList<>();
    private ArrayList<NewDraft> draftList = new ArrayList<>();
    private ArrayList<Sent> sentList = new ArrayList<>();

    private int s_id = 0;
    private int r_id = 0;

    private Uri signatureUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_view_user_resend);

        myToast = new MyToast();
        db = new AppDatabase(this);
        txtLetterContent = findViewById(R.id.txtLetterContent);

        try {
            Bundle b = getIntent().getExtras();
            s_id = b.getInt("s_id");
            userList = db.getLogInUser();
            sentList = db.getSent(s_id);
            draftList = db.getNewDraft(userList.get(0).getA_id());
            r_id = sentList.get(0).getR_id();

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
        switch(item.getItemId()) {
            case R.id.send:
                sendLetter();

                break;
        }

        return super.onOptionsItemSelected(item);
    }

//    private void servicePayment() {
//        final Dialog paymentDialog = new Dialog(this);
//        paymentDialog.setContentView(R.layout.layout_payment);
//        paymentDialog.setCancelable(false);
//
//        final TextView txtMessage = paymentDialog.findViewById(R.id.txtMessage);
//        Button btnPay = paymentDialog.findViewById(R.id.btnPay);
//        Button btnCancel = paymentDialog.findViewById(R.id.btnCancel);
//
//        try {
//            ArrayList<Price> priceList = db.getPrice();
//
//            if(!priceList.isEmpty()) {
//                txtMessage.setText("You need to pay \u20B1" + priceList.get(0).getP_price() + " to send this letter.");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        btnPay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sendLetter();
//                paymentDialog.dismiss();
//            }
//        });
//
//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                paymentDialog.dismiss();
//            }
//        });
//
//        paymentDialog.show();
//    }

    private void sendLetter() {
//        int a_id = userList.get(0).getA_id();
//        int rep_id = draftList.get(0).getRep_id();
//        int rec_id = draftList.get(0).getRec_id();
//
//        try {
//            db.addPastRecipient(a_id, rec_id);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        db.updateResendSent(s_id, txtLetterContent.getText().toString(), "PENDING");
        db.updateResendReceive(r_id, txtLetterContent.getText().toString(), "PENDING");
//        s_id = db.addSent(
//                dateToday,
//                txtLetterContent.getText().toString(),
//                t_id,
//                a_id,
//                rep_id,
//                rec_id,
//                0,
//                0,
//                "PENDING"
//        );

//        r_id = db.addReceive(
//                dateToday,
//                txtLetterContent.getText().toString(),
//                t_id,
//                a_id,
//                rep_id,
//                rec_id,
//                0,
//                s_id,
//                "PENDING"
//        );
//
//        db.updateSentRID(s_id, r_id);
//        ArrayList<User> aList = db.getUser(a_id);
//        ArrayList<User> recList = db.getUser(rec_id);
//
//        String stype = "Sent";
//        String stitle = "Request Notification";
//        String smessage = "You sent letter to " +recList.get(0).getA_fname()+ " " +recList.get(0).getA_lname();
//        db.addNotification(stype, stitle, dateToday, smessage, a_id, 0, s_id, r_id);
//
//        String rtype = "Receive";
//        String rtitle = "Request Notification";
//        String rmessage = "You received a new request from " +aList.get(0).getA_fname()+ " " +aList.get(0).getA_lname();
//        db.addNotification(rtype, rtitle, dateToday, rmessage, rec_id, 0, s_id, r_id);
//
//        db.addPayment(dateToday, "PayPal", "20", a_id, rec_id, s_id, r_id);

        db.deleteAllNewDraft();

        myToast.makeToast(this, "Letter Resend", "SUCCESS");
        Intent mainScreen = new Intent(this, LandingScreenActivity.class);
        mainScreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainScreen);

//        try {
//            String message = "This is a notification message example.";
//            NotificationCompat.Builder builder = new NotificationCompat.Builder(
//                    NewViewUserResendActivity.this)
//                    .setSmallIcon(R.drawable.authorized_logo)
//                    .setContentTitle("Sent Notification")
//                    .setContentText(smessage)
//                    .setAutoCancel(true);

            /*Intent intent = new Intent(NewViewProgressActivity.this, ASentActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);*/
//            intent.putExtra("message", message);

            /*PendingIntent pendingIntent = PendingIntent.getActivity(NewViewProgressActivity.this,
                    0,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);*/

//            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//            notificationManager.notify(0, builder.build());
//
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
    }
}
