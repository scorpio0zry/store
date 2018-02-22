<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<HTML>
	<HEAD>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="Content-Language" content="zh-cn">
		<link rel="stylesheet"
			href="${pageContext.request.contextPath }/css/bootstrap.min.css"
			type="text/css" />
		<script
			src="${pageContext.request.contextPath }/js/jquery-1.11.3.min.js"
			type="text/javascript"></script>
		<script src="${pageContext.request.contextPath }/js/bootstrap.min.js"
			type="text/javascript"></script>
		<link href="${pageContext.request.contextPath}/css/Style1.css" rel="stylesheet" type="text/css" />
		
	</HEAD>
	<body>
		<br>
		<form id="Form1" name="Form1" action="${pageContext.request.contextPath}/user/list.jsp" method="post">
			<table cellSpacing="1" cellPadding="0" width="100%" align="center" bgColor="#f5fafe" border="0">
				<TBODY>
					<tr>
						<td class="ta_01" align="center" bgColor="#afd1f3">
							<strong>订单列表</strong>
						</TD>
					</tr>
					
					<tr>
						<td class="ta_01" align="center" bgColor="#f5fafe">
							<table cellspacing="0" cellpadding="1" rules="all"
								bordercolor="gray" border="1" id="DataGrid1"
								style="BORDER-RIGHT: gray 1px solid; BORDER-TOP: gray 1px solid; BORDER-LEFT: gray 1px solid; WIDTH: 100%; WORD-BREAK: break-all; BORDER-BOTTOM: gray 1px solid; BORDER-COLLAPSE: collapse; BACKGROUND-COLOR: #f5fafe; WORD-WRAP: break-word">
								<tr
									style="FONT-WEIGHT: bold; FONT-SIZE: 12pt; HEIGHT: 25px; BACKGROUND-COLOR: #afd1f3">

									<td align="center" width="10%">
										序号
									</td>
									<td align="center" width="10%">
										订单编号
									</td>
									<td align="center" width="10%">
										订单金额
									</td>
									<td align="center" width="10%">
										收货人
									</td>
									<td align="center" width="10%">
										订单状态
									</td>
									<td align="center" width="50%">
										订单详情
									</td>
								</tr>
									<c:forEach var="order" items="${page.list }" varStatus="status">
										<tr onmouseover="this.style.backgroundColor = 'white'"
											onmouseout="this.style.backgroundColor = '#F5FAFE';">
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="18%">
												${ status.count }
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="17%">
												${ order.oid }
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="17%">
												${ order.total }
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="17%">
												${ order.name }
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="17%">
												<c:if test="${order.state == 1 }">
													未付款
												</c:if>
												<c:if test="${order.state == 2 }">
													<a href="${ pageContext.request.contextPath }/AdminOrderServlet?method=changeState&state=${ order.state }&oid=${order.oid}"><font color="blue">发货</font></a>
												</c:if>
												<c:if test="${order.state == 3 }">
													等待确认收货
												</c:if>
												<c:if test="${order.state == 4 }">
													订单完成
												</c:if>
											
											</td>
											<td align="center" style="HEIGHT: 22px">
												<input type="button" value="订单详情" onclick="showDetail('${order.oid}')"/>
											</td>
							
										</tr>
									</c:forEach>	
							</table>
						</td>
					</tr>
					<tr align="center">
						<td colspan="7">
							第${page.currPage }/${page.totalPage }页 
							<c:if test="${page.currPage <= 1 }">
								<a>首页</a>|
								<a>上一页</a>|
							</c:if>
							<c:if test="${page.currPage > 1 }">
								<a href="${ pageContext.request.contextPath }/AdminOrderServlet?method=findAll&currPage=1&state=${state}" >首页</a>|
								<a href="${ pageContext.request.contextPath }/AdminOrderServlet?method=findAll&currPage=${page.currPage-1}&state=${state}">上一页</a>|
							</c:if>
							<c:forEach var="i" begin="1" end="${page.totalPage }">
								<c:if test="${page.currPage == i }">
									<a>${ i }</a>
								</c:if>
								<c:if test="${page.currPage != i }">
									<a href="${ pageContext.request.contextPath }/AdminOrderServlet?method=findAll&currPage=${i}&state=${state}">${ i }</a>
								</c:if>
							
							</c:forEach>
							<c:if test="${page.currPage >= page.totalPage }">
								<a>下一页</a>|
								<a>尾页</a>|
							</c:if>
							<c:if test="${page.currPage < page.totalPage }">
								<a href="${ pageContext.request.contextPath }/AdminOrderServlet?method=findAll&currPage=${page.currPage+1 }&state=${state}">下一页</a>|
								<a href="${ pageContext.request.contextPath }/AdminOrderServlet?method=findAll&currPage=${page.totalPage }&state=${state}" >尾页</a>|
							</c:if>
						</td>
					</tr>
				</TBODY>
			</table>
		</form>
	</body>
	<!-- 商品订单详情模态框 -->
	<!-- Modal -->
	<div class="modal fade" id="product" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	        <h4 class="modal-title" id="myModalLabel">订单详情</h4>
	        <br/>
	   		<div>订单号：<span id="ospan"></span>&nbsp;&nbsp;<span id="mspan"></span></div>
	      </div>
	      <div class="modal-body">
	        <table class="table table-bordered"  id="plist">
				<tr class="success">
					<td>商品编号</td>
					<td>商品图片</td>
					<td>商品名称</td>
					<td>商品数量</td>
					<td>商品小计</td>
				</tr>
			</table>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	
	<script type="text/javascript">
		function showDetail(oid){
			$("#ospan").html(oid);
		    $.post("${pageContext.request.contextPath}/AdminOrderServlet",
					{"method":"findOid","oid":oid},
					function(data){
						$("#mspan").html("总金额："+data.total+"元");
						$("#plist").html("<tr class='success'><td>商品编号</td><td>商品图片</td><td>商品名称</td><td>商品数量</td><td>商品小计</td></tr>");
						$(data.list).each(function(i,n){
							$("#plist").append("<tr><td>"+n.product.pid+"</td><td><img width='40' height='45' src='${ pageContext.request.contextPath }/"+n.product.pimage+"'></td><td>"+n.product.pname+"</td><td>"+n.count+"</td><td>"+n.subtotal+"</td></tr>")
						})
			},"json")
			
			$("#product").modal('show');
		}
	</script>
</HTML>

