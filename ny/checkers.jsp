<%@page pageEncoding="Cp1252" contentType="text/html; charset=Cp1252"%>
<%@page import="login.*, data.*, core.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=Cp1252"/>
<title>Yahoo Checkers</title>
</head>
<% 
	String agent = request.getHeader("agent");
	String room = request.getParameter("room");
	String intl_code = request.getParameter("intl_code");
	if(intl_code == null)
		intl_code = "us";

	Cookie[] cookies = request.getCookies();
	String cookie = "";
	String ycookie = "";
	for(int i = 0; i < cookies.length; i++){
		String cookieName = cookies[i].getName();
		if(cookieName.equals("cookie"))
			cookie = cookies[i].getValue();
		else if(cookieName.equals("ycookie"))
			ycookie = cookies[i].getValue();
	}
	
	if(!cookie.startsWith("id=")){
		response.setStatus(302);
		response.setHeader("Location", "/ny/login.jsp?redirect_url=%2Fny%2Fcheckers.jsp%3fgame=checkers%26room=" + room + "%26intl_code=" + intl_code);
		return;
	}
	
	String username = cookie.substring(3);
	
	MySQLTable ids = Initializer.selfInstance.ids;
	
	if(!Login.isValidCookie(ids, username, ycookie)){
		response.setStatus(302);
		response.setHeader("Location", "/ny/login.jsp?redirect_url=%2Fny%2Fcheckers.jsp%3fgame=checkers%26room=" + room + "%26intl_code=" + intl_code);
		return;	
	}
%>
<body>
<applet code="y.k.YahooCheckers" name="ygames_applet" codebase="http://saddam.virtuaserver.com.br/" archive="client.jar" width="100%" height="100%">
<param name="port" value="11999">
<param name="cookie" value="<%out.print(cookie);%>">
<param name="uselogin" value="0">
<param name="agent" value="<%out.print(agent);%>">
<param name="ycookie" value="<%out.print(ycookie);%>">
<param name="logsentmessages" value="0">
<param name="logreceivedmessages" value="0">
<param name="yport" value="<%out.print(room);%>">
<param name="ldict_url" value="/yog/y/k/us-t4.ldict">
<param name="host" value="saddam.virtuaserver.com.br">
<param name="ratingmilestones" value="2100|1800|1500|1200|0">
<param name="intl_code" value="<%out.print(intl_code);%>">
</applet>
</body>
</html>
