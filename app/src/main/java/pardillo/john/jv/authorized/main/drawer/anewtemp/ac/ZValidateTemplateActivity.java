package pardillo.john.jv.authorized.main.drawer.anewtemp.ac;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.pojo.User;
import pardillo.john.jv.authorized.style.MyToast;

public class ZValidateTemplateActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivDate, ivRec, ivOT, ivYourName, ivRep, ivCT, ivSign;
    private Button btnSave, btnBack;

    private String letterContent = "";
    private String t_image = "";

    private AppDatabase db;
    private MyToast myToast;

    private ArrayList<User> userList = new ArrayList<>();

    private boolean isValidated = false;
    private String s = "";

    private Spinner cboSort;
    private Dialog detailsDialog;

    private String t_title = "";
    private String sort = "LOA for Collecting Documents";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zvalidate_template);

        ivDate = findViewById(R.id.ivDate);
        ivRec = findViewById(R.id.ivRec);
        ivOT = findViewById(R.id.ivOT);
        ivYourName = findViewById(R.id.ivYourName);
        ivRep = findViewById(R.id.ivRep);
        ivCT = findViewById(R.id.ivCT);
        ivSign = findViewById(R.id.ivSign);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);

        try {
            Bundle b = getIntent().getExtras();
            letterContent = b.getString("letterContent");
            t_image = b.getString("t_image");

            boolean hasDate = letterContent.contains("[DATE]");
            boolean hasRecipient = letterContent.contains("[RECIPIENT]");
            boolean hasOpeningTag = letterContent.contains("[SALUTATION]");
            boolean hasYourName = letterContent.contains("[YOUR NAME]");
            boolean hasRepresentative = letterContent.contains("[REPRESENTATIVE]");
            boolean hasClosingTag = letterContent.contains("[CLOSING]");
            boolean hasSignature = letterContent.contains("[SIGNATURE]");

            if(!hasDate) {
                ivDate.setImageResource(R.drawable.ic_error);
            }

            if(!hasRecipient) {
                ivRec.setImageResource(R.drawable.ic_error);
            }

            if(!hasOpeningTag) {
                ivOT.setImageResource(R.drawable.ic_error);
            }

            if(!hasYourName) {
                ivYourName.setImageResource(R.drawable.ic_error);
            }

            if(!hasRepresentative) {
                ivRep.setImageResource(R.drawable.ic_error);
            }

            if(!hasClosingTag) {
                ivCT.setImageResource(R.drawable.ic_error);
            }

            if(!hasSignature) {
                ivSign.setImageResource(R.drawable.ic_error);
            }

            if(hasDate &&
                    hasRecipient &&
                    hasOpeningTag &&
                    hasYourName &&
                    hasRepresentative &&
                    hasClosingTag &&
                    hasSignature) {
                isValidated = true;
            } else {
                btnSave.setClickable(false);
                btnSave.setBackgroundResource(R.drawable.style_button_not_valid);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(isValidated) {
            btnSave.setOnClickListener(this);
        }
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnSave:
                detailsDialog = new Dialog(this);
                detailsDialog.setContentView(R.layout.layout_template_details);
                detailsDialog.setCancelable(false);

                cboSort = detailsDialog.findViewById(R.id.cboSort);
                Button btnSave = detailsDialog.findViewById(R.id.btnSave);
                Button btnCancel = detailsDialog.findViewById(R.id.btnCancel);

                cboSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        sort = cboSort.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        detailsDialog.dismiss();
                        Intent intent = new Intent();
                        intent.putExtra("isValidated", isValidated);
                        intent.putExtra("t_image", t_image);
                        intent.putExtra("t_title", t_title);
                        intent.putExtra("t_type", sort);
                        ZValidateTemplateActivity.this.setResult(Activity.RESULT_OK, intent);
                        ZValidateTemplateActivity.this.finish();
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        detailsDialog.dismiss();
                    }
                });

                detailsDialog.show();

                break;
            case R.id.btnBack:
                this.finish();

                break;
        }
    }
}








