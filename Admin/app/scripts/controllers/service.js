'use strict';

angular.module('webAdminApp')
    .controller('ServiceCtrl', [
        'config', '$http', '$scope', '$cookieStore', '$location',
        function (config, $http, $scope, $cookieStore, $location) {

            $scope.auth = $cookieStore.get('auth') || '';

            $scope.checkLogin = function() {
                if($scope.auth === '') {
                    return false;
                } else {
                    return true;
                }
            };

            $scope.eventTab = function() {
                if($location.url() === '/service' || $location.url() === '/service#event') {
                    $('#event').addClass('in active');
                    $('#gift').removeClass('in active');
                    $('#tag').removeClass('in active');
                    return true;
                }
                else
                    return false;
            };

            $scope.totalEventItems = 0;
            $scope.currentEventPage = 1;
            $scope.eventSize = 10;
            $scope.maxEventSize = 10;
            $scope.searchEventId = '';
            $scope.searchEventTitle = '';
            $scope.searchEventPremium = '';
            $scope.searchEventVisible = '';
            $scope.selectedEventImage = '';
            $scope.eventId = 0;

            $scope.minDate = $scope.minDate ? null : new Date();
            $scope.maxDate = new Date(2020, 12, 31);

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

            $scope.openPublication = function() {
                $scope.popupPublication.opened = true;
            };

            $scope.popupStart = {
                opened: false
            };

            $scope.popupEnd = {
                opened: false
            };

            $scope.popupPublication = {
                opened: false
            };

            $scope.eventInfoInit = function() {
                $scope.eventInfo = {
                    title: '',
                    description: '',
                    company: '',
                    eventType: '',
                    eventPage: '',
                    homePage: '',
                    startDate: new Date(),
                    endDate: new Date(),
                    publicationDate: new Date(),
                    premium: false,
                    visible: true,
                    gifts: [],
                    tags: []
                };
                $scope.gift1 = { product: '', count: 3 };
                $scope.gift2 = { product: '', count: 3 };
                $scope.gift3 = { product: '', count: 3 };
                $scope.gift4 = { product: '', count: 3 };
                $scope.gift5 = { product: '', count: 3 };
                $scope.gift6 = { product: '', count: 3 };
                $scope.gift7 = { product: '', count: 3 };
                $scope.gift8 = { product: '', count: 3 };
                $scope.gift9 = { product: '', count: 3 };
                $scope.gift10 = { product: '', count: 3 };
                $scope.tag1 = { name: '', color: ''};
                $scope.tag2 = { name: '', color: ''};
                $scope.tag3 = { name: '', color: ''};
                $scope.tag4 = { name: '', color: ''};
                $scope.tag5 = { name: '', color: ''};
                $('#eventFile').val('');
            };

            $scope.eventInfoInit();

            $scope.eventPageChanged = function() {
                $http.get(config.apiUrl + '/admin/events?page=' + $scope.currentEventPage + '&size=' + $scope.eventSize
                  + '&eventId=' + $scope.searchEventId + '&eventTitle=' + $scope.searchEventTitle
                  + '&premium=' + $scope.searchEventPremium + '&visible=' + $scope.searchEventVisible,
                  { headers: {'Authorization': $scope.auth }})
                  .success(function (response) {
                      if(response.success) {
                          $scope.events = response.data.content;
                          $scope.totalEventItems= response.data.totalElements;
                      }
                  }
                );
            };

            if($location.url() === '/service' || $location.url() === '/service#event') {
                $scope.eventPageChanged();
            }

            $scope.eventPremiumChangedValue = function() {
                $scope.eventPageChanged();
            };

            $scope.eventVisibleChangedValue = function() {
                $scope.eventPageChanged();
            };

            $scope.addEventInfo = function() {
                var tagIndex = 0;
                var tag1 = $.trim($scope.tag1.name);
                var tag2 = $.trim($scope.tag2.name);
                var tag3 = $.trim($scope.tag3.name);
                var tag4 = $.trim($scope.tag4.name);
                var tag5 = $.trim($scope.tag5.name);
                if (tag1 != '') {
                    $scope.eventInfo.tags[tagIndex] = { name: tag1, color: $scope.tag1.color };
                    tagIndex++;
                }
                if (tag2 != '') {
                    $scope.eventInfo.tags[tagIndex] = { name: tag2, color: $scope.tag2.color };
                    tagIndex++;
                }
                if (tag3 != '') {
                    $scope.eventInfo.tags[tagIndex] = { name: tag3, color: $scope.tag3.color };
                    tagIndex++;
                }
                if (tag4 != '') {
                    $scope.eventInfo.tags[tagIndex] = { name: tag4, color: $scope.tag4.color };
                    tagIndex++;
                }
                if (tag5 != '') {
                    $scope.eventInfo.tags[tagIndex] = { name: tag5, color: $scope.tag5.color };
                }

                var giftIndex = 0;
                var giftProduct1 = $.trim($scope.gift1.product);
                var giftProduct2 = $.trim($scope.gift2.product);
                var giftProduct3 = $.trim($scope.gift3.product);
                var giftProduct4 = $.trim($scope.gift4.product);
                var giftProduct5 = $.trim($scope.gift5.product);
                var giftProduct6 = $.trim($scope.gift6.product);
                var giftProduct7 = $.trim($scope.gift7.product);
                var giftProduct8 = $.trim($scope.gift8.product);
                var giftProduct9 = $.trim($scope.gift9.product);
                var giftProduct10 = $.trim($scope.gift10.product);
                if (giftProduct1 != '') {
                    $scope.eventInfo.gifts[giftIndex] = { product: giftProduct1, count: $scope.gift1.count };
                    giftIndex++;
                }
                if (giftProduct2 != '') {
                    $scope.eventInfo.gifts[giftIndex] = { product: giftProduct2, count: $scope.gift2.count };
                    giftIndex++;
                }
                if (giftProduct3 != '') {
                    $scope.eventInfo.gifts[giftIndex] = { product: giftProduct3, count: $scope.gift3.count };
                    giftIndex++;
                }
                if (giftProduct4 != '') {
                    $scope.eventInfo.gifts[giftIndex] = { product: giftProduct4, count: $scope.gift4.count };
                    giftIndex++;
                }
                if (giftProduct5 != '') {
                    $scope.eventInfo.gifts[giftIndex] = { product: giftProduct5, count: $scope.gift5.count };
                    giftIndex++;
                }
                if (giftProduct6 != '') {
                    $scope.eventInfo.gifts[giftIndex] = { product: giftProduct6, count: $scope.gift6.count };
                    giftIndex++;
                }
                if (giftProduct7 != '') {
                    $scope.eventInfo.gifts[giftIndex] = { product: giftProduct7, count: $scope.gift7.count };
                    giftIndex++;
                }
                if (giftProduct8 != '') {
                    $scope.eventInfo.gifts[giftIndex] = { product: giftProduct8, count: $scope.gift8.count };
                    giftIndex++;
                }
                if (giftProduct9 != '') {
                    $scope.eventInfo.gifts[giftIndex] = { product: giftProduct9, count: $scope.gift9.count };
                    giftIndex++;
                }
                if (giftProduct10 != '') {
                    $scope.eventInfo.gifts[giftIndex] = { product: giftProduct10, count: $scope.gift10.count };
                }

                if ($scope.eventId == 0) {
                    var file = document.getElementById('eventFile').files[0];
                    if(typeof file == 'undefined') {
                        BootstrapDialog.show({
                            type: BootstrapDialog.TYPE_DANGER,
                            message: '이미지 파일을 첨부해 주세요!'
                        });
                        return;
                    }
                    var formData = new FormData();
                    formData.append("event",JSON.stringify($scope.eventInfo));
                    formData.append("file", file);

                    var req = {
                        method: 'POST',
                        url: config.apiUrl + '/admin/events/',
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
                        title: '이벤트 등록',
                        message: '위 이벤트를 등록할까요?',
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
                                            message: '이벤트 등록 성공!'
                                        });
                                    }

                                    $scope.eventPageChanged();
                                    $scope.eventInfoInit();

                                    $('.loading-modal').remove();
                                    $('body').removeClass("loading");
                                });
                            }
                        }
                    });
                } else {
                    var req = {
                        method: 'POST',
                        url: config.apiUrl + '/admin/events/' + $scope.eventId,
                        headers: {
                            'Authorization': $scope.auth,
                            'Content-Type': 'application/json'
                        },
                        data: {
                            'title': $scope.eventInfo.title,
                            'description': $scope.eventInfo.description,
                            'company': $scope.eventInfo.company,
                            'eventType': $scope.eventInfo.eventType,
                            'eventPage': $scope.eventInfo.eventPage,
                            'homePage': $scope.eventInfo.homePage,
                            'startDate': $scope.eventInfo.startDate,
                            'endDate': $scope.eventInfo.endDate,
                            'publicationDate': $scope.eventInfo.publicationDate,
                            'premium': $scope.eventInfo.premium,
                            'visible': $scope.eventInfo.visible,
                            'gifts': $scope.eventInfo.gifts,
                            'tags': $scope.eventInfo.tags
                        }
                    };

                    BootstrapDialog.confirm({
                        title: '이벤트 수정 확인',
                        message: 'ID: ' + $scope.eventId + '\n 제목: ' + $scope.eventInfo.title + '\n 위 항목을 수정할까요?',
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
                                            message: '이벤트 수정 성공!'
                                        });

                                        $scope.eventPageChanged();
                                        $scope.eventInfoInit();

                                        $('.loading-modal').remove();
                                        $('body').removeClass("loading");
                                    }
                                });
                            }
                        }
                    });
                }
            };

            $scope.getEventInfo = function(id) {

                $scope.eventInfoInit();

                $http.get(config.apiUrl + '/admin/events/' + id,
                  { headers: {'Authorization': $scope.auth }})
                  .success(function (response) {
                      if(response.success) {
                          $scope.eventId = response.data.id;
                          $scope.eventInfo.title = response.data.title;
                          $scope.eventInfo.description = response.data.description;
                          $scope.eventInfo.company = response.data.company;
                          $scope.eventInfo.eventType = response.data.eventType;
                          $scope.eventInfo.eventPage = response.data.eventPage;
                          $scope.eventInfo.homePage = response.data.homePage;
                          $scope.eventInfo.startDate = new Date(response.data.startDate);
                          $scope.eventInfo.endDate = new Date(response.data.endDate);
                          $scope.eventInfo.publicationDate = new Date(response.data.publicationDate);
                          $scope.eventInfo.premium = response.data.premium;
                          $scope.eventInfo.visible = response.data.visible;
                          for(var i = 0; i < response.data.tags.length; i++) {
                              if(i == 0) $scope.tag1 = response.data.tags[i];
                              if(i == 1) $scope.tag2 = response.data.tags[i];
                              if(i == 2) $scope.tag3 = response.data.tags[i];
                              if(i == 3) $scope.tag4 = response.data.tags[i];
                              if(i == 4) $scope.tag5 = response.data.tags[i];
                          }
                          for(var i = 0; i < response.data.gifts.length; i++) {
                              if(i == 0) $scope.gift1 = response.data.gifts[i];
                              if(i == 1) $scope.gift2 = response.data.gifts[i];
                              if(i == 2) $scope.gift3 = response.data.gifts[i];
                              if(i == 3) $scope.gift4 = response.data.gifts[i];
                              if(i == 4) $scope.gift5 = response.data.gifts[i];
                              if(i == 5) $scope.gift6 = response.data.gifts[i];
                              if(i == 6) $scope.gift7 = response.data.gifts[i];
                              if(i == 7) $scope.gift8 = response.data.gifts[i];
                              if(i == 8) $scope.gift9 = response.data.gifts[i];
                              if(i == 9) $scope.gift10 = response.data.gifts[i];
                          }
                          $('#addEventButton').click();
                      }
                  }
                );
            };

            $scope.searchEvents = function() {
                $scope.eventPageChanged();
            };

            $scope.setEventVisible = function(_eventId, _visible) {

                var visible = (_visible != true);

                var formData = new FormData();
                formData.append('visible', visible);

                var req = {
                    method: 'POST',
                    url: config.apiUrl + '/admin/events/' + _eventId + '/visible',
                    headers: {
                        'Authorization': $scope.auth,
                        'Content-Type': undefined
                    },
                    data: angular.identity(formData)
                };

                BootstrapDialog.confirm({
                    title: '이벤트 DP 세팅' ,
                    message: '이벤트 ID: ' + _eventId + ', DP: ' + visible + '\n 위와 같이 세팅할까요?',
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
                                        message: '이벤트 DP 세팅 성공!'
                                    });
                                }

                                $scope.eventPageChanged();
                            });
                        }
                    }
                });
            };

            $scope.removeEvent = function(_eventId) {

                var req = {
                    method: 'DELETE',
                    url: config.apiUrl + '/admin/events/' + _eventId,
                    headers: {
                        'Authorization': $scope.auth
                    }
                };

                BootstrapDialog.confirm({
                    title: '이벤트 삭제' ,
                    message: '이벤트 ID: ' + _eventId + '\n 위 이벤트를 삭제할까요?',
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
                                        message: '이벤트 삭제 성공!'
                                    });
                                }

                                $scope.eventId = 0;
                                $scope.eventPageChanged();
                            });
                        }
                    }
                });
            };

            $scope.setSelectedEventImage = function(_url) {

                $scope.selectedEventImage = _url;
            };

            $scope.giftTab = function() {
                if($location.url() === '/service#gift') {
                    $('#event').removeClass('in active');
                    $('#gift').addClass('in active');
                    $('#tag').removeClass('in active');
                    return true;
                }
                else
                    return false;
            };

            $scope.totalGiftItems = 0;
            $scope.currentGiftPage = 1;
            $scope.giftSize = 10;
            $scope.maxGiftSize = 10;
            $scope.searchGiftEventId = '';
            $scope.searchGiftProduct = '';

            $scope.giftPageChanged = function() {
                $http.get(config.apiUrl + '/admin/gifts?page=' + $scope.currentGiftPage + '&size=' + $scope.giftSize
                  + '&eventId=' + $scope.searchGiftEventId + '&product=' + $scope.searchGiftProduct,
                  { headers: {'Authorization': $scope.auth }})
                  .success(function (response) {
                      if(response.success) {
                          $scope.gifts = response.data.content;
                          $scope.totalGiftItems= response.data.totalElements;
                      }
                  }
                );
            };

            if($location.url() === '/service#gift') {
                $scope.giftPageChanged();
            }

            $scope.searchGifts = function() {
                $scope.giftPageChanged();
            };

            $scope.editGiftInfo = function(_giftId, _product, _count) {

                var formData = new FormData();
                formData.append('product', _product);
                formData.append('count', _count);

                var req = {
                    method: 'POST',
                    url: config.apiUrl + '/admin/gifts/' + _giftId,
                    headers: {
                        'Authorization': $scope.auth,
                        'Content-Type': undefined
                    },
                    data: angular.identity(formData)
                };

                BootstrapDialog.confirm({
                    title: '상품 수정' ,
                    message: '상품 ID: ' + _giftId + '\n product: ' + _product + '\n count: ' + _count + '\n 위와 같이 수정할까요?',
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
                                        message: '상품 수정 성공!'
                                    });
                                }

                                $scope.giftPageChanged();
                            });
                        }
                    }
                });
            };

            $scope.removeGift = function(_giftId) {

                var req = {
                    method: 'DELETE',
                    url: config.apiUrl + '/admin/gifts/' + _giftId,
                    headers: {
                        'Authorization': $scope.auth
                    }
                };

                BootstrapDialog.confirm({
                    title: '상품 삭제' ,
                    message: '상품 ID: ' + _giftId + '\n 위 상품을 삭제할까요?',
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
                                        message: '상품 삭제 성공!'
                                    });
                                }

                                $scope.giftPageChanged();
                            });
                        }
                    }
                });
            };

            $scope.tagTab = function() {
                if($location.url() === '/service#tag') {
                    $('#event').removeClass('in active');
                    $('#gift').removeClass('in active');
                    $('#tag').addClass('in active');
                    return true;
                }
                else
                    return false;
            };

            $scope.totalTagItems = 0;
            $scope.currentTagPage = 1;
            $scope.tagSize = 10;
            $scope.maxTagSize = 10;
            $scope.searchTagEventId = '';
            $scope.searchTagName = '';

            $scope.tagPageChanged = function() {
                $http.get(config.apiUrl + '/admin/tags?page=' + $scope.currentTagPage + '&size=' + $scope.tagSize
                  + '&eventId=' + $scope.searchTagEventId + '&tagName=' + $scope.searchTagName,
                  { headers: {'Authorization': $scope.auth }})
                  .success(function (response) {
                      if(response.success) {
                          $scope.tags = response.data.content;
                          $scope.totalTagItems= response.data.totalElements;
                      }
                  }
                );
            };

            if($location.url() === '/service#tag') {
                $scope.tagPageChanged();
            }

            $scope.searchTags = function() {
                $scope.tagPageChanged();
            };

            $scope.editTagInfo = function(_tagId, _tagName) {

                var formData = new FormData();
                formData.append('name', _tagName);

                var req = {
                    method: 'POST',
                    url: config.apiUrl + '/admin/tags/' + _tagId,
                    headers: {
                        'Authorization': $scope.auth,
                        'Content-Type': undefined
                    },
                    data: angular.identity(formData)
                };

                BootstrapDialog.confirm({
                    title: 'Tag Name 수정' ,
                    message: 'Tag ID: ' + _tagId + ', Tag Name: ' + _tagName + '\n 위와 같이 수정할까요?',
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
                                        message: 'Tag Name 수정 성공!'
                                    });
                                }

                                $scope.tagPageChanged();
                            });
                        }
                    }
                });
            };

            $scope.removeTag = function(_tagId) {

                var req = {
                    method: 'DELETE',
                    url: config.apiUrl + '/admin/tags/' + _tagId,
                    headers: {
                        'Authorization': $scope.auth
                    }
                };

                BootstrapDialog.confirm({
                    title: 'Tag 삭제' ,
                    message: 'Tag ID: ' + _tagId + '\n 위 태그를 삭제할까요?',
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
                                        message: 'Tag 삭제 성공!'
                                    });
                                }

                                $scope.tagPageChanged();
                            });
                        }
                    }
                });
            };

        }
    ]);