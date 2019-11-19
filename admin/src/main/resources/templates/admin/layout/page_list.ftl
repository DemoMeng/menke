<#import "global.ftl" as global />
<#setting url_escaping_charset='utf-8'>
<#setting classic_compatible=true>

<#macro pageContent showBreadcrumbs = true >
    <#if showBreadcrumbs>
        <@global.breadcrumbs breadcrumbs/>
    </#if>
    <#nested />
</#macro>

<#macro javascript>
    <#nested />
</#macro>
