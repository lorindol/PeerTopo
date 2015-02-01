package net.brotzeller.topeer.topo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.brotzeller.topeer.R;
import net.brotzeller.topeer.xml.Routetype;

import java.util.List;

/**
 * Created by martin on 10.01.15.
 */
public class RouteAdapter extends ArrayAdapter<Routetype> {
    private final Context context;

    public RouteAdapter(Context context, List<Routetype> objects) {
        super(context, R.layout.route_row_layout, objects);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.route_row_layout, parent, false);
        Routetype item = getItem(position);

        TextView number = (TextView) view.findViewById(R.id.routeidx);
        number.setText(item.index);

        TextView routeName = (TextView) view.findViewById(R.id.routelabel);
        routeName.setText(item.name);

        TextView difficulty = (TextView) view.findViewById(R.id.routedifficulty);
        difficulty.setText(item.difficulty);

        if (item.description != null) {
            ImageView moreImage = (ImageView) view.findViewById(R.id.routemore);
            moreImage.setBackgroundResource(R.drawable.icon_text);
        }

        return view;

    }
}
