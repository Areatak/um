package com.areatak;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Component;
import org.zeromq.ZMQ;

import javax.annotation.PostConstruct;
import java.nio.ByteBuffer;

/**
 * Created by Mehrdad on 02/06/2017.
 */
@Component
public class Main {

    @PostConstruct
    public void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ZMQ.Context context = ZMQ.context(1);

                // Socket to talk to server
                System.out.println("Connecting to hello world server");


                ZMQ.Socket socket = context.socket(ZMQ.SUB);

                socket.connect("tcp://localhost:8555");
                socket.subscribe("hashblock".getBytes());
                socket.subscribe("hashtx".getBytes());
                socket.subscribe("rawblock".getBytes());
                socket.subscribe("rawtx".getBytes());

                boolean connected = true;
                while (connected) {
                    byte[] headerBytes = socket.recv(0);
                    byte[] bodyBytes = socket.recv(1);
                    byte[] sequenceBytes = socket.recv(2);
                    //reverse the array because little endian difference between C++ and java
                    //sequence number originally is C++ unsigned int
                    ArrayUtils.reverse(sequenceBytes);

                    System.out.println(
                            new String(headerBytes) +
                                    "[" +
                                    unsignedIntToLong(sequenceBytes) +
                                    "] " +
                                    ":{" +
                                    new String(Hex.encodeHex(bodyBytes)) + "}");
                }

                socket.close();
                context.term();
            }
        }).start();
    }

    /**
     * Converts a 4 byte array of unsigned bytes to an long
     *
     * @param b an array of 4 unsigned bytes
     * @return a long representing the unsigned int
     */
    public final long unsignedIntToLong(byte[] b) {
        long l = 0;
        l |= b[0] & 0xFF;
        l <<= 8;
        l |= b[1] & 0xFF;
        l <<= 8;
        l |= b[2] & 0xFF;
        l <<= 8;
        l |= b[3] & 0xFF;
        return l;
    }

    /**
     * Converts a two byte array to an integer
     *
     * @param b a byte array of length 2
     * @return an int representing the unsigned short
     */
    public final int unsignedShortToInt(byte[] b) {
        int i = 0;
        i |= b[0] & 0xFF;
        i <<= 8;
        i |= b[1] & 0xFF;
        return i;
    }
}