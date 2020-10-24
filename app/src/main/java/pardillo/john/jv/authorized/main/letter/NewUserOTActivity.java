package pardillo.john.jv.authorized.main.letter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
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

public class NewUserOTActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private EditText txtAdd;
    private ImageView ivAdd;
    private ListView lv;

    private ArrayList<String> otList = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    private AppDatabase db;
    private MyToast myToast;

    private int a_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_ot);

        myToast = new MyToast();
        db = new AppDatabase(this);
        otList = db.getAllOpeningTag();

        txtAdd = findViewById(R.id.txtAdd);
        ivAdd = findViewById(R.id.ivAdd);
        lv = findViewById(R.id.listView);

        try {
            Bundle b = getIntent().getExtras();
            a_id = b.getInt("a_id");
        } catch (Exception e) {
            e.printStackTrace();
        }

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
            myToast.makeToast(NewUserOTActivity.this, openingTag +" has been set as salutation", "SUCCESS");
            db.updateNewDraftOT(a_id, openingTag);
            this.finish();
//            Intent intent = new Intent();
//            intent.putExtra("openingTag", openingTag);
//            this.setResult(Activity.RESULT_OK, intent);
//            this.finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        myToast.makeToast(NewUserOTActivity.this, otList.get(position) +" has been set as opening tag", "SUCCESS");
        db.updateNewDraftOT(a_id, otList.get(position));
        this.finish();
    }
}





















