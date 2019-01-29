/*
    author: kzhang
    data: 2017-7-27
    desc: 页面工具集重构
 */
!function(global,factory){
    typeof exports === 'object' && typeof module !== "undefined" ? module.exports = factory(jQuery) :
    typeof define === "function" && define.amd ? define(['jquery'], factory) :
    global.Util = factory(jQuery);
}( this, function($){
    'use strict';

    var ArrayProto = Array.prototype, ObjProto = Object.prototype;
    //var SymbolProto = typeof Symbol !== 'undefined' ? Symbol.prototype : null;

    /*var push = ArrayProto.push,
        slice = ArrayProto.slice,
        concat = ArrayProto.concat;*/
    var slice = ArrayProto.slice;

    var toString = ObjProto.toString,
        hasOwn = ObjProto.hasOwnProperty;

    /*var nativeIsArray = Array.isArray,*/
    var nativeKeys = Object.keys,
        nativeValues = Object.values;
        /*nativeCreate = Object.create;*/

   // var Croto = function() {};

    var Util = {
        dialog: {
            openDiv: function(params) {
                var top = params.top || window.top;
                var dialog_id = params.id;
                // var dialog_id = params.id || "default-dialog";
                var _dialog = top.dialog.get( dialog_id );
                if(_dialog) {
                    // 替换缓存旧内容
                    _dialog.content( params.content );
                    toString.call( params.content ) === "[object Object]" || (typeof params.content === "string" && /<([^>]*)>/.test( params.content )) ? _dialog.showModal() : _dialog.show();
                    return _dialog;
                }
                _dialog = top.dialog({
                    id: dialog_id,
                    fixed: !0,
                    title: params.title,
                    content: params.content instanceof jQuery ? params.content.get(0) : params.content,
                    quickClose: params.quickClose,
                    okValue: params.okVal,
                    cancelValue: params.cancelVal,
                    ok: params.okCallback,
                    cancel: params.cancelCallback || function() {
                        this.close();
                        !dialog_id && this.remove();
                        return false;
                    },
                    onclose: params.closeCallback || function() {
                        console.log("onclose");
                    },
                    onremove: params.removeCallback || function() {
                        console.log("remove");
                    },
                    onshow: params.onshow || function() {
                        console.log("show");
                    },
                    cancelDisplay: !!params.cancelVal,
                    skin: params.className,
                    button: params.button
                })
                params.width && _dialog.width( params.width );
                params.height && _dialog.height( params.height );
                toString.call( params.content ) === "[object Object]" || (typeof params.content === "string" && /<([^>]*)>/.test( params.content )) ? _dialog.showModal() : _dialog.show();
                return _dialog;
            },

            msg: function(text, delay, win) {
                var top = win || window.top;
                var _dialog = top.dialog.get( "D_msg2" );
                if ( _dialog ) {
                    _dialog.content( text );
                } else {
                    _dialog = top.dialog({
                        id: "D_msg2",
                        fixed: !0,
                        quickClose: !0,
                        content: text,
                        skin: 'msg2-modal'
                    });
                }
                var msg2_dom = $('.msg2-modal').length ? $('.msg2-modal') : $('.msg2-modal', parent.document);
                msg2_dom.css({ background: "rgba(0,0,0,.7)", color: "#fff", 'borderRadius': '5px', filter: "progid:DXImageTransform.Microsoft.gradient(startColorstr=#7F000000,endColorstr=#7F000000);" });
                _dialog.show();
                setTimeout(function() {
                    _dialog.close();
                }, delay || 2e3);
            },

            confirm: function(params) {
                var top = params.top || window.top;
                var _dialog = top.dialog.get( "D_confirm" );
                if ( _dialog && !params.id ) {
                    _dialog.showModal();
                    return _dialog;
                }
                _dialog = top.dialog({
                    id: params.id || "D_confirm",
                    title: params.title,
                    fixed: !0,
                    content: params.content,
                    quickClose: params.quickClose,
                    okValue: params.okVal || "确认",
                    cancelValue: params.cancelVal || "取消",
                    ok: params.okCallback,
                    cancel: function(){
                        this.close();
                        return  false;
                    },
                    onclose: params.closeCallback || function () {
                        console.log("close confirm");
                    },
                    onremove: params.removeCallback || function () {
                        console.log("remove confirm");
                    }
                });
                params.width && _dialog.width( parseInt( params.width ) );
                params.height && _dialog.height( parseInt( params.height ) );
                _dialog.showModal();
                return _dialog;
            },

            close: function(id, win, isRemove) {
                var top = win || window.top;
                top.dialog.get(id).close();
                isRemove && top.dialog.get(id).remove();
            },

            bubble: function(params) {
                var obj = null;
                if ( toString.call(params) !== "[object Object]" || toString.call(params.element) !== "[object Object]" ) {
                	return false;
                }
                params.content = params.content || "空内容";
                typeof params.quickClose === "undefined" ? params.quickClose = true : "";
                //obj = top.dialog( params ).show( params.selector ? params.element[0] : params.element);
            },

            config: layer.config,
            open: layer.open,
            alert: layer.alert,
            confirm2: layer.confirm,
            msg2: layer.msg,
            load: layer.load,
            tips: layer.tips,
            close2: layer.close,
            closeAll: layer.closeAll,
            tab: layer.tab,
            photos: layer.photos,
            prompt: layer.prompt
        },

        pagination: function(page_index, showPager, params, extra, isHandle) {
            var pageInfo = JSON.parse(sessionStorage.getItem("cp") || "{}");
            // 非手动触发分页器（直接查询）留在当前页
           /*if (!isHandle && pageInfo.href === location.href && pageInfo.id === params.pagination && pageInfo.extra === extra){
                page_index =  sessionStorage.getItem('n') != 1 ? pageInfo.index : (pageInfo.index - 1) < 0 ? 0 : (pageInfo.index - 1);
            }*/
            this.ajax.postJson(
                params.url,
                "start=" + page_index * params.items_per_page + "&limit=" + params.items_per_page + "&" + extra,
                function(data, isSuc){
                    var pager = params.pagination instanceof jQuery ? params.pagination : $("#" + params.pagination.replace(/#/g,''));
                    if (isSuc) {
                        if (typeof params.tabletpl === 'function') {
                            $(params.tablewrap).html(params.tabletpl(data));
                        } else {
                            Util.ajax.loadTemp(params.tablewrap, params.tabletpl, data);
                        }
                        typeof params.pageCallback === 'function' && params.pageCallback.call(pager,data);
                        var len = data.beans.length;
                        sessionStorage.setItem('n',len);
                        if (showPager) {
                            if (len < 1) {
                                pager.hide().siblings().hide();
                            } else {
                               pager.pagination(data.bean.total, {
                                    items_per_page: params.items_per_page,
                                    current_page: page_index,
                                    num_display_entries: 2,
                                    num_edge_entries: 1,
                                    prev_text: "<",
                                    next_text: ">",
                                    link_to: '',
                                    call_callback_at_once: !1,
                                    callback: function(index, panel) {
                                        pageInfo.index = index;
                                        pageInfo.href = location.href;
                                        pageInfo.id = params.pagination;
                                        pageInfo.extra = extra;
                                        sessionStorage.setItem("cp", JSON.stringify(pageInfo));
                                        Util.pagination(index, !1, params, extra, true);
                                    }
                                })
                                pager.show().next().text("共" + data.bean.total + "条").show();
                               
                            }
                        }
                    } else {
                        pager.siblings().hide();
                        var modal = '<div class="ui-loading"><h1>暂时没有数据!</h1></div>';
                        params.tablewrap instanceof jQuery ? params.tablewrap.html(modal) : $("#" + params.tablewrap.replace(/#/g,'')).html(modal);
                    }
                    
                }
            )

        },

        browser: {
            /*
                @parame {string} url中的字段名。(required)
                @parame {string} 指定url，默认当前页面url。
                @return {obj} 返回字段值
             */
            getParameter: function(name, src) {
                var search = src ? src.split('?')[1] : window.location.search.substr(1);
                var reg = new RegExp( "(^|&)" + name + "=([^&]*)(&|$)" );
                var r = search.match(reg);
                if ( r != null ) {
                	return unescape(r[2]);
                }
                return null;
            }
        },

        date: {
            /*
                @parame {string} 格式须严格对应字符串格式，例如YYyy-Mm-Dd hh:mm:ss、MM-dd。[optional,default=yyyy-mm-dd]
                @parame {string | dateObj | timestamp} 目标时间，例如2017-01-01、new Date(1501837472102)、1501837472102。[optional,default=new Date()]
                @return {obj} 返回指定格式的对应时间字符串
             */
            date2str: function( format, time ){
                var role = format || 'yyyy-MM-dd';
                var date = time ? typeof time === 'string' ? this.str2date(time, role) : new Date(time) : new Date();
                role = role.replace(/yyyy|YYYY/, date.getFullYear()),
                role = role.replace(/yy|YY/, date.getYear() % 100 > 9 ? (date.getYear() % 100).toString() : "0" + date.getYear() % 100),
                role = role.replace(/MM/, date.getMonth() > 8 ? (date.getMonth() + 1).toString() : "0" + (date.getMonth() + 1)),
                role = role.replace(/M/g, date.getMonth() + 1),
                role = role.replace(/dd|DD/, date.getDate() > 9 ? date.getDate().toString() : "0" + date.getDate()),
                role = role.replace(/d|D/g, date.getDate()),
                role = role.replace(/hh|HH/, date.getHours() > 9 ? date.getHours().toString() : "0" + date.getHours()),
                role = role.replace(/h|H/g, date.getHours()),
                role = role.replace(/mm/, date.getMinutes() > 9 ? date.getMinutes().toString() : "0" + date.getMinutes()),
                role = role.replace(/m/g, date.getMinutes()),
                role = role.replace(/ss|SS/, date.getSeconds() > 9 ? date.getSeconds().toString() : "0" + date.getSeconds()),
                role = role.replace(/s|S/g, date.getSeconds())
                return role;
            },
            /*
                @parame {string} 时间字符串，例如2017-01-01、01~17。(required)
                @parame {string} 格式须严格对应字符串格式，例如YYyy-Mm-Dd、MM-dd。(required)
                @return {obj} 返回与时间字符串对应的时间对象
             */
            str2date: function( timeStr, format ) {
                function calc(timeStr, role, str) {
                    var len = str.length,
                        // 搜索是否有对应时间单位，没有则不设置
                        index = role.indexOf(str);
                    if ( index < 0 ){
                    	return index;
                    }
                    var _str = timeStr.substr(index - vacancy, len),
                        result = parseInt(_str);
                    10 > result && 0 != _str.charAt(0) && vacancy++;
                    return result
                }
                var vacancy = 0,
                    date = new Date(),
                    role = format.toUpperCase(),
                    year = calc(timeStr, role, "YYYY"),
                    month = calc(timeStr, role, "MM") - 1,
                    day = calc(timeStr, role, "DD"),
                    hour = calc(timeStr, role, "HH"),
                    min = calc(timeStr, role, "MN"),
                    sec = calc(timeStr, role, "SS");

                year > 0 && date.setFullYear(year);
                month >= 0 && date.setMonth(month);
                day > 0 && date.setDate(day);
                hour > 0 && date.setHours(hour);
                min > 0 && date.setMinutes(min);
                sec > 0 && date.setSeconds(sec);
                return date;
            }
        },

        ajax: {
            /**
             * 请求状态码
             * @type {Object}
             */
            reqCode : {
                /**
                 * 成功返回码 0
                 * @type {Number} 1
                 * @property SUCC
                 */
                SUCC : 0
            },
            /**
             * 请求的数据类型
             * @type {Object}
             * @class reqDataType
             */
            dataType : {
                /**
                 * 返回html类型
                 * @type {String}
                 * @property HTML
                 */
                HTML : "html",
                /**
                 * 返回json类型
                 * @type {Object}
                 * @property JSON
                 */
                JSON : "json",
                /**
                 * 返回text字符串类型
                 * @type {String}
                 * @property TEXT
                 */
                TEXT : "text"
            },
            /**
             * 超时,默认超时10000ms
             * 
             * @type {Number} 10000ms
             * @property TIME_OUT
             */
            TIME_OUT : 10000,
            /**
             * 显示请求成功信息
             * 
             * @type {Boolean} false
             * @property SHOW_SUCC_INFO
             */
            SHOW_SUCC_INFO : false,
            /**
             * 显示请求失败信息
             * 
             * @type {Boolean} false
             * @property SHOW_ERROR_INFO
             */
            SHOW_ERROR_INFO : false,
            /**
             * PostJson是对ajax的封装,为创建 "POST" 请求方式返回 "JSON"(text) 数据类型
             * @param {String}
             *            url HTTP(POST)请求地址
             * @param {Object}
             *            data json对象参数
             * @param {Function}
             *            callback [optional,default=undefined] POST请求成功回调函数
             * 支持对象形式传参(兼容IE变量声明问题)
             */
            postJson: function(url, data, callback) {
                if (arguments.length === 1 && toString.call(arguments[0]) === '[object Object]'){
                    data = arguments[0].data;
                    callback = arguments[0].callback;
                    url = arguments[0].url;
                }
                this.ajaxBase(url, 'POST', data, this.dataType.JSON, callback,  false);
            },
            /**
             * PostJsonAsync是对ajax的封装,为创建 "POST" 请求方式返回 "JSON"(text) 数据类型,
             * 采用同步阻塞的post方式调用ajax
             * @param {String}
             *            url HTTP(POST)请求地址
             * @param {Object}
             *            data json对象参数
             * @param {Function}
             *            callback [optional,default=undefined] POST请求成功回调函数
             * 支持对象形式传参
             */
            postJsonSync : function(url, data, callback) {
                if (arguments.length === 1 && toString.call(arguments[0]) === '[object Object]'){
                    data = arguments[0].data;
                    callback = arguments[0].callback;
                    url = arguments[0].url;
                }
                this.ajaxBase(url, 'POST', data, this.dataType.JSON, callback, true);
            },
            /**
             * 基于jQuery ajax的封装，可配置化
             * 
             * @method ajax
             * @param {String}
             *            url HTTP(POST/GET)请求地址
             * @param {String}
             *            type POST/GET
             * @param {Object}
             *            data json参数命令和数据
             * @param {String}
             *            dataType 返回的数据类型
             * @param {Function}
             *            callback [optional,default=undefined] 请求成功回调函数,返回数据data和isSuc
             */
            ajaxBase : function(url, type, data, dataType, callback, sync) {
                var param = "";
                var _this = this;
                var cache = (dataType == "html") ? !0 : 0;
                $.ajax({
                    url : url,
                    type : type,
                    data : data,
                    cache : cache,
                    dataType : dataType,
                    async : !sync,
                    timeout : _this.TIME_OUT,
                    beforeSend : function(xhr) {
                        xhr.overrideMimeType("text/plain; charset=utf-8");
                        Util.dialog.load(2,{
                            time: this.TIME_OUT
                        })
                    },
                    success : function(data) {
                        if (!data) {
                            Util.dialog.msg("get empty data");
                            return;
                        }
                        if (toString.call(data) !== "[object Object]") {
                            Util.dialog.msg("data is not json");
                            return;
                        }
                        if (dataType == "html") {
                            callback(data, true);
                            return;
                        }

                        //超时重定向至登陆页
//                        if (data.returnCode == 0 && data.returnMessage == "未登录或权限不足！") {
//                            //判断是否存在iframe
//                            top.location.href = '../../login.html';
//                            return;
//                        }

                        var isSuc = _this.printReqInfo(data);
                        if (callback && data) {
                            callback(data, isSuc);
                        }
                    },
                    error : function(e) {
                        var retErr ={};
                        retErr['returnCode']="404";
                        retErr['returnMessage']="网络异常或超时，请稍候再试！"; 
                        callback(retErr, 0);
                    },
                    complete:function(){
                        Util.dialog.closeAll("loading");
                    }
                });
            },
            /**
             * 打开请求返回代码和信息
             * 
             * @method printRegInfo
             * @param {Object}
             *            data 请求返回JSON数据
             * @return {Boolean} true-成功; false-失败
             */
            printReqInfo : function(data) {
                var code = parseInt(data.returnCode),
                    msg = data.returnMessage,
                    succ = this.reqCode.SUCC;

                code === succ ? this.SHOW_SUCC_INFO && Util.dialog.msg(msg || "操作成功!")
                            : this.SHOW_ERROR_INFO && Util.dialog.msg(msg || "操作失败");

                return code === succ;
            },

            /**
            * 兼容老版代码
            * 编译模板
            * @parame 模板外围jqueryDom 或 dom id
            * @parame script模板jqueryDom 或 dom id 或 dom String
            * @parame 用于编译的数据;
            */
            loadTemp: function(wrapDom, tpl, data) {
                tpl = tpl instanceof jQuery ? tpl : tpl.indexOf("<") > -1 ? tpl : $("#" + tpl.replace(/#/g,''));
                wrapDom = wrapDom instanceof jQuery ? wrapDom : $("#" + wrapDom.replace(/#/g,''));
                var compiled = Handlebars.compile(tpl.html ? tpl.html() : tpl);
                wrapDom.html(compiled(data))
            }
        },

        trans: {
            b64Coding: function(a) {
                //var b = encodeURIComponent(a)
                     // b = CryptoJS.enc.Base64.stringify(b)
            },
            b64Decoding: function(a) {
               // var b = CryptoJS.enc.Base64.parse(a)
                     // b = decodeURIComponent(b)
            }
        },
        sms: {
            formatStr: function(str) {
                if (str && arguments.length > 1){
                	   for (var i = 1; i < arguments.length; i++){
                		   str = str.replace(new RegExp("\\{" + (i - 1) + "\\}", "g"), arguments[i]);
                		   return str
                	   }
                }
            }
        },

        validate: {
            
        },

        select: {
            /*
             * params.el  {String}下拉框的id
             * params.data  {Array}下拉列表数据
             * params.textKey {String} 下拉框显示的列表内容,接收data中存储对应信息的key
             * params.valueKey  {String} 下拉框显示内容的对应value,接收data中存储对应信息的key
             * params.disableList {Array} 下拉框禁用的列表项,接收对应项的valueKey,可不传
             * params.onChange {Function} 修改select值回调
             * params.onOpen {Function} 打开select下拉回调
             * params.onClose {Function} 关闭select下拉回调
             * params.onSelect {Function} 选中select下拉回调
             * params.placeholder {String} 占位符提示内容
             * 
             */
            create: function(params) {
                var dom = params.el && $(params.el);
                if (!dom) {
                    throw new Error("未传入初始化dom节点");
                    return false;
                };
                var dfConfig = {
                    placeholder: "请选择",
                    closeOnSelect: 1,
                    tags: 0,
                    allowClear: 0,
                    templateResult: function(data) {
                        return data.text;
                    },
                    minimumResultsForSearch: Infinity
                    // maximumInputLength: 5,
                    // maximumSelectionLength: 2,
                    // dropdownParent: $(dom),
                    // ajax: {
                    //     url: 'api',
                    //     cache: true,
                    //     processResults: function(data) {
                    //         return data;
                    //     },
                    //     delay: 250
                    // },
                }
                params = this.formatData(params);
                params = this.bindEvents(params);
                return $(dom).select2($.extend(dfConfig,params));
            },
            disable: function(dom) {
                return $(dom).prop('disabled',true);
            },
            enable: function(dom) {
                return $(dom).prop('disabled',false);
            },
            set: function(dom, val) {
                return $(dom).val(val).trigger("change");
            },
            getText: function(param) {
                if (!param) { throw "缺乏参数"; }
                if (!param.valueKey || !param.textKey) { throw "缺乏valueKey或textKey"; }
                if (!param.data) { throw "缺乏data"; }
                var keys = Util.z.pluck(param.data || [], param.valueKey);
                var texts = Util.z.pluck(param.data || [], param.textKey);
                if (typeof param.val === "string") {
                    return texts[Util.z.indexOf(keys, param.val)];
                } else if (Util.z.isArray(param.val)) {
                    var results = [];
                    Util.z.each(param.val || [], function(value){
                        var index = Util.z.indexOf(keys, value);
                        results.push(texts[index]);
                    })
                    return results;
                }
            },
            destroy: function(dom) {
                return $(dom).empty();
            },
            // 内建方法
            bindEvents: function(params) {
                typeof params.onSelect === 'function' && $(params.el).on("select2:select",params.onSelect) && delete params.onSelect;
                typeof params.onOpen === 'function' && $(params.el).on("select2:open",params.onOpen) && delete params.onOpen ;
                typeof params.onClose === 'function' && $(params.el).on("select2:close",params.onClose) && delete params.onClose;
                typeof params.onChange === 'function' && $(params.el).on("change",params.onChange) && delete params.onChange;
                return params;
            },
            // 内建方法
            formatData: function(params) {
                var arr = [];
                if (params.data instanceof Array) {
                    var disabledList = params.disabledList instanceof Array ? params.disabledList : [];
                    var valueKey = params.valueKey || "id";
                    var textKey = params.textKey || "text";
                    for (var i = 0, len = params.data.length; i < len; i++) {
                        var obj = {};
                        obj.id = params.data[i][valueKey];
                        obj.text = params.data[i][textKey];
                        obj.disabled = disabledList.indexOf(params.data[i][valueKey]) > -1 ? !0 : !!0;
                        arr.push(obj);
                    }
                    delete params.valueKey;
                    delete params.textKey;
                }
                delete params.disabledList;
                params.data = arr;
                return params;
            }
        },
        
        z: {
            /*-------------------------------collection methods-----------------------------*/
            each: function(obj, iterator, context) {
                try{
                    // es5 array
                    if (obj.forEach) {
                        obj.forEach(iterator, context);
                    // normal array like
                    } else if (obj.length){
                        for (var i = 0,len = obj.length; i < len; i++) {
                        	iterator.call(obj, obj[i], i);
                        }
                    // jquery obj
                    } else if (obj.each) {
                        obj.each(function(value){ iterator.call(context, value, i++); })
                    // obj
                    } else {
                        for (var key in obj) {
                            if(hasOwn && !hasOwn.call(obj,key)) {
                            	continue;
                            }
                            var value = obj[key], pair = [key, value];
                            pair.key = key;
                            pair.value = value;
                            iterator.call(context, pair, i++);
                        }
                    }
                } catch(e) {
                    if (e != "__break__") {
                        throw e;
                    }
                }
                return obj;
            },
            map: function(obj, iterator, context) {
                if (obj && obj.map) {
                	return obj.map(iterator, context);
                }
                var results = [];
                Util.z.each(obj, function(value, index) {
                    results.push(iterator.call(context, value, index));
                });
                return results;
            },
            reduce: function(obj, memo, iterator, context) {
                if (obj && obj.reduce) return obj.reduce(iterator.bind(context),memo);
                Util.z.each(obj, function(value, index){
                    memo = iterator.call(context, memo, value, index);
                })
                return memo;
            },
            detect: function(obj, iterator, context) {
                var results;
                Util.z.each(obj,function(value,index){
                    if(iterator.call(context,value)){
                        results = value;
                        throw "__break__";   
                    }
                    return results;
                })
            },
            filter: function(obj, iterator, context) {
                if (obj && obj.filter) {
                	return obj.filter(iterator, context);
                }
                var results = [];
                Util.z.each(obj, function(value, index){
                    if (iterator.call(context,value,index)) results.push(value);
                })
                return results;
            },
            reject: function(obj, iterator, context) {
                var results = [];
                Util.z.each(obj, function(value, index){
                    if (!iterator.call(context,value,index)) {
                    	results.push(value);
                    }
                })
                return results;
            },
            all: function(obj, iterator, context) {
                if (obj && obj.every) return obj.every(iterator, context);
                var results = true;
                Util.z.each(obj, function(value,index) {
                    results = iterator.call(context,value,index);
                    if (!results){
                    	throw "__break__";
                    }
                })
                return results;
            },
            any: function(obj, iterator, context) {
                if (obj && obj.some) return obj.some(iterator, context);
                var results = false;
                Util.z.each(obj, function(value,index) {
                    if (results = iterator.call(context,value,index)) {
                    	throw "__break__";
                    }
                })
                return results;
            },
            include: function(obj, target) {
                if (Util.z.isArray(obj)){
                	return Util.z.indexOf(obj, target) != -1;
                }
                var results = false;
                Util.z.each(obj,function(pair,index){
                    if (target === pair.value) {
                        results = true;
                        throw "__break__";
                    }
                })
                return results;
            },
            pluck: function(obj, key) {
                var results = [];
                Util.z.each(obj, function(value, index){
                    results.push(value[key]);
                })
                return results;
            },
            max: function(obj, iterator, context) {
                if (!iterator && Util.z.isArray(obj)) {
                	return Math.max.apply(Math, Util.z.compact(obj));
                }
                var results;
                Util.z.each(obj, function(value, index){
                    var computed = iterator ? iterator.call(context, value, index) : value;
                    if( results === null || computed >= results.computed) results = {value: value, computed: computed};
                })
                return results.value;
            },
            min: function(obj, iterator, context) {
                if (!iterator && Util.z.isArray(obj)) return Math.min.apply(Math, Util.z.compact(obj));
                var results;
                Util.z.each(obj, function(value, index){
                    var computed = iterator ? iterator.call(context, value, index) : value;
                    if( results === null || computed < results.computed) results = {value: value, computed: computed};
                })
                return results.value;
            },
            sortBy: function(obj, iterator, context) {
                return Util.z.pluck(Util.z.map(obj,function(value,index){
                    return {
                        value: value,
                        compare: iterator.call(context,value,index)
                    }
                }).sort(function(left,right){
                    return left.compare < right.compare ? -1 : 1;
                }),'value');
            },
            sortedIndex: function(array, obj, iterator) {
                iterator = iterator || function(val) { return val; }
                var low = 0, high = array.length;
                while (low < high) {
                    var mid = (low + high) >> 1;
                    iterator(array[mid]) < iterator(obj) ? low = mid + 1 : high = mid;
                }
                return low;
            },
            toArray: function(obj) {
                if (!obj) return [];
                if (Util.z.isArray(obj)) return obj;
                return Util.z.map(obj, function(value){ return value; });
            },
            size: function(obj) {
                return Util.z.toArray(obj).length;
            },
            
            /*--------------------------array methods-----------------------*/
            compact: function(array) {
                return Util.z.filter(array, function(value){ return !!value; });
            },
            without: function(array) {
                var values = slice.call(arguments, 1);
                return Util.z.filter(array, function(value) {return !Util.z.include(values, value); });
            },
            flatten: function(array) {
                return Util.z.reduce(array, [], function(memo, value) {
                    if (Util.z.isArray(value)){
                    	return memo.concat(Util.z.flatten(value));
                    }
                    memo.push(value);
                    return memo
                })
            },
            uniq: function(array, isSorted) {
                return Util.z.reduce(array, [], function(memo, value, index) {
                    if(!index || (isSorted ? Util.z.last(memo) != value : !Util.z.include(memo, value))) {
                    	memo.push(value);
                    	return memo;
                    }
                    
                })
            },
            intersect: function(array) {
                var rest = slice.call(arguments, 1);
                return Util.z.filter(Util.z.uniq(array), function(item) {
                    return Util.z.all(rest, function(other){
                        return Util.z.indexOf(other, item) != -1;
                    })
                })
            },
            zip: function(array) {
                var args = Util.z.toArray(array);
                var len = Util.z.max(Util.z.pluck(args, 'length'));
                var results = new Array(len);
                for (var i = 0; i < len; i++) {
                    results[i] = Util.z.pluck(args, String(i));
                }
                return results;
            },
            first: function(array) {
                return array[0];
            },
            last: function(array) {
                return array(array.length - 1);
            },
            indexOf: function(array, item) {
                if (array.indexOf){
                	return array.indexOf(item);
                }
                for (var i = 0,len = array.length; i < len; i++) {
                    if (array[i] === item){
                    	return i;
                    }
                }
                return -1;
            },
            /*--------------------------object methods-----------------------*/
            keys: function(obj) {
                return nativeKeys ? nativeKeys(obj) : Util.z.pluck(obj, 'key');
            },
            values: function(obj) {
                return nativeValues ? nativeValues(obj) : Util.z.pluck(obj, 'value');
            },
            extend: function(destination, source) {
                for (var key in source){
                	if(hasOwn.call(source,key)) {
                		destination[key] = source[key];
                	}
                }
            },
            clone: function(obj) {
                return Util.z.extend({}, obj);
            },
            isEqual: function(a, b) {
                if (a === b) {
                	return true;
                }
                var atype = typeof a, btype = typeof b;
                if (atype != btype){
                	return false;
                }
                if (a == b){
                	return true;
                }
                if (a.isEqual) {
                	return a.isEqual(b);
                }
                if (atype != 'object'){
                	return false;
                }
                var aKeys = Util.z.keys(a), bKeys = Util.z.keys(b);
                if (aKeys.length !== bKeys.length) {
                	return false;
                }
                for (var key in a) {
                	if (!Util.z.isEqual(a[key], b[key])){
                		return false;
                	}
                }
                return true;
            },
            /*--------------------------base methods-----------------------*/
            isArray: function(obj) {
                return toString.call(obj) === '[object Array]';
            },
            isFunction: function(obj) {
                return typeof obj === 'function';
            },
            isUndefined: function(obj) {
                return typeof obj === 'undefined';
            },
            uniqId: function(prefix) {
                var id = this._idCounter = (this._idCounter || 0) + 1;
                return prefix ? prefix.toString() + id : id;
            }
        }
    }
    Util.z.extend($,{
        browser:{
            msie: /msie/.test(navigator.userAgent.toLowerCase())
        }
    })
    return Util;
} )