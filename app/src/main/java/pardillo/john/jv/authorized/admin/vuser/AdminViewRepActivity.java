package pardillo.john.jv.authorized.admin.vuser;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
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

public class AdminViewRepActivity extends AppCompatActivity {

    private ImageView iv;
    private TextView txtFullName, txtContact, txtGender, txtUpdateInfo, txtUpdateID;
    private LinearLayout lnrImages;

    private AppDatabase db;
    private ArrayList<Rep> repList = new ArrayList<>();
    private ArrayList<RepId> repidList = new ArrayList<>();
    private ArrayList<User> userList = new ArrayList<>();

    private int aid = 0, rep_id = 0;

    // DIALOGS
    private Dialog infoDialog;

    // UPDATE USER INFORMATION
    private EditText txtEditFname, txtEditLname, txtEditContact;
    private RadioGroup rgGender;

    // DIALOG BUTTONS
    private Button btnSave, btnCancel;

    private MyToast myToast = new MyToast();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_rep);

        db = new AppDatabase(this);

        iv = findViewById(R.id.imageView);
        txtFullName = findViewById(R.id.txtFullName);
        txtContact = findViewById(R.id.txtContact);
        txtGender = findViewById(R.id.txtGender);
        txtUpdateInfo = findViewById(R.id.txtUpdateInfo);
        txtUpdateID = findViewById(R.id.txtUpdateID);
        lnrImages = findViewById(R.id.lnrImages);

        try {
            Bundle b = getIntent().getExtras();
            rep_id = b.getInt("rep_id");
            aid = b.getInt("a_id");

            repList = db.getRep(rep_id);
            repidList = db.getRepId(aid, rep_id);

            this.setTitle(repList.get(0).getRep_fname() +"'s Info");

//            iv.setImageURI(repList.get(0).getRep_image());
            Picasso.with(getApplicationContext()).load(repList.get(0).getRep_image()).transform(new CircleTransform()).into(iv);
            txtFullName.setText(repList.get(0).getRep_fname() +" "+ repList.get(0).getRep_lname());
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
        } catch(Exception e) {
            Log.d("TID", " ID IS NULL");
            this.finish();
        }
    }
}
