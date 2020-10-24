package pardillo.john.jv.authorized.admin.maincontent;

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
import pardillo.john.jv.authorized.database.adapter.MainContentAdapter;
import pardillo.john.jv.authorized.database.pojo.MainContent;
import pardillo.john.jv.authorized.style.MyToast;

public class MainContentActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final int CODE_VIEW_MAIN_CONTENT = 8;
    private static final int CODE_ADD_MAIN_CONTENT = 18;

    private ListView lv;
    private TextView txtEmpty;

    private MainContentAdapter adapter;
    private ArrayList<MainContent> mcList = new ArrayList<>();

    private AppDatabase db;
    private MyToast myToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_content);

        myToast = new MyToast();

        db = new AppDatabase(this);
        mcList = db.getAllMainContent();

        txtEmpty = findViewById(R.id.txtEmpty);
        lv = findViewById(R.id.listView);

        adapter = new MainContentAdapter(this, mcList);
        lv.setAdapter(adapter);

        if(mcList.isEmpty()) {
            txtEmpty.setVisibility(View.VISIBLE);
        } else {
            txtEmpty.setVisibility(View.INVISIBLE);
        }

        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent viewSent = new Intent(this, ViewMainContentActivity.class);
        viewSent.putExtra("mc_id", mcList.get(position).getMc_id());
        startActivityForResult(viewSent, CODE_VIEW_MAIN_CONTENT);
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
                Intent addMainContent = new Intent(this, AddMainContentActivity.class);
                startActivityForResult(addMainContent, CODE_ADD_MAIN_CONTENT);

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == CODE_ADD_MAIN_CONTENT) {
                Bundle b = null;

                try {
                    b = data.getExtras();
                    String mc_name = b.getString("mc_name");
                    String mc_type = b.getString("mc_type");

                    int mcid = db.addMainContent(mc_name, mc_type);

                    myToast.makeToast(this, "mcid: " +mcid, "SUCCESS");

                    mcList.add(new MainContent(mcid, mc_name, mc_type));
                    adapter.notifyDataSetChanged();
                    myToast.makeToast(this, "New Content Added", "SUCCESS");
                    txtEmpty.setVisibility(View.INVISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            this.finish();
            this.startActivity(getIntent());
        }
    }
}












