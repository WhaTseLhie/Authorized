package pardillo.john.jv.authorized.database.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.pojo.Rep;
import pardillo.john.jv.authorized.style.CircleTransform;

public class RepAdapter extends BaseAdapter {

    Context context;
    ArrayList<Rep> list;
    LayoutInflater inflater;

    public RepAdapter(Context context, ArrayList<Rep> list) {
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
        RepHandler handler;

        if(convertView == null) {
            handler = new RepHandler();
            convertView = inflater.inflate(R.layout.adapter_rep, null);

            handler.iv = convertView.findViewById(R.id.imageView);
            handler.txtFullName = convertView.findViewById(R.id.txtFullName);
            handler.txtContact = convertView.findViewById(R.id.txtContact);
            handler.txtGender = convertView.findViewById(R.id.txtGender);

            convertView.setTag(handler);
        } else {
            handler = (RepHandler) convertView.getTag();
        }

//        handler.iv.setImageURI(list.get(position).getRep_image());
        Picasso.with(context).load(list.get(position).getRep_image()).transform(new CircleTransform()).into(handler.iv);
        handler.txtFullName.setText(list.get(position).getRep_fname() +" "+ list.get(position).getRep_lname());
        handler.txtContact.setText(list.get(position).getRep_contact());
        handler.txtGender.setText(list.get(position).getRep_gender());

        return convertView;
    }

    static class RepHandler {
        ImageView iv;
        TextView txtFullName, txtContact, txtGender;
    }
}