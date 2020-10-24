package pardillo.john.jv.authorized.database.pojo;

public class Payment {

    private int ir_id;
    private String ir_date, ir_method, ir_payment;
    private int a_id, rec_id, s_id, r_id;

    public Payment(int ir_id, String ir_date, String ir_method, String ir_payment, int a_id, int rec_id, int s_id, int r_id) {
        this.ir_id = ir_id;
        this.ir_date = ir_date;
        this.ir_method = ir_method;
        this.ir_payment = ir_payment;
        this.a_id = a_id;
        this.rec_id = rec_id;
        this.s_id = s_id;
        this.r_id = r_id;
    }

    public int getIr_id() {
        return ir_id;
    }

    public void setIr_id(int ir_id) {
        this.ir_id = ir_id;
    }

    public String getIr_date() {
        return ir_date;
    }

    public void setIr_date(String ir_date) {
        this.ir_date = ir_date;
    }

    public String getIr_method() {
        return ir_method;
    }

    public void setIr_method(String ir_method) {
        this.ir_method = ir_method;
    }

    public String getIr_payment() {
        return ir_payment;
    }

    public void setIr_payment(String ir_payment) {
        this.ir_payment = ir_payment;
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
}