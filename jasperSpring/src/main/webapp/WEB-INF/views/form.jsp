<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title> Form </title>
</head>
<body>
<form:form method="POST" action="/report/generateSummary">
   <table>
    <tr>
        <td><form:label path="library">Library</form:label></td>
        <td><form:input path="library" /></td>
    </tr>
    <tr>
        <td><form:label path="start_date">Start Date</form:label></td>
        <td><form:input path="start_date" /></td>
    </tr>
    <tr>
        <td><form:label path="end_date">End Date</form:label></td>
        <td><form:input path="end_date" /></td>
    </tr>
    <tr>
        <td colspan="4">
            <input type="submit" value="Generate Summary Report"/>
        </td>
    </tr>
</table>  
</form:form>
</body>
</html>