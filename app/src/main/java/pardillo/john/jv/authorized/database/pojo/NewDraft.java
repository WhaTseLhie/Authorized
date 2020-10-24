package pardillo.john.jv.authorized.database.pojo;

public class NewDraft {

    private int d_id;
    private String d_date, d_letterContent, d_ot, d_ct;
    private int t_id, a_id, rep_id, rec_id;

    public NewDraft(int d_id, String d_date, String d_letterContent, String d_ot, String d_ct, int t_id, int a_id, int rep_id, int rec_id) {
        this.d_id = d_id;
        this.d_date = d_date;
        this.d_letterContent = d_letterContent;
        this.d_ot = d_ot;
        this.d_ct = d_ct;
        this.t_id = t_id;
        this.a_id = a_id;
        this.rep_id = rep_id;
        this.rec_id = rec_id;
    }

    public int getD_id() {
        return d_id;
    }

    public void setD_id(int d_id) {
        this.d_id = d_id;
    }

    public String getD_date() {
        return d_date;
    }

    public void setD_date(String d_date) {
        this.d_date = d_date;
    }

    public String getD_letterContent() {
        return d_letterContent;
    }

    public void setD_letterContent(String d_letterContent) {
        this.d_letterContent = d_letterContent;
    }

    public String getD_ot() {
        return d_ot;
    }

    public void setD_ot(String d_ot) {
        this.d_ot = d_ot;
    }

    public String getD_ct() {
        return d_ct;
    }

    public void setD_ct(String d_ct) {
        this.d_ct = d_ct;
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

    public int getRep_id() {
        return rep_id;
    }

    public void setRep_id(int rep_id) {
        this.rep_id = rep_id;
    }

    public int getRec_id() {
        return rec_id;
    }

    public void setRec_id(int rec_id) {
        this.rec_id = rec_id;
    }
}