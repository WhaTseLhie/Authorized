package pardillo.john.jv.authorized.admin.admintemp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.pojo.NewTemplate;
import pardillo.john.jv.authorized.style.MyToast;

public class NewAdminViewMyTemplateActivity extends AppCompatActivity {

    private static final int CODE_UPDATE_TEMPLATE = 16;

    private TextView txtLetterContent;

    private AppDatabase db;
    private MyToast myToast;

    private ArrayList<NewTemplate> templateList = new ArrayList<>();

    private int t_id;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_admin_view_my_template);

        myToast = new MyToast();
        db = new AppDatabase(this);

        txtLetterContent = findViewById(R.id.txtLetterContent);

        try {
            Bundle b = getIntent().getExtras();
            t_id = b.getInt("t_id");
            position = b.getInt("position");
            templateList = db.getNewTemplate(t_id);

            SpannableStringBuilder letterContent = new SpannableStringBuilder(templateList.get(0).getT_letterContent());

            txtLetterContent.setText(letterContent);
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
            menu.findItem(R.id.shared).setIcon(getDrawable(R.drawable.ic_visibility_off));
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.shared:
                if(templateList.get(0).getT_status().equals("PUBLIC")) {
                    db.updateNewTemplateStatus("PRIVATE", t_id);
                    myToast.makeToast(this, "Template status changed to PRIVATE", "SUCCESS");
                } else {
                    db.updateNewTemplateStatus("PUBLIC", t_id);
                    myToast.makeToast(this, "Template status changed to PUBLIC", "SUCCESS");
                }

                this.finish();
                this.startActivity(getIntent());

                break;
            case R.id.update:
                Intent updateTemplate = new Intent(this, NewAdminUpdateTemplateActivity.class);
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
                        NewAdminViewMyTemplateActivity.this.setResult(Activity.RESULT_OK, intent);
                        NewAdminViewMyTemplateActivity.this.finish();
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