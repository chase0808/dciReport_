<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
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
    $(document).ready(function(){
 
    $('#myModal').on('hidden.bs.modal', function (e) {
    //alert("show event fired!");
    $(this).find('form')[0].reset();
    $('#selectedClient').html("");
    collapseTree("tree1");
    //$(this).find('#selectedClient').val('');
    //$('form').find('input[type=date], input[type=checkbox], input[type=number], input[type=email], textarea').val('');
    });
    $("input[type='checkbox']").change(function(){
    var value = $(this).val();
    var deptname = $(this).parent("label").text();
    if(this.checked){
    var txt1 = "<p class = " + value +">" + dept + "</p>";
    $("#selectedClient").append(txt1);
    } else {
    $("p").remove("." + value);
    }
    });
    });
    </script>
    <script id = "forEach">
    
    </script>
    <link rel="stylesheet" href="<c:url value = "/resources/css/mktree.css" />" type="text/css">
  </head>
  <body>
    <!-- Modal -->
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
                        <span class="label label-warning">${output.type} &nbsp; 	 ${output.status}</span>
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
                  <a  href="/report/uitest3?transactionID=${transaction.id}" class="btn btn-default btn-xs" role="button">Generate</a>
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