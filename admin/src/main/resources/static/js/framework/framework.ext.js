/**
 * jquery 插件整合
 * @since 2017年02月08日
 * @author Han.Sun
 */

/**
 * 整合dataTables
 */
(function ($) {
    $.fn.beeTables = function (params) {
        var table = $(this);
        var columns = [];
        var columnDefs = [];
        var key = table.data("key");
        if (typeof key === 'undefined' || key === '') {
            key = "id";
        }

        table.find("thead tr th").each(function (i) {
            var field = $(this).data("field");
            var render = $(this).data("render");
            var format = $(this).data("format");
            var dict = $(this).data("dict");
            var dictOther = $(this).data("dict-other");
            var className = $(this).data("class-name");
            if (field) {
                var column = {orderable: false, searchable: false};
                column.data = field.toString();
                column.defaultContent = '';
                if (render) {
                    column.render = function (data, type, row) {
                        return eval(render + "(data, type, row)");
                    };
                }
                if (format) {
                    column.render = function (data, type, row) {
                        if (!data) {
                            return "";
                        }
                        var date = new Date(data);

                        return date.Format(format)
                    };
                }
                if (dict) {
                    column.render = function (data, type, row) {
                        var res = "";
                        $.get("sys/dict/type/" + dict + "/" + data, {}, function (result) {
                            res = result;
                        });
                        return res;
                    };
                }
                if (dictOther) {
                    column.render = function (data, type, row) {
                        var res = "";
                        $.get("sys/dict/other/" + dictOther + "/" + data, {}, function (result) {
                            res = result;
                        });
                        return res;
                    };
                }
                columns.push(column)

            }
            var columnDef = {};
            columnDef.targets = i;
            if (className) {
                columnDef.className = className;
            }
            if (render) {
                columnDef.render = function (data, type, row) {
                    return eval(render + "(data, type, row)");
                };
            }
            columnDefs.push(columnDef)

        });
        return table.dataTable({
            "dom": "r<'table-scrollable't><'row'<'col-md-5 col-sm-5'li><'col-md-7 col-sm-7'p>>",
            "searching": false,
            "ordering": false,
            "pagingType": "bootstrap_full_number",
            "autoWidth": false,
            "serverSide": true,
            "ajax": {
                "url": table.data("url"),
                "type": "post",
                "data": function (data) {
                    var param = {};
                    param.start = data.start;
                    param.length = data.length;

                    return $('#searchForm').serializeObject(param);
                },
                // datatable 出现错误，修改默认
                "error": function (xhr, textStatus, error) {
                    if (xhr.status === 403){
                        swal({
                            title: "您无权访问此数据列表，请联系管理员",
                            type: "warning"
                        });
                        return;
                    }
                    swal({
                        title: "访问数据列表出错，请联系管理员",
                        type: "warning"
                    })
                }
            },
            "createdRow": function (row, data) {
                if (table.data("check") || typeof  table.data("check") === 'undefined') {
                    $('td:eq(0)', row).html('<label class="mt-checkbox mt-checkbox-single mt-checkbox-outline">'
                        + '<input type="checkbox" class="checkboxes" name="chx_default" value="' + data[key] + '"/>'
                        + '<span></span>'
                        + '</label>');
                }

            },
            "columns": columns,
            "columnDefs": columnDefs,
            "drawCallback": function () {
                var drawCallback = table.data("drawCallback");
                if (drawCallback) {
                    if (drawCallback.indexOf(')') !== -1) {
                        eval(drawCallback);
                    } else {
                        eval(drawCallback + "()");
                    }

                }
            },
            "initComplete": function () {
                var initComplete = table.data("initComplete");
                if (initComplete) {
                    if (initComplete.indexOf(')') !== -1) {
                        eval(initComplete);
                    } else {
                        eval(initComplete + "()");
                    }

                }
                table.find('.group-checkable').change(function () {
                    var set = jQuery(this).attr("data-set");
                    var checked = jQuery(this).is(":checked");
                    jQuery(set).each(function () {
                        if (checked) {
                            $(this).prop("checked", true);
                            $(this).parents('tr').addClass("active");
                        } else {
                            $(this).prop("checked", false);
                            $(this).parents('tr').removeClass("active");
                        }
                    });
                });

                table.on('change', 'tbody tr .checkboxes', function () {
                    $(this).parents('tr').toggleClass("active");
                });
            },
            "language": {
                "emptyTable": "表中无指定条件数据存在！",
                "info": "显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
                "infoEmpty": "显示0到0条记录",
                "infoFiltered": "(数据表中共为 _MAX_ 条记录)",
                "lengthMenu": "_MENU_",
                "search": "搜索:",
                "zeroRecords": "表中无指定条件数据存在！",
                "paginate": {
                    "previous": "上一页",
                    "next": "下一页",
                    "last": "末页",
                    "first": "首页"
                }
            }

        });
    };
    $.fn.serializeObject = function (para) {
        var serializeObj = {}; // 目标对象
        var array = this.serializeArray(); // 转换数组格式
        if (para !== null && para !== undefined) {
            $.each(para, function (name, value) {
                array.push({"name": name, "value": value});
            });
        }
        $(array).each(function () { // 遍历数组的每个元素 {name : xx , value : xxx}
            if (serializeObj[this.name]) { // 判断对象中是否已经存在 name，如果存在name
                if ($.isArray(serializeObj[this.name])) {
                    serializeObj[this.name].push(this.value); // 追加一个值
                } else {
                    serializeObj[this.name] = [serializeObj[this.name], this.value];
                }
            } else {
                serializeObj[this.name] = this.value; // 如果元素name不存在，添加一个属性 name:value
            }
        });
        return serializeObj;
    };

    $.fn.toJSON = function () {
        var $elements = {};
        var $form = $(this);
        $form.find('input, select, textarea').each(function () {
            var name = $(this).attr('name');
            var type = $(this).attr('type');
            if (name) {
                var $value;
                if (type === 'radio') {
                    $value = $('input[name=' + name + ']:checked', $form).val()
                } else if (type === 'checkbox') {
                    $value = $(this).is(':checked')
                } else {
                    $value = $(this).val()
                }
                $elements[$(this).attr('name')] = $value
            }
        });
        return JSON.stringify($elements)
    };
    $.fn.beeForm = function (remoteUrl, callback) {
        var $form = $(this);
        $.get(remoteUrl, {}, function (data) {
            $.each(data.data, function (key, value) {
                var $ctrls = $form.find('[name=' + key + ']');
                if ($ctrls.is('select')) {
                    $('option', $ctrls).each(function () {
                        if (this.value === value)
                            this.selected = true;
                    });
                    $ctrls.trigger("change");
                } else if ($ctrls.is('textarea')) {
                    $ctrls.val(value);
                } else if ($ctrls.is("div[data-type='rich']")) {
                    if ($ctrls.length >= 1) {
                        $.each($ctrls, function () {
                            if ($(this).attr("type") !== "hidden") {
                                $(this).summernote('code', htmlDecode(value));
                            }
                        });
                    }
                } else {
                    switch ($ctrls.attr("type"))   //input type
                    {
                        case "text":
                        case "hidden":
                            if ($ctrls.hasClass("datepicker")) {
                                $ctrls.val(new Date(value).Format("yyyy-MM-dd"));
                                break;
                            }

                            var id = $ctrls.attr('id');
                            if ($ctrls.parents("form").find("input[data-target-id='" + id + "']")) {
                                var $Auto = $ctrls.parents("form").find("input[data-target-id='" + id + "']");
                                var res = "";
                                if (!$Auto.data("url")) {
                                    $ctrls.val(value);
                                    break;
                                }
                                var url = $Auto.data("url").replace("auto", "type");
                                $.get(url + "/" + value, {}, function (result) {
                                    res = result;
                                });
                                $Auto.val(res);
                                break;
                            }
                            $ctrls.val(value);
                            break;
                        case "radio":
                            if ($ctrls.length >= 1) {
                                $.each($ctrls, function () {
                                    var elemValue = $(this).attr("value");
                                    if (elemValue === value) {
                                        $(this).prop('checked', true);
                                    } else {
                                        $(this).prop('checked', false);
                                    }
                                });
                            }
                            break;
                        case "checkbox":
                            if ($ctrls.length > 1) {
                                $.each($ctrls, function () {
                                    if (typeof value === 'undefined' || value === null) {
                                        return;
                                    }
                                    var elemValue = $(this).attr("value");
                                    var elemValueInData = undefined;
                                    var singleVal;
                                    var values = value.toString().split(',');
                                    for (var i = 0; i < values.length; i++) {
                                        singleVal = values[i];
                                        if (singleVal === elemValue) {
                                            elemValueInData = singleVal
                                        }
                                    }
                                    if (elemValueInData) {
                                        $(this).prop('checked', true);
                                    } else {
                                        $(this).prop('checked', false);
                                    }
                                });
                            } else if ($ctrls.length === 1) {
                                if (value) {
                                    $ctrls.prop('checked', true);
                                }
                                else {
                                    $ctrls.prop('checked', false);
                                }
                            }
                            break;
                    }
                }
            });

            if (callback) {
                callback(data);
            }
        });
    };


    /**
     * autocomplete
     * data-url:数据源get地址(返回[{id:xx,text:xx}] 类型数据)
     * data-target-id:保存autocomplete选中Id的input的id属性
     */
    $.fn.beeTypeAhead = function () {
        var el = $(this);
        var url = el.data("url");
        var targetId = el.data("target-id");
        el.typeahead({
            source: function (query, process) {
                $.get(url + "?text=" + query, function (data) {
                    process(data);
                });
            },
            displayText: function (item) {
                return item.text;
            },
            afterSelect: function (item) {
                el.parents("form").find("#" + targetId).val(item.id);
                return item;
            }
        });
    };

    /**
     * select2 全局插件。
     * Server Return Class List<KeyValue>
     */
    $.fn.beeSelect2 = function () {
        var el = $(this);
        var url = $(this).data("url");
        if (url !== undefined) {
            $.get(url, {}, function (data) {
                el.select2({
                    data: data,
                    placeholder: {
                        id: '-1', // the value of the option
                        text: '请选择..'
                    }
                });
            });
        } else {
            el.select2({
                placeholder: {
                    id: '-1', // the value of the option
                    text: '请选择..'
                }
            });
        }

    };

    /**
     * radio remote  全局插件。
     * Server Return Class List<KeyValue>
     */
    $.fn.beeCheck = function () {
        $(this).each(function (i, el) {
            var url = $(el).data("url");
            var name = $(el).data("name");
            var type = "checkbox";
            if (url !== undefined) {
                $.get(url, {}, function (data) {
                    $.each(data, function (i, item) {
                        var option = '<label class="mt-checkbox">' +
                            '<input type="' + type + '" id="' + name + '_' + item.id + '" name="' + name + '" value="' + item.id + '">' +
                            item.text +
                            '<span></span>' +
                            '</label>';
                        $(el).append(option);
                    })
                });
            }
        });

    };

    $.fn.beeRadio = function () {
        $(this).each(function (i, el) {
            var url = $(el).data("url");
            var name = $(el).data("name");
            var type = "radio";
            if (url !== undefined) {
                $.get(url, {}, function (data) {
                    $.each(data, function (i, item) {
                        var option = '<label class="mt-radio">' +
                            '<input type="' + type + '" name="' + name + '" value="' + item.id + '">' +
                            item.text +
                            '<span></span>' +
                            '</label>';
                        $(el).append(option);
                    })
                });
            }
        });

    };
    $.fn.beeSearchableMulti = function () {
        var el = $(this);
        el.multiSelect({
            selectableHeader: "<input type='text' class='search-input' autocomplete='off' placeholder=''>",
            selectionHeader: "<input type='text' class='search-input' autocomplete='off' placeholder=''>",
            afterInit: function (ms) {
                var that = this,
                    $selectableSearch = that.$selectableUl.prev(),
                    $selectionSearch = that.$selectionUl.prev(),
                    selectableSearchString = '#' + that.$container.attr('id') + ' .ms-elem-selectable:not(.ms-selected)',
                    selectionSearchString = '#' + that.$container.attr('id') + ' .ms-elem-selection.ms-selected';

                that.qs1 = $selectableSearch.quicksearch(selectableSearchString)
                    .on('keydown', function (e) {
                        if (e.which === 40) {
                            that.$selectableUl.focus();
                            return false;
                        }
                    });

                that.qs2 = $selectionSearch.quicksearch(selectionSearchString)
                    .on('keydown', function (e) {
                        if (e.which === 40) {
                            that.$selectionUl.focus();
                            return false;
                        }
                    });
            },
            afterSelect: function () {
                this.qs1.cache();
                this.qs2.cache();
            },
            afterDeselect: function () {
                this.qs1.cache();
                this.qs2.cache();
            }
        });
    };


    /**
     * 以指定的Json数据，初始化JStree控件
     * treeName 树div Id
     * url 数据源地址
     * checkbox 是否显示复选框
     * loadedFunction 加载完毕的回调函数
     */

    $.fn.beeJsTree = function () {

        var el = $(this);
        var url = el.data("url");
        var isCheck = el.data("check") || false; //设置checkbox默认值为false
        if (isCheck) {
            //复选框树的初始化
            $.getJSON(url, function (data) {
                console.log(data);
                el.jstree({
                    'plugins': ["checkbox"], //出现选择框
                    'checkbox': {cascade: "", three_state: false}, //不级联
                    "types": {
                        "default": {
                            "icon": "fa fa-file-o"
                        },
                        "file": {
                            "icon": "fa fa-file-o"
                        }
                    },
                    'core': {
                        'data': data,
                        "themes": {
                            "responsive": false
                        }
                    }
                }).bind('loaded.jstree', loadedFunction(data));
            });
        } else {
            //普通树列表的初始化
            $.getJSON(url, function (data) {
                console.log(data);
                el.jstree({
                    'core': {
                        'data': data,
                        "types": {
                            "default": {
                                "icon": "fa fa-file-o"
                            },
                            "file": {
                                "icon": "fa fa-file-o"
                            }
                        },
                        "themes": {
                            "responsive": false
                        }
                    }
                }).bind('loaded.jstree', loadedFunction(data));
            });
        }

        function loadedFunction(data) {
            var callback = el.data("callback");
            if (callback) {
                if (callback.indexOf(')') !== -1) {
                    eval(callback(data));
                } else {
                    eval(callback + "(data)");
                }

            }
        }
    }


    /**
     * 对Date的扩展，将 Date 转化为指定格式的String
     * 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
     * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
     * 例子：
     * (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
     * (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
     */
    Date.prototype.Format = function (fmt) {
        var o = {
            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "h+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    };


    /**
     * 是否为空(不为空返回null)
     *
     * @returns
     */
    String.prototype.isNotBlank = function () {
        var blank = /^\s*$/;
        return (!blank.test(this));
    };
    /**
     * 字符串长度是否符合要求
     *
     * @returns
     */
    String.prototype.isLess = function (minNum) {
        return (this.length < minNum);
    };
    String.prototype.isMore = function (maxNum) {
        return (this.length > maxNum);
    };


    String.prototype.isBetween = function (min, max) {

        return (this.isNotBlank() && (!this.isLess(min)) && (!this.isMore(max)));
    };

    /**
     * 是否有效的手机号码
     *
     * @returns
     */
    String.prototype.isMobileNum = function () {
        return (new RegExp(/^((13[0-9])|(14[4,7])|(15[^4,\D])|(17[6-8])|(18[0-9]))(\d{8})$/)
            .test(this));
    };
    /**
     * 是否是整数倍
     *
     * @returns
     */
    String.prototype.isMultiple = function (num) {
        return ((this.isBlank()) && (this % num === 0));
    };

    String.prototype.isPositInt = function () {
        var res = /^[1-9]+[0-9]*]*$/;
        var vl = this;
        return res.test(vl);
    };

    String.prototype.isNumLess = function (min) {
        if (!this.isNotBlank()) {
            return false;
        }
        var fl = parseFloat(this);
        return fl < min;

    };
    String.prototype.isNumMore = function (max) {
        if (!this.isNotBlank()) {
            return false;
        }
        var fl = parseFloat(this);
        return fl > max;

    };

    /** 字符串是否是属于该区间的数字【包含两个区间】 */
    String.prototype.isNumBetween = function (min, max) {
        return (this.isNotBlank() && (!this.isNumLess(min)) && (!this.isNumMore(max)))
    };

    /** 一位小数或者非负整数 */
    String.prototype.isFloat = function () {
        var res = new RegExp(/^(\d+\.\d{1,1}|\d+)$/);
        return res.test(this);
    };
    /**
     * 是否为汉字
     * @returns
     */
    String.prototype.isChinese = function () {
        return (new RegExp("[\\u4E00-\\u9FFF]+", "g")
            .test(this));
    };
    /**
     * 是否有效的邮箱
     *
     * @returns
     */
    String.prototype.isEmail = function () {
        return (
            new RegExp(/^([a-zA-Z0-9])+([a-zA-Z0-9_.-])+@([a-zA-Z0-9_-])+((\.([a-zA-Z0-9_-]){2,3}){1,2})$/).test(this)
        );
    };
    /**
     * 是否是QQ邮箱
     */
    String.prototype.isQQEmail = function () {
        return new RegExp(/^([\s\S]*@qq.com)$/).test(this);
    };

    String.prototype.isQQ = function () {
        return new RegExp(/^\d{6,10}$/).test(this);
    };

    /**
     * 判断元素值是是否是日期类型
     */
    String.prototype.isDate = function () {
        return (new RegExp(
            /^([1-2]\d{3})[\/|\-](0?[1-9]|10|11|12)[\/|\-]([1-2]?[0-9]|0[1-9]|30|31)$/ig)
            .test(this));
    }


}(jQuery));