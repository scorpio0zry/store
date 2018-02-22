<%@page import="com.domain.Product"%>
<%@page import="com.service.Impl.ProductServiceImpl"%>
<%@page import="com.service.ProductService"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="com.utils.CookieUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>商品列表</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath }/css/bootstrap.min.css" type="text/css" />
		<script src="${pageContext.request.contextPath }/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath }/js/bootstrap.min.js" type="text/javascript"></script>
		<!-- 引入自定义css文件 style.css -->
		<link rel="stylesheet" href="css/style.css" type="${pageContext.request.contextPath }/text/css" />

		<style>
			body {
				margin-top: 20px;
				margin: 0 auto;
				width: 100%;
			}
			.carousel-inner .item img {
				width: 100%;
				height: 300px;
			}
		</style>
	</head>

	<body>
		
		<%@include file="top.jsp" %>	

		<div class="row" style="width:1210px;margin:0 auto;">
			<div class="col-md-12">
				<ol class="breadcrumb">
					<li><a href="${pageContext.request.contextPath }/index.jsp">首页</a></li>
				</ol>
			</div>
		<c:forEach var="p" items="${page.list }">
			<div class="col-md-2">
				<a href="${pageContext.request.contextPath }/ProductServlet?method=findProduct&pid=${p.pid}">
					<img src="${pageContext.request.contextPath }/${p.pimage}" width="170" height="170" style="display: inline-block;">
				</a>
				<p><a href="${pageContext.request.contextPath }/ProductServlet?method=findProduct&pid=${p.pid}" style='color:green' title="${ p.pname }">
				<!-- 如果名字长度小于13 -->
				<c:if test="${p.pname.length() <= 13 }">
				${p.pname }
				</c:if>
				<!-- 如果名字长度大于13 -->
				<c:if test="${p.pname.length() > 13 }">
				${fn:substring(p.pname,0,13)}...
				</c:if>
				</a></p>
				<p><font color="#FF0000">商城价：&yen; ${ p.shop_price }</font></p>
			</div>
		</c:forEach>
			
		</div>

		<!--分页 -->
		<div style="width:380px;margin:0 auto;margin-top:50px;">
			<ul class="pagination" style="text-align:center; margin-top:10px;">
			<c:if test="${page.currPage <= 1 }">
				<li class="disabled"><a aria-label="Previous" ><span aria-hidden="true">&laquo;</span></a></li>
			</c:if>
			<c:if test="${page.currPage > 1 }">
				<li><a href="${pageContext.request.contextPath }/ProductServlet?method=findPage&currPage=1&cid=${param.cid}" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
			</c:if>
			<!-- 数字栏 -->
			<c:forEach var="i" begin="1" end="${page.totalPage }">
				<c:if test="${i == page.currPage }">
					<li class="active"><a>${ i }</a></li>
				</c:if>
				<c:if test="${i != page.currPage }">
					<li><a href="${pageContext.request.contextPath }/ProductServlet?method=findPage&currPage=${i}&cid=${param.cid}">${ i }</a></li>
				</c:if>
			</c:forEach>
				<c:if test="${page.currPage >= page.totalPage }">
				<li class="disabled">
					<a aria-label="Next">
						<span aria-hidden="true">&raquo;</span>
					</a>
				</li>
				</c:if>
				<c:if test="${page.currPage < page.totalPage }">
				<li>
					<a href="${pageContext.request.contextPath }/ProductServlet?method=findPage&currPage=${page.totalPage }&cid=${param.cid}" aria-label="Next">
						<span aria-hidden="true">&raquo;</span>
					</a>
				</li>
				</c:if>	
			</ul>
		</div>
		<!-- 分页结束=======================        -->

		<!--
       		商品浏览记录:
        -->
		<div style="width:1210px;margin:0 auto; padding: 0 9px;border: 1px solid #ddd;border-top: 2px solid #999;height: 246px;">

			<h4 style="width: 50%;float: left;font: 14px/30px " 微软雅黑 ";">浏览记录</h4>
			<div style="width: 50%;float: right;text-align: right;"><a href="">more</a></div>
			<div style="clear: both;"></div>

			<div><a href="${pageContext.request.contextPath }/ProductServlet?method=clearRecord&cid=${param.cid}">清除浏览器记录</a></div>
			<div style="overflow: hidden;">
			    <%  Cookie[] cookies = request.getCookies();
					ProductService ps = new ProductServiceImpl();
					String value = CookieUtils.findCookie(cookies, "history");
					if(value != null){
						String[] ids = value.split("-");
						for(String id:ids){
							Product product = ps.findByPid(id);
					%>
						<a href="${pageContext.request.contextPath }/ProductServlet?method=findProduct&pid=<%= product.getPid() %>">
							<img src="${pageContext.request.contextPath }/<%= product.getPimage()  %>" width="170" height="170" style="display: inline-block;">
						</a>
					<%
						}
					}else{	
					%>
						<h2 style="width: 50%;float: left;font: 14px/30px " 微软雅黑 ";">您还没有浏览记录</h2>
					<%	
					}	
				%>
				
			</div>
		</div>
		<%@include file="footer.jsp" %>
	</body>

</html>