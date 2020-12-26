<%@page import="java.util.*, javax.servlet.*, javax.servlet.http.*, login.*, data.*, core.*"%>
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" type="text/JavaScript">
<!--
function MM_reloadPage(init) {  //reloads the window if Nav4 resized
  if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
    document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}
  else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
}
MM_reloadPage(true);
//-->
</script>
</head>
<body bgcolor="#FFFFFF" marginwidth="0" marginheight="0" leftmargin="0" topmargin="0">

<%
	boolean logged = false;
	String username = null;
	Cookie[] cookies = request.getCookies();
	String cookie = null;
	String ycookie = null;
	if(cookies != null)
	for(int i = 0; i < cookies.length; i++){
		String cookieName = cookies[i].getName();
		if(cookieName.equals("cookie"))
			cookie = cookies[i].getValue();
		else if(cookieName.equals("ycookie"))
			ycookie = cookies[i].getValue();
	}
	
	MySQLTable ids = Initializer.selfInstance.ids;
	
	if(cookie != null && cookie.startsWith("id=")){
		username = cookie.substring(3);
		logged = Login.isValidCookie(ids, username, ycookie);
	}
	

%>

<div id="Layer1" style="position:absolute; left:13px; top:90px; width:1046px; height:502px; z-index:1">
  
    <p>&nbsp;</p>
    <p><strong><font size="7">Welcome to New Yahoo!</font><font size="7"></font></strong></p>
    <p>&nbsp;</p>
    <p>Games:</p>
    <p>&nbsp;</p>
    <p>&nbsp;</p>
    <p><div align="center">
    <table width="100%" border="0" cellspacing="0" cellpadding="4">
    <tr> 
    <td><a href="rooms.jsp?game=checkers">Checkers</a></td>
    </tr> 
    </table>
    </div></p>
  
</div>
<div id="Layer2" style="position:absolute; left:49px; top:206px; width:975px; height:336px; z-index:2"></div>
<table width="100%" border="0" cellspacing="0" cellpadding="4">
  <tr> 
    <td rowspan="2" bgcolor="#666666">&nbsp; 
    </td>
    <td width="100%" bgcolor="#666666"><font color="#CCCCCC"> 
      New Yahoo!</font></td>
  </tr>
  <tr> 
    <td bgcolor="#CCCCCC">      <table width="100%" border="0" cellspacing="1" cellpadding="2">
        <tr> 
<%      
	if(logged){%>  
          <td align="center" width="20%"><a href="login.jsp?redirect_url=index.jsp&action=logout&username=<%out.print(username);%>">Logout</a></td>
	<%}else{%>
		  <td align="center" width="20%"><a href="login.jsp?redirect_url=index.jsp">Login</a></td>
	<%}          
%>         
          <td align="center" width="20%"><a href="#">About</a></td>
        </tr>
      </table>
</td>
  </tr>
</table>
<br>
</body>
</html>

