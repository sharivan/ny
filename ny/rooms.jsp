<%@page pageEncoding="Cp1252" contentType="text/html; charset=Cp1252" %>
<%@page import="core.*, java.sql.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=Cp1252"/>
<title></title>
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
<body>
<div id="Layer1" style="position:absolute; left:9px; top:65px; width:1044px; height:385px; z-index:1"></div>
New Yahoo! Checkers
</body>

<script>
java_vendor = "";
function launchAnteroom(name) {
    window.open("/ny/checkers.jsp?game=checkers&room=" + name, "", "top=2,left=2,toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=0,resizable=yes");
    return false;
}
</script>

<script language="javascript">


function initialize() {
this.name = "yahoo_games_home";
}

function lobbyopen(name) {
    return launchAnteroom(name);
}

initialize();

</script>
<%
			ResultSet rs = Initializer.selfInstance.checkers_rooms.getAllValues();
			try {
				while(rs.next()){
					String name = rs.getString("name");
					String label = rs.getString("label");
					int idCount = rs.getInt("id_count");
					out.println("<br><a href=\"#stayhere\" onClick='return lobbyopen(\"" + name + "\");'>" + label + "</a> (" + idCount + ")</br>");
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			finally {
				Initializer.selfInstance.checkers_rooms.closeResultSet(rs);
				rs = null;
			}
%>
</html>
