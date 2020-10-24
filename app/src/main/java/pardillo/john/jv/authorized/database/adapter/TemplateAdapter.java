package pardillo.john.jv.authorized.database.adapter;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.pojo.TemplateDetails;
import pardillo.john.jv.authorized.database.pojo.User;

public class TemplateAdapter extends BaseAdapter {

    Context context;
    ArrayList<TemplateDetails> list;
    LayoutInflater inflater;

    public TemplateAdapter(Context context, ArrayList<TemplateDetails> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TemplateHandler handler;

        if(convertView == null) {
            handler = new TemplateHandler();
            convertView = inflater.inflate(R.layout.adapter_template, null);

            handler.iv = convertView.findViewById(R.id.imageView);
            handler.txtMaker = convertView.findViewById(R.id.txtMaker);
            handler.txtTitle = convertView.findViewById(R.id.txtTitle);
            handler.txtType = convertView.findViewById(R.id.txtType);
            handler.txtDate = convertView.findViewById(R.id.txtDate);

            convertView.setTag(handler);
        } else {
            handler = (TemplateHandler) convertView.getTag();
        }

        AppDatabase db = new AppDatabase(context);
        ArrayList<User> aList = db.getUser(list.get(position).getA_id());

        try {
            if(TextUtils.isEmpty(list.get(position).getT_image().toString())) {
                db.deleteTemplateContentTID(list.get(position).getT_id());
                db.deleteTemplate(list.get(position).getT_id());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        handler.iv.setImageURI(list.get(position).getT_image());
        handler.txtMaker.setText("Created by: " +aList.get(0).getA_fname() +" "+ aList.get(0).getA_lname());
        handler.txtTitle.setText("Title: " +list.get(position).getT_title());
        handler.txtType.setText("Type: " +list.get(position).getT_type());
        handler.txtDate.setText("Date Created: " +list.get(position).getT_date());

        return convertView;
    }

    static class TemplateHandler {
        ImageView iv;
        TextView txtMaker, txtTitle, txtType, txtDate;
    }
}