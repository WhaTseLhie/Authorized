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
import pardillo.john.jv.authorized.database.pojo.Sent;
import pardillo.john.jv.authorized.database.pojo.User;
import pardillo.john.jv.authorized.style.CircleTransform;

public class SentAdapter extends BaseAdapter {

    Context context;
    ArrayList<Sent> list;
    LayoutInflater inflater;

    public SentAdapter(Context context, ArrayList<Sent> list) {
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
        SentHandler handler;

        if(convertView == null) {
            handler = new SentHandler();
            convertView = inflater.inflate(R.layout.adapter_sent, null);

            handler.ivProfile = convertView.findViewById(R.id.ivProfile);
            handler.txtRecipientName = convertView.findViewById(R.id.txtRecipientName);
            handler.txtSentDate = convertView.findViewById(R.id.txtSentDate);
            handler.txtStatus = convertView.findViewById(R.id.txtStatus);
            handler.rbRate = convertView.findViewById(R.id.ratingBar);

            convertView.setTag(handler);
        } else {
            handler = (SentHandler) convertView.getTag();
        }

        AppDatabase db = new AppDatabase(context);
        ArrayList<User> recList = db.getUser(list.get(position).getRec_id());

//        accepted, decline
//        handler.ivProfile.setImageURI(recList.get(0).getA_image());
        Picasso.with(context).load(recList.get(0).getA_image()).transform(new CircleTransform()).into(handler.ivProfile);
        handler.txtRecipientName.setText(recList.get(0).getA_fname() +" "+ recList.get(0).getA_lname());
        handler.txtSentDate.setText(list.get(position).getSentDate());
        handler.txtStatus.setText(list.get(position).getS_status());

        if(list.get(position).getRate_id() == 0) {
            handler.rbRate.setRating(0);
        } else {
            handler.rbRate.setRating(db.getRating(list.get(position).getRate_id()));
        }

        return convertView;
    }

    static class SentHandler {
        ImageView ivProfile;
        TextView txtRecipientName, txtSentDate, txtStatus;
        RatingBar rbRate;
    }
}