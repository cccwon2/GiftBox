'use strict';

angular.module('webAdminApp')
    .controller('PushCtrl', ['config', '$http', '$scope', '$cookieStore',
        function (config, $http, $scope, $cookieStore) {

            $scope.auth = $cookieStore.get('auth') || '';

            $scope.checkLogin = function() {
                if($scope.auth === '') {
                    return false;
                } else {
                    return true;
                }
            };

            $scope.totalPushItems = 0;
            $scope.currentPushPage = 1;
            $scope.pushSize = 10;
            $scope.maxPushSize = 10;

            $scope.pushInfoInit = function() {
                $scope.pushInfo = {
                    title: '',
                    body: '',
                    eventId: ''
                };
            };

            $scope.pushInfoInit();

            $scope.pushPageChanged = function() {
                $http.get(config.apiUrl + '/admin/pushNotifications?page=' + $scope.currentPushPage + '&size=' + $scope.pushSize,
                  { headers: {'Authorization': $scope.auth }})
                  .success(function (response) {
                      if(response.success) {
                          $scope.pushs = response.data.content;
                          $scope.totalPushItems= response.data.totalElements;
                      }
                  }
                );
            };

            $scope.pushPageChanged();

            $scope.iosParmasInit = function() {
                $scope.iosParams = {
                    notification: {
                        title: '',
                        body: '',
                        badge: 1,
                        sound: 'default',
                        eventId: ''
                    },
                    priority: 'high',
                    content_available : true,
                    to : ''
                };
            };

            $scope.androidParmasInit = function() {
                $scope.androidParams = {
                    data : {
                        title : '',
                        body : '',
                        eventId: '',
                        msgcnt: 1
                    },
                    priority: 'high',
                    content_available : true,
                    to : ''
                };
            };

            $scope.addPushInfo = function() {

                var formData = new FormData();
                formData.append('title', $scope.pushInfo.title);
                formData.append('body', $scope.pushInfo.body);
                formData.append('eventId', $scope.pushInfo.eventId);

                var req = {
                    method: 'POST',
                    url: config.apiUrl + '/admin/pushNotifications',
                    headers: {
                        'Authorization': $scope.auth,
                        'Content-Type': undefined
                    },
                    data: angular.identity(formData)
                };

                BootstrapDialog.confirm({
                    title: '푸쉬 등록',
                    message: '위 푸쉬를 등록할까요?',
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

                                $http.get(config.apiUrl + '/admin/devices',
                                  { headers: {'Authorization': $scope.auth }})
                                  .success(function (response) {
                                      if(response.success) {
                                          $scope.devices = response.data;

                                          for(var i = 0; i < $scope.devices.length; i++) {

                                              if($scope.devices[i].os === 'ANDROID') {
                                                  $scope.androidParmasInit();
                                                  $scope.androidParams.data.title = $scope.pushInfo.title;
                                                  $scope.androidParams.data.body = $scope.pushInfo.body;
                                                  $scope.androidParams.data.eventId = $scope.pushInfo.eventId;
                                                  $scope.androidParams.to = $scope.devices[i].token;
                                                  console.log('android title: ' + $scope.androidParams.data.title);
                                                  console.log('android body: ' + $scope.androidParams.data.body);
                                                  console.log('android eventId: ' + $scope.androidParams.data.eventId);
                                                  console.log('android token: ' + $scope.androidParams.to);

                                                  var xmlhttp = new XMLHttpRequest();
                                                  xmlhttp.open("POST", 'https://gcm-http.googleapis.com/gcm/send', true);
                                                  xmlhttp.setRequestHeader("Authorization", "key=AIzaSyCdEotPGmkFBmCH5e5abUXoZa4KWEap3rE");
                                                  xmlhttp.setRequestHeader("Content-type", "application/json");
                                                  xmlhttp.onreadystatechange = function() {
                                                      if (xmlhttp.readyState == XMLHttpRequest.DONE) {
                                                          if(xmlhttp.status == 200){
                                                              var res = JSON.parse(xmlhttp.responseText);

                                                              if(res.success) {
                                                                  console.log("success: " + res.success);
                                                              }

                                                              if(res.failure) {
                                                                  console.log("failure: " + res.failure);
                                                              }
                                                          }else{
                                                              var res = JSON.parse(xmlhttp.responseText);
                                                          }
                                                      }
                                                  };
                                                  xmlhttp.send(JSON.stringify($scope.androidParams));
                                                  console.log('android push send');
                                              } else if($scope.devices[i].os === 'IOS') {
                                                  $scope.iosParmasInit();
                                                  $scope.iosParams.notification.title = $scope.pushInfo.title;
                                                  $scope.iosParams.notification.body = $scope.pushInfo.body;
                                                  $scope.iosParams.notification.eventId = $scope.pushInfo.eventId;
                                                  $scope.iosParams.to = $scope.devices[i].token;
                                                  console.log('ios title: ' + $scope.iosParams.notification.title);
                                                  console.log('ios body: ' + $scope.iosParams.notification.body);
                                                  console.log('ios eventId: ' + $scope.iosParams.notification.eventId);
                                                  console.log('ios token: ' + $scope.iosParams.to);

                                                  var xmlhttp = new XMLHttpRequest();
                                                  xmlhttp.open("POST", 'https://gcm-http.googleapis.com/gcm/send', true);
                                                  xmlhttp.setRequestHeader("Authorization", "key=AIzaSyCdEotPGmkFBmCH5e5abUXoZa4KWEap3rE");
                                                  xmlhttp.setRequestHeader("Content-type", "application/json");
                                                  xmlhttp.onreadystatechange = function() {
                                                      if (xmlhttp.readyState == XMLHttpRequest.DONE) {
                                                          if(xmlhttp.status == 200){
                                                              var res = JSON.parse(xmlhttp.responseText);

                                                              if(res.success) {
                                                                  console.log("success: " + res.success);
                                                              }

                                                              if(res.failure) {
                                                                  console.log("failure: " + res.failure);
                                                              }
                                                          }else{
                                                              var res = JSON.parse(xmlhttp.responseText);
                                                          }
                                                      }
                                                  };
                                                  xmlhttp.send(JSON.stringify($scope.iosParams));
                                                  console.log('ios push send');
                                              } else {
                                                  console.log('Undefined Device OS');
                                              }
                                          }

                                          BootstrapDialog.show({
                                              message: '푸쉬 등록 성공!'
                                          });

                                          $scope.pushPageChanged();
                                          $scope.pushInfoInit();

                                          $('.loading-modal').remove();
                                          $('body').removeClass("loading");
                                      }
                                  }
                                );
                            });
                        }
                    }
                });
            };

        }]);
