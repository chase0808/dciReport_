<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="../../assets/ico/favicon.ico">
    <title>Dashboard Template for Bootstrap</title>
    <!-- Bootstrap core CSS -->
    <link href="<c:url value = "/resources/bootstrap/css/bootstrap.min.css" />"  rel="stylesheet">
    <!-- Custom styles for this template -->
    <link href="<c:url value = "/resources/css/dashboard.css" />" rel="stylesheet">
    <!-- Just for debugging purposes. Don't actually copy this line! -->
    <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script type="text/javascript" src="<c:url value = "/resources/js/mktree.js" />"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script>
    var reportname;
    $(window).load(function(){
    $('#myModal').modal({
    keyboard: false,
    show: true
    });
    
    $('#selected').attr('checked','checked');
    var deptname = $('#selected').parent("label").text();
    var txt1 = "<p class = " + value +">" + deptname + "</p>";
    alert(txt1);
    $("#selectedClient").append(txt1);
    });
    
    $(document).ready(function(){
    
    $('#myModal').on('hidden.bs.modal', function (e) {
    //alert("show event fired!");
    $(this).find('form')[0].reset();
    $('#selectedClient').html("");
    collapseTree("tree1");
    //$(this).find('#selectedClient').val('');
    //$('form').find('input[type=date], input[type=checkbox], input[type=number], input[type=email], textarea').val('');
    });
    
    
    
    $("input[type='checkbox'][id!='_output']").change(function(){
    var value = $(this).val();
    var deptname = $(this).parent("label").text();
    if(this.checked){
    var txt1 = "<p class = " + value +">" + deptname + "</p>";
    
    $("#selectedClient").append(txt1);
    } else {
    $("p").remove("." + value);
    }
    });
    });
    </script>
    
    <link rel="stylesheet" href="<c:url value = "/resources/css/mktree.css" />" type="text/css">
  </head>
  <body>
    <!-- Modal -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header"> 
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="myModalLabel">Criteria</h4>
          </div>
          <div class="modal-body ">
            <form:form role="form" method="POST"  action="/report/generate" >
            <form:hidden path="reportid" value="${command.reportid}"></form:hidden>
              <div class="form-group">
                <c:forEach var="parameters"  items="${reportParaValue}" varStatus="i">
                
                <c:if test="${parameters.id == '1'}">
              <form:label for="start_date" path="para[${i.index}].value">Start Date</form:label>
              <form:hidden path="para[${i.index}].id" value="${parameters.id}"></form:hidden>
              
              
              <form:input type="date" class="form-control" id="start_date" path="para[${i.index}].value" value="${parameters.value[0]}" />
                </c:if>
                <c:if test="${parameters.id == '2'}">
              <form:label for="end_date" path="para[${i.index}].value">End Date</form:label>
              <form:hidden path="para[${i.index}].id" value="${parameters.id}"></form:hidden>
             
              
              <form:input type="date" class="form-control" id="end_date" path="para[${i.index}].value" value="${parameters.value[0]}"/>
                </c:if>
                <c:if test="${parameters.id == '3'}">
                <div class="container">
                  <div class="row">
                    <div class="col-md-3 panel1">
                      <div class="panel panel-default panel-primary ">
                        <div class="panel-heading">Select Client</div>
                        <div class="panel-body checkboxes">
                          <ul class="mktree" id="tree1">
                            <c:forEach var="client"  items="${clientlist}" >
                            <li>${client.clientname}
                              <c:forEach var="department" items="${client.departments}">
                              <ul>
                                <li>
                                  
                                  
                                  <label>
                                    ${department.departmentName}
                                    <c:set var="flag" scope="page" value="false"/>
                                    <c:forEach var="val" items="${parameters.value}">
                                    
                                    <c:choose>
                                    <c:when test="${val == department.departmentID}">
                                    
                                    <c:set var="flag" scope="page" value="true"/>
                                    
                                    </c:when>
                                    </c:choose>
                                    </c:forEach>
                                    <c:choose>
                                    
                                    <c:when test="${flag eq true}">
                                    
                                  <form:checkbox path="para[${i.index}].value" value= "${department.departmentID}" id="selected" checked="true"></form:checkbox>
                                  <form:hidden path="para[${i.index}].id" value="${parameters.id}"></form:hidden>
                                  </c:when>
                                  <c:otherwise>
                                  
                                <form:checkbox path="para[${i.index}].value" value= " ${department.departmentID}"></form:checkbox>
                                <form:hidden path="para[${i.index}].id" value="${parameters.id}"></form:hidden>
                                
                                
                                </c:otherwise>
                                </c:choose>
                                
                                
                                
                                
                              </label>
                              
                              
                              
                              
                            </li>
                          </ul>
                          </c:forEach>
                        </li>
                        </c:forEach>
                        
                      </ul>
                    </div>
                  </div>
                </div>
                <div class="col-md-3 panel2">
                  <div class="panel panel-default panel-primary">
                    <div class="panel-heading">Selected Client</div>
                    <div class="panel-body"  id="selectedClient" >
                      <c:forEach var="d" items="${selectedDepartment}">
                      <p class="${d.departmentID}">${d.departmentName}</p>
                      </c:forEach>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          
          </c:if>
          
          
          </c:forEach>
          
          <h4>Output Type</h4>
          <c:forEach var="outputID" items="${outputIDs}" >
          <label class="checkbox-inline">
            <c:if test="${outputID == '1'}">
            <c:set var="flag" scope="page" value="false"/>
            <c:forEach var="val" items="${transactionOuputIDs}">
            
            <c:choose>
            <c:when test="${val == outputID}">
            
            <c:set var="flag" scope="page" value="true"/>
            
            </c:when>
            </c:choose>
            </c:forEach>
            <c:choose>
            
            <c:when test="${flag eq true}">
            
          <form:checkbox path="output" value= " ${outputID}" checked="true"></form:checkbox>PDF
          </c:when>
          <c:otherwise>
          
        <form:checkbox path="output" value= " ${outputID}"></form:checkbox>PDF
        </c:otherwise>
        </c:choose>
        
        </c:if>
        <c:if test="${outputID == '2'}">
        <c:set var="flag" scope="page" value="false"/>
        <c:forEach var="val" items="${transactionOuputIDs}">
        
        <c:choose>
        <c:when test="${val == outputID}">
        
        <c:set var="flag" scope="page" value="true"/>
        
        </c:when>
        </c:choose>
        </c:forEach>
        <c:choose>
        
        <c:when test="${flag eq true}">
        
      <form:checkbox path="output" value= " ${outputID}" checked="true" id="_output"></form:checkbox>XLS
      </c:when>
      <c:otherwise>
      
    <form:checkbox path="output" value= " ${outputID}" id="_output"></form:checkbox>XLS
    </c:otherwise>
    </c:choose>
    </c:if>
  </label>
  </c:forEach>
  <div class="modal-footer">
    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
    <button type="submit" class="btn btn-primary">Generate</button>
  </div>
</div>
</form:form>
</div>
</div>
</div>
<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
<div class="container-fluid">
<div class="navbar-header">
<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
<span class="sr-only">Toggle navigation</span>
<span class="icon-bar"></span>
<span class="icon-bar"></span>
<span class="icon-bar"></span>
</button>
<a class="navbar-brand" href="#">DCI Report</a>
</div>
<div class="navbar-collapse collapse">
<form class="navbar-form navbar-right">
<input type="text" class="form-control" placeholder="Search...">
</form>
</div>
</div>
</div>
<div class="container-fluid">
<div class="row">
<div class="col-sm-3 col-md-2 sidebar">
<ul class="nav nav-sidebar">
<li><a href="#">Overview</a></li>
<li class="active"><a href="#">Summary Reports</a></li>
<li><a href="#">Executive Report</a></li>
<li><a href="#">Published Report</a></li>
</ul>
</div>
<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
<h1 class="page-header">Summary Reports</h1>
<div class="btn-group">
<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
Generate New Report <span class="caret"></span>
</button>
<ul class="dropdown-menu" role="menu">
  <c:forEach var = "reportType" items = "${reportTypeList}">
  <li><a  href="/report/uitest2?reportTypeName=${reportType}"> ${reportType} </a></li>
  </c:forEach>
</ul>
</div>



<h2 class="sub-header">Report History</h2>
<div class="table-responsive">
<table class="table table-striped">
  <thead>
    <tr>
      <th>Report Name</th>
      <th>Timestamp</th>
      <th>Status</th>
      <th>Review</th>
      <th>Generate</th>
      
      <th>Delete</th>
    </tr>
  </thead>
  <tbody>
    <c:forEach var = "transaction" items = "${transactionList}">
    <tr>
      <td>${transaction.name}</td>
      <td>${transaction.date}</td>
      <td>
        <c:forEach var = "output" items = "${transaction.arroutput}">
        <c:choose>
        <c:when test = "${output.status == 'In progress'}">
        <span class="label label-primary">${output.type} &nbsp; ${output.status}</span>
        </c:when>
        <c:when test = "${output.status == 'Fail'}">
        <span class="label label-danger">${output.type} &nbsp; ${output.status}</span>
        </c:when>
        <c:otherwise>
        <span class="label label-warning">${output.type} &nbsp;    ${output.status}</span>
        </c:otherwise>
        </c:choose>
        </c:forEach>
      </td>
      <td>
        <div class="btn-group">
          <c:forEach var = "output" items = "${transaction.arroutput}">
        <a href="file:///C:/Users/ldong/Desktop/${output.filename}.${output.type}" class="btn btn-default btn-xs" role="button"><span class="glyphicon glyphicon-search"></span>${output.type}</a >
        </c:forEach>
      </div>
    </td>
    <td>
      <a  href="/report/uitest3?transactionID=${transaction.id}" class="btn btn-default btn-xs" role="button">Generate </a>
    </td>
    <td>
      <a  href="/report/delete?transactionID=${transaction.id}" class="btn btn-danger btn-xs" role="button">delete</a>
    </td>
  </tr>
  </c:forEach>
  
</tbody>
</table>
</div>
</div>
</div>
</div>
<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="<c:url value = "/resources/bootstrap/js/bootstrap.min.js" />"></script>
<script src="<c:url value = "/resources/bootstrap/js/docs.min.js" />"></script>
</body>
</html>