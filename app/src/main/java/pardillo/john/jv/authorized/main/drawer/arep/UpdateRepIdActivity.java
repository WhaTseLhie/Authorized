package pardillo.john.jv.authorized.main.drawer.arep;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.style.MyToast;

public class UpdateRepIdActivity extends AppCompatActivity implements View.OnClickListener {

    private final int PICK_IMAGE_MULTIPLE =1;

    private LinearLayout lnrImages;
    private Button btnAddPhotos;
    private Button btnSaveImages;

    private ArrayList<String> imagesPathList;
    private Bitmap yourbitmap;
    private Bitmap resized;

    private MyToast myToast;
    private AppDatabase db;

    private int a_id = 0;
    private int rep_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_rep_id);

        myToast = new MyToast();
        db = new AppDatabase(this);

        try {
            Bundle b = getIntent().getExtras();
            a_id = b.getInt("a_id");
            rep_id = b.getInt("rep_id");
        } catch (Exception e) {
            e.printStackTrace();
        }

        lnrImages = findViewById(R.id.lnrImages);
        btnAddPhotos = findViewById(R.id.btnAddPhotos);
        btnSaveImages = findViewById(R.id.btnSaveImages);

        btnAddPhotos.setOnClickListener(this);
        btnSaveImages.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAddPhotos:
                if(ActivityCompat.checkSelfPermission(UpdateRepIdActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(UpdateRepIdActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                    return;
                }

                Intent intent = new Intent(UpdateRepIdActivity.this, CustomPhotoGalleryActivity.class);
                startActivityForResult(intent, PICK_IMAGE_MULTIPLE);

                break;
            case R.id.btnSaveImages:
                if(imagesPathList != null) {
                    if(!imagesPathList.isEmpty()) {
                        // DELETE REPRESENTATIVES PREVIOUS IDENTIFICATIONS
                        db.deleteRepId(a_id, rep_id);
                        // ADD TO DB
                        for (int i = 0; i < imagesPathList.size(); i++) {
                            db.addRepId(a_id, rep_id, imagesPathList.get(i));
                        }

                        myToast.makeToast(UpdateRepIdActivity.this, "Identifications Updated", "SUCCESS");
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("result", "SUCCESS");
                        this.setResult(Activity.RESULT_OK, resultIntent);
                        this.finish();
                    } else {
                        myToast.makeToast(this, "Please select at least 1 identification", "ERROR");
                    }
                } else {
                    myToast.makeToast(this, "Please select at least 1 identification", "ERROR");
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if(requestCode == PICK_IMAGE_MULTIPLE) {
                imagesPathList = new ArrayList<>();
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

                btnAddPhotos.setText("CHANGE IDENTIFICATIONS");
            }
        }

    }
}