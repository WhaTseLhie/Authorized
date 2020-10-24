package pardillo.john.jv.authorized.main.drawer.arep;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.adapter.RepAdapter;
import pardillo.john.jv.authorized.database.pojo.Rep;
import pardillo.john.jv.authorized.database.pojo.User;
import pardillo.john.jv.authorized.style.MyToast;

public class ARepActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final int CODE_VIEW = 10;
    private static final int CODE_ADD = 13;

    private ListView lv;
    private TextView txtEmpty;

    private RepAdapter adapter;
    private ArrayList<Rep> repList = new ArrayList<>();
    private ArrayList<User> userList = new ArrayList<>();

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arep);

        db = new AppDatabase(this);
        userList = db.getLogInUser();
        repList = db.getAllRep(userList.get(0).getA_id());

        txtEmpty = findViewById(R.id.txtEmpty);
        lv = findViewById(R.id.listView);

        adapter = new RepAdapter(this, repList);
        lv.setAdapter(adapter);

        if(repList.isEmpty()) {
            txtEmpty.setVisibility(View.VISIBLE);
        } else {
            txtEmpty.setVisibility(View.INVISIBLE);
        }

        lv.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_person_menu, menu);
        
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.add) {
            Intent repIntent = new Intent(this, AddRepActivity.class);
            startActivityForResult(repIntent, CODE_ADD);
        }
        
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent repIntent = new Intent(this, ViewRepActivity.class);
        repIntent.putExtra("id", repList.get(position).getRep_id());
        repIntent.putExtra("position", position);
        startActivityForResult(repIntent, CODE_VIEW);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        MyToast myToast = new MyToast();

        if(resultCode == Activity.RESULT_OK) {
            switch(requestCode) {
                case CODE_VIEW:
                    try {
                        Bundle deleteBundle = data.getExtras();
                        int position = deleteBundle.getInt("position");

                        ////////////////////
                        db.deleteRepId(repList.get(position).getA_id(), repList.get(position).getRep_id());
                        ////////////////////

                        int deleteId = db.deleteRep(repList.get(position).getA_id(), repList.get(position).getRep_id());
                        repList.remove(position);
                        adapter.notifyDataSetChanged();

                        if(deleteId == 1) {
                            myToast.makeToast(this, "Representative has been removed", "SUCCESS");
                        } else {
                            myToast.makeToast(this, "Something went wrong", "ERROR");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                case CODE_ADD:
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
        }

        if(repList.isEmpty()) {
            txtEmpty.setVisibility(View.VISIBLE);
        } else {
            txtEmpty.setVisibility(View.INVISIBLE);
        }
//        repList = db.getAllRep(1);
//        adapter.notifyDataSetChanged();
    }
}