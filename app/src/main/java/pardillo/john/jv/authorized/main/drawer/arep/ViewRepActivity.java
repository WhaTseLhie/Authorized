package pardillo.john.jv.authorized.main.drawer.arep;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.pojo.Rep;
import pardillo.john.jv.authorized.database.pojo.RepId;
import pardillo.john.jv.authorized.database.pojo.User;
import pardillo.john.jv.authorized.style.CircleTransform;
import pardillo.john.jv.authorized.style.MyToast;

public class ViewRepActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "137";
    private static final int CODE_UPDATE_ID = 69;

    private ImageView iv;
    private TextView txtFullName, txtContact, txtGender, txtUpdateInfo, txtUpdateID;
    private LinearLayout lnrImages;

    private AppDatabase db;
    private ArrayList<Rep> repList = new ArrayList<>();
    private ArrayList<RepId> repidList = new ArrayList<>();
    private ArrayList<User> userList = new ArrayList<>();

    private int id = 0, position = 0;
    
    // DIALOGS
    private Dialog infoDialog;

    // UPDATE USER INFORMATION
    private EditText txtEditFname, txtEditLname, txtEditContact;
    private RadioGroup rgGender;

    // DIALOG BUTTONS
    private Button btnSave, btnCancel;

    private MyToast myToast = new MyToast();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rep);

        iv = findViewById(R.id.imageView);
        txtFullName = findViewById(R.id.txtFullName);
        txtContact = findViewById(R.id.txtContact);
        txtGender = findViewById(R.id.txtGender);
        txtUpdateInfo = findViewById(R.id.txtUpdateInfo);
        txtUpdateID = findViewById(R.id.txtUpdateID);
        lnrImages = findViewById(R.id.lnrImages);

        try {
            Bundle b = getIntent().getExtras();
            id = b.getInt("id");
            position = b.getInt("position");

            db = new AppDatabase(this);

            userList = db.getLogInUser();
            repList = db.getRep(id);
            repidList = db.getRepId(userList.get(0).getA_id(), id);

            this.setTitle(repList.get(0).getRep_fname() +"'s Info");

//            iv.setImageURI(repList.get(0).getRep_image());
            Picasso.with(getApplicationContext()).load(repList.get(0).getRep_image()).transform(new CircleTransform()).into(iv);
            txtFullName.setText(repList.get(0).getRep_fname() +" "+ repList.get(0).getRep_lname());
            txtContact.setText(repList.get(0).getRep_contact());
            txtGender.setText(repList.get(0).getRep_gender());

            try {
                lnrImages.removeAllViews();
            } catch (Throwable e) {
                e.printStackTrace();
            }

            for(int i=0; i<repidList.size(); i++) {
                Bitmap yourbitmap = BitmapFactory.decodeFile(repidList.get(i).getRepid_image());

                ImageView imageView = new ImageView(this);
                imageView.setImageBitmap(yourbitmap);
                imageView.setAdjustViewBounds(true);
                lnrImages.addView(imageView);
            }
        } catch(Exception e) {
            Log.d(TAG, " ID IS NULL");
            this.finish();
        }

        txtUpdateInfo.setOnClickListener(this);
        txtUpdateID.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete Confirmation");
            builder.setMessage("Do you really want to delete " +repList.get(0).getRep_fname()+ " as your representative?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                    Intent intent = new Intent();
                    intent.putExtra("position", position);
                    ViewRepActivity.this.setResult(Activity.RESULT_OK, intent);
                    ViewRepActivity.this.finish();

                    /*MyToast myToast = new MyToast();
                    long res = db.deleteRep(repList.get(0).getA_id(), repList.get(0).getRep_id());

                    if(res == 1) {
                        myToast.makeToast(ViewRepActivity.this, "Representative has been removed", "SUCCESS");

                        Intent intent = new Intent();
                        intent.putExtra("id", repList.get(0).getRep_id());
                        ViewRepActivity.this.setResult(Activity.RESULT_OK, intent);
                        ViewRepActivity.this.finish();
                    } else {
                        myToast.makeToast(ViewRepActivity.this, "Something went wrong", "ERROR");
                    }*/
                }
            });
            builder.setNegativeButton("No", null);

            AlertDialog dialog = builder.create();
            dialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.txtUpdateInfo:
                infoDialog = new Dialog(this);
                infoDialog.setContentView(R.layout.layout_edit_user_info);
    
                txtEditFname = infoDialog.findViewById(R.id.txtFname);
                txtEditLname = infoDialog.findViewById(R.id.txtLname);
                txtEditContact = infoDialog.findViewById(R.id.txtContact);
                rgGender = infoDialog.findViewById(R.id.rgGender);
                btnSave = infoDialog.findViewById(R.id.btnSave);
                btnCancel = infoDialog.findViewById(R.id.btnCancel);
    
                txtEditFname.setText(repList.get(0).getRep_fname());
                txtEditLname.setText(repList.get(0).getRep_lname());
                txtEditContact.setText(repList.get(0).getRep_contact());
                if (repList.get(0).getRep_gender().equals("Male")) {
                    rgGender.check(R.id.rbMale);
                } else {
                    rgGender.check(R.id.rbFemale);
                }
    
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String errMsg = "This field is empty.";
                        String fname = txtEditFname.getText().toString();
                        String lname = txtEditLname.getText().toString();
                        String contact = txtEditContact.getText().toString();
    
                        if (TextUtils.isEmpty(fname)) {
                            txtEditFname.requestFocus();
                            txtEditFname.setError(errMsg);
                        } else if (TextUtils.isEmpty(lname)) {
                            txtEditLname.requestFocus();
                            txtEditLname.setError(errMsg);
                        } else if (TextUtils.isEmpty(contact)) {
                            txtEditContact.requestFocus();
                            txtEditContact.setError(errMsg);
                        } else {
                            int selectedSex = rgGender.getCheckedRadioButtonId();
                            RadioButton selectedButton = infoDialog.findViewById(selectedSex);
                            String gender = selectedButton.getText().toString();
    
                            repList.get(0).setRep_fname(txtEditFname.getText().toString());
                            repList.get(0).setRep_lname(txtEditLname.getText().toString());
                            repList.get(0).setRep_contact(txtEditContact.getText().toString());
                            repList.get(0).setRep_gender(gender);
    
                            txtFullName.setText(repList.get(0).getRep_fname() + " " + repList.get(0).getRep_lname());
                            txtContact.setText(repList.get(0).getRep_contact());
                            txtGender.setText(repList.get(0).getRep_gender());

                            ViewRepActivity.this.setTitle(repList.get(0).getRep_fname() +"'s Info");
                            db.updateRepInfo(repList.get(0).getA_id(), repList.get(0).getRep_id(), fname, lname, contact, gender);
                            myToast.makeToast(ViewRepActivity.this, "Information Successfully Updated", "SUCCESS");
                            infoDialog.dismiss();
                        }
                    }
                });
    
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        infoDialog.dismiss();
                    }
                });
    
                infoDialog.show();

                break;
            case R.id.txtUpdateID:
                Intent updateRepId = new Intent(this, UpdateRepIdActivity.class);
                updateRepId.putExtra("a_id", repList.get(0).getA_id());
                updateRepId.putExtra("rep_id", repList.get(0).getRep_id());
                startActivityForResult(updateRepId, CODE_UPDATE_ID);

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == CODE_UPDATE_ID) {
                this.finish();
                this.startActivity(getIntent());
            }
        }
    }
}
















