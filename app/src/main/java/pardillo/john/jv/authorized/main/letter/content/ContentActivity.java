package pardillo.john.jv.authorized.main.letter.content;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;

public class ContentActivity extends AppCompatActivity {

    private AppDatabase db;
//    private ContentAdapter adapter;
//    private ArrayList<TemplateContent> contentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        db = new AppDatabase(this);
        Toast.makeText(this, "TemplateContent Activity", Toast.LENGTH_SHORT).show();
    }
}
