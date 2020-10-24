package pardillo.john.jv.authorized.main.drawer.areceive;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.pojo.Rep;
import pardillo.john.jv.authorized.database.pojo.RepId;
import pardillo.john.jv.authorized.database.pojo.User;
import pardillo.john.jv.authorized.style.CircleTransform;
import pardillo.john.jv.authorized.style.MyToast;

public class ViewReceiveRepActivity extends AppCompatActivity {

    private ImageView iv;
    private LinearLayout lnrImages;
    private TextView txtFullName, txtContact, txtGender;

    private AppDatabase db;

    private ArrayList<Rep> repList = new ArrayList<>();
    private ArrayList<User> userList = new ArrayList<>();
    private ArrayList<RepId> repidList = new ArrayList<>();

    private int id = 0;
    private int r_id = 0;

    private MyToast myToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_receive_rep);

        db = new AppDatabase(this);
        myToast = new MyToast();
        userList = db.getLogInUser();

        iv = findViewById(R.id.imageView);
        txtFullName = findViewById(R.id.txtFullName);
        txtContact = findViewById(R.id.txtContact);
        txtGender = findViewById(R.id.txtGender);
        lnrImages = findViewById(R.id.lnrImages);

        Bundle b = getIntent().getExtras();
        try {
            id = b.getInt("rep_id");
            r_id = b.getInt("r_id");
            repList = db.getRep(id);
            repidList = db.getRepId(repList.get(0).getA_id(), id);

            this.setTitle(repList.get(0).getRep_fname() + "'s Info");

//            iv.setImageURI(repList.get(0).getRep_image());
            Picasso.with(getApplicationContext()).load(repList.get(0).getRep_image()).transform(new CircleTransform()).into(iv);
            txtFullName.setText(repList.get(0).getRep_fname() + " " + repList.get(0).getRep_lname());
            txtContact.setText(repList.get(0).getRep_contact());
            txtGender.setText(repList.get(0).getRep_gender());

            try {
                lnrImages.removeAllViews();
            } catch (Throwable e) {
                e.printStackTrace();
            }

            for(int i=0; i<repidList.size(); i++) {
                Bitmap yourbitmap = BitmapFactory.decodeFile(repidList.get(i).getRepid_image());

                ImageView imageView = new ImageView(this);
                imageView.setImageBitmap(yourbitmap);
                imageView.setAdjustViewBounds(true);
                lnrImages.addView(imageView);
            }
        } catch (Exception e) {
            Log.d("Empyt: ", " ID IS NULL");
            this.finish();
        }
    }
}
