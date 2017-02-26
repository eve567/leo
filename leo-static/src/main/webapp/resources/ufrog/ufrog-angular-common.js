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
                    }
                }
            };
            return $this;
        }])
})(window.angular, window.jQuery);