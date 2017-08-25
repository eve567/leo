/*!
 *
 */
// noinspection JSUnusedLocalSymbols
(function(ng, undefined) {'use strict';
    /** 模块定义 */
    ng.module('leoApp', ['ngSanitize', 'ngAnimate', 'ugCommon', 'ugBootstrap', 'ugRequest', 'ugTree', 'leoApi'])
        /** 全局配置 */
        .config(['$controllerProvider', '$sceProvider', function($controllerProvider, $sceProvider) {
            $controllerProvider.allowGlobals();
            $sceProvider.enabled(false);
        }])

        /** 全局控制器 */
        .controller('leoCtrl', ['$scope', '$common', '$request', function($scope, $common, $request) {
            ng.extend($scope, $common, $request);
        }]);
})(window.angular);