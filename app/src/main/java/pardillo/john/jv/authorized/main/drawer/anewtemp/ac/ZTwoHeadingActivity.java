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
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.pojo.AcTemplate;
import pardillo.john.jv.authorized.database.pojo.User;
import pardillo.john.jv.authorized.style.MyToast;

public class ZTwoHeadingActivity extends AppCompatActivity implements TextWatcher {

    private LinearLayout llRecipient;
    private TextView txtLetterHeading, txtLetterRecName;
    private EditText txtLetterRecSt, txtLetterRecCity, txtLetterRecProvince, txtLetterRecZip;

    private String recName = "", recSt = "", recCity = "", recProvince = "", recZip = "";
    private String let1 = "";

    private ArrayList<User> userList = new ArrayList<>();
    private ArrayList<AcTemplate> acList = new ArrayList<>();

    private AppDatabase db;
    private MyToast myToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ztwo_heading);

        myToast = new MyToast();
        db = new AppDatabase(this);

        llRecipient = findViewById(R.id.llRecipient);
        txtLetterHeading = findViewById(R.id.txtLetterHeading);
        txtLetterRecName = findViewById(R.id.txtLetterRecName);
        txtLetterRecSt = findViewById(R.id.txtLetterRecSt);
        txtLetterRecCity = findViewById(R.id.txtLetterRecCity);
        txtLetterRecProvince = findViewById(R.id.txtLetterRecProvince);
        txtLetterRecZip = findViewById(R.id.txtLetterRecZip);

        userList = db.getLogInUser();
        acList = db.getAcTemplate(userList.get(0).getA_id());

        if(!acList.isEmpty()) {
            txtLetterHeading.setText(acList.get(0).getAc_letterContent());
        }

        ////////////////////////////////////
//        txtLetterRecSt.setText("Colon St.");
//        txtLetterRecCity.setText("Cebu");
//        txtLetterRecProvince.setText("Cebu");
//        txtLetterRecZip.setText("6000");
        ////////////////////////////////////

        try {
            Bundle b = getIntent().getExtras();
            let1 = b.getString("let1");
            txtLetterHeading.setText(let1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        txtLetterRecSt.addTextChangedListener(this);
        txtLetterRecCity.addTextChangedListener(this);
        txtLetterRecProvince.addTextChangedListener(this);
        txtLetterRecZip.addTextChangedListener(this);
    }

    public void changeLetterHeading() {
        recName = txtLetterRecName.getText().toString();
        recSt = txtLetterRecSt.getText().toString();
        recCity = txtLetterRecCity.getText().toString();
        recProvince = txtLetterRecProvince.getText().toString();
        recZip = txtLetterRecZip.getText().toString();

        StringBuilder letterHeading = new StringBuilder();
        letterHeading
                .append(let1)
                .append(recName)
                .append("\n")
                .append(recSt)
                .append("\n")
                .append(recCity);

        if (TextUtils.isEmpty(recCity)) {
            letterHeading.append(" ");
        } else {
            letterHeading.append(", ");
        }

        letterHeading
                .append(recProvince)
                .append(" ")
                .append(recZip)
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
            myToast.makeToast(this, "Next", "SUCCESS");
            db.updateAcTemplate(txtLetterHeading.getText().toString(), userList.get(0).getA_id());

            Intent salBody = new Intent(this, ZBodyActivity.class);
            salBody.putExtra("let12", txtLetterHeading.getText().toString());
            this.startActivity(salBody);

            /*if(TextUtils.equals(recName, "Click to select Recipient")) {
                myToast.makeToast(this, "Please select a recipient", "ERROR");
            } else {
                myToast.makeToast(this, "Success", "SUCCESS");

                Intent salBody = new Intent(this, ZBodyActivity.class);
                salBody.putExtra("head12", txtLetterHeading.getText().toString());
                this.startActivity(salBody);
            }*/
        }

        return super.onOptionsItemSelected(item);
    }
}