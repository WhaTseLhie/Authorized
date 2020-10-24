package pardillo.john.jv.authorized.main;

import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.pojo.User;
import pardillo.john.jv.authorized.main.drawer.ANotificationActivity;
import pardillo.john.jv.authorized.main.drawer.aprofile.AProfileActivity;
import pardillo.john.jv.authorized.main.drawer.areceive.AReceiveActivity;
import pardillo.john.jv.authorized.main.drawer.arep.ARepActivity;
import pardillo.john.jv.authorized.main.drawer.asent.ASentActivity;
import pardillo.john.jv.authorized.main.drawer.anewtemp.ATemplateActivity;
import pardillo.john.jv.authorized.style.CircleTransform;

public class LandingScreenActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DialogInterface.OnClickListener {

    private static final int CODE_VIEW_MY_TEMPLATE = 69;

    private AppDatabase db;
    private ArrayList<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_screen);

        db = new AppDatabase(this);
        userList = db.getLogInUser();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
                try {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);

        ImageView iv = headerView.findViewById(R.id.imageView);
        TextView txtFullName = headerView.findViewById(R.id.textView);
        TextView txtContact = headerView.findViewById(R.id.textView2);
//        iv.setImageURI(userList.get(0).getA_image());

        Picasso.with(getApplicationContext()).load(userList.get(0).getA_image()).transform(new CircleTransform()).into(iv);
        txtFullName.setText("Name: " +userList.get(0).getA_fname() +" "+ userList.get(0).getA_lname());
        txtContact.setText("Contact: " +userList.get(0).getA_contact());

        ///////////////////////////////////////////////////////////////
        // NOTIFICATION
        ///////////////////////////////////////////////////////////////
        try {
            if(db.hasUnclaimed(userList.get(0).getA_id())) {
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
                String dateToday = sdf.format(c);

                db.addNotification(
                        "Unclaimed Documents",
                        "Unclaimed Documents Notification",
                        dateToday,
                        "Your representative didn't claim the documents on time",
                        userList.get(0).getA_id(),
                        0,
                        0,
                        0
                );

                try {
                    String message = "Your representative didn't claim the document on time";
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(
                            LandingScreenActivity.this)
                            .setSmallIcon(R.drawable.authorized_logo)
                            .setContentTitle("Unclaimed Documents Notification")
                            .setContentText(message)
                            .setAutoCancel(true);

                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, builder.build());
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ///////////////////////////////////////////////////////////////

        TemplateFragment templateFragment = new TemplateFragment();
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
            builder.setPositiveButton("Yes", this);
            builder.setNegativeButton("No", null);

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.landing_screen, menu);
//
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch(item.getItemId()) {
//            case R.id.write:
////                Intent writeIntent = new Intent(this, WriteActivity.class);
////                startActivity(writeIntent);
//
//                break;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        } catch(Exception e) {
            e.printStackTrace();
        }

        switch(item.getItemId()) {
            case R.id.profile:
                Intent profileIntent = new Intent(this, AProfileActivity.class);
                startActivity(profileIntent);

                break;
            case R.id.notification:
                Intent notIntent = new Intent(this, ANotificationActivity.class);
                startActivity(notIntent);

                break;
            case R.id.template:
                Intent viewAllMyTemplate = new Intent(this, ATemplateActivity.class);
                startActivityForResult(viewAllMyTemplate, CODE_VIEW_MY_TEMPLATE);

                break;
            case R.id.rep:
                Intent repIntent = new Intent(this, ARepActivity.class);
                startActivity(repIntent);

                break;
            /*case R.id.draft:
                Intent draftIntent = new Intent(this, ADraftActivity.class);
                startActivity(draftIntent);

                break;*/
            case R.id.receive:
                Intent receiveIntent = new Intent(this, AReceiveActivity.class);
                startActivity(receiveIntent);

                break;
            case R.id.sent:
                Intent sentIntent = new Intent(this, ASentActivity.class);
                startActivity(sentIntent);

                break;
            case R.id.logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want to logout?");
                builder.setPositiveButton("Yes", this);
                builder.setNegativeButton("No", null);

                AlertDialog dialog = builder.create();
                dialog.show();

                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch(which) {
            case DialogInterface.BUTTON_POSITIVE:
                this.finish();

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        this.finish();
        this.startActivity(getIntent());
    }
}