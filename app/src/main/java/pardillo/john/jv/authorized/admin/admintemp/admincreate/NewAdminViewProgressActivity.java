package pardillo.john.jv.authorized.admin.admintemp.admincreate;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.admin.admain.AdminLandingScreenActivity;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.pojo.ClickLetterContent;
import pardillo.john.jv.authorized.database.pojo.NewDraft;
import pardillo.john.jv.authorized.database.pojo.NewTemplate;
import pardillo.john.jv.authorized.database.pojo.Price;
import pardillo.john.jv.authorized.database.pojo.User;
import pardillo.john.jv.authorized.style.MyToast;

public class NewAdminViewProgressActivity extends AppCompatActivity {

    private TextView txtLetterContent;

    private AppDatabase db;
    private MyToast myToast;

    private ArrayList<User> userList = new ArrayList<>();
    private ArrayList<NewDraft> draftList = new ArrayList<>();
//    private ArrayList<NewTemplate> templateList = new ArrayList<>();

    private int s_id = 0;
    private int r_id = 0;

    private Uri signatureUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_admin_view_progress);

        myToast = new MyToast();
        db = new AppDatabase(this);
        txtLetterContent = findViewById(R.id.txtLetterContent);

        try {
            Bundle b = getIntent().getExtras();
            userList = db.getLogInUser();
            draftList = db.getNewDraft(userList.get(0).getA_id());
//            templateList = db.getNewTemplate(draftList.get(0).getT_id());

//            String strDraftContent = templateList.get(0).getT_letterContent();
            String strLetterContent = draftList.get(0).getD_letterContent();
            SpannableStringBuilder ssbLetterContent = new SpannableStringBuilder(strLetterContent);

            try {
                int clc_start = strLetterContent.indexOf("[SIGN]");
                signatureUri = userList.get(0).getA_image();
                ssbLetterContent.setSpan(
                        new ImageSpan(this, signatureUri),
                        clc_start,
                        clc_start + "[SIGN]".length() + 1,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } catch (Exception e) {
                e.printStackTrace();
            }

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
                servicePayment();

                break;
        }

        return super.onOptionsItemSelected(item);
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
                sendLetter();
                paymentDialog.dismiss();
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

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy", Locale.getDefault());
        String dateToday = sdf.format(c);

        myToast.makeToast(this, txtLetterContent.getText().toString(), "NORMAL");

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

        db.updateSentRID(s_id, r_id);
        myToast.makeToast(this, "Letter Sent", "SUCCESS");ArrayList<User> aList = db.getUser(a_id);
        ArrayList<User> recList = db.getUser(rec_id);

        String stype = "Sent";
        String stitle = "Request Notification";
        String smessage = "You sent letter to " +recList.get(0).getA_fname()+ " " +recList.get(0).getA_lname();
        db.addNotification(stype, stitle, dateToday, smessage, a_id, 0, s_id, r_id);

        String rtype = "Receive";
        String rtitle = "Request Notification";
        String rmessage = "You received a new request from " +aList.get(0).getA_fname()+ " " +aList.get(0).getA_lname();
        db.addNotification(rtype, rtitle, dateToday, rmessage, rec_id, 0, s_id, r_id);

        db.addPayment(dateToday, "PayPal", "20", a_id, rec_id, s_id, r_id);

        db.deleteAllNewDraft();
//        db.deleteDraft(t_id, a_id);
//        db.deleteLetterDetails(t_id, a_id);
//        db.deleteLetterContent(t_id, a_id);

        myToast.makeToast(this, "Letter Sent", "SUCCESS");
        Intent mainScreen = new Intent(this, AdminLandingScreenActivity.class);
        mainScreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainScreen);

        try {
//            String message = "This is a notification message example.";
            NotificationCompat.Builder builder = new NotificationCompat.Builder(
                    NewAdminViewProgressActivity.this)
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
}
