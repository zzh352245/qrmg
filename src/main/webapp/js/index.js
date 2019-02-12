$(window).resize(function(e) {
    $("#bd").height($(window).height() - $("#hd").height() - $("#ft").height()-6);
	$(".wrap").height($("#bd").height()-6);
	$(".nav").css("minHeight", $(".sidebar").height() - $(".sidebar-header").height()-1);
	$("#iframe").height($(window).height() - $("#hd").height() - $("#ft").height()-12);
}).resize();
/* $('.exitDialog').Dialog({
	title:'提示信息',
	autoOpen: false,
	width:400,
	height:200

});
$('.exit').click(function(){
	$('.exitDialog').Dialog('open');
});

$('.exitDialog input[type=button]').click(function(e) {
    $('.exitDialog').Dialog('close');

	if($(this).hasClass('ok')){
		window.location.href = "../login.html"	;
	}
}); */
$(function() {
	$('.nav>li').click(function () {
		$('.nav>li').removeClass("current");
		$(".subnav li a").removeClass("color");
		$(this).addClass("current");
		var $ul = $(".subnav",this);
		$(".subnav").slideUp();
		if ($ul.is(':visible')) {
			$ul.slideUp();
			//$(".subnav li a").removeClass("color");
		}else {
			$ul.slideDown();
		}
	});
	$(".subnav li").click(function(e){
		$(".subnav li a").removeClass("color");
		$("a",$(this)).addClass("color");
		e.stopPropagation();

	});
	//$(".nav").click(function(){
    //
	//})
	//修改密码
	
	
});

function zhbulr(){
	var account = $("#account",parent.document).val();
	if (account == '' || account == undefined || account == null) {
		parent.layer.msg("账号不可为空！");
		return;
	}
	$.ajax({
		type: "get",
		url: "/qrmg/front/mana/account/getIsReg?",
		dataType: "json",
		data: {
			userName: account
		},
		success: function(result) {
			if (result.object=="false") {
				alert("账号已存在！");
				$("#account",parent.document).val("");
			}
		}
	})
}

function showchannelurl(showchanurl){
	if(showchanurl == ""){
		$("#churlshowdiv",parent.document).show();
	}else{
		$("#churlshowdiv",parent.document).hide();
	}
}