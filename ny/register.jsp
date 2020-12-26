<%@page import="core.*, data.*, login.*, javax.servlet.http.*"%>
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

</head>
<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<div align="center">
  <% 
	String action = request.getParameter("action");
	String username = request.getParameter("username");
	String email = request.getParameter("email");
	String msgError = null;
	int result = 0;
	
	MySQLTable ids = Initializer.selfInstance.ids;
	
	if(action != null){
		if(action.equals("register")){			
			String password1 = request.getParameter("password1");
			String password2 = request.getParameter("password2");
			if(password1 != null && password1.equals(password2)){
				email = request.getParameter("email");
				String ip = request.getRemoteHost();
				result = Login.newLogin(ids, username, password1, email, ip);
				switch(result){
				case 0:
%>
  Foi enviado um e-mail de confirmação para
  <%out.print(email);%>
  .<br>
  <br>
  <a href="index.jsp">Home</a>
<%
					return;		
				case 1:
					msgError = "Login inválido";
					break;
				case 2:
					msgError = "Senha inválida";
					break;
				case 3:
					msgError = "e-mail inválido";
					break;
				case 4:
					msgError = "Login indisponível";
					break;
				default:
					msgError = "Erro interno do servidor";
				}
			}
			else{
				msgError = "Senhas não conferem";
				result = 5;
			}
		}
		else if(action.equals("confirm")){
			String uid = request.getParameter("uid");
			result = Login.confirm(ids, username, uid);
			switch(result){
			case 0:
%>
  Email verificado com sucesso.<br>
  <br>
  <a href="index.jsp">Home</a>
<%
				return;	
			case 1:
%>
  Invalid username.<br>
  <br>
  <a href="index.jsp">Home</a>
<%			
				break;
			case 2:
%>
  Username not exist.<br>
  <br>
  <a href="index.jsp">Home</a>
<%			
				break;
			case 3:
%>
  Username already registered.<br>
  <br>
  <a href="index.jsp">Home</a>
<%			
				break;
			case 4:
%>
  Invalid uid.<br>
  <br>
  <a href="index.jsp">Home</a>
<%			
				break;
			default:
					%>
  Internal error.<br>
  <br>
  <a href="index.jsp">Home</a>
<%	
				return;	
			}
		}
	}
 %>
  <%
	if(msgError != null)
		out.println(msgError);
%>
</div>
<form name="form1" method="post" action="">
  <table width="100%" border="0" cellpadding="8" bgcolor="#FFFFFF">
    <tr> 
      <td colspan="3" bgcolor="#CCCCCC">
        <b>Register</b></td>
    </tr>
    <tr valign="top"> 
      <td align="right" bgcolor="<%out.print(result == 1 || result == 4 ? "red" : "#CCCCCC" );%>" nowrap>
        User Name</td>
      <td width="89%" colspan="2" bgcolor="#FFFFFF">        <input type="text" name="username" value="<%out.println(username != null ? username : "");%>">        <font size="-1">The user name only allowed contains
        numbers, letters and the characters dot (.) and underline (_).</font></td>
    </tr>
    <tr valign="top"> 
      <td align="right" bgcolor="<%out.print(result == 5 || result == 2 ? "red" : "#CCCCCC" );%>">
        Password</td>
      <td colspan="2" bgcolor="#FFFFFF">        <p>
          <input type="password" name="password1">
          <font size="-1">with digits count &gt;=6 and digits count &lt;= 16</font></p>
      </td>
    </tr>
    <tr valign="top"> 
      <td align="right" bgcolor="<%out.print(result == 5 ? "red" : "#CCCCCC" );%>">Confirm<br>
      Password</td>
      <td colspan="2" bgcolor="#FFFFFF">        <input type="password" name="password2">        
      </td>
    </tr>
    <tr valign="top"> 
      <td align="right" bgcolor="<%out.print(result == 3 ? "red" : "#CCCCCC" );%>">Email</td>
      <td colspan="2" bgcolor="#FFFFFF">        <p>
        <input type="text" name="email"  value="<%out.println(email != null ? email : "");%>" size="50">
        <font size="-1">Input a valid e-mail for confirmation</font></p>
      </td>
    </tr>
    <tr valign="top" bgcolor="#FFFFFF"> 
      <td colspan="3"><input type="submit" name="Submit" value="Submit">
      <input name="action" type="hidden" value="register"></td>
    </tr>
  </table>
  <p align="center"><a href="register.jsp?action=back">Back</a> </p>
</form>
</body>
</html>
