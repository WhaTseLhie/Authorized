package pardillo.john.jv.authorized.main.drawer.areceive;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
import pardillo.john.jv.authorized.database.pojo.Receive;
import pardillo.john.jv.authorized.style.MyToast;

public class UpdateFeedbackActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout linearLayout, linearLayoutSDate, linearLayoutEDate;
    private EditText txtTitle, txtMessage;
    private TextView txtSDate, txtEDate;
    private Button btnUpdate, btnCancel;
    private ScrollView scrollView;
    private Snackbar snackbar, snackbar2;

    private AppDatabase db;
    private MyToast myToast;
    private ArrayList<Feedback> feedbackList = new ArrayList<>();

    private int f_id;
    private String f_sdate = "", f_edate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_feedback);

        db = new AppDatabase(this);
        myToast = new MyToast();

        scrollView = findViewById(R.id.scrollView);
        linearLayout = findViewById(R.id.linearLayout);
        txtTitle = findViewById(R.id.txtTitle);
        linearLayoutSDate = findViewById(R.id.linearLayoutSDate);
        linearLayoutEDate = findViewById(R.id.linearLayoutEDate);
        txtSDate = findViewById(R.id.txtSDate);
        txtEDate = findViewById(R.id.txtEDate);
        txtMessage = findViewById(R.id.txtMessage);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnCancel = findViewById(R.id.btnCancel);

        try {
            Bundle b = getIntent().getExtras();
            f_id = b.getInt("f_id");
            feedbackList = db.getFeedback(f_id);

            txtTitle.setText(feedbackList.get(0).getF_title());

            f_sdate = feedbackList.get(0).getF_sdate();
            txtSDate.setText(f_sdate);

            f_edate = feedbackList.get(0).getF_edate();
            txtEDate.setText(f_edate);

            txtMessage.setText(feedbackList.get(0).getF_message());

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(!feedbackList.get(0).getF_sdate().contains("No")) {
            linearLayoutSDate.setOnClickListener(this);
            linearLayoutEDate.setOnClickListener(this);
        }
        btnUpdate.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.linearLayoutSDate:
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

                selectStartDate();

                break;
            case R.id.linearLayoutEDate:
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
            case R.id.btnUpdate:
                String f_title = txtTitle.getText().toString();
                String f_message = txtMessage.getText().toString();

                if(validateMessage() && validateTitle()) {
                    db.updateFeedback(f_id, f_title, f_sdate, f_edate, f_message);
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

    private void selectStartDate() {
        snackbar = Snackbar.make(linearLayout, "", Snackbar.LENGTH_INDEFINITE);
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
        TextView textView = layout.findViewById(android.support.design.R.id.snackbar_text);
        textView.setVisibility(View.INVISIBLE);

        LayoutInflater inflater = LayoutInflater.from(this);
        View snackView = inflater.inflate(R.layout.layout_letter_snackbar_calendar, null);
        CalendarView calendarView = snackView.findViewById(R.id.calendarView);
        calendarView.setMinDate(System.currentTimeMillis() - 1000);
        ///
        String dateParts[] = f_sdate.split("/");
        int day = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int year = Integer.parseInt(dateParts[2]);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, (month-1));
        calendar.set(Calendar.DAY_OF_MONTH, day);
        long milliTime = calendar.getTimeInMillis();
        calendarView.setDate(milliTime, true, true);
        ///
        Button btnCancel = snackView.findViewById(R.id.button);
        Button btnDone = snackView.findViewById(R.id.button2);
        layout.setPadding(0, 0, 0, 0);
        layout.addView(snackView, 0);
        snackbar.show();

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateSDate(f_sdate)) {
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy", Locale.getDefault());

                    try {
                        c.setTime(sdf.parse(f_sdate));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    // change end date 7 days from start date
                    c.add(Calendar.DATE, 7);
                    f_edate = sdf.format(c.getTime());
                    txtEDate.setText(f_edate);
                }

                txtSDate.setText(f_sdate);
                snackbar.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                f_sdate = dayOfMonth +"/"+ (month+1) +"/"+ year;
            }
        });
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

        /// set minimum date
        String sdateParts[] = f_sdate.split("/");
        int sday = Integer.parseInt(sdateParts[0]);
        int smonth = Integer.parseInt(sdateParts[1]);
        int syear = Integer.parseInt(sdateParts[2]);
        Calendar scalendar = Calendar.getInstance();
        scalendar.set(Calendar.YEAR, syear);
        scalendar.set(Calendar.MONTH, (smonth-1));
        scalendar.set(Calendar.DAY_OF_MONTH, sday+1);
        long smilliTime = scalendar.getTimeInMillis();
        calendarView.setMinDate(smilliTime - 1000);
        ///

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

    private boolean validateTitle() {
        String title = txtTitle.getText().toString();

        if (title.isEmpty()) {
            txtTitle.setError("Field can't be empty");
            return false;
        } else {
            txtTitle.setError(null);
            return true;
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

    public boolean validateSDate(String sdate) {
        boolean isBeforeEDate = false;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date startDate = null;
        Date endDate = null;

        try {
            startDate = sdf.parse(sdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            endDate = sdf.parse(txtEDate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            if(startDate.before(endDate)) {
                isBeforeEDate = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isBeforeEDate;
    }
}

























