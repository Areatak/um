package com.areatak.gazette.blockchain;

import wf.bitcoin.javabitcoindrpcclient.BitcoindRpcClient;

/**
 * Created by asus on 4/16/2017.
 */
public class ExtendedTxOut extends BitcoindRpcClient.BasicTxOutput {
    private boolean isData = false;
    public ExtendedTxOut(String address, double amount) {
        super(address, amount);
    }
    public ExtendedTxOut(String address,double amount,boolean dataOutput){
        this(address,amount);
        this.isData = dataOutput;
    }

    public boolean isData() {
        return isData;
    }

    public void setData(boolean data) {
        isData = data;
    }
}
