package pardillo.john.jv.authorized.admin.maincontent;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.pojo.MainContent;
import pardillo.john.jv.authorized.style.MyToast;

public class UpdateMainContentActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText txtName, txtType;
    private Button btnUpdate, btnCancel;

    private AppDatabase db;
    private MyToast myToast;

    ArrayList<MainContent> mcList = new ArrayList<>();

    private int mc_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_main_content);

        myToast = new MyToast();
        db = new AppDatabase(this);

        txtName = findViewById(R.id.txtName);
        txtType = findViewById(R.id.txtType);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnCancel = findViewById(R.id.btnCancel);

        try {
            Bundle b = this.getIntent().getExtras();
            mc_id = b.getInt("mc_id");
            mcList = db.getMainContent(mc_id);

            if(mcList.isEmpty()) {
                myToast.makeToast(this, "Something went wrong", "ERROR");
                this.finish();
            } else {
                txtName.setText(mcList.get(0).getMc_name());
                txtType.setText(mcList.get(0).getMc_type());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnUpdate.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnUpdate:
                String mc_name = txtName.getText().toString();
                String mc_type = txtType.getText().toString();

                if(TextUtils.isEmpty(mc_name) || TextUtils.isEmpty(mc_type)) {
                    myToast.makeToast(this, "Please fill all fields", "ERROR");
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("mc_name", mc_name);
                    intent.putExtra("mc_type", mc_type);
                    this.setResult(Activity.RESULT_OK, intent);
                    this.finish();
                }

                break;
            case R.id.btnCancel:
                this.finish();

                break;
        }
    }
}



















