<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- D3.js -->
	<script src="https://d3js.org/d3.v5.min.js"></script>
	
	<!-- 대시보드 css -->
	<link href="${pageContext.request.contextPath}/resource/css/dashboard.css" rel="stylesheet">
</head>
<body class="dashboardBody">
<table class="chartTable">
	<thead></thead>
	<tbody>
		<tr>
			<td>
				<div class="inline-block-chart" id="bar-svg-area1">
					<div id="bar-tooltip1"></div>
				</div>
				<script src="${pageContext.request.contextPath}/resource/js/dashboard/sample/bar-chart.js"></script>
			</td>
			<td>
				<div class="inline-block-chart" id="line-svg-area1"></div>
				<script src="${pageContext.request.contextPath}/resource/js/dashboard/sample/line-chart.js"></script>
			</td>
		</tr>
	</tbody>
	<tfoot></tfoot>
</table>
</html>