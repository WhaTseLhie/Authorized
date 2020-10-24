package pardillo.john.jv.authorized.database.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.pojo.MainContent;

public class MainContentAdapter extends BaseAdapter {

    Context context;
    ArrayList<MainContent> list;
    LayoutInflater inflater;

    public MainContentAdapter(Context context, ArrayList<MainContent> list) {
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
        MainContentHandler handler;

        if(convertView == null) {
            handler = new MainContentHandler();
            convertView = inflater.inflate(R.layout.adapter_main_content, null);

            handler.txtName = convertView.findViewById(R.id.txtName);
            handler.txtType = convertView.findViewById(R.id.txtType);

            convertView.setTag(handler);
        } else {
            handler = (MainContentHandler) convertView.getTag();
        }

        handler.txtName.setText("Name: " +list.get(position).getMc_name());
        handler.txtType.setText("Type: " +list.get(position).getMc_type());

        return convertView;
    }

    static class MainContentHandler {
        TextView txtName, txtType;
    }
}