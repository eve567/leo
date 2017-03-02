/*!
 *
 */
(function(ng, jq, undefined) {'use strict';
    /** 模块定义 */
    ng.module('ugCommon', [])

        /** 服务定义 */
        .service('$common', ['$window', '$filter', function($window, $filter) {
            var $this = {
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
                        if ($this.valid.undefined(data) || data === null) return true;
                        else if ($this.valid.arr(data) && data.length === 0) return true;
                        else if ($this.valid.str(data) && data === '') return true;
                        return false;
                    },

                    /** 判断数据是否相同 */
                    equals: function(one, another, keys) {
                        if ($this.valid.obj(one) && $this.valid.obj(another)) {
                            var $bool = true;
                            ng.forEach($this.valid.arr(keys) ? keys : [keys], function(k) {
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
                        var $pos = -1;
                        ng.forEach(array, function(val, idx) {
                            if ($this.valid.equals(data, val, key || 'id')) $pos = idx;
                        });
                        return $pos;
                    },

                    /** 将数据加入数组中 */
                    add: function(array, data, idx) {
                        if (!$this.valid.arr(array)) array = [];
                        if ($this.valid.undefined(idx)) {
                            array.push(data);
                        } else {
                            array.splice(idx, 0, data);
                        }
                        return array;
                    },

                    /** 替换数组中数据 */
                    replace: function(array, data, key) {
                        var $idx = $this.array.in(array, data, key);
                        if ($idx >= 0) {
                            array.splice($idx, 1, data);
                        } else {
                            $scope.array.add(array, data);
                        }
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
                        if (!$this.valid.arr(keys)) keys = [keys];
                        ng.forEach(keys, function(key) {
                            delete(data[key]);
                        });
                    },

                    /** 解析成对象或转换成字符串 */
                    json: function(data) {
                        if ($this.valid.str(data)) {
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
                        return $this.valid.date(date) ? date : $this.date.current();
                    },

                    /** 生成时间戳 */
                    timestamp: function(date) {
                        return $this.date.check(date).valueOf();
                    },

                    /** 格式化时间 */
                    format: function(pattern, date) {
                        return $filter('date')($this.date.check(date), pattern || 'yyyy-MM-dd HH:mm:ss');
                    },

                    /** 时间累加 */
                    add: function(qty, type, date, pattern) {
                        var $unit = ($this.date.$mType[type] || $this.date.$mType.d),
                            $date = new Date($this.date.timestamp(date) + qty * $unit);
                        return $this.valid.defined(pattern) ? $this.date.format(pattern, $date) : $date;
                    }
                },

                /** 控制器工具 */
                ctrl: {
                    $mStatus: {},

                    /** 状态设定判断 */
                    status: function(name, status) {
                        if ($this.valid.arr(status)) {
                            return $this.array.in(status, $this.ctrl.$mStatus[name]) >= 0;
                        } else {
                            $this.ctrl.$mStatus[name] = status;
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

                    /** 表单数据 */
                    formData: function(data) {
                        var $data = new FormData();
                        ng.forEach(data, function(val, idx) {
                            if ($this.valid.arr(val)) {
                                ng.forEach(val, function(v) {
                                    $data.append(idx, v);
                                });
                            } else {
                                $data.append(idx, val);
                            }
                        });
                        return $data;
                    }
                }
            };
            return $this;
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
        }]);
})(window.angular, window.jQuery);