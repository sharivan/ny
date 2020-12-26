/**
 * 
 */
package y.net;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * @author Saddam Hussein
 * 
 */
public class MacAddress {

	private static String[]	hexDigits	= { "0", "1", "2", "3", "4", "5", "6",
			"7", "8", "9", "A", "B", "C", "D", "E", "F" };

	public static byte[] getMacAddress() {
		try {
			return getMacAddress(InetAddress.getLocalHost());
		}
		catch (UnknownHostException e) {
		}
		return null;
	}

	public static byte[] getMacAddress(InetAddress inetAddress) {
		try {
			NetworkInterface element = NetworkInterface
					.getByInetAddress(InetAddress.getLocalHost());
			if (element == null)
				return null;
			return element.getHardwareAddress();
		}
		catch (SocketException e) {
		}
		catch (UnknownHostException e) {
		}
		return null;
	}

	public static String macToString(byte[] b) {
		if (b != null && b.length > 0) {
			String result = toHex(b[0]);
			for (int i = 1; i < b.length; i++)
				result += ":" + toHex(b[i]);
			return result;
		}
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(macToString(getMacAddress()));
	}

	public static String toHex(byte b) {
		return hexDigits[(b & 0xf0) >>> 4] + hexDigits[b & 0x0f];
	}

}
