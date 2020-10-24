package pardillo.john.jv.authorized.main.drawer.areceive;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.adapter.ReceiveAdapter;
import pardillo.john.jv.authorized.database.pojo.Receive;
import pardillo.john.jv.authorized.database.pojo.User;
import pardillo.john.jv.authorized.style.MyToast;

public class AReceiveActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

    private static final int CODE_VIEW_RECEIVED_LETTER = 10;

    private ListView lv;
    private Spinner cboSort;
    private TextView txtEmpty, txtSort;

    private ReceiveAdapter adapter;
    private ArrayList<Receive> receiveList = new ArrayList<>();
    private ArrayList<User> userList = new ArrayList<>();

    private AppDatabase db;

    private MyToast myToast;

    private String sort = "PENDING";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_areceive);

        myToast = new MyToast();

        db = new AppDatabase(this);
        userList = db.getLogInUser();
        receiveList = db.getAllReceiveSortStatus(userList.get(0).getA_id(), sort);
//        receiveList = db.getAllReceive(userList.get(0).getA_id());

        txtEmpty = findViewById(R.id.txtEmpty);
        txtSort = findViewById(R.id.txtSort);
        cboSort = findViewById(R.id.cboSort);
        lv = findViewById(R.id.listView);

        adapter = new ReceiveAdapter(this, receiveList);
        lv.setAdapter(adapter);

        if(receiveList.isEmpty()) {
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
        Intent viewReceive = new Intent(AReceiveActivity.this, NewViewUserReceiveActivity.class);
        viewReceive.putExtra("r_id", receiveList.get(position).getR_id());
        startActivityForResult(viewReceive, CODE_VIEW_RECEIVED_LETTER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        this.finish();
        this.startActivity(getIntent());

        if(resultCode == Activity.RESULT_OK) {
            switch(requestCode) {
                case CODE_VIEW_RECEIVED_LETTER:
                    try {
                        this.finish();
                        this.startActivity(getIntent());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        sort = cboSort.getItemAtPosition(position).toString();
        receiveList.clear();
        adapter.notifyDataSetChanged();

//        myToast.makeToast(this, sort, "NORMAL");
        receiveList = db.getAllReceiveSortStatus(userList.get(0).getA_id(), sort);
        adapter = new ReceiveAdapter(this, receiveList);
        lv.setAdapter(adapter);

        if(receiveList.isEmpty()) {
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



















