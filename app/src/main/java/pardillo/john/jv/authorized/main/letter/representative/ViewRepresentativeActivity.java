package pardillo.john.jv.authorized.main.letter.representative;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

public class ViewRepresentativeActivity extends AppCompatActivity {

    private ImageView iv;
    private LinearLayout lnrImages;
    private TextView txtFullName, txtContact, txtGender;

    private ArrayList<Rep> repList = new ArrayList<>();
    private ArrayList<User> userList = new ArrayList<>();
    private ArrayList<RepId> repidList = new ArrayList<>();

    private AppDatabase db;
    private MyToast myToast;

    private int rep_id = 0;
//    private int t_id = 0;
//    private int lc_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_representative);

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
            rep_id = b.getInt("rep_id");
//            t_id = b.getInt("t_id");
//            lc_id = b.getInt("lc_id");
            repList = db.getRep(rep_id);
            repidList = db.getRepId(repList.get(0).getA_id(), rep_id);

//            iv.setImageURI(repList.get(0).getRep_image());
            Picasso.with(getApplicationContext()).load(repList.get(0).getRep_image()).transform(new CircleTransform()).into(iv);
            txtFullName.setText(repList.get(0).getRep_fname() +" "+ repList.get(0).getRep_lname());
            txtContact.setText(repList.get(0).getRep_contact());
            txtGender.setText(repList.get(0).getRep_gender());

            this.setTitle(repList.get(0).getRep_fname() +"'s Info");

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.choose_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.choose:
                myToast.makeToast(this, repList.get(0).getRep_fname() +" is chosen", "SUCCESS");
                db.updateNewDraftRep(userList.get(0).getA_id(), rep_id);
//                db.updateLetterContentTextLCID(lc_id, repList.get(0).getRep_id()+"", t_id, userList.get(0).getA_id());
                Intent intent = new Intent();
                this.setResult(Activity.RESULT_OK, intent);
                this.finish();

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
















