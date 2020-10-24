package pardillo.john.jv.authorized.main.letter.recipient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.adapter.RatingAdapter;
import pardillo.john.jv.authorized.database.pojo.Rating;
import pardillo.john.jv.authorized.database.pojo.User;

public class ViewRecipientReviewActivity extends AppCompatActivity {

    private ArrayList<Rating> ratingList = new ArrayList<>();
    private RatingAdapter adapter;
    private AppDatabase db;

    private ListView lv;
    private TextView txtEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipient_review);

        db = new AppDatabase(this);

        try {
            Bundle b = getIntent().getExtras();
            int rec_id = b.getInt("rec_id");
            ratingList = db.getAllRating(rec_id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        txtEmpty = findViewById(R.id.txtEmpty);
        lv = findViewById(R.id.listView);

        adapter = new RatingAdapter(this, ratingList);
        lv.setAdapter(adapter);

        if(ratingList.isEmpty()) {
            txtEmpty.setVisibility(View.VISIBLE);
        } else {
            txtEmpty.setVisibility(View.INVISIBLE);
        }
    }
}
















