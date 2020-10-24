package pardillo.john.jv.authorized.main.drawer.aprofile;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.register.anew.PaintView;

public class UpdateSignActivity extends AppCompatActivity {

    private PaintView paintView;
    private Bitmap bitmap;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_sign);

        linearLayout = findViewById(R.id.linearLayout);
        paintView = new PaintView(this);
        linearLayout.addView(paintView);

        linearLayout.setDrawingCacheEnabled(true);
        linearLayout.buildDrawingCache();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.save) {

            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
                }
            } else {
                bitmap = linearLayout.getDrawingCache();
                saveImage();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveImage() {
        String path = Environment.getExternalStorageDirectory().toString();
        File file = new File(path, "Signature");

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
            String s = MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), "Signature", "Signature");
            Intent intent = new Intent();
            intent.putExtra("signature", s);
            this.setResult(Activity.RESULT_OK, intent);
            this.finish();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case 100:
                if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    final Dialog dialog2 = new Dialog(this);
                    dialog2.setContentView(R.layout.layout_permission);

                    TextView t1 = (TextView) dialog2.findViewById(R.id.txtTitle);
                    TextView t2 = (TextView) dialog2.findViewById(R.id.txtMessage);
                    t1.setText("Request Permissions");
                    t2.setText("Please allow permissions if you want this application to perform the task.");

                    Button dialogButton = (Button) dialog2.findViewById(R.id.btnOk);
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog2.dismiss();
                        }
                    });

                    dialog2.show();
                }

                break;
        }
    }
}
