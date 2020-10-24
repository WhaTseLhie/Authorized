package pardillo.john.jv.authorized.database.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import pardillo.john.jv.authorized.R;
import pardillo.john.jv.authorized.database.AppDatabase;
import pardillo.john.jv.authorized.database.pojo.Receive;
import pardillo.john.jv.authorized.database.pojo.User;
import pardillo.john.jv.authorized.style.CircleTransform;

public class ReceiveAdapter extends BaseAdapter {

    Context context;
    ArrayList<Receive> list;
    LayoutInflater inflater;

    public ReceiveAdapter(Context context, ArrayList<Receive> list) {
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
        ReceiveHandler handler;

        if(convertView == null) {
            handler = new ReceiveHandler();
            convertView = inflater.inflate(R.layout.adapter_receive, null);

            handler.ivProfile = convertView.findViewById(R.id.ivProfile);
            handler.txtSenderName = convertView.findViewById(R.id.txtSenderName);
            handler.txtReceiveDate = convertView.findViewById(R.id.txtReceiveDate);
            handler.txtStatus = convertView.findViewById(R.id.txtStatus);
            handler.rbRate = convertView.findViewById(R.id.ratingBar);

            convertView.setTag(handler);
        } else {
            handler = (ReceiveHandler) convertView.getTag();
        }

        AppDatabase db = new AppDatabase(context);
        ArrayList<User> userList = db.getUser(list.get(position).getA_id());

//        handler.ivProfile.setImageURI(userList.get(0).getA_image());
        Picasso.with(context).load(userList.get(0).getA_image()).transform(new CircleTransform()).into(handler.ivProfile);
        handler.txtSenderName.setText(userList.get(0).getA_fname() +" "+ userList.get(0).getA_lname());
        handler.txtReceiveDate.setText(list.get(position).getReceivedDate());
        handler.txtStatus.setText(list.get(position).getR_status());

        if(list.get(position).getRate_id() == 0) {
            handler.rbRate.setRating(0);
        } else {
            handler.rbRate.setRating(db.getRating(list.get(position).getRate_id()));
        }

        return convertView;
    }

    static class ReceiveHandler {
        ImageView ivProfile;
        TextView txtSenderName, txtReceiveDate, txtStatus;
        RatingBar rbRate;
    }
}