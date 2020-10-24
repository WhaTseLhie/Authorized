package pardillo.john.jv.authorized.main.drawer.areceive;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.pojo.User;
import pardillo.john.jv.authorized.main.letter.recipient.ViewRecipientReviewActivity;
import pardillo.john.jv.authorized.style.CircleTransform;
import pardillo.john.jv.authorized.style.MyToast;

public class ViewReceiveAuthActivity extends AppCompatActivity {

    private ImageView iv;
    private TextView txtReview, txtFullName, txtContact, txtGender;
    private RatingBar rbRating;

    private AppDatabase db;
    private ArrayList<User> authList = new ArrayList<>();
    private ArrayList<User> userList = new ArrayList<>();

    private MyToast myToast;
    int a_id = 0;
    int r_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_receive_auth);

        db = new AppDatabase(this);
        myToast = new MyToast();
        userList = db.getLogInUser();

        Bundle b = getIntent().getExtras();

        try {
            a_id = b.getInt("a_id");
            r_id = b.getInt("r_id");
            authList = db.getUser(a_id);

            this.setTitle(authList.get(0).getA_fname() +"\'s Info");
        } catch (Exception e) {
            e.printStackTrace();
        }

        iv = findViewById(R.id.imageView);
        rbRating = findViewById(R.id.ratingBar);
        txtReview = findViewById(R.id.txtReview);
        txtFullName = findViewById(R.id.txtFullName);
        txtContact = findViewById(R.id.txtContact);
        txtGender = findViewById(R.id.txtGender);

//        iv.setImageURI(authList.get(0).getA_image());
        Picasso.with(getApplicationContext()).load(authList.get(0).getA_image()).transform(new CircleTransform()).into(iv);
        float aveRating = db.getAverageRating(a_id);
        rbRating.setRating(aveRating);
        txtReview.setText("View ratings and reviews (" +aveRating+ ")");
        txtFullName.setText(authList.get(0).getA_fname() +" "+ authList.get(0).getA_lname());
        txtContact.setText(authList.get(0).getA_contact());
        txtGender.setText(authList.get(0).getA_gender());

        txtReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewRecReview = new Intent(ViewReceiveAuthActivity.this, ViewReceiveAuthReviewActivity.class);
                viewRecReview.putExtra("a_id", a_id);
                startActivity(viewRecReview);
            }
        });

        /*btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(this, "backr_id:" +r_id, Toast.LENGTH_LONG).show();
                Intent ridIntent = new Intent();
                ridIntent.putExtra("r_id", r_id);
                ViewReceiveAuthActivity.this.setResult(Activity.RESULT_OK, ridIntent);
                ViewReceiveAuthActivity.this.finish();
            }
        });*/
    }

    /*@Override
    public void onBackPressed() {
        Intent ridIntent = new Intent();
        ridIntent.putExtra("r_id", r_id);
        this.setResult(Activity.RESULT_OK, ridIntent);
        this.finish();
    }*/
}
