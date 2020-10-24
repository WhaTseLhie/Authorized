package pardillo.john.jv.authorized.admin;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.pojo.Payment;
import pardillo.john.jv.authorized.database.pojo.Price;
import pardillo.john.jv.authorized.style.MyToast;

public class AdminPaymentActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtPayment, txtTotal;
    private Button btnUpdate;

    private AppDatabase db;
    private MyToast myToast;

    private ArrayList<Price> priceList = new ArrayList<>();
    private ArrayList<Payment> paymentList = new ArrayList<>();

    private int total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_payment);

        myToast = new MyToast();
        db = new AppDatabase(this);
        priceList = db.getPrice();
        total = db.getTotalPayment();

        txtTotal = findViewById(R.id.txtTotal);
        txtPayment = findViewById(R.id.txtPayment);
        btnUpdate = findViewById(R.id.btnUpdate);

        txtTotal.setText("\u20B1" +total);

        if(!priceList.isEmpty()) {
            txtPayment.setText("\u20B1" +priceList.get(0).getP_price());
        }

        btnUpdate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnUpdate:
                updatePayment();

                break;
        }
    }

    private void updatePayment() {
        final Dialog paymentDialog = new Dialog(this);
        paymentDialog.setContentView(R.layout.layout_update_payment);
        paymentDialog.setCancelable(false);

        final EditText txtUpdatePayment = paymentDialog.findViewById(R.id.txtPayment);
        Button btnUpdate = paymentDialog.findViewById(R.id.btnUpdate);
        Button btnCancel = paymentDialog.findViewById(R.id.btnCancel);

        if(priceList.isEmpty()) {
            txtUpdatePayment.setText("20");
        } else {
            txtUpdatePayment.setText("" +priceList.get(0).getP_price());
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String payment = txtUpdatePayment.getText().toString();

                if(TextUtils.isEmpty(payment)) {
                    myToast.makeToast(AdminPaymentActivity.this, "Please enter a price", "ERROR");
                } else {
                    txtPayment.setText("\u20B1" +payment);

                    if(priceList.isEmpty()) {
                        try {
                            db.addPrice(Integer.parseInt(payment));
                            myToast.makeToast(AdminPaymentActivity.this, "Price Updated", "SUCCESS");

                            AdminPaymentActivity.this.finish();
                            AdminPaymentActivity.this.startActivity(getIntent());
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    } else {
                        db.updatePrice(priceList.get(0).getP_id(), Integer.parseInt(payment));
                        myToast.makeToast(AdminPaymentActivity.this, "Price Updated", "SUCCESS");

                        AdminPaymentActivity.this.finish();
                        AdminPaymentActivity.this.startActivity(getIntent());
                    }

                    paymentDialog.dismiss();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentDialog.dismiss();
            }
        });

        paymentDialog.show();
    }
}











