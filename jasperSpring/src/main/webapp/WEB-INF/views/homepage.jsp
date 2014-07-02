<%@ page language="java" contentType="text/html; charset=ISO-8859-1"

    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%> 


<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Insert title here</title>
</head>
<body>
<form:form method="POST" commandname="departments" action="/report/result">
<table>
<tbody>
<c:forEach var="client"  items="${clientlist}" >
<tr>
	<td>${client.clientname}</td> n 

<c:forEach var="department" items="${client.departments}">
 	<td>${department.departmentName}</td>
 	<td><form:checkbox path="departmentIDs" value= " ${department.departmentID}"></form:checkbox></td> 
 <input type="hidden" value="on" name="_active"/> 
</c:forEach>

</tr>
</c:forEach>
<tr>  
        <td colspan="2">  
            <input type="submit" value="Submit">  
        </td>  
    </tr>  
</tbody>
</table>

</form:form>  

<title>Form</title>
</head>
<body>
	<form:form method="POST" action="/report/generate">
		<table>
			<tr>
				<td><form:label path="type">type</form:label></td>
				<td><form:input path="type" /></td>
			</tr>
			<tr>
				<td><form:label path="startdate">startdate</form:label></td>
				<td><form:input path="startdate" /></td>
			</tr>
			<tr>
				<td><form:label path="enddate">enddate</form:label></td>
				<td><form:input path="enddate" /></td>
			</tr>
			<tr>
				<td><form:label path="para">para</form:label></td>
				<td><form:input path="para" /></td>
			</tr>
			<tr>
				<td><form:label path="para">para</form:label></td>
				<td><form:input path="para" /></td>
			</tr>
			<tr>
				<td><form:label path="para">para</form:label></td>
				<td><form:input path="para" /></td>
			</tr>
			<tr>
				<td colspan="4"><input type="submit" value="generate" /></td>
			</tr>
		</table>
	</form:form>

</body>
</html>