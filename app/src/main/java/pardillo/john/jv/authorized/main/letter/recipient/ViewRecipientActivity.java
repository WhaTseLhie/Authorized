package pardillo.john.jv.authorized.main.letter.recipient;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.pojo.User;
import pardillo.john.jv.authorized.main.letter.CreateLetterActivity;
import pardillo.john.jv.authorized.style.CircleTransform;
import pardillo.john.jv.authorized.style.MyToast;

public class ViewRecipientActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv;
    private TextView txtReview, txtFullName, txtContact, txtGender;
    private RatingBar rbRating;

    private AppDatabase db;
    private ArrayList<User> recipientList = new ArrayList<>();
    private ArrayList<User> userList = new ArrayList<>();

    private MyToast myToast;

    int rec_id = 0;
    int a_id = 0;
    ///////////////////////
//    int t_id = 0;
//    int lc_id = 0;
    ///////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipient);

        db = new AppDatabase(this);
        myToast = new MyToast();
        userList = db.getLogInUser();

        iv = findViewById(R.id.imageView);
        rbRating = findViewById(R.id.ratingBar);
        txtReview = findViewById(R.id.txtReview);
        txtFullName = findViewById(R.id.txtFullName);
        txtContact = findViewById(R.id.txtContact);
        txtGender = findViewById(R.id.txtGender);

        Bundle b = getIntent().getExtras();

        try {
            rec_id = b.getInt("rec_id");
            a_id = b.getInt("a_id");
            ///////////////////////////////
//            t_id = b.getInt("t_id");
//            lc_id = b.getInt("lc_id");
            ///////////////////////////////
            recipientList = db.getUser(rec_id);

            this.setTitle(recipientList.get(0).getA_fname() +"\'s Info");

//            iv.setImageURI(recipientList.get(0).getA_image());
            Picasso.with(getApplicationContext()).load(recipientList.get(0).getA_image()).transform(new CircleTransform()).into(iv);
            float aveRating = db.getAverageRating(rec_id);
            rbRating.setRating(aveRating);
            txtReview.setText("View ratings and reviews (" +aveRating+ ")");
            txtFullName.setText(recipientList.get(0).getA_fname() +" "+ recipientList.get(0).getA_lname());
            txtContact.setText(recipientList.get(0).getA_contact());
            txtGender.setText(recipientList.get(0).getA_gender());
        } catch (Exception e) {
            e.printStackTrace();
        }

        txtReview.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.choose_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.choose:
                myToast.makeToast(this, recipientList.get(0).getA_fname() +" is chosen", "SUCCESS");
                db.updateNewDraftRec(a_id, rec_id);
                ////////////////////////////
//                db.updateLetterContentTextLCID(lc_id, recipientList.get(0).getA_id()+"", t_id, userList.get(0).getA_id());
                ////////////////////////////
                Intent intent = new Intent();
                this.setResult(Activity.RESULT_OK, intent);
                this.finish();

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.txtReview:
                Intent viewRecReview = new Intent(this, ViewRecipientReviewActivity.class);
                viewRecReview.putExtra("rec_id", rec_id);
                startActivity(viewRecReview);

                break;
        }
    }
}




















