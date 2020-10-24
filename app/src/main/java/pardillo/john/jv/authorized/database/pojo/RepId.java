package pardillo.john.jv.authorized.database.pojo;

public class RepId {

    private int repid_id,  a_id, rep_id;
    private String repid_image;

    public RepId(int repid_id, int a_id, int rep_id, String repid_image) {
        this.repid_id = repid_id;
        this.a_id = a_id;
        this.rep_id = rep_id;
        this.repid_image = repid_image;
    }

    public int getRepid_id() {
        return repid_id;
    }

    public void setRepid_id(int repid_id) {
        this.repid_id = repid_id;
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

    public String getRepid_image() {
        return repid_image;
    }

    public void setRepid_image(String repid_image) {
        this.repid_image = repid_image;
    }
}