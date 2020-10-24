package pardillo.john.jv.authorized.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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
import pardillo.john.jv.authorized.database.adapter.TemplateAdapter;
import pardillo.john.jv.authorized.database.pojo.TemplateDetails;
import pardillo.john.jv.authorized.database.pojo.User;

public class AdminActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final int CODE_VIEW_MY_TEMPLATE = 69;

    private ListView lv;
    private TextView txtEmpty;

    private ArrayList<TemplateDetails> templateList = new ArrayList<>();
    private ArrayList<User> userList = new ArrayList<>();

    private AppDatabase db;
    private TemplateAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        db = new AppDatabase(this);
        userList = db.getLogInUser();
        templateList = db.getAllTemplateDetails();

        txtEmpty = findViewById(R.id.txtEmpty);
        lv = findViewById(R.id.listView);
        adapter = new TemplateAdapter(this, templateList);
        lv.setAdapter(adapter);
//        templateList.add(new TemplateDetails(1, "ADMIN", "13/1/2020", "", "", "", "", ""));

        if(templateList.isEmpty()) {
            txtEmpty.setVisibility(View.VISIBLE);
        } else {
            txtEmpty.setVisibility(View.INVISIBLE);
        }

        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent viewTemplate = new Intent(this, AdminViewTemplateActivity.class);
        viewTemplate.putExtra("t_id", templateList.get(position).getT_id());
        startActivity(viewTemplate);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_template_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.view:
                Intent viewAllMyTemplate = new Intent(this, AdminMyTemplateActivity.class);
                startActivityForResult(viewAllMyTemplate, CODE_VIEW_MY_TEMPLATE);

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                AdminActivity.this.finish();
            }
        });
        builder.setNegativeButton("No", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        this.finish();
        this.startActivity(getIntent());
    }
}