/**
 * Created by chenq on 2017/7/10.
 */
var _AJS = _AJS || {};
(function ($, contextPath) {
    _AJS.prototype.Format = function (fmt) { //author: meizz
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
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    };

    $.fn.serializeJson = function () {
        var serializeObj = {};
        var array = this.serializeArray();
        $(array).each(function () {
            if (serializeObj[this.name]) {
                if ($.isArray(serializeObj[this.name])) {
                    serializeObj[this.name].push(this.value);
                } else {
                    serializeObj[this.name] = [serializeObj[this.name], this.value];
                }
            } else {
                serializeObj[this.name] = this.value;
            }
        });
        var select2Array = this.find('input[type=hidden].select2-field');
        $(select2Array).each(function () {
            serializeObj[this.name] = $(this).auiSelect2('val');
        });

        return serializeObj;
    };
    // 拓展AJS的方法
    _AJS.common = {
        ajax: function (option) {
            var option = option || {};
            $.ajax({
                type: option.type || 'get',
                url: !!option.fullUrl ? option.fullUrl : (contextPath + option.url), // 如果有全路径，则直接访问全路径，否则访问相对路径
                dataType: option.dataType || "json",
                contentType: option.contentType || "application/json; charset=utf-8",
                data: option.data || {},
                success: function (data) {
                    !!option.success ? option.success(data) : null;
                },
                error:function (XMLHttpRequest, textStatus, errorThrown) {
                    !!option.success ? option.error(XMLHttpRequest, textStatus, errorThrown) : null;
                }
               /* complete: function (result) {
                    if (!!option.complete) {
                        option.complete(result);
                        return;
                    }
                    if (result.status != 200) {
                        if (result.status == 400) {
                            var error = JSON.parse(result.responseText);
                            if (!!option.error) {
                                option.error(error);
                            }
                            return;
                        }
                        AJS.common.showError("operation failed")
                    }
                }*/
            })
        },
        constant: {
            language: AJS.I18n.getText('shdsd.fine.timesheet.common.language'),
            DATE_FORMAT:"YYYY-MM-DD",
            PLUGIN_URL:contextPath + '/rest/shdsd-fineTimesheet/latest/',
        },
        showInfo: function (options) {
            options = options || {};
            var myFlag = AJS.flag({
                type: options.type || 'info',
                title: options.title || 'title'
            });
            setTimeout(function () {
                myFlag.close()
            }, options.timeout || 2000);
        },
        showSuccess: function (msg) {
            var myFlag = AJS.flag({
                type: 'success',
                title: msg
            });
            setTimeout(function () {
                myFlag.close()
            }, 2000);
        },
        showNError: function (msg) {
            var myFlag = AJS.flag({
                type: 'error',
                title: msg
            });
            setTimeout(function () {
                myFlag.close()
            }, 2000);
        },
        showError: function (option) {
            option = option || {};
            if (!option instanceof Object) {
                option.title = option;
            }

            this.showInfo($.extend(option, {type: 'error', title: option.title || 'title'}));
        }
    };
    // 字符串替换全部
    _AJS.replace = function (str, a, b) {
        str += '';
        return str.replace(new RegExp(a, 'g'), b || '');
    };
// 获取URL参数
    _AJS.urlp = function (key) {
        var qstring = window.location.search;
        if (!qstring) return (!key || key == '*') ? {} : null;
        qstring = qstring.replace(/%20/g, ' ');
        qstring = qstring.substr(1); // remove ?
        var param = qstring.split('&');
        var map = new Object();
        for (var i = 0, j = param.length; i < j; i++) {
            var pl = param[i].split('=');
            map[pl[0]] = pl[1];
        }
        return (!key || key == '*') ? map : map[key];
    };
// 转义html、xml
    _AJS.escape = function (val) {
        if (!!!val) return '';
        val += '';
        val = _AJS.replace(val, '&', '&amp;');
        val = _AJS.replace(val, '"', '&quot;');
        val = _AJS.replace(val, '<', '&lt;');
        val = _AJS.replace(val, '>', '&gt;');
        return val;
    };
// 反转义html、xml
    _AJS.unescape = function (val) {
        if (!!!val) return '';
        val += '';
        val = _AJS.replace(val, '&lt;', '<');
        val = _AJS.replace(val, '&gt;', '>');
        val = _AJS.replace(val, '&quot;', '"');
        val = _AJS.replace(val, '&amp;', '&');
        return val;
    };
    _AJS.getSelectDays = function(statr, end) {
        var start = statr || $('#startDate').text();
        var end = end || $('#endDate').text();
        var startM = moment(start);
        var endM = moment(end);
        var dateArray = [];
        for (var thisDay = startM, i = 0; !thisDay.isAfter(endM); thisDay.add(1, 'days'), i++) {
            dateArray.push(thisDay.clone());
            if (i > 31) {
                break;
            }
        }

        return dateArray;
    };
    _AJS.differDays = function (start, end, nonworkingDays) {
        var valid = moment(start).isBefore(end) || moment(start).isSame(end);
        nonworkingDays = nonworkingDays || 8;
        if (valid) {
            var days = 1;
            var add = moment(start);
            while (add.isBefore(end)) {
                add.add(1, 'day');
                if (nonworkingDays.indexOf(add.day()) == -1) {//是否是休息日
                    days++;
                }
            }
            return days;
        } else {
            return -1;
        }
    }
})(AJS.$, AJS.contextPath());