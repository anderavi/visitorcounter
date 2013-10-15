<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<script src="http://maps.google.com/maps/api/js?sensor=true"></script>
<script
	src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
</head>
<body>
	<h2>VisitorCounter</h2>

	<table border="1">
		<tr>
			<th>Counter</th>
			<th>Value</th>
		</tr>
		<c:forEach items="${allCounters}" var="entry">
		<tr>
			<td>${entry.key}</td>
			<td>${entry.value}</td>
		</tr>
		</c:forEach>
	</table>

	<a href="/page1">Page1</a> <br/>
	<a href="/page2">Page2</a> <br/>
	<a href="/page3">Page3</a> <br/>
	<a href="/page4">Page4</a> <br/>
	
</body>
</html>




