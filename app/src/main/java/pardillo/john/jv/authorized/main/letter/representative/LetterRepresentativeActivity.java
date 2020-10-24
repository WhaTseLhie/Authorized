package pardillo.john.jv.authorized.main.letter.representative;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.adapter.RepAdapter;
import pardillo.john.jv.authorized.database.pojo.Rep;
import pardillo.john.jv.authorized.database.pojo.User;
import pardillo.john.jv.authorized.style.MyToast;

public class LetterRepresentativeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, TextWatcher {

    private static final int CODE_VIEW_REPRESENTATIVE = 10;
    private static final int CODE_ADD_REPRESENTATIVE = 13;

    private ListView lv;
    private EditText txtSearch;
    private TextView txtEmpty;

    private ArrayList<Rep> repList = new ArrayList<>();
    private ArrayList<Rep> sourceList = new ArrayList<>();
    private ArrayList<User> userList = new ArrayList<>();

    private AppDatabase db;
    private MyToast myToast;
    private RepAdapter adapter;

    /*private int t_id = 0;
    private int lc_id = 0;*/
    private int a_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letter_representative);

        myToast = new MyToast();

        db = new AppDatabase(this);
        userList = db.getLogInUser();

        try {
            Bundle b = getIntent().getExtras();
            a_id = b.getInt("a_id");
//            t_id = b.getInt("t_id");
//            lc_id = b.getInt("lc_id");
        } catch (Exception e) {
            e.printStackTrace();
        }

        repList = db.getAllRep(userList.get(0).getA_id());
        sourceList = db.getAllRep(userList.get(0).getA_id());

        txtSearch = findViewById(R.id.txtSearch);
        txtEmpty = findViewById(R.id.txtEmpty);
        lv = findViewById(R.id.listView);

        adapter = new RepAdapter(this, repList);
        lv.setAdapter(adapter);

        if(repList.isEmpty()) {
            txtEmpty.setVisibility(View.VISIBLE);
        } else {
            txtEmpty.setVisibility(View.INVISIBLE);
        }

        txtSearch.addTextChangedListener(this);
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent viewRep = new Intent(this, ViewRepresentativeActivity.class);
        viewRep.putExtra("rep_id", repList.get(position).getRep_id());
        viewRep.putExtra("a_id", a_id);
//        viewRep.putExtra("lc_id", lc_id);
//        viewRep.putExtra("t_id", t_id);
        startActivityForResult(viewRep, CODE_VIEW_REPRESENTATIVE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_person_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.add:
                Intent repIntent = new Intent(this, LetterAddRepActivity.class);
                startActivityForResult(repIntent, CODE_ADD_REPRESENTATIVE);

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CODE_VIEW_REPRESENTATIVE:
                    Intent intent = new Intent();
                    this.setResult(Activity.RESULT_OK, intent);
                    this.finish();

                    break;
                case CODE_ADD_REPRESENTATIVE:
                    Bundle addBundle = data.getExtras();
                    ArrayList<String> imagesPathList = addBundle.getStringArrayList("imagesPathList");
                    String image = addBundle.getString("image");
                    String fname = addBundle.getString("fname");
                    String lname = addBundle.getString("lname");
                    String contact = addBundle.getString("contact");
                    String gender = addBundle.getString("gender");
                    String ids = addBundle.getString("ids");

                    if(imagesPathList != null) {
                        if (!imagesPathList.isEmpty()) {
                            long addId = db.addRep(image, fname, lname, contact, gender, ids, userList.get(0).getA_id());

                            if (addId > 0) {
                                int rep_id = Integer.parseInt(addId + "");
                                Rep rep = new Rep(rep_id, Uri.parse(image), fname, lname, contact, gender, ids, userList.get(0).getA_id());
                                repList.add(rep);
                                adapter.notifyDataSetChanged();

                                // ADD TO DB
                                for (int i = 0; i < imagesPathList.size(); i++) {
                                    db.addRepId(userList.get(0).getA_id(), rep_id, imagesPathList.get(i));
                                }

                                myToast.makeToast(this, "You added a new representative", "SUCCESS");
                            } else {
                                myToast.makeToast(this, "Something went wrong", "ERROR");
                            }
                        } else {
                            myToast.makeToast(this, "Something went wrong", "ERROR");
                        }
                    }

                    break;
            }

            if(repList.isEmpty()) {
                txtEmpty.setVisibility(View.VISIBLE);
            } else {
                txtEmpty.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Pattern p = Pattern.compile(s.toString().toLowerCase());
        repList.clear();

        for(int i=0; i<sourceList.size(); i++) {
            Matcher m = p.matcher(sourceList.get(i).getRep_fname().toLowerCase());
            Matcher m1 = p.matcher(sourceList.get(i).getRep_lname().toLowerCase());
            Matcher m2 = p.matcher(sourceList.get(i).getRep_contact().toLowerCase());

            if(m.find() || m1.find() || m2.find()) {
                repList.add(sourceList.get(i));
            }
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}