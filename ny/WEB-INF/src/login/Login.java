/**
 * 
 */
package login;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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

	public static int confirm(MySQLTable ids, String username, String uid) {
		if (!isValidLogin(username))
			return 1;
		if (!loginExist(ids, username))
			return 2;

		ResultSet rs = null;
		try {
			rs = ids.getValue(new String[] {"name"}, new Object[] {username},
					new String[] { "status" });			
			if (!rs.next())
				return -1;
			int status = rs.getInt("status");
			if (status != 0)
				return 3;
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		finally {
			if(rs != null) {
				ids.closeResultSet(rs);
				rs = null;
			}
		}

		if (!isValidCookie(ids, username, uid))
			return 4;

		ids.assyncUpdate(new String[] {"name"}, new Object[] {username}, new String[] { "status" },
				new Object[] { 1 });

		return 0;
	}

	public static String getHostByName(String inetAddress) {
		try {
			InetAddress ia = InetAddress.getByName(inetAddress);
			return addressToString(ia.getAddress());
		}
		catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static boolean isValidCookie(MySQLTable ids, String login, String ycookie) {
		if (!isValidLogin(login))
			return false;
		if (!loginExist(ids, login))
			return false;
		ResultSet rs = null;
		try {
			rs = ids.getValue(new String[] {"name"}, new Object[] {login}, new String[] {
					"ycookie", "cookie_expires" });			
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
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		finally {
			if(rs != null) {
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

	public static int login(MySQLTable ids, String name, String password, String ip,
			String ycookie[]) {
		if (!isValidLogin(name))
			return 1;
		if (!loginExist(ids, name))
			return 3;
		if (!isValidPassword(password))
			return 2;
		
		ResultSet rs = null;
		try {
			rs = ids.getValue(new String[] {"name"}, new Object[] {name}, new String[] {
					"password", "status" });			
			if (!rs.next())
				return -1;
			String password1 = rs.getString("password");
			int status = rs.getInt("status");
			if (password1 == null || !password1.equals(password))
				return 4;
			if (status == 0)
				return 5;
			if (status == 2)
				return 6;
			if (status != 1)
				return -1;
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		finally {
			if(rs != null) {
				ids.closeResultSet(rs);
				rs = null;
			}
		}

		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		}
		catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		md5.reset();
		md5.update(password.getBytes());
		ycookie[0] = Hex.toString(md5.digest());

		String ip1 = getHostByName(ip);
		ids.assyncUpdate(new String[] {"name"}, new Object[] {name}, new String[] { "ycookie", "ip",
				"cookie_expires", "last_access" },
				new Object[] {
						ycookie[0],
						ip1,
						new Timestamp(System.currentTimeMillis() + 24 * 60 * 60
								* 1000),
						new Timestamp(System.currentTimeMillis()) });

		return 0;
	}

	public static boolean loginExist(MySQLTable ids, String login) {
		return ids.contains("name", login);
	}

	public static int logout(MySQLTable ids, String name, String ycookie) {
		if (!isValidLogin(name))
			return 1;
		if (!loginExist(ids, name))
			return 2;
		if (!isValidCookie(ids, name, ycookie))
			return 3;
		
		ids.assyncUpdate(new String[] {"name"}, new Object[] {name}, new String[] { "ycookie" },
				new Object[] { "0" });
		return 0;
	}

	public static void main(String args[]) {
		//String host = args[0];
		//System.out.println(getHostByName(host));
	}

	public static int newLogin(MySQLTable ids, String name, String password, String email,
			String ip) {
		if (!isValidLogin(name))
			return 1;
		if (!isValidPassword(password))
			return 2;
		if (!isValidEMail(email))
			return 3;
		if (loginExist(ids, name))
			return 4;
		String ip1 = getHostByName(ip);
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		}
		catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		md5.reset();
		md5.update(password.getBytes());
		byte[] hash = md5.digest();
		String ycookie = Hex.toString(hash);
		Timestamp cookieExpires = new Timestamp(System.currentTimeMillis() + 24
				* 60 * 60 * 1000);
		
		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
		
		ids.assyncInsert(new Object[] { name, currentTimestamp, 0, 192, 0, password,
				ycookie, email, ip1, cookieExpires, currentTimestamp, 0, null,
				null, 1, 2, 1, 0, 0, 1});

		Properties p = new Properties();
		p.put("mail.smtp.host", "smtp.pro-cheats.com");

		Session session = Session.getInstance(p, null);
		MimeMessage msg = new MimeMessage(session);

		String emailMsg = "\r\n";
		emailMsg += "Click (or copy and paste in your web browser) the follow link for confirm your register\r\n";
		emailMsg += "\r\n";
		emailMsg += "http://www.pro-cheats.com/ny/register.jsp?action=confirm&username="
				+ name + "&uid=" + ycookie + "\r\n";
		emailMsg += "\r\n";

		try {
			// "de" e "para"!!
			msg.setFrom(new InternetAddress("webmaster@pro-cheats.com"));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(
					email));

			// nao esqueca da data!
			// ou ira 31/12/1969 !!!
			msg.setSentDate(new Date());

			msg.setSubject("Register Confirmation");

			msg.setText(emailMsg);

			// evniando mensagem (tentando)
			Transport.send(msg);
		}
		catch (AddressException e) {
			e.printStackTrace();
			return 5;
		}
		catch (MessagingException e) {
			e.printStackTrace();
			return 6;
		}

		return 0;
	}

}
