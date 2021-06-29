package com.hummo.hummigo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class GridAdapt extends ArrayAdapter<GridModel> {
    public GridAdapt(@NonNull Context context, ArrayList<GridModel> courseModelArrayList) {
        super(context, 0, courseModelArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item, parent, false);
        }
        GridModel gridModel = getItem(position);

        ImageView courseIV = listitemView.findViewById(R.id.grid_im);

        courseIV.setImageResource(gridModel.getImgid());
        return listitemView;
    }
}