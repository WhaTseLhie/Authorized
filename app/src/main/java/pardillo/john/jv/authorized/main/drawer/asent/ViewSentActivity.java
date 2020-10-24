package pardillo.john.jv.authorized.main.drawer.asent;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.PersistableBundle;
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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.pojo.Feedback;
import pardillo.john.jv.authorized.database.pojo.LetterContent;
import pardillo.john.jv.authorized.database.pojo.LetterSentContent;
import pardillo.john.jv.authorized.database.pojo.LetterSentDetails;
import pardillo.john.jv.authorized.database.pojo.Rating;
import pardillo.john.jv.authorized.database.pojo.Rep;
import pardillo.john.jv.authorized.database.pojo.Sent;
import pardillo.john.jv.authorized.database.pojo.User;
import pardillo.john.jv.authorized.style.MyToast;

public class ViewSentActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtLetterContents;
    private Button btnRate, btnView;

    private Dialog ratingDialog;

    private AppDatabase db;
    private MyToast myToast;

    private ArrayList<Sent> sentList = new ArrayList<>();
    private ArrayList<User> recList = new ArrayList<>();
    private ArrayList<LetterSentDetails> lsdList = new ArrayList<>();
    private ArrayList<LetterSentContent> lscList = new ArrayList<>();

    private int s_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sent);

        myToast = new MyToast();
        db = new AppDatabase(this);

        btnRate = findViewById(R.id.btnRate);
        btnView = findViewById(R.id.btnView);
        txtLetterContents = findViewById(R.id.txtLetterContents);

        try {
            Bundle b = getIntent().getExtras();
            s_id = b.getInt("s_id");
            recList = db.getUser(sentList.get(0).getRec_id());
            lsdList = db.getLetterSentDetailsSID(s_id);
            lscList = db.getLetterSentContentSID(s_id);
            sentList = db.getSent(s_id);


            txtLetterContents.setText(sentList.get(0).getS_letter());
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
                            ViewSentActivity.this.setResult(Activity.RESULT_OK, intent);
                            ViewSentActivity.this.finish();
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
















