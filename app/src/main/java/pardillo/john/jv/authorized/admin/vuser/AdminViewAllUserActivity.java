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
import pardillo.john.jv.authorized.database.adapter.UserAdapter;
import pardillo.john.jv.authorized.database.pojo.User;
import pardillo.john.jv.authorized.style.MyToast;

public class AdminViewAllUserActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final int CODE_VIEW_USER = 10;

    private ListView lv;
    private TextView txtEmpty;

    private UserAdapter adapter;
    private ArrayList<User> aList = new ArrayList<>();

    private AppDatabase db;

    private MyToast myToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_all_user);

        myToast = new MyToast();

        db = new AppDatabase(this);
        aList = db.adminGetAllUser();

        txtEmpty = findViewById(R.id.txtEmpty);
        lv = findViewById(R.id.listView);

        adapter = new UserAdapter(this, aList);
        lv.setAdapter(adapter);

        if(aList.isEmpty()) {
            txtEmpty.setVisibility(View.VISIBLE);
        } else {
            txtEmpty.setVisibility(View.INVISIBLE);
        }

        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent viewUser = new Intent(this, AdminViewUserActivity.class);
//        Intent viewUser = new Intent(this, AdminViewAllUserRepActivity.class);
        viewUser.putExtra("a_id", aList.get(position).getA_id());
        startActivity(viewUser);
    }
}









