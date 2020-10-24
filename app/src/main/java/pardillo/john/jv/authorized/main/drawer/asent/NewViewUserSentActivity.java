package pardillo.john.jv.authorized.main.drawer.asent;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.pojo.Feedback;
import pardillo.john.jv.authorized.database.pojo.NewTemplate;
import pardillo.john.jv.authorized.database.pojo.Rating;
import pardillo.john.jv.authorized.database.pojo.Sent;
import pardillo.john.jv.authorized.database.pojo.User;
import pardillo.john.jv.authorized.style.MyToast;

public class NewViewUserSentActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtLetterContent;
    private Button btnRate, btnView;

    private Dialog ratingDialog;

    private AppDatabase db;
    private MyToast myToast;

    private ArrayList<User> userList = new ArrayList<>();
    private ArrayList<Sent> sentList = new ArrayList<>();
    private ArrayList<NewTemplate> templateList = new ArrayList<>();
    private ArrayList<User> recList = new ArrayList<>();

    private int s_id = 0;

    private Uri signatureUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_view_user_sent);

        myToast = new MyToast();
        db = new AppDatabase(this);

        txtLetterContent = findViewById(R.id.txtLetterContent);
        btnRate = findViewById(R.id.btnRate);
        btnView = findViewById(R.id.btnView);

        try {
            Bundle b = getIntent().getExtras();
            s_id = b.getInt("s_id");
            sentList = db.getSent(s_id);
            userList = db.getLogInUser();
            templateList = db.getNewTemplate(sentList.get(0).getT_id());
            recList = db.getUser(sentList.get(0).getRec_id());

            String strLetterContent = sentList.get(0).getS_letter();
            SpannableStringBuilder ssbLetterContent = new SpannableStringBuilder(strLetterContent);

            // SET SIGNATURE
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
//                int sign_start = strLetterContent.indexOf("[SIGN]");
//                signatureUri = userList.get(0).getA_image();
//                Uri authSignature = Uri.parse(userList.get(0).getA_signature());
//
//                ssbLetterContent.setSpan(
//                        new ImageSpan(this, signatureUri),
//                        sign_start,
//                        sign_start + "[SIGN]".length() + 1,
//                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

            txtLetterContent.setText(ssbLetterContent);
            txtLetterContent.setMovementMethod(LinkMovementMethod.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnRate.setOnClickListener(this);
        btnView.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sent_menu, menu);
//        getMenuInflater().inflate(R.menu.delete_menu, menu);

        if(sentList.get(0).getS_status().equals("DECLINED")) {
            menu.findItem(R.id.resend).setVisible(true);
        } else {
            menu.findItem(R.id.resend).setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.resend:
                try {
                    db.deleteAllNewDraft();
                    db.addNewDraft(
                            sentList.get(0).getSentDate(),
                            sentList.get(0).getS_letter(),
                            "",
                            "",
                            sentList.get(0).getT_id(),
                            sentList.get(0).getA_id(),
                            sentList.get(0).getRep_id(),
                            sentList.get(0).getRec_id()
                    );

                    Intent resendLetter = new Intent(this, NewViewUserResendActivity.class);
                    resendLetter.putExtra("s_id", sentList.get(0).getS_id());
                    startActivity(resendLetter);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
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
                            NewViewUserSentActivity.this.setResult(Activity.RESULT_OK, intent);
                            NewViewUserSentActivity.this.finish();
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

                txtTitle.setText("Review " +templateList.get(0).getT_type()+ " transaction of " +recList.get(0).getA_fname());
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
















