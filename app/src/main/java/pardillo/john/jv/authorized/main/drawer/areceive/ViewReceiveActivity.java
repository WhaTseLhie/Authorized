package pardillo.john.jv.authorized.main.drawer.areceive;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.pojo.Feedback;
import pardillo.john.jv.authorized.database.pojo.LetterReceiveContent;
import pardillo.john.jv.authorized.database.pojo.LetterReceiveDetails;
import pardillo.john.jv.authorized.database.pojo.LetterSentContent;
import pardillo.john.jv.authorized.database.pojo.LetterSentDetails;
import pardillo.john.jv.authorized.database.pojo.Rating;
import pardillo.john.jv.authorized.database.pojo.Receive;
import pardillo.john.jv.authorized.database.pojo.Rep;
import pardillo.john.jv.authorized.database.pojo.User;
import pardillo.john.jv.authorized.style.MyToast;

public class ViewReceiveActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int CODE_ADD_FEEDBACK = 30;

    private EditText txtLetterContents;
    private Button btnAccept, btnDecline, btnFinish;

    private AppDatabase db;
    private MyToast myToast;

    private ArrayList<Receive> receiveList = new ArrayList<>();
    private ArrayList<User> userList = new ArrayList<>();
    private ArrayList<User> recList = new ArrayList<>();
    private ArrayList<User> aList = new ArrayList<>();
    private ArrayList<LetterReceiveDetails> lsdList = new ArrayList<>();
    private ArrayList<LetterReceiveContent> lscList = new ArrayList<>();

    private String dateToday = "";
    private int r_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_receive);

        myToast = new MyToast();
        db = new AppDatabase(this);

        txtLetterContents = findViewById(R.id.txtLetterContents);
        btnAccept = findViewById(R.id.btnAccept);
        btnDecline = findViewById(R.id.btnDecline);
        btnFinish = findViewById(R.id.btnFinish);
        userList = db.getLogInUser();

        try {
            Bundle b = getIntent().getExtras();
            r_id = b.getInt("r_id");
            recList = db.getUser(receiveList.get(0).getRec_id());
            lsdList = db.getLetterReceiveDetailsRID(r_id);
            lscList = db.getLetterReceiveContentRID(r_id);
            receiveList = db.getReceive(r_id);

            txtLetterContents.setText(receiveList.get(0).getR_letter());

            if(TextUtils.equals(receiveList.get(0).getR_status(), "PENDING")) {
                btnDecline.setVisibility(View.VISIBLE);
                btnAccept.setVisibility(View.VISIBLE);
            } else {
                btnDecline.setVisibility(View.INVISIBLE);
                btnAccept.setVisibility(View.INVISIBLE);
            }

            if(TextUtils.equals(receiveList.get(0).getR_status(), "ACCEPTED")) {
                btnFinish.setVisibility(View.VISIBLE);
            } else {
                btnFinish.setVisibility(View.INVISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnAccept.setOnClickListener(this);
        btnDecline.setOnClickListener(this);
        btnFinish.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {/*
            case R.id.txtReason:
                ArrayList<Rep> repList = db.getRep(r_id);

                if(repList.isEmpty()) {
                    myToast.makeToast(this, "Representative has been deleted", "WARNING");
                } else {
                    Intent viewRep = new Intent(this, ViewReceiveRepActivity.class);
                    viewRep.putExtra("rep_id", receiveList.get(0).getRep_id());
                    viewRep.putExtra("r_id", r_id);
                    startActivityForResult(viewRep, CODE_VIEW_REPRESENTATIVE);
                }

                break;
            case R.id.txtYourName:
                Intent viewRec = new Intent(this, ViewReceiveAuthActivity.class);
                viewRec.putExtra("a_id", receiveList.get(0).getA_id());
                viewRec.putExtra("r_id", r_id);
                startActivityForResult(viewRec, CODE_VIEW_RECIPIENT);

                break;*/
            case R.id.btnAccept:
                AlertDialog.Builder acceptBuilder = new AlertDialog.Builder(this);
                acceptBuilder.setTitle("Accept Request Confirmation");
                acceptBuilder.setMessage("Do you really want to accept this request?");
                acceptBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        Intent addFeedback = new Intent(ViewReceiveActivity.this, AddFeedbackActivity.class);
                        addFeedback.putExtra("r_id", receiveList.get(0).getR_id());
                        addFeedback.putExtra("status", "ACCEPTED");
                        startActivityForResult(addFeedback, CODE_ADD_FEEDBACK);
                    }
                });
                acceptBuilder.setNegativeButton("No", null);

                AlertDialog acceptDialog = acceptBuilder.create();
                acceptDialog.show();

                break;
            case R.id.btnDecline:
                AlertDialog.Builder declineBuilder = new AlertDialog.Builder(this);
                declineBuilder.setTitle("Decline Request Confirmation");
                declineBuilder.setMessage("Do you really want to decline this request?");
                declineBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        Intent addFeedback = new Intent(ViewReceiveActivity.this, AddFeedbackActivity.class);
                        addFeedback.putExtra("r_id", receiveList.get(0).getR_id());
                        addFeedback.putExtra("status", "DECLINED");
                        startActivityForResult(addFeedback, CODE_ADD_FEEDBACK);
                    }
                });
                declineBuilder.setNegativeButton("No", null);

                AlertDialog declineDialog = declineBuilder.create();
                declineDialog.show();

                break;
            case R.id.btnFinish:
                ArrayList<Feedback> feedbackList = db.getReceiveFeedback(receiveList.get(0).getR_id());

                if(validateStartDate(feedbackList.get(0).getF_sdate())) {
                    AlertDialog.Builder finishBuilder = new AlertDialog.Builder(this);
                    finishBuilder.setTitle("Finish Request Confirmation");
                    finishBuilder.setMessage("Request Finish?");
                    finishBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.updateSentStatus(receiveList.get(0).getS_id(), "FINISHED");
                            db.updateReceiveStatus(receiveList.get(0).getR_id(), "FINISHED");
                            myToast.makeToast(ViewReceiveActivity.this, "Request Transaction Finished", "SUCCESS");
                            dialog.dismiss();

                            Intent declineIntent = new Intent();
                            declineIntent.putExtra("status", "FINISHED");
                            ViewReceiveActivity.this.setResult(Activity.RESULT_OK, declineIntent);

                            ArrayList<User> aList = db.getUser(receiveList.get(0).getA_id());
                            Date c = Calendar.getInstance().getTime();
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                            String dateToday = sdf.format(c);

                            int a_id = receiveList.get(0).getA_id();
                            int rec_id = receiveList.get(0).getRec_id();
                            int s_id = receiveList.get(0).getS_id();
                            String stype = "Sent";
                            String stitle = "Request Progress Notification";
                            String smessage = "Your request sent to " + userList.get(0).getA_fname() + " " + userList.get(0).getA_lname() + " is finished";
                            db.addNotification(stype, stitle, dateToday, smessage, a_id, 0, s_id, r_id);

                            String rtype = "Receive";
                            String rtitle = "Request Progress Notification";
                            String rmessage = "The request of " + aList.get(0).getA_fname() + " " + aList.get(0).getA_lname() + " is finish";
                            db.addNotification(rtype, rtitle, dateToday, rmessage, rec_id, 0, s_id, r_id);

                            ViewReceiveActivity.this.finish();
                            startActivity(ViewReceiveActivity.this.getIntent());
                        }
                    });
                    finishBuilder.setNegativeButton("No", null);

                    AlertDialog finishDialog = finishBuilder.create();
                    finishDialog.show();
                } else {
                    AlertDialog.Builder messageBuilder = new AlertDialog.Builder(this);
                    messageBuilder.setTitle("Request Finish Error");
                    messageBuilder.setMessage("Transaction Start Date: " +feedbackList.get(0).getF_sdate()+ "\nDate Today: " +dateToday+ "\nPlease wait for the representative to claim the documents after transaction start date before finishing the request.");
                    messageBuilder.setNeutralButton("Ok", null);

                    AlertDialog messageDialog = messageBuilder.create();
                    messageDialog.show();
                }

                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.feedback_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.feedback:
                String status = receiveList.get(0).getR_status();

                if(TextUtils.equals("PENDING", status)) {
                    myToast.makeToast(this, "Please accept your request first to add transaction information", "NORMAL");
                } else {
                    ArrayList<Feedback> feedbackList = db.getReceiveFeedback(r_id);

                    if(feedbackList.isEmpty()) {
                        myToast.makeToast(this, "You already deleted your feedback", "NORMAL");
                    } else {
                        Intent viewFeedback = new Intent(this, ViewReceiveFeedbackActivity.class);
                        viewFeedback.putExtra("r_id", receiveList.get(0).getR_id());
                        startActivity(viewFeedback);
                    }
                }

                break;
            case R.id.delete:
                String rstatus = receiveList.get(0).getR_status();

                if(TextUtils.equals("PENDING", rstatus) || TextUtils.equals("ACCEPTED", rstatus)) {
                    myToast.makeToast(this, "You can only delete if the status is DECLINED or FINISHED", "NORMAL");
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Delete Confirmation");
                    builder.setMessage("Do you really want to delete this letter?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                            db.deleteReceive(receiveList.get(0).getR_id());
                            Intent intent = new Intent();
                            intent.putExtra("delete", "delete");
                            ViewReceiveActivity.this.setResult(Activity.RESULT_OK, intent);
                            ViewReceiveActivity.this.finish();
                        }
                    });
                    builder.setNegativeButton("No", null);

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == CODE_ADD_FEEDBACK) {
                try {
                    Bundle b = data.getExtras();
                    String fstatus = b.getString("status");
                    this.finish();
                    this.startActivity(getIntent());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean validateStartDate(String startDate) {
        boolean isAfterStartDate = false;
        dateToday = "";
        Date f_date = null;
        Date f_sdate = null;

        // initialize start date to date today
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy", Locale.getDefault());
        dateToday = sdf.format(c.getTime());

        try {
            f_date = sdf.parse(dateToday);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            f_sdate = sdf.parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(f_date.after(f_sdate)) {
            isAfterStartDate = true;
        }

        if(f_date.compareTo(f_sdate) == 0) {
            isAfterStartDate = true;
        }

        return isAfterStartDate;
    }
}