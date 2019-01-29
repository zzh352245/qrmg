var curMenu = null;
var zTree_Menu = null;
var setting = {
	view: {
		showLine: false,
		showIcon: false,
		selectedMulti: false,
		dblClickExpand: false,
		addDiyDom: addDiyDom
	},
	data: {
		simpleData: {
			enable: true
		}
	},
	callback: {
		onClick: function(e, treeId, treeNode){
			singlePath(treeNode);
			if(treeNode.isParent){
				//有子节点
				zTree_Menu.expandNode(treeNode, null, false , true, false);
			}else{
				//无子节点
			    var url = treeNode.tt;
			    //由于ie和火狐，其他浏览器不同，ie支持location,貌似不支持src,不知道是什么原因，所以做了判断，可能是iframe的原因
			    if ($.browser.msie) {
			    	///获取iframe的id
			        window.parent.frames['J_busi_iframe'].location = url;
			    }else{
			        window.parent.frames['J_busi_iframe'].src = url;
			    }
			}
		}
	} 
};
var zNodes =[];
function addDiyDom(treeId, treeNode) {  
	var spaceWidth = 5;
	var switchObj = $("#" + treeNode.tId + "_switch"),
	icoObj = $("#" + treeNode.tId + "_ico");
	switchObj.remove();
	icoObj.before(switchObj);

	if (treeNode.level > 1) {
		var spaceStr = "<span style='display: inline-block;width:" + (spaceWidth * treeNode.level)+ "px'></span>";
		switchObj.before(spaceStr);
	}
}
srvMap.add('info', 'userinfo.json','front/sh/user!userinfo');
srvMap.add('role', 'role.json','front/sh/login!getRole');//首页左边栏控制
function createTree(){
	$.ajax({
		type:"POST",
		url:"/mail/front/sh/login!getCurrentUserMenu?uid=getCurrentUserMenu",
		dataType:"json",
		success:function(data){
			if(data.returnCode == 0){
				for(var i=0;i<data.beans.length;i++){
					var  arr={};
		          	arr.id=data.beans[i].id;
		          	arr.pId=data.beans[i].pId;
		          	arr.name=data.beans[i].name;
		          	arr.tt=data.beans[i].url;
		          	zNodes.push(arr);
				}
				var treeObj = $("#treeDemo");
				$.fn.zTree.init(treeObj, setting, zNodes);
				zTree_Menu = $.fn.zTree.getZTreeObj("treeDemo");
				
				var node = zTree_Menu.getNodes()[0];
				if(node.isParent){
					var node2 = node.children[0];
					if(node2.isParent){
						curMenu = node2.children[0];
					}else{
						curMenu = node2;
					}
				}else{
					curMenu = node;
				}
				zTree_Menu.selectNode(curMenu);
				if ($.browser.msie) {
			    	///获取iframe的id
			        window.parent.frames['J_busi_iframe'].location = curMenu.tt;
			    }else{
			        window.parent.frames['J_busi_iframe'].src = curMenu.tt;
			    }
				
				treeObj.hover(function () {
					if (!treeObj.hasClass("showIcon")) {
						treeObj.addClass("showIcon");
					}
				}, function() {
					treeObj.removeClass("showIcon");
				});
			}
		}
	});
    $(window).on("click", function(){
        $('#J_dropDown').hide();
    });
}
function singlePath(currNode) {
	var cLevel = currNode.level;
	//这里假设id是唯一的
	var cId = currNode.id;
	//展开的所有节点，这是从父节点开始查找（也可以全文查找）
	var expandedNodes = zTree_Menu.getNodesByParam("open", true, currNode.getParentNode());

	for(var i = expandedNodes.length - 1; i >= 0; i--){
		var node = expandedNodes[i];
		var level = node.level;
		var id = node.id;
		if (cId != id && level == cLevel) {
			zTree_Menu.expandNode(node, false);
		}
	}
}
function pageJumpLocation(jump_location_href){
	var node = parent.zTree_Menu.getNodeByParam("tt",jump_location_href);
	if(node != null){
		node.checked = true;
		parent.zTree_Menu.selectNode(node);
	}
}