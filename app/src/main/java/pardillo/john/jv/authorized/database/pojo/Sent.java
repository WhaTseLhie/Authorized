package pardillo.john.jv.authorized.database.pojo;

public class Sent {

    private int s_id, s_otp;
    private String sentDate, s_letter;
    private int t_id, a_id, rep_id, rec_id, rate_id, r_id;
    private String s_status;

    public Sent(int s_id, int s_otp, String sentDate, String s_letter, int t_id, int a_id, int rep_id, int rec_id, int rate_id, int r_id, String s_status) {
        this.s_id = s_id;
        this.s_otp = s_otp;
        this.sentDate = sentDate;
        this.s_letter = s_letter;
        this.t_id = t_id;
        this.a_id = a_id;
        this.rep_id = rep_id;
        this.rec_id = rec_id;
        this.rate_id = rate_id;
        this.r_id = r_id;
        this.s_status = s_status;
    }

    public int getS_id() {
        return s_id;
    }

    public void setS_id(int s_id) {
        this.s_id = s_id;
    }

    public int getS_otp() {
        return s_otp;
    }

    public void setS_otp(int s_otp) {
        this.s_otp = s_otp;
    }

    public String getSentDate() {
        return sentDate;
    }

    public void setSentDate(String sentDate) {
        this.sentDate = sentDate;
    }

    public String getS_letter() {
        return s_letter;
    }

    public void setS_letter(String s_letter) {
        this.s_letter = s_letter;
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

    public int getRate_id() {
        return rate_id;
    }

    public void setRate_id(int rate_id) {
        this.rate_id = rate_id;
    }

    public int getR_id() {
        return r_id;
    }

    public void setR_id(int r_id) {
        this.r_id = r_id;
    }

    public String getS_status() {
        return s_status;
    }

    public void setS_status(String s_status) {
        this.s_status = s_status;
    }
}