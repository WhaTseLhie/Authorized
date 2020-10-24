package pardillo.john.jv.authorized.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import pardillo.john.jv.authorized.R;

public class GenderActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender);

        this.btnNext = this.findViewById(R.id.button);

        this.btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent passIntent = new Intent(this, PasswordActivity.class);
        startActivity(passIntent);
    }
}