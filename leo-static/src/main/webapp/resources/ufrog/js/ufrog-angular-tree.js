/*!
 *
 */
(function(ng, undefined) {
    /** 模块定义 */
    ng.module('ugTree', ['ugCommon'])

        /** 服务定义 */
        .service('$tree', ['$common', function($common) {
            var $this = {
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
                            $this.collapse(n);
                        });
                    } else if ($common.valid.obj(node)) {
                        $this.$init(node);
                        if ($common.valid.undefined(node.$status.collapsed)) {
                            if ($common.valid.undefined(node.children) && $common.valid.fn(node.$fn.load)) {
                                $this.loadChildren(node);
                            } else if ($common.valid.undefined(node.children)) {
                                node.children = [];
                            } if ($common.valid.defined(node.children) && node.children.length > 0) {
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
                loadChildren: function(node) {
                    if ($common.valid.arr(node)) {
                        ng.forEach(node, function(n) {
                            $this.loadChildren(n);
                        });
                    } else if ($common.valid.obj(node) && $common.valid.fn(node.$fn.load)) {
                        $this.$init(node);
                        node.$status.loading = true;
                        node.$fn.load(node, function(children) {
                            node.$status.loading = false;
                            $this.$init(children, node);
                            $this.collapse(node);
                        });
                    }
                },

                /** 初始化节点 */
                $init: function(node, parent) {
                    if ($common.valid.arr(node)) {
                        ng.forEach(node, function(n) {
                            $this.$init(n, parent);
                        });
                    } else if ($common.valid.obj(node) && (node.$status || {}).inited !== true) {
                        console.log(node);
                        if ($common.valid.undefined(node.$fn)) node.$fn = {};
                        if ($common.valid.undefined(node.$status)) node.$status = {};
                        if ($common.valid.undefined(node.$operations)) node.$operations = [];
                        if ($common.valid.undefined(node.$parent) && $common.valid.obj(parent)) node.$parent = parent;
                        node.$status.inited = true;
                        console.log(node);
                    }
                }
            };
            return $this;
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