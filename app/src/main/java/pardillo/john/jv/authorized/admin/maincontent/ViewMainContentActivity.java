package pardillo.john.jv.authorized.admin.maincontent;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.pojo.MainContent;
import pardillo.john.jv.authorized.style.MyToast;

public class ViewMainContentActivity extends AppCompatActivity {

    private static int CODE_UPDATE_MAIN_CONTENT = 3;

    private TextView txtName, txtType;

    private AppDatabase db;
    private MyToast myToast;

    ArrayList<MainContent> mcList = new ArrayList<>();

    private int mc_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_main_content);

        myToast = new MyToast();
        db = new AppDatabase(this);

        txtName = findViewById(R.id.txtName);
        txtType = findViewById(R.id.txtType);

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_content_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.update:
                Intent updateMainContent = new Intent(this, UpdateMainContentActivity.class);
                updateMainContent.putExtra("mc_id", mc_id);
                startActivityForResult(updateMainContent, CODE_UPDATE_MAIN_CONTENT);

                break;
            case R.id.delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Delete Confirmation");
                builder.setMessage("Do you really want to delete " +mcList.get(0).getMc_name());
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        db.deleteMainContent(mc_id);
                        ViewMainContentActivity.this.finish();
                    }
                });
                builder.setNegativeButton("No", null);

                AlertDialog dialog = builder.create();
                dialog.show();

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == CODE_UPDATE_MAIN_CONTENT) {
                Bundle b = null;

                try {
                    b = data.getExtras();
                    String mc_name = b.getString("mc_name");
                    String mc_type = b.getString("mc_type");

                    txtName.setText(mc_name);
                    txtType.setText(mc_type);

                    db.updateMainContent(mc_id, mc_name, mc_type);
                    myToast.makeToast(this, "Content Updated", "SUCCESS");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

















