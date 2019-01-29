//////////////////////////////////////////////弹框内容区/////////////////////////////////////////////////////
window.JSON_OBJ = {
    ad: {
        _dialog: null,
        data: null
    },
    img: {
        _dialog: null,
        data: null
    },
    module: {
        _dialog: null,
        data: null
    }
};

function openListDialog(div) {
    var dialogType = div.attrJson.type;
    if (dialogType == "1") {
        JSON_OBJ.ad.data = div;
        openAdvertisementDialog(div);
    } else if (dialogType == "2") {
        JSON_OBJ.img.data = div;
        openModuleDialog(div);
    } else if (dialogType == "3") {
        JSON_OBJ.module.data = div;
        openModuleDialog3(div);
    }
    top.JSON_OBJ = window.JSON_OBJ;
}


/**
 * 广告弹窗
 * **/
function advert_dataLoad(width, height, page) {
    var G_params = {
        url: "/mail/front/sh/advertise!selectAdvertiseListBySize?uid=selectAdvertiseListBySize&length=" + width + "&width=" + height + "&type=" + page,
        items_per_page: 5, // 每页数     @param : limit
        page_index: 0, //当前页  @param : start
        pagination: $('#pagination', JSON_OBJ.ad._dialog.node), //分页id
        tabletpl: $('#T_tabletpl1'), //表格模板容器
        tablewrap: $('#J_tabletpl1', JSON_OBJ.ad._dialog.node) //表格容器
        //pageCallback: pageback
    };
    Util.pagination(0, true, G_params, "");
}

function openAdvertisementDialog(attrObj) {
    var width = attrObj.attrJson.width;
    var height = attrObj.attrJson.height;
    var page = attrObj.attrJson.page;
    var params3 = {
        id: 'd1', //弹出对话框的id
        title: '广告 ', //左上角提示标题
        content: $('#listFrame1').html(), //具体提示内容
        width: '700', //对话框宽度
        height: '400'
    }
    JSON_OBJ.ad._dialog = Util.dialog.openDiv(params3);
    advert_dataLoad(width, height, page);
    JSON_OBJ.ad.data = attrObj;
}



/**
 * 图片组建弹窗
 * ***/
function parts_dataLoad(width, height, page) {
    var G_params = {
        url: "/mail/front/sh/mailDeployPart!selectImgPartBySize?uid=u005&width=" + width + "&height=" + height + "&type=" + page,
        items_per_page: 5, // 每页数     @param : limit
        page_index: 0, //当前页  @param : start
        pagination: $('#pagination', JSON_OBJ.img._dialog.node), //分页id
        tabletpl: $('#T_tabletpl2'), //表格模板容器
        tablewrap: $('#J_tabletpl2', JSON_OBJ.img._dialog.node) //表格容器
        //pageCallback: pageback
    };
    Util.pagination(0, true, G_params, "");
}

function openModuleDialog(attrObj) {
    var width = attrObj.attrJson.width;
    var height = attrObj.attrJson.height;
    var page = attrObj.attrJson.page;
    var params3 = {
        id: 'd2', //弹出对话框的id
        title: '图片组件 ', //左上角提示标题
        content: $('#listFrame2').html(), //具体提示内容
        width: '700', //对话框宽度
        height:'300'
    }
    JSON_OBJ.img._dialog = Util.dialog.openDiv(params3);
    parts_dataLoad(width, height, page);
}


/**
 * 模块组建弹窗
 * ***/
function module_parts_dataLoad(width, height, page) {
    var G_params = {
        url: "/mail/front/sh/mailDeployPart!selectModulePartBySize?uid=u006&width=" + width + "&height=" + height + "&type=" + page,
        items_per_page: 5, // 每页数     @param : limit
        page_index: 0, //当前页  @param : start
        pagination: $('#pagination', JSON_OBJ.module._dialog.node), //分页id
        tabletpl: $('#T_tabletpl3'), //表格模板容器
        tablewrap: $('#J_tabletpl3', JSON_OBJ.module._dialog.node) //表格容器
        //pageCallback: pageback
    };
    Util.pagination(0, true, G_params, "");
}

function openModuleDialog3(attrObj) {
    var width = attrObj.attrJson.width;
    var height = attrObj.attrJson.height;
    var page = attrObj.attrJson.page;
    var params3 = {
        id: 'd3', //弹出对话框的id
        title: '模块组件 ', //左上角提示标题
        content: $('#listFrame3').html(), //具体提示内容
        width: '700' //对话框宽度
    }
    JSON_OBJ.module._dialog = Util.dialog.openDiv(params3);
    module_parts_dataLoad(width, height, page);
}

function ad_confirm() {
    var sel = $("input:radio[name='right']:checked");
    if (sel.length == 0) {
        Util.dialog.msg("请重新选择");
        return false;
    }
    var selValue = sel.val();
    var data = selValue.split("|");
    var adId = data[0];
    var adCode = data[1];
    var picUrl = data[2];
    var jumpUrl = data[3];
    
    var selObj = JSON_OBJ.ad.data;

    var type = selObj.attrJson.type;
    var width = selObj.attrJson.width;
    var height = selObj.attrJson.height;
    var moduleId = selObj.attrJson.moduleId;
    var moduleCode = selObj.attrJson.moduleCode;
    var adPositionId = selObj.attrJson.adPositionId;
    var adPositionCode = selObj.attrJson.adPositionCode;
    var page = selObj.attrJson.page;
    var creater = selObj.attrJson.creater;

    var json = '{"type":"' + type + '","width":"' + width + '","height":"' + height + '"' +
        ',"moduleId":"' + moduleId + '","moduleCode":"' + moduleCode + '","adId":"' + adId + '","adCode":"' + adCode + '",' +
        '"adPositionId":"' + adPositionId + '","adPositionCode":"' + adPositionCode + '","page":"' + page + '","creater":"' + creater + '"}';

    selObj.ele.attr("targeModleAttr", json);

    var imghtml = getImgLable(picUrl, jumpUrl);
    selObj.ele.html(imghtml).css("background","none");
    JSON_OBJ.ad._dialog.close();
    selObj.ele.css("border", "0");
}

function ad_cancel() {
    window.JSON_OBJ.ad._dialog.close();
}

function imgParts_confirm() {
    var sel = $("input:radio[name='right']:checked");
    if (sel.length == 0) {
        Util.dialog.msg("请重新选择");
        return false;
    }
    var selValue = sel.val();
    var data = selValue.split("|");
    var imgPartsId = data[0];
    var imgPartsCode = data[1];
    var picUrl = data[2];
    var picId = data[3];
    var jumpUrl = "";
    var selObj = JSON_OBJ.img.data;

    var type = selObj.attrJson.type;
    var width = selObj.attrJson.width;
    var height = selObj.attrJson.height;
    //var adId = selObj.attrJson.adId;
    var adCode = selObj.attrJson.adCode;
    var adPositionId = selObj.attrJson.adPositionId;
    var adPositionCode = selObj.attrJson.adPositionCode;
    var page = selObj.attrJson.page;
    var creater = selObj.attrJson.creater;

    var json = '{"type":"' + type + '","width":"' + width + '","height":"' + height + '"' +
        ',"moduleId":"' + imgPartsId + '","moduleCode":"' + imgPartsCode + '","adId":"' + picId + '","adCode":"' + adCode + '",' +
        '"adPositionId":"' + adPositionId + '","adPositionCode":"' + adPositionCode + '","page":"' + page + '","creater":"' + creater + '"}';

    selObj.ele.attr("targeModleAttr", json);

    var imghtml = getImgLable(picUrl, jumpUrl);
    selObj.ele.html(imghtml);
    JSON_OBJ.img._dialog.close();
    selObj.ele.css("border", "0")
}

function imgParts_cancel() {
    window.JSON_OBJ.img._dialog.close();
}

function moduleParts_confirm() {
    var sel = $("input:radio[name='right']:checked");
    if (sel.length == 0) {
        Util.dialog.msg("请重新选择");
        return false;
    }
    var selValue = sel.val();
    var data = selValue.split("|");
    var modulePartsId = data[0];
    var modulePartsCode = data[1];
    var moduleContent = data[2];
    var selObj = JSON_OBJ.module.data;

    var type = selObj.attrJson.type;
    var width = selObj.attrJson.width;
    var height = selObj.attrJson.height;
    var adId = selObj.attrJson.adId;
    var adCode = selObj.attrJson.adCode;
    var adPositionId = selObj.attrJson.adPositionId;
    var adPositionCode = selObj.attrJson.adPositionCode;
    var page = selObj.attrJson.page;
    var creater = selObj.attrJson.creater;

    var json = '{"type":"' + type + '","width":"' + width + '","height":"' + height + '"' +
        ',"moduleId":"' + modulePartsId + '","moduleCode":"' + modulePartsCode + '","adId":"' + adId + '","adCode":"' + adCode + '",' +
        '"adPositionId":"' + adPositionId + '","adPositionCode":"' + adPositionCode + '","page":"' + page + '","creater":"' + creater + '"}';

    selObj.ele.attr("targeModleAttr", json);
    selObj.ele.html(moduleContent);
    JSON_OBJ.module._dialog.close();

    selObj.ele.css("border", "0")
}

function moduleParts_cancel() {
    window.JSON_OBJ.module._dialog.close();
}

function getImgLable(picUrl, jumpUrl) {
    var embed = '<div style="width:100%;height:100%;" class="embed1"><a to_href="' + jumpUrl + '" href="#"><img src="' + picUrl + '" style="width:100%;"></a></div>';
    return embed;
}

/*图片组件缩略图*/

function morePics(id, url, master_type,picId) {
    var doc = $("#J_busi_iframe")[0].contentDocument || $("#J_busi_iframe",top.document)[0].document;
    JSON_OBJ.img._dialog.close();
    $(".imagePicPc",doc).empty();
    $(".imagePicH5",doc).empty();
    var img2src = url.split("|");
    var picId = picId.split("|");
    var sizes = img2src.length;
    var num = 0;
    if ("pc" == master_type) {
        var kw = top.screen.width / 2 - 644 > 0 ? top.screen.width / 2 - 644 : 0;
        var kh = top.screen.height / 2 - 355 > 0 ? top.screen.height / 2 - 355 : 0;
        $(".cd-popup-sexShowPc",doc).addClass('is-visible');
        $('.cd-popup-show-imagePic',doc).show().css({ "position": "fixed", "left": kw + "px", "top": kh + "px" });
        $(".leftMask").attr("style", "width:204px;height:100%;background:#000;position:absolute;left:0;top:0;opacity:.7;filter:progid:DXImageTransform.Microsoft.Alpha(Opacity=70)");
        $(".topMask").attr("style", "width:100%;height:55px;background:#000;position:fixed;left:204px;top:0;opacity:.7;filter:progid:DXImageTransform.Microsoft.Alpha(Opacity=70)");
        $("body",doc).css("overflow", "hidden");
        var slider = $(".navPicPC .imagePicPc",doc)
        for (var i = 0; i < sizes; i++) {
            $(".imagePicPc",doc).append('<li id="pc-li-' + i + '" align="center"><img src="'+ img2src[i] +'" alt="" id="img' + i + '"/><a i="close" class="normalBtn BGblue celsela" href="javascript:;" onclick=rightPic("' + img2src[i] + '","' + id + '","'+picId[i]+'")>确定</a><a i="close" class="cancels normalBtn BGgray normalBtn" href="javascript:;" style="margin-left:20px">取消</a></li>');
            slider.css({
                left: 0 
            });
        }
        $(".navPicPC .btn_l",doc).click(function() {
            //debugger;
            num--;
            if (num == -1) {
                slider.css({ left: -(sizes - 1) * 880 });
                num = sizes - 1;
            }
            slider.stop().animate({ left: -num * 880 }, 500);
        });
        //点击向右轮播
        $(".navPicPC .btn_r",doc).click(function() {
            num++;
            if (num == sizes) {
                slider.css({ left: 0 });
                num = 0;
            }
            slider.stop().animate({ left: -num * 880 }, 500);
        });
    }
    if ("h5" == master_type) {
        slider = $(".navPicH5 .imagePicH5",doc)
        $(".cd-popup-sexShowH5",doc).addClass('is-visible');
        var kw = top.screen.width / 2 - 644 > 0 ? top.screen.width / 2 - 644 : 0;
        var kh = top.screen.height / 2 - 355 > 0 ? top.screen.height / 2 - 355 : 0;
        $('.cd-popup-show-imagePicH5',doc).show().css({ "position": "fixed", "left": kw + "px", "top": kh + "px" });
        $(".leftMask").attr("style", "width:204px;height:100%;background:#000;position:absolute;left:0;top:0;opacity:.7;filter:progid:DXImageTransform.Microsoft.Alpha(Opacity=70)");
        $(".topMask").attr("style", "width:100%;height:55px;background:#000;position:fixed;left:204px;top:0;opacity:.7;filter:progid:DXImageTransform.Microsoft.Alpha(Opacity=70)");
        $("body",doc).css("overflow", "hidden");
        for (var i = 0; i < sizes; i++) {
            $(".imagePicH5",doc).append('<li id="h5-li-' + i + '" align="center"><img src="'+ img2src[i] +'" alt="" id="img' + i + '"/><a i="close" class="normalBtn BGblue celsela" href="javascript:;" onclick=rightPic("' + img2src[i] + '","' + id + '","'+picId[i]+'")>确定</a><a i="close" class="cancels normalBtn BGgray normalBtn" href="javascript:;" style="margin-left:20px">取消</a></li>');
            slider.css({
                left: 0
            }, 500);
        }
        $(".navPicH5 .btn_l",doc).click(function() {
            num--;
            if (num == -1) {
                slider.css({ left: -(sizes - 1) * 880 });
                num = sizes - 1;
            }
            slider.stop().animate({ left: -num * 880 }, 500);
        });
        //点击向右轮播
        $(".navPicH5 .btn_r",doc).click(function() {
            num++;
            if (num == sizes) {
                slider.css({ left: 0 });
                num = 0;
            }
            slider.stop().animate({ left: -num * 880 }, 500);
        });
    }
    $('.the-popup-show-image',doc).on('click', function(event) {
        if ($(event.target).is('.cd-popup-close1') || $(event.target).is('.cancels')) {
            closeImgPreview();
        }
    });
}

/*点击确定*/
function rightPic(url, id,thisPicId) {
    var obj = $("#tabList .thumbnailPc",top.document);
    var picId = $("#tabList .picId",top.document);
    var radioId = $("#tabList .radioId",top.document);
    var index = 0;
    var newValue;
    for (var j = 0; j < picId.length; j++) {
        if (id == $(picId[j]).html()) {
            index = j;
            var selsUrl = $(picId[j]).prev().prop("outerHTML");
            var elementSelsUrl = $(selsUrl).get(0);
            var valUrl = $(elementSelsUrl).children().val();
            var dataValue = valUrl.split("|");
            //var picUrl = dataValue[2];
            newValue = dataValue[0] + "|" + dataValue[1] + "|" + url + "|" + thisPicId;
            break;
        }
    }
    $(obj[index]).children(".firstUrl").attr("src", url);
    

    $(radioId[index]).val(newValue);
    closeImgPreview();
}

function rightPicH5(url, id,thisPicId) {
    var obj = $("#tabList .thumbnailPc",top.document);
    var picId = $("#tabList .picId",top.document);
    var radioId = $("#tabList .radioId",top.document);
    var index = 0;
    var newValue;
    for (var j = 0; j < picId.length; j++) {
        if (id == $(picId[j]).html()) {
            // debugger;
            index = j;
            var selsUrl = $(picId[j]).prev().prop("outerHTML");
            var elementSelsUrl = $(selsUrl).get(0);
            var valUrl = $(elementSelsUrl).children().val();
            // Util.dialog.msg(valUrl);
            var dataValue = valUrl.split("|");
           // var picUrl = dataValue[2];
            newValue = dataValue[0] + "|" + dataValue[1] + "|" + url + "|" + thisPicId;
            break;
        }
    }
    $(obj[index]).children(".firstUrl").attr("src", url);
    $(radioId[index]).val(newValue);
    closeImgPreview();
}

function closeImgPreview(ctx) {
    JSON_OBJ.img._dialog.show();
    var doc = $("#J_busi_iframe",top.document)[0].contentDocument || $("#J_busi_iframe",top.document)[0].document;
    $(".cd-popup-sexShowH5",doc).removeClass('is-visible').next().hide();
    $(".cd-popup-sexShowPc",doc).removeClass('is-visible').next().hide();
    $(".topMask",top.document).removeAttr("style");
    $(".leftMask",top.document).removeAttr("style");
    $("body",doc).css("overflow", "auto");
}