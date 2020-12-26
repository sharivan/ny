<%@page import="core.*, data.*, login.*, javax.servlet.http.*"%>
<% 
	String action = request.getParameter("action");
	String redirect_url = request.getParameter("redirect_url");
	String username = request.getParameter("username");
	String msgError = null;
	
	MySQLTable ids = Initializer.selfInstance.ids;
	
	if(action != null){
		if(action.equals("login")){			
			String password = request.getParameter("password");
			String ip = request.getRemoteHost();
			String ycookie[] = new String[1];
			int result = Login.login(ids, username, password, ip, ycookie);
			switch(result){
			case 0:
				response.setStatus(302);
				if(redirect_url != null){
					response.setHeader("Location", redirect_url);
				}
				Cookie cookie1 = new Cookie("cookie", "id=" + username);
				Cookie cookie2 = new Cookie("ycookie", ycookie[0]);
				response.addCookie(cookie1);
				response.addCookie(cookie2);
				return;		
			case 1:
				msgError = "Login inválido";
				break;
			case 2:
				msgError = "Senha inválida";
				break;
			case 3:
				msgError = "Login não existe";
				break;
			case 4:
				msgError = "Senha incorreta";
				break;
			case 5:
				msgError = "Confirmação por e-mail pendente";
				break;
			case 6:
				msgError = "Login desativado";
				break;
			default:
				msgError = "Erro interno do servidor";
			}
		}
		else if(action.equals("logout")){
			Cookie[] cookies = request.getCookies();			
			String ycookie = null;
			for(int i = 0; i < cookies.length; i++){
				String cookieName = cookies[i].getName();
				if(cookieName.equals("ycookie")){
					ycookie = cookies[i].getValue();
					break;
				}					
			}
			Login.logout(ids, username, ycookie);
			if(redirect_url != null){
				response.setStatus(302);
				response.setHeader("Location", redirect_url);
			}	
			return;		
		}
		else if(action.equals("back")){
			if(redirect_url != null)
				response.setHeader("Location", redirect_url);	
		}
	}
 %>
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
<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<div id="Layer1" style="position:absolute; left:1px; top:255px; width:524px; height:18px; z-index:1">
<div align="center"><a href="register.jsp">Sign
  Up</a></div>
</div>
<div id="Layer2" style="position:absolute; left:527px; top:255px; width:530px; height:18px; z-index:2">
  <div align="center"><a href="login.jsp?action=back">Back</a> </div>
</div>
<br>
<%
if(msgError != null)
	out.println(msgError);
%>
<form name="form1" method="post" action="">
	<input type="hidden" name="action" value="login">
  <table width="100%" border="0" cellspacing="0" cellpadding="1" bgcolor="#000000">
    <tr valign="top"> 
      <td> <table width="100%" border="0" cellspacing="0" cellpadding="4" bgcolor="#CCCCCC">
          <tr bgcolor="#000000"> 
            <td width="20%" valign="top"><div align="center"><font color="#CCCCCC"><b>Sign 
              In</b></font></div></td>
          </tr>
          <tr valign="top"> 
            <td width="20%"><div align="center"><b>User Name</b><br> 
                <input name="username" type="text" value="<%out.print(username != null ? username : "");%>" size="32"> 
                </div>              <p align="center"><b>Password</b><br>
                <input name="password" type="password" size="25">
              </p>
              <p align="center"> 
                <input type="submit" name="Submit" value="Sign In">
            </p></td>
          </tr>
        </table></td>
    </tr> 
  </table>
</form>
</body>
</html>
