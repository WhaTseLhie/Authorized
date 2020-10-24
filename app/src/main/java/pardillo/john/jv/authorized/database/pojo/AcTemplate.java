package pardillo.john.jv.authorized.database.pojo;

public class AcTemplate {

    private int ac_id;
    private String ac_letterContent;
    private int a_id;

    public AcTemplate(int ac_id, String ac_letterContent, int a_id) {
        this.ac_id = ac_id;
        this.ac_letterContent = ac_letterContent;
        this.a_id = a_id;
    }

    public int getAc_id() {
        return ac_id;
    }

    public void setAc_id(int ac_id) {
        this.ac_id = ac_id;
    }

    public String getAc_letterContent() {
        return ac_letterContent;
    }

    public void setAc_letterContent(String ac_letterContent) {
        this.ac_letterContent = ac_letterContent;
    }

    public int getA_id() {
        return a_id;
    }

    public void setA_id(int a_id) {
        this.a_id = a_id;
    }
}