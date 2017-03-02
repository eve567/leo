/*!
 *
 */
(function(ng, undefined) {
    /** 模块定义 */
    ng.module('ugBootstrap', ['ugCommon'])

        /** 服务定义 */
        .service('$bootstrap', ['$timeout', '$common', function($timeout, $common) {
            var $this = {
                $config: null,

                /** 配置 */
                config: function(context, scope) {
                    $this.$config = $this.$config || {};
                    $this.$config.context = context;
                    $this.$config.scope = scope;
                },

                /** 警告工具 */
                alert: {
                    $config: null,
                    $object: null,

                    /** 配置 */
                    config: function(data, signOutFlag, signView, delay) {
                        $this.alert.$config = $this.alert.$config || {};
                        $this.alert.$config.signOutFlag = signOutFlag;
                        $this.alert.$config.signView = signView;
                        $this.alert.$config.delay = delay || 5000;
                        $this.alert.$object = data;
                    },

                    /** 显示警告层 */
                    show: function(data) {
                        if (data.show !== false) {
                            $this.alert.$object = $this.alert.$object || {};
                            $this.alert.$object.type = ng.lowercase(data.type);
                            $this.alert.$object.message = data.message || data.messages.join('<br>');
                            $this.alert.$object.show = true;

                            if (data.delay !== -1) {
                                $timeout(function() {
                                    $this.alert.hide();
                                }, data.delay || $this.alert.$config.delay);
                            }
                        }
                    },

                    /** 隐藏警告层 */
                    hide: function() {
                        $this.alert.$object = $this.alert.$object || {};
                        $this.alert.$object.show = false;
                    },

                    /** 检查结果并自动显示 */
                    check: function(data) {
                        if (data && data.result) {
                            if ($common.valid.str(data.data) && data.data.indexOf($this.alert.$config.signOutFlag) === 0) {
                                $common.ctrl.redirect($this.alert.$config.signView);
                                return false;
                            } else {
                                $this.alert.show(data);
                                return $this.alert.$object.type === 'success';
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
                        $common.$('#' + id).modal('show');
                    },

                    /** 隐藏 */
                    hide: function(id) {
                        $common.$('#' + id).modal('hide');
                    }
                }
            };
            return $this;
        }])

        /** 表格合并单元格 */
        .directive('colSpan', function() {
            return {
                restrict: '',
                replace: true,
                scope: true,
                transclude: true,
                template: '<tr><td colspan="{{$len}}" class="text-center"><ng-transclude></ng-transclude></td></tr>',
                link: function($scope, $element) {
                    $scope.$len = $element.parent().parent().find('thead th').length;
                }
            };
        })

        /** 必填指令 */
        .directive('rq', function() {
            return {
                restrict: 'E',
                replace: true,
                transclude: true,
                template: '<span><i class="text-danger">*</i>&nbsp;<ng-transclude></ng-transclude></span>'
            };
        });
})(window.angular);