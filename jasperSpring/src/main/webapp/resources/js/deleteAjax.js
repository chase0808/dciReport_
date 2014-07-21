/**
 * 
 */

function deleteTransaction (url, transactionID) {
                     $.get(url)
                     .done(function(data) {
                       if(data === "success"){
                    	   $("#"+transactionID).remove();
                    	   $('#delete-dialog').modal('hide');
                       }
                     })
                     .fail(function() {
                        console.log( "error" );
                     })
                     .always(function() {
                        console.log( "complete" );
                     });
       };