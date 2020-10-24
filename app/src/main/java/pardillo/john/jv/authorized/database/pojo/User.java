package pardillo.john.jv.authorized.database.pojo;

import android.net.Uri;

public class User {

    private int a_id;
    private Uri a_image;
    private String a_fname, a_lname, a_contact, a_gender, a_username, a_password, a_signature, a_status;

    public User(int a_id, Uri a_image, String a_fname, String a_lname, String a_contact, String a_gender, String a_username, String a_password, String a_signature, String a_status) {
        this.a_id = a_id;
        this.a_image = a_image;
        this.a_fname = a_fname;
        this.a_lname = a_lname;
        this.a_contact = a_contact;
        this.a_gender = a_gender;
        this.a_username = a_username;
        this.a_password = a_password;
        this.a_signature = a_signature;
        this.a_status = a_status;
    }

    public int getA_id() {
        return a_id;
    }

    public void setA_id(int a_id) {
        this.a_id = a_id;
    }

    public Uri getA_image() {
        return a_image;
    }

    public void setA_image(Uri a_image) {
        this.a_image = a_image;
    }

    public String getA_fname() {
        return a_fname;
    }

    public void setA_fname(String a_fname) {
        this.a_fname = a_fname;
    }

    public String getA_lname() {
        return a_lname;
    }

    public void setA_lname(String a_lname) {
        this.a_lname = a_lname;
    }

    public String getA_contact() {
        return a_contact;
    }

    public void setA_contact(String a_contact) {
        this.a_contact = a_contact;
    }

    public String getA_gender() {
        return a_gender;
    }

    public void setA_gender(String a_gender) {
        this.a_gender = a_gender;
    }

    public String getA_username() {
        return a_username;
    }

    public void setA_username(String a_username) {
        this.a_username = a_username;
    }

    public String getA_password() {
        return a_password;
    }

    public void setA_password(String a_password) {
        this.a_password = a_password;
    }

    public String getA_signature() {
        return a_signature;
    }

    public void setA_signature(String a_signature) {
        this.a_signature = a_signature;
    }

    public String getA_status() {
        return a_status;
    }

    public void setA_status(String a_status) {
        this.a_status = a_status;
    }
}