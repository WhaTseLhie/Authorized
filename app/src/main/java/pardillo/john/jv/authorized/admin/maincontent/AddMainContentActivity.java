package pardillo.john.jv.authorized.admin.maincontent;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.style.MyToast;

public class AddMainContentActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText txtName, txtType;
    private Button btnAdd, btnCancel;

//    private AppDatabase db;
    private MyToast myToast;

//    ArrayList<MainContent> mcList = new ArrayList<>();

    private int mc_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_main_content);

        myToast = new MyToast();
//        db = new AppDatabase(this);

        txtName = findViewById(R.id.txtName);
        txtType = findViewById(R.id.txtType);
        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);

        btnAdd.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnAdd:
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



















