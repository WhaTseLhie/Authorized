package pardillo.john.jv.authorized.admin.vuser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.pojo.User;
import pardillo.john.jv.authorized.style.CircleTransform;
import pardillo.john.jv.authorized.style.MyToast;

public class AdminViewUserActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv;
    private TextView txtReview, txtFullName, txtContact, txtGender, lblGender;
    private RatingBar rbRating;

    private AppDatabase db;
    private ArrayList<User> aList = new ArrayList<>();

    private MyToast myToast;
    
    private int a_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_user);

        db = new AppDatabase(this);
        myToast = new MyToast();

        iv = findViewById(R.id.imageView);
        rbRating = findViewById(R.id.ratingBar);
        txtReview = findViewById(R.id.txtReview);
        txtFullName = findViewById(R.id.txtFullName);
        txtContact = findViewById(R.id.txtContact);
        lblGender = findViewById(R.id.lblGender);
        txtGender = findViewById(R.id.txtGender);

        try {
            Bundle b = getIntent().getExtras();
            a_id = b.getInt("a_id");
            aList = db.getUser(a_id);

            this.setTitle(aList.get(0).getA_fname() +"\'s Info");

//            iv.setImageURI(aList.get(0).getA_image());
            Picasso.with(getApplicationContext()).load(aList.get(0).getA_image()).transform(new CircleTransform()).into(iv);
            float aveRating = db.getAverageRating(a_id);
            rbRating.setRating(aveRating);
            txtReview.setText("View ratings and reviews (" +aveRating+ ")");
            txtFullName.setText(aList.get(0).getA_fname() +" "+ aList.get(0).getA_lname());
            txtContact.setText(aList.get(0).getA_contact());
            txtGender.setText(aList.get(0).getA_gender());

            if(aList.get(0).getA_lname().equals("")) {
                lblGender.setText("Address: ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        txtReview.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.txtReview:
                Intent viewUserReview = new Intent(this, AdminViewUserReviewActivity.class);
                viewUserReview.putExtra("a_id", a_id);
                startActivity(viewUserReview);

                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rep_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent viewRep = new Intent(this, AdminViewAllUserRepActivity.class);
        viewRep.putExtra("a_id", a_id);
        startActivity(viewRep);

        return super.onOptionsItemSelected(item);
    }
}

















