/*!
 *
 */
(function(ng, undefined) {
    /** 模块定义 */
    ng.module('ugTree', ['ugCommon'])
        /** 服务定义 */
        .service('$tree', ['$common', function($common) {
            var $_ = {
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
                            $_.collapse(n);
                        });
                    } else if ($common.valid.obj(node)) {
                        node.$status = node.$status || {};
                        if ($common.valid.undefined(node.$status.collapsed)) {
                            if ($common.valid.undefined(node.children) && $common.valid.fn(node.$fn.load)) {
                                $_.load(node);
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
                load: function(node) {
                    if ($common.valid.arr(node)) {
                        ng.forEach(node, function(n) {
                            $_.load(n);
                        });
                    } else if ($common.valid.obj(node) && $common.valid.fn(node.$fn.load)) {
                        node.$status = node.$status || {};
                        node.$status.loading = true;
                        node.$fn.load(node);
                    }
                },

                /** 重加载下级节点 */
                reload: function(node) {
                    if ($common.valid.arr(node)) {
                        ng.forEach(node, function(n) {
                            $_.reload(n);
                        });
                    } else if ($common.valid.obj(node)) {
                        $common.array.empty(node.children);
                        node.$status.collapsed = false;
                        $_.load(node);
                    }
                },

                /** 加载完成处理 */
                loaded: function(parent, children) {
                    parent.children = parent.children || [];
                    ng.forEach(children, function(child) {
                        child.$parent = parent;
                        parent.children.push(child);
                    });
                    $_.collapse(parent);
                    parent.$status.loading = false;
                },

                /** 选择节点 */
                check: function(node, checked, spreadParent, spreadChildren) {
                    node.$status = node.$status || {};
                    if ($common.valid.defined(checked) && node.$status.check === (checked ? 'checked' : 'unchecked')) {
                        return;
                    } else if ($common.valid.defined(checked)) {
                        node.$status.check = checked ? 'checked' : 'unchecked';
                    } else if ($common.valid.undefined(node.$status.check)) {
                        node.$status.check = 'checked';
                    } else if (node.$status.check === 'checked') {
                        node.$status.check = 'unchecked';
                    } else if (node.$status.check === 'unchecked' && $common.valid.fn(node.$fn.ban)) {
                        node.$status.check = 'ban';
                    } else if (node.$status.check === 'unchecked') {
                        node.$status.check = 'checked';
                    } else if (node.$status.check === 'ban') {
                        node.$status.check = 'checked';
                    } if (spreadParent && !$common.valid.empty(node.$parent.$parent) && node.$status.check === 'checked') {
                        $_.check(node.$parent, node.$status.check === 'checked', true, false);
                    } if (spreadChildren && !$common.valid.empty(node.children)) {
                        ng.forEach(node.children, function(child) {
                            $_.check(child, node.$status.check === 'checked', false, true);
                        });
                    }
                    node.$fn.check(node, node.$status.check === 'checked');
                },

                /** 设置加载方法 */
                setLoadFn: function(fn, node) {
                    node.$fn = node.$fn || {};
                    node.$fn.load = fn;
                },

                /** 设置选择方法 */
                setCheckFn: function(fn, node) {
                    node.$fn = node.$fn || {};
                    node.$fn.check = fn;
                },

                /** 设置禁止方法 */
                setBanFn: function(fn, node) {
                    node.$fn = node.$fn || {};
                    node.$fn.ban = fn;
                },

                /** 添加操作 */
                addOperation: function(icon, cls, title, fn, node) {
                    node.$operations = node.$operations || [];
                    node.$operations.push({
                        icon: icon,
                        cls: cls,
                        title: title,
                        fn: fn
                    });
                },

                /** 执行操作 */
                doOperation: function(node, operation) {
                    if ($common.valid.empty(node.$status.collapsed) || node.$status.collapsed === false) {
                        $_.collapse(node);
                    }
                    (operation.fn || ng.noop)(node);
                },

                /** 添加节点功能 */
                addNodeFn: function(html, fn, node) {
                    node.$nodeFns = node.$nodeFns || [];
                    node.$nodeFns.push({
                        html: html,
                        fn: fn
                    });
                }
            };
            return $_;
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
                            node.$status = node.$status || {};
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
                            node.$status = node.$status || {};
                            if ($common.valid.undefined(node.$status.check) || node.$status.check === 'unchecked') {
                                return $scope.$service.$config.icon.unchecked;
                            } else if (node.$status.check === 'checked') {
                                return $scope.$service.$config.icon.checked;
                            } else if (node.$status.check === 'ban') {
                                return $scope.$service.$config.icon.ban;
                            }
                        },

                        // 判断是否有复选框
                        $hasCheckbox: function(node) {
                            return ($common.valid.defined(node.$fn) && ($common.valid.fn(node.$fn.check) || $common.valid.fn(node.$fn.ban)));
                        },

                        //
                        $service: $tree
                    }).$init();
                }
            };
        }])
})(window.angular);