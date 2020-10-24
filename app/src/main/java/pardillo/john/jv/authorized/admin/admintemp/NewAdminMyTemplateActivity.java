package pardillo.john.jv.authorized.admin.admintemp;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.adapter.NewTemplateAdapter;
import pardillo.john.jv.authorized.database.pojo.NewTemplate;
import pardillo.john.jv.authorized.database.pojo.User;
import pardillo.john.jv.authorized.style.MyToast;

public class NewAdminMyTemplateActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final int CODE_ADD_TEMPLATE_DETAILS = 39;
    private static final int CODE_DELETE_TEMPLATE = 19;

    private ListView lv;
    private TextView txtEmpty;

    private ArrayList<NewTemplate> templateList = new ArrayList<>();
    private ArrayList<User> userList = new ArrayList<>();

    private AppDatabase db;
    private MyToast myToast;
    private NewTemplateAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_admin_my_template);

        myToast = new MyToast();
        db = new AppDatabase(this);

        userList = db.getLogInUser();
        templateList = db.getAllNewTemplate(userList.get(0).getA_id());

        txtEmpty = findViewById(R.id.txtEmpty);
        lv = findViewById(R.id.listView);
        adapter = new NewTemplateAdapter(this, templateList);
        lv.setAdapter(adapter);

        if(templateList.isEmpty()) {
            txtEmpty.setVisibility(View.VISIBLE);
        } else {
            txtEmpty.setVisibility(View.INVISIBLE);
        }

        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent viewMyTemplate = new Intent(NewAdminMyTemplateActivity.this, NewAdminViewMyTemplateActivity.class);
        viewMyTemplate.putExtra("t_id", templateList.get(position).getT_id());
        viewMyTemplate.putExtra("position", position);
        startActivityForResult(viewMyTemplate, CODE_DELETE_TEMPLATE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_template_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.add:
                Intent addTemplate = new Intent(this, NewAdminAddTemplateDetailsActivity.class);
                this.startActivityForResult(addTemplate, CODE_ADD_TEMPLATE_DETAILS);

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == CODE_DELETE_TEMPLATE) {
                this.finish();
                this.startActivity(getIntent());
                try {
                    Bundle b = data.getExtras();
                    int t_id = b.getInt("t_id");
                    int position = b.getInt("position");

//                    db.deleteTemplateContentTID(t_id);
//                    db.deleteTemplate(t_id);
                    db.deleteNewTemplate(t_id);
                    myToast.makeToast(this, "Template Deleted", "SUCCESS");

                    templateList.remove(position);
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        this.finish();
        this.startActivity(getIntent());
    }
}