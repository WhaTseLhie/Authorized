package pardillo.john.jv.authorized.admin.admintemp;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.admin.admintemp.admincreate.NewAdminCreateLetterActivity;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.pojo.NewTemplate;
import pardillo.john.jv.authorized.style.MyToast;

public class NewAdminViewTemplateActivity extends AppCompatActivity {

    private static final int CODE_CREATE_LETTER = 1;

    private TextView txtLetterContent;

    private AppDatabase db;
    private MyToast myToast;

    private ArrayList<NewTemplate> templateList = new ArrayList<>();

    private int t_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_admin_view_template);

        myToast = new MyToast();
        db = new AppDatabase(this);

        txtLetterContent = findViewById(R.id.txtLetterContent);

        try {
            Bundle b = getIntent().getExtras();
            t_id = b.getInt("t_id");
            templateList = db.getNewTemplate(t_id);

            SpannableStringBuilder letterContent = new SpannableStringBuilder(templateList.get(0).getT_letterContent());

            txtLetterContent.setText(letterContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.use_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.use:
                db.deleteAllNewDraft();
                Intent createLetter = new Intent(this, NewAdminCreateLetterActivity.class);
                createLetter.putExtra("t_id", t_id);
                startActivityForResult(createLetter, CODE_CREATE_LETTER);

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            this.finish();
        }
    }*/
}