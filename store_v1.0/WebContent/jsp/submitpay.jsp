<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript">
	$(function(){
		$("#submitpay").submit();
	});
</script>
</head>
<body>
	<form id="submitpay" action="https://www.yeepay.com/app-merchant-proxy/node" method="post">
		<input type="hidden" name="pd_FrpId" value="${pay.pd_FrpId }" />
		<input type="hidden" name="p0_Cmd" value="${pay.p0_Cmd }" />
		<input type="hidden" name="p1_MerId" value="${pay.p1_MerId }" />
		<input type="hidden" name="p2_Order" value="${pay.p2_Order }" />
		<input type="hidden" name="p3_Amt" value="${pay.p3_Amt }" />
		<input type="hidden" name="p4_Cur" value="${pay.p4_Cur }" />
		<input type="hidden" name="p5_Pid" value="${pay.p5_Pid }" />
		<input type="hidden" name="p6_Pcat" value="${pay.p6_Pcat }" />
		<input type="hidden" name="p7_Pdesc" value="${pay.p7_Pdesc }" />
		<input type="hidden" name="p8_Url" value="${pay.p8_Url }" />
		<input type="hidden" name="p9_SAF" value="${pay.p9_SAF }" />
		<input type="hidden" name="pa_MP" value="${pay.pa_MP }" />
		<input type="hidden" name="pr_NeedResponse" value="${pay.pr_NeedResponse }" />
		<input type="hidden" name="hmac" value="${ pay.hmac }" />
	</form>

</body>
</html>