$(function() {
	/* 登录 */
	document.onkeydown = function(e) {
		var theEvent = window.event || e;
		var code = theEvent.keyCode || theEvent.which;
		if (code == 13) {
			$(".userLogBtn").click();
		}
	}
	/* 验证码刷新*/
	function changeImg() {
		$("#identifyingValue").attr('src', src = 'http://172.18.64.67:28080/qrmg/front/mana/loginCode' + Math.random());
	}
	$("#userName").blur(function() {
		if ($(this).val() == '') {
			$(".reg_error1").html('请输入正确的账号').removeClass("fn-hide");
		} else {
			$(".reg_error1").addClass("fn-hide");
		}
	});
	$("#password").blur(function() {
		if ($(this).val().replace(/\s+/g, "") == '') {
			$(".reg_error2").html('请输入密码').removeClass("fn-hide");
		} else {
			$(".reg_error2").addClass("fn-hide");
		}
	})
	$("#smsCode").blur(function() {
		if ($(this).val() == '') {
			$(".reg_error3").html('请输入验证码').removeClass("fn-hide");
		} else {
			$(".reg_error3").addClass("fn-hide");
		}
	})
	/* 提交 */
	$(".userLogBtn").on("click", function() {
		var userName = $("#userName").val();
		var password = $("#password").val();
		var smsCode = $("#smsCode").val();
		if (userNamereplace(/\s+/g, "") == '') {
			$(".reg_error1").html('请输入正确的账号').removeClass("fn-hide");
		};
		if (password.replace(/\s+/g, "") == '') {
			$(".reg_error2").html('请输入密码').removeClass("fn-hide");
		};
		if (smsCode == '') {
			$(".reg_error3").html('请输入验证码').removeClass("fn-hide");
		};
		$.ajax({
			url: "http://172.18.64.67:28080/qrmg/front/mana/account/login?userName=admin&userPass=admin.123",
			dataType: "json",
			type: "get",
			async: false,
			data: {
				userName: userName,
				userPass: password,
				smsCode: smsCode
			},
			success: function(json) {
				var code = json.


			},
			error: function(json) {

			}
		});

	})

})
