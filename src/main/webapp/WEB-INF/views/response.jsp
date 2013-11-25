<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>EHRI helpdesk</title>
</head>
<body>
<h2>EHRI integrated helpdesk</h2>
<form:form method="GET" action="response.html">
 
    <table>
    <tr>
        <td><form:label path="input">Introduce your text:</form:label></td>
        </tr>
        <tr>
        <td><form:textarea rows="10" cols="50" path="input" /></td>
    </tr>

    <tr>
        <td colspan="2">
            <input type="submit" value="Submit"/>
        </td>
    </tr>
</table> 
     
</form:form>

<p>Query-Results</p>

<!-- 
1. ${command.input}<br>

2. ${list2display}
 -->
<c:forEach items="${list2display}" var="queryresult"> 
	
		<p>${list2display.result}</p>
	
</c:forEach>





</body>
</html>