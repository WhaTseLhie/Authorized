package pardillo.john.jv.authorized.main.drawer.atemplate;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.adapter.MainContentAdapter;
import pardillo.john.jv.authorized.database.pojo.MainContent;
import pardillo.john.jv.authorized.database.pojo.User;
import pardillo.john.jv.authorized.style.MyToast;

public class AddTemplateActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int CODE_FINISH_TEMPLATE = 10;

    private ListView lv;
    private TextView txtEmpty;
    private EditText txtItem, txtTitle, txtType;
    private ImageView ivAdd;
    private Button btnSave;

    private AppDatabase db;
    private MyToast myToast;

    private MainContentAdapter adapter;
    private ArrayList<User> userList = new ArrayList<>();

    private String item = "";
    private int mc_index = 0;

    private ArrayList<MainContent> mcList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_template);

        myToast = new MyToast();
        db = new AppDatabase(this);

        userList = db.getLogInUser();
        mcList = db.getAllMainContent();

        txtTitle = findViewById(R.id.txtTitle);
        txtType = findViewById(R.id.txtType);
        ivAdd = findViewById(R.id.ivAdd);
        btnSave = findViewById(R.id.btnSave);
        txtItem = findViewById(R.id.txtItem);
        txtEmpty = findViewById(R.id.txtEmpty);
        lv = findViewById(R.id.listView);

        adapter = new MainContentAdapter(this, mcList);
        lv.setAdapter(adapter);

        if(mcList.isEmpty()) {
            txtEmpty.setVisibility(View.VISIBLE);
        } else {
            txtEmpty.setVisibility(View.INVISIBLE);
            mc_index = mcList.size();

            setListViewHeightBasedOnChildren(lv);
        }

        ivAdd.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.ivAdd:
                item = txtItem.getText().toString();

                if(item.isEmpty()) {
                    myToast.makeToast(this, "Empty Content", "ERROR");
                } else {
                    int new_mc_id = mcList.size();
                    mcList.add(new MainContent(new_mc_id, item, "text"));

                    txtEmpty.setVisibility(View.INVISIBLE);
                    adapter.notifyDataSetChanged();
                    txtItem.setText("");

                    setListViewHeightBasedOnChildren(lv);
                }

                break;
            case R.id.btnSave:
                String t_title = txtTitle.getText().toString();
                String t_type = txtType.getText().toString();

                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy", Locale.getDefault());
                String t_date = sdf.format(c);

                if(TextUtils.isEmpty(t_title) || TextUtils.isEmpty(t_type)) {
                    myToast.makeToast(this, "Please fill all fields", "ERROR");
                } else if(mcList.isEmpty()) {
                    myToast.makeToast(this, "Please add content", "ERROR");
                } else  {
                    int t_id = db.addTemplateDetails("", t_title, t_date, t_type, userList.get(0).getA_id(), "PUBLIC");

                    for(int i=0; i<mcList.size(); i++) {
                        db.addTemplateContent(mcList.get(i).getMc_name(), mcList.get(i).getMc_type(), 0, 0, 0, 0, i, t_id);
                    }

                    txtEmpty.setVisibility(View.INVISIBLE);
                    adapter.notifyDataSetChanged();
                    txtItem.setText("");
                    myToast.makeToast(this, "Dragged Items Positions", "NORMAL");

                    Intent finishIntent = new Intent(this, FinishTemplateActivity.class);
                    finishIntent.putExtra("t_id", t_id);
                    startActivityForResult(finishIntent, CODE_FINISH_TEMPLATE);
                }

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Intent intent = new Intent();
        intent.putExtra("SUCCESS", "SUCCESS");
        this.setResult(Activity.RESULT_OK);
        this.finish();
    }

    private static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;

        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
















