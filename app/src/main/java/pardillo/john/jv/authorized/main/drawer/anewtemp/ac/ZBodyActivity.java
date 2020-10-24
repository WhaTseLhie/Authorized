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

import java.util.ArrayList;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.pojo.AcTemplate;
import pardillo.john.jv.authorized.database.pojo.User;
import pardillo.john.jv.authorized.style.MyToast;

public class ZBodyActivity extends AppCompatActivity implements TextWatcher {

    private TextView txtLetterHeading, txtLetterSal, txtLetterSenderName, txtLetterRep;
    private EditText txtLetterClaim, txtLetterReason;

    private String letterSal = "", letterSenderName = "", letterRep = "", letterClaim = "", letterReason = "";
    private String let12 = "";

    private ArrayList<User> userList = new ArrayList<>();
    private ArrayList<AcTemplate> acList = new ArrayList<>();

    private AppDatabase db;
    private MyToast myToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zbody);

        myToast = new MyToast();
        db = new AppDatabase(this);

        txtLetterHeading = findViewById(R.id.txtLetterHeading);
        txtLetterSal = findViewById(R.id.txtLetterSal);
        txtLetterSenderName = findViewById(R.id.txtLetterSenderName);
        txtLetterRep = findViewById(R.id.txtLetterRep);
        txtLetterClaim = findViewById(R.id.txtLetterClaim);
        txtLetterReason = findViewById(R.id.txtLetterReason);

        userList = db.getLogInUser();
        acList = db.getAcTemplate(userList.get(0).getA_id());

        if(!acList.isEmpty()) {
            txtLetterHeading.setText(acList.get(0).getAc_letterContent());
        }

        ////////////////////////////////////
//        txtLetterClaim.setText("to claim my TOR");
//        txtLetterReason.setText("I am unable to claim due to this reason");
        ////////////////////////////////////

        try {
            Bundle b = getIntent().getExtras();
            let12 = b.getString("let12");
            txtLetterHeading.setText(let12);
        } catch (Exception e) {
            e.printStackTrace();
        }

        txtLetterClaim.addTextChangedListener(this);
        txtLetterReason.addTextChangedListener(this);
    }

    public void changeLetterHeading() {
        try {
            letterSal = txtLetterSal.getText().toString();
            letterSenderName = txtLetterSenderName.getText().toString();
            letterRep = txtLetterRep.getText().toString();
            letterClaim = txtLetterClaim.getText().toString();
            letterReason = txtLetterReason.getText().toString();

            StringBuilder letterHeading = new StringBuilder();
            letterHeading
                    .append(let12)
                    .append(letterSal)
                    .append("\n\n")
                    .append(letterSenderName)
                    .append(letterRep)
                    .append(letterClaim);

            if (!TextUtils.isEmpty(letterReason.trim())) {
                if (!TextUtils.equals(letterClaim.charAt(letterClaim.length() - 1) + "", ".")) {
                    letterHeading.append(". ");
                }
            }

            letterHeading
                    .append(letterReason);

            if (!TextUtils.isEmpty(letterReason.trim())) {
                if (!TextUtils.equals(letterReason.charAt(letterReason.length() - 1) + "", ".")) {
                    letterHeading.append(". ");
                }
            }

            txtLetterHeading.setText(letterHeading);
        }catch(Exception e) {
            e.printStackTrace();
        }
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
            if(TextUtils.isEmpty(letterClaim) || TextUtils.isEmpty(letterReason)) {
                myToast.makeToast(this, "Please fill all fields", "ERROR");
            } else {
                myToast.makeToast(this, "Next", "SUCCESS");
                db.updateAcTemplate(txtLetterHeading.getText().toString(), userList.get(0).getA_id());

                Intent cnsBody = new Intent(this, ZClosingNSign.class);
                cnsBody.putExtra("let123", txtLetterHeading.getText().toString());
                this.startActivity(cnsBody);
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
