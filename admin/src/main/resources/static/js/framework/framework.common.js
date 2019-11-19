/**
 * 通用方法
 * @since 2017年02月08日
 * @author Han.Sun
 */
function newGuid() {
    var guid = "";
    for (var i = 1; i <= 32; i++) {
        var n = Math.floor(Math.random() * 16.0).toString(16);
        guid += n;
    }
    guid += new Date().getTime();
    return guid.toUpperCase();
}

//获得选中项
function getCheckboxItem(checkBoxName) {
    var allSel = "";
    var ids = $("input[name='" + checkBoxName + "']");
    $(ids).each(function () {
        if (this.disabled == false) {
            if (this.checked) {
                if (allSel == "")
                    allSel = this.value;
                else
                    allSel = allSel + "," + this.value;
            }
        }
    });
    return allSel;
}



function htmlEncode(str) {
    if (typeof str == 'undefined') return "";
    if (str.length == 0) return "";
    var s = str.replace(/&/g, "&amp;");
    s = s.replace(/</g, "&lt;");
    s = s.replace(/>/g, "&gt;");
    s = s.replace(/ /g, "&nbsp;");
    s = s.replace(/\'/g, "&#39;");
    s = s.replace(/\"/g, "&quot;");
    s = s.replace(/\n/g, "<br>");
    return s;
}


function htmlDecode(str) {
    if (typeof str == 'undefined') return "";
    if (str.length == 0) return "";
    var s = str.replace(/&amp;/g, "&");
    s = s.replace(/&lt;/g, "<");
    s = s.replace(/&gt;/g, ">");
    s = s.replace(/&nbsp;/g, " ");
    s = s.replace(/&#39;/g, "\'");
    s = s.replace(/&quot;/g, "\"");
    s = s.replace(/<br>/g, "\n");
    return s;
}


function beeConfirm(msg, callback) {
    swal({
            title: msg,
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "确定",
            cancelButtonText: "取消"
        },
        function () {
            if (callback && callback instanceof Function) //判断是否传参，以及参数类型
                callback();
        })
}