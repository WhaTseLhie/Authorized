package pardillo.john.jv.authorized.main.drawer.anewtemp.ac;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.pojo.AcTemplate;
import pardillo.john.jv.authorized.database.pojo.User;
import pardillo.john.jv.authorized.style.MyToast;

public class ZClosingNSign extends AppCompatActivity {

    private TextView txtLetterHeading, txtLetterClosing, txtLetterSign, txtLetterSenderName;
    private String let123 = "";

    private ArrayList<User> userList = new ArrayList<>();
    private ArrayList<AcTemplate> acList = new ArrayList<>();

    private AppDatabase db;
    private MyToast myToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zclosing_nsign);

        myToast = new MyToast();
        db = new AppDatabase(this);

        txtLetterHeading = findViewById(R.id.txtLetterHeading);
        txtLetterClosing = findViewById(R.id.txtLetterClosing);
        txtLetterSign = findViewById(R.id.txtLetterSign);
        txtLetterSenderName = findViewById(R.id.txtLetterSenderName);

        try {
            Bundle b = getIntent().getExtras();
            let123 = b.getString("let123");
            userList = db.getLogInUser();
            acList = db.getAcTemplate(userList.get(0).getA_id());

            if(!acList.isEmpty()) {
                txtLetterHeading.setText(acList.get(0).getAc_letterContent());
            }

            StringBuilder letterContent = new StringBuilder();
            letterContent
                    .append(let123)
                    .append("\n\n")
                    .append(txtLetterClosing.getText().toString())
                    .append("\n\n")
                    .append(txtLetterSign.getText().toString())
                    .append("\n\n")
                    .append(txtLetterSenderName.getText().toString());

            txtLetterHeading.setText(letterContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_progress_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.view) {
            myToast.makeToast(this, "View Progress", "SUCCESS");
            db.updateAcTemplate(txtLetterHeading.getText().toString(), userList.get(0).getA_id());

            Intent viewProgress = new Intent(this, ZViewProgressActivity.class);
            viewProgress.putExtra("letterContent", txtLetterHeading.getText().toString());
            this.startActivity(viewProgress);
        }

        return super.onOptionsItemSelected(item);
    }
}
