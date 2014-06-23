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
           <form:form role="form" method="POST" commandname="departments" action="/report/generate" >
              <div class="form-group">
                <form:label for="start_date" path="startdate">Start Date</form:label>
                <form:input type="date" class="form-control" id="start_date" path="startdate"/>
                <form:label for="end_date" path="enddate">End Date</form:label>
                <form:input type="date" class="form-control" id="end_date" path="enddate"/>
                
                
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
                                  <form:checkbox path="para" value= " ${department.departmentID}"></form:checkbox>
                                  
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
                        <div class="panel-body checkboxes">
                          
                        </div>
                      </div>
                    </div>
                    
                  </div>
                </div>
                
                
              </div>
              
              
              
              
              
              
              
            </div>
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
        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal">Generate New Report</button>  
        
        <a id="dLabel" href="www.google.com">Link 1</a>
        <h2 class="sub-header">Report History</h2>
        <div class="table-responsive">
          <table class="table table-striped">
            <thead>
              <tr>
                <th>Report ID</th>
                <th>Client Name</th>
                <th>Timestamp</th>
                <th>Report Name</th>
                <th>Criteria</th>
                <th>Review</th>
                <th>Delete</th>

              </tr>
            </thead>
            <tbody>
              <tr>
                <td>1,001</td>
                <td>Morgan Stanley</td>
                <td>06/23/2014 00:00:00</td>
                <td>summary.pdf</td>
                <td>
                  <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#myModal">Regenerate</button>
                </td>
                <td>
                  <a  href="file:///C:/Users/xqi/eclipesworkspace/jasperSpring/summary.pdf">Review</a>
                </td>
                <td>
                  <button type="button" class="btn btn-danger btn-sm">Delete</button>
                </td>
              </tr>
              
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