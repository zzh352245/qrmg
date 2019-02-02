/**
 * Created by Administrator on 2016/8/4.
 */

var userName, phoneNum, currentID,channelCode, demo, flag = true;
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
                createTime: 'createTime',
                align: 'center'
            },
            {
                title: '手机号码',
                field: 'userPhone',
                align: 'center'
            },
           
            {
                title: '操作',
                field: '',
                align: 'center',
                formatter: function (value, row) {
                    var e = '<button button="#" mce_href="#" onclick="delNotice(\'' + row.WORKRECORDID + '\')">删除</button> '
                    var d = '<button button="#" mce_href="#" onclick="editNotice(\'' + row.WORKRECORDID + '\')">编辑</button> ';
                    return e + d;
                }
            }
        ]
    });
    getNoticeTableData();
}
function getNoticeTableData() {
    if (flag) {
        userName = "";
        phoneNum = "";
        demo = "";
        channelCode="";
        flag = false;
    } else {
        userName = $("#userName").val();
        phoneNum = $("#phoneNum").val();
        demo = $("#demo").val();
        channelCode = $("#channelCode option:selected").val();
    }
    $.ajax({
        type: "GET",
        url: "/qrmg/person/queryPersonList?start=0&length=10",
        dataType: "json",
        success: function (result) {
            if (result.object) {
                var NoticeTableData = result.object;
                $('#table').bootstrapTable("load", NoticeTableData);
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
/* function openlayer(id){
    layer.open({
        type: 2,
        title: '添加信息',
        shadeClose: true,
        shade: 0.5,
        skin: 'layui-layer-rim',
//            maxmin: true,
        closeBtn:2,
        area: ['80%', '90%'],
        shadeClose: true,
        closeBtn: 2,
        content: 'announcer_tail01.html'
        //iframe的url
    });
} */




