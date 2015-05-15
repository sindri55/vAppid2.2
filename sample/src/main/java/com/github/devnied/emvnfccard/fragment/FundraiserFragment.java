package com.github.devnied.emvnfccard.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.github.devnied.emvnfccard.R;
import com.github.devnied.emvnfccard.activity.Global;
import com.github.devnied.emvnfccard.activity.SimplePayActivity;
import com.github.devnied.emvnfccard.adapter.MobileArrayAdapter;

import static android.view.View.INVISIBLE;

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
        final Button more = (Button) view.findViewById(R.id.button_search);
        more.setVisibility(INVISIBLE);
        if(mGlobal.getFundraiser() != null) {
            more.setVisibility(View.VISIBLE);
            more.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle("Úrskrańing");
                    alertDialog.setMessage("Afskrá úr fjáröflun fyrir " + mGlobal.getFundraiser());
                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Afskrá", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mGlobal.setFundraiser(null);
                            more.setVisibility(INVISIBLE);


                        }
                    });
                    alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Hætta við", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                        }
                    });
                    alertDialog.show();

                }
            });
        }


        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String tmp = null;
                if (position == 0) {
                    tmp = "Landsbjörg";
                } else if (position == 1) {
                    tmp = "Bláa Naglann";
                } else if (position == 2) {
                    tmp = "SÁÁ Álfinn";
                }
                final String result = tmp;
                AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(getActivity());
                final EditText input = new EditText(getActivity());
                alertdialogbuilder
                        .setMessage("Sláðu inn kóða")
                        .setCancelable(true)
                        .setView(input)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface alertDialog, int id) {
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {

                                        mGlobal.setFundraiser(result);
                                        //CroutonUtils.display(getActivity(), "Þú valdir að styrkja " + result, CroutonUtils.CoutonColor.ORANGE);
                                        Intent intent = new Intent(getActivity(), SimplePayActivity.class);
                                        startActivity(intent);


                                    }
                                },0);

                            }
                        });
                AlertDialog alertDialog = alertdialogbuilder.create();
                alertDialog.show();



                /*mGlobal.setFundraiser(tmp);
                CroutonUtils.display(getActivity(), "Þú valdir að styrkja " + tmp, CroutonUtils.CoutonColor.ORANGE);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {

                        Intent intent = new Intent(getActivity(), SimplePayActivity.class);
                        startActivity(intent);


                    }
                }, 2000);*/

            }
        });

        return view;
    }




}

