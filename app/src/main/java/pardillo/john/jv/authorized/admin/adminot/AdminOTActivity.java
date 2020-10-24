package pardillo.john.jv.authorized.admin.adminot;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.style.MyToast;

public class AdminOTActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private EditText txtAdd;
    private ImageView ivAdd;
    private ListView lv;

    private ArrayList<String> otList = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    private AppDatabase db;
    private MyToast myToast;

    private int pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_ot);

        myToast = new MyToast();
        db = new AppDatabase(this);
        otList = db.getAllOpeningTag();

        txtAdd = findViewById(R.id.txtAdd);
        ivAdd = findViewById(R.id.ivAdd);
        lv = findViewById(R.id.listView);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, otList);
        lv.setAdapter(adapter);

        ivAdd.setOnClickListener(this);
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String openingTag = txtAdd.getText().toString();

        if(TextUtils.isEmpty(openingTag)) {
            myToast.makeToast(this, "Field is empty", "ERROR");
        } else {
            boolean isFound = db.findOpeningTag(openingTag.toLowerCase());

            if(isFound) {
                myToast.makeToast(this, openingTag +" already exist", "ERROR");
            } else {
                txtAdd.setText("");
                otList.add(openingTag);
                adapter.notifyDataSetChanged();
                db.addOpeningTag(openingTag);
                myToast.makeToast(this, openingTag + " successfully added", "SUCCESS");
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        pos = position;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Confirmation");
        builder.setMessage("Do you really want to delete " +otList.get(position));
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                myToast.makeToast(AdminOTActivity.this, otList.get(pos) +" has been deleted", "SUCCESS");

                db.deleteOpeningTag(otList.get(pos));
                otList.remove(pos);
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("No", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}





















