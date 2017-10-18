package com.areatak.util;

import com.areatak.gazette.blockchain.ExtendedBitcoinJSONRPCClient;
import com.areatak.gazette.metadata.CoinType;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Properties;

/**
 * Created by Mehrdad on 14/06/2017.
 */
public class CoinUtils {
    private static Properties properties;
    private static ExtendedBitcoinJSONRPCClient bitcoin;

    static {
        properties = new Properties();
        try {
            properties.load(CoinUtils.class.getClassLoader().getResourceAsStream("blockchain.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String generateCoinPaymentUrl(CoinType coin, double amount, String address) {
        if (coin == CoinType.Bitcoin) {
            return String.format("bitcoin:%s?amount=%f", address, amount);
        } else if (coin == CoinType.Utabit) {
            return String.format("utabit:%s?amount=%f", address, amount);
        }
        return "";
    }

    public static long convertBitcoinToSatoshi(double btc) {
        return (long) (btc * 100000000);
    }

    public static long addCommissionToCoinAmount(double btc) {
        //TODO: Farnoosh - round up??!! or what?
        return (long) Math.ceil(btc * 1.05);
    }


    public static ExtendedBitcoinJSONRPCClient getBitcoinConnection() {
        if (bitcoin == null) {
            try {
                Logger.info("bitcoin", "try to connect to bitcoin");
                bitcoin = new ExtendedBitcoinJSONRPCClient(String.format("http://%s:%s@%s:%s",
                        properties.getProperty("blockchain.bitcoin.rpc.user"),
                        properties.getProperty("blockchain.bitcoin.rpc.password"),
                        properties.getProperty("blockchain.bitcoin.host"),
                        properties.getProperty("blockchain.bitcoin.rpc.port")));
                Logger.info("bitcoin", "connected to bitcoin");
            } catch (MalformedURLException e) {
                Logger.error("bitcoin", e.getMessage(), e);
            }
        }
        if (bitcoin == null)
            Logger.error("bitcoin", "null bitcoin connection");
        else
            bitcoin.getInfo();
        return bitcoin;
    }


}