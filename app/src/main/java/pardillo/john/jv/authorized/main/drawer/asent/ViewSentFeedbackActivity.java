package pardillo.john.jv.authorized.main.drawer.asent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.pojo.Feedback;
import pardillo.john.jv.authorized.style.MyToast;

public class ViewSentFeedbackActivity extends AppCompatActivity {

    private TextView txtSDate, txtEDate, txtTitle, txtMessage;
    private Button btnOk;

    private AppDatabase db;
    private MyToast myToast;
    private ArrayList<Feedback> feedbackList = new ArrayList<>();

    private int s_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sent_feedback);

        db = new AppDatabase(this);
        myToast = new MyToast();

//        txtTitle = findViewById(R.id.txtTitle);
        txtSDate = findViewById(R.id.txtSDate);
        txtEDate = findViewById(R.id.txtEDate);
        txtMessage = findViewById(R.id.txtMessage);
        btnOk = findViewById(R.id.btnOk);

        try {
            Bundle b = getIntent().getExtras();
            s_id = b.getInt("s_id");
            feedbackList = db.getSentFeedback(s_id);

//            txtTitle.setText(feedbackList.get(0).getF_title());
            txtSDate.setText(feedbackList.get(0).getF_sdate());
            txtEDate.setText(feedbackList.get(0).getF_edate());
            txtMessage.setText(feedbackList.get(0).getF_message());
        } catch (Exception e) {
            e.printStackTrace();
            this.finish();
        }

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewSentFeedbackActivity.this.finish();
            }
        });
    }
}