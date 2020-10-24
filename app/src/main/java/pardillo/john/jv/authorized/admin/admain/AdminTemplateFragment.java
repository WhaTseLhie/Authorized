package pardillo.john.jv.authorized.admin.admain;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.admin.AdminViewTemplateActivity;
import pardillo.john.jv.authorized.admin.admintemp.NewAdminViewTemplateActivity;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.adapter.NewTemplateAdapter;
import pardillo.john.jv.authorized.database.adapter.TemplateAdapter;
import pardillo.john.jv.authorized.database.pojo.NewTemplate;
import pardillo.john.jv.authorized.database.pojo.TemplateDetails;

public class AdminTemplateFragment extends Fragment {

    private ListView lv;
    private TextView txtEmpty;

    private ArrayList<NewTemplate> templateList = new ArrayList<>();
//    private ArrayList<TemplateDetails> templateList = new ArrayList<>();

    private AppDatabase db;
    private NewTemplateAdapter adapter;
//    private TemplateAdapter adapter;

    public AdminTemplateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_template, container, false);

        db = new AppDatabase(getContext());

        lv = view.findViewById(R.id.listView);
        txtEmpty = view.findViewById(R.id.txtEmpty);
        templateList = db.getAllNewTemplate("LOA for Collecting Documents");
//        templateList = db.getAllTemplateDetails();
        adapter = new NewTemplateAdapter(getContext(), templateList);
        lv.setAdapter(adapter);

        if(templateList.isEmpty()) {
            txtEmpty.setVisibility(View.VISIBLE);
        } else {
            txtEmpty.setVisibility(View.INVISIBLE);
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent viewTemplate = new Intent(getContext(), NewAdminViewTemplateActivity.class);
                viewTemplate.putExtra("t_id", templateList.get(position).getT_id());
                startActivity(viewTemplate);
//                Intent createTemplate = new Intent(getContext(), NewCreateLetterActivity.class);
//                createTemplate.putExtra("t_id", templateList.get(position).getT_id());
//                startActivity(createTemplate);
            }
        });

        /*try {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        } catch(Exception e) {
            e.printStackTrace();
        }*/

        return view;
    }
}