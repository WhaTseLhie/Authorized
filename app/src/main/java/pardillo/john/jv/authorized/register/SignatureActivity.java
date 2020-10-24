package pardillo.john.jv.authorized.register;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.login.LoginActivity;
import pardillo.john.jv.authorized.register.anew.SignActivity;
import pardillo.john.jv.authorized.style.MyToast;

public class SignatureActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    private ImageView iv;

    private int id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);

        this.iv = this.findViewById(R.id.imageView);
        this.button = this.findViewById(R.id.button);

        this.button.setOnClickListener(this);
        this.iv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                if (id != 0) {
                    MyToast myToast = new MyToast();
                    myToast.makeToast(this, "Account Successfully Created", "SUCCESS");

                    Intent logIntent = new Intent(this, LoginActivity.class);
                    startActivity(logIntent);
                } else {
                    MyToast toast = new MyToast();
                    toast.makeToast(this, "Please write your signature", "WARNING");
                }

                break;
            case R.id.imageView:
                Intent signIntent = new Intent(this, SignActivity.class);
                startActivityForResult(signIntent, 69);

                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.signature_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent signIntent = new Intent(this, SignActivity.class);
        startActivityForResult(signIntent, 69);

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            id = R.drawable.delete_ic_sample_signature;
            this.iv.setImageResource(id);
        } catch (Exception e) {
            Log.d("ERR", "ERR");
        }
//        if(resultCode == RESULT_OK) {
//            if(requestCode == 69) {
//            }
//        }
    }
}