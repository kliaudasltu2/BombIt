package ktu.bombit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends ArrayAdapter<Player> {

    public  ListAdapter(Context context, List<Player> objects){
        super(context, R.layout.playeradapter, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;

        if(v == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.playeradapter, null);
        }

        TextView title = (TextView) v.findViewById(R.id.title);
        TextView description = (TextView) v.findViewById(R.id.description);
        ImageView image = (ImageView) v.findViewById(R.id.image);

        Player item = getItem(position);

        title.setText(item.getTitle() + " Best Score: " + item.getBestScore());
        description.setText(item.getDescription());
        image.setImageResource(R.drawable.ic_account_circle_black_24dp);

        return v;
    }
}