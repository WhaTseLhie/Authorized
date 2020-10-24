package pardillo.john.jv.authorized.main.drawer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.main.drawer.asent.ViewSentFeedbackActivity;

public class AViewSentActivity extends AppCompatActivity {

    private ImageView imgSign;
    private TextView txtBody, txtAuthorizer;
    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aview_sent);

        this.setTitle("Request for TOR");

        this.btnCancel = this.findViewById(R.id.button2);
        this.imgSign = this.findViewById(R.id.imageView);
        this.txtBody = this.findViewById(R.id.textView);
        this.txtAuthorizer = this.findViewById(R.id.textView2);

        String date = "October 5, 2019";
        String recipient = "University of Cebu Main-Campus";
        String recipAddress = "Sanciangko St, Cebu City";
        String opening = "give full authorization to ";
        String authorizedPerson = "Mr. Le Bron ";
        String reason = "to apply for and pick my Official Transcript of Records from the ";
        String writer = "Bruce Galido";

        StringBuilder sb = new StringBuilder();
        sb.append(date)
                .append("\n\n")
                .append(recipient)
                .append("\n")
                .append(recipAddress)
                .append("\n\n")
                .append("Dear Sir/Madam: ")
                .append("\n\n")
                .append("I, ")
                .append("Bruce Galido, ")
                .append(opening)
                .append(authorizedPerson)
                .append(reason)
                .append(recipient)
                .append(".")
                .append("\n\n")
                .append("Thank you very much.")
                .append("\n\n")
                .append("Very truly yours,");

        this.txtBody.setText(sb);
        this.imgSign.setImageResource(R.drawable.delete_ic_sample_signature);
        this.txtAuthorizer.setText(writer);

        this.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AViewSentActivity.this, ViewSentFeedbackActivity.class);
                startActivity(intent);
            }
        });
    }
}