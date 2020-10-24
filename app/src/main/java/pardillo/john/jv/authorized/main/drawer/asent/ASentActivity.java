package pardillo.john.jv.authorized.main.drawer.asent;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.adapter.SentAdapter;
import pardillo.john.jv.authorized.database.pojo.LetterSentContent;
import pardillo.john.jv.authorized.database.pojo.LetterSentDetails;
import pardillo.john.jv.authorized.database.pojo.Sent;
import pardillo.john.jv.authorized.database.pojo.User;
import pardillo.john.jv.authorized.style.MyToast;

public class ASentActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

    private static final int CODE_VIEW_SENT_LETTER = 10;

    private ListView lv;
    private Spinner cboSort;
    private TextView txtEmpty, txtSort;

    private SentAdapter adapter;
    private ArrayList<Sent> sentList = new ArrayList<>();
    private ArrayList<User> userList = new ArrayList<>();

    private AppDatabase db;

    private MyToast myToast;

    private String sort = "PENDING";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asent);

        myToast = new MyToast();

        db = new AppDatabase(this);
        userList = db.getLogInUser();
        sentList = db.getAllSentSortStatus(userList.get(0).getA_id(), sort);
//        sentList = db.getAllSent(userList.get(0).getA_id());

//        if(TextUtils.equals(sort, "PENDING")) {

        txtEmpty = findViewById(R.id.txtEmpty);
        txtSort = findViewById(R.id.txtSort);
        cboSort = findViewById(R.id.cboSort);
        lv = findViewById(R.id.listView);

        adapter = new SentAdapter(this, sentList);
        lv.setAdapter(adapter);

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
        Intent viewSent = new Intent(ASentActivity.this, NewViewUserSentActivity.class);
        viewSent.putExtra("s_id", sentList.get(position).getS_id());
        startActivityForResult(viewSent, CODE_VIEW_SENT_LETTER);


//        Intent viewSent = new Intent(ASentActivity.this, NewViewSentActivity.class);
//        viewSent.putExtra("s_id", sentList.get(position).getS_id());
//        startActivityForResult(viewSent, CODE_VIEW_SENT_LETTER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        this.finish();
        this.startActivity(getIntent());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        sort = cboSort.getItemAtPosition(position).toString();
        sentList.clear();
        adapter.notifyDataSetChanged();

        sentList = db.getAllSentSortStatus(userList.get(0).getA_id(), sort);
        adapter = new SentAdapter(this, sentList);
        lv.setAdapter(adapter);

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



















