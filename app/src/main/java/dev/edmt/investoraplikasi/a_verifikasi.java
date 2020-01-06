package dev.edmt.investoraplikasi;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Protek on 04/03/2018.
 */

public class a_verifikasi extends ArrayAdapter<m_verifikasi> {

    Activity activity;
    int resouse;
    List<m_verifikasi>list;

    public a_verifikasi(Activity activity, int resouse, List<m_verifikasi> list) {
        super(activity, resouse, list);
        this.activity = activity;
        this.resouse = resouse;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(resouse, null);

        ImageView imageView = (ImageView)view.findViewById(R.id.getImages);
        TextView name = (TextView)view.findViewById(R.id.getName);
        TextView email = (TextView)view.findViewById(R.id.getEmail);

        name.setText(list.get(position).getName());
        email.setText(list.get(position).getEmail());

        Glide.with(activity).load(list.get(position).getFilePath()).into(imageView);

        return view;

    }
}
