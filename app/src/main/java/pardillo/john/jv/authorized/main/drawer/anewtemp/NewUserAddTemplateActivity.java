package pardillo.john.jv.authorized.main.drawer.anewtemp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.pojo.User;
import pardillo.john.jv.authorized.style.MyToast;

public class NewUserAddTemplateActivity extends AppCompatActivity {

    private static final int CODE_VALIDATE_TEMPLATE = 19;

    private ViewGroup rootLayout;
    private EditText txtLetterContent;

    private AppDatabase db;
    private MyToast myToast;

    private ArrayList<User> userList = new ArrayList<>();

    private String title = "";
    private String type = "";

    // TEMPLATE IMAGE
    private Bitmap bitmap;
    private String s = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_add_template);

        myToast = new MyToast();
        db = new AppDatabase(this);
        userList = db.getLogInUser();

        txtLetterContent = findViewById(R.id.txtLetterContent);
        rootLayout = findViewById(R.id.view_root);

        try {
            Bundle b = getIntent().getExtras();
            title = b.getString("title");
            type = b.getString("type");

//            String letterContent =
//                    "\n[DATE]\n\n" +
//                            "[RECIPIENT]\n\n" +
//                            "[SALUTATION]\n" +
//                            "I, [YOUR NAME], hereby grant [REPRESENTATIVE] permission to act as my representative to collect " +
//                            "my birth certificate in my behalf.\n\n" +
//                            "[CLOSING]\n" +
//                            "[SIGNATURE]\n" +
//                            "[YOUR NAME]\n";
//            txtLetterContent.setText(letterContent);

            rootLayout.setDrawingCacheEnabled(true);
            rootLayout.buildDrawingCache();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.save) {
            saveImage();
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveImage() {
        bitmap = rootLayout.getDrawingCache();
        String path = Environment.getExternalStorageDirectory().toString();
        File file = new File(path, "Template");

        try {
            OutputStream fout = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fout);

            try {
                fout.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                fout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            s = MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), "Template", "Template");

            Intent validateTemplate = new Intent(this, NewUserValidateAddTemplateActivity.class);
            validateTemplate.putExtra("letterContent", txtLetterContent.getText().toString());
            validateTemplate.putExtra("t_image", s);
            startActivityForResult(validateTemplate, CODE_VALIDATE_TEMPLATE);

//            String letterContent = txtLetterContent.getText().toString();
//            Date c = Calendar.getInstance().getTime();
//            SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy", Locale.getDefault());
//            String t_date = sdf.format(c);
//
//            db.addNewTemplate(s, title, t_date, type, letterContent, userList.get(0).getA_id(), "PUBLIC");
//            myToast.makeToast(this, "NEW TEMPLATE ADDED", "SUCCESS");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == CODE_VALIDATE_TEMPLATE) {
                try {
                    Bundle b = data.getExtras();
                    boolean isValidated = b.getBoolean("isValidated");
                    String t_image = b.getString("t_image");

                    if(isValidated) {
//                        bitmap = rootLayout.getDrawingCache();
//                        saveImage();

                        if (!TextUtils.isEmpty(t_image)) {
                            String letterContent = txtLetterContent.getText().toString();
                            Date c = Calendar.getInstance().getTime();
                            SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss", Locale.getDefault());
                            String t_date = sdf.format(c);

                            db.addNewTemplate(t_image, title, t_date, type, letterContent, userList.get(0).getA_id(), "PUBLIC");
                            myToast.makeToast(this, "NEW TEMPLATE ADDED", "SUCCESS");

                            Intent intent = new Intent();
                            intent.putExtra("template", t_image);
                            this.setResult(Activity.RESULT_OK, intent);
                            this.finish();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}












