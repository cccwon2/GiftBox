'use strict';

angular.module('webAdminApp')
    .controller('BannerCtrl', ['config', '$http', '$scope', '$cookieStore', '$location',
        function (config, $http, $scope, $cookieStore, $location) {

            $scope.auth = $cookieStore.get('auth') || '';

            $scope.checkLogin = function() {
                if($scope.auth === '') {
                    return false;
                } else {
                    return true;
                }
            };

        }]);
