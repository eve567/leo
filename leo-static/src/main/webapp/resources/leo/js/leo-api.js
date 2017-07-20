/*!
 *
 */
(function(ng, undefined) {'use strict';
    /** 定义模块 */
    ng.module('leoApi', ['ugCommon'])

        /** 定义服务 */
        .service('$leo', ['$http', '$common', function($http, $common) {
            var $this = {
                $config: {},
                $constant: {
                    jsonp: 'jsonp=JSON_CALLBACK',
                    uri: {
                        core: {
                            apps: 'api/apps',
                            navs: 'api/navs'
                        }
                    }
                },

                /** 配置 */
                config: function(host, accessToken, appId, scope) {
                    $this.$config.host = host;
                    $this.$config.accessToken = accessToken;
                    $this.$config.appId = appId;
                    $this.$config.scope = scope;
                },

                /** 查询应用 */
                apps: function(callback) {
                    $this.$jsonp([$this.$config.host, $this.$constant.uri.core.apps, $this.$config.accessToken, $this.$config.appId].join('/'), function(data) {
                        (callback || ng.noop)(data);
                    });
                },

                /** 查询导航 */
                navs: function(parent, callback) {
                    $this.$jsonp([$this.$config.host, $this.$constant.uri.core.navs, '99', parent.id, $this.$config.accessToken, $this.$config.appId].join('/'), function(data) {
                        (callback || ng.noop)(data);
                    });
                },

                /** 刷新视图 */
                refresh: function(url) {
                    if ($common.valid.str(url)) {
                        $this.$config.scope.bodyUrl = url + (url.indexOf('?') >= 0 ? '&' : '?') + $common.date.timestamp();
                    } else if ($common.valid.obj($this.$config.scope.$subnav)) {
                        $this.$config.scope.bodyUrl = $this.$config.scope.$subnav.path + ($this.$config.scope.$subnav.path.indexOf('?') >= 0 ? '&' : '?') + $common.date.timestamp();
                    } else {
                        console.error('wrong url or nav setting');
                    }
                },

                /** 全屏切换 */
                fullscreen: function(bool) {
                    $this.$config.scope.$fullscreen = $common.valid.defined(bool) ? bool : ($this.$config.scope.$fullscreen !== true);
                    if ($this.$config.scope.$fullscreen) {
                        $common.$('body').addClass('fullscreen');
                    } else {
                        $common.$('body').removeClass('fullscreen');
                    }
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
        .directive('layout', ['$leo', '$common', function($leo, $common) {
            return {
                restrict: 'E',
                replace: true,
                transclude: true,
                scope: true,
                templateUrl: $common.ctrl.scriptPath(['leo-api.min.js', 'leo-api.js']) + '/layout.html',
                link: function($scope, $element, $attrs) {
                    ng.extend($scope, {
                        // 初始化
                        $init: function() {
                            ng.extend($scope, {
                                $host: $attrs['host'],
                                $accessToken: $attrs['accessToken'],
                                $appId: $attrs['appId'],
                                $brand: $attrs['brand'],
                                $username: $attrs['username'],
                                $mode: parseInt($attrs['mode'] || 0)
                            });
                            $leo.config($scope.$host, $scope.$accessToken, $scope.$appId, $scope);
                            $scope.$dom();
                            $scope.$find({id: 'root', root: true});
                            $leo.apps(function(data) {
                                $scope.$apps = data;
                            });
                        },

                        // 标签节点处理
                        $dom: function() {
                            $scope.$transcludeDom = $element.find('[ng-transclude]');
                            $scope.$brandDom = $scope.$transcludeDom.find('.navbar-brand');
                            $scope.$llisDom = $scope.$transcludeDom.find('li.layout-nav-left');
                            $scope.$rlisDom = $scope.$transcludeDom.find('li.layout-nav-right');

                            if ($scope.$brandDom.length > 0) {
                                $element.find('.navbar-header > .navbar-brand').remove();
                                $element.find('.navbar-header').append($scope.$brandDom);
                            } if ($scope.$llisDom.length > 0) {
                                $element.find('.navbar-nav').not('.navbar-right').append($scope.$llisDom);
                            } if ($scope.$rlisDom.length > 0) {
                                $element.find('.navbar-right').prepend($scope.$rlisDom);
                            }
                        },

                        // 查询数据
                        $find: function(parent) {
                            $scope.$modes[$scope.$mode].$find(parent);
                        },

                        // 激活
                        $active: function(subnav) {
                            $scope.$subnav = subnav;
                            $leo.refresh();
                        },

                        // 模式数组
                        $modes: {
                            // 上-左右模式
                            0: {
                                $find: function(parent) {
                                    if ($common.valid.arr(parent.children) && parent.$loaded === true) {
                                        $scope.$nav = parent;
                                        $scope.$active($scope.$nav.children[0]);
                                    } else {
                                        $leo.navs(parent, function(data) {
                                            if ($common.valid.arr(data)) {
                                                if (parent.root === true) {
                                                    $scope.$navs = data;
                                                    $scope.$modes[0].$find($scope.$navs[0]);
                                                } else {
                                                    $scope.$nav = parent;
                                                    $scope.$nav.$loaded = true;
                                                    $scope.$nav.children = data;
                                                    $scope.$active($scope.$nav.children[0]);
                                                }
                                            } else {
                                                console.error(data);
                                            }
                                        });
                                    }
                                }
                            },

                            // 上下模式
                            1: {
                                $find: function(parent, checked) {
                                    if (!$common.valid.arr(parent.children) || parent.$loaded !== true) {
                                        $leo.navs(parent, function(data) {
                                            if ($common.valid.arr(data)) {
                                                if (parent.root === true) {
                                                    $scope.$navs = data;
                                                    ng.forEach($scope.$navs, function(val, idx) {
                                                        $scope.$modes[1].$find(val, idx === 0);
                                                    });
                                                } else {
                                                    parent.children = data;
                                                    parent.$loaded = true;
                                                    if (checked) {
                                                        $scope.$active(parent.children[0]);
                                                    }
                                                }
                                            } else {
                                                console.error(data);
                                            }
                                        });
                                    }
                                }
                            }
                        }
                    }).$init();
                }
            };
        }]);
})(window.angular);