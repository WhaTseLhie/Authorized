package pardillo.john.jv.authorized.database.pojo;

public class Price {

    private int p_id, p_price;

    public Price(int p_id, int p_price) {
        this.p_id = p_id;
        this.p_price = p_price;
    }

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public int getP_price() {
        return p_price;
    }

    public void setP_price(int p_price) {
        this.p_price = p_price;
    }
}