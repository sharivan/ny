<%@page pageEncoding="Cp1252" contentType="text/html; charset=Cp1252"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=Cp1252"/>
<title>Yahoo Checkers</title>
</head>
<% 
	String nick = request.getParameter("nick");
	if(nick == null)
		nick = "guest" + (int)(99999 * Math.random());
 %>
<body>
<applet code="y.po.YahooPool" name="ygames_applet" codebase="http://www.pro-cheats.com/" archive="client.jar" width="100%" height="100%">
<param name="port" value="80">
<param name="host" value="www.pro-cheats.com">
<param name="yport" value="games.room.pool_br">
<param name="cookie" value="id=<%out.print(nick);%>">
<param name="uselogin" value="0">
<param name="ycookie" value=".">
<param name="logsentmessages" value="1">
<param name="logreceivedmessages" value="1">
<param name="room" value="pool_br">
<param name="path" value="ny/servlet/YahooPoolServlet">
<param name="update" value="1">
</applet>
</body>
</html>
