package sg.edu.rp.c346.id20013676.myndpsongs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {
    Context parent_context;
    int layout_id;
    ArrayList<Island> IslandList;

    public CustomAdapter(Context context, int resource, ArrayList<Island> objects) {
        super(context, resource, objects);

        this.parent_context = context;
        this.layout_id = resource;
        this.IslandList = objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)
                parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(layout_id, parent, false);

        TextView tvName = rowView.findViewById(R.id.textViewName);
        TextView tvArea = rowView.findViewById(R.id.textViewArea);
//        TextView tvStars = rowView.findViewById(R.id.textViewStars);
        TextView tvSinger = rowView.findViewById(R.id.textViewSinger);
        ImageView ivNew = rowView.findViewById(R.id.imageViewNew);
        RatingBar ratingBar = (RatingBar) rowView.findViewById(R.id.ratingBar);

        Island Island = IslandList.get(position);

        tvName.setText(Island.getName());
        tvArea.setText(Island.getArea() + "");
//        tvStars.setText(Island.toStars());
        tvSinger.setText(Island.getDescription());
        ratingBar.setRating(Island.getStars());

        if (Island.getArea() > 4) {
            ivNew.setVisibility(View.VISIBLE);
        } else {
            ivNew.setVisibility(View.INVISIBLE);
        }

        return rowView;
    }



}
