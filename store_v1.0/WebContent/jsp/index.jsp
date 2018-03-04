<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>首页</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath }/css/bootstrap.min.css" type="text/css" />
		<script src="${pageContext.request.contextPath }/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath }/js/bootstrap.min.js" type="text/javascript"></script>
	</head>

	<body>
		<%@include file="top.jsp" %>
			
			<div class="container-fluid">
				<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
					<!-- Indicators -->
					<ol class="carousel-indicators">
						<li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
						<li data-target="#carousel-example-generic" data-slide-to="1"></li>
						<li data-target="#carousel-example-generic" data-slide-to="2"></li>
					</ol>

					<!-- Wrapper for slides -->
					<div class="carousel-inner" role="listbox">
						<div class="item active">
							<img src="${pageContext.request.contextPath }/img/1.jpg">
							<div class="carousel-caption">

							</div>
						</div>
						<div class="item">
							<img src="${pageContext.request.contextPath }/img/2.jpg">
							<div class="carousel-caption">

							</div>
						</div>
						<div class="item">
							<img src="${pageContext.request.contextPath }/img/3.jpg">
							<div class="carousel-caption">

							</div>
						</div>
					</div>

					<!-- Controls -->
					<a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
						<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
						<span class="sr-only">Previous</span>
					</a>
					<a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
						<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
						<span class="sr-only">Next</span>
					</a>
				</div>
			</div>
			
			<div class="container-fluid">
				<div class="col-md-12">
					<h2>最新商品&nbsp;&nbsp;<img src="${pageContext.request.contextPath }/img/title2.jpg"/></h2>
				</div>
				<div class="col-md-2" style="border:1px solid #E7E7E7;border-right:0;padding:0;">
					<img src="${pageContext.request.contextPath }/products/hao/big01.jpg" width="205" height="404" style="display: inline-block;"/>
				</div>
				<div class="col-md-10">
					<div class="col-md-6" style="text-align:center;height:200px;padding:0px;">
						<img src="${pageContext.request.contextPath }/products/hao/middle01.jpg" width="516px" height="200px" style="display: inline-block;">
					</div>
				<c:forEach var="nl" items="${ newList }">
					<div class="col-md-2" style="text-align:center;height:200px;padding:10px 0px;">
						<a href="${pageContext.request.contextPath }/ProductServlet?method=findProduct&pid=${nl.pid}&">
							<img src="${pageContext.request.contextPath }/${nl.pimage}" width="130" height="130" style="display: inline-block;">
						</a>
						<p><a href="${pageContext.request.contextPath }/ProductServlet?method=findProduct&pid=${nl.pid}" style='color:#666' title="${nl.pname }">
						<!-- 如果名字长度小于13 -->
						<c:if test="${nl.pname.length() <= 13 }">
							${nl.pname }
						</c:if>
						<!-- 如果名字长度大于13 -->
						<c:if test="${nl.pname.length() > 13 }">
							${fn:substring(nl.pname,0,13)}...
						</c:if>
						</a></p>
						<p><font color="#E4393C" style="font-size:16px">&yen; ${nl.shop_price }</font></p>
					</div>
				</c:forEach>
					
				</div>
			</div>
			
			
            <div class="container-fluid">
				<img src="${pageContext.request.contextPath }/products/hao/ad.jpg" width="100%"/>
			</div>
			
			
			<div class="container-fluid">
				<div class="col-md-12">
					<h2>热门商品&nbsp;&nbsp;<img src="${pageContext.request.contextPath }/img/title2.jpg"/></h2>
				</div>
				<div class="col-md-2" style="border:1px solid #E7E7E7;border-right:0;padding:0;">
					<img src="${pageContext.request.contextPath }/products/hao/big01.jpg" width="205" height="404" style="display: inline-block;"/>
				</div>
				<div class="col-md-10">
					<div class="col-md-6" style="text-align:center;height:200px;padding:0px;">
						<img src="${pageContext.request.contextPath }/products/hao/middle01.jpg" width="516px" height="200px" style="display: inline-block;">
					</div>
				<c:forEach var="hl" items="${hotList }">
					<div class="col-md-2" style="text-align:center;height:200px;padding:10px 0px;">
						<a href="${pageContext.request.contextPath }/ProductServlet?method=findProduct&pid=${hl.pid}">
							<img src="${pageContext.request.contextPath }/${hl.pimage}" width="130" height="130" style="display: inline-block;">
						</a>
						<p><a href="${pageContext.request.contextPath }/ProductServlet?method=findProduct&pid=${hl.pid}" style='color:#666' title="${hl.pname }">
						<!-- 如果名字长度小于13 -->
						<c:if test="${hl.pname.length() <= 13 }">
							${hl.pname }
						</c:if>
						<!-- 如果名字长度大于13 -->
						<c:if test="${hl.pname.length() > 13 }">
							${fn:substring(hl.pname,0,13)}...
						</c:if>
						</a></p>
						<p><font color="#E4393C" style="font-size:16px">&yen; ${hl.shop_price }</font></p>
					</div>
				</c:forEach>
					
				</div>
			</div>			
		<%@include file="footer.jsp" %>
		
	</body>

</html>