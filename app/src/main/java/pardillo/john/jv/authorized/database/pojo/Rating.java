package pardillo.john.jv.authorized.database.pojo;

public class Rating {

    private int rate_id, a_id, rec_id, rep_id, s_id, r_id;
    private float rate_rating;
    String rate_date, rate_comment;

    public Rating(int rate_id, int a_id, int rec_id, int rep_id, int s_id, int r_id, float rate_rating, String rate_date, String rate_comment) {
        this.rate_id = rate_id;
        this.a_id = a_id;
        this.rec_id = rec_id;
        this.rep_id = rep_id;
        this.s_id = s_id;
        this.r_id = r_id;
        this.rate_rating = rate_rating;
        this.rate_date = rate_date;
        this.rate_comment = rate_comment;
    }

    public int getRate_id() {
        return rate_id;
    }

    public void setRate_id(int rate_id) {
        this.rate_id = rate_id;
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

    public int getRep_id() {
        return rep_id;
    }

    public void setRep_id(int rep_id) {
        this.rep_id = rep_id;
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

    public float getRate_rating() {
        return rate_rating;
    }

    public void setRate_rating(float rate_rating) {
        this.rate_rating = rate_rating;
    }

    public String getRate_date() {
        return rate_date;
    }

    public void setRate_date(String rate_date) {
        this.rate_date = rate_date;
    }

    public String getRate_comment() {
        return rate_comment;
    }

    public void setRate_comment(String rate_comment) {
        this.rate_comment = rate_comment;
    }
}