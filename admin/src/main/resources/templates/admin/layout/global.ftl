<#import "/spring.ftl" as spring/>
<#macro basePath>${absPath!'/'}</#macro>
<#--metric插件-->
<#macro importOSSResource(pathList...)>
    <#list pathList as path>
        <#if path?ends_with('js')>
        <script src="${ossPath}${path}" type="text/javascript"></script>
        </#if>
        <#if path?ends_with('css')>
        <link href="${ossPath}${path}" rel="stylesheet" type="text/css"/>
        </#if>
    </#list>
</#macro>

<#--插件-->
<#macro importResource(pathList...)>
    <#list pathList as path>
        <#if path?ends_with('js')>
        <script src="<@basePath/>${path}" type="text/javascript"></script>
        </#if>
        <#if path?ends_with('css')>
        <link href="<@basePath/>${path}" rel="stylesheet" type="text/css"/>
        </#if>
    </#list>
</#macro>

<#macro importOSSPlugins(pathList...)>
    <#list pathList as path>
        <#if path?ends_with('js')>
        <script src="${ossPath}${path}" type="text/javascript"></script>
        </#if>
        <#if path?ends_with('css')>
        <link href="${ossPath}${path}" rel="stylesheet" type="text/css"/>
        </#if>
    </#list>
</#macro>


<#--插件-->
<#macro importPlugins(pathList...)>
    <#list pathList as path>
        <#if path?ends_with('js')>
        <script src="<@basePath/>${path}" type="text/javascript"></script>
        </#if>
        <#if path?ends_with('css')>
        <link href="<@basePath/>${path}" rel="stylesheet" type="text/css"/>
        </#if>
    </#list>
</#macro>


<#-- 此处是公用的引入-->

<#macro importCss()>

    <@importOSSResource
    <#-- BEGIN GLOBAL MANDATORY STYLES -->
    "global/plugins/font-awesome/css/font-awesome.min.css",
    "global/plugins/simple-line-icons/simple-line-icons.min.css",
    "global/plugins/bootstrap/css/bootstrap.min.css",
    "global/plugins/bootstrap-switch/css/bootstrap-switch.min.css",

    <#-- BEGIN PAGE LEVEL PLUGINS -->
    'global/plugins/pace/pace-theme-flash.css',

    "global/plugins/bootstrap-modal/css/bootstrap-modal-bs3patch.css",
    "global/plugins/bootstrap-modal/css/bootstrap-modal.css",
    "global/plugins/bootstrap-sweetalert/sweetalert.css",
    "global/plugins/bootstrap-summernote/summernote.css",
    "global/plugins/select2/css/select2.min.css",
    "global/plugins/select2/css/select2-bootstrap.min.css",

    "global/plugins/bootstrap-daterangepicker/daterangepicker.min.css",
    "global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css",
    "global/plugins/bootstrap-timepicker/css/bootstrap-timepicker.min.css",
    "global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css",
    "global/plugins/datatables/datatables.min.css",
    "global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css",
    "global/plugins/jstree/dist/themes/default/style.min.css",


    <#-- BEGIN THEME GLOBAL STYLES -->
    "global/css/components-rounded.min.css",
    "global/css/plugins.min.css",

    "pages/css/error.min.css",

    <#-- BEGIN THEME LAYOUT STYLES -->
    "layouts/layout2/css/layout.min.css",
    "layouts/layout2/css/themes/blue.min.css",
    "layouts/layout2/css/custom.min.css"


    />
    <@importResource
    "plugins/treegrid/css/jquery.treegrid.css",
    "css/override.css",
    "css/style.css"
    />
</#macro>

<#-- 此处是公用的引入-->
<#macro importRequest()>
<!--[if lt IE 9]>
    <@importOSSResource
    "global/plugins/respond.min.js",
    "global/plugins/excanvas.min.js",
    "global/plugins/ie8.fix.min.js",
    "global/plugins/jquery.min.js"
    />
<![endif]-->
    <@importOSSResource
    "global/plugins/jquery.min.js",
    "global/plugins/bootstrap/js/bootstrap.min.js",
    "global/plugins/js.cookie.min.js",
    "global/plugins/jquery-slimscroll/jquery.slimscroll.min.js",
    "global/plugins/jquery.blockui.min.js",
    "global/plugins/bootstrap-switch/js/bootstrap-switch.min.js",

    'global/plugins/pace/pace.min.js',

    "global/plugins/bootstrap-modal/js/bootstrap-modalmanager.js",
    "global/plugins/bootstrap-modal/js/bootstrap-modal.js",
    "global/plugins/bootstrap-sweetalert/sweetalert.min.js",

    "global/plugins/jquery-validation/js/jquery.validate.min.js",
    "global/plugins/jquery-validation/js/additional-methods.min.js",
    "global/plugins/jquery-validation/js/localization/messages_zh.min.js",



    "global/scripts/datatable.js",
    "global/plugins/datatables/datatables.min.js",
    "global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js",

    "global/plugins/bootstrap-summernote/summernote.min.js",
    "global/plugins/bootstrap-typeahead/bootstrap3-typeahead.min.js",
    "global/plugins/moment.min.js",
    "global/plugins/bootstrap-daterangepicker/daterangepicker.min.js",
    "global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js",
    "global/plugins/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js",
    "global/plugins/bootstrap-timepicker/js/bootstrap-timepicker.min.js",
    "global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js",


    "global/plugins/select2/js/select2.full.min.js",


    "global/plugins/jstree/dist/jstree.min.js",

    "global/scripts/app.js",
    "layouts/layout2/scripts/layout.js",
    "layouts/layout2/scripts/demo.js",
    "layouts/global/scripts/quick-sidebar.min.js",
    "layouts/global/scripts/quick-nav.min.js"/>

    <@importResource
    "js/jquery/jquery.form.js"

    "plugins/treegrid/js/jquery.treegrid.js",
    "plugins/treegrid/js/jquery.treegrid.bootstrap3.js",

    "js/framework/framework.common.js",
    "js/framework/framework.delegate.js",
    "js/framework/framework.component.js",
    "js/framework/framework.ext.js",
    "js/framework/framework.validate.js"
    />
<script type="text/javascript">basePath = "<@basePath/>";</script>
</#macro>

<#--资源文件-->
<#macro resources(paths...)>
    <#list paths as path>
        <#if path?ends_with("js")>
        <script src="<@basePath/>${path}" type="text/javascript"></script>
        </#if>
        <#if path?ends_with('css')>
        <link href="<@basePath/>${path}" rel="stylesheet" type="text/css"/>
        </#if>
    </#list>
</#macro>
<#macro img(path,class)>
<img src="<@basePath/>static/img/${path}" class="${class}"/>
</#macro>

<#macro pluginUrl(uri)><@basePath/>ui/plugins/${uri}</#macro>
<#macro api(uri)><@basePath/>${uri}</#macro>

<#macro breadcrumbs(breadcrumbs)>
<div class="page-bar">
    <ul class="page-breadcrumb">
        <#list breadcrumbs as breadcrumb>
            <#if breadcrumb_has_next>
                <li>
                    <i class="${breadcrumb.icon}"></i>
                    <#if breadcrumb.href>
                        <a href="${breadcrumb.href}" class="ajaxify nav-link">${breadcrumb.name}</a>
                    <#else >
                        <a href="javascript:;">${breadcrumb.name}</a>
                    </#if>

                    <i class="fa fa-angle-right"></i>
                </li>
            <#else >
                <li>
                    <i class="${breadcrumb.icon}"></i>
                    <span>${breadcrumb.name}</span>
                </li>
            </#if>
        </#list>
    </ul>
</div>
</#macro>



