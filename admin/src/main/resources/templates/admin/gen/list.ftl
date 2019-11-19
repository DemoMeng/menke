<#include "../layout/page_list.ftl"/>
<@pageContent>
    <@global.importOSSResource 'global/plugins/bootstrap-select/css/bootstrap-select.min.css'/>
<style>
    table thead tr th, table tbody tr td {
        text-align: center;
    }

    .select2-container--bootstrap {
        display: inline-block !important;
    }

    option {
        text-align: center;
    }

    .table-scrollable .mt-checkbox {
        margin-bottom: 15px !important;
    }
</style>
<div class="row">
    <div class="col-md-12">
        <div style="float:left;width: 250px;margin-right: 20px;">
            <div class="portlet light ">
                <div class="portlet-title">
                    <div class="caption">
                        <i class="icon-social-dribbble font-blue-sharp"></i>
                        <span class="caption-subject font-blue-sharp bold uppercase">table</span>
                    </div>
                    <div class="actions">
                    </div>
                </div>
                <div class="portlet-body">
                    <ul class="ver-inline-menu tabbable margin-bottom-10">
                    </ul>
                </div>
            </div>
        </div>
        <div style="overflow: hidden;">
            <div class="portlet light table-info" style="display:none">
                <div class="portlet-body form">
                    <form class="form-horizontal" id="gen_table">

                        <div class="form-body">
                            <h3 class="form-section"><span class="form-control-static entityName"></span>表结构配置</h3>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="col-md-3 control-label">输出目录</label>
                                        <div class="col-md-9">
                                            <input type="text" class="form-control" placeholder="请输入输出目录"
                                                   name="outputDir">
                                            <input type="hidden" name="id">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="col-md-3 control-label">开发人员</label>
                                        <div class="col-md-9">
                                            <input type="text" class="form-control" placeholder="请输入开发人员"
                                                   name="author">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="col-md-3 control-label">父包名</label>
                                        <div class="col-md-9">
                                            <input type="text" class="form-control" placeholder="请输入父包名"
                                                   name="parentModuleName">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="col-md-3 control-label">父包模块名</label>
                                        <div class="col-md-9">
                                            <input type="text" class="form-control" placeholder="请输入父包模块名"
                                                   name="moduleName">
                                        </div>
                                    </div>

                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="col-md-3 control-label">前缀</label>
                                        <div class="col-md-9">
                                            <input type="text" class="form-control" placeholder="前缀"
                                                   name="prefix">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="col-md-3 control-label">是否覆盖</label>
                                        <div class="col-md-9">
                                            <div class="mt-radio-inline">
                                                <label class="mt-radio">
                                                    <input type="radio" name="fileOverride" id="fileOverride_1"
                                                           value="1">是
                                                    <span></span>
                                                </label>
                                                <label class="mt-radio">
                                                    <input type="radio" name="fileOverride" id="fileOverride_0"
                                                           value="0"
                                                           checked="checked"> 否
                                                    <span></span>
                                                </label>

                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <h3 class="form-section">初始化菜单配置</h3>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="col-md-3 control-label">菜单名称</label>
                                        <div class="col-md-9">
                                            <input type="text" class="form-control" placeholder="请输入菜单名称"
                                                   name="menuName">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="col-md-3 control-label">菜单Icon</label>
                                        <div class="col-md-9">
                                            <select name="menuIcon" class="bs-select form-control" data-live-search="true"
                                                    data-size="4"
                                                    data-show-subtext="true">
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <h3 class="form-section">表字段配置</h3>
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="table-scrollable">
                                        <table class="table table-striped table-bordered table-hover"
                                               id="bee_table">
                                            <thead>
                                            <tr>
                                                <th>SqlName</th>
                                                <th>JavaName</th>
                                                <th>字段label</th>
                                                <th>必填</th>
                                                <th>保存</th>
                                                <th>列表</th>
                                                <th>查询</th>
                                                <th>查询匹配方式</th>
                                                <th>显示表单类型</th>
                                                <th>字典类型</th>
                                                <th>排序</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-actions">
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="row">
                                        <div class="col-md-offset-3 col-md-9">
                                            <button type="submit" class="btn green">保存</button>
                                            <button type="button" class="btn red generator">生成</button>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6"></div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<form id="searchForm" style="display: none;">
    <input type="text" name="appKey">
</form>
</@pageContent>
<@javascript>
    <@global.importOSSResource 'global/plugins/bootstrap-select/js/bootstrap-select.min.js'/>
<script type="text/javascript">
    _result = {};

    _activeTable = {};

    _activeField = [];

    $.get("gen/table/info", {}, function (result) {
        _result = result;
    });

    initTableList(_result.list);

    $(".generator").on("click", function () {
        // 先执行保存操作
        $('#gen_table').submit();

        $.post("gen/table/generator", {genTableId: _activeTable.genTable.id}, function () {
            swal({
                title: "操作结果提示",
                text: "生成成功!",
                type: "success",
                timer: 100000
            });
        });


    });

    $(".ver-inline-menu li").on('click', function () {
        var tableName = $(this).find('a').data("key");
        _activeTable = _result.tableInfoMap[tableName];
        loadData();
    });

    // 加载图标
    $.getJSON("json/simple-line-icons.json", function (data) {
        $("select[name='menuIcon']").html("");
        $.each(data, function (i, n) {
            $("select[name='menuIcon']").append('<option value="' + n + '" data-icon="' + n + '">' + n + '</option>')

        });
        $("select[name='menuIcon']").selectpicker({
            iconBase: 'fa',
            tickIcon: 'fa-check'
        });
    });

    // 保存信息
    $('#gen_table').on('submit', function () {

        if ($("input[name='parentModuleName']").val() === '') {
            $("input[name='parentModuleName']").css("border-color", "red");
            swal({
                title: "请输入父包名",
                type: "warning",
                timer: 100000
            });
            return false;
        } else {
            $("input[name='parentModuleName']").css("border-color", "#c2cad8");
        }

        if ($("input[name='moduleName']").val() === '') {
            $("input[name='moduleName']").css("border-color", "red");
            swal({
                title: "请输入父包模块名",
                type: "warning",
                timer: 100000
            });
            return false;
        } else {
            $("input[name='moduleName']").css("border-color", "#c2cad8");
        }

        $(this).find("input[type='checkbox']").each(function (i, n) {
            if ($(n).prop("checked")) {
                $(n).val("1");
            } else {
                $(n).val("0");
            }
        });
        $(this).ajaxSubmit({
            type: 'put',
            url: 'gen/table/save',
            success: function () {
                swal({
                    title: "操作结果提示",
                    text: "保存成功!",
                    type: "success",
                    timer: 100000
                });
            }
        });

        return false; // 阻止表单自动提交事件
    });

    /**
     * 粗暴一时爽啊
     */
    function loadData() {

        $("#gen_table").find(".entityName").html(_activeTable.tableInfo.entityName);
        $("#gen_table").find("input[name='id']").val(_activeTable.genTable.id);
        $("#gen_table").find("input[name='outputDir']").val(_activeTable.genTable.outputDir);
        $("#gen_table").find("input[name='author']").val(_activeTable.genTable.author);
        $("#gen_table").find("input[name='parentModuleName']").val(_activeTable.genTable.parentModuleName);
        $("#gen_table").find("input[name='moduleName']").val(_activeTable.genTable.moduleName);
        $("#gen_table").find("input[name='menuName']").val(_activeTable.genTable.menuName);
        $("#gen_table").find("input[name='prefix']").val(_activeTable.genTable.prefix);
        $("#gen_table").find('input:radio[name="fileOverride"]').filter('[value="1"]').attr('checked', _activeTable.genTable.fileOverride);
        $("#gen_table").find('input:radio[name="fileOverride"]').filter('[value="0"]').attr('checked', !_activeTable.genTable.fileOverride);

        $('option', $("#gen_table").find("select[name='menuIcon']")).each(function () {
            if (this.value == _activeTable.genTable.menuIcon)
                this.selected = true;
        });

        $("#gen_table").find("select[name='menuIcon']").trigger("change");
        $('#bee_table tbody').html("");

        $.each(_activeTable.genTableFieldList, function (index, genTableInfo) {
            $('#bee_table tbody').append(initTr(index, genTableInfo));
        });

        $("select[name!='menuIcon']").select2({'width': '100px'});

        $(".table-info").css("display", "block");
    }
    function initTr(index, genTableInfo) {
        var dictType = "";
        if (genTableInfo.genTableField.dictType) {
            dictType = genTableInfo.genTableField.dictType
        }
        var sort = "";
        if (genTableInfo.genTableField.sort) {
            dictType = genTableInfo.genTableField.sort
        }

        var tr = '<tr>' +
                '<td style="padding-top: 15px;"><input name="genTableFieldList[' + index + '].id" value="' + genTableInfo.genTableField.id + '" type="hidden"/>' + genTableInfo.tableField.name + '</td>' +
                '<td style="padding-top: 15px;">' + genTableInfo.tableField.propertyName + '</td>' +
                '<td ><input type="text" name="genTableFieldList[' + index + '].label" value="' + genTableInfo.genTableField.label + '"   style="width: 150px;display: inherit;" class="form-control"/></td>' +
                '<td style="padding-top: 15px;"><label class="mt-checkbox"><input type="checkbox" name="genTableFieldList[' + index + '].nullable"';
        if (genTableInfo.genTableField.nullable) {
            tr += ' checked = "checked"';
        }
        tr += '/><span></span></label></td>' +
                '<td style="padding-top: 15px;"><label class="mt-checkbox"><input type="checkbox" name="genTableFieldList[' + index + '].savable"';
        if (genTableInfo.genTableField.savable) {
            tr += ' checked = "checked"';
        }
        tr += '/><span></span></label></td>' +
                '<td style="padding-top: 15px;"><label class="mt-checkbox"><input type="checkbox" name="genTableFieldList[' + index + '].listable" ';
        if (genTableInfo.genTableField.listable) {
            tr += ' checked = "checked"';
        }
        tr += '/><span></span></label></td>' +
                '<td style="padding-top: 15px;"><label class="mt-checkbox"><input type="checkbox" name="genTableFieldList[' + index + '].queryable" ';
        if (genTableInfo.genTableField.queryable) {
            tr += ' checked = "checked"';
        }
        tr += '/><span></span></label></td>' +
                '<td ><select name="genTableFieldList[' + index + '].queryType" class="required input-mini">' +
                '<option';
        var queryType = genTableInfo.genTableField.queryType;
        if (queryType === "=") {
            tr += ' selected = "selected"';
        }
        tr += ' value="=" >=</option><option';
        if (queryType === "!=") {
            tr += ' selected = "selected"';
        }

        tr += ' value="!=" >!=</option><option';
        if (queryType === "&gt;") {
            tr += ' selected = "selected"';
        }
        tr += ' value="&gt;" >&gt;</option><option';
        if (queryType === "&gt;=") {
            tr += ' selected = "selected"';
        }
        tr += ' value="&gt;=" >&gt;=</option><option';
        if (queryType === "&lt;") {
            tr += ' selected = "selected"';
        }
        tr += ' value="&lt;" >&lt;</option><option';
        if (queryType === "&lt;=") {
            tr += ' selected = "selected"';
        }
        tr += ' value="&lt;=" >&lt;=</option><option';
        if (queryType === "between") {
            tr += ' selected = "selected"';
        }
        tr += ' value="between" >Between</option><option';
        if (queryType === "like") {
            tr += ' selected = "selected"';
        }
        tr += ' value="like" >Like</option><option';
        if (queryType === "left_like") {
            tr += ' selected = "selected"';
        }
        tr += ' value="left_like" >Left Like</option><option';
        if (queryType === "right_like") {
            tr += ' selected = "selected"';
        }
        tr += ' value="right_like" >Right Like</option></select>' +
                '</td>' +
                '<td>' +
                '<select name="genTableFieldList[' + index + '].showType" class="required">' +
                '<option ';
        var showType = genTableInfo.genTableField.showType;

        if (showType === "1") {
            tr += ' selected = "selected"';
        }
        tr += ' value="1">text</option><option ';
        if (showType === "2") {
            tr += ' selected = "selected"';
        }
        tr += ' value="2">hidden</option><option ';
        if (showType === "3") {
            tr += ' selected = "selected"';
        }
        tr += ' value="3">textarea</option><option ';
        if (showType === "4") {
            tr += ' selected = "selected"';
        }
        tr += ' value="4">select</option><option ';
        if (showType === "5") {
            tr += ' selected = "selected"';
        }
        tr += ' value="5">radio</option><option ';
        if (showType === "6") {
            tr += ' selected = "selected"';
        }
        tr += ' value="6">checkbox</option><option ';
        if (showType === "7") {
            tr += ' selected = "selected"';
        }
        tr += ' value="7">rich</option><option ';
        if (showType === "8") {
            tr += ' selected = "selected"';
        }
        tr += ' value="8">date</option><option ';
        if (showType === "9") {
            tr += ' selected = "selected"';
        }
        tr += ' value="9">autocomplete</option></select></td>' +
                '<td ><input type="text" name="genTableFieldList[' + index + '].dictType" value="' + dictType + '" style="width: 100px;display: inherit;" class="form-control"/></td>' +
                '<td ><input type="text" name="genTableFieldList[' + index + '].sort" value="' + sort + '"   style="width: 50px;display: inherit;" class="form-control"/></td>' +
                '</tr>';
        return tr;
    }

    function initTableList(list) {
        $('.ver-inline-menu').html("");
        $.each(list, function (i, n) {
            var li = '';
            if (i == 0) {
                li = '<li class="active">';
            } else {
                li = '<li>';
            }
            li += '<a data-toggle="tab" href="#"  data-key="' + n.tableInfo.name + '">' +
                    '<i class="icon-grid"></i> ' + n.tableInfo.name + ' </a>';
            if (i == 0) {
                li += '<span class="after"> </span>';
            }

            li += '</li>';
            $('.ver-inline-menu').append(li)

        });
    }
</script>
</@javascript>