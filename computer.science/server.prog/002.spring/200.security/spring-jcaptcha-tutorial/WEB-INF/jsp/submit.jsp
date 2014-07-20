<%@ page session="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html>
	<head>
		<title>Comment submitted</title>
	</head>
	<body>
		<h1>Comment has been submitted successfully!</h1>
		<spring:bind path="comment">
			<p>Email: <c:out value="${comment.email}"/></p>
			<p>Subject: <c:out value="${comment.subject}"/></p>
			<pre><c:out value="${comment.body}"/></pre>			
		</spring:bind>
	</body>
</html>

