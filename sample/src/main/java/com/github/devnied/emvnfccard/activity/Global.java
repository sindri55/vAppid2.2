package com.github.devnied.emvnfccard.activity;

/**
 * Created by Sindri on 13/05/15.
 */
public class Global {

    private String fundraiser;
    private static Global mInstance = new Global();
    public static Global getInstance(){return mInstance;}

    public String getFundraiser(){
        return fundraiser;
    }

    public void setFundraiser(String item){
        this.fundraiser = item;
    }
}
