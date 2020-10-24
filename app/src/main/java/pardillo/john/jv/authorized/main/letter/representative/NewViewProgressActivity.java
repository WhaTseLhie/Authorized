package pardillo.john.jv.authorized.main.letter.representative;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.pojo.LetterContent;
import pardillo.john.jv.authorized.database.pojo.LetterDetails;
import pardillo.john.jv.authorized.database.pojo.Price;
import pardillo.john.jv.authorized.database.pojo.Rep;
import pardillo.john.jv.authorized.database.pojo.User;
import pardillo.john.jv.authorized.main.LandingScreenActivity;
import pardillo.john.jv.authorized.main.drawer.asent.ASentActivity;
import pardillo.john.jv.authorized.style.MyToast;

public class NewViewProgressActivity extends AppCompatActivity {

    private EditText txtLetterContents;
    private ViewGroup rootLayout;

    private AppDatabase db;
    private MyToast myToast;

    private ArrayList<User> userList = new ArrayList<>();
    private ArrayList<LetterDetails> ldList = new ArrayList<>();
    private ArrayList<LetterContent> lcList = new ArrayList<>();

    private int t_id = 0;
    private int s_id = 0;
    private int r_id = 0;
    private int repid = 0;
    private int recid = 0;

    private ImageView ivSignature;

    private StringBuilder letterProgress = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_view_progress);

        myToast = new MyToast();
        db = new AppDatabase(this);

        txtLetterContents = findViewById(R.id.txtLetterContents);
        rootLayout = findViewById(R.id.view_root);

        try {
            Bundle b = getIntent().getExtras();
            t_id = b.getInt("t_id");
            lcList = db.getLetterContent(t_id);
            ldList = db.getLetterDetails(t_id);

            if(lcList.isEmpty()) {
                myToast.makeToast(this, "Something went wrong", "ERROR");
                this.finish();
            } else {
                letterProgress = new StringBuilder("\n");
                userList = db.getUser(lcList.get(1).getA_id());

                for(int i=0; i<lcList.size(); i++) {
                    String ConType = lcList.get(i).getC_type();

                    if(TextUtils.equals("recipient", ConType)) {
                        String recip_id = lcList.get(i).getC_name();
                        recid = Integer.parseInt(recip_id);
                        ArrayList<User> recList = db.getUser(recid);
                        letterProgress.append(recList.get(0).getA_fname()).append(recList.get(0).getA_lname()).append("\n\n");
                    } else if(TextUtils.equals("content", ConType)) {
                        String repid_id = lcList.get(i).getC_name();
                        repid = Integer.parseInt(repid_id);
                        ArrayList<Rep> repList = db.getRep(userList.get(0).getA_id(), repid);
                        letterProgress.append(repList.get(0).getRep_fname()).append(repList.get(0).getRep_lname()).append("\n\n");
                    } else if(TextUtils.equals("image", ConType)) {
                        letterProgress.append("\n\n");
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        params.leftMargin = lcList.get(i).getC_leftMargin();
                        params.topMargin = lcList.get(i).getC_topMargin();
                        ivSignature = new ImageView(this);
                        ivSignature.setImageURI(Uri.parse(userList.get(0).getA_signature()));
                        ivSignature.setLayoutParams(params);
                        rootLayout.addView(ivSignature);
//                        letterProgress.append(userList.get(0).getA_signature()).append("\n\n");
                    } else if(TextUtils.equals("authorizer", ConType)) {
                        String authorizerName = userList.get(0).getA_fname() +" "+ userList.get(0).getA_lname();
                        letterProgress.append(authorizerName).append("\n\n");
                    } else {
                        letterProgress.append(lcList.get(i).getC_name()).append("\n\n");
                    }
                }

                txtLetterContents.setText(letterProgress);
                ivSignature.getLayoutParams().height = 150;
                ivSignature.getLayoutParams().width = 150;
            }
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
        int rep_id = repid;
        int rec_id = recid;

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy", Locale.getDefault());
        String dateToday = sdf.format(c);

        myToast.makeToast(this, txtLetterContents.getText().toString(), "NORMAL");

//        s_id = db.addSent(
//                dateToday,
//                txtLetterContents.getText().toString(),
//                t_id,
//                a_id,
//                rep_id,
//                rec_id,
//                0,
//                0,
//                "PENDING"
//        );
//
//        r_id = db.addReceive(
//                dateToday,
//                txtLetterContents.getText().toString(),
//                t_id,
//                a_id,
//                rep_id,
//                rec_id,
//                0,
//                s_id,
//                "PENDING"
//        );

        db.updateSentRID(s_id, r_id);

        /////////////////////////////////////////////////////////////////////////////////
        // SENT
        /////////////////////////////////////////////////////////////////////////////////
        db.addLetterSentDetails(
                ldList.get(0).getT_image().toString(),
                ldList.get(0).getT_title(),
                ldList.get(0).getT_date(),
                ldList.get(0).getT_type(),
                ldList.get(0).getT_id(),
                userList.get(0).getA_id(),
                s_id,
                r_id
        );

        ArrayList<LetterContent> lcList = db.getLetterContent(t_id);
        for(int i=0; i<lcList.size(); i++) {
            db.addLetterSentContent(
                    lcList.get(i).getC_name(),
                    lcList.get(i).getC_type(),
                    lcList.get(i).getC_leftMargin(),
                    lcList.get(i).getC_topMargin(),
                    lcList.get(i).getC_rightMargin(),
                    lcList.get(i).getC_bottomMargin(),
                    lcList.get(i).getLc_id(),
                    lcList.get(0).getT_id(),
                    userList.get(0).getA_id(),
                    s_id,
                    r_id
            );
        }
        /////////////////////////////////////////////////////////////////////////////////

        /////////////////////////////////////////////////////////////////////////////////
        // RECEIVE
        /////////////////////////////////////////////////////////////////////////////////
        db.addLetterReceiveDetails(
                ldList.get(0).getT_image().toString(),
                ldList.get(0).getT_title(),
                ldList.get(0).getT_date(),
                ldList.get(0).getT_type(),
                ldList.get(0).getT_id(),
                userList.get(0).getA_id(),
                s_id,
                r_id
        );


        ArrayList<LetterContent> letterContentList = db.getLetterContent(t_id);
        for(int i=0; i<letterContentList.size(); i++) {
            db.addLetterReceiveContent(
                    lcList.get(i).getC_name(),
                    lcList.get(i).getC_type(),
                    lcList.get(i).getC_leftMargin(),
                    lcList.get(i).getC_topMargin(),
                    lcList.get(i).getC_rightMargin(),
                    lcList.get(i).getC_bottomMargin(),
                    lcList.get(i).getLc_id(),
                    lcList.get(0).getT_id(),
                    userList.get(0).getA_id(),
                    s_id,
                    r_id
            );
        }
        /////////////////////////////////////////////////////////////////////////////////

        ArrayList<User> aList = db.getUser(a_id);
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

        db.deleteDraft(t_id, a_id);
        db.deleteLetterDetails(t_id, a_id);
        db.deleteLetterContent(t_id, a_id);

        myToast.makeToast(this, "Letter Sent", "SUCCESS");
        Intent mainScreen = new Intent(this, LandingScreenActivity.class);
        mainScreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainScreen);

        try {
//            String message = "This is a notification message example.";
            NotificationCompat.Builder builder = new NotificationCompat.Builder(
                    NewViewProgressActivity.this)
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




















