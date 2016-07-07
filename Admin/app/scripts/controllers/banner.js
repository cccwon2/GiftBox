'use strict';

angular.module('webAdminApp')
    .controller('BannerCtrl', ['config', '$http', '$scope', '$cookieStore',
        function (config, $http, $scope, $cookieStore) {

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

            $scope.bannerPageChanged = function() {
                $http.get(config.apiUrl + '/admin/banners?page=' + $scope.currentBannerPage + '&size=' + $scope.bannerSize
                  + '&visible=' + $scope.searchBannerVisible,
                  { headers: {'Authorization': $scope.auth }})
                  .success(function (response) {
                      if(response.success) {
                          $scope.banners = response.data.content;
                          $scope.totalBannerItems= response.data.totalElements;
                      }
                  }
                );
            };

            $scope.bannerPageChanged();

            $scope.bannerVisibleChangedValue = function() {
                $scope.bannerPageChanged();
            };

            $scope.addBannerInfo = function() {
                var file = document.getElementById('bannerFile').files[0];
                if(typeof file == 'undefined') {
                    BootstrapDialog.show({
                        type: BootstrapDialog.TYPE_DANGER,
                        message: '이미지 파일을 첨부해 주세요!'
                    });
                    return;
                }
                var formData = new FormData();
                formData.append("banner",JSON.stringify($scope.bannerInfo));
                formData.append("file", file);

                var req = {
                    method: 'POST',
                    url: config.apiUrl + '/admin/banners/',
                    headers: {
                        'Authorization': $scope.auth,
                        'Content-Type': undefined
                    },
                    data: formData,
                    transformRequest: function (data) {
                        return angular.identity(data);
                    }
                };

                BootstrapDialog.confirm({
                    title: '배너 등록',
                    message: '위 배너를 등록할까요?',
                    type: BootstrapDialog.TYPE_WARNING,
                    closable: true,
                    draggable: true,
                    btnCancelLabel: 'Cancel',
                    btnOKLabel: 'OK',
                    btnOKClass: 'btn-warning',
                    callback: function (result) {
                        if (result) {
                            $('.close').click();
                            $('body').append("<div class='loading-modal'></div>");
                            $('body').addClass("loading");

                            $http(req).success(function (response) {
                                if (response.success) {
                                    BootstrapDialog.show({
                                        message: '배너 등록 성공!'
                                    });
                                }

                                $scope.bannerPageChanged();
                                $scope.bannerInfoInit();

                                $('.loading-modal').remove();
                                $('body').removeClass("loading");
                            });
                        }
                    }
                });
            };

            $scope.editBannerInfo = function(_bannerId, _eventId, _bannerPage, _visible) {

                var req = {
                    method: 'POST',
                    url: config.apiUrl + '/admin/banners/' + _bannerId,
                    headers: {
                        'Authorization': $scope.auth,
                        'Content-Type': 'application/json'
                    },
                    data: {
                        'eventId': _eventId,
                        'bannerPage': _bannerPage,
                        'visible': _visible
                    }
                };

                BootstrapDialog.confirm({
                    title: '배너 수정' ,
                    message: '이벤트 ID: ' + _eventId
                    + '\n배너 페이지: ' + _bannerPage
                    + '\n위와 같이 수정할까요?',
                    type: BootstrapDialog.TYPE_WARNING,
                    closable: true,
                    draggable: true,
                    btnCancelLabel: 'Cancel',
                    btnOKLabel: 'OK',
                    btnOKClass: 'btn-warning',
                    callback: function(result) {
                        if(result) {
                            $http(req).success(function (response) {
                                if(response.success) {
                                    BootstrapDialog.show({
                                        message: '배너 수정 성공!'
                                    });
                                }

                                $scope.bannerPageChanged();
                            });
                        }
                    }
                });
            };

            $scope.setBannerVisible = function(_bannerId, _visible) {

                var visible = (_visible != true);

                var formData = new FormData();
                formData.append('visible', visible);

                var req = {
                    method: 'POST',
                    url: config.apiUrl + '/admin/banners/' + _bannerId + '/visible',
                    headers: {
                        'Authorization': $scope.auth,
                        'Content-Type': undefined
                    },
                    data: angular.identity(formData)
                };

                BootstrapDialog.confirm({
                    title: '배너 DP 세팅' ,
                    message: 'DP: ' + visible + '\n 위와 같이 세팅할까요?',
                    type: BootstrapDialog.TYPE_WARNING,
                    closable: true,
                    draggable: true,
                    btnCancelLabel: 'Cancel',
                    btnOKLabel: 'OK',
                    btnOKClass: 'btn-warning',
                    callback: function(result) {
                        if(result) {
                            $http(req).success(function (response) {
                                if(response.success) {
                                    BootstrapDialog.show({
                                        message: '배너 DP 세팅 성공!'
                                    });
                                }

                                $scope.bannerPageChanged();
                            });
                        }
                    }
                });
            };

            $scope.removeBanner = function(_bannerId) {

                var req = {
                    method: 'DELETE',
                    url: config.apiUrl + '/admin/banners/' + _banenrId,
                    headers: {
                        'Authorization': $scope.auth
                    }
                };

                BootstrapDialog.confirm({
                    title: '배너 삭제' ,
                    message: '위 배너를 삭제할까요?',
                    type: BootstrapDialog.TYPE_WARNING,
                    closable: true,
                    draggable: true,
                    btnCancelLabel: 'Cancel',
                    btnOKLabel: 'OK',
                    btnOKClass: 'btn-warning',
                    callback: function(result) {
                        if(result) {
                            $http(req).success(function (response) {
                                if(response.success) {
                                    BootstrapDialog.show({
                                        message: '배너 삭제 성공!'
                                    });
                                }

                                $scope.bannerPageChanged();
                            });
                        }
                    }
                });
            };

            $scope.setSelectedBannerImage = function(_url) {

                $scope.selectedBannerImage = _url;
            };

        }]);
