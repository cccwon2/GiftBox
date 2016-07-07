'use strict';

angular.module('webAdminApp')
    .controller('PopupCtrl', ['config', '$http', '$scope', '$cookieStore',
        function (config, $http, $scope, $cookieStore) {

            $scope.auth = $cookieStore.get('auth') || '';

            $scope.checkLogin = function() {
                if($scope.auth === '') {
                    return false;
                } else {
                    return true;
                }
            };

            $scope.totalPopupItems = 0;
            $scope.currentPopupPage = 1;
            $scope.popupSize = 10;
            $scope.maxPopupSize = 10;
            $scope.searchPopupVisible = '';
            $scope.selectedPopupImage = '';

            $scope.dateOptions = {
                formatYear: 'yy',
                startingDay: 1
            };

            $scope.openStart = function() {
                $scope.popupStart.opened = true;
            };

            $scope.openEnd = function() {
                $scope.popupEnd.opened = true;
            };

            $scope.popupStart = {
                opened: false
            };

            $scope.popupEnd = {
                opened: false
            };

            $scope.popupInfoInit = function() {
                $scope.popupInfo = {
                    eventId: '',
                    popupPage: '',
                    startDate: new Date(),
                    endDate: new Date(),
                    visible: true
                };
                $('#popupFile').val('');
            };

            $scope.popupInfoInit();

            $scope.popupPageChanged = function() {
                $http.get(config.apiUrl + '/admin/popups?page=' + $scope.currentPopupPage + '&size=' + $scope.popupSize
                  + '&visible=' + $scope.searchPopupVisible,
                  { headers: {'Authorization': $scope.auth }})
                  .success(function (response) {
                      if(response.success) {
                          $scope.popups = response.data.content;
                          $scope.totalPopupItems= response.data.totalElements;
                      }
                  }
                );
            };

            $scope.popupPageChanged();

            $scope.popupVisibleChangedValue = function() {
                $scope.popupPageChanged();
            };

            $scope.addPopupInfo = function() {
                var file = document.getElementById('popupFile').files[0];
                if(typeof file == 'undefined') {
                    BootstrapDialog.show({
                        type: BootstrapDialog.TYPE_DANGER,
                        message: '이미지 파일을 첨부해 주세요!'
                    });
                    return;
                }
                var formData = new FormData();
                formData.append("popup",JSON.stringify($scope.popupInfo));
                formData.append("file", file);

                var req = {
                    method: 'POST',
                    url: config.apiUrl + '/admin/popups/',
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
                    title: '팝업 등록',
                    message: '위 팝업을 등록할까요?',
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
                                        message: '팝업 등록 성공!'
                                    });
                                }

                                $scope.popupPageChanged();
                                $scope.popupInfoInit();

                                $('.loading-modal').remove();
                                $('body').removeClass("loading");
                            });
                        }
                    }
                });
            };

            $scope.editPopupInfo = function(_popupId, _eventId, _popupPage, _popupStartDate, _popupEndDate, _visible) {

                var req = {
                    method: 'POST',
                    url: config.apiUrl + '/admin/popups/' + _popupId,
                    headers: {
                        'Authorization': $scope.auth,
                        'Content-Type': 'application/json'
                    },
                    data: {
                        'eventId': _eventId,
                        'popupPage': _popupPage,
                        'startDate' : _popupStartDate,
                        'endDate' : _popupEndDate,
                        'visible': _visible
                    }
                };

                BootstrapDialog.confirm({
                    title: '팝업 수정' ,
                    message: '이벤트 ID: ' + _eventId
                    + '\n팝업 페이지: ' + _popupPage
                    + '\n시작일: ' + _popupStartDate
                    + '\n종료일: ' + _popupEndDate
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
                                        message: '팝업 수정 성공!'
                                    });
                                }

                                $scope.popupPageChanged();
                            });
                        }
                    }
                });
            };

            $scope.setPopupVisible = function(_popupId, _visible) {

                var visible = (_visible != true);

                var formData = new FormData();
                formData.append('visible', visible);

                var req = {
                    method: 'POST',
                    url: config.apiUrl + '/admin/popups/' + _popupId + '/visible',
                    headers: {
                        'Authorization': $scope.auth,
                        'Content-Type': undefined
                    },
                    data: angular.identity(formData)
                };

                BootstrapDialog.confirm({
                    title: '팝업 DP 세팅' ,
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
                                        message: '팝업 DP 세팅 성공!'
                                    });
                                }

                                $scope.popupPageChanged();
                            });
                        }
                    }
                });
            };

            $scope.removePopup = function(_popupId) {

                var req = {
                    method: 'DELETE',
                    url: config.apiUrl + '/admin/popups/' + _popupId,
                    headers: {
                        'Authorization': $scope.auth
                    }
                };

                BootstrapDialog.confirm({
                    title: '팝업 삭제' ,
                    message: '위 팝업을 삭제할까요?',
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
                                        message: '팝업 삭제 성공!'
                                    });
                                }

                                $scope.popupPageChanged();
                            });
                        }
                    }
                });
            };

            $scope.setSelectedPopupImage = function(_url) {

                $scope.selectedPopupImage = _url;
            };

        }]);
