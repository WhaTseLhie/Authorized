package pardillo.john.jv.authorized.database.pojo;

import android.net.Uri;

public class LetterReceiveDetails {

    private int td_id;
    private Uri t_image;
    private String t_title, t_date, t_type;
    private int t_id, a_id, s_id, r_id;

    public LetterReceiveDetails(int td_id, Uri t_image, String t_title, String t_date, String t_type, int t_id, int a_id, int s_id, int r_id) {
        this.td_id = td_id;
        this.t_image = t_image;
        this.t_title = t_title;
        this.t_date = t_date;
        this.t_type = t_type;
        this.t_id = t_id;
        this.a_id = a_id;
        this.s_id = s_id;
        this.r_id = r_id;
    }

    public int getTd_id() {
        return td_id;
    }

    public void setTd_id(int td_id) {
        this.td_id = td_id;
    }

    public Uri getT_image() {
        return t_image;
    }

    public void setT_image(Uri t_image) {
        this.t_image = t_image;
    }

    public String getT_title() {
        return t_title;
    }

    public void setT_title(String t_title) {
        this.t_title = t_title;
    }

    public String getT_date() {
        return t_date;
    }

    public void setT_date(String t_date) {
        this.t_date = t_date;
    }

    public String getT_type() {
        return t_type;
    }

    public void setT_type(String t_type) {
        this.t_type = t_type;
    }

    public int getT_id() {
        return t_id;
    }

    public void setT_id(int t_id) {
        this.t_id = t_id;
    }

    public int getA_id() {
        return a_id;
    }

    public void setA_id(int a_id) {
        this.a_id = a_id;
    }

    public int getS_id() {
        return s_id;
    }

    public void setS_id(int s_id) {
        this.s_id = s_id;
    }

    public int getR_id() {
        return r_id;
    }

    public void setR_id(int r_id) {
        this.r_id = r_id;
    }
}