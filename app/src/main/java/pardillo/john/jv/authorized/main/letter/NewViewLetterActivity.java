package pardillo.john.jv.authorized.main.letter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import pardillo.john.jv.authorized.database.pojo.User;

public class NewViewLetterActivity extends AppCompatActivity {
    
    private static final int CODE_CREATE_LETTER = 1;

    private ViewGroup rootLayout;
    private int t_id;

    private TextView tv[];

    private AppDatabase db;
    private ArrayList<TemplateDetails> templateList = new ArrayList<>();
    private ArrayList<TemplateContent> templateContentList = new ArrayList<>();
    
    private ArrayList<User> userList = new ArrayList<>(); 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_view_letter);

        db = new AppDatabase(this);
        rootLayout = findViewById(R.id.view_root);

        try {
            Bundle b = getIntent().getExtras();
            t_id = b.getInt("t_id");
            userList = db.getLogInUser();
            templateList = db.getTemplateDetails(t_id);
            templateContentList = db.getTemplateContent(t_id);

            if(templateList.isEmpty()) {
                this.finish();
                startActivity(getIntent());
            } else {
                tv = new TextView[templateContentList.size()];

                for(int i=0; i<templateContentList.size(); i++) {
                    tv[i] = new TextView(this);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    params.leftMargin = templateContentList.get(i).getC_leftMargin();
                    params.topMargin = templateContentList.get(i).getC_topMargin();
                    params.rightMargin = templateContentList.get(i).getC_rightMargin();
                    params.bottomMargin = templateContentList.get(i).getC_bottomMargin();
                    tv[i].setPadding(15,8, 15, 8);
                    tv[i].setId(templateContentList.get(i).getC_id());
                    tv[i].setText(templateContentList.get(i).getC_name());
                    tv[i].setBackgroundResource(R.drawable.style_text_view_border);
                    tv[i].setLayoutParams(params);
                    rootLayout.addView(tv[i]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.use_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.use:
                ////////////////////////////////////////////////////////////////////
                try {
                    db.deleteDraft(t_id, userList.get(0).getA_id());
                    db.deleteLetterDetails(templateList.get(0).getT_id(), userList.get(0).getA_id());
                    db.deleteLetterContent(templateList.get(0).getT_id(), userList.get(0).getA_id());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                db.addLetterDetails(
                    templateList.get(0).getT_image().toString(),
                    templateList.get(0).getT_title(),
                    templateList.get(0).getT_date(),
                    templateList.get(0).getT_type(),
                        templateList.get(0).getT_id(),
                    userList.get(0).getA_id()
                );

                for(int i=0; i<templateContentList.size(); i++) {
                    db.addLetterContent(
                            templateContentList.get(i).getC_name(),
                            templateContentList.get(i).getC_type(),
                            templateContentList.get(i).getC_leftMargin(),
                            templateContentList.get(i).getC_topMargin(),
                            templateContentList.get(i).getC_rightMargin(),
                            templateContentList.get(i).getC_bottomMargin(),
                            templateContentList.get(i).getC_id(),
                            templateList.get(0).getT_id(),
                            userList.get(0).getA_id()
                    );
                }

                db.addDraft(userList.get(0).getA_id(), t_id);
                ////////////////////////////////////////////////////////////////////
                
                Intent createLetter = new Intent(this, NewCreateLetterActivity.class);
                createLetter.putExtra("t_id", t_id);
                startActivityForResult(createLetter, CODE_CREATE_LETTER);
                
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        this.finish();
    }
}