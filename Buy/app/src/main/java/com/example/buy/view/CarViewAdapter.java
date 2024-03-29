package com.example.buy.view;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.buy.R;
import com.example.buy.fragment.ListenerFragment;
import com.example.buy.fragment.MineFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
/**
 * @feature Visualize cars, search, deletion of cars, follow a car
 * @author Chenwei Niu
 */
public class CarViewAdapter extends ArrayAdapter<CarView> {
    ListenerFragment listenerFragment;
    public CarViewAdapter(@NonNull Context context, ArrayList<CarView> arrayList, ListenerFragment listenerFragment){
        super(context,0,arrayList);
        this.listenerFragment = listenerFragment;
    }

    public CarViewAdapter(@NonNull Context context, ArrayList<CarView> arrayList){
        super(context,0,arrayList);
    }

    @Nullable
    @Override
    public CarView getItem(int position) {
        return super.getItem(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // convertView which is recyclable view
        View currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.item_list_layout, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        CarView currentNumberPosition = getItem(position);

        ImageView carImage = currentItemView.findViewById(R.id.car_image);
        assert currentNumberPosition != null;
        Picasso.get().load(currentNumberPosition.getCarImage()).into(carImage);

        ImageView likeImage = currentItemView.findViewById(R.id.like_image);
        likeImage.setImageResource(currentNumberPosition.getLikeImage());
        likeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerFragment.dealWithEvent(position);
            }
        });

        TextView textView1 = currentItemView.findViewById(R.id.car_information);
        textView1.setText(currentNumberPosition.getCarInformation());


        TextView textView2 = currentItemView.findViewById(R.id.car_price);
        textView2.setText("$"+currentNumberPosition.getCarPrice());

        TextView textView3 = currentItemView.findViewById(R.id.car_engine);
        textView3.setText(currentNumberPosition.getCarEngine());

        TextView textView4 = currentItemView.findViewById(R.id.car_odometer);
        textView4.setText(currentNumberPosition.getCarOdometer()+"km");

        TextView textView5 = currentItemView.findViewById(R.id.car_body_style);
        textView5.setText(currentNumberPosition.getCarBodyStyle());

        TextView textView6 = currentItemView.findViewById(R.id.car_transmission);
        textView6.setText(currentNumberPosition.getCarTransmission());

        TextView textView7 = currentItemView.findViewById(R.id.car_location);
        textView7.setText(currentNumberPosition.getCarLocation());

        // then return the recyclable view
        return currentItemView;
    }

}
