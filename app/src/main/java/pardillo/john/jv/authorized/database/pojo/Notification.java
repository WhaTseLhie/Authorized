package pardillo.john.jv.authorized.database.pojo;

public class Notification {

    private int not_id;
    private String not_type, not_title, not_date, not_message;
    private int a_id, rec_id, s_id, r_id;

    public Notification(int not_id, String not_type, String not_title, String not_date, String not_message, int a_id, int rec_id, int s_id, int r_id) {
        this.not_id = not_id;
        this.not_type = not_type;
        this.not_title = not_title;
        this.not_date = not_date;
        this.not_message = not_message;
        this.a_id = a_id;
        this.rec_id = rec_id;
        this.s_id = s_id;
        this.r_id = r_id;
    }

    public int getNot_id() {
        return not_id;
    }

    public void setNot_id(int not_id) {
        this.not_id = not_id;
    }

    public String getNot_type() {
        return not_type;
    }

    public void setNot_type(String not_type) {
        this.not_type = not_type;
    }

    public String getNot_title() {
        return not_title;
    }

    public void setNot_title(String not_title) {
        this.not_title = not_title;
    }

    public String getNot_date() {
        return not_date;
    }

    public void setNot_date(String not_date) {
        this.not_date = not_date;
    }

    public String getNot_message() {
        return not_message;
    }

    public void setNot_message(String not_message) {
        this.not_message = not_message;
    }

    public int getA_id() {
        return a_id;
    }

    public void setA_id(int a_id) {
        this.a_id = a_id;
    }

    public int getRec_id() {
        return rec_id;
    }

    public void setRec_id(int rec_id) {
        this.rec_id = rec_id;
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