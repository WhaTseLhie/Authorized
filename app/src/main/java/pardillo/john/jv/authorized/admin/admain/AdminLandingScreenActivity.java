package pardillo.john.jv.authorized.admin.admain;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.admin.AdminMyTemplateActivity;
import pardillo.john.jv.authorized.admin.AdminPaymentActivity;
import pardillo.john.jv.authorized.admin.adminct.AdminCTActivity;
import pardillo.john.jv.authorized.admin.adminot.AdminOTActivity;
import pardillo.john.jv.authorized.admin.admintemp.NewAdminAddTemplateActivity;
import pardillo.john.jv.authorized.admin.admintemp.NewAdminMyTemplateActivity;
import pardillo.john.jv.authorized.admin.maincontent.MainContentActivity;
import pardillo.john.jv.authorized.admin.vsent.AdminViewAllUserSentActivity;
import pardillo.john.jv.authorized.admin.vuser.AdminViewAllUserActivity;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.pojo.User;
import pardillo.john.jv.authorized.style.CircleTransform;
import pardillo.john.jv.authorized.style.MyToast;

public class AdminLandingScreenActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    
    private static final int CODE_VIEW_MY_TEMPLATE = 69;
    private static final int CODE_VIEW_MAIN_CONTENT = 137;

    private AppDatabase db;
    private ArrayList<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_landing_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new AppDatabase(this);
        userList = db.getLogInUser();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);


        try {
            ImageView iv = headerView.findViewById(R.id.imageView);
            TextView txtFullName = headerView.findViewById(R.id.textView);
            TextView txtContact = headerView.findViewById(R.id.textView2);
//            iv.setImageURI(userList.get(0).getA_image());

            Picasso.with(getApplicationContext()).load(userList.get(0).getA_image()).transform(new CircleTransform()).into(iv);
            txtFullName.setText("Name: " +userList.get(0).getA_fname() +" "+ userList.get(0).getA_lname());
            txtContact.setText("Contact: " +userList.get(0).getA_contact());

            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ////////////////////////////////////////////
//        if(db.getAllMainContent().isEmpty()) {
//            db.addMainContent("date", "date");
//            db.addMainContent("recipient", "recipient");
//            db.addMainContent("opening", "text");
//            db.addMainContent("content", "content");
//            db.addMainContent("closing", "text");
//            db.addMainContent("signature", "image");
//            db.addMainContent("name", "authorizer");
            /*
            ////////////////////////////////////////////
            db.addTemplateDetails("content://media/external/images/media/7979", "claim tor", "05/02/2020","claim letter", 1);
            ////////////////////////////////////////////
            db.addTemplateContent("date", "date",30,10,-250,-250,1, 1);
            db.addTemplateContent("recipient", "recipient",30,100,-250,-250,2, 1);
            db.addTemplateContent("opening", "text",30,220,-250,-250,3, 1);
            db.addTemplateContent("content", "content",30,290,-250,-250,4, 1);
            db.addTemplateContent("closing", "text",30,429,-250,-250,5, 1);
            db.addTemplateContent("signature", "image",30,527,-250,-250,6, 1);
            db.addTemplateContent("name", "authorizer",30,618,-250,-250,7, 1);
            ////////////////////////////////////////////
            */
//        }

        /*try {
            InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        } catch(Exception e) {
            e.printStackTrace();
        }*/

        AdminTemplateFragment templateFragment = new AdminTemplateFragment();
        FragmentManager templateManager = getSupportFragmentManager();
        templateManager.beginTransaction().replace(
                R.id.constraintLayout,
                templateFragment,
                templateFragment.getTag()
        ).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirmation");
            builder.setMessage("Are you sure you want to logout?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    AdminLandingScreenActivity.this.finish();
                }
            });
            builder.setNegativeButton("No", null);

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_template_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.view:
                Intent viewAllMyTemplate = new Intent(this, AdminMyTemplateActivity.class);
                startActivityForResult(viewAllMyTemplate, CODE_VIEW_MY_TEMPLATE);

                break;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        /*if (id == R.id.template) {
            Intent viewAllMyTemplate = new Intent(this, NewAdminMyTemplateActivity.class);
            startActivityForResult(viewAllMyTemplate, CODE_VIEW_MY_TEMPLATE);
//            Intent viewAllMyTemplate = new Intent(this, AdminMyTemplateActivity.class);
//            startActivityForResult(viewAllMyTemplate, CODE_VIEW_MY_TEMPLATE);
        }*//* else if (id == R.id.mainContent) {
            Intent viewMainContent = new Intent(this, MainContentActivity.class);
            startActivityForResult(viewMainContent, CODE_VIEW_MAIN_CONTENT);
        }*/ /*else*/ if (id == R.id.payment) {
            Intent paymentIntent = new Intent(this, AdminPaymentActivity.class);
            startActivity(paymentIntent);
        } else if (id == R.id.ot) {
            Intent openingTag = new Intent(this, AdminOTActivity.class);
            startActivity(openingTag);
        } else if (id == R.id.ct) {
            ////////////////////////////////////////
            Intent closingTag = new Intent(this, AdminCTActivity.class);
            startActivity(closingTag);
        }/* else if (id == R.id.vuser) {
            Intent viewAllUserIntent = new Intent(this, AdminViewAllUserActivity.class);
            startActivity(viewAllUserIntent);
        } else if (id == R.id.vsent) {
            Intent viewAllTransIntent = new Intent(this, AdminViewAllUserSentActivity.class);
            startActivity(viewAllTransIntent);
        }*/ else if (id == R.id.db) {
            Intent viewDB = new Intent(this, AndroidDatabaseManager.class);
            startActivity(viewDB);
        } else if (id == R.id.logout) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want to logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyToast myToast = new MyToast();
                        myToast.makeToast(AdminLandingScreenActivity.this, "Goodbye Admin", "NORMAL");
                        AdminLandingScreenActivity.this.finish();
                    }
                });
                builder.setNegativeButton("No", null);

                AlertDialog dialog = builder.create();
                dialog.show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        this.finish();
        this.startActivity(getIntent());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 100:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    final Dialog dialog2 = new Dialog(this);
                    dialog2.setContentView(R.layout.layout_permission);

                    TextView t1 = (TextView) dialog2.findViewById(R.id.txtTitle);
                    TextView t2 = (TextView) dialog2.findViewById(R.id.txtMessage);
                    t1.setText("Request Permissions");
                    t2.setText("Please allow permissions if you want this application to perform the task.");

                    Button dialogButton = (Button) dialog2.findViewById(R.id.btnOk);
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog2.dismiss();
                        }
                    });

                    dialog2.show();
                }

                break;
        }
    }
}














