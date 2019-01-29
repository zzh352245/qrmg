$(document).ready(function(){

	var conInnerConWidth = $(".innercon").width();
	//var conSize = $(".innercon").size();
	var tabHeight = $(".tab a").height();
	if(!window.cur) {
		window.cur=0;
	}
	
	$(".tab a").click(function(){
		var index = $(this).index();
		//$(this).addClass("bt").siblings().removeClass();
		/*$(".maskcon > .innercon").hide().eq(index).show();
		*/
		if(index==0){
			$(".feature_tour .mask").css("height",$("#con1").height());
			//heightAlls();
		}else if(index==1){
			//alert($("#con2").height());
			 $(".feature_tour .mask").css("height",$("#con2").height());
			// heightAlls();
		}else if(index==2){
			 $(".feature_tour .mask").css("height",$("#con3").height());
			// heightAlls();
		}
	   slide(conInnerConWidth, tabHeight, index);
		return false;
	});
	
	/*$(".prev").click(function(){
	   if(window.cur>0) slide(conInnerConWidth, tabHeight, window.cur-1);
		return false;
	});
	
	$(".next").click(function(){
	   if(window.cur<conSize-1) slide(conInnerConWidth, tabHeight, window.cur+1);
		return false;
	});*/
	
	$(".feature_tour .tab, .feature_tour .nav").addClass("vv");
	
	function slide(conInnerConWidth, tabHeight, index){
		$(".tab a").removeClass("current");
		$(".tab a").eq(index).addClass("current");
		$(".tab").css("background-position","0 -"+index*tabHeight+"px");
	//	$(".maskcon").animate({marginLeft:-index*conInnerConWidth+"px"});
		window.cur = index;
	}
	
});
// 模板管理上线模板审核 ,查看 ,编辑a链接点击事件
function aHrefNo(){
	 var aHref = $(".aHref").find("a");
	   $(aHref).each(function(index,data){
		   $(data).click(function(index){
			   return false;
		   })
	   })
	 $(".aHref").find("[src='##imgs/fp.jpg##']").attr("src","imgs/fp.jpg");
	   
}

function buttonCtrl() {
    var allButtonKey = sessionStorage.getItem("allButtonKey");
    var userButtonKey = sessionStorage.getItem("userButtonKey");
    var userIdKey = sessionStorage.getItem("userIdKey");
    var lable_a = $("a[a_code]");
    if (!(allButtonKey && userButtonKey && userIdKey)){
        window.parent.location.href = "../../login.html";
    } else {
        var allButtonValue = JSON.parse(allButtonKey);
        var userButtonValue = JSON.parse(userButtonKey);
        for (var i = 0; i < lable_a.length; i++) {
            var code = $(lable_a[i]).attr("a_code");
            if (code) {
                for (var j = 0; j < allButtonValue.length; j++) {
                    if (code == allButtonValue[j]) {
                        //当前的a标签是需要做权限控制的按钮
                        var flag = false;
                        for (var k = 0; k < userButtonValue.length; k++) {
                            if (code == userButtonValue[k]) {
                                flag = true;
                            }
                        }
                        if (!flag) {
                            $(lable_a[i]).removeAttr("href")
                                        .removeAttr("onclick")
                                        .unbind("click")
                                        .css({ "cursor": "not-allowed", "background": "#F9F9F9", "border-color": "#E6E6E6", "color": "#AAA" })
                                        .children()
                                        .attr("style", "background:none")
                                        .css("display", "none");
                        }
                    }
                }
            }
        }
    }
};
/*校验上传文件不包含'<script','svg','alert','confirm','prompt','onload','onmouseover','onfocus','onerror','xss'*/
function isContains(str, substr) {
    return str.indexOf(substr) >= 0;
}


