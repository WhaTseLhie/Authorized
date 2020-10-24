package pardillo.john.jv.authorized.database.pojo;

public class PastRecipient {

    private int pr_id, a_id, r_id;

    public PastRecipient(int pr_id, int a_id, int r_id) {
        this.pr_id = pr_id;
        this.a_id = a_id;
        this.r_id = r_id;
    }

    public int getPr_id() {
        return pr_id;
    }

    public void setPr_id(int pr_id) {
        this.pr_id = pr_id;
    }

    public int getA_id() {
        return a_id;
    }

    public void setA_id(int a_id) {
        this.a_id = a_id;
    }

    public int getR_id() {
        return r_id;
    }

    public void setR_id(int r_id) {
        this.r_id = r_id;
    }
}
