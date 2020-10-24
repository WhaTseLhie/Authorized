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
import pardillo.john.jv.authorized.database.pojo.User;
import pardillo.john.jv.authorized.style.CircleTransform;

public class UserAdapter extends BaseAdapter {

    Context context;
    ArrayList<User> list;
    LayoutInflater inflater;

    public UserAdapter(Context context, ArrayList<User> list) {
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
        UserHandler handler;

        if(convertView == null) {
            handler = new UserHandler();
            convertView = inflater.inflate(R.layout.adapter_user, null);

            handler.iv = convertView.findViewById(R.id.imageView);
            handler.rbRating = convertView.findViewById(R.id.ratingBar);
            handler.txtFullName = convertView.findViewById(R.id.txtFullName);
            handler.txtContact = convertView.findViewById(R.id.txtContact);
//            handler.txtAddress = convertView.findViewById(R.id.txtAddress);

            convertView.setTag(handler);
        } else {
            handler = (UserHandler) convertView.getTag();
        }

        AppDatabase db = new AppDatabase(context);
//        ArrayList<User> userList = db.getUser(list.get(0).getA_id());

//        handler.iv.setImageURI(userList.get(position).getA_image());
        Picasso.with(context).load(list.get(position).getA_image()).transform(new CircleTransform()).into(handler.iv);
        handler.rbRating.setRating(db.getAverageRating(list.get(position).getA_id()));
        handler.txtFullName.setText(list.get(position).getA_fname() +" "+ list.get(position).getA_lname());
        handler.txtContact.setText(list.get(position).getA_contact());
//        handler.txtAddress.setText(list.get(position).getA_gender()); // ADDRESS

        return convertView;
    }

    static class UserHandler {
        ImageView iv;
        RatingBar rbRating;
        TextView txtFullName, txtContact, txtAddress;
    }
}