<%@page import="com.domain.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!doctype html>
<html>

<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>购物车</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/css/bootstrap.min.css"
	type="text/css" />
<script
	src="${pageContext.request.contextPath }/js/jquery-1.11.3.min.js"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath }/js/bootstrap.min.js"
	type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/css/style.css"
	type="text/css" />
<style>
body {
	margin-top: 20px;
	margin: 0 auto;
}

.carousel-inner .item img {
	width: 100%;
	height: 300px;
}

.container .row div {
	/* position:relative;
	 float:left; */
	
}

/* font {
	color: #3164af;
	font-size: 18px;
	font-weight: normal;
	padding: 0 10px;
} */
</style>
<script type="text/javascript">
	function del(pid){
		var flag = confirm("你忍心删除我吗？");
		if(flag){
			location.href = "${pageContext.request.contextPath }/CartServlet?method=removeCart&pid="+pid;
		}		
	}
	
	function changeCount(pid,count){
		$.post("${pageContext.request.contextPath }/CartServlet",
			   {"method":"changeCount",
				"pid":pid,
				"count":count},
				function(data){
					if(data == 1){
						alert("输入错误！")
						location.reload();
					}else{
						$("#subtotal_" + pid).html("￥："+data.subtotal+"元");
						$("#score").html(data.total);
						$("#total").html(data.total);
					}
				},"json")
	}
	
 	function changeL(pid){
	 var count = $("#Count_" + pid).val() - 1;
	 if(count < 1){
		 alert("商品不能小于1");
	 }else{
		 $("#Count_" + pid).val(count);
		 $.post("${pageContext.request.contextPath }/CartServlet",
				   {"method":"changeCount",
					"pid":pid,
					"count":count},
					function(data){
							$("#subtotal_" + pid).html("￥："+data.subtotal+"元");
							$("#score").html(data.total);
							$("#total").html(data.total);
						
					},"json")
	 }
		
	} 
	
	
	
	//parseInt($("#Count_" + pid).val())
	
	function changeR(pid){
		 var count = parseInt($("#Count_" + pid).val()) + 1;
		 $("#Count_" + pid).val(count);
		 $.post("${pageContext.request.contextPath }/CartServlet",
				   {"method":"changeCount",
					"pid":pid,
					"count":count},
					function(data){
						$("#subtotal_" + pid).html("￥："+data.subtotal+"元");
						$("#score").html(data.total);
						$("#total").html(data.total);
					},"json")
			
		}
	
	
</script>

</head>

<body>
	<%@include file="top.jsp"%>
	<%
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
	%>
	<div style="text-align: center;">
		您还没有登录,请登录后购买商品,购物车信息将会保存在您的账户中&nbsp;&nbsp;<input id="subBut" type="button"
			class="btn btn-primary btn-md" value="立即登录" />
	</div>
	<%
		}
	%>

	<c:if test="${fn:length(cart.map) == 0 }">
		<center>
			<h1>您的购物车空空的哦！</h1>
		</center>
	</c:if>
	<c:if test="${fn:length(cart.map) != 0 }">
		<div class="container">
			<div class="row">

				<div style="margin: 0 auto; margin-top: 10px; width: 950px;">
					<strong style="font-size: 16px; margin: 5px 0;">订单详情</strong>
					<table class="table table-bordered">
						<tbody>
							<tr class="warning">
								<th>图片</th>
								<th>商品</th>
								<th>价格</th>
								<th>数量</th>
								<th>小计</th>
								<th>操作</th>
							</tr>
							<c:forEach var="entry" items="${cart.map }">
								<input type="hidden" id="pid" value="${entry.value.product.pid}" />
								<tr class="active">
									<td width="60" width="40%"><input type="hidden" name="id"
										value="22"> <img
										src="${pageContext.request.contextPath }/${entry.value.product.pimage}"
										width="70" height="60"></td>
									<td width="30%"><a href="${pageContext.request.contextPath }/ProductServlet?method=findProduct&pid=${entry.value.product.pid}" >${entry.value.product.pname }</a>
									</td>
									<td width="20%">￥：${ entry.value.product.shop_price }元</td>
									<td width="15%">
										<button type="button"  style="display: inline;" onclick="changeL('${entry.value.product.pid}')">
											<span aria-hidden="true">&laquo;</span>
										</button>
										<input type="text" onblur="changeCount('${entry.value.product.pid}',value)" id="Count_${entry.value.product.pid}"
											name="quantity" value="${entry.value.num }" maxlength="4"
											size="3" style="display: inline;text-align:center;"/>
										<button type="button" style="display: inline; " onclick="changeR('${entry.value.product.pid}')">
											<span aria-hidden="true">&raquo;</span>
										</button>
									</td>
									<td width="15%"><span class="subtotal" id="subtotal_${entry.value.product.pid}">￥：${entry.value.subtotal }元</span>
									</td>
									<td><a href="#" onclick="del('${entry.value.product.pid}')">删除</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>

			<div style="margin-right: 130px;">
				<div style="text-align: right;">
					赠送积分: <em style="color: #ff6600;"><span id="score">${cart.total }</span></em>&nbsp; 商品金额: <strong
						style="color: #ff6600;">￥：<span id="total">${cart.total }</span>元</strong>
				</div>
				<div
					style="text-align: right; margin-top: 10px; margin-bottom: 10px;">
					<a href="${pageContext.request.contextPath }/CartServlet?method=clearCart"
						id="clear" class="clear">清空购物车</a> 
					<!-- 用户已登陆 -->
					<c:if test="${not empty user}">
						<a href="${pageContext.request.contextPath }/OrderServlet?method=saveOrder"> <input id="subOrder"
							type="button" width="100" value="提交订单" name="submit" border="0"
							style="background: url('${pageContext.request.contextPath }/images/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0);
							height:35px;width:100px;color:white;">
						</a>
					</c:if>
					<!-- 用户未登录 -->
					<c:if test="${empty user}">
						<a href="#"> <input id="subOrder" onclick="subOrder()"
							type="button" width="100" value="提交订单" name="submit" border="0"
							style="background: url('${pageContext.request.contextPath }/images/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0);
							height:35px;width:100px;color:white;">
						</a>
					</c:if>
				</div>
			</div>

		</div>
	</c:if>

	<%@include file="footer.jsp"%>
</body>


<%@include file="Model.jsp" %>

<script type="text/javascript">
	$("#subBut").click(function() {
		$("#myModal").modal('show');
	});
	
	$("#subBut2").click(function(){
		$.post("${pageContext.request.contextPath}/UserServlet",
				{"method":"asyncLogin",
				 "username":$("#username").val(),
				 "password":$("#password").val()
				 },
				function(data){
					$(data).each(function(i,n){
						if(n.flag){
							$("#myModal").modal('hide');
							location.reload();
						}else{
							$("#message").html("<font color='red'>"+n.message+"</font>")
						}
					})
				},"json")
	})
	
	function subOrder(){
		$("#myModal2").modal('show');
	}
	
	$("#subBut3").click(function(){
		$.post("${pageContext.request.contextPath}/UserServlet",
				{"method":"asyncLogin",
				 "username":$("#username2").val(),
				 "password":$("#password2").val()
				 },
				function(data){
					$(data).each(function(i,n){
						if(n.flag){
							location.href = "${pageContext.request.contextPath}/OrderServlet?method=saveOrder";
						}else{
							$("#message").html("<font color='red'>"+n.message+"</font>")
						}
					})
				},"json")
	})
</script>



</html>