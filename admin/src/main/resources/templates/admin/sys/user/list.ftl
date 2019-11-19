<#include "../../layout/page_list.ftl"/>
<@pageContent>
<div class="row">
    <div class="col-md-12">
        <div class="portlet light">
            <div class="portlet-body form">
                <div class="table-toolbar">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="btn-group">
                                <button id="ajax-demo" class="btn  green" data-url="admin/sys/user/save.html"
                                        data-toggle="modal">新增
                                    <i class="fa fa-plus"></i>
                                </button>
                            </div>
                            <div class="btn-group">
                                <button id="ajax-demo" class="btn red"
                                        data-url="<@global.api 'sys/user/batchDelete'/>" data-batch="true"
                                        data-msg="确定批量删除吗？" data-model="ajaxDelete" data-callback="refreshTable">批量删除
                                    <i class="fa fa-times"></i>
                                </button>
                            </div>

                            <div class="btn-group">
                                <button id="toggleSearch" class="btn default" data-toggle="collapse" href="#searchForm" onclick="toggleSearch()">
                                    打开搜索
                                </button>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="panel search-panel">
                                <form id="searchForm" class="form-horizontal panel-collapse collapse"
                                      aria-expanded="true">
                                    <div class="form-body row">
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <label class="control-label col-md-4">Address 1</label>
                                                <div class="col-md-8">
                                                    <input type="text" class="form-control"></div>
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <label class="control-label col-md-4">Address 1</label>
                                                <div class="col-md-8">
                                                    <input type="text" class="form-control"></div>
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <label class="control-label col-md-4">Address 1</label>
                                                <div class="col-md-8">
                                                    <input type="text" class="form-control"></div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-4">
                                            <div class="col-md-offset-3 col-md-9">
                                                <button type="submit" class="btn green">搜索</button>
                                                <button type="button" class="btn default">清空</button>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <table class="table table-striped table-bordered table-hover table-checkable order-column"
                       id="table_user" data-url="<@global.api 'sys/user/page'/>">
                    <thead>
                    <tr>
                        <th data-field="id">
                            <label class="mt-checkbox mt-checkbox-single mt-checkbox-outline">
                                <input type="checkbox" class="group-checkable" data-set="#table_user .checkboxes"
                                       id="defaultCheck"/>
                                <span></span>
                            </label>
                        </th>
                        <th data-field="loginName">登录名</th>
                        <th data-field="name">用户名</th>
                        <th data-field="phone">手机号码</th>
                        <th data-field="email">邮箱</th>
                        <th data-field="createDate" data-format="yyyy-MM-dd hh:mm:ss">创建时间</th>
                        <th name="action" data-render="rendererAction">操作</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>
</@pageContent>
<@javascript>
<script type="text/javascript">


    var dataTable = $("#table_user").beeTables();

    function toggleSearch() {
        if ($("#searchForm").hasClass("in")){
            $("#toggleSearch").html("打开搜索")
        }else {
            $("#toggleSearch").html("关闭搜索")
        }

    }
    function refreshTable(toFirst) {
        if (toFirst) {
            //表格重绘，并跳转到第一页
            dataTable.fnDraw();
        } else {
            //表格重绘，保持在当前页
            dataTable.fnDraw(false);
        }
    }

    function rendererAction(data, type, row) {
        return '<button data-url="admin/sys/user/save.html?id=' + row.id + '" class="btn btn-xs blue" data-toggle="modal">编辑</button>' +
                '<button data-url="' + basePath + '/sys/user/' + row.id + '" class="btn btn-xs red" data-callback="refreshTable" data-msg="确定删除吗？" data-batch="false" data-model="ajaxDelete">删除</button>';
    }
</script>
</@javascript>