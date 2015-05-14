package com.github.devnied.emvnfccard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.devnied.emvnfccard.R;


/**
 * Created by dabbi on 13.5.2015.
 */

public class MobileArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public MobileArrayAdapter(Context context, String[] values) {
        super(context, R.layout.fundraiser, R.id.label, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.fundraiser, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
        textView.setText(values[position]);


        // Change icon based on name
        String s = values[position];

        //System.out.println(s);

        if (s.equals("Landsbjörg")) {
            imageView.setImageResource(R.drawable.landsbjorg);
        } else if (s.equals("Blái Naglinn")) {
            imageView.setImageResource(R.drawable.blai);
        } else if (s.equals("SÁÁ Álfurinn")) {
            imageView.setImageResource(R.drawable.saa_alfurinn);
        } else {
            imageView.setImageResource(R.drawable.kr);
        }



        return rowView;

    }
}