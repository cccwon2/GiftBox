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

            $scope.totalBannerItems = 0;
            $scope.currentBannerPage = 1;
            $scope.bannerSize = 10;
            $scope.maxBannerSize = 10;
            $scope.searchBannerVisible = '';
            $scope.selectedBannerImage = '';

            $scope.bannerInfoInit = function() {
                $scope.bannerInfo = {
                    eventId: '',
                    bannerPage: '',
                    visible: true
                };
                $('#bannerFile').val('');
            };

            $scope.bannerInfoInit();

        }]);
