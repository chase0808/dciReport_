<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html>
<head>
	<style type="text/css">
		.list-of-transactions
		{
			min-height: 450px;
		}
		.custom
		{
			width: 100px;
		}
	</style>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="">
	<meta name="author" content="">
	<title>DCI Report Dashboard</title>
	<!-- Bootstrap core CSS -->
	<link href="<c:url value = "/resources/bootstrap/css/bootstrap.min.css" />"  rel="stylesheet">
	<link href="<c:url value = "/resources/css/dashboard.css" />" rel="stylesheet">
	<!--css file for pagination-->
	<link href="<c:url value = "/resources/css/simplePagination.css" />" rel="stylesheet">
	<!--css file for tree structure-->
	<link rel="stylesheet" href="<c:url value = "/resources/css/mktree.css" />" type="text/css">
	<!-- include jquery library-->
	<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
	<!-- include tree structure js-->
	<script type="text/javascript" src="<c:url value = "/resources/js/mktree.js" />"></script>
	<!-- include tree structure js-->
	<script type="text/javascript" src="<c:url value = "/resources/js/jquery.simplePagination.js" />"></script>
	<!-- include js file for asynchronous call of delete transaction operation-->
	<script type="text/javascript" src="<c:url value = "/resources/js/deleteAjax.js" />"></script>
	<!-- include js file for asynchronous call of generate transaction operation-->
	<script type="text/javascript" src="<c:url value = "/resources/js/generateAjax.js" />"></script>
	<!-- include js file for asynchronous call of regenerate transaction operation-->
	<script type="text/javascript" src="<c:url value = "/resources/js/regenerateAjax.js" />"></script>
	<!-- include bootstrap core js library-->
	<script src="<c:url value = "/resources/bootstrap/js/bootstrap.min.js" />"></script>
    <script src="<c:url value = "/resources/bootstrap/js/docs.min.js" />"></script>
 <script>
		
		$(document).ready(function(){
		
		
		
		
		// jquery function for pagination
		var items = $("table tbody tr");
        var numItems = items.length;
        var perPage = 10;
        items.slice(perPage).hide();
        $('#choose').pagination({
            items: numItems,
            itemsOnPage: perPage,
            cssStyle: 'light-theme',
            onPageClick: function(pageNumber) { 
            var showFrom = perPage * (pageNumber - 1);
            var showTo = showFrom + perPage;
            items.hide() 
                 .slice(showFrom, showTo).show();
        }
        });

        // call the ajax function when hit the delete button
		$(".deletedialog").click(function(){
			var url = $(this).attr('id');
			var id = $(this).attr('name');
			$("#btnConfirm").click(function(){
				deleteTransaction(url, id);
			});
		});
		
		$(".regenerateLink").click(function(){
			var url = $(this).attr('id');
			regeneratePopup(url);
		});
		
		$(".reportTypeLink").click(function(){
			var url = $(this).attr('id');
			generatePopup(url);
		});
		// refresh the content in modal when modal gets closed
		$('#myModal').on('hidden.bs.modal', function (e) {
		$(this).find('form')[0].reset();
		$('#selectedClient').html("");
		collapseTree("tree1");
		});

		// for dynamic updates selected departments box 
		$("#genmodalbody").on("click", "input[type='checkbox'][id !='_output']", function(event){
		var value = $(this).val();
		var dept = $(this).parent("label").text();
		if(this.checked){
		var txt1 = "<p class = " + value +">" + dept + "</p>";
		$("#selectedClient").append(txt1);
		} else {
		$("p").remove("." + value);
		}
		});
		/*
		$("#genmodalbody").on("submit", "#command", function(event){
			 alert("!!!");
		     
    		 var formURL = '/report/generate';
    		 var postData = $(this).serializeArray();
   			 $.ajax(
    		 {
        		url : formURL,
       			type: "POST",
        		success:function(data) 
        		{
                   alert(data + "!!");
                   $("#history").append(data); 
                   $('#generate-dialog').modal('hide');
        		},
       			 error: function(jqXHR, textStatus, errorThrown) 
        		{
                     alert("fail");
       			}
    		});
		   
        });*/			
});



</script>   
<script>
		function adddept(){
                 			alert($(this).val());
                 			var value = $(this).val();
                 			alert(value + "tsts");
                 			var dept = $(this).parent("label").text();	
                 			alert(dept + "tsts");		
                 			if(this.checked){
                 				var txt1 = "<p class = " + value +">" + dept + "</p>";
                 				$("#selectedClient").append(txt1);
                 				} else {
                 					$("p").remove("." + value);
                 				}
                 		}
		
		function validate(){
			var allVals = [];
			var outputVals = [];
			$("input[type='checkbox'][id!='_output']").each(function(){
				if(this.checked){
					allVals.push($(this).val());
				}
			});
			
			$("input[type='checkbox'][id ='_output']").each(function(){
			    
				if(this.checked){
					outputVals.push($(this).val());
				}
			});
			
			if (allVals.length==0) {
				alert("Please select at least one department!");
				return false;
			}
			if(outputVals.length==0){
				alert("Please select at least one output type!");
				return false;
			}
		
		
			if (allVals.length==0) {
				alert("Please select department!");
				return false;
			}
			return true;
		}
</script>
</head>
<body>

	<!-- Generate Modal-->
	<div id="generate-dialog" class="modal">
		<div class="modal-dialog">
			<div class="modal-content" id="genmodalbody">
			</div>
		</div>
	</div>
	<!-- Delete Comfirm Modal -->
	<div id="delete-dialog" class="modal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<a href="#" data-dismiss="modal" aria-hidden="true" class="close">×</a>
					<h3>Are you sure</h3>
				</div>
				<div class="modal-body">
					<p>Do you want to delete this transaction?</p>
				</div>
				<div class="modal-footer">
					<a href="#"  id="btnConfirm" class="btn confirm">OK</a>
					<a href="#" data-dismiss="modal" aria-hidden="true" class="btn secondary">Cancel</a>
				</div>
			</div>
		</div>
	</div>

	<!--Navigation Bar-->
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
				<p class="navbar-text navbar-right">${username} <a href="/report/login?logout=logout" class="navbar-link">Log out</a></p>
			</div>
		</div>
	</div>


	<div class="container-fluid">
		<div class="row">
			<!-- Side Bar -->
			<div class="col-sm-3 col-md-2 sidebar">
				<ul class="nav nav-sidebar">
					<li class="active"><a href="#">Reports</a></li>
				</ul>
			</div>
		</div>

		<!-- main content panel on the right -->
		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<h1 class="page-header">DCI Reports</h1>

			<!-- Dropdown list for different reports -->
			<div class="btn-group">
				<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
				Generate New Report <span class="caret"></span>
				</button>
				<ul class="dropdown-menu" role="menu">
					<c:forEach var = "reportType" items = "${reportTypeList}">
					<li><a  id="/report/getReportType?reportTypeName=${reportType}"  class="reportTypeLink"> ${reportType} </a></li>
					</c:forEach>
				</ul>
			</div>

			<h2 class="sub-header">Reports History</h2>
			<!-- Transaction list table -->
			<div class="list-of-transactions">
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
						<tbody id="history">
							<c:forEach var = "transaction" items = "${transactionList}">
								<tr id="${transaction.id}">
									<td>${transaction.name}</td>
									<td>${transaction.date}</td>
									<td>
										<c:forEach var = "output" items = "${transaction.arroutput}">
										<c:choose>
										<c:when test = "${output.status == 'In progress'}">
										<span class="glyphicon glyphicon-refresh"></span>
										</c:when>
										<c:when test = "${output.status == 'Fail'}">
										<span class="glyphicon glyphicon-remove"></span>
										</c:when>
										<c:otherwise>
										<span class="glyphicon glyphicon-ok"></span>
										</c:otherwise>
										</c:choose>
										</c:forEach>
									</td>
									<td>
										<div class="btn-group">
											<c:forEach var = "output" items = "${transaction.arroutput}">
												<a href="/report/download?fileName=${path}${transaction.name}/${output.filename}.${output.type}" class="btn btn-default btn-xs" role="button" target="_blank"><span class="glyphicon glyphicon-search"></span>${output.type}</a>
											</c:forEach>
										</div>
									</td>
									<td>
										<a  id="/report/regenerate?transactionID=${transaction.id}" class="btn btn-default btn-xs regenerateLink" role="button">Generate</a>
									</td>
									<td>
										<a  data-target="#delete-dialog" class="btn btn-danger btn-xs deletedialog" role="button" data-toggle="modal" id="/report/delete?transactionID=${transaction.id}" name ="${transaction.id}"  >delete</a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				
			</div >
			<div id="choose">      
   
    		</div> 
		</div>
	</div>
	
	
	
</body>
</html>