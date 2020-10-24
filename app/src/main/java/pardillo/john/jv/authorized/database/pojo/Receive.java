package pardillo.john.jv.authorized.database.pojo;

public class Receive {

    private int r_id, r_otp;
    private String receivedDate, r_letter;
    private int t_id, a_id, rep_id, rec_id, rate_id, s_id;
    private String r_status;

    public Receive(int r_id, int r_otp, String receivedDate, String r_letter, int t_id, int a_id, int rep_id, int rec_id, int rate_id, int s_id, String r_status) {
        this.r_id = r_id;
        this.r_otp = r_otp;
        this.receivedDate = receivedDate;
        this.r_letter = r_letter;
        this.t_id = t_id;
        this.a_id = a_id;
        this.rep_id = rep_id;
        this.rec_id = rec_id;
        this.rate_id = rate_id;
        this.s_id = s_id;
        this.r_status = r_status;
    }

    public int getR_id() {
        return r_id;
    }

    public void setR_id(int r_id) {
        this.r_id = r_id;
    }

    public int getR_otp() {
        return r_otp;
    }

    public void setR_otp(int r_otp) {
        this.r_otp = r_otp;
    }

    public String getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(String receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getR_letter() {
        return r_letter;
    }

    public void setR_letter(String r_letter) {
        this.r_letter = r_letter;
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

    public int getS_id() {
        return s_id;
    }

    public void setS_id(int s_id) {
        this.s_id = s_id;
    }

    public String getR_status() {
        return r_status;
    }

    public void setR_status(String r_status) {
        this.r_status = r_status;
    }
}