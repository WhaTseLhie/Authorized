package pardillo.john.jv.authorized.database.pojo;

public class MainContent {

    private int mc_id;
    private String mc_name, mc_type;

    public MainContent(int mc_id, String mc_name, String mc_type) {
        this.mc_id = mc_id;
        this.mc_name = mc_name;
        this.mc_type = mc_type;
    }

    public int getMc_id() {
        return mc_id;
    }

    public void setMc_id(int mc_id) {
        this.mc_id = mc_id;
    }

    public String getMc_name() {
        return mc_name;
    }

    public void setMc_name(String mc_name) {
        this.mc_name = mc_name;
    }

    public String getMc_type() {
        return mc_type;
    }

    public void setMc_type(String mc_type) {
        this.mc_type = mc_type;
    }
}