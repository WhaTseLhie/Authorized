package pardillo.john.jv.authorized.admin.vuser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.adapter.RepAdapter;
import pardillo.john.jv.authorized.database.pojo.Rep;
import pardillo.john.jv.authorized.database.pojo.User;

public class AdminViewAllUserRepActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView lv;
    private TextView txtEmpty;

    private RepAdapter adapter;
    private ArrayList<Rep> repList = new ArrayList<>();
    private ArrayList<User> userList = new ArrayList<>();

    private AppDatabase db;

    private int a_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_all_user_rep);

        db = new AppDatabase(this);

        try {
            Bundle b = getIntent().getExtras();
            a_id = b.getInt("a_id");
            userList = db.getUser(a_id);
            repList = db.getAllRep(userList.get(0).getA_id());

            txtEmpty = findViewById(R.id.txtEmpty);
            lv = findViewById(R.id.listView);

            adapter = new RepAdapter(this, repList);
            lv.setAdapter(adapter);

            if(repList.isEmpty()) {
                txtEmpty.setVisibility(View.VISIBLE);
            } else {
                txtEmpty.setVisibility(View.INVISIBLE);
            }

            lv.setOnItemClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent repIntent = new Intent(this, AdminViewRepActivity.class);
        repIntent.putExtra("a_id", repList.get(0).getA_id());
        repIntent.putExtra("rep_id", repList.get(position).getRep_id());
        startActivity(repIntent);
    }
}
