package com.areatak;

import wf.bitcoin.javabitcoindrpcclient.*;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by win_10 on 4/3/2017.
 */
public class TestBitcoindJsonRPC {

    /**
     * This function is just for preventing code lost. to use this test function:
     * 1- Run a bitcoind with rpc configured.
     * 2- create a new maven project and add "JavaBitcoindRpcClient" dependency to that.
     * 3- create a main function in your new project and copy these lines.
     */
    public void main() {
        BitcoindRpcClient bitcoin = null;
        try {
            bitcoin = new BitcoinJSONRPCClient("http://rpcuser:rpcpassword@localhost:8332");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        List unspentList = bitcoin.listUnspent();
        System.out.println(unspentList.size());

        //Farnoosh: 960115
        //TODO: for receive event handling see the receiveCoins method below.



        List<BitcoindRpcClient.TxInput> inputList = new ArrayList<BitcoindRpcClient.TxInput>();
        List<BitcoindRpcClient.TxOutput> outputList = new ArrayList<BitcoindRpcClient.TxOutput>();

        BitcoindRpcClient.BasicTxInput input1 = new BitcoindRpcClient.BasicTxInput("1e9d3536fb2971ec270be5436d6f1c8165c8148b193884d9749bfee1810e2526",0);
        inputList.add(input1);


        BitcoindRpcClient.BasicTxOutput output1 = new BitcoindRpcClient.BasicTxOutput("muSoCwu5zSoQyFSv14P4kHXYKzjbmnWUG1",9.8);
        outputList.add(output1);

        String tx = bitcoin.createRawTransaction(inputList,outputList);

        List<BitcoindRpcClient.ExtendedTxInput> extendedTxInputList = new ArrayList<BitcoindRpcClient.ExtendedTxInput>();
        List<String> list = new ArrayList<String>();
        String signedTx = bitcoin.signRawTransaction(tx,null,null);

        String txId = bitcoin.sendRawTransaction(signedTx);

        System.out.println("tx sent, id= " + txId);

    }

    public void receiveCoins(final BitcoindRpcClient bitcoin) {
        final BitcoinAcceptor acceptor = new BitcoinAcceptor(bitcoin);

        System.out.println("Send bitcoins to " + bitcoin.getNewAddress("NewAccount"));

        acceptor.addListener(new ConfirmedPaymentListener() {
            HashSet processed = new HashSet();

            @Override
            public void confirmed(BitcoindRpcClient.Transaction transaction) {
                if (!processed.add(transaction.txId()))
                    return; // already processed

                System.out.println("Payment received, amount: " + transaction.amount() + "; account: " + transaction.account());
                try {
                    if (bitcoin.getBalance("NewAccount") >= 10)
                        acceptor.stopAccepting();
                } catch (BitcoinRPCException ex) {
                    ex.printStackTrace();
                }
            }

        });
        acceptor.run();
    }
}