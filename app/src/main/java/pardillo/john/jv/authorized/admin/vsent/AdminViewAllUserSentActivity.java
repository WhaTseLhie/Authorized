package pardillo.john.jv.authorized.admin.vsent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.adapter.SentAdapter;
import pardillo.john.jv.authorized.database.pojo.Sent;
import pardillo.john.jv.authorized.style.MyToast;

public class AdminViewAllUserSentActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

    private ListView lv;
    private Spinner cboSort;
    private TextView txtEmpty, txtSort, txtTotal;

    private SentAdapter adapter;
    private ArrayList<Sent> sentList = new ArrayList<>();

    private AppDatabase db;

    private MyToast myToast;

    private String sort = "PENDING";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_all_user_sent);

        myToast = new MyToast();

        db = new AppDatabase(this);
        sentList = db.getAllSentAdmin(sort);

        txtTotal = findViewById(R.id.txtTotal);
        txtEmpty = findViewById(R.id.txtEmpty);
        txtSort = findViewById(R.id.txtSort);
        cboSort = findViewById(R.id.cboSort);
        lv = findViewById(R.id.listView);

        adapter = new SentAdapter(this, sentList);
        lv.setAdapter(adapter);

        txtTotal.setText("" +sentList.size());

        if(sentList.isEmpty()) {
            txtEmpty.setVisibility(View.VISIBLE);
            txtEmpty.setText("No " +sort+ " letters.");
        } else {
            txtEmpty.setVisibility(View.INVISIBLE);
        }

        cboSort.setOnItemSelectedListener(this);
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent viewSent = new Intent(this, AdminViewUserSentActivity.class);
        viewSent.putExtra("s_id", sentList.get(position).getS_id());
        startActivity(viewSent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        sort = cboSort.getItemAtPosition(position).toString();
        sentList.clear();
        adapter.notifyDataSetChanged();

//        myToast.makeToast(this, sort, "NORMAL");
        sentList = db.getAllSentAdmin(sort);
        adapter = new SentAdapter(this, sentList);
        lv.setAdapter(adapter);

        txtTotal.setText("" +sentList.size());

        if(sentList.isEmpty()) {
            txtEmpty.setVisibility(View.VISIBLE);
            txtEmpty.setText("No " +sort+ " letters.");
        } else {
            txtEmpty.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
