/*!
 *
 */
(function(ng, jq, undefined) {'use strict';
    /** 模块定义 */
    ng.module('ugRequest', ['ugCommon', 'ugBootstrap'])

        /** 服务定义 */
        .service('$request', ['$common', '$bootstrap', '$http', function($common, $bootstrap, $http) {
            var $this = {
                $config: {
                    $defaultParams: {_request_type: 'json'}
                },

                /** 配置 */
                config: function(defaultParams) {
                    $this.$config.$defaultParams = defaultParams;
                },

                /** Method.GET */
                get: function(url, data, success, pre, post, error) {
                    var args = $this.$checkArguments(data, success, pre, post, error);
                    $http({
                        method: 'get',
                        url: url,
                        params: ng.extend({}, $this.$config.$defaultParams, args.data)
                    }).then(function(data) {
                        $this.$doSuccess(data, args.success, args.pre, args.post, args.error);
                    }, function(data) {
                        $this.$doError(data, args.error, args.pre, args.post);
                    });
                },

                /** Method.POST */
                post: function(url, data, success, pre, post, error) {
                    var args = $this.$checkArguments(data, success, pre, post, error);
                    $http({
                        method: 'post',
                        url: url + ((url.indexOf('?') >= 0) ? '&' : '?') + jq.param($this.$config.$defaultParams),
                        data: args.data
                    }).then(function(data) {
                        $this.$doSuccess(data, args.success, args.pre, args.post, args.error);
                    }, function(data) {
                        $this.$doError(data, args.error, args.pre, args.post);
                    });
                },

                /** Method.POST urlencoded */
                postUrlencoded: function(url, data, success, pre, post, error) {
                    var args = $this.$checkArguments(data, success, pre, post, error);
                    $http({
                        method: 'post',
                        url: url,
                        data: jq.param(ng.extend({}, $this.$config.$defaultParams, args.data)),
                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
                    }).then(function(data) {
                        $this.$doSuccess(data, args.success, args.pre, args.post, args.error);
                    }, function(data) {
                        $this.$doError(data, args.error, args.pre, args.post);
                    });
                },

                /** Method.PUT */
                put: function(url, data, success, pre, post, error) {
                    var args = $this.$checkArguments(data, success, pre, post, error);
                    $http({
                        method: 'put',
                        url: url + ((url.indexOf('?') >= 0) ? '&' : '?') + jq.param($this.$config.$defaultParams),
                        data: args.data
                    }).then(function(data) {
                        $this.$doSuccess(data, args.success, args.pre, args.post, args.error);
                    }, function(data) {
                        $this.$doError(data, args.error, args.pre, args.post);
                    });
                },

                /** Method.PUT urlencoded */
                putUrlencoded: function(url, data, success, pre, post, error) {
                    var args = $this.$checkArguments(data, success, pre, post, error);
                    $http({
                        method: 'put',
                        url: url,
                        data: jq.param(ng.extend({}, $this.$config.$defaultParams, args.data)),
                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
                    }).then(function(data) {
                        $this.$doSuccess(data, args.success, args.pre, args.post, args.error);
                    }, function(data) {
                        $this.$doError(data, args.error, args.pre, args.post);
                    });
                },

                /** Method.DELETE */
                delete: function(url, data, success, pre, post, error) {
                    var args = $this.$checkArguments(data, success, pre, post, error);
                    $http({
                        method: 'delete',
                        url: url + ((url.indexOf('?') >= 0) ? '&' : '?') + jq.param($this.$config.$defaultParams),
                        data: args.data
                    }).then(function(data) {
                        $this.$doSuccess(data, args.success, args.pre, args.post, args.error);
                    }, function(data) {
                        $this.$doError(data, args.error, args.pre, args.post);
                    });
                },

                /** Method upload */
                upload: function(url, data, success, pre, post, error) {
                    var args = $this.$checkArguments(data, success, pre, post, error);
                    $http({
                        method: 'post',
                        url: url,
                        data: $common.ctrl.formData(ng.extend({}, $this.$config.$defaultParams, args.data)),
                        headers: {'Content-Type': undefined},
                        transformRequest: function(data) {
                            console.log('upload data:', data);
                            return data;
                        }
                    }).then(function(data) {
                        $this.$doSuccess(data, args.success, args.pre, args.post, args.error);
                    }, function(data) {
                        $this.$doError(data, args.error, args.pre, args.post);
                    });
                },

                /** 成功处理 */
                $doSuccess: function(data, success, pre, post, error) {
                    (pre || ng.noop)(data.data);
                    if ($bootstrap.alert.check(data.data)) {
                        (success || ng.noop)(data.data);
                    } else {
                        $this.$doError(data, error);
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
            return $this;
        }])
})(window.angular, window.jQuery);