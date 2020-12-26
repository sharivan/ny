/**
 * 
 */
package login;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import data.MySQLTable;

/**
 * @author Saddam
 * 
 */
public class Login {

	public static String addressToString(byte[] address) {
		if (address.length > 0) {
			String result = String.valueOf(address[0] & 0xff);
			for (int i = 1; i < address.length; i++)
				result += "." + (address[i] & 0xff);
			return result;
		}
		return null;
	}

	public static String getHostByName(String inetAddress) {
		try {
			InetAddress ia = InetAddress.getByName(inetAddress);
			return addressToString(ia.getAddress());
		}
		catch (UnknownHostException e) {
			return null;
		}
	}

	public static boolean isValidCookie(MySQLTable ids, String login,
			String ycookie) {
		if (!isValidLogin(login))
			return false;
		if (!loginExist(ids, login))
			return false;
		ResultSet rs = null;
		try {
			rs = ids.getValue(new String[] { "name" }, new Object[] { login },
					new String[] { "ycookie", "cookie_expires" });
			if (!rs.next())
				return false;
			String ycookie1 = rs.getString("ycookie");
			Timestamp cookie_expires = rs.getTimestamp("cookie_expires");
			if (System.currentTimeMillis() > cookie_expires.getTime())
				return false;
			if (ycookie1 == null || ycookie1.equals("0")
					|| !ycookie1.equals(ycookie))
				return false;
			return true;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		finally {
			if (rs != null) {
				ids.closeResultSet(rs);
				rs = null;
			}
		}
	}

	public static boolean isValidEMail(String email) {
		int p = email.indexOf("@");
		if (p == -1)
			return false;
		String name = email.substring(0, p);
		String host = email.substring(p + 1);
		return isValidName(name) && isValidName(host);
	}

	public static boolean isValidLogin(String login) {
		if (login == null || login.equals(""))
			return false;
		return isValidName(login);
	}

	public static boolean isValidName(String name) {
		for (int i = 0; i < name.length(); i++) {
			char c = name.charAt(i);
			if (c >= 'a' || c <= 'z')
				continue;
			if (c >= 'A' || c <= 'Z')
				continue;
			if (c >= '0' || c <= '9')
				continue;
			if (c == '_' || c == '.')
				continue;
			return false;
		}
		return true;
	}

	public static boolean isValidPassword(String password) {
		if (password == null || password.length() < 6 || password.length() > 16)
			return false;
		return isValidName(password);
	}

	public static boolean loginExist(MySQLTable ids, String login) {
		return ids.contains("name", login);
	}

}
