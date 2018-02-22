$(function(){
		// 异步校验 用户名
		$("#username").blur(function(){
			var value = $(this).val();
			if(value != ""){
				$.post("/store_v1.0/UserServlet",{"method":"checkUsername","value":value},function(data){
					if(data == 2){
						$("#usersp").html("<font color='green'>用户名可以使用</font>");

						$("#submit").prop("disabled",false);
					}else if(data == 1){
						$("#usersp").html("<font color='red'>用户名已经存在</font>");
						// 提交按钮变灰
						$("#submit").prop("disabled",true);
					}
				})
			}else{
				$("#usersp").html("<font color='red'>用户名不能为空</font>");
				$("#submit").prop("disabled",true);
				
			}
			
		});
		
		// 校验密码是否一致
		$("#confirmpwd").blur(function(){
			var password = $("#password").val();
			var confirmpwd = $(this).val();
			if(password == confirmpwd && password != ""){
				$("#pwdsp").html("<font color='green'>密码输入一致</font>");
				$("#submit").prop("disabled",false);
			}else if(password == ""){
				$("#pwdsp").html("<font color='red'>密码不能为空</font>");
				$("#submit").prop("disabled",true);
			}else{
				$("#pwdsp").html("<font color='red'>密码输入不一致</font>");
				$("#submit").prop("disabled",true);
			}
		});
		
		//校验邮箱是否正确
		$("#email").blur(function(){
			var value = $(this).val();
			if(value != ""){
				$.post("/store_v1.0/UserServlet",{"method":"checkEmail","value":value},function(data){
					if(data == 1){
						$("#emsp").html("<font color='green'>邮箱格式正确</font>");
						$("#submit").prop("disabled",false);
					}else if(data == 2){
						$("#emsp").html("<font color='red'>邮箱格式不正确</font>");
						$("#submit").prop("disabled",true);
					}
				})
			}
		});
		
		//姓名不能为空
		$("#name").blur(function(){
			var value = $(this).val();
			if(value == null || value == ""){
				$("#nasp").html("<font color='red'>姓名不能为空</font>");
				$("#submit").prop("disabled",true);
			}else{
				$("#nasp").html("<font color='green'>姓名格式正确</font>");
				$("#submit").prop("disabled",false);
			}
			
		});
})
