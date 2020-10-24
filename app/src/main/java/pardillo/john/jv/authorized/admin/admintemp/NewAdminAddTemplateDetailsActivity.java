package pardillo.john.jv.authorized.admin.admintemp;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.style.MyToast;

public class NewAdminAddTemplateDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int CODE_ADD_TEMPLATE_CONTENT = 39;

    private EditText txtTitle;
    private TextView txtType;
    private Button btnSave;

    private AppDatabase db;
    private MyToast myToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_admin_add_template_details);

        myToast = new MyToast();
        db = new AppDatabase(this);

        txtTitle = findViewById(R.id.txtTitle);
        txtType = findViewById(R.id.txtType);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnSave:
                String title = txtTitle.getText().toString();
                String type = txtType.getText().toString();

                if(TextUtils.isEmpty(title) || TextUtils.isEmpty(type)) {
                    myToast.makeToast(this, "Please fill all fields", "ERROR");
                } else {
                    Intent addTemplate = new Intent(this, NewAdminAddTemplateActivity.class);
                    addTemplate.putExtra("title", title);
                    addTemplate.putExtra("type", type);
                    this.startActivityForResult(addTemplate, CODE_ADD_TEMPLATE_CONTENT);
                }

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == CODE_ADD_TEMPLATE_CONTENT) {
                this.finish();
            }
        }
    }
}





















