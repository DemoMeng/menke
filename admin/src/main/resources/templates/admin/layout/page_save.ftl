<#import "global.ftl" as global />
<#setting url_escaping_charset='utf-8'>
<#setting classic_compatible=true>

<#macro pageContent showBreadcrumbs = true >
    <#nested />
</#macro>

<#macro javascript>
<script type="text/javascript">

</script>
    <#nested />
</#macro>
