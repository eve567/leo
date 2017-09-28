/*!
 *
 */
(function(ng, jq, undefined) {'use strict';
    /** 模块定义 */
    ng.module('ugRequest', ['ugCommon', 'ugBootstrap'])

        /** 服务定义 */
        .service('$request', ['$common', '$bootstrap', '$http', function($common, $bootstrap, $http) {
            var $_ = {
                $config: {
                    $defaultParams: {_request_type: 'json'}
                },

                /** 配置 */
                config: function(defaultParams) {
                    $_.$config.$defaultParams = defaultParams;
                },

                /** Method.GET */
                get: function(url, data, success, pre, post, error) {
                    var args = $_.$checkArguments(data, success, pre, post, error);
                    $http({
                        method: 'get',
                        url: url,
                        params: ng.extend({}, $_.$config.$defaultParams, args.data)
                    }).then(function(data) {
                        $_.$doSuccess(data, args.success, args.pre, args.post, args.error);
                    }, function(data) {
                        $_.$doError(data, args.error, args.pre, args.post);
                    });
                },

                /** Method.POST */
                post: function(url, data, success, pre, post, error) {
                    var args = $_.$checkArguments(data, success, pre, post, error);
                    $http({
                        method: 'post',
                        url: url + ((url.indexOf('?') >= 0) ? '&' : '?') + jq.param($_.$config.$defaultParams),
                        data: args.data
                    }).then(function(data) {
                        $_.$doSuccess(data, args.success, args.pre, args.post, args.error);
                    }, function(data) {
                        $_.$doError(data, args.error, args.pre, args.post);
                    });
                },

                /** Method.POST urlencoded */
                postUrlencoded: function(url, data, success, pre, post, error) {
                    var args = $_.$checkArguments(data, success, pre, post, error);
                    $http({
                        method: 'post',
                        url: url,
                        data: jq.param(ng.extend({}, $_.$config.$defaultParams, args.data)),
                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
                    }).then(function(data) {
                        $_.$doSuccess(data, args.success, args.pre, args.post, args.error);
                    }, function(data) {
                        $_.$doError(data, args.error, args.pre, args.post);
                    });
                },

                /** Method.PUT */
                put: function(url, data, success, pre, post, error) {
                    var args = $_.$checkArguments(data, success, pre, post, error);
                    $http({
                        method: 'put',
                        url: url + ((url.indexOf('?') >= 0) ? '&' : '?') + jq.param($_.$config.$defaultParams),
                        data: args.data
                    }).then(function(data) {
                        $_.$doSuccess(data, args.success, args.pre, args.post, args.error);
                    }, function(data) {
                        $_.$doError(data, args.error, args.pre, args.post);
                    });
                },

                /** Method.PUT urlencoded */
                putUrlencoded: function(url, data, success, pre, post, error) {
                    var args = $_.$checkArguments(data, success, pre, post, error);
                    $http({
                        method: 'put',
                        url: url,
                        data: jq.param(ng.extend({}, $_.$config.$defaultParams, args.data)),
                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
                    }).then(function(data) {
                        $_.$doSuccess(data, args.success, args.pre, args.post, args.error);
                    }, function(data) {
                        $_.$doError(data, args.error, args.pre, args.post);
                    });
                },

                /** Method.DELETE */
                delete: function(url, data, success, pre, post, error) {
                    var args = $_.$checkArguments(data, success, pre, post, error);
                    $http({
                        method: 'delete',
                        url: url + ((url.indexOf('?') >= 0) ? '&' : '?') + jq.param($_.$config.$defaultParams),
                        data: args.data
                    }).then(function(data) {
                        $_.$doSuccess(data, args.success, args.pre, args.post, args.error);
                    }, function(data) {
                        $_.$doError(data, args.error, args.pre, args.post);
                    });
                },

                /** Method upload */
                upload: function(url, data, success, pre, post, error) {
                    var args = $_.$checkArguments(data, success, pre, post, error);
                    $http({
                        method: 'post',
                        url: url,
                        data: $common.ctrl.formData(ng.extend({}, $_.$config.$defaultParams, args.data)),
                        headers: {'Content-Type': undefined},
                        transformRequest: function(data) {
                            console.log('upload data:', data);
                            return data;
                        }
                    }).then(function(data) {
                        $_.$doSuccess(data, args.success, args.pre, args.post, args.error);
                    }, function(data) {
                        $_.$doError(data, args.error, args.pre, args.post);
                    });
                },

                /** 转换编码 */
                toUnicode: function(str) {
                    if (!$common.valid.empty(str) && $common.valid.str(str)) {
                        var $unicode = '';
                        for (var $i = 0; $i < str.length; $i++) {
                            $unicode = $unicode + '\\u' + ('0000' + str.charCodeAt($i).toString(16)).slice(-4);
                        }
                        return $unicode;
                    }
                    return str;
                },

                /** 逆转换编码 */
                fromUnicode: function(unicode) {
                    if (!$common.valid.empty(unicode) && $common.valid.str(unicode)) {
                        var $str = '', $data = unicode.split('\\u');
                        for (var $i = 0; $i < $data.length; $i++) {
                            $str += String.fromCharCode(parseInt(parseInt($data[$i], 16).toString(10)));
                        }
                        return $str;
                    }
                    return unicode;
                },

                /** 成功处理 */
                $doSuccess: function(data, success, pre, post, error) {
                    (pre || ng.noop)(data.data);
                    if ($bootstrap.alert.check(data.data)) {
                        (success || ng.noop)(data.data);
                    } else {
                        $_.$doError(data, error);
                    }
                    (post || ng.noop)(data.data);
                },

                /** 错误处理 */
                $doError: function(data, error, pre, post) {
                    console.error(data);
                    (pre || ng.noop)(data.data);
                    (error || ng.noop)(data.data);
                    (post || ng.noop)(data.data);
                },

                /** 检查参数 */
                $checkArguments: function(data, success, pre, post, error) {
                    if ($common.valid.fn(data)) {
                        return {data: {}, success: data, pre: success, post: pre, error: post};
                    } else {
                        return {data: data, success: success, pre: pre, post: post, error: error};
                    }
                }
            };
            return $_;
        }])
})(window.angular, window.jQuery);