<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Form</title>
</head>
<body>

	<table>
			<tr>
				<c:forEach var = "reportType" items = "${reportTypeList}"> 
					<td>
					${reportType}
					</td>				
				</c:forEach>
			</tr>
			<tr>
			<td>
				${test}
				</td>
			</tr>
	</table>
	
	
	<%-- 
  <form:form method="POST" action="/report/delete">
		<table>
		
			<tr>
				<td><form:label path="startdate">startdate</form:label></td>
				<td><form:input path="startdate" /></td>
			</tr>
			<tr>
				<td><form:label path="enddate">enddate</form:label></td>
				<td><form:input path="enddate" /></td>
			</tr>
			<tr>
				<td><form:label path="para">departments</form:label></td>
				<td><form:input path="para" /></td>
			</tr>
			<tr>
				<td><form:label path="para">PDF</form:label></td>
				<td><form:input path="para" type = checkbox/></td>
			</tr>
			<tr>
				<td><form:label path="para">excel</form:label></td>
				<td><form:input path="para" type = "checkbox" /></td>
			</tr>
			<tr>
				<td colspan="4"><input type="submit" value="delete" /></td>
			</tr>
		</table>
	</form:form>
	--%>
</body>
</html>