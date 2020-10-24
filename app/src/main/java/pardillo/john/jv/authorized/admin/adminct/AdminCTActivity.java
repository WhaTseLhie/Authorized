package pardillo.john.jv.authorized.admin.adminct;

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

public class AdminCTActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private EditText txtAdd;
    private ImageView ivAdd;
    private ListView lv;

    private ArrayList<String> ctList = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    private AppDatabase db;
    private MyToast myToast;

    private int pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_ct);

        myToast = new MyToast();
        db = new AppDatabase(this);
        ctList = db.getAllClosingTag();

        txtAdd = findViewById(R.id.txtAdd);
        ivAdd = findViewById(R.id.ivAdd);
        lv = findViewById(R.id.listView);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ctList);
        lv.setAdapter(adapter);

        ivAdd.setOnClickListener(this);
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String closingTag = txtAdd.getText().toString();

        if(TextUtils.isEmpty(closingTag)) {
            myToast.makeToast(this, "Field is empty", "ERROR");
        } else {
            boolean isFound = db.findClosingTag(closingTag.toLowerCase());

            if(isFound) {
                myToast.makeToast(this, closingTag +" already exist", "ERROR");
            } else {
                txtAdd.setText("");
                ctList.add(closingTag);
                adapter.notifyDataSetChanged();
                db.addClosingTag(closingTag);
                myToast.makeToast(this, closingTag + " successfully added", "SUCCESS");
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        pos = position;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Confirmation");
        builder.setMessage("Do you really want to delete " +ctList.get(position));
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                myToast.makeToast(AdminCTActivity.this, ctList.get(pos) +" has been deleted", "NORMAL");

                db.deleteClosingTag(ctList.get(pos));
                ctList.remove(pos);
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("No", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}





















