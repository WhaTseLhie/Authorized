package pardillo.john.jv.authorized.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import pardillo.john.jv.authorized.database.pojo.AcTemplate;
import pardillo.john.jv.authorized.database.pojo.LetterContent;
import pardillo.john.jv.authorized.database.pojo.LetterDetails;
import pardillo.john.jv.authorized.database.pojo.LetterReceiveContent;
import pardillo.john.jv.authorized.database.pojo.LetterReceiveDetails;
import pardillo.john.jv.authorized.database.pojo.LetterSentContent;
import pardillo.john.jv.authorized.database.pojo.LetterSentDetails;
import pardillo.john.jv.authorized.database.pojo.MainContent;
import pardillo.john.jv.authorized.database.pojo.NewDraft;
import pardillo.john.jv.authorized.database.pojo.NewTemplate;
import pardillo.john.jv.authorized.database.pojo.Price;
import pardillo.john.jv.authorized.database.pojo.TemplateContent;
import pardillo.john.jv.authorized.database.pojo.Draft;
import pardillo.john.jv.authorized.database.pojo.Feedback;
import pardillo.john.jv.authorized.database.pojo.Notification;
import pardillo.john.jv.authorized.database.pojo.PastRecipient;
import pardillo.john.jv.authorized.database.pojo.Payment;
import pardillo.john.jv.authorized.database.pojo.Rating;
import pardillo.john.jv.authorized.database.pojo.Receive;
import pardillo.john.jv.authorized.database.pojo.Rep;
import pardillo.john.jv.authorized.database.pojo.RepId;
import pardillo.john.jv.authorized.database.pojo.Sent;
import pardillo.john.jv.authorized.database.pojo.TemplateDetails;
import pardillo.john.jv.authorized.database.pojo.User;

public class AppDatabase extends SQLiteOpenHelper {

    static String DATABASE = "db_app";
    static String TBL_ADMIN = "tbl_admin";
    static String TBL_PRICE = "tbl_price";
    static String TBL_USER = "tbl_user";
    static String TBL_LOGIN_USER = "tbl_login_user";
    static String TBL_REPRESENTATIVES = "tbl_rep";
    static String TBL_REPID = "tbl_repid";
    static String TBL_DRAFT = "tbl_draft";
    static String TBL_SENT = "tbl_sent";
    static String TBL_RECEIVE = "tbl_receive";
    static String TBL_RATE = "tbl_rate";
    static String TBL_USER_RATING = "tbl_user_rating";
    static String TBL_FEEDBACK = "tbl_feedback";
    static String TBL_PAYMENT = "tbl_payment";
    static String TBL_NOTIFICATION = "tbl_notification";
    static String TBL_PAST_RECIPIENT = "tbl_past_recipient";
    static String TBL_NEW_TEMPLATE = "tbl_new_template";
    static String TBL_NEW_DRAFT = "tbl_new_draft";
    static String TBL_TEMPLATE_DETAILS = "tbl_template_details";
    static String TBL_TEMPLATE_CONTENT = "tbl_template_content";
    static String TBL_LETTER_DETAILS = "tbl_letter_details";
    static String TBL_LETTER_CONTENT = "tbl_letter_content";
    static String TBL_LETTER_SENT_DETAILS = "tbl_letter_sent_details";
    static String TBL_LETTER_SENT_CONTENT = "tbl_letter_sent_content";
    static String TBL_LETTER_RECEIVE_DETAILS = "tbl_letter_receive_details";
    static String TBL_LETTER_RECEIVE_CONTENT = "tbl_letter_receive_content";
    static String TBL_MAIN_CONTENT = "tbl_main_content";
    static String TBL_OPENING_TAG = "tbl_opening_tag";
    static String TBL_CLOSING_TAG = "tbl_closing_tag";
    static String TBL_AC_TEMPLATE = "tbl_ac_template";

    public AppDatabase(Context context) {
        super(context, DATABASE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlPrice = "CREATE TABLE " +TBL_PRICE+
                "(p_id integer primary key autoincrement," +
                "p_price integer)";
        db.execSQL(sqlPrice);
        String sqlAdminUser = "CREATE TABLE " +TBL_ADMIN+
                "(a_id integer primary key autoincrement," +
                "a_image varchar(100)," +
                "a_fname varchar(100)," +
                "a_lname varchar(100)," +
                "a_contact varchar(20)," +
                "a_gender varchar(10)," +
                "a_username varchar(100)," +
                "a_password varchar(100)," +
                "a_signature varchar(200)," +
                "a_status varchar(20))";
        db.execSQL(sqlAdminUser);
        String sqlLogInUser = "CREATE TABLE " +TBL_LOGIN_USER+
                "(a_id integer primary key," +
                "a_image varchar(100)," +
                "a_fname varchar(100)," +
                "a_lname varchar(100)," +
                "a_contact varchar(20)," +
                "a_gender varchar(10)," +
                "a_username varchar(100)," +
                "a_password varchar(100)," +
                "a_signature varchar(200)," +
                "a_status varchar(20))";
        db.execSQL(sqlLogInUser);
        //*******************************************************************************
        String sqlUser = "CREATE TABLE " +TBL_USER+
                "(a_id integer primary key autoincrement," +
                "a_image varchar(100)," +
                "a_fname varchar(100)," +
                "a_lname varchar(100)," +
                "a_contact varchar(20)," +
                "a_gender varchar(10)," +
                "a_username varchar(200)," +
                "a_password varchar(100)," +
                "a_signature varchar(200)," +
                "a_status varchar(20))";
        db.execSQL(sqlUser);
        //*******************************************************************************
        String sqlRepresentatives = "CREATE TABLE " +TBL_REPRESENTATIVES+
                "(rep_id integer primary key autoincrement," +
                "rep_image varchar(100)," +
                "rep_fname varchar(100)," +
                "rep_lname varchar(100)," +
                "rep_contact varchar(20)," +
                "rep_gender varchar(10)," +
                "rep_identification varchar(200)," +
                "a_id integer)";
        db.execSQL(sqlRepresentatives);
        //*******************************************************************************
        String sqlRepId = "CREATE TABLE " +TBL_REPID+
                "(repid_id integer primary key autoincrement," +
                "a_id integer," +
                "rep_id integer," +
                "repid_image varchar(200))";
        db.execSQL(sqlRepId);
        //*******************************************************************************
        String sqlDraft = "CREATE TABLE " +TBL_DRAFT+
                "(d_id integer primary key autoincrement," +
                "a_id integer," +
                "t_id integer)";
        db.execSQL(sqlDraft);
        //*******************************************************************************
        String sqlNewDraft = "CREATE TABLE " +TBL_NEW_DRAFT+
                "(d_id integer primary key autoincrement," +
                "d_date varchar(100)," +
                "d_letterContent varchar(10000)," +
                "d_ot varchar(1000)," +
                "d_ct varchar(1000)," +
                "t_id integer," +
                "a_id integer," +
                "rep_id integer," +
                "rec_id integer)";
        db.execSQL(sqlNewDraft);
        //*******************************************************************************
        String sqlSent = "CREATE TABLE " +TBL_SENT+
                "(s_id integer primary key autoincrement," +
                "s_otp integer," +
                "sentDate varchar(100)," +
                "s_letter varchar(10000)," +
                "t_id integer," +
                "a_id integer," +
                "rep_id integer," +
                "rec_id integer," +
                "rate_id integer," +
                "r_id integer," +
                "s_status)";
        db.execSQL(sqlSent);
        //*******************************************************************************
        String sqlReceive = "CREATE TABLE " +TBL_RECEIVE+
                "(r_id integer primary key autoincrement," +
                "r_otp integer," +
                "receivedDate varchar(100)," +
                "r_letter varchar(10000)," +
                "t_id integer," +
                "a_id integer," +
                "rep_id integer," +
                "rec_id integer," +
                "rate_id integer," +
                "s_id integer," +
                "r_status)";
        db.execSQL(sqlReceive);
        //*******************************************************************************
        String sqlRate = "CREATE TABLE " +TBL_RATE+
                "(rate_id integer primary key autoincrement," +
                "a_id integer," +
                "rec_id integer," +
                "rep_id integer," +
                "s_id integer," +
                "r_id integer," +
                "rate_rating float," +
                "rate_date varchar(30)," +
                "rate_comment varchar(200))";
        db.execSQL(sqlRate);
        //*******************************************************************************
        String sqlFeedback = "CREATE TABLE " +TBL_FEEDBACK+
                "(f_id integer primary key autoincrement," +
                "f_title varchar(100)," +
                "f_date varchar(30)," +
                "f_sdate varchar(30)," +
                "f_edate varchar(30)," +
                "f_message varchar(500)," +
                "a_id integer," +
                "rec_id integer," +
                "s_id integer," +
                "r_id integer)";
        db.execSQL(sqlFeedback);
        //*******************************************************************************
        String sqlPayment = "CREATE TABLE " +TBL_PAYMENT+
                "(ir_id integer primary key autoincrement," +
                "ir_date varchar(100)," +
                "ir_method varchar(100)," +
                "ir_payment varchar(30)," +
                "a_id integer," +
                "rec_id integer," +
                "s_id integer," +
                "r_id integer)";
        db.execSQL(sqlPayment);
        //*******************************************************************************
        String sqlNotification = "CREATE TABLE " +TBL_NOTIFICATION+
                "(not_id integer primary key autoincrement," +
                "not_type varchar(100)," +
                "not_title varchar(100)," +
                "not_date varchar(30)," +
                "not_message varchar(200)," +
                "a_id integer," +
                "rec_id integer," +
                "s_id integer," +
                "r_id integer)";
        db.execSQL(sqlNotification);
        //*******************************************************************************
        String sqlUserRating = "CREATE TABLE " +TBL_USER_RATING+
                "(ur_id integer primary key autoincrement," +
                "a_id integer," +
                "r_total float)";
        db.execSQL(sqlUserRating);
        //*******************************************************************************
        String sqlPastRecipient = "CREATE TABLE " +TBL_PAST_RECIPIENT+
                "(pr_id integer primary key autoincrement," +
                "a_id integer," +
                "r_id integer)";
        db.execSQL(sqlPastRecipient);
        //*******************************************************************************
        String sqlTemplateDetails = "CREATE TABLE " + TBL_TEMPLATE_DETAILS +
                "(t_id integer primary key autoincrement," +
                "t_image varchar(100)," +
                "t_title varchar(100)," +
                "t_date varchar(100)," +
                "t_type varchar(100)," +
                "a_id integer," +
                "t_status varchar(100))";
        db.execSQL(sqlTemplateDetails);
        //*******************************************************************************
        String sqlTemplateContent = "CREATE TABLE " + TBL_TEMPLATE_CONTENT+
                "(tc_id integer primary key autoincrement," +
                "c_name varchar(100)," +
                "c_type varchar(100)," +
                "c_leftMargin integer," +
                "c_topMargin integer," +
                "c_rightMargin integer," +
                "c_bottomMargin integer," +
                "c_id integer," +
                "t_id integer)";
        db.execSQL(sqlTemplateContent);
        //*******************************************************************************
        String sqlLetterDetails = "CREATE TABLE " + TBL_LETTER_DETAILS+
                "(td_id integer primary key autoincrement," +
                "t_image varchar(100)," +
                "t_title varchar(100)," +
                "t_date varchar(100)," +
                "t_type varchar(100)," +
                "t_id integer," +
                "a_id integer)";
        db.execSQL(sqlLetterDetails);
        //*******************************************************************************
        String sqlLetterContent = "CREATE TABLE " + TBL_LETTER_CONTENT+
                "(lc_id integer primary key autoincrement," +
                "c_name varchar(100)," +
                "c_type varchar(100)," +
                "c_leftMargin integer," +
                "c_topMargin integer," +
                "c_rightMargin integer," +
                "c_bottomMargin integer," +
                "c_id integer," +
                "t_id integer," +
                "a_id integer)";
        db.execSQL(sqlLetterContent);
        //*******************************************************************************
        String sqlLetterSentDetails = "CREATE TABLE " + TBL_LETTER_SENT_DETAILS+
                "(td_id integer primary key autoincrement," +
                "t_image varchar(100)," +
                "t_title varchar(100)," +
                "t_date varchar(100)," +
                "t_type varchar(100)," +
                "t_id integer," +
                "a_id integer," +
                "s_id integer," +
                "r_id integer)";
        db.execSQL(sqlLetterSentDetails);
        //*******************************************************************************
        String sqlLetterSentContent = "CREATE TABLE " + TBL_LETTER_SENT_CONTENT+
                "(lc_id integer primary key autoincrement," +
                "c_name varchar(100)," +
                "c_type varchar(100)," +
                "c_leftMargin integer," +
                "c_topMargin integer," +
                "c_rightMargin integer," +
                "c_bottomMargin integer," +
                "c_id integer," +
                "t_id integer," +
                "a_id integer," +
                "s_id integer," +
                "r_id integer)";
        db.execSQL(sqlLetterSentContent);
        //*******************************************************************************
        String sqlLetterReceiveDetails = "CREATE TABLE " + TBL_LETTER_RECEIVE_DETAILS+
                "(td_id integer primary key autoincrement," +
                "t_image varchar(100)," +
                "t_title varchar(100)," +
                "t_date varchar(100)," +
                "t_type varchar(100)," +
                "t_id integer," +
                "a_id integer," +
                "s_id integer," +
                "r_id integer)";
        db.execSQL(sqlLetterReceiveDetails);
        //*******************************************************************************
        String sqlLetterReceiveContent = "CREATE TABLE " + TBL_LETTER_RECEIVE_CONTENT+
                "(lc_id integer primary key autoincrement," +
                "c_name varchar(100)," +
                "c_type varchar(100)," +
                "c_leftMargin integer," +
                "c_topMargin integer," +
                "c_rightMargin integer," +
                "c_bottomMargin integer," +
                "c_id integer," +
                "t_id integer," +
                "a_id integer," +
                "s_id integer," +
                "r_id integer)";
        db.execSQL(sqlLetterReceiveContent);
        //*******************************************************************************
        String sqlMainContent = "CREATE TABLE " + TBL_MAIN_CONTENT+
                "(mc_id integer primary key autoincrement," +
                "mc_name varchar(100)," +
                "mc_type varchar(100))";
        db.execSQL(sqlMainContent);
        //*******************************************************************************
        String sqlNewTemplate = "CREATE TABLE " + TBL_NEW_TEMPLATE+
                "(t_id integer primary key autoincrement," +
                "t_image varchar(100)," +
                "t_title varchar(100)," +
                "t_date varchar(100)," +
                "t_letterContent varchar(10000)," +
                "t_type varchar(100)," +
                "a_id integer," +
                "t_status varchar(100))";
        db.execSQL(sqlNewTemplate);
        String sqlOT = "CREATE TABLE " +TBL_OPENING_TAG+
                "(ot_id integer primary key autoincrement," +
                "ot_name varchar(1000))";
        db.execSQL(sqlOT);
        String sqlCT = "CREATE TABLE " +TBL_CLOSING_TAG+
                "(ct_id integer primary key autoincrement," +
                "ct_name varchar(1000))";
        db.execSQL(sqlCT);
        String sqlAcTemplate = "CREATE TABLE " +TBL_AC_TEMPLATE+
                "(ac_id integer primary key autoincrement," +
                "ac_letterContent varchar(1000)," +
                "a_id integer)";
        db.execSQL(sqlAcTemplate);
    }

    //***********************************************************************************************************
    // CLOSING TAG
    //***********************************************************************************************************
    public void addClosingTag(String ct_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("ct_name", ct_name);
        db.insert(TBL_CLOSING_TAG, null, cv);

        db.close();
    }

    public boolean findClosingTag(String ctname) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_CLOSING_TAG, null, null, null, null, null, "ct_id");
        boolean isFound = false;

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            String ct_name = c.getString(c.getColumnIndex("ct_name"));

            if(TextUtils.equals(ctname, ct_name.toLowerCase())) {
                isFound = true;
                break;
            }
        }

        c.close();
        db.close();
        return isFound;
    }

    public ArrayList<String> getAllClosingTag() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_CLOSING_TAG, null, null, null, null, null, "ct_id");
        ArrayList<String> list = new ArrayList<>();
        list.clear();

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            String ct_name = c.getString(c.getColumnIndex("ct_name"));

            list.add(ct_name);
        }

        c.close();
        db.close();
        return list;
    }

    //***********************************************************************************************************
    // AC TEMPLATE
    //***********************************************************************************************************
    public void addAcTemplate(String ac_letterContent, int a_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("ac_letterContent", ac_letterContent);
        cv.put("a_id", a_id);
        db.insert(TBL_AC_TEMPLATE, null, cv);

        db.close();
    }

    public void updateAcTemplate(String ac_letterContent, int a_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("ac_letterContent", ac_letterContent);
        db.update(TBL_AC_TEMPLATE, cv, "a_id=" +a_id, null);

        db.close();
    }

    public ArrayList<AcTemplate> getAcTemplate(int aid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_AC_TEMPLATE, null, null, null, null, null, "a_id");
        ArrayList<AcTemplate> list = new ArrayList<>();
        list.clear();

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int a_id = c.getInt(c.getColumnIndex("a_id"));

            if(a_id == aid) {
                int ac_id = c.getInt(c.getColumnIndex("ac_id"));
                String ac_letterContent = c.getString(c.getColumnIndex("ac_letterContent"));

                list.add(new AcTemplate(ac_id, ac_letterContent, a_id));
                break;
            }
        }

        c.close();
        db.close();
        return list;
    }

    //***********************************************************************************************************
    // OPENING TAG
    //***********************************************************************************************************
    public void addOpeningTag(String ot_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("ot_name", ot_name);
        db.insert(TBL_OPENING_TAG, null, cv);

        db.close();
    }

    public boolean findOpeningTag(String otname) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_OPENING_TAG, null, null, null, null, null, "ot_id");
        boolean isFound = false;

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            String ot_name = c.getString(c.getColumnIndex("ot_name"));

            if(TextUtils.equals(otname, ot_name.toLowerCase())) {
                isFound = true;
                break;
            }
        }

        c.close();
        db.close();
        return isFound;
    }

    public ArrayList<String> getAllOpeningTag() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_OPENING_TAG, null, null, null, null, null, "ot_id");
        ArrayList<String> list = new ArrayList<>();
        list.clear();

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            String ot_name = c.getString(c.getColumnIndex("ot_name"));

            list.add(ot_name);
        }

        c.close();
        db.close();
        return list;
    }

    //***********************************************************************************************************
    // PRICE
    //***********************************************************************************************************
    public void addPrice(int p_price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("p_price", p_price);
        db.insert(TBL_PRICE, null, cv);

        db.close();
    }

    public void updatePrice(int p_id, int p_price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("p_price", p_price);
        db.update(TBL_PRICE, cv, "p_id=" +p_id, null);

        db.close();
    }

    public ArrayList<Price> getPrice() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_PRICE, null, null, null, null, null, "p_id");
        ArrayList<Price> list = new ArrayList<>();
        list.clear();

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int p_id = c.getInt(c.getColumnIndex("p_id"));
            int p_price = c.getInt(c.getColumnIndex("p_price"));

            list.add(new Price(p_id, p_price));
        }

        c.close();
        db.close();
        return list;
    }

    //***********************************************************************************************************
    // PAST RECIPIENT
    //***********************************************************************************************************
    public long addPastRecipient(int a_id, int rec_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        long res = 0;

        if(!findRID(rec_id)) {
            cv.put("a_id", a_id);
            cv.put("r_id", rec_id);
            res = db.insert(TBL_PAST_RECIPIENT, null, cv);
        }

        db.close();
        return res;
    }

    public ArrayList<User> getAllPastRecipient(int aid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_PAST_RECIPIENT, null, null, null, null ,null, "a_id");
        ArrayList<PastRecipient> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int a_id = c.getInt(c.getColumnIndex("a_id"));

            if(a_id == aid) {
                int pr_id = c.getInt(c.getColumnIndex("pr_id"));
                int r_id = c.getInt(c.getColumnIndex("r_id"));

                list.add(new PastRecipient(pr_id, a_id, r_id));
            }
        }

        ArrayList<User> userList = new ArrayList<>();
        if(!list.isEmpty()) {
            for(int i=0; i<list.size(); i++) {
                userList.add(getPreviousRecipient(list.get(i).getR_id()));
            }
        }

        db.close();
        c.close();
        return userList;
    }

    public boolean findRID(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_PAST_RECIPIENT, null, null, null, null ,null, "r_id");
        boolean bool = false;

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int r_id = c.getInt(c.getColumnIndex("r_id"));

            if(r_id == id) {
                bool = true;
            }
        }

//        db.close();
        c.close();
        return bool;
    }

    //***********************************************************************************************************
    // ADMIN
    //***********************************************************************************************************
    public int addAdmin(String a_image, String a_fname, String a_lname, String a_contact, String a_gender, String a_username, String a_password, String a_signature, String a_status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        int res = 0;

        cv.put("a_image", a_image);
        cv.put("a_fname", a_fname);
        cv.put("a_lname", a_lname);
        cv.put("a_contact", a_contact);
        cv.put("a_gender", a_gender);
        cv.put("a_username", a_username);
        cv.put("a_password", a_password);
        cv.put("a_signature", a_signature);
        cv.put("a_status", a_status);
        try {
            res = Integer.parseInt(db.insert(TBL_ADMIN, null, cv) +"");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        db.close();
        return res;
    }

    public ArrayList<User> getAdminUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_ADMIN, null, null, null, null, null, "a_id");
        ArrayList<User> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int a_id = c.getInt(c.getColumnIndex("a_id"));
            String a_username = c.getString(c.getColumnIndex("a_username"));
            String a_image = c.getString(c.getColumnIndex("a_image"));
            String a_fname = c.getString(c.getColumnIndex("a_fname"));
            String a_lname = c.getString(c.getColumnIndex("a_lname"));
            String a_contact = c.getString(c.getColumnIndex("a_contact"));
            String a_gender = c.getString(c.getColumnIndex("a_gender"));
            String a_password = c.getString(c.getColumnIndex("a_password"));
            String a_signature = c.getString(c.getColumnIndex("a_signature"));
            String a_status = c.getString(c.getColumnIndex("a_status"));

            list.add(new User(a_id, Uri.parse(a_image), a_fname, a_lname, a_contact, a_gender, a_username, a_password, a_signature, a_status));
        }

        db.close();
        c.close();
        return list;
    }

    //***********************************************************************************************************
    // USER
    //***********************************************************************************************************
    public int addUser(String a_image, String a_fname, String a_lname, String a_contact, String a_gender, String a_username, String a_password, String a_signature, String a_status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        int res = 0;

        cv.put("a_image", a_image);
        cv.put("a_fname", a_fname);
        cv.put("a_lname", a_lname);
        cv.put("a_contact", a_contact);
        cv.put("a_gender", a_gender);
        cv.put("a_username", a_username);
        cv.put("a_password", a_password);
        cv.put("a_signature", a_signature);
        cv.put("a_status", a_status);
        try {
            res = Integer.parseInt(db.insert(TBL_USER, null, cv) +"");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        db.close();
        return res;
    }

    public ArrayList<User> getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_USER, null, null, null, null, null, "a_id");
        ArrayList<User> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int a_id = c.getInt(c.getColumnIndex("a_id"));

            if(a_id == id) {
                String a_image = c.getString(c.getColumnIndex("a_image"));
                String a_fname = c.getString(c.getColumnIndex("a_fname"));
                String a_lname = c.getString(c.getColumnIndex("a_lname"));
                String a_contact = c.getString(c.getColumnIndex("a_contact"));
                String a_gender = c.getString(c.getColumnIndex("a_gender"));
                String a_username = c.getString(c.getColumnIndex("a_username"));
                String a_password = c.getString(c.getColumnIndex("a_password"));
                String a_signature = c.getString(c.getColumnIndex("a_signature"));
                String a_status = c.getString(c.getColumnIndex("a_status"));

                list.add(new User(a_id, Uri.parse(a_image), a_fname, a_lname, a_contact, a_gender, a_username, a_password, a_signature, a_status));
                break;
            }
        }

        db.close();
        c.close();
        return list;
    }

    public User getPreviousRecipient(int aid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_USER, null, null, null, null, null, "a_id");
        User user = null;

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int a_id = c.getInt(c.getColumnIndex("a_id"));

            if(a_id == aid) {
                String a_image = c.getString(c.getColumnIndex("a_image"));
                String a_fname = c.getString(c.getColumnIndex("a_fname"));
                String a_lname = c.getString(c.getColumnIndex("a_lname"));
                String a_contact = c.getString(c.getColumnIndex("a_contact"));
                String a_gender = c.getString(c.getColumnIndex("a_gender"));
                String a_username = c.getString(c.getColumnIndex("a_username"));
                String a_password = c.getString(c.getColumnIndex("a_password"));
                String a_signature = c.getString(c.getColumnIndex("a_signature"));
                String a_status = c.getString(c.getColumnIndex("a_status"));

                user = new User(a_id, Uri.parse(a_image), a_fname, a_lname, a_contact, a_gender, a_username, a_password, a_signature, a_status);
                break;
            }
        }

        db.close();
        c.close();
        return user;
    }

    public ArrayList<User> getAllUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_USER, null, null, null, null, null, "a_id");
        ArrayList<User> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            String a_username = c.getString(c.getColumnIndex("a_username"));

            if(!TextUtils.equals(a_username, username)) {
                int a_id = c.getInt(c.getColumnIndex("a_id"));
                String a_image = c.getString(c.getColumnIndex("a_image"));
                String a_fname = c.getString(c.getColumnIndex("a_fname"));
                String a_lname = c.getString(c.getColumnIndex("a_lname"));
                String a_contact = c.getString(c.getColumnIndex("a_contact"));
                String a_gender = c.getString(c.getColumnIndex("a_gender"));
                String a_password = c.getString(c.getColumnIndex("a_password"));
                String a_signature = c.getString(c.getColumnIndex("a_signature"));
                String a_status = c.getString(c.getColumnIndex("a_status"));

                list.add(new User(a_id, Uri.parse(a_image), a_fname, a_lname, a_contact, a_gender, a_username, a_password, a_signature, a_status));
            }
        }

        db.close();
        c.close();
        return list;
    }

    public ArrayList<User> getAllUserName(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_USER, null, null, null, null, null, "a_fname");
        ArrayList<User> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            String a_username = c.getString(c.getColumnIndex("a_username"));
            String a_status = c.getString(c.getColumnIndex("a_status"));

            if(TextUtils.equals(a_status, "ACTIVATED")) {
                if(!TextUtils.equals(a_username, username)) {
                    if(!TextUtils.equals(a_username, "admin")) {
                        int a_id = c.getInt(c.getColumnIndex("a_id"));
                        String a_image = c.getString(c.getColumnIndex("a_image"));
                        String a_fname = c.getString(c.getColumnIndex("a_fname"));
                        String a_lname = c.getString(c.getColumnIndex("a_lname"));
                        String a_contact = c.getString(c.getColumnIndex("a_contact"));
                        String a_gender = c.getString(c.getColumnIndex("a_gender"));
                        String a_password = c.getString(c.getColumnIndex("a_password"));
                        String a_signature = c.getString(c.getColumnIndex("a_signature"));

                        list.add(new User(a_id, Uri.parse(a_image), a_fname, a_lname, a_contact, a_gender, a_username, a_password, a_signature, a_status));
                    }
                }
            }
        }

        db.close();
        c.close();
        return list;
    }

    public ArrayList<User> getUserAdmin(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_USER, null, null, null, null, null, "a_username");
        ArrayList<User> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            String a_username = c.getString(c.getColumnIndex("a_username"));

            if (TextUtils.equals(a_username, username)) {
                int a_id = c.getInt(c.getColumnIndex("a_id"));
                String a_image = c.getString(c.getColumnIndex("a_image"));
                String a_fname = c.getString(c.getColumnIndex("a_fname"));
                String a_lname = c.getString(c.getColumnIndex("a_lname"));
                String a_contact = c.getString(c.getColumnIndex("a_contact"));
                String a_gender = c.getString(c.getColumnIndex("a_gender"));
                String a_password = c.getString(c.getColumnIndex("a_password"));
                String a_signature = c.getString(c.getColumnIndex("a_signature"));
                String a_status = c.getString(c.getColumnIndex("a_status"));

                list.add(new User(a_id, Uri.parse(a_image), a_fname, a_lname, a_contact, a_gender, a_username, a_password, a_signature, a_status));
                break;
            }
        }

        db.close();
        c.close();
        return list;
    }

    /////////////////////////////////////////////////////
    /////////////////////////////////////////////////////
    /////////////////////////////////////////////////////
    /////////////////////////////////////////////////////
    /////////////////////////////////////////////////////
    /////////////////////////////////////////////////////
    /////////////////////////////////////////////////////
    /////////////////////////////////////////////////////

    public ArrayList<User> getAllUserRating(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_USER, null, null, null, null, null, "a_fname");
        ArrayList<User> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int a_id = c.getInt(c.getColumnIndex("a_id"));
            String a_status = c.getString(c.getColumnIndex("a_status"));
            String a_username = c.getString(c.getColumnIndex("a_username"));

            if(TextUtils.equals("ACTIVATED", a_status)) {
                if(a_id != id) {
                    if(!TextUtils.equals(a_username, "admin")) {
                        addUserRating(a_id, getAverageRating(a_id));
                    }
                }
            }
        }

        SQLiteDatabase db2 = this.getReadableDatabase();
        Cursor c2 = db2.query(TBL_USER_RATING, null, null, null, null, null, "r_total DESC");

        for(c2.moveToFirst(); !c2.isAfterLast(); c2.moveToNext()) {
            int ura_id = c2.getInt(c2.getColumnIndex("a_id"));
            list.add(getUserE(ura_id));
        }

        if(!list.isEmpty()) {
            deleteAllUserRating();
        }

        c.close();
        db.close();
        c2.close();
        db2.close();
        return list;
    }

    public void addUserRating(int a_id, float r_total) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("a_id", a_id);
        cv.put("r_total", r_total);
        db.insert(TBL_USER_RATING, null, cv);

        db.close();
    }

    public User getUserE(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_USER, null, null, null, null, null, "a_id");
        User e = null;

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int a_id = c.getInt(c.getColumnIndex("a_id"));

            if(a_id == id) {
                String a_image = c.getString(c.getColumnIndex("a_image"));
                String a_fname = c.getString(c.getColumnIndex("a_fname"));
                String a_lname = c.getString(c.getColumnIndex("a_lname"));
                String a_contact = c.getString(c.getColumnIndex("a_contact"));
                String a_gender = c.getString(c.getColumnIndex("a_gender"));
                String a_username = c.getString(c.getColumnIndex("a_username"));
                String a_password = c.getString(c.getColumnIndex("a_password"));
                String a_signature = c.getString(c.getColumnIndex("a_signature"));
                String a_status = c.getString(c.getColumnIndex("a_status"));

                e = new User(a_id, Uri.parse(a_image), a_fname, a_lname, a_contact, a_gender, a_username, a_password, a_signature, a_status);
                break;
            }
        }

        db.close();
        c.close();
        return e;
    }

    public ArrayList<User> getAllUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_USER, null, null, null, null, null, "a_id");
        ArrayList<User> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            String a_status = c.getString(c.getColumnIndex("a_status"));

            if(TextUtils.equals(a_status, "ACTIVATED")) {
                int a_id = c.getInt(c.getColumnIndex("a_id"));
                String a_image = c.getString(c.getColumnIndex("a_image"));
                String a_fname = c.getString(c.getColumnIndex("a_fname"));
                String a_lname = c.getString(c.getColumnIndex("a_lname"));
                String a_contact = c.getString(c.getColumnIndex("a_contact"));
                String a_gender = c.getString(c.getColumnIndex("a_gender"));
                String a_username = c.getString(c.getColumnIndex("a_username"));
                String a_password = c.getString(c.getColumnIndex("a_password"));
                String a_signature = c.getString(c.getColumnIndex("a_signature"));

                list.add(new User(a_id, Uri.parse(a_image), a_fname, a_lname, a_contact, a_gender, a_username, a_password, a_signature, a_status));
            }
        }

        db.close();
        c.close();
        return list;
    }

    public ArrayList<User> adminGetAllUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_USER, null, null, null, null, null, "a_id");
        ArrayList<User> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            String a_status = c.getString(c.getColumnIndex("a_status"));
            String a_username = c.getString(c.getColumnIndex("a_username"));

            if(!TextUtils.equals(a_username, "admin")) {
                int a_id = c.getInt(c.getColumnIndex("a_id"));
                String a_image = c.getString(c.getColumnIndex("a_image"));
                String a_fname = c.getString(c.getColumnIndex("a_fname"));
                String a_lname = c.getString(c.getColumnIndex("a_lname"));
                String a_contact = c.getString(c.getColumnIndex("a_contact"));
                String a_gender = c.getString(c.getColumnIndex("a_gender"));
                String a_password = c.getString(c.getColumnIndex("a_password"));
                String a_signature = c.getString(c.getColumnIndex("a_signature"));

                list.add(new User(a_id, Uri.parse(a_image), a_fname, a_lname, a_contact, a_gender, a_username, a_password, a_signature, a_status));
            }
        }

        db.close();
        c.close();
        return list;
    }

    public boolean isTaken(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_USER, null, null, null, null, null, "a_username");
        boolean isTaken = false;

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            String a_username = c.getString(c.getColumnIndex("a_username"));

            if(a_username.equals(username)) {
                isTaken = true;
                break;
            }
        }

        db.close();
        c.close();
        return isTaken;
    }

    public void updateUserImage(String a_username, String a_image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("a_image", a_image);
        db.update(TBL_USER, cv, "a_username='" +a_username+ "';", null);
        db.update(TBL_LOGIN_USER, cv, "a_username='" +a_username+ "';", null);

        db.close();
    }

    public void updateUserSignature(int a_id, String a_signature) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("a_signature", a_signature);
        db.update(TBL_USER, cv, "a_id=" +a_id, null);
        db.update(TBL_LOGIN_USER, cv, "a_id=" +a_id, null);

        db.close();
    }

    public void updateUserInfo(String a_username, String a_fname, String a_lname, String a_contact, String a_gender) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("a_fname", a_fname);
        cv.put("a_lname", a_lname);
        cv.put("a_contact", a_contact);
        cv.put("a_gender", a_gender);
        db.update(TBL_USER, cv, "a_username='" +a_username+ "';", null);
        db.update(TBL_LOGIN_USER, cv, "a_username='" +a_username+ "';", null);

        db.close();
    }

    public void updateUserPassword(String a_username, String a_password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("a_password", a_password);
        db.update(TBL_USER, cv, "a_username='" +a_username+ "';", null);
        db.update(TBL_LOGIN_USER, cv, "a_username='" +a_username+ "';", null);

        db.close();
    }

    public void updateUserStatus(String a_username, String a_status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("a_status", a_status);
        db.update(TBL_USER, cv, "a_username='" +a_username+ "';", null);
        db.update(TBL_LOGIN_USER, cv, "a_username='" +a_username+ "';", null);

        db.close();
    }

    //***********************************************************************************************************
    // LOGIN USER
    //***********************************************************************************************************
    public long addLoginUser(int a_id, String a_image, String a_fname, String a_lname, String a_contact, String a_gender, String a_username, String a_password, String a_signature, String a_status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        long res = 0;

        cv.put("a_id", a_id);
        cv.put("a_image", a_image);
        cv.put("a_fname", a_fname);
        cv.put("a_lname", a_lname);
        cv.put("a_contact", a_contact);
        cv.put("a_gender", a_gender);
        cv.put("a_username", a_username);
        cv.put("a_password", a_password);
        cv.put("a_signature", a_signature);
        cv.put("a_status", a_status);
        res = db.insert(TBL_LOGIN_USER, null, cv);

        db.close();
        return res;
    }

    public boolean checkLoginUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_USER, null, null, null, null, null, "a_username");
        boolean isCheck = false;

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            String a_username = c.getString(c.getColumnIndex("a_username"));
            String a_password = c.getString(c.getColumnIndex("a_password"));

            if(a_username.equals(username) && a_password.equals(password)) {
                int a_id = c.getInt(c.getColumnIndex("a_id"));
                String a_image = c.getString(c.getColumnIndex("a_image"));
                String a_fname = c.getString(c.getColumnIndex("a_fname"));
                String a_lname = c.getString(c.getColumnIndex("a_lname"));
                String a_contact = c.getString(c.getColumnIndex("a_contact"));
                String a_gender = c.getString(c.getColumnIndex("a_gender"));
                String a_signature = c.getString(c.getColumnIndex("a_signature"));
                String a_status = c.getString(c.getColumnIndex("a_status"));

                addLoginUser(a_id, a_image, a_fname, a_lname, a_contact, a_gender, a_username, a_password, a_signature, a_status);
                isCheck = true;
                break;
            }
        }

        db.close();
        c.close();
        return isCheck;
    }

    public boolean checkUserStatus(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_USER, null, null, null, null, null, "a_username");
        boolean isCheck = false;

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            String a_username = c.getString(c.getColumnIndex("a_username"));

            if(a_username.equals(username)) {
                String a_status = c.getString(c.getColumnIndex("a_status"));

                if(a_status.equals("ACTIVATED")) {
                    isCheck = true;
                }

                break;
            }
        }

        db.close();
        c.close();
        return isCheck;
    }

    /*public void addLoginUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_USER, null, null, null, null, null, "a_id");

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            String a_username = c.getString(c.getColumnIndex("a_username"));

            if(a_username.equals(username)) {
                String a_image = c.getString(c.getColumnIndex("a_image"));
                String a_fname = c.getString(c.getColumnIndex("a_fname"));
                String a_lname = c.getString(c.getColumnIndex("a_lname"));
                String a_contact = c.getString(c.getColumnIndex("a_contact"));
                String a_gender = c.getString(c.getColumnIndex("a_gender"));
                String a_password = c.getString(c.getColumnIndex("a_password"));
                String a_signature = c.getString(c.getColumnIndex("a_signature"));

                addLoginUser(a_image, a_fname, a_lname, a_contact, a_gender, a_username, a_password, a_signature);
                break;
            }
        }

        db.close();
        c.close();
    }*/

    public ArrayList<User> getLogInUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_LOGIN_USER, null, null, null, null, null, "a_id");
        ArrayList<User> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int a_id = c.getInt(c.getColumnIndex("a_id"));
            String a_username = c.getString(c.getColumnIndex("a_username"));
            String a_image = c.getString(c.getColumnIndex("a_image"));
            String a_fname = c.getString(c.getColumnIndex("a_fname"));
            String a_lname = c.getString(c.getColumnIndex("a_lname"));
            String a_contact = c.getString(c.getColumnIndex("a_contact"));
            String a_gender = c.getString(c.getColumnIndex("a_gender"));
            String a_password = c.getString(c.getColumnIndex("a_password"));
            String a_signature = c.getString(c.getColumnIndex("a_signature"));
            String a_status = c.getString(c.getColumnIndex("a_status"));

            list.add(new User(a_id, Uri.parse(a_image), a_fname, a_lname, a_contact, a_gender, a_username, a_password, a_signature, a_status));
        }

        db.close();
        c.close();
        return list;
    }

    //***********************************************************************************************************
    // REPRESENTATIVE
    //***********************************************************************************************************
    public long addRep(String rep_image, String rep_fname, String rep_lname, String rep_contact, String rep_gender, String rep_identification, int a_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        long res = 0;

        cv.put("rep_image", rep_image);
        cv.put("rep_fname", rep_fname);
        cv.put("rep_lname", rep_lname);
        cv.put("rep_contact", rep_contact);
        cv.put("rep_gender", rep_gender);
        cv.put("rep_identification", rep_identification);
        cv.put("a_id", a_id);
        res = db.insert(TBL_REPRESENTATIVES, null, cv);

        db.close();
        return res;
    }

    /*public void editRep(String rep_image, String rep_fname, String rep_lname, String rep_contact, String rep_gender, String rep_identification, int a_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("rep_image", rep_image);
        cv.put("rep_fname", rep_fname);
        cv.put("rep_lname", rep_lname);
        cv.put("rep_contact", rep_contact);
        cv.put("rep_gender", rep_gender);
        cv.put("rep_identification", rep_identification);
        db.update(TBL_REPRESENTATIVES, cv, "a_id=" +a_id, null);

        db.close();
    }*/

    public void updateRepInfo(int a_id, int rep_id, String rep_fname, String rep_lname, String rep_contact, String rep_gender) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("rep_fname", rep_fname);
        cv.put("rep_lname", rep_lname);
        cv.put("rep_contact", rep_contact);
        cv.put("rep_gender", rep_gender);
        db.update(TBL_REPRESENTATIVES, cv, "a_id=" +a_id+ " AND rep_id=" +rep_id +";", null);
//        db.delete(TBL_REPRESENTATIVES, "a_id=" +a_id+ " AND rep_id=" +rep_id, null);

        db.close();
    }

    public ArrayList<Rep> getAllRep() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_REPRESENTATIVES, null, null, null, null, null, "a_id");
        ArrayList<Rep> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int rep_id = c.getInt(c.getColumnIndex("rep_id"));
            String rep_image = c.getString(c.getColumnIndex("rep_image"));
            String rep_fname = c.getString(c.getColumnIndex("rep_fname"));
            String rep_lname = c.getString(c.getColumnIndex("rep_lname"));
            String rep_contact = c.getString(c.getColumnIndex("rep_contact"));
            String rep_gender = c.getString(c.getColumnIndex("rep_gender"));
            String rep_identification = c.getString(c.getColumnIndex("rep_identification"));
            int a_id = c.getInt(c.getColumnIndex("a_id"));

            list.add(new Rep(rep_id, Uri.parse(rep_image), rep_fname, rep_lname, rep_contact, rep_gender, rep_identification, a_id));
        }

        c.close();
        db.close();
        return list;
    }

    public ArrayList<Rep> getAllRep(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_REPRESENTATIVES, null, null, null, null, null, "a_id");
        ArrayList<Rep> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int a_id = c.getInt(c.getColumnIndex("a_id"));

            Log.d("a_id", ": " +a_id);
            Log.d("id", ": " +id);

            if(id == a_id) {
                int rep_id = c.getInt(c.getColumnIndex("rep_id"));
                String rep_image = c.getString(c.getColumnIndex("rep_image"));
                String rep_fname = c.getString(c.getColumnIndex("rep_fname"));
                String rep_lname = c.getString(c.getColumnIndex("rep_lname"));
                String rep_contact = c.getString(c.getColumnIndex("rep_contact"));
                String rep_gender = c.getString(c.getColumnIndex("rep_gender"));
                String rep_identification = c.getString(c.getColumnIndex("rep_identification"));

                list.add(new Rep(rep_id, Uri.parse(rep_image), rep_fname, rep_lname, rep_contact, rep_gender, rep_identification, a_id));
            }
        }

        c.close();
        db.close();
        return list;
    }

    public ArrayList<Rep> getRep(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_REPRESENTATIVES, null, null, null, null, null, "rep_id");
        ArrayList<Rep> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int rep_id = c.getInt(c.getColumnIndex("rep_id"));

            if(id == rep_id) {
                int a_id = c.getInt(c.getColumnIndex("a_id"));
                String rep_image = c.getString(c.getColumnIndex("rep_image"));
                String rep_fname = c.getString(c.getColumnIndex("rep_fname"));
                String rep_lname = c.getString(c.getColumnIndex("rep_lname"));
                String rep_contact = c.getString(c.getColumnIndex("rep_contact"));
                String rep_gender = c.getString(c.getColumnIndex("rep_gender"));
                String rep_identification = c.getString(c.getColumnIndex("rep_identification"));

                list.add(new Rep(rep_id, Uri.parse(rep_image), rep_fname, rep_lname, rep_contact, rep_gender, rep_identification, a_id));
            }
        }

        c.close();
        db.close();
        return list;
    }

    public ArrayList<Rep> getRep(int aid, int repid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_REPRESENTATIVES, null, null, null, null, null, "rep_id");
        ArrayList<Rep> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int rep_id = c.getInt(c.getColumnIndex("rep_id"));
            int a_id = c.getInt(c.getColumnIndex("a_id"));

            if(repid == rep_id && a_id == aid) {
                String rep_image = c.getString(c.getColumnIndex("rep_image"));
                String rep_fname = c.getString(c.getColumnIndex("rep_fname"));
                String rep_lname = c.getString(c.getColumnIndex("rep_lname"));
                String rep_contact = c.getString(c.getColumnIndex("rep_contact"));
                String rep_gender = c.getString(c.getColumnIndex("rep_gender"));
                String rep_identification = c.getString(c.getColumnIndex("rep_identification"));

                list.add(new Rep(rep_id, Uri.parse(rep_image), rep_fname, rep_lname, rep_contact, rep_gender, rep_identification, a_id));
            }
        }

        c.close();
        db.close();
        return list;
    }

    //***********************************************************************************************************
    // REPID
    //***********************************************************************************************************
    public long addRepId(int a_id, int rep_id, String repid_image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        long res = 0;

        cv.put("a_id", a_id);
        cv.put("rep_id", rep_id);
        cv.put("repid_image", repid_image);
        res = db.insert(TBL_REPID, null, cv);

        db.close();
        return res;
    }

    public ArrayList<RepId> getAllRepId(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_REPID, null, null, null, null, null, "a_id");
        ArrayList<RepId> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int a_id = c.getInt(c.getColumnIndex("a_id"));

            if(a_id == id) {
                int repid_id = c.getInt(c.getColumnIndex("repid_id"));
                String repid_image = c.getString(c.getColumnIndex("repid_image"));
                int rep_id = c.getInt(c.getColumnIndex("rep_id"));

                list.add(new RepId(repid_id, a_id, rep_id, repid_image));
            }
        }

        c.close();
        db.close();
        return list;
    }

    public ArrayList<RepId> getRepId(int aid, int repid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_REPID, null, null, null, null, null, "a_id");
        ArrayList<RepId> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int a_id = c.getInt(c.getColumnIndex("a_id"));
            int rep_id = c.getInt(c.getColumnIndex("rep_id"));

            if(a_id == aid && repid == rep_id) {
                String repid_image = c.getString(c.getColumnIndex("repid_image"));
                int repid_id = c.getInt(c.getColumnIndex("repid_id"));

                list.add(new RepId(repid_id, a_id, rep_id, repid_image));
            }
        }

        c.close();
        db.close();
        return list;
    }

    public ArrayList<RepId> getAllRepId() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_REPID, null, null, null, null, null, "a_id");
        ArrayList<RepId> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int a_id = c.getInt(c.getColumnIndex("a_id"));
            int repid_id = c.getInt(c.getColumnIndex("repid_id"));
            String repid_image = c.getString(c.getColumnIndex("repid_image"));
            int rep_id = c.getInt(c.getColumnIndex("rep_id"));

            list.add(new RepId(repid_id, a_id, rep_id, repid_image));
        }

        c.close();
        db.close();
        return list;
    }

    //***********************************************************************************************************
    // DRAFT
    //***********************************************************************************************************
    public void addDraft(int a_id, int t_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("a_id", a_id);
        cv.put("t_id", t_id);
        db.insert(TBL_DRAFT, null, cv);

        db.close();
    }

//    public void updateDraftDate(int a_id, String d_date) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//
//        cv.put("d_date", d_date);
//        db.update(TBL_DRAFT, cv, "a_id=" +a_id, null);
//
//        db.close();
//    }
//
//    public void updateDraftAddress(int a_id, String d_address) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//
//        cv.put("d_address", d_address);
//        db.update(TBL_DRAFT, cv, "a_id=" +a_id, null);
//
//        db.close();
//    }
//
//    public void updateDraftReason(int a_id, String d_reason) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//
//        cv.put("d_reason", d_reason);
//        db.update(TBL_DRAFT, cv, "a_id=" +a_id, null);
//
//        db.close();
//    }
//
//    public void updateDraftClosing(int a_id, String d_closing) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//
//        cv.put("d_closing", d_closing);
//        db.update(TBL_DRAFT, cv, "a_id=" +a_id, null);
//
//        db.close();
//    }
//
//    public void updateDraftSignature(int a_id, String d_signature) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//
//        cv.put("d_signature", d_signature);
//        db.update(TBL_DRAFT, cv, "a_id=" +a_id, null);
//
//        db.close();
//    }
//
//    public void updateDraftRec(int a_id, int rec_id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//
//        cv.put("rec_id", rec_id);
//        db.update(TBL_DRAFT, cv, "a_id=" +a_id, null);
//
//        db.close();
//    }
//
//    public void updateDraftRep(int a_id, int rep_id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//
//        cv.put("rep_id", rep_id);
//        db.update(TBL_DRAFT, cv, "a_id=" +a_id, null);
//
//        db.close();
//    }

    public ArrayList<Draft> getDraft(int aid, int tid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_DRAFT, null, null, null, null, null, "a_id");
        ArrayList<Draft> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int a_id = c.getInt(c.getColumnIndex("a_id"));
            int t_id = c.getInt(c.getColumnIndex("t_id"));

            if(a_id == aid && t_id == tid) {
                int d_id = c.getInt(c.getColumnIndex("d_id"));

                list.add(new Draft(d_id, a_id, t_id));
            }
        }

        c.close();
        db.close();
        return list;
    }

    //***********************************************************************************************************
    // NEW DRAFT
    //***********************************************************************************************************
    public int addNewDraft(String d_date, String d_letterContent, String d_ot, String d_ct, int t_id, int a_id, int rep_id, int rec_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        int res = 0;

        cv.put("d_date", d_date);
        cv.put("d_letterContent", d_letterContent);
        cv.put("d_ot", d_ot);
        cv.put("d_ct", d_ct);
        cv.put("t_id", t_id);
        cv.put("a_id", a_id);
        cv.put("rep_id", rep_id);
        cv.put("rec_id", rec_id);
        try {
            res = Integer.parseInt(db.insert(TBL_NEW_DRAFT, null, cv) +"");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        db.close();
        return res;
    }

    public void updateNewDraftContent(int a_id, String d_letterContent) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("d_letterContent", d_letterContent);
        db.update(TBL_NEW_DRAFT, cv, "a_id=" +a_id, null);

        db.close();
    }

    public void updateNewDraftRep(int a_id, int rep_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("rep_id", rep_id);
        db.update(TBL_NEW_DRAFT, cv, "a_id=" +a_id, null);

        db.close();
    }

    public void updateNewDraftRec(int a_id, int rec_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("rec_id", rec_id);
        db.update(TBL_NEW_DRAFT, cv, "a_id=" +a_id, null);

        db.close();
    }

    public void updateNewDraftOT(int a_id, String d_ot) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("d_ot", d_ot);
        db.update(TBL_NEW_DRAFT, cv, "a_id=" +a_id, null);

        db.close();
    }

    public void updateNewDraftCT(int a_id, String d_ct) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("d_ct", d_ct);
        db.update(TBL_NEW_DRAFT, cv, "a_id=" +a_id, null);

        db.close();
    }

    public ArrayList<NewDraft> getNewDraft(int aid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_NEW_DRAFT, null, null, null, null, null, "a_id");
        ArrayList<NewDraft> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int a_id = c.getInt(c.getColumnIndex("a_id"));

            if(a_id == aid) {
                int d_id = c.getInt(c.getColumnIndex("d_id"));
                String d_date = c.getString(c.getColumnIndex("d_date"));
                String d_letterContent = c.getString(c.getColumnIndex("d_letterContent"));
                String d_ot = c.getString(c.getColumnIndex("d_ot"));
                String d_ct = c.getString(c.getColumnIndex("d_ct"));
                int t_id = c.getInt(c.getColumnIndex("t_id"));
                int rep_id = c.getInt(c.getColumnIndex("rep_id"));
                int rec_id = c.getInt(c.getColumnIndex("rec_id"));

                list.add(new NewDraft(d_id, d_date, d_letterContent, d_ot, d_ct, t_id, a_id, rep_id, rec_id));
                break;
            }
        }

        c.close();
        db.close();
        return list;
    }

    //***********************************************************************************************************
    // SENT
    //***********************************************************************************************************
    public int addSent(int s_otp, String sentDate, String s_letter, int t_id, int a_id, int rep_id, int rec_id, int rate_id, int r_id, String s_status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        int res = 0;

        cv.put("s_otp", s_otp);
        cv.put("sentDate", sentDate);
        cv.put("s_letter", s_letter);
        cv.put("t_id", t_id);
        cv.put("a_id", a_id);
        cv.put("rep_id", rep_id);
        cv.put("rec_id", rec_id);
        cv.put("rate_id", rate_id);
        cv.put("r_id", r_id);
        cv.put("s_status", s_status);
        try {
            res = Integer.parseInt(db.insert(TBL_SENT, null, cv) +"");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        db.close();
        return res;
    }

    public void updateResendSent(int s_id, String s_letter, String s_status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("s_letter", s_letter);
        cv.put("s_status", s_status);
        db.update(TBL_SENT, cv, "s_id=" +s_id, null);

        db.close();
    }

    public void updateResendReceive(int r_id, String r_letter, String r_status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("r_letter", r_letter);
        cv.put("r_status", r_status);
        db.update(TBL_RECEIVE, cv, "r_id=" +r_id, null);

        db.close();
    }

    public void updateSentStatus(int s_id, String s_status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("s_status", s_status);
        db.update(TBL_SENT, cv, "s_id=" +s_id, null);

        db.close();
    }

    public void updateSentRate(int s_id, int rate_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("rate_id", rate_id);
        db.update(TBL_SENT, cv, "s_id=" +s_id, null);

        db.close();
    }

    public void updateSentRID(int s_id, int r_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("r_id", r_id);
        db.update(TBL_SENT, cv, "s_id=" +s_id, null);

        db.close();
    }

    public ArrayList<Sent> getAllSent(int aid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_SENT, null, null, null, null, null, "a_id");
        ArrayList<Sent> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int a_id = c.getInt(c.getColumnIndex("a_id"));

            if(a_id == aid) {
                int s_id = c.getInt(c.getColumnIndex("s_id"));
                int s_otp = c.getInt(c.getColumnIndex("s_otp"));
                String sentDate = c.getString(c.getColumnIndex("sentDate"));
                String s_letter = c.getString(c.getColumnIndex("s_letter"));
                int t_id = c.getInt(c.getColumnIndex("t_id"));
                int rep_id = c.getInt(c.getColumnIndex("rep_id"));
                int rec_id = c.getInt(c.getColumnIndex("rec_id"));
                int rate_id = c.getInt(c.getColumnIndex("rate_id"));
                int r_id = c.getInt(c.getColumnIndex("r_id"));
                String s_status = c.getString(c.getColumnIndex("s_status"));

                list.add(new Sent(s_id, s_otp, sentDate, s_letter, t_id, a_id, rep_id, rec_id, rate_id, r_id, s_status));
            }
        }

        c.close();
        db.close();
        return list;
    }

    public String getSentStatus(int sid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_SENT, null, null, null, null, null, "a_id");
        String s_status = "";

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int s_id = c.getInt(c.getColumnIndex("s_id"));

            if(s_id == sid) {
                s_status = c.getString(c.getColumnIndex("s_status"));
                break;
            }
        }

        c.close();
//        db.close();
        return s_status;
    }

    public ArrayList<Sent> getAllSentSortStatus(int aid, String sstatus) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_SENT, null, null, null, null, null, "sentDate DESC");
        ArrayList<Sent> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int a_id = c.getInt(c.getColumnIndex("a_id"));

            if(a_id == aid) {
                String s_status = c.getString(c.getColumnIndex("s_status"));

                if(s_status.equals(sstatus)) {
                    int s_id = c.getInt(c.getColumnIndex("s_id"));
                    int s_otp = c.getInt(c.getColumnIndex("s_otp"));
                    String sentDate = c.getString(c.getColumnIndex("sentDate"));
                    String s_letter = c.getString(c.getColumnIndex("s_letter"));
                    int t_id = c.getInt(c.getColumnIndex("t_id"));
                    int rep_id = c.getInt(c.getColumnIndex("rep_id"));
                    int rec_id = c.getInt(c.getColumnIndex("rec_id"));
                    int rate_id = c.getInt(c.getColumnIndex("rate_id"));
                    int r_id = c.getInt(c.getColumnIndex("r_id"));

                    list.add(new Sent(s_id, s_otp, sentDate, s_letter, t_id, a_id, rep_id, rec_id, rate_id, r_id, s_status));
                }
            }
        }

        c.close();
        db.close();
        return list;
    }

    public ArrayList<Sent> getAllSentAdmin(String sstatus) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_SENT, null, null, null, null, null, "sentDate DESC");
        ArrayList<Sent> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            String s_status = c.getString(c.getColumnIndex("s_status"));

            if(s_status.equals(sstatus)) {
                int s_id = c.getInt(c.getColumnIndex("s_id"));
                int s_otp = c.getInt(c.getColumnIndex("s_otp"));
                String sentDate = c.getString(c.getColumnIndex("sentDate"));
                String s_letter = c.getString(c.getColumnIndex("s_letter"));
                int t_id = c.getInt(c.getColumnIndex("t_id"));
                int a_id = c.getInt(c.getColumnIndex("a_id"));
                int rep_id = c.getInt(c.getColumnIndex("rep_id"));
                int rec_id = c.getInt(c.getColumnIndex("rec_id"));
                int rate_id = c.getInt(c.getColumnIndex("rate_id"));
                int r_id = c.getInt(c.getColumnIndex("r_id"));

                list.add(new Sent(s_id, s_otp, sentDate, s_letter, t_id, a_id, rep_id, rec_id, rate_id, r_id, s_status));
            }
        }

        c.close();
        db.close();
        return list;
    }

    public ArrayList<Sent> getSent(int sid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_SENT, null, null, null, null, null, "s_id");
        ArrayList<Sent> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int s_id = c.getInt(c.getColumnIndex("s_id"));

            if(s_id == sid) {
                int s_otp = c.getInt(c.getColumnIndex("s_otp"));
                String sentDate = c.getString(c.getColumnIndex("sentDate"));
                String s_letter = c.getString(c.getColumnIndex("s_letter"));
                int t_id = c.getInt(c.getColumnIndex("t_id"));
                int a_id = c.getInt(c.getColumnIndex("a_id"));
                int rep_id = c.getInt(c.getColumnIndex("rep_id"));
                int rec_id = c.getInt(c.getColumnIndex("rec_id"));
                int rate_id = c.getInt(c.getColumnIndex("rate_id"));
                int r_id = c.getInt(c.getColumnIndex("r_id"));
                String s_status = c.getString(c.getColumnIndex("s_status"));

                list.add(new Sent(s_id, s_otp, sentDate, s_letter, t_id, a_id, rep_id, rec_id, rate_id, r_id, s_status));
                break;
            }
        }

        c.close();
        db.close();
        return list;
    }/*

    public List getPastRecipient(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_SENT, null, null, null, null, null, "a_id");
        List<Integer> list = new ArrayList<>();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int a_id = c.getInt(c.getColumnIndex("a_id"));

            if(a_id == id) {
                int rec_id = c.getInt(c.getColumnIndex("rec_id"));

                list.add(rec_id);
            }
        }

        db.close();
        c.close();
        return list;
    }*/

    public ArrayList<User> getPastRecipient(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_SENT, null, null, null, null, null, "a_id");

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int a_id = c.getInt(c.getColumnIndex("a_id"));

            if(a_id == id) {
                int rec_id = c.getInt(c.getColumnIndex("rec_id"));

                if(!findRID(rec_id)) {
                    addPastRecipient(a_id, rec_id);
                }
            }
        }
        SQLiteDatabase db2 = this.getReadableDatabase();
        Cursor c2 = db2.query(TBL_PAST_RECIPIENT, null, null, null, null, null, "a_id");
        ArrayList<User> list = new ArrayList<>();

        for(c2.moveToFirst(); !c2.isAfterLast(); c2.moveToNext()) {
            int a_id = c2.getInt(c2.getColumnIndex("a_id"));

            if(a_id == id) {
                int r_id = c2.getInt(c2.getColumnIndex("r_id"));

                list.add(getPreviousRecipient(r_id));
            }
        }

        deleteAllPastRecipient();

        db.close();
        c.close();
        db2.close();
        c2.close();
        return list;
    }

    /// KANI
    /*public ArrayList<User> getPastRecipient(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_SENT, null, null, null, null, null, "a_id");
        ArrayList<User> list = new ArrayList<>();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int a_id = c.getInt(c.getColumnIndex("a_id"));

            if(a_id == id) {
                int rec_id = c.getInt(c.getColumnIndex("rec_id"));

                list.add(getPreviousRecipient(rec_id));
            }
        }

        db.close();
        c.close();
        return list;
    }*/

    //***********************************************************************************************************
    // RECEIVE
    //***********************************************************************************************************
    public int addReceive(int r_otp, String receivedDate, String r_letter, int t_id, int a_id, int rep_id, int rec_id, int rate_id, int s_id, String r_status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        int res = 0;

        cv.put("r_otp", r_otp);
        cv.put("receivedDate", receivedDate);
        cv.put("r_letter", r_letter);
        cv.put("t_id", t_id);
        cv.put("a_id", a_id);
        cv.put("rep_id", rep_id);
        cv.put("rec_id", rec_id);
        cv.put("rate_id", rate_id);
        cv.put("s_id", s_id);
        cv.put("r_status", r_status);
        try {
            res = Integer.parseInt(db.insert(TBL_RECEIVE, null, cv) +"");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        db.close();
        return res;
    }

    public void updateReceiveStatus(int r_id, String r_status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("r_status", r_status);
        db.update(TBL_RECEIVE, cv, "r_id=" +r_id, null);

        db.close();
    }

    public void updateReceiveRate(int r_id, int rate_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("rate_id", rate_id);
        db.update(TBL_RECEIVE, cv, "r_id=" +r_id, null);

        db.close();
    }

    public void updateReceiveSID(int r_id, int s_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("s_id", s_id);
        db.update(TBL_RECEIVE, cv, "r_id=" +r_id, null);

        db.close();
    }

    public ArrayList<Receive> getAllReceive(int recid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_RECEIVE, null, null, null, null, null, "rec_id");
        ArrayList<Receive> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int rec_id = c.getInt(c.getColumnIndex("rec_id"));

            if(rec_id == recid) {
                int r_id = c.getInt(c.getColumnIndex("r_id"));
                int r_otp = c.getInt(c.getColumnIndex("r_otp"));
                String receivedDate = c.getString(c.getColumnIndex("receivedDate"));
                String r_letter = c.getString(c.getColumnIndex("r_letter"));
                int t_id = c.getInt(c.getColumnIndex("t_id"));
                int a_id = c.getInt(c.getColumnIndex("a_id"));
                int rep_id = c.getInt(c.getColumnIndex("rep_id"));
                int rate_id = c.getInt(c.getColumnIndex("rate_id"));
                int s_id = c.getInt(c.getColumnIndex("s_id"));
                String r_status = c.getString(c.getColumnIndex("r_status"));

                list.add(new Receive(r_id, r_otp, receivedDate, r_letter, t_id, a_id, rep_id, rec_id, rate_id, s_id, r_status));
            }
        }

        c.close();
        db.close();
        return list;
    }

    public ArrayList<Receive> getAllReceiveSortStatus(int recid, String rstatus) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_RECEIVE, null, null, null, null, null, "receivedDate  DESC");
        ArrayList<Receive> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int rec_id = c.getInt(c.getColumnIndex("rec_id"));

            if(rec_id == recid) {
                String r_status = c.getString(c.getColumnIndex("r_status"));

                if(r_status.equals(rstatus)) {
                    int r_id = c.getInt(c.getColumnIndex("r_id"));
                    int r_otp = c.getInt(c.getColumnIndex("r_otp"));
                    String receivedDate = c.getString(c.getColumnIndex("receivedDate"));
                    String r_letter = c.getString(c.getColumnIndex("r_letter"));
                    int t_id = c.getInt(c.getColumnIndex("t_id"));
                    int a_id = c.getInt(c.getColumnIndex("a_id"));
                    int rep_id = c.getInt(c.getColumnIndex("rep_id"));
                    int rate_id = c.getInt(c.getColumnIndex("rate_id"));
                    int s_id = c.getInt(c.getColumnIndex("s_id"));

                    list.add(new Receive(r_id, r_otp, receivedDate, r_letter, t_id, a_id, rep_id, rec_id, rate_id, s_id, r_status));
                }
            }
        }

        c.close();
        db.close();
        return list;
    }

    public ArrayList<Receive> getReceive(int rid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_RECEIVE, null, null, null, null, null, "rec_id");
        ArrayList<Receive> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int r_id = c.getInt(c.getColumnIndex("r_id"));

            if(r_id == rid) {
                int r_otp = c.getInt(c.getColumnIndex("r_otp"));
                String receivedDate = c.getString(c.getColumnIndex("receivedDate"));
                String r_letter = c.getString(c.getColumnIndex("r_letter"));
                int t_id = c.getInt(c.getColumnIndex("t_id"));
                int a_id = c.getInt(c.getColumnIndex("a_id"));
                int rep_id = c.getInt(c.getColumnIndex("rep_id"));
                int rec_id = c.getInt(c.getColumnIndex("rec_id"));
                int rate_id = c.getInt(c.getColumnIndex("rate_id"));
                int s_id = c.getInt(c.getColumnIndex("s_id"));
                String r_status = c.getString(c.getColumnIndex("r_status"));

                list.add(new Receive(r_id, r_otp, receivedDate, r_letter, t_id, a_id, rep_id, rec_id, rate_id, s_id, r_status));
                break;
            }
        }

        c.close();
        db.close();
        return list;
    }

    //***********************************************************************************************************
    // FEEDBACK
    //***********************************************************************************************************
    public void addFeedback(String f_title, String f_date, String f_sdate, String f_edate, String f_message, int a_id, int rec_id, int s_id, int r_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("f_title", f_title);
        cv.put("f_date", f_date);
        cv.put("f_sdate", f_sdate);
        cv.put("f_edate", f_edate);
        cv.put("f_message", f_message);
        cv.put("a_id", a_id);
        cv.put("rec_id", rec_id);
        cv.put("s_id", s_id);
        cv.put("r_id", r_id);
        db.insert(TBL_FEEDBACK, null, cv);

        db.close();
    }

    public void updateFeedback(int f_id, String f_title, String f_sdate, String f_edate, String f_message) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("f_title", f_title);
        cv.put("f_sdate", f_sdate);
        cv.put("f_edate", f_edate);
        cv.put("f_message", f_message);
        db.update(TBL_FEEDBACK, cv, "f_id=" +f_id, null);

        db.close();
    }

    public ArrayList<Feedback> getFeedback(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_FEEDBACK, null, null, null, null, null, "f_id");
        ArrayList<Feedback> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int f_id = c.getInt(c.getColumnIndex("f_id"));

            if(f_id == id) {
                String f_title = c.getString(c.getColumnIndex("f_title"));
                String f_date = c.getString(c.getColumnIndex("f_date"));
                String f_sdate = c.getString(c.getColumnIndex("f_sdate"));
                String f_edate = c.getString(c.getColumnIndex("f_edate"));
                String f_message = c.getString(c.getColumnIndex("f_message"));
                int a_id = c.getInt(c.getColumnIndex("a_id"));
                int rec_id = c.getInt(c.getColumnIndex("rec_id"));
                int s_id = c.getInt(c.getColumnIndex("s_id"));
                int r_id = c.getInt(c.getColumnIndex("r_id"));

                list.add(new Feedback(f_id, f_title, f_date, f_sdate, f_edate, f_message, a_id, rec_id, s_id, r_id));
                break;
            }
        }

        c.close();
        db.close();
        return list;
    }

    public ArrayList<Feedback> getReceiveFeedback(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_FEEDBACK, null, null, null, null, null, "f_id");
        ArrayList<Feedback> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int r_id = c.getInt(c.getColumnIndex("r_id"));

            if(r_id == id) {
                int f_id = c.getInt(c.getColumnIndex("f_id"));
                String f_title = c.getString(c.getColumnIndex("f_title"));
                String f_date = c.getString(c.getColumnIndex("f_date"));
                String f_sdate = c.getString(c.getColumnIndex("f_sdate"));
                String f_edate = c.getString(c.getColumnIndex("f_edate"));
                String f_message = c.getString(c.getColumnIndex("f_message"));
                int a_id = c.getInt(c.getColumnIndex("a_id"));
                int rec_id = c.getInt(c.getColumnIndex("rec_id"));
                int s_id = c.getInt(c.getColumnIndex("s_id"));

                list.add(new Feedback(f_id, f_title, f_date, f_sdate, f_edate, f_message, a_id, rec_id, s_id, r_id));
                break;
            }
        }

        c.close();
        db.close();
        return list;
    }

    public ArrayList<Feedback> getSentFeedback(int sid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_FEEDBACK, null, null, null, null, null, "f_id");
        ArrayList<Feedback> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int s_id = c.getInt(c.getColumnIndex("s_id"));

            if(s_id == sid) {
                int f_id = c.getInt(c.getColumnIndex("f_id"));
                String f_title = c.getString(c.getColumnIndex("f_title"));
                String f_date = c.getString(c.getColumnIndex("f_date"));
                String f_sdate = c.getString(c.getColumnIndex("f_sdate"));
                String f_edate = c.getString(c.getColumnIndex("f_edate"));
                String f_message = c.getString(c.getColumnIndex("f_message"));
                int a_id = c.getInt(c.getColumnIndex("a_id"));
                int rec_id = c.getInt(c.getColumnIndex("rec_id"));
                int r_id = c.getInt(c.getColumnIndex("r_id"));

                list.add(new Feedback(f_id, f_title, f_date, f_sdate, f_edate, f_message, a_id, rec_id, s_id, r_id));
                break;
            }
        }

        c.close();
        db.close();
        return list;
    }

    public boolean hasUnclaimed(int aid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_FEEDBACK, null, null, null, null, null, "f_id");
        boolean hasUnclaimed = false;

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int a_id = c.getInt(c.getColumnIndex("a_id"));

            if(a_id == aid) {
                Date date = Calendar.getInstance().getTime();
                SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy", Locale.getDefault());
                String dateToday = sdf.format(date);

                String f_edate = c.getString(c.getColumnIndex("f_edate"));
                int s_id = c.getInt(c.getColumnIndex("s_id"));

                if(getSentStatus(s_id).equals("ACCEPTED")) {
                    if(f_edate.contains(dateToday)) {
                        hasUnclaimed = true;
                        break;
                    }
                }
            }
        }

        c.close();
        db.close();
        return hasUnclaimed;
    }

    public ArrayList<Feedback> getAllFeedback(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_FEEDBACK, null, null, null, null, null, "f_id");
        ArrayList<Feedback> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int f_id = c.getInt(c.getColumnIndex("f_id"));

            if(f_id == id) {
                String f_title = c.getString(c.getColumnIndex("f_title"));
                String f_date = c.getString(c.getColumnIndex("f_date"));
                String f_sdate = c.getString(c.getColumnIndex("f_sdate"));
                String f_edate = c.getString(c.getColumnIndex("f_edate"));
                String f_message = c.getString(c.getColumnIndex("f_message"));
                int a_id = c.getInt(c.getColumnIndex("a_id"));
                int rec_id = c.getInt(c.getColumnIndex("rec_id"));
                int s_id = c.getInt(c.getColumnIndex("s_id"));
                int r_id = c.getInt(c.getColumnIndex("r_id"));

                list.add(new Feedback(f_id, f_title, f_date, f_sdate, f_edate, f_message, a_id, rec_id, s_id, r_id));
            }
        }

        c.close();
        db.close();
        return list;
    }

    //***********************************************************************************************************
    // NOTIFICATION
    //***********************************************************************************************************
    public void addNotification(String not_type, String not_title, String not_date, String not_message, int a_id, int rec_id, int s_id, int r_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("not_type", not_type);
        cv.put("not_title", not_title);
        cv.put("not_date", not_date);
        cv.put("not_message", not_message);
        cv.put("a_id", a_id);
        cv.put("rec_id", rec_id);
        cv.put("s_id", s_id);
        cv.put("r_id", r_id);
        db.insert(TBL_NOTIFICATION, null, cv);

        db.close();
    }

    public ArrayList<Notification> getNotificationNOTID(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_NOTIFICATION, null, null, null, null, null, "not_id");
        ArrayList<Notification> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int not_id = c.getInt(c.getColumnIndex("not_id"));

            if(not_id == id) {
                String not_type = c.getString(c.getColumnIndex("not_type"));
                String not_title = c.getString(c.getColumnIndex("not_title"));
                String not_date = c.getString(c.getColumnIndex("not_date"));
                String not_message = c.getString(c.getColumnIndex("not_message"));
                int a_id = c.getInt(c.getColumnIndex("a_id"));
                int rec_id = c.getInt(c.getColumnIndex("rec_id"));
                int s_id = c.getInt(c.getColumnIndex("s_id"));
                int r_id = c.getInt(c.getColumnIndex("r_id"));

                list.add(new Notification(not_id, not_type, not_title, not_date, not_message, a_id, rec_id, s_id, r_id));
                break;
            }
        }

        c.close();
        db.close();
        return list;
    }

    public ArrayList<Notification> getAllNotificationAID(int aid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_NOTIFICATION, null, null, null, null, null, "not_date DESC");
        ArrayList<Notification> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int a_id = c.getInt(c.getColumnIndex("a_id"));

            if(a_id == aid) {
                int not_id = c.getInt(c.getColumnIndex("not_id"));
                String not_type = c.getString(c.getColumnIndex("not_type"));
                String not_title = c.getString(c.getColumnIndex("not_title"));
                String not_date = c.getString(c.getColumnIndex("not_date"));
                String not_message = c.getString(c.getColumnIndex("not_message"));
                int rec_id = c.getInt(c.getColumnIndex("rec_id"));
                int s_id = c.getInt(c.getColumnIndex("s_id"));
                int r_id = c.getInt(c.getColumnIndex("r_id"));

                list.add(new Notification(not_id, not_type, not_title, not_date, not_message, a_id, rec_id, s_id, r_id));
            }
        }

        c.close();
        db.close();
        return list;
    }

    public ArrayList<Notification> getAllNotificationRECID(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_NOTIFICATION, null, null, null, null, null, "not_date DESC");
        ArrayList<Notification> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int rec_id = c.getInt(c.getColumnIndex("rec_id"));

            if(rec_id == id) {
                int not_id = c.getInt(c.getColumnIndex("not_id"));
                String not_type = c.getString(c.getColumnIndex("not_type"));
                String not_title = c.getString(c.getColumnIndex("not_title"));
                String not_date = c.getString(c.getColumnIndex("not_date"));
                String not_message = c.getString(c.getColumnIndex("not_message"));
                int a_id = c.getInt(c.getColumnIndex("a_id"));
                int s_id = c.getInt(c.getColumnIndex("s_id"));
                int r_id = c.getInt(c.getColumnIndex("r_id"));

                list.add(new Notification(not_id, not_type, not_title, not_date, not_message, a_id, rec_id, s_id, r_id));
            }
        }

        c.close();
        db.close();
        return list;
    }

    //***********************************************************************************************************
    // PAYMENT
    //***********************************************************************************************************
    public void addPayment(String ir_date, String ir_method, String ir_payment, int a_id, int rec_id, int s_id, int r_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("ir_date", ir_date);
        cv.put("ir_method", ir_method);
        cv.put("ir_payment", ir_payment);
        cv.put("a_id", a_id);
        cv.put("rec_id", rec_id);
        cv.put("s_id", s_id);
        cv.put("r_id", r_id);
        db.insert(TBL_PAYMENT, null, cv);

        db.close();
    }

    public ArrayList<Payment> getPaymentAID(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_PAYMENT, null, null, null, null, null, "a_id");
        ArrayList<Payment> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int a_id = c.getInt(c.getColumnIndex("a_id"));

            if(a_id == id) {
                int ir_id = c.getInt(c.getColumnIndex("ir_id"));
                String ir_date = c.getString(c.getColumnIndex("ir_date"));
                String ir_method = c.getString(c.getColumnIndex("ir_method"));
                String ir_payment = c.getString(c.getColumnIndex("ir_payment"));
                int rec_id = c.getInt(c.getColumnIndex("rec_id"));
                int s_id = c.getInt(c.getColumnIndex("s_id"));
                int r_id = c.getInt(c.getColumnIndex("r_id"));

                list.add(new Payment(ir_id, ir_date, ir_method, ir_payment, a_id, rec_id, s_id, r_id));
                break;
            }
        }

        c.close();
        db.close();
        return list;
    }

    public ArrayList<Payment> getPaymentIRID(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_PAYMENT, null, null, null, null, null, "ir_id");
        ArrayList<Payment> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int ir_id = c.getInt(c.getColumnIndex("ir_id"));

            if(ir_id == id) {
                String ir_date = c.getString(c.getColumnIndex("ir_date"));
                String ir_method = c.getString(c.getColumnIndex("ir_method"));
                String ir_payment = c.getString(c.getColumnIndex("ir_payment"));
                int a_id = c.getInt(c.getColumnIndex("a_id"));
                int rec_id = c.getInt(c.getColumnIndex("rec_id"));
                int s_id = c.getInt(c.getColumnIndex("s_id"));
                int r_id = c.getInt(c.getColumnIndex("r_id"));

                list.add(new Payment(ir_id, ir_date, ir_method, ir_payment, a_id, rec_id, s_id, r_id));
                break;
            }
        }

        c.close();
        db.close();
        return list;
    }

    public ArrayList<Payment> getAllPayment(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_PAYMENT, null, null, null, null, null, "a_id");
        ArrayList<Payment> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int a_id = c.getInt(c.getColumnIndex("a_id"));

            if(a_id == id) {
                int ir_id = c.getInt(c.getColumnIndex("ir_id"));
                String ir_date = c.getString(c.getColumnIndex("ir_date"));
                String ir_method = c.getString(c.getColumnIndex("ir_method"));
                String ir_payment = c.getString(c.getColumnIndex("ir_payment"));
                int rec_id = c.getInt(c.getColumnIndex("rec_id"));
                int s_id = c.getInt(c.getColumnIndex("s_id"));
                int r_id = c.getInt(c.getColumnIndex("r_id"));

                list.add(new Payment(ir_id, ir_date, ir_method, ir_payment, a_id, rec_id, s_id, r_id));
            }
        }

        c.close();
        db.close();
        return list;
    }

    public ArrayList<Payment> getAllPayment() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_PAYMENT, null, null, null, null, null, "a_id");
        ArrayList<Payment> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int ir_id = c.getInt(c.getColumnIndex("ir_id"));
            String ir_date = c.getString(c.getColumnIndex("ir_date"));
            String ir_method = c.getString(c.getColumnIndex("ir_method"));
            String ir_payment = c.getString(c.getColumnIndex("ir_payment"));
            int a_id = c.getInt(c.getColumnIndex("a_id"));
            int rec_id = c.getInt(c.getColumnIndex("rec_id"));
            int s_id = c.getInt(c.getColumnIndex("s_id"));
            int r_id = c.getInt(c.getColumnIndex("r_id"));

            list.add(new Payment(ir_id, ir_date, ir_method, ir_payment, a_id, rec_id, s_id, r_id));
        }

        c.close();
        db.close();
        return list;
    }

    public int getTotalPayment() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_PAYMENT, null, null, null, null, null, "a_id");
        int total = 0;

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            String ir_payment = c.getString(c.getColumnIndex("ir_payment"));

            try {
                int payment = Integer.parseInt(ir_payment);
                total += payment;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        c.close();
        db.close();
        return total;
    }

    //***********************************************************************************************************
    // RATE
    //***********************************************************************************************************
    public int addRating(int a_id, int rec_id, int rep_id, int s_id, int r_id, float rate_rating, String rate_date, String rate_comment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        int res = 0;

        cv.put("a_id", a_id);
        cv.put("rec_id", rec_id);
        cv.put("rep_id", rep_id);
        cv.put("s_id", s_id);
        cv.put("r_id", r_id);
        cv.put("rate_rating", rate_rating);
        cv.put("rate_date", rate_date);
        cv.put("rate_comment", rate_comment);
        try {
            res = Integer.parseInt(db.insert(TBL_RATE, null, cv) + "");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        db.close();
        return res;
    }

    public void updateRating(int rate_id, float rate_rating, String rate_comment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("rate_rating", rate_rating);
        cv.put("rate_comment", rate_comment);
        db.update(TBL_RATE, cv, "rate_id=" +rate_id, null);

        db.close();
    }

    public ArrayList<Rating> getAllRating(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_RATE, null, null, null, null, null, "rec_id");
        ArrayList<Rating> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int rec_id = c.getInt(c.getColumnIndex("rec_id"));

            if(rec_id == id) {
                int rate_id = c.getInt(c.getColumnIndex("rate_id"));
                int a_id = c.getInt(c.getColumnIndex("a_id"));
                int rep_id = c.getInt(c.getColumnIndex("rep_id"));
                int s_id = c.getInt(c.getColumnIndex("s_id"));
                int r_id = c.getInt(c.getColumnIndex("r_id"));
                float rate_rating = c.getFloat(c.getColumnIndex("rate_rating"));
                String rate_date = c.getString(c.getColumnIndex("rate_date"));
                String rate_comment = c.getString(c.getColumnIndex("rate_comment"));

                list.add(new Rating(rate_id, a_id, rec_id, rep_id, s_id, r_id, rate_rating, rate_date, rate_comment));
            }
        }

        c.close();
        db.close();
        return list;
    }

    public float getRating(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_RATE, null, null, null, null, null, "rate_id");
        float total_rating = 0;

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int rate_id = c.getInt(c.getColumnIndex("rate_id"));

            if(rate_id == id) {
                total_rating = c.getFloat(c.getColumnIndex("rate_rating"));

                break;
            }
        }

        c.close();
        db.close();
        return total_rating;
    }

    public ArrayList<Rating> getRatingList(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_RATE, null, null, null, null, null, "rate_id");
        ArrayList<Rating> list = new ArrayList<>();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int rate_id = c.getInt(c.getColumnIndex("rate_id"));

            if(rate_id == id) {
                int rec_id = c.getInt(c.getColumnIndex("rec_id"));
                int a_id = c.getInt(c.getColumnIndex("a_id"));
                int rep_id = c.getInt(c.getColumnIndex("rep_id"));
                int s_id = c.getInt(c.getColumnIndex("s_id"));
                int r_id = c.getInt(c.getColumnIndex("r_id"));
                float rate_rating = c.getFloat(c.getColumnIndex("rate_rating"));
                String rate_date = c.getString(c.getColumnIndex("rate_date"));
                String rate_comment = c.getString(c.getColumnIndex("rate_comment"));

                list.add(new Rating(rate_id, a_id, rec_id, rep_id, s_id, r_id, rate_rating, rate_date, rate_comment));
                break;
            }
        }

        c.close();
        db.close();
        return list;
    }

    public float getAverageRating(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_RATE, null, null, null, null, null, "rec_id");
        float total_rating = 0;
        int size = 0;

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int rec_id = c.getInt(c.getColumnIndex("rec_id"));

            if(rec_id == id) {
                total_rating += c.getFloat(c.getColumnIndex("rate_rating"));
                size++;
            }
        }

        float averageRating;
        if(size != 0) {
            averageRating = total_rating / size;
        } else {
            averageRating = total_rating;
        }

        c.close();
        db.close();
        return averageRating;
    }

    //***********************************************************************************************************
    // TEMPLATE CONTENT
    //***********************************************************************************************************
    public int addTemplateContent(String c_name, String c_type, int c_leftMargin, int c_topMargin, int c_rightMargin, int c_bottomMargin, int c_id, int t_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        int res = 0;

        cv.put("c_name", c_name);
        cv.put("c_type", c_type);
        cv.put("c_leftMargin", c_leftMargin);
        cv.put("c_topMargin", c_topMargin);
        cv.put("c_rightMargin", c_rightMargin);
        cv.put("c_bottomMargin", c_bottomMargin);
        cv.put("c_id", c_id);
        cv.put("t_id", t_id);
        res = Integer.parseInt(db.insert(TBL_TEMPLATE_CONTENT, null, cv) +"");

        db.close();
        return res;
    }

    public void updateTemplateContent(int c_id, int c_leftMargin, int c_topMargin, int c_rightMargin, int c_bottomMargin, int t_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("c_leftMargin", c_leftMargin);
        cv.put("c_topMargin", c_topMargin);
        cv.put("c_rightMargin", c_rightMargin);
        cv.put("c_bottomMargin", c_bottomMargin);
        db.update(TBL_TEMPLATE_CONTENT, cv, "c_id=" +c_id+ " AND t_id=" +t_id, null);

        db.close();
    }

    public ArrayList<TemplateContent> getTemplateContent(int tid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_TEMPLATE_CONTENT, null, null, null, null ,null, "t_id");
        ArrayList<TemplateContent> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int t_id = c.getInt(c.getColumnIndex("t_id"));

            if(t_id == tid) {
                int tc_id = c.getInt(c.getColumnIndex("tc_id"));
                String c_name = c.getString(c.getColumnIndex("c_name"));
                String c_type = c.getString(c.getColumnIndex("c_type"));
                int c_leftMargin = c.getInt(c.getColumnIndex("c_leftMargin"));
                int c_topMargin = c.getInt(c.getColumnIndex("c_topMargin"));
                int c_rightMargin = c.getInt(c.getColumnIndex("c_rightMargin"));
                int c_bottomMargin = c.getInt(c.getColumnIndex("c_bottomMargin"));
                int c_id = c.getInt(c.getColumnIndex("c_id"));

                list.add(new TemplateContent(tc_id, c_name, c_type, c_leftMargin, c_topMargin, c_rightMargin, c_bottomMargin, c_id, t_id));
            }
        }

        db.close();
        c.close();
        return list;
    }

    public int getTemplateContentSize(int tid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_TEMPLATE_CONTENT, null, null, null, null ,null, "t_id");
        int size = 0;

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int t_id = c.getInt(c.getColumnIndex("t_id"));

            if(t_id == tid) {
                String c_type = c.getString(c.getColumnIndex("c_type"));
                if(!c_type.equals("image")) {
                    size++;
                }
            }
        }

        db.close();
        c.close();
        return size;
    }

    public int getTemplateContentImgSize(int tid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_TEMPLATE_CONTENT, null, null, null, null ,null, "t_id");
        int size = 0;

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int t_id = c.getInt(c.getColumnIndex("t_id"));

            if(t_id == tid) {
                String c_type = c.getString(c.getColumnIndex("c_type"));
                if(c_type.equals("image")) {
                    size++;
                }
            }
        }

        db.close();
        c.close();
        return size;
    }

    //***********************************************************************************************************
    // NEW TEMPLATE
    //***********************************************************************************************************
    public int addNewTemplate(String t_image, String t_title, String t_date, String t_type, String t_letterContent, int a_id, String t_status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        int res = 0;

        cv.put("t_title", t_title);
        cv.put("t_image", t_image);
        cv.put("t_date", t_date);
        cv.put("t_type", t_type);
        cv.put("t_letterContent", t_letterContent);
        cv.put("a_id", a_id);
        cv.put("t_status", t_status);
        res = Integer.parseInt(db.insert(TBL_NEW_TEMPLATE, null, cv) +"");

        db.close();
        return res;
    }

    public void updateNewTemplateImage(String t_image, int t_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("t_image", t_image);
        db.update(TBL_NEW_TEMPLATE, cv, "t_id=" +t_id, null);

        db.close();
    }

    public void updateNewTemplate(int t_id, String t_image, String t_letterContent) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("t_image", t_image);
        cv.put("t_letterContent", t_letterContent);
        db.update(TBL_NEW_TEMPLATE, cv, "t_id=" +t_id, null);

        db.close();
    }

    public void updateNewTemplateStatus(String t_status, int t_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("t_status", t_status);
        db.update(TBL_NEW_TEMPLATE, cv, "t_id=" +t_id, null);

        db.close();
    }

    public ArrayList<NewTemplate> getNewTemplate(int tid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_NEW_TEMPLATE, null, null, null, null ,null, "t_id");
        ArrayList<NewTemplate> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int t_id = c.getInt(c.getColumnIndex("t_id"));

            if(t_id == tid) {
                String t_title = c.getString(c.getColumnIndex("t_title"));
                String t_image = c.getString(c.getColumnIndex("t_image"));
                String t_date = c.getString(c.getColumnIndex("t_date"));
                String t_type = c.getString(c.getColumnIndex("t_type"));
                String t_letterContent = c.getString(c.getColumnIndex("t_letterContent"));
                int a_id = c.getInt(c.getColumnIndex("a_id"));
                String t_status = c.getString(c.getColumnIndex("t_status"));

                list.add(new NewTemplate(t_id, Uri.parse(t_image), t_title, t_date, t_type, t_letterContent, a_id, t_status));
                break;
            }
        }

        db.close();
        c.close();
        return list;
    }

    public ArrayList<NewTemplate> getAllNewTemplate(int aid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_NEW_TEMPLATE, null, null, null, null ,null, "t_date DESC");
        ArrayList<NewTemplate> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int a_id = c.getInt(c.getColumnIndex("a_id"));

            if(a_id == aid) {
                int t_id = c.getInt(c.getColumnIndex("t_id"));
                String t_title = c.getString(c.getColumnIndex("t_title"));
                String t_image = c.getString(c.getColumnIndex("t_image"));
                String t_date = c.getString(c.getColumnIndex("t_date"));
                String t_type = c.getString(c.getColumnIndex("t_type"));
                String t_letterContent = c.getString(c.getColumnIndex("t_letterContent"));
                String t_status = c.getString(c.getColumnIndex("t_status"));

                list.add(new NewTemplate(t_id, Uri.parse(t_image), t_title, t_date, t_type, t_letterContent, a_id, t_status));
            }
        }

        db.close();
        c.close();
        return list;
    }

    public ArrayList<NewTemplate> getAllNewTemplate(String sort) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_NEW_TEMPLATE, null, null, null, null ,null, "t_date DESC");
        ArrayList<NewTemplate> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            String t_status = c.getString(c.getColumnIndex("t_status"));

            if(TextUtils.equals(t_status, "PUBLIC")) {
                String t_type = c.getString(c.getColumnIndex("t_type"));

                if(TextUtils.equals(t_type, sort)) {
                    int t_id = c.getInt(c.getColumnIndex("t_id"));
                    String t_title = c.getString(c.getColumnIndex("t_title"));
                    String t_image = c.getString(c.getColumnIndex("t_image"));
                    String t_date = c.getString(c.getColumnIndex("t_date"));
                    String t_letterContent = c.getString(c.getColumnIndex("t_letterContent"));
                    int a_id = c.getInt(c.getColumnIndex("a_id"));

                    list.add(new NewTemplate(t_id, Uri.parse(t_image), t_title, t_date, t_type, t_letterContent, a_id, t_status));
                }
            }
        }

        db.close();
        c.close();
        return list;
    }

    //***********************************************************************************************************
    // TEMPLATE DETAILS
    //***********************************************************************************************************
    public int addTemplateDetails(String t_image, String t_title, String t_date, String t_type, int a_id, String t_status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        int res = 0;

        cv.put("t_title", t_title);
        cv.put("t_image", t_image);
        cv.put("t_date", t_date);
        cv.put("t_type", t_type);
        cv.put("a_id", a_id);
        cv.put("t_status", t_status);
        res = Integer.parseInt(db.insert(TBL_TEMPLATE_DETAILS, null, cv) +"");

        db.close();
        return res;
    }

    public void updateTemplateDetails(String t_image, int t_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("t_image", t_image);
        db.update(TBL_TEMPLATE_DETAILS, cv, "t_id=" +t_id, null);

        db.close();
    }

    public void updateTemplateDetailsStatus(String t_status, int t_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("t_status", t_status);
        db.update(TBL_TEMPLATE_DETAILS, cv, "t_id=" +t_id, null);

        db.close();
    }

    public ArrayList<TemplateDetails> getTemplateDetails(int tid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_TEMPLATE_DETAILS, null, null, null, null ,null, "t_id");
        ArrayList<TemplateDetails> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int t_id = c.getInt(c.getColumnIndex("t_id"));

            if(t_id == tid) {
                String t_title = c.getString(c.getColumnIndex("t_title"));
                String t_image = c.getString(c.getColumnIndex("t_image"));
                String t_date = c.getString(c.getColumnIndex("t_date"));
                String t_type = c.getString(c.getColumnIndex("t_type"));
                int a_id = c.getInt(c.getColumnIndex("a_id"));
                String t_status = c.getString(c.getColumnIndex("t_status"));

                list.add(new TemplateDetails(t_id, Uri.parse(t_image), t_title, t_date, t_type, a_id, t_status));
            }
        }

        db.close();
        c.close();
        return list;
    }

    public ArrayList<TemplateDetails> getAllTemplateDetails(int aid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_TEMPLATE_DETAILS, null, null, null, null ,null, "t_id");
        ArrayList<TemplateDetails> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int a_id = c.getInt(c.getColumnIndex("a_id"));

            if(a_id == aid) {
                String t_image = c.getString(c.getColumnIndex("t_image"));

                if(!t_image.equals("")) {
                    int t_id = c.getInt(c.getColumnIndex("t_id"));
                    String t_title = c.getString(c.getColumnIndex("t_title"));
                    String t_date = c.getString(c.getColumnIndex("t_date"));
                    String t_type = c.getString(c.getColumnIndex("t_type"));
                    String t_status = c.getString(c.getColumnIndex("t_status"));

                    list.add(new TemplateDetails(t_id, Uri.parse(t_image), t_title, t_date, t_type, a_id, t_status));
                }
            }
        }

        db.close();
        c.close();
        return list;
    }

    public ArrayList<TemplateDetails> getAllTemplateDetails() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_TEMPLATE_DETAILS, null, null, null, null ,null, "a_id");
        ArrayList<TemplateDetails> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            String t_image = c.getString(c.getColumnIndex("t_image"));

            if(!t_image.equals("")) {
                String t_status = c.getString(c.getColumnIndex("t_status"));

                if(t_status.equals("PUBLIC")) {
                    int t_id = c.getInt(c.getColumnIndex("t_id"));
                    String t_title = c.getString(c.getColumnIndex("t_title"));
                    String t_date = c.getString(c.getColumnIndex("t_date"));
                    String t_type = c.getString(c.getColumnIndex("t_type"));
                    int a_id = c.getInt(c.getColumnIndex("a_id"));

                    list.add(new TemplateDetails(t_id, Uri.parse(t_image), t_title, t_date, t_type, a_id, t_status));
                }
            }
        }

        db.close();
        c.close();
        return list;
    }

    //***********************************************************************************************************
    // LETTER CONTENT
    //***********************************************************************************************************
    public int addLetterContent(String c_name, String c_type, int c_leftMargin, int c_topMargin, int c_rightMargin, int c_bottomMargin, int c_id, int t_id, int a_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        int res = 0;

        cv.put("c_name", c_name);
        cv.put("c_type", c_type);
        cv.put("c_leftMargin", c_leftMargin);
        cv.put("c_topMargin", c_topMargin);
        cv.put("c_rightMargin", c_rightMargin);
        cv.put("c_bottomMargin", c_bottomMargin);
        cv.put("c_id", c_id);
        cv.put("t_id", t_id);
        cv.put("a_id", a_id);
        res = Integer.parseInt(db.insert(TBL_LETTER_CONTENT, null, cv) +"");

        db.close();
        return res;
    }

    public void updateLetterContentText(int c_id, String c_name, int t_id, int a_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("c_name", c_name);
        db.update(TBL_LETTER_CONTENT, cv, "c_id=" +c_id+ " AND t_id=" +t_id+ " AND a_id=" +a_id, null);

        db.close();
    }

    public int updateLetterContentTextLCID(int lc_id, String c_name, int t_id, int a_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        int res = 0;

        cv.put("c_name", c_name);
        res = db.update(TBL_LETTER_CONTENT, cv, "lc_id=" +lc_id, null);
//        res = db.update(TBL_LETTER_CONTENT, cv, "lc_id=" +lc_id+ " AND t_id=" +t_id+ " AND a_id=" +a_id, null);

        db.close();
        return res;
    }

    public void updateLetterContent(int c_id, int c_leftMargin, int c_topMargin, int c_rightMargin, int c_bottomMargin, int t_id, int a_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("c_leftMargin", c_leftMargin);
        cv.put("c_topMargin", c_topMargin);
        cv.put("c_rightMargin", c_rightMargin);
        cv.put("c_bottomMargin", c_bottomMargin);
        db.update(TBL_LETTER_CONTENT, cv, "t_id=" +t_id+ " AND a_id=" +a_id, null);

        db.close();
    }

    public ArrayList<LetterContent> getLetterContent(int tid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_LETTER_CONTENT, null, null, null, null ,null, "t_id");
        ArrayList<LetterContent> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int t_id = c.getInt(c.getColumnIndex("t_id"));

            if(t_id == tid) {
                int lc_id = c.getInt(c.getColumnIndex("lc_id"));
                String c_name = c.getString(c.getColumnIndex("c_name"));
                String c_type = c.getString(c.getColumnIndex("c_type"));
                int c_leftMargin = c.getInt(c.getColumnIndex("c_leftMargin"));
                int c_topMargin = c.getInt(c.getColumnIndex("c_topMargin"));
                int c_rightMargin = c.getInt(c.getColumnIndex("c_rightMargin"));
                int c_bottomMargin = c.getInt(c.getColumnIndex("c_bottomMargin"));
                int c_id = c.getInt(c.getColumnIndex("c_id"));
                int a_id = c.getInt(c.getColumnIndex("a_id"));

                list.add(new LetterContent(lc_id, c_name, c_type, c_leftMargin, c_topMargin, c_rightMargin, c_bottomMargin, c_id, t_id, a_id));
            }
        }

        db.close();
        c.close();
        return list;
    }

    public int getLetterContentSize(int tid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_LETTER_CONTENT, null, null, null, null ,null, "t_id");
        int size = 0;

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int t_id = c.getInt(c.getColumnIndex("t_id"));

            if(t_id == tid) {
                String c_type = c.getString(c.getColumnIndex("c_type"));
                if(c_type.equals("text")) {
                    size++;
                }
            }
        }

        db.close();
        c.close();
        return size;
    }

    public int getLetterContentImgSize(int tid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_LETTER_CONTENT, null, null, null, null ,null, "t_id");
        int size = 0;

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int t_id = c.getInt(c.getColumnIndex("t_id"));

            if(t_id == tid) {
                String c_type = c.getString(c.getColumnIndex("c_type"));
                if(c_type.equals("image")) {
                    size++;
                }
            }
        }

        db.close();
        c.close();
        return size;
    }

    public ArrayList<LetterContent> getLetterContentLCID(int lcid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_LETTER_CONTENT, null, null, null, null ,null, "c_id");
        ArrayList<LetterContent> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int lc_id = c.getInt(c.getColumnIndex("lc_id"));

            if(lc_id == lcid) {
                String c_name = c.getString(c.getColumnIndex("c_name"));
                String c_type = c.getString(c.getColumnIndex("c_type"));
                int c_leftMargin = c.getInt(c.getColumnIndex("c_leftMargin"));
                int c_topMargin = c.getInt(c.getColumnIndex("c_topMargin"));
                int c_rightMargin = c.getInt(c.getColumnIndex("c_rightMargin"));
                int c_bottomMargin = c.getInt(c.getColumnIndex("c_bottomMargin"));
                int c_id = c.getInt(c.getColumnIndex("c_id"));
                int t_id = c.getInt(c.getColumnIndex("t_id"));
                int a_id = c.getInt(c.getColumnIndex("a_id"));

                list.add(new LetterContent(lc_id, c_name, c_type, c_leftMargin, c_topMargin, c_rightMargin, c_bottomMargin, c_id, t_id, a_id));
                break;
            }
        }

        db.close();
        c.close();
        return list;
    }

    //***********************************************************************************************************
    // LETTER DETAILS
    //***********************************************************************************************************
    public int addLetterDetails(String t_image, String t_title, String t_date, String t_type, int t_id, int a_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        int res = 0;

        cv.put("t_title", t_title);
        cv.put("t_image", t_image);
        cv.put("t_date", t_date);
        cv.put("t_type", t_type);
        cv.put("t_id", t_id);
        cv.put("a_id", a_id);
        res = Integer.parseInt(db.insert(TBL_LETTER_DETAILS, null, cv) +"");

        db.close();
        return res;
    }

    public ArrayList<LetterDetails> getLetterDetails(int tid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_LETTER_DETAILS, null, null, null, null ,null, "t_id");
        ArrayList<LetterDetails> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int t_id = c.getInt(c.getColumnIndex("t_id"));

            if(t_id == tid) {
                int td_id = c.getInt(c.getColumnIndex("td_id"));
                String t_title = c.getString(c.getColumnIndex("t_title"));
                String t_image = c.getString(c.getColumnIndex("t_image"));
                String t_date = c.getString(c.getColumnIndex("t_date"));
                String t_type = c.getString(c.getColumnIndex("t_type"));
                int a_id = c.getInt(c.getColumnIndex("a_id"));

                list.add(new LetterDetails(td_id, Uri.parse(t_image), t_title, t_date, t_type, t_id, a_id));
                break;
            }
        }

        db.close();
        c.close();
        return list;
    }

    public ArrayList<LetterDetails> getAllLetterDetails(int aid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_LETTER_DETAILS, null, null, null, null ,null, "a_id");
        ArrayList<LetterDetails> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int a_id = c.getInt(c.getColumnIndex("a_id"));

            if(a_id == aid) {
                int td_id = c.getInt(c.getColumnIndex("td_id"));
                String t_image = c.getString(c.getColumnIndex("t_image"));
                String t_title = c.getString(c.getColumnIndex("t_title"));
                String t_date = c.getString(c.getColumnIndex("t_date"));
                String t_type = c.getString(c.getColumnIndex("t_type"));
                int t_id = c.getInt(c.getColumnIndex("t_id"));

                list.add(new LetterDetails(td_id, Uri.parse(t_image), t_title, t_date, t_type, t_id, a_id));
            }
        }

        db.close();
        c.close();
        return list;
    }

    public ArrayList<LetterDetails> getAllLetterDetails() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_LETTER_DETAILS, null, null, null, null ,null, "a_id");
        ArrayList<LetterDetails> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int td_id = c.getInt(c.getColumnIndex("td_id"));
            String t_image = c.getString(c.getColumnIndex("t_image"));
            String t_title = c.getString(c.getColumnIndex("t_title"));
            String t_date = c.getString(c.getColumnIndex("t_date"));
            String t_type = c.getString(c.getColumnIndex("t_type"));
            int t_id = c.getInt(c.getColumnIndex("t_id"));
            int a_id = c.getInt(c.getColumnIndex("a_id"));

            list.add(new LetterDetails(td_id, Uri.parse(t_image), t_title, t_date, t_type, t_id, a_id));
        }

        db.close();
        c.close();
        return list;
    }

    //***********************************************************************************************************
    // MAIN CONTENT
    //***********************************************************************************************************
    public int addMainContent(String mc_name, String mc_type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        int res = 0;

        cv.put("mc_name", mc_name);
        cv.put("mc_type", mc_type);
        res = Integer.parseInt(db.insert(TBL_MAIN_CONTENT, null, cv) +"");

        db.close();
        return res;
    }

    public void updateMainContent(int mc_id, String mc_name, String mc_type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("mc_name", mc_name);
        cv.put("mc_type", mc_type);
        db.update(TBL_MAIN_CONTENT, cv, "mc_id=" +mc_id, null);

        db.close();
    }

    public ArrayList<MainContent> getAllMainContent() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_MAIN_CONTENT, null, null, null, null ,null, "mc_id");
        ArrayList<MainContent> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int mc_id = c.getInt(c.getColumnIndex("mc_id"));
            String mc_name = c.getString(c.getColumnIndex("mc_name"));
            String mc_type = c.getString(c.getColumnIndex("mc_type"));

            list.add(new MainContent(mc_id, mc_name, mc_type));
        }

        db.close();
        c.close();
        return list;
    }

    public ArrayList<MainContent> getMainContent(int mcid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_MAIN_CONTENT, null, null, null, null ,null, "mc_id");
        ArrayList<MainContent> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int mc_id = c.getInt(c.getColumnIndex("mc_id"));

            if(mc_id == mcid) {
                String mc_name = c.getString(c.getColumnIndex("mc_name"));
                String mc_type = c.getString(c.getColumnIndex("mc_type"));

                list.add(new MainContent(mc_id, mc_name, mc_type));
                break;
            }
        }

        db.close();
        c.close();
        return list;
    }

    //***********************************************************************************************************
    // LETTER SENT CONTENT
    //***********************************************************************************************************
    public int addLetterSentContent(String c_name, String c_type, int c_leftMargin, int c_topMargin, int c_rightMargin, int c_bottomMargin, int c_id, int t_id, int a_id, int s_id, int r_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        int res = 0;

        cv.put("c_name", c_name);
        cv.put("c_type", c_type);
        cv.put("c_leftMargin", c_leftMargin);
        cv.put("c_topMargin", c_topMargin);
        cv.put("c_rightMargin", c_rightMargin);
        cv.put("c_bottomMargin", c_bottomMargin);
        cv.put("c_id", c_id);
        cv.put("t_id", t_id);
        cv.put("a_id", a_id);
        cv.put("s_id", s_id);
        cv.put("r_id", r_id);
        res = Integer.parseInt(db.insert(TBL_LETTER_SENT_CONTENT, null, cv) +"");

        db.close();
        return res;
    }

    public ArrayList<LetterSentContent> getLetterSentContent(int tid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_LETTER_SENT_CONTENT, null, null, null, null ,null, "t_id");
        ArrayList<LetterSentContent> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int t_id = c.getInt(c.getColumnIndex("t_id"));

            if(t_id == tid) {
                int lc_id = c.getInt(c.getColumnIndex("lc_id"));
                String c_name = c.getString(c.getColumnIndex("c_name"));
                String c_type = c.getString(c.getColumnIndex("c_type"));
                int c_leftMargin = c.getInt(c.getColumnIndex("c_leftMargin"));
                int c_topMargin = c.getInt(c.getColumnIndex("c_topMargin"));
                int c_rightMargin = c.getInt(c.getColumnIndex("c_rightMargin"));
                int c_bottomMargin = c.getInt(c.getColumnIndex("c_bottomMargin"));
                int c_id = c.getInt(c.getColumnIndex("c_id"));
                int a_id = c.getInt(c.getColumnIndex("a_id"));
                int s_id = c.getInt(c.getColumnIndex("s_id"));
                int r_id = c.getInt(c.getColumnIndex("r_id"));

                list.add(new LetterSentContent(lc_id, c_name, c_type, c_leftMargin, c_topMargin, c_rightMargin, c_bottomMargin, c_id, t_id, a_id, s_id, r_id));
            }
        }

        db.close();
        c.close();
        return list;
    }

    public ArrayList<LetterSentContent> getLetterSentContentSID(int sid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_LETTER_SENT_CONTENT, null, null, null, null ,null, "s_id");
        ArrayList<LetterSentContent> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int s_id = c.getInt(c.getColumnIndex("s_id"));

            if(s_id == sid) {
                int lc_id = c.getInt(c.getColumnIndex("lc_id"));
                String c_name = c.getString(c.getColumnIndex("c_name"));
                String c_type = c.getString(c.getColumnIndex("c_type"));
                int c_leftMargin = c.getInt(c.getColumnIndex("c_leftMargin"));
                int c_topMargin = c.getInt(c.getColumnIndex("c_topMargin"));
                int c_rightMargin = c.getInt(c.getColumnIndex("c_rightMargin"));
                int c_bottomMargin = c.getInt(c.getColumnIndex("c_bottomMargin"));
                int c_id = c.getInt(c.getColumnIndex("c_id"));
                int t_id = c.getInt(c.getColumnIndex("t_id"));
                int a_id = c.getInt(c.getColumnIndex("a_id"));
                int r_id = c.getInt(c.getColumnIndex("r_id"));

                list.add(new LetterSentContent(lc_id, c_name, c_type, c_leftMargin, c_topMargin, c_rightMargin, c_bottomMargin, c_id, t_id, a_id, s_id, r_id));
            }
        }

        db.close();
        c.close();
        return list;
    }

    public ArrayList<LetterSentContent> getLetterSentContentCID(int cid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_LETTER_SENT_CONTENT, null, null, null, null ,null, "c_id");
        ArrayList<LetterSentContent> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int c_id = c.getInt(c.getColumnIndex("c_id"));

            if(c_id == cid) {
                int lc_id = c.getInt(c.getColumnIndex("lc_id"));
                String c_name = c.getString(c.getColumnIndex("c_name"));
                String c_type = c.getString(c.getColumnIndex("c_type"));
                int c_leftMargin = c.getInt(c.getColumnIndex("c_leftMargin"));
                int c_topMargin = c.getInt(c.getColumnIndex("c_topMargin"));
                int c_rightMargin = c.getInt(c.getColumnIndex("c_rightMargin"));
                int c_bottomMargin = c.getInt(c.getColumnIndex("c_bottomMargin"));
                int t_id = c.getInt(c.getColumnIndex("t_id"));
                int a_id = c.getInt(c.getColumnIndex("a_id"));
                int s_id = c.getInt(c.getColumnIndex("s_id"));
                int r_id = c.getInt(c.getColumnIndex("r_id"));

                list.add(new LetterSentContent(lc_id, c_name, c_type, c_leftMargin, c_topMargin, c_rightMargin, c_bottomMargin, c_id, t_id, a_id, s_id, r_id));
                break;
            }
        }

        db.close();
        c.close();
        return list;
    }

    //***********************************************************************************************************
    // LETTER SENT DETAILS
    //***********************************************************************************************************
    public int addLetterSentDetails(String t_image, String t_title, String t_date, String t_type, int t_id, int a_id, int s_id, int r_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        int res = 0;

        cv.put("t_title", t_title);
        cv.put("t_image", t_image);
        cv.put("t_date", t_date);
        cv.put("t_type", t_type);
        cv.put("t_id", t_id);
        cv.put("a_id", a_id);
        cv.put("s_id", s_id);
        cv.put("r_id", r_id);
        res = Integer.parseInt(db.insert(TBL_LETTER_SENT_DETAILS, null, cv) +"");

        db.close();
        return res;
    }

    public ArrayList<LetterSentDetails> getLetterSentDetails(int tid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_LETTER_SENT_DETAILS, null, null, null, null ,null, "t_id");
        ArrayList<LetterSentDetails> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int t_id = c.getInt(c.getColumnIndex("t_id"));

            if(t_id == tid) {
                int td_id = c.getInt(c.getColumnIndex("td_id"));
                String t_title = c.getString(c.getColumnIndex("t_title"));
                String t_image = c.getString(c.getColumnIndex("t_image"));
                String t_date = c.getString(c.getColumnIndex("t_date"));
                String t_type = c.getString(c.getColumnIndex("t_type"));
                int a_id = c.getInt(c.getColumnIndex("a_id"));
                int s_id = c.getInt(c.getColumnIndex("s_id"));
                int r_id = c.getInt(c.getColumnIndex("r_id"));

                list.add(new LetterSentDetails(td_id, Uri.parse(t_image), t_title, t_date, t_type, t_id, a_id, s_id, r_id));
                break;
            }
        }

        db.close();
        c.close();
        return list;
    }

    public ArrayList<LetterSentDetails> getLetterSentDetailsSID(int sid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_LETTER_SENT_DETAILS, null, null, null, null ,null, "s_id");
        ArrayList<LetterSentDetails> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int s_id = c.getInt(c.getColumnIndex("s_id"));

            Log.d("s_id: ", ""+s_id);
            Log.d("sid: ", ""+sid);

            if(s_id == sid) {
                int td_id = c.getInt(c.getColumnIndex("td_id"));
                String t_title = c.getString(c.getColumnIndex("t_title"));
                String t_image = c.getString(c.getColumnIndex("t_image"));
                String t_date = c.getString(c.getColumnIndex("t_date"));
                String t_type = c.getString(c.getColumnIndex("t_type"));
                int t_id = c.getInt(c.getColumnIndex("t_id"));
                int a_id = c.getInt(c.getColumnIndex("a_id"));
                int r_id = c.getInt(c.getColumnIndex("r_id"));

                list.add(new LetterSentDetails(td_id, Uri.parse(t_image), t_title, t_date, t_type, t_id, a_id, s_id, r_id));
                break;
            }
        }

        db.close();
        c.close();
        return list;
    }

    public ArrayList<LetterSentDetails> getAllLetterSentDetails() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_LETTER_SENT_DETAILS, null, null, null, null ,null, "a_id");
        ArrayList<LetterSentDetails> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int td_id = c.getInt(c.getColumnIndex("td_id"));
            String t_image = c.getString(c.getColumnIndex("t_image"));
            String t_title = c.getString(c.getColumnIndex("t_title"));
            String t_date = c.getString(c.getColumnIndex("t_date"));
            String t_type = c.getString(c.getColumnIndex("t_type"));
            int t_id = c.getInt(c.getColumnIndex("t_id"));
            int a_id = c.getInt(c.getColumnIndex("a_id"));
            int s_id = c.getInt(c.getColumnIndex("s_id"));
            int r_id = c.getInt(c.getColumnIndex("r_id"));

            list.add(new LetterSentDetails(td_id, Uri.parse(t_image), t_title, t_date, t_type, t_id, a_id, s_id, r_id));
        }

        db.close();
        c.close();
        return list;
    }

    //***********************************************************************************************************
    // LETTER RECEIVE CONTENT
    //***********************************************************************************************************
    public int addLetterReceiveContent(String c_name, String c_type, int c_leftMargin, int c_topMargin, int c_rightMargin, int c_bottomMargin, int c_id, int t_id, int a_id, int s_id, int r_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        int res = 0;

        cv.put("c_name", c_name);
        cv.put("c_type", c_type);
        cv.put("c_leftMargin", c_leftMargin);
        cv.put("c_topMargin", c_topMargin);
        cv.put("c_rightMargin", c_rightMargin);
        cv.put("c_bottomMargin", c_bottomMargin);
        cv.put("c_id", c_id);
        cv.put("t_id", t_id);
        cv.put("a_id", a_id);
        cv.put("s_id", s_id);
        cv.put("r_id", r_id);
        res = Integer.parseInt(db.insert(TBL_LETTER_RECEIVE_CONTENT, null, cv) +"");

        db.close();
        return res;
    }

    public ArrayList<LetterReceiveContent> getLetterReceiveContent(int tid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_LETTER_RECEIVE_CONTENT, null, null, null, null ,null, "t_id");
        ArrayList<LetterReceiveContent> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int t_id = c.getInt(c.getColumnIndex("t_id"));

            if(t_id == tid) {
                int lc_id = c.getInt(c.getColumnIndex("lc_id"));
                int c_id = c.getInt(c.getColumnIndex("c_id"));
                String c_name = c.getString(c.getColumnIndex("c_name"));
                String c_type = c.getString(c.getColumnIndex("c_type"));
                int c_leftMargin = c.getInt(c.getColumnIndex("c_leftMargin"));
                int c_topMargin = c.getInt(c.getColumnIndex("c_topMargin"));
                int c_rightMargin = c.getInt(c.getColumnIndex("c_rightMargin"));
                int c_bottomMargin = c.getInt(c.getColumnIndex("c_bottomMargin"));
                int a_id = c.getInt(c.getColumnIndex("a_id"));
                int s_id = c.getInt(c.getColumnIndex("s_id"));
                int r_id = c.getInt(c.getColumnIndex("r_id"));

                list.add(new LetterReceiveContent(lc_id, c_name, c_type, c_leftMargin, c_topMargin, c_rightMargin, c_bottomMargin, c_id, t_id, a_id, s_id, r_id));
            }
        }

        db.close();
        c.close();
        return list;
    }

    public ArrayList<LetterReceiveContent> getLetterReceiveContentRID(int rid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_LETTER_RECEIVE_CONTENT, null, null, null, null ,null, "t_id");
        ArrayList<LetterReceiveContent> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int r_id = c.getInt(c.getColumnIndex("r_id"));

            if(r_id == rid) {
                int lc_id = c.getInt(c.getColumnIndex("lc_id"));
                String c_name = c.getString(c.getColumnIndex("c_name"));
                String c_type = c.getString(c.getColumnIndex("c_type"));
                int c_leftMargin = c.getInt(c.getColumnIndex("c_leftMargin"));
                int c_topMargin = c.getInt(c.getColumnIndex("c_topMargin"));
                int c_rightMargin = c.getInt(c.getColumnIndex("c_rightMargin"));
                int c_bottomMargin = c.getInt(c.getColumnIndex("c_bottomMargin"));
                int c_id = c.getInt(c.getColumnIndex("c_id"));
                int t_id = c.getInt(c.getColumnIndex("t_id"));
                int a_id = c.getInt(c.getColumnIndex("a_id"));
                int s_id = c.getInt(c.getColumnIndex("s_id"));

                list.add(new LetterReceiveContent(lc_id, c_name, c_type, c_leftMargin, c_topMargin, c_rightMargin, c_bottomMargin, c_id, t_id, a_id, s_id, r_id));
            }
        }

        db.close();
        c.close();
        return list;
    }

    public ArrayList<LetterReceiveContent> getLetterReceiveContentCID(int cid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_LETTER_RECEIVE_CONTENT, null, null, null, null ,null, "c_id");
        ArrayList<LetterReceiveContent> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int c_id = c.getInt(c.getColumnIndex("c_id"));

            if(c_id == cid) {
                int lc_id = c.getInt(c.getColumnIndex("lc_id"));
                String c_name = c.getString(c.getColumnIndex("c_name"));
                String c_type = c.getString(c.getColumnIndex("c_type"));
                int c_leftMargin = c.getInt(c.getColumnIndex("c_leftMargin"));
                int c_topMargin = c.getInt(c.getColumnIndex("c_topMargin"));
                int c_rightMargin = c.getInt(c.getColumnIndex("c_rightMargin"));
                int c_bottomMargin = c.getInt(c.getColumnIndex("c_bottomMargin"));
                int t_id = c.getInt(c.getColumnIndex("t_id"));
                int a_id = c.getInt(c.getColumnIndex("a_id"));
                int s_id = c.getInt(c.getColumnIndex("s_id"));
                int r_id = c.getInt(c.getColumnIndex("r_id"));

                list.add(new LetterReceiveContent(lc_id, c_name, c_type, c_leftMargin, c_topMargin, c_rightMargin, c_bottomMargin, c_id, t_id, a_id, s_id, r_id));
                break;
            }
        }

        db.close();
        c.close();
        return list;
    }

    public ArrayList<LetterReceiveContent> getLetterReceiveContentLCID(int lcid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_LETTER_RECEIVE_CONTENT, null, null, null, null ,null, "lc_id");
        ArrayList<LetterReceiveContent> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int lc_id = c.getInt(c.getColumnIndex("lc_id"));

            if(lc_id == lcid) {
                String c_name = c.getString(c.getColumnIndex("c_name"));
                String c_type = c.getString(c.getColumnIndex("c_type"));
                int c_leftMargin = c.getInt(c.getColumnIndex("c_leftMargin"));
                int c_topMargin = c.getInt(c.getColumnIndex("c_topMargin"));
                int c_rightMargin = c.getInt(c.getColumnIndex("c_rightMargin"));
                int c_bottomMargin = c.getInt(c.getColumnIndex("c_bottomMargin"));
                int c_id = c.getInt(c.getColumnIndex("c_id"));
                int t_id = c.getInt(c.getColumnIndex("t_id"));
                int a_id = c.getInt(c.getColumnIndex("a_id"));
                int s_id = c.getInt(c.getColumnIndex("s_id"));
                int r_id = c.getInt(c.getColumnIndex("r_id"));

                list.add(new LetterReceiveContent(lc_id, c_name, c_type, c_leftMargin, c_topMargin, c_rightMargin, c_bottomMargin, c_id, t_id, a_id, s_id, r_id));
                break;
            }
        }

        db.close();
        c.close();
        return list;
    }

    //***********************************************************************************************************
    // LETTER SENT DETAILS
    //***********************************************************************************************************
    public int addLetterReceiveDetails(String t_image, String t_title, String t_date, String t_type, int t_id, int a_id, int s_id, int r_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        int res = 0;

        cv.put("t_title", t_title);
        cv.put("t_image", t_image);
        cv.put("t_date", t_date);
        cv.put("t_type", t_type);
        cv.put("t_id", t_id);
        cv.put("a_id", a_id);
        cv.put("s_id", s_id);
        cv.put("r_id", r_id);
        res = Integer.parseInt(db.insert(TBL_LETTER_RECEIVE_DETAILS, null, cv) +"");

        db.close();
        return res;
    }

    public ArrayList<LetterReceiveDetails> getLetterReceiveDetails(int tid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_LETTER_RECEIVE_DETAILS, null, null, null, null ,null, "t_id");
        ArrayList<LetterReceiveDetails> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int t_id = c.getInt(c.getColumnIndex("t_id"));

            if(t_id == tid) {
                int td_id = c.getInt(c.getColumnIndex("td_id"));
                String t_title = c.getString(c.getColumnIndex("t_title"));
                String t_image = c.getString(c.getColumnIndex("t_image"));
                String t_date = c.getString(c.getColumnIndex("t_date"));
                String t_type = c.getString(c.getColumnIndex("t_type"));
                int a_id = c.getInt(c.getColumnIndex("a_id"));
                int s_id = c.getInt(c.getColumnIndex("s_id"));
                int r_id = c.getInt(c.getColumnIndex("r_id"));

                list.add(new LetterReceiveDetails(td_id, Uri.parse(t_image), t_title, t_date, t_type, t_id, a_id, s_id, r_id));
                break;
            }
        }

        db.close();
        c.close();
        return list;
    }

    public ArrayList<LetterReceiveDetails> getLetterReceiveDetailsRID(int rid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_LETTER_RECEIVE_DETAILS, null, null, null, null ,null, "t_id");
        ArrayList<LetterReceiveDetails> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int r_id = c.getInt(c.getColumnIndex("r_id"));

            if(r_id == rid) {
                int td_id = c.getInt(c.getColumnIndex("td_id"));
                String t_title = c.getString(c.getColumnIndex("t_title"));
                String t_image = c.getString(c.getColumnIndex("t_image"));
                String t_date = c.getString(c.getColumnIndex("t_date"));
                String t_type = c.getString(c.getColumnIndex("t_type"));
                int t_id = c.getInt(c.getColumnIndex("t_id"));
                int a_id = c.getInt(c.getColumnIndex("a_id"));
                int s_id = c.getInt(c.getColumnIndex("s_id"));

                list.add(new LetterReceiveDetails(td_id, Uri.parse(t_image), t_title, t_date, t_type, t_id, a_id, s_id, r_id));
                break;
            }
        }

        db.close();
        c.close();
        return list;
    }

    public ArrayList<LetterReceiveDetails> getAllLetterReceiveDetails() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_LETTER_RECEIVE_DETAILS, null, null, null, null ,null, "a_id");
        ArrayList<LetterReceiveDetails> list = new ArrayList<>();
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int td_id = c.getInt(c.getColumnIndex("td_id"));
            String t_image = c.getString(c.getColumnIndex("t_image"));
            String t_title = c.getString(c.getColumnIndex("t_title"));
            String t_date = c.getString(c.getColumnIndex("t_date"));
            String t_type = c.getString(c.getColumnIndex("t_type"));
            int t_id = c.getInt(c.getColumnIndex("t_id"));
            int a_id = c.getInt(c.getColumnIndex("a_id"));
            int s_id = c.getInt(c.getColumnIndex("s_id"));
            int r_id = c.getInt(c.getColumnIndex("r_id"));

            list.add(new LetterReceiveDetails(td_id, Uri.parse(t_image), t_title, t_date, t_type, t_id, a_id, s_id, r_id));
        }

        db.close();
        c.close();
        return list;
    }

    // ***********************************************************************************************************
    // DELETES
    //***********************************************************************************************************
    public void deleteMainContent(int mc_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TBL_MAIN_CONTENT, "mc_id=" +mc_id, null);
        db.close();
    }

    public void deleteTemplateContentTID(int t_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlDeleteMC = "DELETE FROM " +TBL_TEMPLATE_CONTENT+ " WHERE t_id=" +t_id;
        db.execSQL(sqlDeleteMC);
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TBL_MAIN_CONTENT, "t_id=?", new int[]{t_id});
//        db.close();
    }

    public void deleteTemplate(int t_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TBL_TEMPLATE_DETAILS, "t_id=" +t_id, null);
        db.close();
    }

    public void deleteNewTemplate(int t_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TBL_NEW_TEMPLATE, "t_id=" +t_id, null);
        db.close();
    }

    public void deleteDraft(int t_id, int a_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TBL_DRAFT, "t_id=" +t_id+ " AND a_id=" +a_id, null);
        db.close();
    }

    public int deleteRep(int a_id, int rep_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int res = db.delete(TBL_REPRESENTATIVES, "a_id=" +a_id+ " AND rep_id=" +rep_id, null);
        db.close();

        return res;
    }

    public int deleteRepId(int a_id, int rep_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int res = db.delete(TBL_REPID, "a_id=" +a_id+ " AND rep_id=" +rep_id, null);
        db.close();

        return res;
    }

    public void deleteLoginUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TBL_LOGIN_USER, null, null);

        db.close();
    }

    public void deleteAllUserRating() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TBL_USER_RATING, null, null);

        db.close();
    }

    public void deleteAllPastRecipient() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TBL_PAST_RECIPIENT, null, null);

        db.close();
    }

    public void deleteAllNewDraft() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TBL_NEW_DRAFT, null, null);

        db.close();
    }

    public void deleteFeedback(int f_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TBL_FEEDBACK, "f_id=" +f_id, null);
        db.close();
    }

    public void deleteNotification(int not_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TBL_NOTIFICATION, "not_id=" +not_id, null);
        db.close();
    }

    public void deleteAcTemplate(int a_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TBL_AC_TEMPLATE, "a_id=" +a_id, null);
        db.close();
    }

    public void deleteSent(int s_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TBL_SENT, "s_id=" +s_id, null);
        db.close();
    }

    public void deleteReceive(int r_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TBL_RECEIVE, "r_id=" +r_id, null);
        db.close();
    }

    public int deleteLetterDetails(int t_id, int a_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int res = db.delete(TBL_LETTER_DETAILS, "t_id=" +t_id+ " AND a_id=" +a_id, null);
        db.close();

        return res;
    }

    public void deleteOpeningTag(String ot_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TBL_OPENING_TAG, "ot_name='" +ot_name+ "';", null);
        db.close();
    }

    public void deleteClosingTag(String ct_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TBL_CLOSING_TAG, "ct_name='" +ct_name+ "';", null);
        db.close();
    }

    public void deleteLetterContent(int t_id, int a_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TBL_LETTER_CONTENT, "t_id=" +t_id+ " AND a_id=" +a_id, null);
        db.close();
    }

    /*public int deleteLetterContent(int t_id, int a_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int res = db.delete(TBL_LETTER_CONTENT, "t_id=" +t_id+ " AND a_id=" +a_id, null);
        db.close();

        return res;
    }*/

    /*public int deleteContact(String phone){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TBL_CONTACT, "phone=?", new String[]{phone});
    }*/


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "message" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){
            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }
    }
}