<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ page
	import="com.google.appengine.api.datastore.DatastoreServiceFactory"%>
<%@ page import="com.google.appengine.api.datastore.DatastoreService"%>
<%@ page import="com.google.appengine.api.datastore.Query.Filter"%>
<%@ page
	import="com.google.appengine.api.datastore.Query.FilterPredicate"%>
<%@ page
	import="com.google.appengine.api.datastore.Query.FilterOperator"%>
<%@ page
	import="com.google.appengine.api.datastore.Query.CompositeFilter"%>
<%@ page import="com.google.appengine.api.datastore.Query.SortDirection"%>
<%@ page
	import="com.google.appengine.api.datastore.Query.CompositeFilterOperator"%>
<%@ page import="com.google.appengine.api.datastore.Query"%>
<%@ page import="com.google.appengine.api.datastore.PreparedQuery"%>
<%@ page import="com.google.appengine.api.datastore.Entity"%>


<%@ page import="java.util.List"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>

<%@page import="java.util.ArrayList"%>

<%
UserService userService = UserServiceFactory.getUserService();
User user = userService.getCurrentUser();

if (user==null) {	
	out.println("<p>Please <a href=\""
	        + userService.createLoginURL(request.getRequestURI())
	        + "\">sign in</a>.</p>");
	return; // No muy "fino"
	}

//Get the Datastore Service
DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
%>

<html>
<head>
<style type="text/css">
html, body {
	height: 100%;
	margin: 0;
	padding: 0;
}

#map {
	height: 100%;
}
</style>

</head>
<body>

	<div id="map"></div>
	<script type="text/javascript">
      
   var map;
   function initMap() {
     var myLatLng = {lat: 36.726048, lng: -4.428342};

     var map = new google.maps.Map(document.getElementById('map'), {
       zoom: 13,
       center: myLatLng
     });


 	<%
	//Use class Query to assemble a query
	Query q = new Query("localizacion");
				//	.addSort("instante", SortDirection.ASCENDING);
					
	//Use PreparedQuery interface to retrieve results
	PreparedQuery pq = datastore.prepare(q);

	int i=0;
	for (Entity result : pq.asIterable()) {
//		Long x = result.getKey().getId();
		Double lat = (Double)result.getProperty("lat");
		Double longi = (Double)result.getProperty("lon");

	    out.println("var myLatLng"+i+" = {lat: "+lat+", lng: "+longi+"};");

		out.println("var marker"+i+" = new google.maps.Marker({");
		out.println("position: myLatLng"+i+",");
		out.println("map: map,");
	    //out.println("title: '"+result.getProperty("nombre")+": "+result.getProperty("libres")+"'");
	     out.println("});");
	     %>
	     
	     var poliline = new google.maps.Polyline({
	          path: marker[i].position,
	          geodesic: true,
	          strokeColor: '#FF0000',
	          strokeOpacity: 1.0,
	          strokeWeight: 2
	        });

	        poliline.setMap(map);
		<%
	     i++;
		}
	      %>
   }
}
    </script>

	<script async defer
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBYMrQUTfCjEwfrxJlZcSAj9qmqLQnm3J8&callback=initMap">
    </script>

</body>
</html>
