package pardillo.john.jv.authorized.admin.vsent;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ImageSpan;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.pojo.NewTemplate;
import pardillo.john.jv.authorized.database.pojo.Sent;
import pardillo.john.jv.authorized.database.pojo.User;
import pardillo.john.jv.authorized.style.MyToast;

public class AdminViewUserSentActivity extends AppCompatActivity {

    private TextView txtLetterContent;

    private AppDatabase db;
    private MyToast myToast;

    private ArrayList<User> userList = new ArrayList<>();
    private ArrayList<Sent> sentList = new ArrayList<>();
//    private ArrayList<NewTemplate> templateList = new ArrayList<>();
    private ArrayList<User> aList = new ArrayList<>();

    private int s_id = 0;

    private Uri signatureUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_user_sent);

        myToast = new MyToast();
        db = new AppDatabase(this);

        txtLetterContent = findViewById(R.id.txtLetterContent);

        try {
            Bundle b = getIntent().getExtras();
            s_id = b.getInt("s_id");
            sentList = db.getSent(s_id);
            userList = db.getUser(sentList.get(0).getA_id());
//            templateList = db.getNewTemplate(sentList.get(0).getT_id());
//            recList = db.getUser(sentList.get(0).getRec_id());

            String strLetterContent = sentList.get(0).getS_letter();
            SpannableStringBuilder ssbLetterContent = new SpannableStringBuilder(strLetterContent);

            // SET SIGNATURE
            Drawable signatureDrawable = null;
            InputStream inputStream = null;
            ImageSpan imageSpan = null;
            try {
                try {
                    signatureUri = Uri.parse(userList.get(0).getA_signature());
                    inputStream = getContentResolver().openInputStream(signatureUri);
                    signatureDrawable = Drawable.createFromStream(inputStream, signatureUri.toString());
                    signatureDrawable.setBounds(0, 0, 150, 150);

                    int sign_start = strLetterContent.indexOf("[SIGN]");
                    imageSpan = new ImageSpan(signatureDrawable);
                    ssbLetterContent.setSpan(
                            imageSpan,
                            sign_start,
                            sign_start + "[SIGN]".length() + 1,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                } catch (FileNotFoundException e) {
                    myToast.makeToast(this, "Something went wrong when displaying digital signature", "ERROR");
                    e.printStackTrace();
                } finally {
                    try {
                        if(inputStream != null) {
                            inputStream.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            txtLetterContent.setText(ssbLetterContent);
            txtLetterContent.setMovementMethod(LinkMovementMethod.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
