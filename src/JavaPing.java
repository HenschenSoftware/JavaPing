/*
 * Author: Dillon Henschen
 */

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class JavaPing {

	public static final int sendingPORT = 7777;
	public static final int echoPort = 7;
	public static final int numPings = 4;
	
	public static void main(String[] args) throws IOException {
	        
	    DatagramSocket echoSocket;
		echoSocket = new DatagramSocket(sendingPORT);
		echoSocket.setSoTimeout(1500); /* 1.5 seconds */
		
	    byte[] outputData = new byte[32];
	    byte[] inputData = new byte[outputData.length];

		InetAddress destAddr = InetAddress.getByName(args[0]);
		System.out.printf("Pinging %s with %d bytes of data:\n", destAddr.getHostAddress(), outputData.length);
		
		int packetsReceived = 0;
		long minTime = Integer.MAX_VALUE;
		long maxTime = Integer.MIN_VALUE;
		long total = 0;

		for (int i = 0; i < numPings; i++) {
			DatagramPacket sendpacket = new DatagramPacket(outputData, outputData.length, destAddr, echoPort);
			DatagramPacket receivePacket = new DatagramPacket(inputData, outputData.length);
			long startTime = System.currentTimeMillis();
			echoSocket.send(sendpacket);

	    	try {
				echoSocket.receive(receivePacket);
				long elapsedTimeMS = System.currentTimeMillis() - startTime;
				packetsReceived++;
				if (elapsedTimeMS < minTime) {
					minTime = elapsedTimeMS;
				}
				if (elapsedTimeMS > maxTime) {
					maxTime = elapsedTimeMS;
				}
				total += elapsedTimeMS;
				System.out.printf("Reply from %s: bytes=%d time=%dms\n", receivePacket.getAddress().getHostAddress(), 
						receivePacket.getLength(), elapsedTimeMS);

	    	} catch (SocketTimeoutException e) {
				System.out.println("Request timed out.");
			}
		}

		System.out.println();
		System.out.printf("Ping statistics for %s:\n", destAddr.getHostAddress());
		System.out.printf("\tPackets: Sent = %d, Received = %d, Lost = %d (%d%% loss),\n", numPings, packetsReceived, 
								(numPings - packetsReceived), ((numPings - packetsReceived)/numPings)*100);

		if (packetsReceived > 0) {
			System.out.println("Approximate round trip times in milli-seconds:");
			System.out.printf("\tMinimum = %dms, Maximum = %dms, Average = %2.3fms\n", minTime, maxTime, (double)total/4.0);
		}
    	echoSocket.close();
	}	
}