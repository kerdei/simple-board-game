package hu.kerdei.uni.network;

import hu.kerdei.uni.Main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

public class GameNetwork {

    private static final int MCU_PORT = 42101;

    private DatagramSocket datagramSocket;
    private byte[] data;
    private int dataLength = 255;
    private InetAddress address;
    private int messageID;


    public GameNetwork(String IPAddress) throws IOException {
        Main.LOG.debug("Gamenetwork constructor");
        address = InetAddress.getByName(IPAddress);
        datagramSocket = new DatagramSocket();
        messageID = 11123;
    }

    public void communicateBoardWithMcu(String mcuString) throws IOException {
        Main.LOG.debug("communicateBoardWithMcu");
        sendBoardToMCU(mcuString);
        for (int actualID = -1; actualID != messageID; ) {
            sendBoardToMCU(mcuString);
            actualID = waitForReplyID();
            Main.LOG.debug("actual id", actualID);
            Main.LOG.debug("expected id", messageID);
        }
        Main.LOG.debug("communication done.");
        messageID++;
    }

    private void sendBoardToMCU(String mcuString) throws IOException {
        Main.LOG.debug("sending board info to MCU");

        String dataString = mcuString + "," + messageID + ",";
        data = dataString.getBytes();
        DatagramPacket datagramPacket = new DatagramPacket(data, data.length,
                address, MCU_PORT);
        datagramSocket.send(datagramPacket);

        Main.LOG.debug("data sent " + dataString + " " + address.getHostAddress() + " : " + MCU_PORT);
    }

    private int waitForReplyID() throws IOException {
        Main.LOG.debug("Waiting for reply");

        data = new byte[dataLength];
        DatagramPacket packet = new DatagramPacket(data, dataLength);
        try {
            datagramSocket.setSoTimeout(500);
            datagramSocket.receive(packet);
            data = packet.getData();
            Main.LOG.debug(new String(data, 0, packet.getLength()));
            int reply = Integer.valueOf(new String(data, 0, packet.getLength()));
            Main.LOG.debug("Reply:", reply);
            return reply;
        } catch (SocketTimeoutException e) {
            Main.LOG.debug("Socket timeout, resending message");
            return 0;
        }
    }
}
