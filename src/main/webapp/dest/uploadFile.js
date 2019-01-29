/*******************************************************************************
 * 异步上传文件,兼容IE8，如果可以使用h5则使用h5 (由于时间原因本人只在IE8下调试通过) 
 * 实现单个多次上传不刷新
 * @version 1.5 
 *******************************************************************************/
(function ($) {
    var frameCount = 0;
    var formName = "";
    var iframeObj = null;
    var state = {};
    //var fileHtml = "";
    var colfile = null;
    //清空值
    function clean(target) {
        var file = $(target);
        var col = file.clone(true).val("");
        file.after(col);
        file.remove();
    //关键说明
    //先得到当前的对象和参数，接着进行克隆（同时克隆事件）
    //将克隆好的副本放在原先的之后，按照顺序逐个删除，最后初始化克隆的副本
    };
    function h5Submit(target) {
        var options = state.options;
        var fileObj = target[0].files[0];


        var fd = new FormData();//h5对象
        //附加参数
        for (key in options.params) {
            fd.append(key, options.params[key])
        }
        var fileName = target.attr('name');
        if (fileName == ''
            || fileName == undefined) {
            fileName = 'file';
        }
        fd.append(fileName, fileObj);
        //异步上传
        var xhr = new XMLHttpRequest();
        xhr.upload.addEventListener("progress", function (evt) {
            if (evt.lengthComputable) {
                var percentComplete = Math.round(evt.loaded * 100 / evt.total);
                console.log(percentComplete + "%");
                if (options.onProgress) {
                    options.onProgress(evt);
                }
            }
        }, false);
        xhr.addEventListener("load", function (evt) {
            if ('json' == options.dataType) {
            	var d= evt.target.responseText;
                options.onComplate(d);
            } else {
                options.onComplate(evt.target.responseText);
            }
        }, false);
        xhr.addEventListener("error", function () {
            console.log("error");
        }, false);
        xhr.open("POST", options.url);
        xhr.send(fd);
    }
    function ajaxSubmit(target) {
        var options = state.options;
        if (options.url == '' || options.url == null) {
            console.log("无上传地址");
            return;
        }
        if ($(target).val() == '' || $(target).val() == null) {
        	console.log("请选择文件");
            return;
        }
        var canSend = options.onSend($(target), $(target).val());
        if (!canSend) {
            return;
        }
        /*判断是否可以用h5*/
        if (window.FormData) {
            //h5
            console.log('h5Submit');
            h5Submit(target);
        } else {
            /**/
            if (iframeObj == null) {
                var iframe; 
                try { // for I.E.
                    iframe = document.createElement('<iframe name="fileUploaderEmptyHole" style="position:absolute;top:-8888px">'); 
                } catch (ex) { //for other browsers, an exception will be thrown
                    iframe = document.createElement('iframe'); 
                }
                iframe.id = 'fileUploaderEmptyHole';
                iframe.name = 'fileUploaderEmptyHole';
                iframe.width = 0;
                iframe.height = 0;
                iframe.marginHeight = 0;
                iframe.marginWidth = 0;
                iframe = $(iframe);
                var frameName = 'upload_frame_' + (frameCount++);
                //var iframe = $('<iframe style="position:absolute;top:-9999px" ><script type="text/javascript"></script></iframe>').attr('name', frameName);
                formName = 'form_' + frameName;
                var form = $('<form method="post" style="display:none;" enctype="multipart/form-data"/>').attr('name', formName);
                form.attr("target", 'fileUploaderEmptyHole').attr('action', options.url);
                //
                var fileHtml = $(target).prop("outerHTML");
                colfile = $(target).clone(true);
                $(target).replaceWith(colfile);
                var formHtml = "";
                // form中增加数据域
                for (key in options.params) {
                    formHtml += '<input type="hidden" name="' + key + '" value="' + options.params[key] + '">';
                }
                form.append(formHtml);
                form.append(target);
                iframe.appendTo("body");
                form.appendTo("body");
                iframeObj = iframe;
            }
            //禁用
            $(colfile).attr("disabled", "disabled");
            var form = $("form[name=" + formName + "]");
            
            var fm1=window.frames['fileUploaderEmptyHole']; 
            var fmState=function(){   
                var state=null;   
                if(document.readyState){     
                    try{       
                        state=fm1.document.readyState;    
                    }catch(e){
                        state=null;
                    }     
                    if(state=="complete" || !state){//loading,interactive,complete  
                      //  alert(state);
                        var contents = fm1.document;
                      //  alert(contents);
                        var data = $(contents).contents().find('body').text();
                        options.onComplate(data);
                        iframeObj.remove();
                        form.remove();
                        iframeObj = null;
                        //启用
                        $(colfile).removeAttr("disabled");      
                        return;    
                    }     
                    window.setTimeout(fmState,10);  
                }
            };
            //在改变src或者通过form target提交表单时，执行语句： 
            if(fmState.TimeoutInt) 
                window.clearTimeout(fmState.timeoutInt); 
            fmState.timeoutInt = window.setTimeout(fmState,400);


            try {
               // alert(1);
                form.submit();
            } catch (Eobject) {
                console.log(Eobject);
            }
        }
    };


    //构造
    $.fn.upload = function (options) {
        if (typeof options == "string") {
            return $.fn.upload.methods[options](this);
        }
        options = options || {};
        state = $.data(this, "upload");
        if (state)
            $.extend(state.options, options);
        else {
            state = $.data(this, "upload", {
                options: $.extend({}, $.fn.upload.defaults, options)
            });
        }
    };
    //方法
    $.fn.upload.methods = {
        clean: function (jq) {
            return jq.each(function () {
                clean(jq);
            });
        },
        ajaxSubmit: function (jq) {
            return jq.each(function () {
                ajaxSubmit(jq);
            });
        },
        getFileVal: function (jq) {
            return jq.val()
        }
    };
    //默认项
    $.fn.upload.defaults = {
        url: '',
        dataType: 'json',
        params: {},
        onSend: function (obj, str) {
            return true;
        },
        onComplate: function (e) {},
        onProgress: function (e) {}
    };
})(jQuery);