/**
 * Created by Administrator on 2016/8/4.
 */

var userName, phoneNum, currentID, flag = true;
function Noticeload() {
    $('#table').bootstrapTable({
        method: "get",
        striped: true,
        singleSelect: false,
        dataType: "json",
        pagination: true, //分页
        pageSize: 10,
        pageNumber: 1,
        search: false, //显示搜索框
        contentType: "application/x-www-form-urlencoded",
        queryParams: null,
        columns: [
           
            {
                title: "ID",
                field: 'id',
                align: 'center',
                valign: 'middle'
            },
            {
                title: '用户姓名',
                field: 'userName',
                align: 'center',
                valign: 'middle'
            },
            {
                title: '登记渠道',
                field: 'channelCode',
                align: 'center',
                valign: 'middle'
            },
            {
                title: '登记时间',
                field: 'createTime',
                align: 'center'
            },
            {
                title: '手机号码',
                field: 'userPhone',
                align: 'center'
            }
        ]
    });
    getNoticeTableData();
}
function getNoticeTableData() {
    $.ajax({
        type: "GET",
        url: "/qrmg/person/queryPersonList?",
        dataType: "json",
        data:{
        	userName: encodeURI($("#userName").val()),
			channelCode: $("#channelCode option:selected").val()
        },
        success: function (result) {
        	if (result.returnCode == 0) {
				var NoticeTableData = result.object;
				$('#table').bootstrapTable("load", NoticeTableData);
			} else {
				layer.msg(result.returnMessage);
				var tBody = $("#table").find("tbody");
				$(tBody).empty().append('<tr class="no-records-found"><td colspan="9">没有找到匹配的记录</td></tr>');
				$(".fixed-table-pagination").hide();
			}
        }
    })
}
function addNotice() {
    openlayer()
    currentID = "";
}
function editNotice(id) {
    openlayer()
    currentID = id;
}
function delNotice(id) {
    alert(id)
    var NoticeId = id;
    $.ajax({
        url: '../WorkRecord/DeleteWork?workId=' + NoticeId,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            if (data.data) {
                alert("删除成功！")
                getNoticeTableData();
            } else {
                alert("删除失败")
            }
        },
        error: function (err) {
        }
    });
}
function getCurrentID() {
    return currentID;
}
function openlayer() {
    layer.open({
        type: 2,
        title: '通知信息',
        shadeClose: true,
        shade: 0.5,
        skin: 'layui-layer-rim',
        closeBtn: 2,
        area: ['98%', '98%'],
        shadeClose: true,
        closeBtn: 2,
        content: 'notic_tail.html'

    });
    
}




