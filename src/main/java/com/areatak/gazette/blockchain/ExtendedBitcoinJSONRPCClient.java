package com.areatak.gazette.blockchain;

import wf.bitcoin.javabitcoindrpcclient.BitcoinJSONRPCClient;
import wf.bitcoin.javabitcoindrpcclient.BitcoinRpcException;
import wf.bitcoin.javabitcoindrpcclient.BitcoinUtil;
import wf.bitcoin.krotjson.Base64Coder;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by asus on 4/16/2017.
 */
public class ExtendedBitcoinJSONRPCClient extends BitcoinJSONRPCClient {
    public ExtendedBitcoinJSONRPCClient(String rpcUrl) throws MalformedURLException { this(new URL(rpcUrl)); }
    public ExtendedBitcoinJSONRPCClient(URL rpc) { super(rpc); }
    public ExtendedBitcoinJSONRPCClient(boolean testNet) { super(testNet); }
    public ExtendedBitcoinJSONRPCClient() {
        super();
    }


    public String createExtendedRawTransaction(List<TxInput> inputs, List<ExtendedTxOut> outputs) throws BitcoinRpcException {
        List<Map> pInputs = new ArrayList();
        Iterator var4 = inputs.iterator();

        while(var4.hasNext()) {
            final TxInput txInput = (TxInput)var4.next();
            pInputs.add(new LinkedHashMap() {
                {
                    this.put("txid", txInput.txid());
                    this.put("vout", Integer.valueOf(txInput.vout()));
                }
            });
        }

        Map<String, String> pOutputs = new LinkedHashMap();
        Iterator var6 = outputs.iterator();

        while(var6.hasNext()) {
            ExtendedTxOut txOutput = (ExtendedTxOut)var6.next();
            if(!txOutput.isData()) {
                Double oldValue = Double.valueOf(pOutputs.getOrDefault(txOutput.address(),"0"));
                //if oldValue != 0 ==> the map contains the address with this value
                //if oldValue = ==> the map not contains this address.


                //oldValue = BitcoinUtil.normalizeAmount(oldValue + txOutput.amount());

                //try to trim decimal values to 8 floating numbers
                oldValue += txOutput.amount();
                oldValue = Math.floor(oldValue * 100000000) / 100000000; //in Bitcoin amounts are as Satoshis
                //String finalValue = new DecimalFormat("#.########").format(oldValue);
                oldValue = BitcoinUtil.normalizeAmount(oldValue);
                pOutputs.put(txOutput.address(),String.format("%.8f", oldValue));
            }else{
                pOutputs.put("data", txOutput.address());
            }
        }

        return (String)this.query("createrawtransaction", new Object[]{pInputs, pOutputs});
    }
}
