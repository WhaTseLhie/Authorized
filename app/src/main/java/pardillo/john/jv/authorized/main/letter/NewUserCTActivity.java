package pardillo.john.jv.authorized.main.letter;

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

public class NewUserCTActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private EditText txtAdd;
    private ImageView ivAdd;
    private ListView lv;

    private ArrayList<String> ctList = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    private AppDatabase db;
    private MyToast myToast;

    private int a_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_ct);

        myToast = new MyToast();
        db = new AppDatabase(this);
        ctList = db.getAllClosingTag();

        txtAdd = findViewById(R.id.txtAdd);
        ivAdd = findViewById(R.id.ivAdd);
        lv = findViewById(R.id.listView);

        try {
            Bundle b = getIntent().getExtras();
            a_id = b.getInt("a_id");
        } catch (Exception e) {
            e.printStackTrace();
        }

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
            myToast.makeToast(this, closingTag +" has been set as letter closing", "SUCCESS");
            db.updateNewDraftCT(a_id, closingTag);
            this.finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        myToast.makeToast(this, ctList.get(position) +" has been set as closing tag", "SUCCESS");
        db.updateNewDraftCT(a_id, ctList.get(position));
        this.finish();
    }
}





















