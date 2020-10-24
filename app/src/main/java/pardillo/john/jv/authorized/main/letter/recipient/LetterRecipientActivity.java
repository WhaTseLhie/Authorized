package pardillo.john.jv.authorized.main.letter.recipient;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.adapter.UserAdapter;
import pardillo.john.jv.authorized.database.pojo.User;
import pardillo.john.jv.authorized.style.MyToast;

public class LetterRecipientActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener, TextWatcher {

    private static final int CODE_VIEW_RECIPIENT = 10;

    private EditText txtSearch;
    private Spinner cboSort;
    private ListView lv;

    private ArrayList<User> userList = new ArrayList<>();
    private ArrayList<User> sourceList = new ArrayList<>();
    private ArrayList<User> loginList = new ArrayList<>();
    private UserAdapter adapter;
    private AppDatabase db;

//    private MyToast myToast;

    private String sort = "Name";
    ////////////////////////////////
//    private int lc_id = 0;
//    private int t_id = 0;
    ////////////////////////////////
    private int a_id = 0;
    ////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letter_recipient);

        db = new AppDatabase(this);
//        myToast = new MyToast();

        lv = findViewById(R.id.listView);
        cboSort = findViewById(R.id.cboSort);
        txtSearch = findViewById(R.id.txtSearch);

        try {
            Bundle b = getIntent().getExtras();
//            t_id = b.getInt("t_id");
//            lc_id = b.getInt("lc_id");
            a_id = b.getInt("a_id");

            userList = db.getLogInUser();
            loginList = db.getLogInUser();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(TextUtils.equals(sort, "name")) {
            sourceList = db.getAllUserName(loginList.get(0).getA_username());
            userList = db.getAllUserName(loginList.get(0).getA_username());
        } else if(TextUtils.equals(sort, "rating")) {
            sourceList = db.getAllUserRating(loginList.get(0).getA_id());
            userList = db.getAllUserRating(loginList.get(0).getA_id());
        } else {
            sourceList = db.getAllPastRecipient(loginList.get(0).getA_id());
            userList = db.getAllPastRecipient(loginList.get(0).getA_id());
//            sourceList = db.getPastRecipient(loginList.get(0).getA_id());
//            userList = db.getPastRecipient(loginList.get(0).getA_id());
        }

        adapter = new UserAdapter(this, userList);
        lv.setAdapter(adapter);

        txtSearch.addTextChangedListener(this);
        cboSort.setOnItemSelectedListener(this);
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent viewRecipient = new Intent(this, ViewRecipientActivity.class);
        viewRecipient.putExtra("rec_id", userList.get(position).getA_id());
        viewRecipient.putExtra("a_id", a_id);
        ////////////////////////////////////////////////////////
//        viewRecipient.putExtra("a_id", userList.get(position).getA_id());
//        viewRecipient.putExtra("lc_id", lc_id);
//        viewRecipient.putExtra("t_id", t_id);
        ////////////////////////////////////////////////////////
        startActivityForResult(viewRecipient, CODE_VIEW_RECIPIENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            switch(requestCode) {
                case CODE_VIEW_RECIPIENT:
                    Intent intent = new Intent();
                    this.setResult(Activity.RESULT_OK, intent);
                    this.finish();
//                    Bundle b = data.getExtras();
//                    String letterRecipient = b.getString("recipient");
//                    myToast.makeToast(this, "recipient: " +letterRecipient, "SUCCESS");

                    break;
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        sort = cboSort.getItemAtPosition(position).toString().toLowerCase();
        txtSearch.setText("");
        userList.clear();
        sourceList.clear();

        if(TextUtils.equals(sort, "name")) {
            sourceList = db.getAllUserName(loginList.get(0).getA_username());
            userList = db.getAllUserName(loginList.get(0).getA_username());
        } else if(TextUtils.equals(sort, "rating")) {
            sourceList = db.getAllUserRating(loginList.get(0).getA_id());
            userList = db.getAllUserRating(loginList.get(0).getA_id());
        } else {
            sourceList = db.getAllPastRecipient(loginList.get(0).getA_id());
            userList = db.getAllPastRecipient(loginList.get(0).getA_id());
//            sourceList = db.getPastRecipient(loginList.get(0).getA_id());
//            userList = db.getPastRecipient(loginList.get(0).getA_id());
        }

        adapter = new UserAdapter(this, userList);
        lv.setAdapter(adapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Pattern p = Pattern.compile(s.toString().toLowerCase());
        userList.clear();

        for(int i=0; i<sourceList.size(); i++) {
            Matcher m = p.matcher(sourceList.get(i).getA_fname().toLowerCase());
            Matcher m1 = p.matcher(sourceList.get(i).getA_lname().toLowerCase());
            Matcher m2 = p.matcher(sourceList.get(i).getA_contact().toLowerCase());

            if(m.find() || m1.find() || m2.find()) {
                userList.add(sourceList.get(i));
            }
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}















