package com.github.devnied.emvnfccard.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.github.devnied.emvnfccard.R;
import com.github.devnied.emvnfccard.activity.Global;
import com.github.devnied.emvnfccard.activity.SimplePayActivity;
import com.github.devnied.emvnfccard.adapter.MobileArrayAdapter;
import com.github.devnied.emvnfccard.utils.CroutonUtils;

public class FundraiserFragment extends android.support.v4.app.Fragment{
    Global mGlobal = Global.getInstance();

    static final String[] values = new String[] {"Landsbjörg", "Blái Naglinn", "SÁÁ Álfurinn"};

    /*ListView list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fundraiser2, container,false);
        list = (ListView) view.findViewById(R.id.list);
        list.setAdapter(new MobileArrayAdapter(getActivity(), values));
        return view;
    }*/

    GridView grid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fundgrid, container,false);
        grid = (GridView) view.findViewById(R.id.gridview);
        grid.setAdapter(new MobileArrayAdapter(getActivity(), values));


        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String tmp = null;
                if (position == 0) {
                    tmp = "Landsbjörg";
                } else if (position == 1) {
                    tmp = "Blái Naglinn";
                } else if (position == 2) {
                    tmp = "SÁÁ Álfurinn";
                }
                mGlobal.setFundraiser(tmp);
                CroutonUtils.display(getActivity(), "Þú valdir að fjárefla " + tmp, CroutonUtils.CoutonColor.ORANGE);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {

                        Intent intent = new Intent(getActivity(), SimplePayActivity.class);
                        startActivity(intent);


                    }
                }, 2000);

            }
        });

        return view;
    }




}

