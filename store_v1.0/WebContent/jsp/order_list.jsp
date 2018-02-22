<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>会员登录</title>
		<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
		<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="js/bootstrap.min.js" type="text/javascript"></script>
		<!-- 引入自定义css文件 style.css -->
		<link rel="stylesheet" href="css/style.css" type="text/css" />

		<style>
			body {
				margin-top: 20px;
				margin: 0 auto;
			}
			
			.carousel-inner .item img {
				width: 100%;
				height: 300px;
			}
		</style>
	</head>

	<body>

		<%@include file="top.jsp" %>

		<div class="container">
			<div class="row">

				<div style="margin:0 auto; margin-top:10px;width:950px;">
					<strong>我的订单</strong>
					<table class="table table-bordered">
					<c:forEach var="order" items="${page.list }">
						<tbody>
							<tr class="success">
								<th colspan="5">订单编号:${order.oid} 
								&nbsp;&nbsp;&nbsp;&nbsp;
									<c:if test="${order.state == 1 }">
										<a href="${pageContext.request.contextPath }/OrderServlet?method=findByOid&oid=${order.oid}">付款</a>
									</c:if>
									<c:if test="${order.state == 2 }">
										卖家未发货
									</c:if>
									<c:if test="${order.state == 3 }">
										<a href="${ pageContext.request.contextPath }/AdminOrderServlet?method=changeState&state=${ order.state }&oid=${order.oid}">确认接收</a>
									</c:if>
									<c:if test="${order.state == 4 }">
										<a>订单已完成</a>
									</c:if>
								&nbsp;&nbsp;&nbsp;&nbsp;
								订单总额：￥：${order.total }
								</th>
								
							</tr>	
							<tr class="success">
								<th colspan="5">下单时间：${fn:substring(order.ordertime,0,19) }</th>
							</tr>						
							<tr class="warning">
								<th>图片</th>
								<th>商品</th>
								<th>价格</th>
								<th>数量</th>
								<th>小计</th>
							</tr>
							<c:forEach var="orderItem" items="${order.list }">
							<tr class="active">
								<td width="60" width="40%">
									<input type="hidden" name="id" value="22">
									<img src="${pageContext.request.contextPath }/${orderItem.product.pimage }" width="70" height="60">
								</td>
								<td width="30%">
									<a href="${pageContext.request.contextPath }/ProductServlet?method=findProduct&pid=${orderItem.product.pid}" >${orderItem.product.pname }</a>
								</td>
								<td width="20%">
									￥${orderItem.product.shop_price }
								</td>
								<td width="10%">
									${orderItem.count}
								</td>
								<td width="15%">
									<span class="subtotal">￥：${orderItem.subtotal }</span>
								</td>
							</tr>
							</c:forEach>
						</tbody>
					</c:forEach>
					</table>
				</div>
			</div>
			<div style="text-align: center;">
				<ul class="pagination">
				<c:if test="${page.currPage <= 1 }">
					<li class="disabled"><a aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
				</c:if>
				<c:if test="${page.currPage > 1 }">
					<li class="disabled"><a href="${pageContext.request.contextPath }/OrderServlet?method=listOrder&currPage=${page.currPage-1}" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
				</c:if>
					<c:forEach var="i" begin="1" end="${page.totalPage }">
						<c:if test="${page.currPage != i }">
							<li><a href="${pageContext.request.contextPath }/OrderServlet?method=listOrder&currPage=${i}">${ i }</a></li>
						</c:if>
						<c:if test="${page.currPage == i }">
							<li class="active"><a href="#">${ i }</a></li>
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
						<a href="${pageContext.request.contextPath }/OrderServlet?method=listOrder&currPage=${page.currPage+1}" aria-label="Next">
							<span aria-hidden="true">&raquo;</span>
						</a>
					</li>
				</c:if>
					
				</ul>
			</div>
		</div>

	<%@include file="footer.jsp" %>
	</body>

</html>