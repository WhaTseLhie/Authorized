package pardillo.john.jv.authorized.main.letter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.admin.AdminAddTemplateActivity;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.pojo.Draft;
import pardillo.john.jv.authorized.database.pojo.LetterContent;
import pardillo.john.jv.authorized.database.pojo.LetterDetails;
import pardillo.john.jv.authorized.database.pojo.Rep;
import pardillo.john.jv.authorized.database.pojo.TemplateContent;
import pardillo.john.jv.authorized.database.pojo.TemplateDetails;
import pardillo.john.jv.authorized.database.pojo.User;
import pardillo.john.jv.authorized.main.LandingScreenActivity;
import pardillo.john.jv.authorized.main.letter.recipient.LetterRecipientActivity;
import pardillo.john.jv.authorized.main.letter.representative.LetterRepresentativeActivity;
import pardillo.john.jv.authorized.main.letter.representative.NewViewProgressActivity;
import pardillo.john.jv.authorized.style.MyToast;

public class NewCreateLetterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int CODE_SELECT_RECIPIENT = 20;
    private static final int CODE_SELECT_REPRESENTATIVE = 15;

    private ViewGroup rootLayout;
    private int t_id;

    private TextView tv[];

    private AppDatabase db;
    private MyToast myToast;
    
    private ArrayList<User> userList = new ArrayList<>();
    private ArrayList<LetterDetails> letterDetailList = new ArrayList<>();
    private ArrayList<LetterContent> letterContentList = new ArrayList<>();

    private int lc_id = 0;
    private String newContent = "";
    private TextView txtLetterContent = null;

    private int repid = 0, recid = 0, s_id = 0, r_id = 0;

    private Dialog dialog;
    private EditText txtLetterAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_create_letter);

        myToast = new MyToast();
        db = new AppDatabase(this);
        rootLayout = findViewById(R.id.view_root);

        try {
            Bundle b = getIntent().getExtras();
            t_id = b.getInt("t_id");
            userList = db.getLogInUser();
            letterDetailList = db.getLetterDetails(t_id);
            letterContentList = db.getLetterContent(t_id);

            if(letterDetailList.isEmpty()) {
                this.finish();
                startActivity(getIntent());
            } else {
                tv = new TextView[letterContentList.size()];

                for(int i=0; i<letterContentList.size(); i++) {
                    tv[i] = new TextView(this);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    params.leftMargin = letterContentList.get(i).getC_leftMargin();
                    params.topMargin = letterContentList.get(i).getC_topMargin();
//                    params.rightMargin = letterContentList.get(i).getC_rightMargin();
//                    params.bottomMargin = letterContentList.get(i).getC_bottomMargin();
                    tv[i].setPadding(15,8, 15, 8);
                    tv[i].setId(letterContentList.get(i).getLc_id());

                    ////////////////////////////////////////////////////////////
                    switch(letterContentList.get(i).getC_type()) {
                        case "text":
                            tv[i].setText(letterContentList.get(i).getC_name());
                            db.updateLetterContentTextLCID(letterContentList.get(i).getLc_id(), letterContentList.get(i).getC_name(), t_id, userList.get(0).getA_id());

                            break;
                        case "date":
                            Date c = Calendar.getInstance().getTime();
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                            String date = sdf.format(c);
                            tv[i].setText(date);
                            db.updateLetterContentTextLCID(letterContentList.get(i).getLc_id(), date, t_id, userList.get(0).getA_id());

                            break;
                        case "recipient":
                            String rec_id = letterContentList.get(i).getC_name();
                            boolean isNumerec = TextUtils.isDigitsOnly(rec_id);

                            if(rec_id.equals("")) {
                                tv[i].setText(letterContentList.get(i).getC_name());
                            } else {
                                if(isNumerec) {
                                    try {
                                        recid = Integer.parseInt(rec_id);
                                        ArrayList<User> recipientList = db.getUser(Integer.parseInt(rec_id));
                                        tv[i].setText(recipientList.get(0).getA_fname() + " " + recipientList.get(0).getA_lname());
                                    } catch (NumberFormatException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    tv[i].setText(letterContentList.get(i).getC_name());
                                }
                            }

                            break;
                        case "content":
                            String rep_id = letterContentList.get(i).getC_name();
                            boolean isNumerep = TextUtils.isDigitsOnly(rep_id);

                            if(!rep_id.isEmpty() && isNumerep) {
                                try {
                                    repid = Integer.parseInt(rep_id);
                                    ArrayList<Rep> repList = db.getRep(userList.get(0).getA_id(), Integer.parseInt(rep_id));
                                    tv[i].setText(repList.get(0).getRep_fname() + " " + repList.get(0).getRep_lname());
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                tv[i].setText(letterContentList.get(i).getC_name());
                            }

                            break;
                        case "image":
                            String signature = userList.get(0).getA_signature();
                            tv[i].setText(signature);

                            break;
                        case "authorizer":
                            String name = userList.get(0).getA_fname() +" "+ userList.get(0).getA_lname();
                            tv[i].setText(name);

                            break;
                    }
                    ////////////////////////////////////////////////////////////



//                    setContents(letterContentList.get(i).getLc_id());
                    tv[i].setBackgroundResource(R.drawable.style_text_view_border);
                    tv[i].setLayoutParams(params);
                    rootLayout.addView(tv[i]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < letterContentList.size(); i++) {
            tv[i].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        lc_id = v.getId();
        txtLetterContent = findViewById(lc_id);
        ArrayList<LetterContent> lcList = db.getLetterContentLCID(v.getId());

        switch (lcList.get(0).getC_type()) {
            case "text":
                if(txtLetterContent != null) {
                    inputContent(txtLetterContent.getText().toString());
                }

                break;
            case "date":
                

                break;
            case "recipient":
                Intent selectRecipient = new Intent(this, LetterRecipientActivity.class);
                selectRecipient.putExtra("lc_id", v.getId());
                selectRecipient.putExtra("t_id", t_id);
                startActivityForResult(selectRecipient, CODE_SELECT_RECIPIENT);

                break;
            case "content":
                Intent selectRep = new Intent(this, LetterRepresentativeActivity.class);
                selectRep.putExtra("lc_id", v.getId());
                selectRep.putExtra("t_id", t_id);
                startActivityForResult(selectRep, CODE_SELECT_REPRESENTATIVE);

                break;
            case "image":

                break;
            case "authorizer":

                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_progress_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.view:
                Intent viewProgress = new Intent(this, NewViewProgressActivity.class);
                viewProgress.putExtra("t_id", t_id);
                startActivity(viewProgress);

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void inputContent(String content) {
        newContent = content;
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.layout_letter_address);

        txtLetterAddress = dialog.findViewById(R.id.txtLetterAddress);
        txtLetterAddress.setHint(newContent);
        ImageView ivSave = dialog.findViewById(R.id.ivSave);

        if(!TextUtils.isEmpty(newContent)) {
            txtLetterAddress.setText(newContent);
        }

        ivSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newContent = txtLetterAddress.getText().toString();

                if(TextUtils.isEmpty(newContent)) {
                    myToast.makeToast(NewCreateLetterActivity.this, "Must not be empty", "ERROR");
                } else {
                    try {
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    } catch(Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        txtLetterContent.setText(newContent);
                        int res = db.updateLetterContentTextLCID(txtLetterContent.getId(), newContent, t_id, userList.get(0).getA_id());
                        dialog.dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        dialog.show();
    }

    /*private void servicePayment() {
        final Dialog paymentDialog = new Dialog(this);
        paymentDialog.setContentView(R.layout.layout_payment);
        paymentDialog.setCancelable(false);

        final TextView txtMessage = paymentDialog.findViewById(R.id.txtMessage);
        Button btnPay = paymentDialog.findViewById(R.id.btnPay);
        Button btnCancel = paymentDialog.findViewById(R.id.btnCancel);

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendLetter();
                paymentDialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentDialog.dismiss();
            }
        });

        paymentDialog.show();
    }

    private void sendLetter() {
        int a_id = userList.get(0).getA_id();
        int rep_id = repid;
        int rec_id = recid;

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy", Locale.getDefault());
        String dateToday = sdf.format(c);

        s_id = db.addSent(
                dateToday,
                t_id,
                a_id,
                rep_id,
                rec_id,
                0,
                0,
                "PENDING"
        );

        r_id = db.addReceive(
                dateToday,
                t_id,
                a_id,
                rep_id,
                rec_id,
                0,
                s_id,
                "PENDING"
        );

        db.updateSentRID(s_id, r_id);

        /////////////////////////////////////////////////////////////////////////////////
        // SENT
        /////////////////////////////////////////////////////////////////////////////////
        db.addLetterSentDetails(
                letterDetailList.get(0).getT_image().toString(),
                letterDetailList.get(0).getT_title(),
                letterDetailList.get(0).getT_date(),
                letterDetailList.get(0).getT_type(),
                letterDetailList.get(0).getT_id(),
                userList.get(0).getA_id(),
                s_id,
                r_id
        );

        ArrayList<LetterContent> lcList = db.getLetterContent(t_id);
        for(int i=0; i<lcList.size(); i++) {
            db.addLetterSentContent(
                    lcList.get(i).getC_name(),
                    lcList.get(i).getC_type(),
                    lcList.get(i).getC_leftMargin(),
                    lcList.get(i).getC_topMargin(),
                    lcList.get(i).getC_rightMargin(),
                    lcList.get(i).getC_bottomMargin(),
                    lcList.get(i).getLc_id(),
                    lcList.get(0).getT_id(),
                    userList.get(0).getA_id(),
                    s_id,
                    r_id
            );
        }
        /////////////////////////////////////////////////////////////////////////////////

        /////////////////////////////////////////////////////////////////////////////////
        // RECEIVE
        /////////////////////////////////////////////////////////////////////////////////
        db.addLetterReceiveDetails(
                letterDetailList.get(0).getT_image().toString(),
                letterDetailList.get(0).getT_title(),
                letterDetailList.get(0).getT_date(),
                letterDetailList.get(0).getT_type(),
                letterDetailList.get(0).getT_id(),
                userList.get(0).getA_id(),
                s_id,
                r_id
        );

        for(int i=0; i<letterContentList.size(); i++) {
            db.addLetterReceiveContent(
                    lcList.get(i).getC_name(),
                    lcList.get(i).getC_type(),
                    lcList.get(i).getC_leftMargin(),
                    lcList.get(i).getC_topMargin(),
                    lcList.get(i).getC_rightMargin(),
                    lcList.get(i).getC_bottomMargin(),
                    lcList.get(i).getLc_id(),
                    lcList.get(0).getT_id(),
                    userList.get(0).getA_id(),
                    s_id,
                    r_id
            );
        }
        /////////////////////////////////////////////////////////////////////////////////

        ArrayList<User> aList = db.getUser(a_id);
        ArrayList<User> recList = db.getUser(rec_id);

        String stype = "Sent";
        String stitle = "Request Notification";
        String smessage = "You sent letter to " +recList.get(0).getA_fname()+ " " +recList.get(0).getA_lname();
        db.addNotification(stype, stitle, dateToday, smessage, a_id, 0, s_id, r_id);

        String rtype = "Receive";
        String rtitle = "Request Notification";
        String rmessage = "You received a new request from " +aList.get(0).getA_fname()+ " " +aList.get(0).getA_lname();
        db.addNotification(rtype, rtitle, dateToday, rmessage, rec_id, 0, s_id, r_id);

        db.addPayment(dateToday, "PayPal", "20", a_id, rec_id, s_id, r_id);

        db.deleteDraft(t_id, a_id);
        db.deleteLetterDetails(t_id, a_id);
        db.deleteLetterContent(t_id, a_id);

        myToast.makeToast(this, "Letter Sent", "SUCCESS");
        Intent mainScreen = new Intent(this, LandingScreenActivity.class);
        mainScreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainScreen);
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        this.finish();
        this.startActivity(getIntent());
    }
}