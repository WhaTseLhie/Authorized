package pardillo.john.jv.authorized.main.letter.representative;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.style.MyToast;

public class LetterAddRepActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int CODE_PICK_IMAGE = 10;

    private ImageView iv;
    private TextView txtGivenName, txtLastName, txtContact;
    private RadioGroup rgGender;
    private Button btnAdd, btnCancel;

    // INTERNET SOLUTION
    private LinearLayout lnrImages;
    private Button btnAddID;
    private ArrayList<String> imagesPathList = new ArrayList<>();
    private Bitmap yourbitmap;
    private Bitmap resized;
    private final int CODE_PICK_IMAGE_MULTIPLE = 1;

    private Uri imageUri = null;
//    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letter_add_rep);

//        db = new AppDatabase(this);

        iv = findViewById(R.id.imageView);
        btnAddID = findViewById(R.id.btnAddID);
        txtGivenName = findViewById(R.id.txtGivenName);
        txtLastName = findViewById(R.id.txtLastName);
        txtContact = findViewById(R.id.txtContact);
        rgGender = findViewById(R.id.rgGender);
        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);
        lnrImages = findViewById(R.id.lnrImages);

        iv.setOnClickListener(this);
        btnAddID.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.imageView:
                Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickImage, CODE_PICK_IMAGE);

                break;
            case R.id.btnAddID:
                if(ActivityCompat.checkSelfPermission(LetterAddRepActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(LetterAddRepActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                    return;
                }
                Intent intent = new Intent(LetterAddRepActivity.this, LetterCustomPhotoGalleryActivity.class);
                startActivityForResult(intent, CODE_PICK_IMAGE_MULTIPLE);

                break;
            case R.id.btnAdd:
                isEmptyFields();

                break;
            case R.id.btnCancel:
                this.finish();

                break;
        }
    }

    private void isEmptyFields() {
        MyToast myToast = new MyToast();
        String fname = txtGivenName.getText().toString();
        String lname = txtLastName.getText().toString();
        String contact = txtContact.getText().toString();

        int rbCheckedId = rgGender.getCheckedRadioButtonId();
        RadioButton rbChecked = findViewById(rbCheckedId);
        String gender = rbChecked.getText().toString();


        if(imageUri != null) {
            if(TextUtils.isEmpty(fname) || TextUtils.isEmpty(lname) || TextUtils.isEmpty(contact) || TextUtils.isEmpty(gender)) {
                myToast.makeToast(this, "Please fill all fields", "ERROR");
            } else {
                if(imagesPathList.isEmpty()) {
                    myToast.makeToast(this, "Please select at least 1 identification", "ERROR");
                } else {
                    Intent intent = new Intent();
                    intent.putStringArrayListExtra("imagesPathList", imagesPathList);
                    intent.putExtra("image", imageUri.toString());
                    intent.putExtra("fname", fname);
                    intent.putExtra("lname", lname);
                    intent.putExtra("contact", contact);
                    intent.putExtra("gender", gender);
                    intent.putExtra("ids", "");
                    this.setResult(Activity.RESULT_OK, intent);
                    this.finish();
                }
            }
        } else {
            myToast.makeToast(this, "Please click the photo to select an image", "ERROR");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            switch(requestCode) {
                case CODE_PICK_IMAGE:
                    try {
                        imageUri = data.getData();
                        iv.setImageURI(imageUri);
                    } catch (Exception e) {
                        Log.d("AddRep", ": Image Error");
                    }

                    break;
                case CODE_PICK_IMAGE_MULTIPLE:
                    String[] imagesPath = data.getStringExtra("data").split("\\|");
                    try {
                        lnrImages.removeAllViews();
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }

                    for(int i=0; i<imagesPath.length; i++) {
                        imagesPathList.add(imagesPath[i]);
                        yourbitmap = BitmapFactory.decodeFile(imagesPath[i]);

                        ImageView imageView = new ImageView(this);
                        imageView.setImageBitmap(yourbitmap);
                        imageView.setAdjustViewBounds(true);
                        lnrImages.addView(imageView);
                    }

                    btnAddID.setText("CHANGE IDENTIFICATIONS");

                    break;
            }
        }
    }
}