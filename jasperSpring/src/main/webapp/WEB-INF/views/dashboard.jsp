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
            <form:form role="form" method="POST" commandname="departments" action="/report/result" >
              <div class="form-group">
                <label for="start_date">Start Date</label>
                <input type="date" class="form-control" id="start_date">
                <label for="end_date">End Date</label>
                <input type="date" class="form-control" id="end_date">
                
                
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
                                    <form:checkbox path="departmentIDs" value= " ${department.departmentID}"></form:checkbox>
                                    
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
        <h2 class="sub-header">Report History</h2>
        <div class="table-responsive">
          <table class="table table-striped">
            <thead>
              <tr>
                <th>Report ID</th>
                <th>Client</th>
                <th>Department</th>
                <th>Report Name</th>
                <th>Manipulation</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>1,001</td>
                <td>Lorem</td>
                <td>ipsum</td>
                <td>dolor</td>
                <td>sit</td>
              </tr>
              <tr>
                <td>1,002</td>
                <td>amet</td>
                <td>consectetur</td>
                <td>adipiscing</td>
                <td>elit</td>
              </tr>
              <tr>
                <td>1,003</td>
                <td>Integer</td>
                <td>nec</td>
                <td>odio</td>
                <td>Praesent</td>
              </tr>
              <tr>
                <td>1,003</td>
                <td>libero</td>
                <td>Sed</td>
                <td>cursus</td>
                <td>ante</td>
              </tr>
              <tr>
                <td>1,004</td>
                <td>dapibus</td>
                <td>diam</td>
                <td>Sed</td>
                <td>nisi</td>
              </tr>
              <tr>
                <td>1,005</td>
                <td>Nulla</td>
                <td>quis</td>
                <td>sem</td>
                <td>at</td>
              </tr>
              <tr>
                <td>1,006</td>
                <td>nibh</td>
                <td>elementum</td>
                <td>imperdiet</td>
                <td>Duis</td>
              </tr>
              <tr>
                <td>1,007</td>
                <td>sagittis</td>
                <td>ipsum</td>
                <td>Praesent</td>
                <td>mauris</td>
              </tr>
              <tr>
                <td>1,008</td>
                <td>Fusce</td>
                <td>nec</td>
                <td>tellus</td>
                <td>sed</td>
              </tr>
              <tr>
                <td>1,009</td>
                <td>augue</td>
                <td>semper</td>
                <td>porta</td>
                <td>Mauris</td>
              </tr>
              <tr>
                <td>1,010</td>
                <td>massa</td>
                <td>Vestibulum</td>
                <td>lacinia</td>
                <td>arcu</td>
              </tr>
              <tr>
                <td>1,011</td>
                <td>eget</td>
                <td>nulla</td>
                <td>Class</td>
                <td>aptent</td>
              </tr>
              <tr>
                <td>1,012</td>
                <td>taciti</td>
                <td>sociosqu</td>
                <td>ad</td>
                <td>litora</td>
              </tr>
              <tr>
                <td>1,013</td>
                <td>torquent</td>
                <td>per</td>
                <td>conubia</td>
                <td>nostra</td>
              </tr>
              <tr>
                <td>1,014</td>
                <td>per</td>
                <td>inceptos</td>
                <td>himenaeos</td>
                <td>Curabitur</td>
              </tr>
              <tr>
                <td>1,015</td>
                <td>sodales</td>
                <td>ligula</td>
                <td>in</td>
                <td>libero</td>
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