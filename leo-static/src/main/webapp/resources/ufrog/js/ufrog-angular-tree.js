/*!
 *
 */
(function(ng, undefined) {
    /** 模块定义 */
    ng.module('ugTree', ['ugCommon'])

        /** 服务定义 */
        .service('$tree', ['$common', '$timeout', function($common, $timeout) {
            var _ = {
                $config: {
                    icon: {
                        spin: 'fa-spinner fa-spin',
                        none: 'fa-info-circle',
                        close: 'fa-caret-right',
                        open: 'fa-caret-down',
                        unchecked: 'fa-square-o',
                        checked: 'fa-check-square-o',
                        ban: 'fa-ban'
                    }
                },

                /** 展开节点 */
                collapse: function(node) {
                    if ($common.valid.arr(node)) {
                        ng.forEach(node, function(n) {
                            _.collapse(n);
                        });
                    } else if ($common.valid.obj(node)) {
                        _.$init(node);
                        if ($common.valid.undefined(node.$status.collapsed)) {
                            if ($common.valid.undefined(node.children) && $common.valid.fn(node.$fn.load)) {
                                _.$loadChildren(node);
                            } else if ($common.valid.undefined(node.children)) {
                                node.children = [];
                            } if ($common.valid.defined(node.children)) {
                                node.$status.collapsed = true;
                            }
                        } else if (node.$status.collapsed === false) {
                            node.$status.collapsed = true;
                        } else if (node.$status.collapsed === true) {
                            node.$status.collapsed = false;
                        }
                    }
                },

                /** 查询下级节点 */
                $loadChildren: function(node) {
                    if ($common.valid.arr(node)) {
                        ng.forEach(node, function(n) {
                            _.$loadChildren(n);
                        });
                    } else if ($common.valid.obj(node) && $common.valid.fn(node.$fn.load)) {
                        _.$init(node);
                        node.$status.loading = true;
                        node.$fn.load(node);
                        _.$checkLoadStatus(node);
                        _.$init(node.children, node);
                        _.collapse(node);
                        node.$status.loading = false;
                    }
                },

                /** 执行操作 */
                $doOperation: function(node, operation) {
                    if ($common.valid.empty(node.$status.collapsed) || node.$status.collapsed === false) {
                        _.collapse(node);
                        _.$checkLoadStatus(node);
                    }
                    (operation.fn || ng.noop)(node);
                },

                /** 初始化节点 */
                $init: function(node, parent) {
                    if ($common.valid.arr(node)) {
                        ng.forEach(node, function(n) {
                            _.$init(n, parent);
                        });
                    } else if ($common.valid.obj(node) && (node.$status || {}).inited !== true) {
                        if ($common.valid.undefined(node.$fn)) node.$fn = {};
                        if ($common.valid.undefined(node.$status)) node.$status = {};
                        if ($common.valid.undefined(node.$operations)) node.$operations = [];
                        if ($common.valid.undefined(node.$parent) && $common.valid.obj(parent)) node.$parent = parent;
                        node.$status.inited = true;
                    }
                },

                /** 判断是否纯功能 */
                $isFn: function(node) {
                    return ($common.valid.obj(node) && $common.valid.fn((node.$fn || {}).node));
                },

                /** 检查加载状态 */
                $checkLoadStatus: function(node, times) {
                    times = times || 0;
                    $timeout(function() {
                        if ($common.valid.undefined(node.children) && times < 10) {
                            _.$checkLoadStatus(node, times++);
                        } else if (times >= 10) {
                            console.log('cannot check node load status', node);
                        }
                    }, 200);
                }
            };
            return _;
        }])

        /** 树形指令 */
        .directive('tree', ['$common', '$tree', function($common, $tree) {
            return {
                restrict: 'E',
                replace: true,
                require: '^ngModel',
                scope: {
                    ngModel: '=',
                    autoCollapse: '='
                },
                templateUrl: $common.ctrl.scriptPath(['ufrog-angular-tree.min.js', 'ufrog-angular-tree.js']) + '/ufrog-angular-tree.html',
                link: function($scope, $element) {
                    ng.extend($scope, {
                        // 初始化
                        $init: function() {
                            $scope.$wrap();
                        },

                        // 顶层标签包裹
                        $wrap: function() {
                            if (!$element.parent().is('li')) {
                                $element.wrap('<div class="tree"></div>');
                            }
                        },

                        // 展示图标样式
                        $collapseIcon: function(node) {
                            $scope.$service.$init(node);
                            if (node.$status.loading === true) {
                                return $scope.$service.$config.icon.spin;
                            } else if ($common.valid.arr(node.children) && node.children.length === 0) {
                                return $scope.$service.$config.icon.none;
                            } else if ($common.valid.undefined(node.$status.collapsed) || node.$status.collapsed === false) {
                                return $scope.$service.$config.icon.close;
                            } else if (node.$status.collapsed === true) {
                                return $scope.$service.$config.icon.open;
                            }
                        },

                        // 复选框图标样式
                        $checkboxIcon: function(node) {
                            if ($common.valid.undefined(node.$status.check) || node.$status.check === 'unchecked') {
                                return $scope.$service.$config.icon.unchecked;
                            } else if (node.$status.check === 'checked') {
                                return $scope.$service.$config.icon.unchecked;
                            } else if (node.$status.check === 'ban') {
                                return $scope.$service.$config.icon.ban;
                            }
                        },

                        $service: $tree
                    }).$init();
                }
            };
        }])
})(window.angular);