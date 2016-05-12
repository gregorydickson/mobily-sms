<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page import="mobily.sms.java.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<%
	Mobily m=new Mobily();
	if(request.getParameter("mode")!= null && request.getParameter("mode").equals("check")){
		String senderName=request.getParameter("txtSender");
		String password=request.getParameter("txtSender");
		String senderID=request.getParameter("txtSender");
		m.checkSender(senderName,password,senderID);
		String output=m.getMessage();
		out.println(output);
	}
	
%>
<body>
	<form action="ActivateSender.jsp">
	<table border="1">
		<tr>
			<td colspan="2"  align="center" >Mobily Check Sender Form</td>
			
		</tr>
		<tr>
			<td>Sender Name:</td>
			<td><input type="Text" name="txtSender"/></td>
		</tr>
		<tr>
			<td>Password:</td>
			<td><input type="Text" name="txtPassword"/></td>
		</tr>
		<tr>
			<td>Sender ID:</td>
			<td><input type="Text" name="txtSenderID"/></td>
		</tr>
		<tr>
			<td colspan="2" align="center"><input type="submit" value="Check Sender Name"/></td>
		</tr>
		
		
	</table>
	<input type="hidden" name="mode" value ="check"/>
</form>
</body>
</html>