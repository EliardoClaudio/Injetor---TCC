package br.ufpe.cin.support;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class HardwareRepair {
	private static byte[] parseHexString(String string) {
		byte[] bytes = new byte[string.length() / 2];
		for (int i = 0, j = 0; i < string.length(); i += 2, j++) {
			bytes[j] = (byte) Integer.parseInt(string.substring(i, i + 2), 16);
		}
		return bytes;
	}

	private static byte[] getMagicBytes(String macAddress) throws IOException {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		for (int i = 0; i < 6; i++) {
			bytes.write(0xff);
		}

		byte[] macAddressBytes = parseHexString(macAddress);
		for (int i = 0; i < 16; i++) {
			bytes.write(macAddressBytes);
		}
		bytes.flush();
		return bytes.toByteArray();
	}

	private static InetAddress getMulticastAddress() throws UnknownHostException {
		return InetAddress.getByAddress(new byte[] { -1, -1, -1, -1 });
	}

	private static void send(byte[] bytes, InetAddress addr, int port) throws IOException {
		DatagramPacket p = new DatagramPacket(bytes, bytes.length, addr, port);
		DatagramSocket datagramSocket = new DatagramSocket();
		datagramSocket.send(p);
		datagramSocket.close();
	}

	public static void wakeUp(String macAddress, int port) throws IOException {
		byte[] bytes = getMagicBytes(macAddress);
		InetAddress address = getMulticastAddress();
		send(bytes, address, port);
	}

	public static void main(String[] args) throws IOException {
		String macAddress = "1078D2B8AB34";
		int port = 9;
		wakeUp(macAddress, port);
	}
}