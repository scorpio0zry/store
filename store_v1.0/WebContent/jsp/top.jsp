<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
 
 <script type="text/javascript">
	$(function(){
		$.post("${pageContext.request.contextPath}/CategoryServlet",{"method":"findAll"},function(data){
			$(data).each(function(i,n){
				if(i == 5){
					$("#category").append("<li class='dropdown'><a href='${pageContext.request.contextPath}/ProductServlet?cid="+n.cid+"&method=findPage&currPage=1' class='dropdown-toggle' data-toggle='dropdown'>"+n.cname+"<b class='caret'></b></a> <ul class='dropdown-menu navbar-inverse' id='ucate'></ul></li>")
				}else if(i > 5){
					$("#ucate").append("<li><a style='color:white;' href='${pageContext.request.contextPath}/ProductServlet?cid="+n.cid+"&method=findPage&currPage=1'>"+n.cname+"</a></li>")
				}else{
					$("#category").append("<li><a href='${pageContext.request.contextPath}/ProductServlet?cid="+n.cid+"&method=findPage&currPage=1'>"+ n.cname +"</a></li>");
				}
				
			})
			
		},"json");
	})
 </script>
			<!--
            	时间：2015-12-30
            	描述：菜单栏
            -->
			<div class="container-fluid">
				<div class="col-md-4">
					<img src="${pageContext.request.contextPath }/img/logo.png" />
				</div>
				<div class="col-md-5">
					<img src="${pageContext.request.contextPath }/img/header.png" />
				</div>
				<div class="col-md-3" style="padding-top:20px">
					<ol class="list-inline">
					<c:if test="${empty user }">
						<li><a href="${pageContext.request.contextPath }/UserServlet?method=loginUI">登录</a></li>
						<li><a href="${pageContext.request.contextPath }/UserServlet?method=registUI">注册</a></li>
					</c:if>
					<c:if test="${not empty user }">
						<li>您好,</li>
						<li><a href="#">${user.username }</a></li>
						<li><a href="${pageContext.request.contextPath }/UserServlet?method=loginOut">退出</a></li>
						<li><a href="${pageContext.request.contextPath }/OrderServlet?method=listOrder&currPage=1">我的订单</a></li>
					</c:if>
						
						<li><a href="${pageContext.request.contextPath }/jsp/cart.jsp">购物车</a></li>
					</ol>
				</div>
			</div>
			<!--
            	时间：2015-12-30
            	描述：导航条
            -->
			<div class="container-fluid">
				<nav class="navbar navbar-inverse">
					<div class="container-fluid">
						<!-- Brand and toggle get grouped for better mobile display -->
						<div class="navbar-header">
							<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
								<span class="sr-only">Toggle navigation</span>
								<span class="icon-bar"></span>
								<span class="icon-bar"></span>
								<span class="icon-bar"></span>
							</button>
							<a class="navbar-brand" href="${pageContext.request.contextPath }/index.jsp">首页</a>
						</div>

						<!-- Collect the nav links, forms, and other content for toggling -->
						<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
							<ul class="nav navbar-nav" id="category">
								
							</ul>
							<form class="navbar-form navbar-right" role="search" method="post" action="${pageContext.request.contextPath }/ProductServlet">
								<input type="hidden" name="method" value="findByName"/>
								<div class="form-group">
									<input type="text" class="form-control" placeholder="Search" name="name">
								</div>
								<button type="submit" class="btn btn-default">搜索</button>
							</form>

						</div>
						<!-- /.navbar-collapse -->
					</div>
					<!-- /.container-fluid -->
				</nav>
			</div>
		
		