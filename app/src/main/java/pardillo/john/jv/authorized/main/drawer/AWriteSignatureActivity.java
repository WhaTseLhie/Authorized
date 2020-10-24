package pardillo.john.jv.authorized.main.drawer;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import pardillo.john.jv.authorized.R;

public class AWriteSignatureActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    private ImageView iv;

    private int id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_awrite_signature);

        this.iv = this.findViewById(R.id.imageView);
        this.button = this.findViewById(R.id.button);

        this.button.setOnClickListener(this);
        this.iv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
//                if (id != 0) {
//                    Intent progIntent = new Intent(this, ProgressActivity.class);
//                    startActivity(progIntent);
//                } else {
//                    LayoutInflater inflater = getLayoutInflater();
//                    View layout = inflater.inflate(R.layout.design_toast, (ViewGroup) findViewById(R.id.toast));
//                    layout.setBackground(getResources().getDrawable(R.drawable.style_warning_toast));
//                    layout.getBackground().setAlpha(204);
//
//                    TextView toastText = layout.findViewById(R.id.toast_text);
//                    ImageView toastImage = layout.findViewById(R.id.toast_image);
//                    toastText.setText("Please write your signature");
//                    toastImage.setImageResource(R.drawable.ic_warning);
//
//                    Toast toast = new Toast(this);
//                    toast.setGravity(Gravity.BOTTOM, 0, 30);
//                    toast.setDuration(Toast.LENGTH_LONG);
//                    toast.setView(layout);
//                    toast.show();
//                }

                break;
            case R.id.imageView:
//                Intent signIntent = new Intent(this, ASignActivity.class);
//                startActivityForResult(signIntent, 69);

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
//        Intent signIntent = new Intent(this, ASignActivity.class);
//        startActivityForResult(signIntent, 69);

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