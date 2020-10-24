package pardillo.john.jv.authorized.database.pojo;

public class Draft {

    private int d_id, a_id, t_id;

    public Draft(int d_id, int a_id, int t_id) {
        this.d_id = d_id;
        this.a_id = a_id;
        this.t_id = t_id;
    }

    public int getD_id() {
        return d_id;
    }

    public void setD_id(int d_id) {
        this.d_id = d_id;
    }

    public int getA_id() {
        return a_id;
    }

    public void setA_id(int a_id) {
        this.a_id = a_id;
    }

    public int getT_id() {
        return t_id;
    }

    public void setT_id(int t_id) {
        this.t_id = t_id;
    }
}