package pardillo.john.jv.authorized.database.pojo;

public class TemplateContent {

    private int tc_id;
    private String c_name, c_type;
    private int c_leftMargin, c_topMargin, c_rightMargin, c_bottomMargin;
    private int c_id, t_id;

    public TemplateContent(int tc_id, String c_name, String c_type, int c_leftMargin, int c_topMargin, int c_rightMargin, int c_bottomMargin, int c_id, int t_id) {
        this.tc_id = tc_id;
        this.c_name = c_name;
        this.c_type = c_type;
        this.c_leftMargin = c_leftMargin;
        this.c_topMargin = c_topMargin;
        this.c_rightMargin = c_rightMargin;
        this.c_bottomMargin = c_bottomMargin;
        this.c_id = c_id;
        this.t_id = t_id;
    }

    public int getTc_id() {
        return tc_id;
    }

    public void setTc_id(int tc_id) {
        this.tc_id = tc_id;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getC_type() {
        return c_type;
    }

    public void setC_type(String c_type) {
        this.c_type = c_type;
    }

    public int getC_leftMargin() {
        return c_leftMargin;
    }

    public void setC_leftMargin(int c_leftMargin) {
        this.c_leftMargin = c_leftMargin;
    }

    public int getC_topMargin() {
        return c_topMargin;
    }

    public void setC_topMargin(int c_topMargin) {
        this.c_topMargin = c_topMargin;
    }

    public int getC_rightMargin() {
        return c_rightMargin;
    }

    public void setC_rightMargin(int c_rightMargin) {
        this.c_rightMargin = c_rightMargin;
    }

    public int getC_bottomMargin() {
        return c_bottomMargin;
    }

    public void setC_bottomMargin(int c_bottomMargin) {
        this.c_bottomMargin = c_bottomMargin;
    }

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public int getT_id() {
        return t_id;
    }

    public void setT_id(int t_id) {
        this.t_id = t_id;
    }
}