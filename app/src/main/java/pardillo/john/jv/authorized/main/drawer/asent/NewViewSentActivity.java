package pardillo.john.jv.authorized.main.drawer.asent;

import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.pojo.Feedback;
import pardillo.john.jv.authorized.database.pojo.LetterSentContent;
import pardillo.john.jv.authorized.database.pojo.LetterSentDetails;
import pardillo.john.jv.authorized.database.pojo.Rating;
import pardillo.john.jv.authorized.database.pojo.Rep;
import pardillo.john.jv.authorized.database.pojo.Sent;
import pardillo.john.jv.authorized.database.pojo.User;
import pardillo.john.jv.authorized.style.MyToast;

public class NewViewSentActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewGroup rootLayout;
    private int s_id;

    private TextView tv[];
    private Button btnRate, btnView;

    private Dialog ratingDialog;

    private AppDatabase db;
    private MyToast myToast;

    private ArrayList<LetterSentDetails> lsdList = new ArrayList<>();
    private ArrayList<LetterSentContent> lscList = new ArrayList<>();
    private ArrayList<Sent> sentList = new ArrayList<>();
    private ArrayList<User> recList = new ArrayList<>();
    private ArrayList<User> aList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_view_sent);

        myToast = new MyToast();
        db = new AppDatabase(this);
        rootLayout = findViewById(R.id.view_root);
        btnRate = findViewById(R.id.btnRate);
        btnView = findViewById(R.id.btnView);

        try {
            Bundle b = getIntent().getExtras();
            s_id = b.getInt("s_id");
            sentList = db.getSent(s_id);
            lsdList = db.getLetterSentDetailsSID(s_id);
            lscList = db.getLetterSentContentSID(s_id);

            if(lsdList.isEmpty()) {
                this.finish();
                startActivity(getIntent());
            } else {
                tv = new TextView[lscList.size()];
                aList = db.getUser(lscList.get(1).getA_id());

                for(int i=0; i<lscList.size(); i++) {
                    tv[i] = new TextView(this);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    params.leftMargin = lscList.get(i).getC_leftMargin();
                    params.topMargin = lscList.get(i).getC_topMargin();
//                    params.rightMargin = lscList.get(i).getC_rightMargin();
//                    params.bottomMargin = lscList.get(i).getC_bottomMargin();
                    tv[i].setPadding(15,8, 15, 8);
                    tv[i].setId(lscList.get(i).getC_id());
//                    tv[i].setText(lscList.get(i).getC_name());

                    ////////////////////////////////////////////////////////////
                    switch(lscList.get(i).getC_type()) {
                        case "date":
                            tv[i].setText(lscList.get(i).getC_name());

                            break;
                        case "text":
                            tv[i].setText(lscList.get(i).getC_name());

                            break;
                        case "recipient":
                            String rec_id = lscList.get(i).getC_name();
                            boolean isNumerec = TextUtils.isDigitsOnly(rec_id);

                            if(rec_id.equals("")) {
                                tv[i].setText(lscList.get(i).getC_name());
                            } else {
                                if (isNumerec) {
                                    try {
                                        int recid = Integer.parseInt(rec_id);
                                        recList = db.getUser(Integer.parseInt(rec_id));
                                        tv[i].setText(recList.get(0).getA_fname() + " " + recList.get(0).getA_lname());
                                    } catch (NumberFormatException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    tv[i].setText(lscList.get(i).getC_name());
                                }
                            }

                            break;
                        case "content":
                            String rep_id = lscList.get(i).getC_name();
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
                                tv[i].setText(lscList.get(i).getC_name());
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

        btnRate.setOnClickListener(this);
        btnView.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.delete:
                String status = sentList.get(0).getS_status();

                if(TextUtils.equals("PENDING", status) || TextUtils.equals("ACCEPTED", status)) {
                    myToast.makeToast(this, "You can only delete if the status is DECLINED or FINISHED", "NORMAL");
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Delete Confirmation");
                    builder.setMessage("Do you really want to delete this letter?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                            db.deleteSent(sentList.get(0).getS_id());
                            Intent intent = new Intent();
                            intent.putExtra("delete", "delete");
                            NewViewSentActivity.this.setResult(Activity.RESULT_OK, intent);
                            NewViewSentActivity.this.finish();
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
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnRate:
                ratingDialog = new Dialog(this);
                ratingDialog.setContentView(R.layout.layout_rating);

                TextView txtTitle = ratingDialog.findViewById(R.id.textView);
                ImageView iv = ratingDialog.findViewById(R.id.imageView);
                final RatingBar addRating = ratingDialog.findViewById(R.id.ratingBar);
                final TextView txtAddComment = ratingDialog.findViewById(R.id.textView2);
                Button btnReviewSave = ratingDialog.findViewById(R.id.button);
                Button btnReviewCancel = ratingDialog.findViewById(R.id.button2);

                txtTitle.setText("Review " +lsdList.get(0).getT_type()+ " transaction of " +recList.get(0).getA_fname());
                iv.setImageURI(recList.get(0).getA_image());

                final ArrayList<Rating> ratingList = db.getRatingList(sentList.get(0).getRate_id());
                if(!ratingList.isEmpty()) {
                    addRating.setRating(ratingList.get(0).getRate_rating());
                    txtAddComment.setText(ratingList.get(0).getRate_comment());
                    btnReviewSave.setText("UPDATE");
                }

                btnReviewSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        float rating = addRating.getRating();
                        String comment = txtAddComment.getText().toString();
                        String date = DateFormat.getDateInstance().format(new Date());
                        int a_id = sentList.get(0).getA_id();
                        int rec_id = sentList.get(0).getRec_id();
                        int rep_id = sentList.get(0).getRep_id();
                        int s_id = sentList.get(0).getS_id();
                        int r_id = sentList.get(0).getR_id();

                        if(!ratingList.isEmpty()) {
                            db.updateRating(sentList.get(0).getRate_id(), rating, comment);
                            db.updateReceiveRate(r_id, sentList.get(0).getRate_id());
                            db.updateSentRate(s_id, sentList.get(0).getRate_id());

                            myToast.makeToast(getApplicationContext(), "Review Updated", "SUCCESS");
                        } else {
                            int rate_id = db.addRating(a_id, rec_id, rep_id, s_id, r_id, rating, date, comment);
                            db.updateReceiveRate(r_id, rate_id);
                            db.updateSentRate(s_id, rate_id);

                            sentList.get(0).setR_id(r_id);

                            myToast.makeToast(getApplicationContext(), "Review Added", "SUCCESS");
                        }
                        ratingDialog.dismiss();
                        finish();
                        startActivity(getIntent());
                    }
                });

                btnReviewCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ratingDialog.dismiss();
                    }
                });

                ratingDialog.show();

                break;
            case R.id.btnView:
                if(TextUtils.equals(sentList.get(0).getS_status(), "PENDING")) {
                    myToast.makeToast(this, "Please wait for the recipient to accept your request", "NORMAL");
                } else {
                    ArrayList<Feedback> feedbackList = db.getSentFeedback(s_id);

                    if(feedbackList.isEmpty()) {
                        myToast.makeToast(this, "Feedback is deleted by the recipient", "NORMAL");
                    } else {
                        Intent viewFeedback = new Intent(this, ViewSentFeedbackActivity.class);
                        viewFeedback.putExtra("s_id", sentList.get(0).getS_id());
                        startActivity(viewFeedback);
                    }
                }

                break;
        }
    }
}
















