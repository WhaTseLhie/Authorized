package pardillo.john.jv.authorized.database.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.pojo.Notification;
import pardillo.john.jv.authorized.database.pojo.User;

public class ANotAdapter extends BaseAdapter {

    Context context;
    ArrayList<Notification> list;
    LayoutInflater inflater;

    public ANotAdapter(Context context, ArrayList<Notification> list) {
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
        NotificationHandler handler;

        if(convertView == null) {
            handler = new NotificationHandler();
            convertView = inflater.inflate(R.layout.adapter_anot, null);

            handler.txtTitle = convertView.findViewById(R.id.txtTitle);
            handler.txtType = convertView.findViewById(R.id.txtType);
            handler.txtDate = convertView.findViewById(R.id.txtDate);
            handler.txtMessage = convertView.findViewById(R.id.txtMessage);

            convertView.setTag(handler);
        } else {
            handler = (NotificationHandler) convertView.getTag();
        }
        handler.txtTitle.setText(list.get(position).getNot_title());
        handler.txtType.setText("Type: " +list.get(position).getNot_type());
        handler.txtDate.setText("Date: " +list.get(position).getNot_date());
        handler.txtMessage.setText(list.get(position).getNot_message());

        return convertView;
    }

    static class NotificationHandler {
        TextView txtTitle, txtType, txtDate, txtMessage;
    }
}