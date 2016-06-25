'use strict';

/**
 * @ngdoc function
 * @name webAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the webAdminApp
 */
angular.module('webAdminApp')
    .controller('MainCtrl',
    ['config', '$scope', '$http', '$cookieStore',
        function (config, $scope, $http, $cookieStore) {

            $scope.auth = $cookieStore.get('auth') || '';

            $scope.checkLogin = function() {
                if($scope.auth === '') {
                    return false;
                } else {
                    return true;
                }
            };

            $scope.login = function() {
                var inputPassword = $.trim($('#inputPassword').val());

                var req = {
                    method: 'POST',
                    url: config.apiUrl + '/account/login/admin',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    data: {
                        password: inputPassword
                    }
                };

                $http(req).success(function(response) {
                    $cookieStore.put('auth', inputPassword);
                    $scope.auth = $cookieStore.get('auth');
                })
            };

            $scope.logout = function() {
                $cookieStore.remove('auth');
                $scope.auth = '';
            };

        }]);
