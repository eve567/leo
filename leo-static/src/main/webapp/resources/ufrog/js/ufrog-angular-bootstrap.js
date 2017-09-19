/*!
 *
 */
(function(ng, undefined) {
    /** 模块定义 */
    ng.module('ugBootstrap', ['ugCommon'])

        /** 服务定义 */
        .service('$bootstrap', ['$timeout', '$common', function($timeout, $common) {
            var $_ = {
                $config: null,

                /** 配置 */
                config: function(context, scope) {
                    $_.$config = $_.$config || {};
                    $_.$config.context = context;
                    $_.$config.scope = scope;
                },

                /** 警告工具 */
                alert: {
                    $config: null,
                    $object: null,

                    /** 配置 */
                    config: function(data, signOutFlag, signView, delay) {
                        $_.alert.$config = $_.alert.$config || {};
                        $_.alert.$config.signOutFlag = signOutFlag;
                        $_.alert.$config.signView = signView;
                        $_.alert.$config.delay = delay || 5000;
                        $_.alert.$object = data;
                    },

                    /** 显示警告层 */
                    show: function(data) {
                        if (data.show !== false) {
                            $_.alert.$object = $_.alert.$object || {};
                            $_.alert.$object.type = ng.lowercase(data.type);
                            $_.alert.$object.message = data.message || data.messages.join('<br>');
                            $_.alert.$object.show = !$common.valid.empty($_.alert.$object.message);

                            if (data.delay !== -1) {
                                $timeout(function() {
                                    $_.alert.hide();
                                }, data.delay || $_.alert.$config.delay);
                            }
                        }
                    },

                    /** 隐藏警告层 */
                    hide: function() {
                        $_.alert.$object = $_.alert.$object || {};
                        $_.alert.$object.show = false;
                    },

                    /** 检查结果并自动显示 */
                    check: function(data) {
                        if (data && data.result) {
                            if ($common.valid.str(data.data) && data.data.indexOf($_.alert.$config.signOutFlag) === 0) {
                                $common.ctrl.redirect($_.alert.$config.signView);
                                return false;
                            } else {
                                $_.alert.show(data);
                                return $_.alert.$object.type === 'success';
                            }
                        } else {
                            return true;
                        }
                    }
                },

                /** 模态框工具 */
                modal: {
                    /** 显示 */
                    show: function(id) {
                        $_.elem(id).modal('show');
                    },

                    /** 隐藏 */
                    hide: function(id) {
                        $_.elem(id).modal('hide');
                    }
                },

                /** 气泡框工具 */
                popover: {
                    /** 配置 */
                    config: function(id, config) {
                        $_.elem(id).popover(config);
                    },

                    /** 显示 */
                    show: function(id) {
                        $_.elem(id).popover('show');
                    },

                    /** 隐藏 */
                    hide: function(id) {
                        $_.elem(id).popover('hide');
                    },

                    /** 切换显示 */
                    toggle: function(id) {
                        $_.elem(id).popover('toggle');
                    },

                    /** 销毁 */
                    destroy: function(id) {
                        $_.elem(id).popover('destroy');
                    }
                },

                /** 分页工具 */
                page: function(data, dname, pname, scope) {
                    (scope || $_.$config.scope)[dname] = data.content;
                    (scope || $_.$config.scope)[pname || 'pagination'] = {current: data.number, size: data.size, total: data.totalElements, pages: data.totalPages};
                },

                /** 页面元素 */
                elem: function(elem) {
                    if ($common.valid.elem(elem)) return elem;
                    else if ($common.valid.str(elem)) return $common.$('#' + elem);
                    throw 'elem "' + elem + '" invalid.';
                }
            };
            return $_;
        }])

        /** 警告指令 */
        .directive('alert', ['$bootstrap', function($bootstrap) {
            return {
                restrict: 'E',
                replace: true,
                require: '^ngModel',
                scope: {
                    signOutFlag: '@',
                    signView: '@',
                    delay: '@',
                    ngModel: '='
                },
                template: '<div class="alert alert-fixed" ng-class="$class()"><p ng-bind-html="ngModel.message"></p></div>',
                link: function($scope, $element) {
                    ng.extend($scope, {
                        // 初始化
                        $init: function() {
                            $bootstrap.alert.config($scope.ngModel, $scope.signOutFlag, $scope.signView, $scope.delay);
                        },

                        // 样式
                        $class: function() {
                            $element.css({top: 51 - $element.outerHeight()});
                            return (($bootstrap.alert.$object.show === true) ? 'alert-show ' : '') + 'alert-' + $scope.$mType[$bootstrap.alert.$object.type];
                        },

                        // 类型映射
                        $mType: {
                            'success': 'success',
                            'warning': 'warning',
                            'failure': 'danger'
                        }
                    }).$init();
                }
            };
        }])

        /**  */
        .directive('pagination', ['$parse', function($parse) {
            return {
                restrict: 'E',
                replace: true,
                scope: {
                    key: '@name',
                    start: '@',
                    size: '@',
                    method: '@',
                    delay: '='
                },
                template: [
                    '<ul class="pagination">',
                        '<li ng-repeat="page in pages" ng-class="page.class">',
                            '<a href="#" ng-click="$search(page.index)" ng-bind="page.text"></a>',
                        '</li>',
                    '</ul>'
                ].join(''),
                link: function($scope) {
                    ng.extend($scope, {
                        // 初始化
                        $init: function() {
                            $scope.$watchPage();
                            if (!$scope.delay || $scope.delay === 'false') $scope.$search(0);
                        },

                        // 监控绑定数据
                        $watchPage: function() {
                            $scope.$watch('$parent.' + ($scope.key || 'pagination'), function(val) {
                                if (ng.isDefined(val)) $scope.$gen(val);
                            });
                        },

                        // 生成数据
                        $gen: function(val) {
                            $scope.pages = [];
                            $scope.start = parseInt($scope.start || '0');
                            $scope.begin = (val.current - 2 > $scope.start) ? val.current - 2 : $scope.start;
                            $scope.end = (val.current + 2 < val.pages - 1 + $scope.start) ? val.current + 2 : val.pages - 1 + $scope.start;

                            if ($scope.begin > $scope.start) $scope.pages.push({index: $scope.start, text: 1, class: ''});
                            if ($scope.begin > $scope.start + 1) $scope.pages.push({index: $scope.start, text: '...', class: 'disabled'});
                            for (; $scope.begin <= $scope.end; $scope.begin++) {
                                $scope.pages.push({
                                    index: $scope.begin,
                                    text: $scope.begin + 1 - $scope.start,
                                    class: ($scope.begin === val.current) ? 'active' : ''
                                });
                            }
                            if ($scope.end < val.pages - 2 + $scope.start) $scope.pages.push({index: val.pages - 1 + $scope.start, text: '...', class: 'disabled'});
                            if ($scope.end < val.pages - 1 + $scope.start) $scope.pages.push({index: val.pages - 1 + $scope.start, text: val.pages, class: ''});
                        },

                        // 执行搜索
                        $search: function(index) {
                            ($parse($scope.method)($scope.$parent) || ng.noop)(index, $scope.size);
                        }
                    }).$init();
                }
            };
        }])

        /** 切换开关指令 */
        .directive('toggleSwitch', [function() {
            return {
                restrict: 'E',
                replace: true,
                require: '^ngModel',
                scope: {
                    ngModel: '=',
                    trueVal: '@true',
                    falseVal: '@false',
                    defaultVal: '@default'
                },
                template: '<i class="fa fa-2x" ng-class="$class()" ng-click="$toggle()"></i>',
                link: function($scope) {
                    ng.extend($scope, {
                        // 初始化
                        $init: function() {
                            $scope.trueVal = $scope.trueVal || true;
                            $scope.falseVal = $scope.falseVal || false;
                            $scope.defaultVal = $scope.defaultVal || $scope.falseVal;
                            $scope.ngModel = $scope.ngModel || $scope.defaultVal;
                        },

                        // 图标样式
                        $class: function() {
                            if ($scope.ngModel === $scope.trueVal) {
                                return 'fa-toggle-on text-success';
                            } else if ($scope.ngModel === $scope.falseVal) {
                                return 'fa-toggle-off text-danger';
                            } else {
                                return 'fa-toggle-off text-danger';
                            }
                        },

                        // 切换
                        $toggle: function() {
                            if ($scope.ngModel === $scope.trueVal) {
                                $scope.ngModel = $scope.falseVal;
                            } else if ($scope.ngModel === $scope.falseVal) {
                                $scope.ngModel = $scope.trueVal;
                            } else {
                                $scope.ngModel = $scope.trueVal;
                            }
                        }
                    }).$init();
                }
            };
        }])

        /** 模态框指令 */
        .directive('modal', [function() {
            return {
                restrict: 'E',
                replace: true,
                scope: true,
                transclude: true,
                template: [
                    '<div class="modal fade">',
                        '<div class="modal-dialog" ng-style="$modalWidth">',
                            '<div class="modal-content">',
                                '<div class="modal-header">',
                                    '<button type="button" class="close" data-dismiss="modal"><span>&times;</span></button>',
                                    '<h4 ng-bind-html="$modalTitle"></h4>',
                                '</div>',
                                '<div class="modal-body" ng-transclude></div>',
                                '<div class="modal-footer"></div>',
                            '</div>',
                        '</div>',
                    '</div>'
                ].join(''),
                link: function($scope, $element, $attrs) {
                    $element.find('.modal-body button:not(.btn-fixed)').appendTo($element.find('.modal-footer'));
                    $scope.$modalTitle = $attrs['title'];
                    $scope.$modalWidth = ng.isDefined($attrs['width']) ? {width: $attrs['width']} : {};
                }
            };
        }])

        /** 文件选择指令 */
        .directive('fileInput', ['$common', function($common) {
            return {
                restrict: 'E',
                replace: true,
                require: '^ngModel',
                scope: {
                    ngModel: '=',
                    btnValue: '@',
                    multiple: '@'
                },
                template: [
                    '<div>',
                        '<div class="input-group">',
                            '<input class="form-control" ng-value="$filenames()" readonly>',
                            '<span class="input-group-btn">',
                                '<button type="button" class="btn btn-default btn-fixed" ng-bind="btnValue"></button>',
                            '</span>',
                        '</div>',
                        '<input type="file" class="sr-only">',
                    '</div>'
                ].join(''),
                link: function($scope, $element) {
                    ng.extend($scope, {
                        // 初始化
                        $init: function() {
                            $scope.btnValue = $scope.btnValue || '\u9009\u62e9\u6587\u4ef6';
                            $element.find(':file').prop('multiple', $scope.$isMultiple()).on('change', $scope.$onChange);
                            $element.find('button, :text').on('click', function() {
                                $element.find(':file').trigger('click');
                            });
                        },

                        // 判断是否为多选
                        $isMultiple: function () {
                            return (!$common.valid.empty($scope.multiple) && $scope.multiple !== 'false');
                        },

                        // 文件改动后回调
                        $onChange: function(event) {
                            $scope.ngModel = $scope.ngModel || [];
                            ng.forEach(event.target.files, function(val) {
                                $scope.ngModel.push(val);
                            });
                            $scope.$apply();
                        },

                        // 文件名称
                        $filenames: function() {
                            $scope.$files = [];
                            ng.forEach($scope.ngModel, function(val) {
                                $scope.$files.push(val.name);
                            });
                            return $scope.$files.join(', ');
                        }
                    }).$init();
                }
            };
        }])

        /** 表单验证提示指令 */
        .directive('alertPop', [function() {
            return {
                restrict: 'E',
                replace: true,
                transclude: true,
                scope: {
                    field: '='
                },
                template: '<ul class="alert alert-danger alert-pop ng-fade-flow" ng-show="$isInvalid()" ng-transclude></ul>',
                link: function($scope, $element) {
                    ng.extend($scope, {
                        // 初始化
                        $init: function() {
                            $element.append('<p class="alert-pop-triangle"></p>');
                        },

                        // 判断是否无效
                        $isInvalid: function() {
                            if (ng.isUndefined($scope.field)) return false;
                            if ($scope.field.$invalid && $scope.field.$dirty) {
                                $element.css('left', $element.prev().outerWidth() + 20);
                                $element.parent().parent().addClass('has-error');
                                return true;
                            } else {
                                $element.parent().parent().removeClass('has-error');
                                return false;
                            }
                        }
                    }).$init();
                }
            };
        }])

        /** 表单验证提示元素指令 */
        .directive('alertPopItem', [function() {
            return {
                restrict: 'E',
                replace: true,
                transclude: true,
                scope: {
                    type: '@'
                },
                template: '<li ng-show="$isInvalid()" ng-transclude></li>',
                link: function($scope) {
                    ng.extend($scope, {
                        $isInvalid: function() {
                            if (ng.isUndefined($scope.$parent.$parent.field)) return false;
                            return ($scope.$parent.$parent.field.$error[$scope.type]);
                        }
                    });
                }
            };
        }])

        /** 表格合并单元格指令 */
        .directive('colSpan', [function() {
            return {
                restrict: 'A',
                scope: true,
                link: function($scope, $element) {
                    $element.attr('colspan', function() {
                        return ng.element($element.parents('table')[0]).find('thead th').length;
                    }).addClass('text-center');
                }
            };
        }])

        /** 必填指令 */
        .directive('rq', [function() {
            return {
                restrict: 'E',
                replace: true,
                transclude: true,
                template: '<span><i class="text-danger">*</i>&nbsp;<ng-transclude></ng-transclude></span>'
            };
        }])

        /** 面板标题栏下拉指令 */
        .directive('panelHeadingDropdown', [function() {
            return {
                restrict: 'A,E',
                replace: true,
                scope: true,
                transclude: true,
                template: '<li ng-transclude></li>',
                link: function($scope, $element) {
                    ng.extend($scope, {
                        // 初始化
                        $init: function() {
                            $scope.$isOpen = false;
                            $scope.$linkOnClick();
                            $scope.$winOnClick();
                        },

                        // 绑定元素单击事件　
                        $linkOnClick: function() {
                            $element.find('a:first-child').on('click', function() {
                                $element.addClass('open');
                                return false;
                            });
                        },

                        // 窗口点击事件
                        $winOnClick: function() {
                            ng.element(window).on('click', function() {
                                if ($element.hasClass('open')) {
                                    $element.removeClass('open');
                                }
                            });
                        }
                    }).$init();
                }
            };
        }])

        /** 气泡框指令 */
        .directive('popover', ['$bootstrap', function($bootstrap) {
            return {
                restrict: 'A',
                link: function($scope, $element, $attrs) {
                    $bootstrap.popover.config($element, {
                        html: true,
                        placement: $attrs['popoverPlacement'] || 'right',
                        title: $attrs['popoverTitle'] || '',
                        trigger: $attrs['popoverTrigger'] || 'click',
                        content: $element.find('.popover-body').children()
                    });
                }
            };
        }])

        /** 表格样式处理 */
        .directive('table', [function() {
            return {
                restrict: 'E',
                link: function($scope, $element) {
                    if (!$element.hasClass('table')) {
                        $element.addClass('table table-striped table-hover');
                    }
                }
            };
        }]);
})(window.angular);