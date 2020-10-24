package pardillo.john.jv.authorized.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.adapter.NewTemplateAdapter;
import pardillo.john.jv.authorized.database.adapter.TemplateAdapter;
import pardillo.john.jv.authorized.database.pojo.NewTemplate;
import pardillo.john.jv.authorized.database.pojo.TemplateDetails;
import pardillo.john.jv.authorized.database.pojo.User;
import pardillo.john.jv.authorized.main.letter.CreateLetterActivity;
import pardillo.john.jv.authorized.main.letter.NewCreateLetterActivity;
import pardillo.john.jv.authorized.main.letter.NewUserViewTemplateActivity;
import pardillo.john.jv.authorized.main.letter.NewViewLetterActivity;

public class TemplateFragment extends Fragment {

    private ListView lv;
    private Spinner cboSort;

    private ArrayList<NewTemplate> templateList = new ArrayList<>();

    private AppDatabase db;
    private NewTemplateAdapter adapter;

    private String sort = "LOA for Collecting Documents";

    public TemplateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_template, container, false);

        db = new AppDatabase(getContext());

        lv = view.findViewById(R.id.listView);
        cboSort = view.findViewById(R.id.cboSort);

        templateList = db.getAllNewTemplate(sort);
        adapter = new NewTemplateAdapter(getContext(), templateList);

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent viewTemplate = new Intent(getContext(), NewUserViewTemplateActivity.class);
                viewTemplate.putExtra("t_id", templateList.get(position).getT_id());
                startActivity(viewTemplate);
            }
        });

        cboSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sort = cboSort.getItemAtPosition(position).toString();
                templateList.clear();
                adapter.notifyDataSetChanged();

                templateList = db.getAllNewTemplate(sort);
                adapter = new NewTemplateAdapter(getContext(), templateList);
                lv.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }
}