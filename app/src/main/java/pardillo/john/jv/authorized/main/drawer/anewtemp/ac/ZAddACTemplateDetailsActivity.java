package pardillo.john.jv.authorized.main.drawer.anewtemp.ac;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.main.drawer.anewtemp.NewUserAddTemplateActivity;
import pardillo.john.jv.authorized.style.MyToast;

public class ZAddACTemplateDetailsActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Spinner cboSort;
    private Button btnSave;

    private AppDatabase db;
    private MyToast myToast;

    private String sort = "LOA for Collecting Documents";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zadd_actemplate_details);

        myToast = new MyToast();
        db = new AppDatabase(this);

        cboSort = findViewById(R.id.cboSort);
        btnSave = findViewById(R.id.btnSave);

        cboSort.setOnItemSelectedListener(this);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnSave:
                Intent addCTTemplate = new Intent(this, ZOneHeadingActivity.class);
                this.startActivity(addCTTemplate);

                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        sort = cboSort.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}





















