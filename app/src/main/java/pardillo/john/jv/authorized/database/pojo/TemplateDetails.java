package pardillo.john.jv.authorized.database.pojo;

import android.net.Uri;

public class TemplateDetails {

    private int t_id;
    private Uri t_image;
    private String t_title, t_date, t_type;
    private int a_id;
    private String t_status;

    public TemplateDetails(int t_id, Uri t_image, String t_title, String t_date, String t_type, int a_id, String t_status) {
        this.t_id = t_id;
        this.t_image = t_image;
        this.t_title = t_title;
        this.t_date = t_date;
        this.t_type = t_type;
        this.a_id = a_id;
        this.t_status = t_status;
    }

    public int getT_id() {
        return t_id;
    }

    public void setT_id(int t_id) {
        this.t_id = t_id;
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

    public int getA_id() {
        return a_id;
    }

    public void setA_id(int a_id) {
        this.a_id = a_id;
    }

    public String getT_status() {
        return t_status;
    }

    public void setT_status(String t_status) {
        this.t_status = t_status;
    }
}