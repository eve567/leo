/*!
 *
 */
(function(ng, undefined) {'use strict';
    /** 定义模块 */
    ng.module('leoApi', [])

        /** 定义服务 */
        .service('$leo', ['$http', function($http) {
            var $this = {
                $config: {},
                $constant: {
                    jsonp: 'jsonp=JSON_CALLBACK',
                    uri: {}
                },

                /** 配置 */
                config: function(host, accessToken, appId, scope) {
                    $this.$config.host = host;
                    $this.$config.accessToken = accessToken;
                    $this.$config.appId = appId;
                    $this.$config.scope = scope;
                },

                /** JSONP */
                $jsonp: function(url, success, error) {
                    $http({
                        method: 'jsonp',
                        url: url
                    }).then(function(data) {
                        (success || ng.noop)(data.data);
                    }, function(data) {
                        console.error(data);
                        (error || ng.noop)(data.data);
                    });
                }
            };
            return $this;
        }])

        /** 布局指令 */
        .directive('layout', ['$leo', function($leo) {
            return {
                restrict: 'E',
                replace: true,
                transclude: true,
                scope: true,
                template: [

                ].join(''),
                link: function($scope, $element, $attrs) {

                }
            };
        }]);
})(window.angular);