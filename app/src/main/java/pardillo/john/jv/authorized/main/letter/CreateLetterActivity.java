package pardillo.john.jv.authorized.main.letter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.pojo.Draft;
import pardillo.john.jv.authorized.database.pojo.Rep;
import pardillo.john.jv.authorized.database.pojo.User;
import pardillo.john.jv.authorized.main.LandingScreenActivity;
import pardillo.john.jv.authorized.main.letter.recipient.LetterRecipientActivity;
import pardillo.john.jv.authorized.main.letter.representative.LetterRepresentativeActivity;
import pardillo.john.jv.authorized.style.MyToast;

public class CreateLetterActivity extends AppCompatActivity {
//public class CreateLetterActivity extends AppCompatActivity implements View.OnClickListener {

    /*private static final int CODE_SELECT_RECIPIENT = 20;
    private static final int CODE_SELECT_REPRESENTATIVE = 15;

    private ScrollView scrollView;
    private TextView txtDate, txtAddress, txtRecipient, txtReason, txtClosing, txtYourName;
    private ImageView ivSignature;

    // LETTER DATA
    private String letterDate = "", letterAddress = "", letterReason = "", letterClosing = "";
    private Snackbar snackbar;

    private AppDatabase db;
    private ArrayList<User> userList = new ArrayList<>();
    private ArrayList<Draft> draftList = new ArrayList<>();

    private MyToast myToast;

    private int a_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letter_create);

        db = new AppDatabase(this);
        userList = db.getLogInUser();
        myToast = new MyToast();
        a_id = userList.get(0).getA_id();
        draftList = db.getDraft(userList.get(0).getA_id());

//        myToast.makeToast(this, "myid=" +userList.get(0).getA_id(), "NORMAL");

        scrollView = findViewById(R.id.scrollView);
        txtDate = findViewById(R.id.txtDate);
        txtAddress = findViewById(R.id.txtAddress);
        txtRecipient = findViewById(R.id.txtRecipient);
        txtReason = findViewById(R.id.txtReason);
        txtClosing = findViewById(R.id.txtClosing);
        txtYourName = findViewById(R.id.txtYourName);
        ivSignature = findViewById(R.id.ivSignature);
        txtYourName.setText(userList.get(0).getA_fname() +" "+ userList.get(0).getA_lname());
        ivSignature.setImageURI(Uri.parse(userList.get(0).getA_signature()));

        if(draftList.isEmpty()) {
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy", Locale.getDefault());
            letterDate = sdf.format(c);
            db.addDraft(letterDate,
                    "",
                    "",
                    "",
//                    txtAddress.getText().toString(),
//                    txtReason.getText().toString(),
//                    txtClosing.getText().toString(),
                    userList.get(0).getA_signature(),
                    0,
                    a_id,
                    0,
                    0,
                    0);
        } else {
            letterDate = draftList.get(0).getD_date();
            letterAddress = draftList.get(0).getD_address();
            letterReason = draftList.get(0).getD_reason();
            letterClosing = draftList.get(0).getD_closing();
            int rec_id = draftList.get(0).getRec_id();
            int rep_id = draftList.get(0).getRep_id();

            if(TextUtils.isEmpty(letterDate)) {
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                letterDate = sdf.format(c);
            } else {
                txtDate.setText(draftList.get(0).getD_date());
            }

            if(!TextUtils.isEmpty(letterAddress)) {
                txtAddress.setText(draftList.get(0).getD_address());
            }

            if(rec_id != 0 ) {
                ArrayList<User> recipientList = db.getUser(rec_id);
                txtRecipient.setText(recipientList.get(0).getA_fname() +" "+ recipientList.get(0).getA_lname());
            }

            if(rep_id != 0 ) {
                ArrayList<Rep> repList = db.getRep(userList.get(0).getA_id(), rep_id);
                txtReason.setText(repList.get(0).getRep_fname() +" "+ repList.get(0).getRep_lname());
            }

            if(!TextUtils.isEmpty(letterReason)) {
                txtReason.setText(draftList.get(0).getD_reason());
            }

            if(!TextUtils.isEmpty(letterClosing)) {
                txtClosing.setText(draftList.get(0).getD_closing());
            }

//            ivSignature.setImageURI(draftList.get(0).getD_signature());

            *//*if (TextUtils.equals(txtDate.getText().toString(), "Date") ||
                    TextUtils.equals(txtAddress.getText().toString(), "Address") ||
                    TextUtils.equals(txtRecipient.getText().toString(), "Recipient") ||
                    TextUtils.equals(txtReason.getText().toString(), "Closing") ||
                    TextUtils.equals(txtClosing.getText().toString(), "Closing")) {
                db.deleteDraft(a_id);
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                letterDate = sdf.format(c);
                db.addDraft("", "", "", "", "", 0, a_id, 0, 0, 0);
            }*//*
        }

        txtDate.setOnClickListener(this);
        txtAddress.setOnClickListener(this);
        txtRecipient.setOnClickListener(this);
        txtReason.setOnClickListener(this);
        txtClosing.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.txtDate:
                txtDate.setText(letterDate);
                selectDate();

                break;
            case R.id.txtAddress:
                selectAddress();

                break;
            case R.id.txtRecipient:
                Intent selectRecipient = new Intent(this, LetterRecipientActivity.class);
                startActivityForResult(selectRecipient, CODE_SELECT_RECIPIENT);

                break;
            case R.id.txtReason:
//                myToast.makeToast(this, "Select Reason", "NORMAL");
                Intent selectRep = new Intent(this, LetterRepresentativeActivity.class);
                startActivityForResult(selectRep, CODE_SELECT_REPRESENTATIVE);

                break;
            case R.id.txtClosing:
                myToast.makeToast(this, "Select Closing", "NORMAL");
                txtClosing.setText("Sincerely yours,");
                db.updateDraftClosing(userList.get(0).getA_id(), "Sincerely yours,");

                break;
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
                validateLetter();

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void selectDate() {
        snackbar = Snackbar.make(scrollView, "", Snackbar.LENGTH_INDEFINITE);
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
        TextView textView = layout.findViewById(android.support.design.R.id.snackbar_text);
        textView.setVisibility(View.INVISIBLE);

        LayoutInflater inflater = LayoutInflater.from(this);
        View snackView = inflater.inflate(R.layout.layout_letter_snackbar_calendar, null);
        CalendarView calendarView = snackView.findViewById(R.id.calendarView);
        calendarView.setMinDate(System.currentTimeMillis() - 1000);
        ///
        String dateParts[] = letterDate.split("/");
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
                txtDate.setText(letterDate);
                db.updateDraftDate(userList.get(0).getA_id(), letterDate);
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
                letterDate = dayOfMonth +"/"+ (month+1) +"/"+ year;
            }
        });
    }

    private void selectAddress() {
        final Dialog addressDialog = new Dialog(this);
        addressDialog.setContentView(R.layout.layout_letter_address);

        final EditText txtLetterAddress = addressDialog.findViewById(R.id.txtLetterAddress);
        ImageView ivSave = addressDialog.findViewById(R.id.ivSave);

        if(!TextUtils.isEmpty(letterAddress)) {
            txtLetterAddress.setText(letterAddress);
        }

        ivSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                letterAddress = txtLetterAddress.getText().toString();

                if(TextUtils.isEmpty(letterAddress)) {
                    myToast.makeToast(CreateLetterActivity.this, "Must not be empty", "ERROR");
                } else {
                    txtAddress.setText(letterAddress);
                    db.updateDraftAddress(userList.get(0).getA_id(), letterAddress);
                    addressDialog.dismiss();
                }
            }
        });

        addressDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            db = new AppDatabase(this);
//            ArrayList<Draft> draftList = db.getDraft(userList.get(0).getA_id());
            draftList = db.getDraft(userList.get(0).getA_id());

            switch(requestCode) {
                case CODE_SELECT_RECIPIENT:
                    if(!draftList.isEmpty()) {
                        ArrayList<User> recList = db.getUser(draftList.get(0).getRec_id());
                        txtRecipient.setText(recList.get(0).getA_fname() +" "+ recList.get(0).getA_lname());
                        /// GG DRI
                    }

                    break;
                case CODE_SELECT_REPRESENTATIVE:
                    if(!draftList.isEmpty()) {
                        ArrayList<Rep> repList = db.getRep(draftList.get(0).getRep_id());
                        txtReason.setText(repList.get(0).getRep_fname() +" "+ repList.get(0).getRep_lname());
                        // GG DIRI
                    }

                    break;
            }
        }

        this.finish();
        this.startActivity(getIntent());
    }

    private void validateLetter() {
        draftList = db.getDraft(userList.get(0).getA_id());
        String d_date = draftList.get(0).getD_date();
        String d_address = draftList.get(0).getD_address();
        String d_reason = draftList.get(0).getD_reason();
        String d_closing = draftList.get(0).getD_closing();
        String d_signature = draftList.get(0).getD_signature().toString();
        int temp_id = draftList.get(0).getTemp_id();
        int a_id = draftList.get(0).getA_id();
        int rep_id = draftList.get(0).getRep_id();
        int rec_id = draftList.get(0).getRec_id();
        int c_id = draftList.get(0).getC_id();

        if(TextUtils.isEmpty(d_date) ||
                TextUtils.isEmpty(d_address) ||
//                        TextUtils.isEmpty(d_reason) ||
                TextUtils.isEmpty(d_closing) ||
                rep_id == 0 ||
                rec_id == 0*//* ||
                        c_id == 0*//*) {
            myToast.makeToast(this, "Please complete the content of your letter", "ERROR");
        } else {
            final Dialog paymentDialog = new Dialog(this);
            paymentDialog.setContentView(R.layout.layout_payment);
            paymentDialog.setCancelable(false);

            final TextView txtMessage = paymentDialog.findViewById(R.id.txtMessage);
            Button btnPay = paymentDialog.findViewById(R.id.btnPay);
            Button btnCancel = paymentDialog.findViewById(R.id.btnCancel);

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
    }

    private void sendLetter() {
        draftList = db.getDraft(userList.get(0).getA_id());
        String d_date = draftList.get(0).getD_date();
        String d_address = draftList.get(0).getD_address();
        String d_reason = draftList.get(0).getD_reason();
        String d_closing = draftList.get(0).getD_closing();
        String d_signature = draftList.get(0).getD_signature().toString();
        int temp_id = draftList.get(0).getTemp_id();
        int a_id = draftList.get(0).getA_id();
        int rep_id = draftList.get(0).getRep_id();
        int rec_id = draftList.get(0).getRec_id();
        int c_id = draftList.get(0).getC_id();

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy", Locale.getDefault());
        String dateToday = sdf.format(c);

        int s_id = db.addSent(
                dateToday,
                d_date,
                d_address,
                d_reason,
                d_closing,
                d_signature,
                temp_id,
                a_id,
                rep_id,
                rec_id,
                c_id,
                0,
                0,
                "PENDING"
        );

        int r_id = db.addReceive(
                dateToday,
                d_date,
                d_address,
                d_reason,
                d_closing,
                d_signature,
                temp_id,
                a_id,
                rep_id,
                rec_id,
                c_id,
                0,
                s_id,
                "PENDING"
        );

        db.updateSentRID(s_id, r_id);

        ArrayList<User> aList = db.getUser(draftList.get(0).getA_id());
        ArrayList<User> recList = db.getUser(draftList.get(0).getRec_id());

        String stype = "Sent";
        String stitle = "Request Notification";
        String smessage = "You sent letter to " +recList.get(0).getA_fname()+ " " +recList.get(0).getA_lname();
        db.addNotification(stype, stitle, dateToday, smessage, a_id, 0, s_id, r_id);

        String rtype = "Receive";
        String rtitle = "Request Notification";
        String rmessage = "You received a new request from " +aList.get(0).getA_fname()+ " " +aList.get(0).getA_lname();
        db.addNotification(rtype, rtitle, dateToday, rmessage, rec_id, 0, s_id, r_id);

        db.addPayment(dateToday, "PayPal", "20", a_id, rec_id, s_id, r_id);

        myToast.makeToast(this, "Letter Sent", "SUCCESS");
        db.deleteDraft(a_id);
        Intent mainScreen = new Intent(this, LandingScreenActivity.class);
        mainScreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainScreen);
    }*/
}
















