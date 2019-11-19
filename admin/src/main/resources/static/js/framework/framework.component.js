var FrameworkComponent = function () {

    var select2 = function () {
        $('.select2').each(function () {
            $(this).beeSelect2();
        })
    };

    var check = function () {
        $('.mt-checkbox-inline').each(function () {
            $(this).beeCheck();
        })
    };

    var radio = function () {
        $('.mt-radio-inline').each(function () {
            $(this).beeRadio();
        })
    };
    var summernote = function () {
        $('.summernote').each(function () {
            $(this).summernote({height: 200});
        })
    };

    var datepicker = function () {
        $('.datepicker').each(function () {
            $(this).datepicker({
                language: "zh-CN",
                autoclose: true,//选中之后自动隐藏日期选择框
                clearBtn: true,//清除按钮
                todayBtn: true,//今日按钮
                format: "yyyy-mm-dd",
                startDate: '-3d'
            });
        })
    };

    var typeahead = function () {
        $('.typeahead').each(function () {
            $(this).beeTypeAhead();
        })
    };

    return {
        loadFormComponent: function () {
            select2();
            check();
            radio();
            summernote();
            datepicker();
            typeahead();
        }
    };
}();
