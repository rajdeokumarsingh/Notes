<%@ page session="true" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html>
	<head>
		<title>New comment</title>
	</head>
	<body>
		<form method="post" action="newcomment.htm">
		<spring:bind path="comment">
			<ul>
				<c:forEach items="${status.errorMessages}" var="errorMessage">
					<li style="color:red"><c:out value="${errorMessage}"/></li>
				</font>			
			</c:forEach>
			</ul>
			<table>	
				<tr>
					<td><label>Email</label></td><td><input type="text" name="email" value="<c:out value="${comment.email}"/>"/></td>
				</tr>	
				<tr>
					<td><label>Subject</label></td><td><input type="text" name="subject" value="<c:out value="${comment.subject}"/>"/></td>
				</tr>
				<tr>
					<td><label>Body</label></td><td><textarea name="body" cols="15" rows="5"><c:out value="${comment.body}"/></textarea></td>
				</tr>
				<tr>
					<td><label>Control text</label></td><td><input type="text" name="j_captcha_response" /></td> 
				</tr>
				<tr>
					<td colspan="2"><img src="captcha.htm" /></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="submit" value="submit" /></td>
				</tr>							
			</table>
			</spring:bind>
		</form>		
	</body>
</html>