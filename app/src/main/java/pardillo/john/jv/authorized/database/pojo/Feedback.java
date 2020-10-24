package pardillo.john.jv.authorized.database.pojo;

public class Feedback {

    private int f_id;
    private String f_title, f_date, f_sdate, f_edate, f_message;
    private int a_id, rec_id, s_id, r_id;

    public Feedback(int f_id, String f_title, String f_date, String f_sdate, String f_edate, String f_message, int a_id, int rec_id, int s_id, int r_id) {
        this.f_id = f_id;
        this.f_title = f_title;
        this.f_date = f_date;
        this.f_sdate = f_sdate;
        this.f_edate = f_edate;
        this.f_message = f_message;
        this.a_id = a_id;
        this.rec_id = rec_id;
        this.s_id = s_id;
        this.r_id = r_id;
    }

    public int getF_id() {
        return f_id;
    }

    public void setF_id(int f_id) {
        this.f_id = f_id;
    }

    public String getF_title() {
        return f_title;
    }

    public void setF_title(String f_title) {
        this.f_title = f_title;
    }

    public String getF_date() {
        return f_date;
    }

    public void setF_date(String f_date) {
        this.f_date = f_date;
    }

    public String getF_sdate() {
        return f_sdate;
    }

    public void setF_sdate(String f_sdate) {
        this.f_sdate = f_sdate;
    }

    public String getF_edate() {
        return f_edate;
    }

    public void setF_edate(String f_edate) {
        this.f_edate = f_edate;
    }

    public String getF_message() {
        return f_message;
    }

    public void setF_message(String f_message) {
        this.f_message = f_message;
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