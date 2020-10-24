package pardillo.john.jv.authorized.main.letter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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

import java.io.FileNotFoundException;
import java.io.InputStream;
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
import pardillo.john.jv.authorized.database.pojo.Rep;
import pardillo.john.jv.authorized.database.pojo.User;
import pardillo.john.jv.authorized.main.letter.recipient.LetterRecipientActivity;
import pardillo.john.jv.authorized.main.letter.representative.LetterRepresentativeActivity;
import pardillo.john.jv.authorized.style.MyToast;

public class NewUserCreateLetterActivity extends AppCompatActivity {

    private static final int CODE_SELECT_OT = 2;
    private static final int CODE_SELECT_CT = 3;
    private static final int CODE_SELECT_RECIPIENT = 20;
    private static final int CODE_SELECT_REPRESENTATIVE = 15;

    private TextView txtLetterContent;

    private AppDatabase db;
    private MyToast myToast;

//    private ArrayList<ClickLetterContent> clcList = new ArrayList<>();
    private ArrayList<NewTemplate> templateList = new ArrayList<>();
    private ArrayList<User> userList = new ArrayList<>();
    private ArrayList<NewDraft> draftList = new ArrayList<>();

    private int t_id;
    private String fullName = "", dateToday = "";
//    private ClickableSpan clickableSpan[];

    private Uri signatureUri;
    private ImageSpan imageSpan = null;

    private Drawable signatureDrawable;

    private String strLetterContent = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_create_letter);

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

            if(draftList.isEmpty()) {
                strLetterContent = templateList.get(0).getT_letterContent();
                db.addNewDraft(dateToday, strLetterContent, "", "", t_id, userList.get(0).getA_id(), 0, 0);
                draftList = db.getNewDraft(userList.get(0).getA_id());
            } else {
                strLetterContent = draftList.get(0).getD_letterContent();
            }

            SpannableStringBuilder ssbLetterContent = new SpannableStringBuilder(strLetterContent);

            ClickableSpan clickRecipient = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    Intent selectRecipient = new Intent(NewUserCreateLetterActivity.this, LetterRecipientActivity.class);
                    selectRecipient.putExtra("a_id", userList.get(0).getA_id());
                    startActivityForResult(selectRecipient, CODE_SELECT_RECIPIENT);
                }
            };

            ClickableSpan clickOpeningTag = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
//                    myToast.makeToast(NewUserCreateLetterActivity.this, "0" +txtLetterContent.getText().toString(), "NORMAL");
                    Intent selectOT = new Intent(NewUserCreateLetterActivity.this, NewUserOTActivity.class);
                    selectOT.putExtra("a_id", userList.get(0).getA_id());
                    startActivityForResult(selectOT, CODE_SELECT_OT);
                }
            };

            ClickableSpan clickRepresentative = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    Intent selectRep = new Intent(NewUserCreateLetterActivity.this, LetterRepresentativeActivity.class);
                    selectRep.putExtra("a_id", userList.get(0).getA_id());
                    startActivityForResult(selectRep, CODE_SELECT_REPRESENTATIVE);
                }
            };

            ClickableSpan clickClosingTag = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    Intent selectCT = new Intent(NewUserCreateLetterActivity.this, NewUserCTActivity.class);
                    selectCT.putExtra("a_id", userList.get(0).getA_id());
                    startActivityForResult(selectCT, CODE_SELECT_CT);
                }
            };

//            int clc_id = 1;
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

//                    clcList.add(new ClickLetterContent(clc_id, clc_name, clc_start, clc_end, t_id));
//                    myToast.makeToast(this, clc_name, "NORMAL");

                    if(clc_name.equals("[DATE]")) {
                        ssbLetterContent.replace(
                                clc_start,
                                clc_end + 1,
                                dateToday
                        );

                        i = clc_start;
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

                        i = clc_start;
                    } else if(TextUtils.equals(clc_name, "[SALUTATION]")) {
//                        ssbLetterContent.setSpan(clickOpeningTag, clc_start, clc_end + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                        if(TextUtils.equals(draftList.get(0).getD_ot(), "")) {
                            ssbLetterContent.setSpan(clickOpeningTag, clc_start, clc_end + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        } else {
                            String d_ot = draftList.get(0).getD_ot();
                            ssbLetterContent.replace(
                                    clc_start,
                                    clc_end + 1,
                                    d_ot
                            );
                            int myend = clc_start + d_ot.length();
                            ssbLetterContent.setSpan(clickOpeningTag, clc_start, myend, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                            i = clc_start;
//                            myToast.makeToast(this, d_ot +" "+ clc_start +" "+ clc_end, "NORMAL");
//                            myToast.makeToast(this, d_ot +" "+ clc_start +" "+ myend, "NORMAL");
//                            ssbLetterContent.setSpan(clickOpeningTag, clc_start, myend, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    } else if(clc_name.equals("[YOUR NAME]")) {
                        userList = db.getLogInUser();
                        String authName = userList.get(0).getA_fname() +" "+ userList.get(0).getA_lname();
//                        myToast.makeToast(this, authName + clc_start, "NORMAL");
                        ssbLetterContent.replace(
                                clc_start,
                                clc_end + 1,
                                authName
                        );

                        i = clc_start;
                    } else if(clc_name.equals("[REPRESENTATIVE]")) {
                        if(draftList.get(0).getRep_id() != 0) {
                            ArrayList<Rep> repList = db.getRep(userList.get(0).getA_id(), draftList.get(0).getRep_id());
                            String repName = repList.get(0).getRep_fname() +" "+ repList.get(0).getRep_lname();
                            ssbLetterContent.replace(
                                    clc_start,
                                    clc_end + 1,
                                    repName
                            );
                            int end = clc_start + repName.length();
                            ssbLetterContent.setSpan(clickRepresentative, clc_start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                            i = clc_start;
                        } else {
                            ssbLetterContent.setSpan(clickRepresentative, clc_start, clc_end + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    } else if(clc_name.equals("[CLOSING]")) {
//                        ssbLetterContent.setSpan(clickClosingTag, clc_start, clc_end + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                        String d_ct = draftList.get(0).getD_ct();

                        if(d_ct.equals("")) {
                            ssbLetterContent.setSpan(clickClosingTag, clc_start, clc_end + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        } else {
                            ssbLetterContent.replace(
                                    clc_start,
                                    clc_end + 1,
                                    d_ct
                            );
                            int end = clc_start + d_ct.length();
                            ssbLetterContent.setSpan(clickClosingTag, clc_start, end + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                            i = clc_start;
                        }
                    } else if(TextUtils.equals(clc_name, "[SIGNATURE]")) {
                        userList = db.getLogInUser();
                        ssbLetterContent.replace(
                                clc_start,
                                clc_end + 1,
                                "[SIGN]         "
                        );

                        InputStream inputStream = null;
                        try {
                            try {
                                signatureUri = Uri.parse(userList.get(0).getA_signature());
                                inputStream = getContentResolver().openInputStream(signatureUri);
                                signatureDrawable = Drawable.createFromStream(inputStream, signatureUri.toString());
                                signatureDrawable.setBounds(0, 0, 150, 150);

                                imageSpan = new ImageSpan(signatureDrawable);
                                ssbLetterContent.setSpan(
                                        imageSpan,
                                        clc_start,
                                        clc_end + 1,
                                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                                i = clc_start;
                            } catch (FileNotFoundException e) {
                                myToast.makeToast(this, "Something went wrong when displaying digital signature", "ERROR");
                                e.printStackTrace();
                            } finally {
                                try {
                                    if(inputStream != null) {
                                        inputStream.close();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

//                    clc_id++;
                    clc_name = "";
                    clc_start = 0;
                }

                if(isBracket) {
                    clc_name += c;
                }
            }

            /*if(clcList.isEmpty()) {
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
            }*/

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
                ArrayList<NewDraft> newDraftList = db.getNewDraft(userList.get(0).getA_id());
                int rec = newDraftList.get(0).getRec_id();
                int rep = newDraftList.get(0).getRep_id();

                if(rec == 0) {
                    myToast.makeToast(this, "Please select recipient", "NORMAL");
                } else if(rep == 0) {
                    myToast.makeToast(this, "Please select representative", "NORMAL");
                } else {
                    db.updateNewDraftContent(userList.get(0).getA_id(), txtLetterContent.getText().toString());
                    Intent viewProgress = new Intent(this, NewUserViewProgressActivity.class);
                    viewProgress.putExtra("t_id", t_id);
                    startActivity(viewProgress);
                }

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        myToast.makeToast(this, txtLetterContent.getText().toString(), "NORMAL");
        this.finish();
        this.startActivity(getIntent());
    }
}