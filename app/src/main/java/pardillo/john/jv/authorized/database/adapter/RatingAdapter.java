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
import pardillo.john.jv.authorized.database.pojo.Rating;
import pardillo.john.jv.authorized.database.pojo.User;
import pardillo.john.jv.authorized.style.CircleTransform;

public class RatingAdapter extends BaseAdapter {

    Context context;
    ArrayList<Rating> list;
    LayoutInflater inflater;

    public RatingAdapter(Context context, ArrayList<Rating> list) {
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
        RatingHandler handler;

        if(convertView == null) {
            handler = new RatingHandler();
            convertView = inflater.inflate(R.layout.adapter_rating, null);

            handler.ivProfile = convertView.findViewById(R.id.ivProfile);
            handler.txtReviewerName = convertView.findViewById(R.id.txtReviewerName);
            handler.txtReviewDate = convertView.findViewById(R.id.txtReviewDate);
            handler.txtComment = convertView.findViewById(R.id.txtComment);
            handler.rbRate = convertView.findViewById(R.id.ratingBar);

            convertView.setTag(handler);
        } else {
            handler = (RatingHandler) convertView.getTag();
        }

        AppDatabase db = new AppDatabase(context);
        ArrayList<User> userList = db.getUser(list.get(position).getA_id());

//        handler.ivProfile.setImageURI(userList.get(0).getA_image());

        Picasso.with(context).load(userList.get(0).getA_image()).transform(new CircleTransform()).into(handler.ivProfile);
        handler.txtReviewerName.setText(userList.get(0).getA_fname() +" "+ userList.get(0).getA_lname());
        handler.txtReviewDate.setText(list.get(position).getRate_date());
        handler.txtComment.setText(list.get(position).getRate_comment());
        handler.rbRate.setRating(list.get(position).getRate_rating());

        return convertView;
    }

    static class RatingHandler {
        ImageView ivProfile;
        TextView txtReviewerName, txtReviewDate, txtComment;
        RatingBar rbRate;
    }
}