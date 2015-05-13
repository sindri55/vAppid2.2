package com.github.devnied.emvnfccard.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.github.devnied.emvnfccard.R;
import com.github.devnied.emvnfccard.adapter.MobileArrayAdapter;

public class FundraiserFragment extends Fragment {

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
        return view;
    }

}

