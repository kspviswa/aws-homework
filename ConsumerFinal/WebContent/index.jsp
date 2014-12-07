<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="refresh" content="10" >
<title>Consumer</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
            //$(document).ready(function() {                        // When the HTML DOM is ready loading, then execute the following function...
                $(window).load(function() {               // Locate HTML DOM element with ID "somebutton" and assign the following function to its "click" event...
                    $.get('ConsumerServletA1', function(responseText) { // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response text...
                        $('#sqsA1').html(responseText);         // Locate HTML DOM element with ID "somediv" and set its text content with the response text.
                    });
                });
            //});
            
           // $(document).ready(function() {                        // When the HTML DOM is ready loading, then execute the following function...
                $(window).load(function() {               // Locate HTML DOM element with ID "somebutton" and assign the following function to its "click" event...
                    $.get('ConsumerServletA2', function(responseText) { // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response text...
                        $('#sqsA2').html(responseText);         // Locate HTML DOM element with ID "somediv" and set its text content with the response text.
                    });
                });
            //});
            
        </script>
</head>
<body>
<center> <h1> BITS MS - Cloud Computing EC-III Assignment </h1></center>
<p> <center> <h2> Consumer Application</h2> </center>
<p>
<!-- button id="Poll">Poll</button-->
	<table>
		<tr>
		<td width="40%">
			<div id="sqsA1"> <img src="images/loading.gif"> </img></div>
		</td>
		<td width="20%">
		</td>
		<td width="40%">
			<div id="sqsA2"> <img src="images/loading.gif"> </img> </div>
		</td>
		</tr>
	</table>
</body>
</html>