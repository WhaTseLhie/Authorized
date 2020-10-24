package pardillo.john.jv.authorized.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import pardillo.john.jv.authorized.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.btnStart = this.findViewById(R.id.button);

        this.btnStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent nameIntent = new Intent(this, NameActivity.class);
        startActivity(nameIntent);
    }
}