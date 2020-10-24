package pardillo.john.jv.authorized.main.drawer.areceive;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.pojo.Feedback;
import pardillo.john.jv.authorized.database.pojo.Receive;
import pardillo.john.jv.authorized.style.MyToast;

public class ViewReceiveFeedbackActivity extends AppCompatActivity {

    private static final int CODE_EDIT_FEEDBACK = 7;

    private TextView txtSDate, txtEDate, txtTitle, txtMessage;
    private Button btnOk;

    private AppDatabase db;
    private MyToast myToast;
    private ArrayList<Feedback> feedbackList = new ArrayList<>();

    private int r_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_receive_feedback);

        db = new AppDatabase(this);
        myToast = new MyToast();

//        txtTitle = findViewById(R.id.txtTitle);
        txtSDate = findViewById(R.id.txtSDate);
        txtEDate = findViewById(R.id.txtEDate);
        txtMessage = findViewById(R.id.txtMessage);
        btnOk = findViewById(R.id.btnOk);

        try {
            Bundle b = getIntent().getExtras();
            r_id = b.getInt("r_id");
            feedbackList = db.getReceiveFeedback(r_id);

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
                ViewReceiveFeedbackActivity.this.finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_feedback_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.delete:
                AlertDialog.Builder deleteBuilder = new AlertDialog.Builder(this);
                deleteBuilder.setTitle("Delete Confirmation");
                deleteBuilder.setMessage("Do you really want to delete this Feedback?");
                deleteBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteFeedback(feedbackList.get(0).getF_id());
                        myToast.makeToast(ViewReceiveFeedbackActivity.this, "Feedback Deleted", "SUCCESS");
                        ViewReceiveFeedbackActivity.this.finish();
                        dialog.dismiss();
                    }
                });
                deleteBuilder.setNegativeButton("No", null);

                AlertDialog deleteDialog = deleteBuilder.create();
                deleteDialog.show();

                break;
            case R.id.edit:
                Intent editFeedback = new Intent(this, UpdateFeedbackActivity.class);
                editFeedback.putExtra("f_id", feedbackList.get(0).getF_id());
                startActivityForResult(editFeedback, CODE_EDIT_FEEDBACK);

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            switch(requestCode) {
                case CODE_EDIT_FEEDBACK:
                    this.finish();
                    this.startActivity(getIntent());

                    break;
            }
        }
    }
}
