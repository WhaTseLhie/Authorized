package pardillo.john.jv.authorized.admin;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.pojo.TemplateContent;
import pardillo.john.jv.authorized.database.pojo.TemplateDetails;
import pardillo.john.jv.authorized.style.MyToast;

public class AdminViewMyTemplateActivity extends AppCompatActivity {

    private static final int CODE_UPDATE_TEMPLATE = 16;

    private ViewGroup rootLayout;
    private int t_id;
    private int position;

    private TextView tv[];

    private AppDatabase db;
    private MyToast myToast;

    private ArrayList<TemplateDetails> templateList = new ArrayList<>();
    private ArrayList<TemplateContent> contentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_my_template);

        myToast = new MyToast();

        db = new AppDatabase(this);
        rootLayout = findViewById(R.id.view_root);

        try {
            Bundle b = getIntent().getExtras();
            t_id = b.getInt("t_id");
            position = b.getInt("position");
            templateList = db.getTemplateDetails(t_id);
            contentList = db.getTemplateContent(t_id);

            ////////////////////////////////////////////
            Log.d("image: ", templateList.get(0).getT_image() +"");
            ////////////////////////////////////////////

            if(templateList.isEmpty()) {
                this.finish();
                this.startActivity(getIntent());
            } else {
                tv = new TextView[contentList.size()];

                for(int i=0; i<contentList.size(); i++) {
                    tv[i] = new TextView(this);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    params.leftMargin = contentList.get(i).getC_leftMargin();
                    params.topMargin = contentList.get(i).getC_topMargin();
//                    params.rightMargin = contentList.get(i).getC_rightMargin();
//                    params.bottomMargin = contentList.get(i).getC_bottomMargin();
                    tv[i].setPadding(15,8, 15, 8);
                    tv[i].setId(contentList.get(i).getC_id());
                    tv[i].setText(contentList.get(i).getC_name());
                    tv[i].setBackgroundResource(R.drawable.style_text_view_border);
                    tv[i].setLayoutParams(params);
                    rootLayout.addView(tv[i]);

                    ////////////////////////////////////////
                    Log.d("leftMargin: ", contentList.get(i).getC_leftMargin() +"");
                    Log.d("topMargin: ", contentList.get(i).getC_topMargin() +"");
                    Log.d("rightMargin: ", contentList.get(i).getC_rightMargin() +"");
                    Log.d("bottomMargin: ", contentList.get(i).getC_bottomMargin() +"");
                    ////////////////////////////////////////
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_my_template_menu, menu);

        if(templateList.get(0).getT_status().equals("PUBLIC")) {
            menu.findItem(R.id.shared).setIcon(getDrawable(R.drawable.ic_menu_eye));
        } else {
            menu.findItem(R.id.shared).setIcon(getDrawable(R.drawable.ic_menu_eye_black));
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.shared:
                if(templateList.get(0).getT_status().equals("PUBLIC")) {
                    db.updateTemplateDetailsStatus("PRIVATE", t_id);
                    myToast.makeToast(this, "Template status changed to PRIVATE", "SUCCESS");
                } else {
                    db.updateTemplateDetailsStatus("PUBLIC", t_id);
                    myToast.makeToast(this, "Template status changed to PUBLIC", "SUCCESS");
                }

                this.finish();
                this.startActivity(getIntent());

                break;
            case R.id.update:
                Intent updateTemplate = new Intent(this, AdminUpdateTemplateActivity.class);
                updateTemplate.putExtra("t_id", t_id);
                startActivityForResult(updateTemplate, CODE_UPDATE_TEMPLATE);

                break;
            case R.id.delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Delete Template Confirmation");
                builder.setMessage("Do you really want to delete this template?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent();
                        intent.putExtra("t_id", t_id);
                        intent.putExtra("position", position);
                        AdminViewMyTemplateActivity.this.setResult(Activity.RESULT_OK, intent);
                        AdminViewMyTemplateActivity.this.finish();
                    }
                });
                builder.setNegativeButton("No", null);
                builder.setCancelable(false);

                AlertDialog dialog = builder.create();
                dialog.show();

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == CODE_UPDATE_TEMPLATE) {
            this.finish();
            this.startActivity(getIntent());
        }
    }
}