package pardillo.john.jv.authorized.main.drawer.anewtemp.ac;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.pojo.AcTemplate;
import pardillo.john.jv.authorized.database.pojo.User;
import pardillo.john.jv.authorized.style.MyToast;

public class ZOneHeadingActivity extends AppCompatActivity implements TextWatcher {

    private TextView txtLetterSenderName, txtLetterHeading, txtLetterDate;
    private EditText txtLetterSenderSt, txtLetterSenderCity, txtLetterSenderProvince, txtLetterSenderZip;

    private String senderName="", senderSt="", senderCity="", senderProvince="", senderZip="", letterDate = "", dateToday = "";

    private ArrayList<User> userList = new ArrayList<>();
    private ArrayList<AcTemplate> acList = new ArrayList<>();

    private AppDatabase db;
    private MyToast myToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zone_heading);

        myToast = new MyToast();
        db = new AppDatabase(this);

        txtLetterHeading = findViewById(R.id.txtLetterHeading);
        txtLetterSenderName = findViewById(R.id.txtLetterSenderName);
        txtLetterSenderSt = findViewById(R.id.txtLetterSenderSt);
        txtLetterSenderCity = findViewById(R.id.txtLetterSenderCity);
        txtLetterSenderProvince = findViewById(R.id.txtLetterSenderProvince);
        txtLetterSenderZip = findViewById(R.id.txtLetterSenderZip);
        txtLetterDate = findViewById(R.id.txtLetterDate);

        userList = db.getLogInUser();
        acList = db.getAcTemplate(userList.get(0).getA_id());

        if(!acList.isEmpty()) {
            txtLetterHeading.setText(acList.get(0).getAc_letterContent());
        }

        ////////////////////////////////////
//        txtLetterSenderSt.setText("Colon St.");
//        txtLetterSenderCity.setText("Cebu");
//        txtLetterSenderProvince.setText("Cebu");
//        txtLetterSenderZip.setText("6000");
        ////////////////////////////////////

        /*try {
            Date date = Calendar.getInstance().getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
            dateToday = sdf.format(date);
            txtLetterDate.setText(dateToday);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        txtLetterSenderSt.addTextChangedListener(this);
        txtLetterSenderCity.addTextChangedListener(this);
        txtLetterSenderProvince.addTextChangedListener(this);
        txtLetterSenderZip.addTextChangedListener(this);
    }

    public void changeLetterHeading() {
        senderName = txtLetterSenderName.getText().toString();
        senderSt = txtLetterSenderSt.getText().toString();
        senderCity = txtLetterSenderCity.getText().toString();
        senderProvince = txtLetterSenderProvince.getText().toString();
        senderZip = txtLetterSenderZip.getText().toString();
        letterDate = txtLetterDate.getText().toString();

        StringBuilder letterHeading = new StringBuilder();
        letterHeading
                .append(senderName)
                .append("\n")
                .append(senderSt)
                .append("\n")
                .append(senderCity);

        if(TextUtils.isEmpty(senderCity)) {
            letterHeading.append(" ");
        } else {
            letterHeading.append(", ");
        }

        letterHeading
                .append(senderProvince)
                .append(" ")
                .append(senderZip)
                .append("\n\n")
                .append(letterDate)
                .append("\n\n");

        txtLetterHeading.setText(letterHeading);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        changeLetterHeading();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.next_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.next) {
            if(TextUtils.isEmpty(senderSt) ||
                    TextUtils.isEmpty(senderCity) ||
                    TextUtils.isEmpty(senderProvince) ||
                    TextUtils.isEmpty(senderZip)) {
                myToast.makeToast(this, "Please fill all fields", "ERROR");
            } else {
                db.deleteAcTemplate(userList.get(0).getA_id());
                myToast.makeToast(this, "Next", "SUCCESS");
                db.addAcTemplate(txtLetterHeading.getText().toString(), userList.get(0).getA_id());

                Intent secHeading = new Intent(this, ZTwoHeadingActivity.class);
                secHeading.putExtra("let1", txtLetterHeading.getText().toString());
                this.startActivity(secHeading);
            }
        }

        return super.onOptionsItemSelected(item);
    }
}