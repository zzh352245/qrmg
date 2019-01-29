$(function(){
	var menu = $("#menu li .tittle");
	$("#J_busi_iframe").attr("src",$(".tittleZh").attr('a_href'));
	$('.tittleZh').css({
		"color":"#b55959"
	});
	$(menu).each(function(index,data){
		$(data).on("click",function(index){
			$('.tittle').css({
				"color":"#4c4949"
			});
			$(this).css({
				"color":"#b55959"
			}); 
			//$(this).eq(index);
			var href = $(this).attr('a_href');
			console.log(href);
			$("#J_busi_iframe").attr("src",href);
		})
	})
	$.ajax({
		type: "GET",
		url: "http://172.18.64.67:28080/qrmg/person/queryPersonList?start=1&length=10",
		dataType: "json",
		success: function(data) {
	
	
		},
		error: function(data) {
	
		}
	});
})