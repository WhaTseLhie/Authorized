package pardillo.john.jv.authorized.database.pojo;

public class ClickLetterContent {

    private int clc_id;
    private String clc_name;
    private int clc_start, clc_end, t_id;

    public ClickLetterContent(int clc_id, String clc_name, int clc_start, int clc_end, int t_id) {
        this.clc_id = clc_id;
        this.clc_name = clc_name;
        this.clc_start = clc_start;
        this.clc_end = clc_end;
        this.t_id = t_id;
    }

    public int getClc_id() {
        return clc_id;
    }

    public void setClc_id(int clc_id) {
        this.clc_id = clc_id;
    }

    public String getClc_name() {
        return clc_name;
    }

    public void setClc_name(String clc_name) {
        this.clc_name = clc_name;
    }

    public int getClc_start() {
        return clc_start;
    }

    public void setClc_start(int clc_start) {
        this.clc_start = clc_start;
    }

    public int getClc_end() {
        return clc_end;
    }

    public void setClc_end(int clc_end) {
        this.clc_end = clc_end;
    }

    public int getT_id() {
        return t_id;
    }

    public void setT_id(int t_id) {
        this.t_id = t_id;
    }
}