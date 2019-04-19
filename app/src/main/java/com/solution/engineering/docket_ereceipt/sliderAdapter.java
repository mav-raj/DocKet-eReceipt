package com.solution.engineering.docket_ereceipt;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by mavra on 03-Mar-18.
 */

public class sliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public sliderAdapter(Context context) {
        this.context = context;
    }


    //arrays
    public int[] slide_images = {
            R.drawable.first,
            R.drawable.second,
            R.drawable.third
    };
    public String[] slide_headings = {
            "place",
            "hold",
            "done"
    };
    public String[] slide_descp = {
            "Place Your device in front of QR of your bill",
            "Hold your device, Let it scan",
            "Volla!! Done, your receipt is now saved"

    };

    //to count the no of slides
    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (RelativeLayout) object;
    }

    //to add slide effect
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.slide_image);
        TextView slideHeadingView = (TextView) view.findViewById(R.id.slide_headings);
        TextView slideDescriptionView = (TextView) view.findViewById(R.id.slide_desc);

        slideImageView.setImageResource(slide_images[position]);
        slideHeadingView.setText(slide_headings[position]);
        slideDescriptionView.setText(slide_descp[position]);

        container.addView(view);
        return view;

    }

    // to prevent any types of errors
    @Override
    public void destroyItem(ViewGroup continer, int position, Object object) {
        continer.removeView((RelativeLayout) object);
    }
}