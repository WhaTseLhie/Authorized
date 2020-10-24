package pardillo.john.jv.authorized.main.drawer.areceive;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.pojo.Receive;
import pardillo.john.jv.authorized.database.pojo.Rep;
import pardillo.john.jv.authorized.database.pojo.User;
import pardillo.john.jv.authorized.style.MyToast;

public class AddFeedbackActivity extends AppCompatActivity implements View.OnClickListener {

//    private LinearLayout linearLayoutSDate, linearLayoutEDate, linearLayout;
    private LinearLayout linearLayout;
    private EditText txtMessage;
    private TextView txtSDate, txtEDate;
    private Button btnSave, btnCancel;
//    private ScrollView scrollView;
    private Snackbar snackbar, snackbar2;

    private AppDatabase db;
    private MyToast myToast;
    private ArrayList<Receive> receiveList = new ArrayList<>();
    private ArrayList<User> userList = new ArrayList<>();

    private int r_id;
    private String status = "", f_date = "", f_sdate = "", f_edate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feedback);

        myToast = new MyToast();
        db = new AppDatabase(this);
        userList = db.getLogInUser();

//        scrollView = findViewById(R.id.scrollView);
        linearLayout = findViewById(R.id.linearLayout);
        txtSDate = findViewById(R.id.txtSDate);
        txtEDate = findViewById(R.id.txtEDate);
        txtMessage = findViewById(R.id.txtMessage);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        try {
            Bundle b = getIntent().getExtras();
            r_id = b.getInt("r_id");
            status = b.getString("status");
            receiveList = db.getReceive(r_id);

            if(status.equals("ACCEPTED")) {
                // initialize start date to date today
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
                f_date = sdf.format(c.getTime());
                f_sdate = sdf.format(c.getTime());
                txtSDate.setText(f_sdate);

//                // initialize end date 7 days from start date
//                c.add(Calendar.DATE, 7);
//                f_edate = sdf.format(c.getTime());
//                txtEDate.setText(f_edate);

                // NOT YET CLAIMED
                f_edate = "Not Claimed";
                txtEDate.setText(f_edate);
            } else {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
                f_date = sdf.format(c.getTime());
                f_sdate = sdf.format(c.getTime());
                txtSDate.setText(f_sdate);

                // NOT YET CLAIMED
                f_edate = "Request Declined";
                txtEDate.setText(f_edate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(status.equals("FINISHED")) {
            txtEDate.setOnClickListener(this);
        }
        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.txtEDate:
                try {
                    InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
                } catch(Exception e) {
                    e.printStackTrace();
                }

                if(snackbar != null && snackbar.isShown()) {
                    snackbar.dismiss();
                }

                if(snackbar2 != null && snackbar2.isShown()) {
                    snackbar2.dismiss();
                }

                selectEndDate();

                break;
            case R.id.btnSave:
                String f_title = "";
                String f_message = txtMessage.getText().toString();
                int a_id = receiveList.get(0).getA_id();
                int rec_id = receiveList.get(0).getRec_id();
                int s_id = receiveList.get(0).getS_id();
                int r_id = receiveList.get(0).getR_id();

                if(validateMessage()) {
                    db.updateSentStatus(receiveList.get(0).getS_id(), status);
                    db.updateReceiveStatus(receiveList.get(0).getR_id(), status);
                    db.addFeedback(f_title, f_date, f_sdate, f_edate, f_message, a_id, rec_id, s_id, r_id);
                    myToast.makeToast(this, "Request " +status, "SUCCESS");

                    ArrayList<User> aList = db.getUser(receiveList.get(0).getA_id());
                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss", Locale.getDefault());
                    String dateToday = sdf.format(c);

                    String stype = "Sent";
                    String stitle = "Request Progress Notification";
                    String smessage = "Your request have been " +status.toLowerCase()+ " by " +userList.get(0).getA_fname()+ " " +userList.get(0).getA_lname();
                    db.addNotification(stype, stitle, dateToday, smessage, a_id, 0, s_id, r_id);


//                    int sentOTP = receiveList.get(0).getR_otp();
                    String refCode = getRefCode();
                    ArrayList<Rep> repList = db.getRep(aList.get(0).getA_id(), receiveList.get(0).getRep_id());
                    android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
                    String otpMessage = "Claim stub:\n"+
                            "Claim To:"+userList.get(0).getA_fname()+ " " +userList.get(0).getA_lname()+ "\n" +
                            "Claim Request Date: "+f_sdate+ "\n" +
                            "REF: "+refCode;
                            smsManager.sendTextMessage(repList.get(0).getRep_contact(), null, otpMessage, null, null);
                            smsManager.sendTextMessage(aList.get(0).getA_contact(), null, otpMessage, null, null);
                            smsManager.sendTextMessage(userList.get(0).getA_contact(), null, otpMessage, null, null);


//                    myToast.makeToast(this, "Verification Code Sent to " +phone, "SUCCESS");


                    String rtype = "Receive";
                    String rtitle = "Request Progress Notification";
                    String rmessage = "You " +status.toLowerCase()+ " the request of " +aList.get(0).getA_fname()+ " " +aList.get(0).getA_lname();
                    db.addNotification(rtype, rtitle, dateToday, rmessage, rec_id, 0, s_id, r_id);

                    Intent intent = new Intent();
                    intent.putExtra("status", status);
                    this.setResult(Activity.RESULT_OK, intent);
                    this.finish();
                }

                break;
            case R.id.btnCancel:
                this.finish();

                break;
        }
    }

    private void selectEndDate() {
        snackbar2 = Snackbar.make(linearLayout, "", Snackbar.LENGTH_INDEFINITE);
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar2.getView();
        TextView textView = layout.findViewById(android.support.design.R.id.snackbar_text);
        textView.setVisibility(View.INVISIBLE);

        LayoutInflater inflater = LayoutInflater.from(this);
        View snackView = inflater.inflate(R.layout.layout_letter_snackbar_calendar, null);
        CalendarView calendarView = snackView.findViewById(R.id.calendarView);

        /// set date
        String dateParts[] = f_edate.split("/");
        int day = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int year = Integer.parseInt(dateParts[2]);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, (month-1));
        calendar.set(Calendar.DAY_OF_MONTH, day);
        long milliTime = calendar.getTimeInMillis();
        calendarView.setDate(milliTime, true, true);
        calendarView.setMinDate(milliTime - 1000);

//        /// set minimum date
//        String sdateParts[] = f_sdate.split("/");
//        int sday = Integer.parseInt(sdateParts[0]);
//        int smonth = Integer.parseInt(sdateParts[1]);
//        int syear = Integer.parseInt(sdateParts[2]);
//        Calendar scalendar = Calendar.getInstance();
//        scalendar.set(Calendar.YEAR, syear);
//        scalendar.set(Calendar.MONTH, (smonth-1));
//        scalendar.set(Calendar.DAY_OF_MONTH, sday+1);
//        long smilliTime = scalendar.getTimeInMillis();
//        calendarView.setMinDate(smilliTime - 1000);
//        ///

        Button btnCancel = snackView.findViewById(R.id.button);
        Button btnDone = snackView.findViewById(R.id.button2);
        layout.setPadding(0, 0, 0, 0);
        layout.addView(snackView, 0);
        snackbar2.show();

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtEDate.setText(f_edate);
                snackbar2.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar2.dismiss();
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                f_edate = dayOfMonth +"/"+ (month+1) +"/"+ year;
            }
        });
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

























