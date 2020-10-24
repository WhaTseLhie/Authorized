package pardillo.john.jv.authorized.main.drawer;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.adapter.ANotAdapter;
import pardillo.john.jv.authorized.database.pojo.Notification;
import pardillo.john.jv.authorized.database.pojo.User;
import pardillo.john.jv.authorized.style.MyToast;

public class ANotificationActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView lv;
    private TextView txtEmpty;

    private ArrayList<Notification> notList = new ArrayList<>();
    private ArrayList<User> userList = new ArrayList<>();

    private ANotAdapter adapter;
    private AppDatabase db;
    private MyToast myToast;

    private int pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anotification);

        myToast = new MyToast();
        db = new AppDatabase(this);
        userList = db.getLogInUser();
        notList = db.getAllNotificationAID(userList.get(0).getA_id());

        txtEmpty = findViewById(R.id.txtEmpty);
        lv = findViewById(R.id.listView);

        adapter = new ANotAdapter(this, notList);
        lv.setAdapter(adapter);

        if(notList.isEmpty()) {
            txtEmpty.setVisibility(View.VISIBLE);
        } else {
            txtEmpty.setVisibility(View.INVISIBLE);
        }

        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        pos = position;

        AlertDialog.Builder deleteBuilder = new AlertDialog.Builder(this);
        deleteBuilder.setTitle("Delete Confirmation");
        deleteBuilder.setMessage("Delete notification?");
        deleteBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.deleteNotification(notList.get(pos).getNot_id());

                notList.remove(pos);
                adapter.notifyDataSetChanged();

                myToast.makeToast(ANotificationActivity.this, "Notification Deleted", "SUCCESS");

                dialog.dismiss();
            }
        });
        deleteBuilder.setNegativeButton("No", null);

        AlertDialog deleteDialog = deleteBuilder.create();
        deleteDialog.show();
    }
}




















