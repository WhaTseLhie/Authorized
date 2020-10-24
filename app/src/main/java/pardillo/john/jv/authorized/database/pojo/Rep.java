package pardillo.john.jv.authorized.database.pojo;

import android.net.Uri;

public class Rep {

    private int rep_id;
    private Uri rep_image;
    private String rep_fname, rep_lname, rep_contact, rep_gender, rep_identification;
    private int a_id;

    public Rep(int rep_id, Uri rep_image, String rep_fname, String rep_lname, String rep_contact, String rep_gender, String rep_identification, int a_id) {
        this.rep_id = rep_id;
        this.rep_image = rep_image;
        this.rep_fname = rep_fname;
        this.rep_lname = rep_lname;
        this.rep_contact = rep_contact;
        this.rep_gender = rep_gender;
        this.rep_identification = rep_identification;
        this.a_id = a_id;
    }

    public int getRep_id() {
        return rep_id;
    }

    public void setRep_id(int rep_id) {
        this.rep_id = rep_id;
    }

    public Uri getRep_image() {
        return rep_image;
    }

    public void setRep_image(Uri rep_image) {
        this.rep_image = rep_image;
    }

    public String getRep_fname() {
        return rep_fname;
    }

    public void setRep_fname(String rep_fname) {
        this.rep_fname = rep_fname;
    }

    public String getRep_lname() {
        return rep_lname;
    }

    public void setRep_lname(String rep_lname) {
        this.rep_lname = rep_lname;
    }

    public String getRep_contact() {
        return rep_contact;
    }

    public void setRep_contact(String rep_contact) {
        this.rep_contact = rep_contact;
    }

    public String getRep_gender() {
        return rep_gender;
    }

    public void setRep_gender(String rep_gender) {
        this.rep_gender = rep_gender;
    }

    public String getRep_identification() {
        return rep_identification;
    }

    public void setRep_identification(String rep_identification) {
        this.rep_identification = rep_identification;
    }

    public int getA_id() {
        return a_id;
    }

    public void setA_id(int a_id) {
        this.a_id = a_id;
    }
}