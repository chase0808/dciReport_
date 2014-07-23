function generatePopup(url) {
                     $.get(url)
                     .done(function(data) {                  	 
                    	   $("#genmodalbody").empty();
                    	   $("#genmodalbody").append(data);                 	   
                    	   $("#generate-dialog").modal('show');
                      
                     })
                     .fail(function() {
                    	alert("fail");
                        console.log( "error" );
                     })
                     .always(function() {
                        console.log( "complete" );
                     });
       };