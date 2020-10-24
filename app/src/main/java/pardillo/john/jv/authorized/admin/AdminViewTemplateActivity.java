package pardillo.john.jv.authorized.admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.pojo.TemplateContent;
import pardillo.john.jv.authorized.database.pojo.TemplateDetails;

public class AdminViewTemplateActivity extends AppCompatActivity {

    private ViewGroup rootLayout;
    private int t_id;

    private TextView tv[];

    private AppDatabase db;
    private ArrayList<TemplateDetails> templateList = new ArrayList<>();
    private ArrayList<TemplateContent> contentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_template);

        db = new AppDatabase(this);
        rootLayout = findViewById(R.id.view_root);

        try {
            Bundle b = getIntent().getExtras();
            t_id = b.getInt("t_id");
            templateList = db.getTemplateDetails(t_id);
            contentList = db.getTemplateContent(t_id);

            if(templateList.isEmpty()) {
                this.finish();
                startActivity(getIntent());
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
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}