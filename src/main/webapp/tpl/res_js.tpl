<!-- 顶部菜单的handlebars模板 -->
<script id="T_topMenu" type="text/x-handlebars-template">
<ul class="sysMenu">
	{{#each beans}}
	    <li><a href="javascript:;" url="{{url}}" menuId="{{menuId}}">{{menuName}}</a></li>
	{{/each}}
</ul>
</script>

<!-- 左侧菜单的handlebars模板 -->
<script id="T_leftMenu" type="text/x-handlebars-template">
    <div class="leftMenu">
        {{#each object}}
        <h2 menuId="{{menuId}}">{{menuName}}</h2>
        <ul>
            {{#each tMenu}}
            <li><a href="javascript:;" url="{{url}}" menuId="{{menuId}}">{{menuName}}</a></li>
            {{/each}}
        </ul>
        {{/each}}
    </div>
</script>

<!-- 菜单面包屑 -->
<script id="T_menuCrumbs" type="text/x-handlebars-template">
{{#if beans.length}}
您的当前位置：
{{#each beans}}<a href="{{url}}" menuId="{{menuId}}" title="{{title}}">{{title}}</a>&nbsp;>&nbsp{{/each}}
{{/if}}
</script>
<script src="../../lib/jquery/3.0.0/jquery-3.2.1.min.js"></script>
<script src="../../lib/layer/3.0.3/layer.js"></script>
<script src="../../lib/select2/select2.min.js"></script>
<script src="../../dest/compatibilityIE.js"></script>
<script src="../../dest/lib.js"></script> 
<!-- <script src="../../dest/lib.min.js"></script>-->
<script src="../../dest/util.js"></script>
<script src="../../dest/common.min.js"></script>
<script type="text/javascript" src="../../dest/js/custom.js"></script>

<script src="../../lib/datepicker/4.8.b2/WdatePicker.js"></script>
<script src="../../lib/pretty/prettify.js"></script>
<script src="../../lib/swfupload/1.0.0/2.5/swfupload.js"></script>
<script src="../../lib/pagination/1.2.1/jquery.pagination.js"></script>
<script src="../../lib/base64/m.js"></script>

<script type="text/javascript" src="../../dest/uploadFile.js"></script>

<script type="text/javascript" src="../../dest/js/jquery.editable-select.js"></script>
