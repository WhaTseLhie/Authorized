package pardillo.john.jv.authorized.main.drawer.areceive;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import pardillo.john.jv.authorized.database.pojo.Receive;
import pardillo.john.jv.authorized.database.pojo.Rep;
import pardillo.john.jv.authorized.database.pojo.User;
import pardillo.john.jv.authorized.style.MyToast;

public class NewViewReceiveActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int CODE_ADD_FEEDBACK = 30;

    private ViewGroup rootLayout;
    private int r_id;

    private TextView tv[];
    private Button btnAccept, btnDecline, btnFinish;

    private AppDatabase db;
    private ArrayList<LetterReceiveDetails> lrdList = new ArrayList<>();
    private ArrayList<LetterReceiveContent> lrcList = new ArrayList<>();
    private ArrayList<Receive> receiveList = new ArrayList<>();
    private ArrayList<User> userList = new ArrayList<>();
    private ArrayList<User> aList = new ArrayList<>();

    private MyToast myToast;

    private String dateToday = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_view_receive);

        myToast = new MyToast();
        db = new AppDatabase(this);

        rootLayout = findViewById(R.id.view_root);
        btnAccept = findViewById(R.id.btnAccept);
        btnDecline = findViewById(R.id.btnDecline);
        btnFinish = findViewById(R.id.btnFinish);
        userList = db.getLogInUser();

        try {
            Bundle b = getIntent().getExtras();
            r_id = b.getInt("r_id");
            receiveList = db.getReceive(r_id);
            lrdList = db.getLetterReceiveDetailsRID(r_id);
            lrcList = db.getLetterReceiveContentRID(r_id);

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

            if(lrdList.isEmpty()) {
                this.finish();
                startActivity(getIntent());
            } else {
                tv = new TextView[lrcList.size()];
                aList = db.getUser(lrcList.get(1).getA_id());

                for(int i=0; i<lrcList.size(); i++) {
                    tv[i] = new TextView(this);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    params.leftMargin = lrcList.get(i).getC_leftMargin();
                    params.topMargin = lrcList.get(i).getC_topMargin();
//                    params.rightMargin = lrcList.get(i).getC_rightMargin();
//                    params.bottomMargin = lrcList.get(i).getC_bottomMargin();
                    tv[i].setPadding(15,8, 15, 8);
                    tv[i].setId(lrcList.get(i).getC_id());

                    ////////////////////////////////////////////////////////////
                    switch(lrcList.get(i).getC_type()) {
                        case "date":
                            tv[i].setText(lrcList.get(i).getC_name());

                            break;
                        case "text":
                            tv[i].setText(lrcList.get(i).getC_name());

                            break;
                        case "recipient":
                            String rec_id = lrcList.get(i).getC_name();
                            boolean isNumerec = TextUtils.isDigitsOnly(rec_id);

                            if(rec_id.equals("")) {
                                tv[i].setText(lrcList.get(i).getC_name());
                            } else {
                                if (isNumerec) {
                                    try {
                                        int recid = Integer.parseInt(rec_id);
                                        ArrayList<User> recipientList = db.getUser(Integer.parseInt(rec_id));
                                        tv[i].setText(recipientList.get(0).getA_fname() + " " + recipientList.get(0).getA_lname());
                                    } catch (NumberFormatException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    tv[i].setText(lrcList.get(i).getC_name());
                                }
                            }

                            break;
                        case "content":
                            String rep_id = lrcList.get(i).getC_name();
                            boolean isNumerep = TextUtils.isDigitsOnly(rep_id);

                            if(!rep_id.isEmpty() && isNumerep) {
                                try {
                                    int repid = Integer.parseInt(rep_id);
                                    ArrayList<Rep> repList = db.getRep(aList.get(0).getA_id(), Integer.parseInt(rep_id));
                                    tv[i].setText(repList.get(0).getRep_fname() + " " + repList.get(0).getRep_lname());
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                tv[i].setText(lrcList.get(i).getC_name());
                            }

                            break;
                        case "image":
                            String signature = aList.get(0).getA_signature();
                            tv[i].setText(signature);

                            break;
                        case "authorizer":
                            String name = aList.get(0).getA_fname() +" "+ aList.get(0).getA_lname();
                            tv[i].setText(name);

                            break;
                    }
                    ////////////////////////////////////////////////////////////

//                    tv[i].setBackgroundResource(R.drawable.style_text_view_border);
                    tv[i].setLayoutParams(params);
                    rootLayout.addView(tv[i]);
                }
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

                        Intent addFeedback = new Intent(NewViewReceiveActivity.this, AddFeedbackActivity.class);
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

                        Intent addFeedback = new Intent(NewViewReceiveActivity.this, AddFeedbackActivity.class);
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
                            myToast.makeToast(NewViewReceiveActivity.this, "Request Transaction Finished", "SUCCESS");
                            dialog.dismiss();

                            Intent declineIntent = new Intent();
                            declineIntent.putExtra("status", "FINISHED");
                            NewViewReceiveActivity.this.setResult(Activity.RESULT_OK, declineIntent);

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

                            NewViewReceiveActivity.this.finish();
                            startActivity(NewViewReceiveActivity.this.getIntent());
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
                            NewViewReceiveActivity.this.setResult(Activity.RESULT_OK, intent);
                            NewViewReceiveActivity.this.finish();
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