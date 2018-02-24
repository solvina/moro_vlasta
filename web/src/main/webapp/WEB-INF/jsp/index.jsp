<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>

</head>
<body>
<form:form method="post" action="/moro/addMessage" modelAttribute="message">
    <form:errors path="message" cssStyle="color: #D8000C;background-color: #FFD2D2;"/><br>
    <form:textarea cols="80" rows="15" path="message" placeholder="Just enter some smart text."/>
    <br>
    <input type="submit" value="send"/>

</form:form>
<ul>
<c:forEach items="${messages}" var="message">
     <li><c:out value="${message.text}"/></li>
</c:forEach>
</ul>
</body>
</html>