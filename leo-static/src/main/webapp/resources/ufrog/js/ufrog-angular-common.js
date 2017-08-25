/*!
 *
 */
(function(ng, jq, undefined) {'use strict';
    /** 模块定义 */
    ng.module('ugCommon', [])

        /** 服务定义 */
        .service('$common', ['$window', '$filter', '$timeout', '$http', '$templateCache', function($window, $filter, $timeout, $http, $templateCache) {
            var $_ = {
                /** jQuery */
                $: jq,

                /** 验证工具 */
                valid: {
                    /** 判断数据是否未定义 */
                    undefined: function(data) {
                        return ng.isUndefined(data);
                    },

                    /** 判断数据是否已定义 */
                    defined: function(data) {
                        return ng.isDefined(data);
                    },

                    /** 判断数据是否为对象 */
                    obj: function(data) {
                        return ng.isObject(data);
                    },

                    /** 判断数据是否为字符串 */
                    str: function(data) {
                        return ng.isString(data);
                    },

                    /** 判断数据是否为数字 */
                    num: function(data) {
                        return ng.isNumber(data);
                    },

                    /** 判断数据是否为日期 */
                    date: function(data) {
                        return ng.isDate(data);
                    },

                    /** 判断数据是否为数组 */
                    arr: function(data) {
                        return ng.isArray(data);
                    },

                    /** 判断数据是否为方法 */
                    fn: function(data) {
                        return ng.isFunction(data);
                    },

                    /** 判断数据是否为元素 */
                    elem: function(data) {
                        return ng.isElement(data);
                    },

                    /** 判断数据是否为空 */
                    empty: function(data) {
                        if ($_.valid.undefined(data) || data === null) return true;
                        else if ($_.valid.arr(data) && data.length === 0) return true;
                        else if ($_.valid.str(data) && data === '') return true;
                        return false;
                    },

                    /** 判断数据是否相同 */
                    equals: function(one, another, keys) {
                        if ($_.valid.obj(one) && $_.valid.obj(another)) {
                            var $bool = true;
                            ng.forEach($_.valid.arr(keys) ? keys : [keys], function(k) {
                                if (one[k] !== another[k]) $bool = false;
                            });
                            return $bool;
                        } else {
                            return one === another;
                        }
                    }
                },

                /** 数组工具 */
                array: {
                    /** 判断数据是否在数组中 */
                    in: function(array, data, key) {
                        var $idx = -1;
                        if ($_.valid.defined(data)) {
                            ng.forEach(array, function(val, idx) {
                                if ($_.valid.equals(data, val, key || 'id')) $idx = idx;
                            });
                        }
                        return $idx;
                    },

                    /** 将数据加入数组中 */
                    add: function(array, data, idx) {
                        array = array || [];
                        if ($_.valid.defined(data)) {
                            if ($_.valid.undefined(idx)) {
                                array.push(data);
                            } else {
                                array.splice(idx, 0, data);
                            }
                        }
                        return array;
                    },

                    /** 替换数组中数据 */
                    replace: function(array, data, key) {
                        array = array || [];
                        if ($_.valid.defined(data)) {
                            var $idx = $_.array.in(array, data, key);
                            if ($idx >= 0) {
                                array.splice($idx, 1, data);
                            } else {
                                $_.array.add(array, data);
                            }
                        }
                        return array;
                    },

                    /** 移除数组中数据 */
                    remove: function(array, data, key) {
                        array = array || [];
                        if ($_.valid.defined(data)) {
                            var $idx = $_.array.in(array, data, key);
                            if ($idx >= 0) {
                                array.splice($idx, 1);
                            }
                        }
                        return array;
                    },

                    /** 清空数组 */
                    empty: function(array) {
                        array.length = 0;
                    }
                },

                /** 对象工具 */
                object: {
                    /** 删除对象属性 */
                    delete: function(data, keys) {
                        if (!$_.valid.arr(keys)) keys = [keys];
                        ng.forEach(keys, function(key) {
                            delete(data[key]);
                        });
                        return data;
                    },

                    /** 解析成对象或转换成字符串 */
                    json: function(data) {
                        if ($_.valid.str(data)) {
                            return ng.fromJson(data);
                        } else {
                            return ng.toJSON(data);
                        }
                    }
                },

                /** 日期工具 */
                date: {
                    $mType: {
                        d: 24 * 60 * 60 * 1000,
                        data: this.d,
                        h: 60 * 60 * 1000,
                        hour: this.h,
                        m: 60 * 1000,
                        min: this.m,
                        minuter: this.m,
                        s: 1000,
                        second: this.s
                    },

                    /** 当前时间 */
                    current: function() {
                        return new Date();
                    },

                    /** 检查是否时间数据 */
                    check: function(date) {
                        return $_.valid.date(date) ? date : $_.date.current();
                    },

                    /** 生成时间戳 */
                    timestamp: function(date) {
                        return $_.date.check(date).valueOf();
                    },

                    /** 格式化时间 */
                    format: function(pattern, date) {
                        return $filter('date')($_.date.check(date), pattern || 'yyyy-MM-dd HH:mm:ss');
                    },

                    /** 时间累加 */
                    add: function(qty, type, date, pattern) {
                        var $unit = ($_.date.$mType[type] || $_.date.$mType.d),
                            $date = new Date($_.date.timestamp(date) + qty * $unit);
                        return $_.valid.defined(pattern) ? $_.date.format(pattern, $date) : $date;
                    }
                },

                /** 控制器工具 */
                ctrl: {
                    $mStatus: {},

                    /** 状态设定判断 */
                    status: function(name, status) {
                        if ($_.valid.arr(status)) {
                            return $_.array.in(status, $_.ctrl.$mStatus[name]) >= 0;
                        } else {
                            $_.ctrl.$mStatus[name] = status;
                        }
                    },

                    /** 阻止事件冒泡 */
                    stop: function(event) {
                        event.preventDefault();
                        event.stopPropagation();
                    },

                    /** 页面跳转 */
                    redirect: function(url) {
                        $window.location.href = url;
                    },

                    /** 重置表单 */
                    resetForm: function(form) {
                        ng.forEach(form, function(val, key) {
                            if (!key.startsWith('$')) {
                                val.$dirty = false;
                            }
                        });
                    },

                    /** 表单数据 */
                    formData: function(data) {
                        var $data = new FormData();
                        ng.forEach(data, function(val, idx) {
                            if ($_.valid.arr(val)) {
                                ng.forEach(val, function(v) {
                                    $data.append(idx, v);
                                });
                            } else {
                                $data.append(idx, val);
                            }
                        });
                        return $data;
                    },

                    /** 脚本路径 */
                    scriptPath: function(names) {
                        var $selector = [], $tags;
                        ng.forEach($_.valid.arr(names) ? names : [names], function(n, i) {
                            $selector[i] = 'script[src*="' + n + '"]';
                        });
                        $tags = $_.$($selector.join(','));
                        return ($tags.length > 0) ? $tags.get(0).src.substr(0, $tags.get(0).src.lastIndexOf('/')) : undefined;
                    }
                }
            };
            return $_;
        }])

        /** 图标指令 */
        .directive('fa', function() {
            return {
                restrict: 'E',
                replace: true,
                scope: true,
                transclude: true,
                template: '<span><i class="fa" ng-class="$class()"></i><ng-transclude></ng-transclude></span>',
                link: function($scope, $element, $attrs) {
                    ng.extend($scope, {
                        $class: function() {
                            return 'fa-' + $attrs['i'].split(' ').join(' fa-');
                        }
                    });
                }
            };
        })

        /** 空数据过滤器 */
        .filter('empty', ['$common', function($common) {
            return function(data, defaultValue) {
                return $common.valid.empty(data) ? defaultValue : data;
            };
        }])

        /** 排除数据过滤器 */
        .filter('exclude', ['$common', function($common) {
            return function(data, exclude, key) {
                if ($common.valid.arr(data)) {
                    data = data.concat();
                    ng.forEach($common.valid.arr(exclude) ? exclude : [exclude], function(ex) {
                        $common.array.remove(data, ex, key);
                    });
                }
                return data;
            }
        }]);
})(window.angular, window.jQuery);