package pardillo.john.jv.authorized.admin;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.pojo.TemplateContent;
import pardillo.john.jv.authorized.database.pojo.TemplateDetails;
import pardillo.john.jv.authorized.style.MyToast;

public class AdminFinishTemplateActivity extends AppCompatActivity {

    private ViewGroup rootLayout;
    private int _xDelta;
    private int _yDelta;
    private int t_id;

    private TextView tv[];

    private Bitmap bitmap;

    private AppDatabase db;
    private MyToast myToast;

    private ArrayList<TemplateDetails> templateList = new ArrayList<>();
    private ArrayList<TemplateContent> contentList = new ArrayList<>();

    private String s = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_finish_template);

        db = new AppDatabase(this);
        myToast = new MyToast();

        rootLayout = findViewById(R.id.view_root);

        try {
            Bundle b = getIntent().getExtras();
            t_id = b.getInt("t_id");
            templateList = db.getTemplateDetails(t_id);
            contentList = db.getTemplateContent(t_id);

            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
                }
            }

            if(templateList.isEmpty()) {
                this.finish();
            } else {
                tv = new TextView[contentList.size()];

                for(int i=0; i<contentList.size(); i++) {
                    tv[i] = new TextView(this);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    params.leftMargin = 50;
                    params.topMargin = i*50;
                    db.updateTemplateContent(contentList.get(i).getC_id(), 50, i*50, 0, 0, t_id);
                    tv[i].setPadding(15,8, 15, 8);
                    tv[i].setId(contentList.get(i).getC_id());
                    tv[i].setText(contentList.get(i).getC_name());
                    tv[i].setBackgroundResource(R.drawable.style_text_view_border);
                    tv[i].setLayoutParams(params);
                    rootLayout.addView(tv[i]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**/
        rootLayout.setDrawingCacheEnabled(true);
        rootLayout.buildDrawingCache();
        /**/

        /*RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(150, 150);
        txtAddress.setLayoutParams(layoutParams);*/

        for(int i=0; i<contentList.size(); i++) {
            tv[i].setOnTouchListener(new ChoiceTouchListener());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.save:
                myToast.makeToast(this, "Template Saved", "SUCCESS");

                bitmap = rootLayout.getDrawingCache();
                saveImage();

//                Intent intent = new Intent();
//                intent.putExtra("SUCCESS", "SUCCESS");
//                this.setResult(Activity.RESULT_OK);
//                this.finish();

                if(!TextUtils.isEmpty(s)) {
                    Intent intent = new Intent();
                    intent.putExtra("template", s);
                    this.setResult(Activity.RESULT_OK, intent);
                    this.finish();
                }

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Confirmation");
        builder.setMessage("Do you really want to cancel process?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                db.deleteTemplateContentTID(t_id);
                db.deleteTemplate(t_id);
                myToast.makeToast(AdminFinishTemplateActivity.this, "Process Cancelled", "SUCCESS");
                AdminFinishTemplateActivity.this.finish();
            }
        });
        builder.setNegativeButton("No", null);
        builder.setCancelable(false);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void saveImage() {
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
            db.updateTemplateDetails(s, t_id);


//            Intent intent = new Intent();
//            intent.putExtra("template", s);
//            this.setResult(Activity.RESULT_OK, intent);
//            this.finish();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private final class ChoiceTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            final int X = (int) event.getRawX();
            final int Y = (int) event.getRawY();
//            boolean isDown = false;

            switch(event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                    _xDelta = X - lParams.leftMargin;
                    _yDelta = Y - lParams.topMargin;
//                    isDown = true;

                    break;
                case MotionEvent.ACTION_UP:
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    break;
                case MotionEvent.ACTION_MOVE:
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                    layoutParams.leftMargin = X - _xDelta;
                    layoutParams.topMargin = Y - _yDelta;
                    layoutParams.rightMargin = -250;
                    layoutParams.bottomMargin = -250;
                    v.setLayoutParams(layoutParams);
                    Log.d("onTouch", ": MOVE");

                    break;
            }

            /*TextView txtItem = findViewById(v.getId());
            for(int i=0; i<contentList.size(); i++) {
                int cid = contentList.get(i).getC_id();

                if(cid == v.getId()) {

                }
            }*/

            /*if() {

            } else {
                int leftMargin = X - _xDelta;
                int topMargin = Y - _yDelta;
                int rightMargin = -250;
                int bottomMargin = -250;
            }*/

            /*if(isDown) {
                _xDelta = X - lParams.leftMargin;
                _yDelta = Y - lParams.topMargin;
            } else {
                int leftMargin = X - _xDelta;
                int topMargin = Y - _yDelta;
                int rightMargin = -250;
                int bottomMargin = -250;
            }*/

            /*RelativeLayout.LayoutParams laypar = (RelativeLayout.LayoutParams) v.getLayoutParams();
            int leftMargin = laypar.leftMargin;
            int topMargin = laypar.topMargin;
            int rightMargin = laypar.rightMargin;
            int bottomMargin = laypar.bottomMargin;

            Log.d("v.getId(): ", v.getId() +"");
            Log.d("leftMargin: ", leftMargin +"");
            Log.d("topMargin: ", topMargin +"");
            Log.d("rightMargin: ", rightMargin +"");
            Log.d("bottomMargin: ", bottomMargin +"");*/

//            Toast.makeText(CreateTemplateActivity.this, "v.getId(): " +v.getId(), Toast.LENGTH_SHORT).show();


            int leftMargin = X - _xDelta;
            int topMargin = Y - _yDelta;
            int rightMargin = -250;
            int bottomMargin = -250;

            db.updateTemplateContent(v.getId(), leftMargin, topMargin, rightMargin, bottomMargin, t_id);
            rootLayout.invalidate();
            return true;
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




















