package pardillo.john.jv.authorized.admin.admintemp.admincreate;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.pojo.ClickLetterContent;
import pardillo.john.jv.authorized.database.pojo.NewDraft;
import pardillo.john.jv.authorized.database.pojo.NewTemplate;
import pardillo.john.jv.authorized.database.pojo.User;
import pardillo.john.jv.authorized.main.letter.recipient.LetterRecipientActivity;
import pardillo.john.jv.authorized.style.MyToast;

public class NewAdminCreateLetterActivity extends AppCompatActivity {

    private static final int CODE_SELECT_RECIPIENT = 20;
    private static final int CODE_SELECT_REPRESENTATIVE = 15;

    private TextView txtLetterContent;

    private AppDatabase db;
    private MyToast myToast;

    private ArrayList<ClickLetterContent> clcList = new ArrayList<>();
    private ArrayList<NewTemplate> templateList = new ArrayList<>();
    private ArrayList<User> userList = new ArrayList<>();
    private ArrayList<NewDraft> draftList = new ArrayList<>();

    private int t_id;
    private String fullName = "", dateToday = "";
    private ClickableSpan clickableSpan[];
    private Uri signatureUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_admin_create_letter);

        myToast = new MyToast();
        db = new AppDatabase(this);

        txtLetterContent = findViewById(R.id.txtLetterContent);

        try {
            Bundle b = getIntent().getExtras();
            t_id = b.getInt("t_id");
            templateList = db.getNewTemplate(t_id);
            userList = db.getLogInUser();
            draftList = db.getNewDraft(userList.get(0).getA_id());
            fullName = userList.get(0).getA_fname() +" "+ userList.get(0).getA_lname();
            Date date = Calendar.getInstance().getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            dateToday = sdf.format(date);

            String strLetterContent = templateList.get(0).getT_letterContent();
            SpannableStringBuilder ssbLetterContent = new SpannableStringBuilder(strLetterContent);

            if(draftList.isEmpty()) {
                db.addNewDraft(dateToday, strLetterContent, "", "", t_id, userList.get(0).getA_id(), 0, 0);
                draftList = db.getNewDraft(userList.get(0).getA_id());
            }

            ClickableSpan clickRecipient = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    Intent selectRecipient = new Intent(NewAdminCreateLetterActivity.this, LetterRecipientActivity.class);
                    selectRecipient.putExtra("a_id", userList.get(0).getA_id());
                    startActivityForResult(selectRecipient, CODE_SELECT_RECIPIENT);

                    /*myToast.makeToast(NewAdminCreateLetterActivity.this, "1", "NORMAL");
                    TextView tv = (TextView) widget;
                    Spanned s = (Spanned) tv.getText();
                    int start = s.getSpanStart(this);
                    int end = s.getSpanEnd(this);
                    CharSequence charSequence = s.subSequence(start, end);
                    myToast.makeToast(NewAdminCreateLetterActivity.this, "RECIPIENT", "NORMAL");*/
                }

                @Override
                public void updateDrawState(@NonNull TextPaint ds) {
                    super.updateDrawState(ds);
//                    ds.setColor(Color.BLUE);
//                    ds.setUnderlineText(false);
                }
            };

            ClickableSpan clickableSpan2 = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    /*myToast.makeToast(NewAdminCreateLetterActivity.this, "2", "NORMAL");
                    TextView tv = (TextView) widget;
                    Spanned s = (Spanned) tv.getText();
                    int start = s.getSpanStart(this);
                    int end = s.getSpanEnd(this);
                    CharSequence charSequence = s.subSequence(start, end);
                    myToast.makeToast(NewAdminCreateLetterActivity.this, "OPENING TAG", "NORMAL");*/
                }
            };

            ClickableSpan clickableSpan3 = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    myToast.makeToast(NewAdminCreateLetterActivity.this, "3", "NORMAL");
                    TextView tv = (TextView) widget;
                    Spanned s = (Spanned) tv.getText();
                    int start = s.getSpanStart(this);
                    int end = s.getSpanEnd(this);
                    CharSequence charSequence = s.subSequence(start, end);
                    myToast.makeToast(NewAdminCreateLetterActivity.this, "REPRESENTATIVE", "NORMAL");
                }
            };

            ClickableSpan clickableSpan4 = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    myToast.makeToast(NewAdminCreateLetterActivity.this, "4", "NORMAL");
                    TextView tv = (TextView) widget;
                    Spanned s = (Spanned) tv.getText();
                    int start = s.getSpanStart(this);
                    int end = s.getSpanEnd(this);
                    CharSequence charSequence = s.subSequence(start, end);
                    myToast.makeToast(NewAdminCreateLetterActivity.this, "CLOSING TAG", "NORMAL");
                }
            };

            int clc_id = 1;
            int clc_start = 0;
            int clc_end = 0;
            String clc_name = "";
            boolean isBracket = false;
            for(int i=0; i<ssbLetterContent.length(); i++) {
                Character c = ssbLetterContent.charAt(i);

                if(c.equals('[')) {
                    isBracket = true;
                    clc_start = i;
                }

                if(c.equals(']')) {
                    isBracket = false;
                    clc_end = i;

                    clc_name += c;
//                    myToast.makeToast(this, "" +clc_id, "NORMAL");
//                    myToast.makeToast(this, clc_name, "NORMAL");
//                    myToast.makeToast(this, "" +clc_start, "NORMAL");
//                    myToast.makeToast(this, "" +clc_end, "NORMAL");
                    clcList.add(new ClickLetterContent(clc_id, clc_name, clc_start, clc_end, t_id));

                    if(clc_name.equals("[DATE]")) {
                        ssbLetterContent.replace(
                                clc_start,
                                clc_end + 1,
                                dateToday
                        );
                    } else if(clc_name.equals("[RECIPIENT]")) {
                        if(draftList.get(0).getRec_id() != 0) {
                            ArrayList<User> recipientList = db.getUser(draftList.get(0).getRec_id());
                            String recipientName = recipientList.get(0).getA_fname() +" "+ recipientList.get(0).getA_lname();
                            ssbLetterContent.replace(
                                    clc_start,
                                    clc_end + 1,
                                    recipientName
                            );
                            int end = clc_start + recipientName.length();
                            ssbLetterContent.setSpan(clickRecipient, clc_start, end + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        } else {
                            ssbLetterContent.setSpan(clickRecipient, clc_start, clc_end + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    } else if(clc_name.equals("[OPENING TAG]")) {
                        ssbLetterContent.setSpan(clickableSpan2, clc_start, clc_end+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    } else if(clc_name.equals("[YOUR NAME]")) {
                        ssbLetterContent.replace(
                                clc_start,
                                clc_end + 1,
                                fullName
                        );
                    } else if(clc_name.equals("[REPRESENTATIVE]")) {
                        ssbLetterContent.setSpan(clickableSpan3, clc_start, clc_end+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    } else if(clc_name.equals("[CLOSING TAG]")) {
                        ssbLetterContent.setSpan(clickableSpan4, clc_start, clc_end+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    } else if(TextUtils.equals(clc_name, "[SIGNATURE]")) {
                        ssbLetterContent.replace(
                                clc_start,
                                clc_end + 1,
                                "[SIGN]         "
                        );

                        try {
                            signatureUri = userList.get(0).getA_image();
                            ssbLetterContent.setSpan(
                                    new ImageSpan(this, signatureUri),
                                    clc_start,
                                    clc_end+1,
                                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

//                        try {
//                            signatureUri = Uri.parse(userList.get(0).getA_signature());
//
//                            if(signatureUri == null) {
//                                signatureUri = userList.get(0).getA_image();
//                            } else {
//                                ssbLetterContent.setSpan(
//                                        new ImageSpan(this, signatureUri),
//                                        clc_start,
//                                        clc_end+1,
//                                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
                    }

                    /*if(clc_id == 1) {
                        ssbLetterContent.setSpan(clickableSpan1, clc_start, clc_end+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    } else if(clc_id == 2) {
                        ssbLetterContent.setSpan(clickableSpan2, clc_start, clc_end+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    } else if(clc_id == 3) {
                        ssbLetterContent.setSpan(clickableSpan3, clc_start, clc_end+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    } else if(clc_id == 4) {
                        ssbLetterContent.setSpan(clickableSpan4, clc_start, clc_end+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    } else if(clc_id == 5) {
                        ssbLetterContent.setSpan(clickableSpan5, clc_start, clc_end+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    } else if(clc_id == 6) {
                        ssbLetterContent.setSpan(clickableSpan6, clc_start, clc_end+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    } else if(clc_id == 7) {
                        ssbLetterContent.setSpan(clickableSpan7, clc_start, clc_end+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    } else if(clc_id == 8) {
                        ssbLetterContent.setSpan(clickableSpan8, clc_start, clc_end+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }*/

                    clc_id++;
                    clc_name = "";
                    clc_start = 0;
                    clc_end = 0;
                }

                if(isBracket) {
                    clc_name += c;
                }
            }

            if(clcList.isEmpty()) {
                myToast.makeToast(this, "Something went wrong", "NORMAL");
                this.finish();
            } else {
                clickableSpan = new ClickableSpan[4];

                for(int ind=0; ind<4; ind++) {
                    ssbLetterContent.setSpan(clickableSpan[ind],
                            clcList.get(ind).getClc_start(),
                            clcList.get(ind).getClc_end(),
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    );
                }
            }

            txtLetterContent.setText(ssbLetterContent);
            txtLetterContent.setMovementMethod(LinkMovementMethod.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
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
                db.updateNewDraftContent(t_id, txtLetterContent.getText().toString());
                Intent viewProgress = new Intent(this, NewAdminViewProgressActivity.class);
                viewProgress.putExtra("t_id", t_id);
                startActivity(viewProgress);

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        this.finish();
        this.startActivity(getIntent());
    }
}