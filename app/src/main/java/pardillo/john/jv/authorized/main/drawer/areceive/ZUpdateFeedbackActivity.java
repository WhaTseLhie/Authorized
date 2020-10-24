package pardillo.john.jv.authorized.main.drawer.areceive;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.pojo.Feedback;
import pardillo.john.jv.authorized.database.pojo.Receive;
import pardillo.john.jv.authorized.database.pojo.Rep;
import pardillo.john.jv.authorized.database.pojo.User;
import pardillo.john.jv.authorized.style.MyToast;

public class ZUpdateFeedbackActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout linearLayout;
    private EditText txtMessage;
    private TextView txtSDate, txtEDate;
    private Button btnSave, btnCancel;
    private ScrollView scrollView;
    private Snackbar snackbar, snackbar2;

    private AppDatabase db;
    private MyToast myToast;
    private ArrayList<Feedback> feedbackList = new ArrayList<>();
    private ArrayList<Receive> receiveList = new ArrayList<>();
    private ArrayList<User> userList = new ArrayList<>();

    private int r_id;
    private int f_id;
    private String f_sdate = "", f_edate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zupdate_feedback);

        db = new AppDatabase(this);
        myToast = new MyToast();

        scrollView = findViewById(R.id.scrollView);
        linearLayout = findViewById(R.id.linearLayout);
        txtSDate = findViewById(R.id.txtSDate);
        txtEDate = findViewById(R.id.txtEDate);
        txtMessage = findViewById(R.id.txtMessage);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        try {
            Bundle b = getIntent().getExtras();
            r_id = b.getInt("r_id");
            receiveList = db.getReceive(r_id);
            userList = db.getLogInUser();
            ArrayList<Feedback> feedbackList = db.getReceiveFeedback(receiveList.get(0).getR_id());
            f_id = feedbackList.get(0).getF_id();

            f_sdate = feedbackList.get(0).getF_sdate();
            txtSDate.setText(f_sdate);

            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
            String f_date = sdf.format(c.getTime());

            txtEDate.setText(f_date);

            txtMessage.setText(feedbackList.get(0).getF_message());

        } catch (Exception e) {
            e.printStackTrace();
        }

        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnSave:
                String f_message = txtMessage.getText().toString();

                if (validateMessage()) {
                    db.updateSentStatus(receiveList.get(0).getS_id(), "FINISHED");
                    db.updateReceiveStatus(receiveList.get(0).getR_id(), "FINISHED");
                    myToast.makeToast(this, "Request Transaction Finished", "SUCCESS");

//                    Intent declineIntent = new Intent();
//                    declineIntent.putExtra("status", "FINISHED");
//                    this.setResult(Activity.RESULT_OK, declineIntent);

                    ArrayList<User> aList = db.getUser(receiveList.get(0).getA_id());
                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
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

                    ////////////////////////////////////
                    db.updateFeedback(f_id, "", f_sdate, txtEDate.getText().toString(), f_message);
                    myToast.makeToast(this, "Feedback Updated", "SUCCESS");
                    Intent intent = new Intent();
                    this.setResult(Activity.RESULT_OK, intent);
                    this.finish();
                }

                break;
            case R.id.btnCancel:
                this.finish();

                break;
        }
    }

    private boolean validateMessage() {
        String message = txtMessage.getText().toString();

        if (message.isEmpty()) {
            txtMessage.setError("Field can't be empty");
            return false;
        } else {
            txtMessage.setError(null);
            return true;
        }
    }
}