package pardillo.john.jv.authorized.database.pojo;

public class UserRating {

    private int ur_id, a_id;
    private float r_total;

    public UserRating(int ur_id, int a_id, float r_total) {
        this.ur_id = ur_id;
        this.a_id = a_id;
        this.r_total = r_total;
    }

    public int getUr_id() {
        return ur_id;
    }

    public void setUr_id(int ur_id) {
        this.ur_id = ur_id;
    }

    public int getA_id() {
        return a_id;
    }

    public void setA_id(int a_id) {
        this.a_id = a_id;
    }

    public float getR_total() {
        return r_total;
    }

    public void setR_total(float r_total) {
        this.r_total = r_total;
    }
}