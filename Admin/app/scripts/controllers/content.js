'use strict';

angular.module('webAdminApp')
    .controller('ContentCtrl', [
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
                if($location.url() === '/content' || $location.url() === '/content#event') {
                    $('#event').addClass('in active');
                    $('#eventTypeCode').removeClass('in active');
                    $('#gift').removeClass('in active');
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
                    eventTarget: '',
                    eventPage: '',
                    prizePage: '',
                    startDate: new Date(),
                    endDate: new Date(),
                    publicationDate: new Date(),
                    publicationContent1: '',
                    publicationContent2: '',
                    premium: false,
                    visible: true,
                    gifts: [],
                    eventTypeCodeIds: []
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
                $('#eventFile').val('');
            };

            $scope.eventTypePlacesInit = function() {
                $http.get(config.apiUrl + '/admin/eventTypeCodes/place',
                  { headers: {'Authorization': $scope.auth }})
                  .success(function (response) {
                      if(response.success) {
                          $scope.eventTypePlaces = response.data;
                      }
                  }
                );
            };

            $scope.eventTypeFormsInit = function() {
                $http.get(config.apiUrl + '/admin/eventTypeCodes/form',
                  { headers: {'Authorization': $scope.auth }})
                  .success(function (response) {
                      if(response.success) {
                          $scope.eventTypeForms = response.data;
                      }
                  }
                );
            };

            $scope.eventTypeMethodsInit = function() {
                $http.get(config.apiUrl + '/admin/eventTypeCodes/method',
                  { headers: {'Authorization': $scope.auth }})
                  .success(function (response) {
                      if(response.success) {
                          $scope.eventTypeMethods = response.data;
                      }
                  }
                );
            };

            $scope.eventInfoInit();
            $scope.eventTypePlacesInit();
            $scope.eventTypeFormsInit();
            $scope.eventTypeMethodsInit();

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

            if($location.url() === '/content' || $location.url() === '/content#event') {
                $scope.eventPageChanged();
            }

            $scope.eventPremiumChangedValue = function() {
                $scope.eventPageChanged();
            };

            $scope.eventVisibleChangedValue = function() {
                $scope.eventPageChanged();
            };

            $scope.addEventInfo = function() {
                var eventTypeCodeIndex = 0;
                for(var i = 0; i < $scope.eventTypePlaces.length; i++) {
                    if($scope.eventTypePlaces[i].checked == true) {
                        $scope.eventInfo.eventTypeCodeIds[eventTypeCodeIndex] = $scope.eventTypePlaces[i].id;
                        eventTypeCodeIndex++;
                    }
                }
                for(var i = 0; i < $scope.eventTypeForms.length; i++) {
                    if($scope.eventTypeForms[i].checked == true) {
                        $scope.eventInfo.eventTypeCodeIds[eventTypeCodeIndex] = $scope.eventTypeForms[i].id;
                        eventTypeCodeIndex++;
                    }
                }
                for(var i = 0; i < $scope.eventTypeMethods.length; i++) {
                    if($scope.eventTypeMethods[i].checked == true) {
                        $scope.eventInfo.eventTypeCodeIds[eventTypeCodeIndex] = $scope.eventTypeMethods[i].id;
                        eventTypeCodeIndex++;
                    }
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
                            'eventTarget': $scope.eventInfo.eventTarget,
                            'eventPage': $scope.eventInfo.eventPage,
                            'prizePage': $scope.eventInfo.prizePage,
                            'startDate': $scope.eventInfo.startDate,
                            'endDate': $scope.eventInfo.endDate,
                            'publicationDate': $scope.eventInfo.publicationDate,
                            'publicationContent1': $scope.eventInfo.publicationContent1,
                            'publicationContent2': $scope.eventInfo.publicationContent2,
                            'premium': $scope.eventInfo.premium,
                            'visible': $scope.eventInfo.visible,
                            'gifts': $scope.eventInfo.gifts,
                            'eventTypeCodeIds': $scope.eventInfo.eventTypeCodeIds
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

            $scope.getEventInfo = function(_eventId) {

                $scope.eventInfoInit();
                $scope.eventTypePlacesInit();
                $scope.eventTypeFormsInit();
                $scope.eventTypeMethodsInit();

                $http.get(config.apiUrl + '/admin/events/' + _eventId,
                  { headers: {'Authorization': $scope.auth }})
                  .success(function (response) {
                      if(response.success) {
                          $scope.eventId = response.data.id;
                          $scope.eventInfo.title = response.data.title;
                          $scope.eventInfo.description = response.data.description;
                          $scope.eventInfo.company = response.data.company;
                          $scope.eventInfo.eventTarget = response.data.eventTarget;
                          $scope.eventInfo.eventPage = response.data.eventPage;
                          $scope.eventInfo.prizePage = response.data.prizePage;
                          $scope.eventInfo.startDate = new Date(response.data.startDate);
                          $scope.eventInfo.endDate = new Date(response.data.endDate);
                          $scope.eventInfo.publicationDate = new Date(response.data.publicationDate);
                          $scope.eventInfo.publicationContent1 = response.data.publicationContent1;
                          $scope.eventInfo.publicationContent2 = response.data.publicationContent2;
                          $scope.eventInfo.premium = response.data.premium;
                          $scope.eventInfo.visible = response.data.visible;
                          for(var i = 0; i < response.data.eventTypeCodeIds.length; i++) {
                              for(var j = 0; j < $scope.eventTypePlaces.length; j++) {
                                  if(response.data.eventTypeCodeIds[i] == $scope.eventTypePlaces[j].id) {
                                      $scope.eventTypePlaces[j].checked = true;
                                  }
                              }
                              for(var j = 0; j < $scope.eventTypeForms.length; j++) {
                                  if(response.data.eventTypeCodeIds[i] == $scope.eventTypeForms[j].id) {
                                      $scope.eventTypeForms[j].checked = true;
                                  }
                              }
                              for(var j = 0; j < $scope.eventTypeMethods.length; j++) {
                                  if(response.data.eventTypeCodeIds[i] == $scope.eventTypeMethods[j].id) {
                                      $scope.eventTypeMethods[j].checked = true;
                                  }
                              }
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

            $scope.eventTypeCodeTab = function() {
                if($location.url() === '/content#eventTypeCode') {
                    $('#event').removeClass('in active');
                    $('#eventTypeCode').addClass('in active');
                    $('#gift').removeClass('in active');
                    return true;
                }
                else
                    return false;
            };

            $scope.totalEventTypeCodeItems = 0;
            $scope.currentEventTypeCodePage = 1;
            $scope.eventTypeCodeSize = 10;
            $scope.maxEventTypeCodeSize = 10;
            $scope.searchEventTypeCodeEventId = '';
            $scope.searchEventTypeCodeSort = '';

            $scope.eventTypeCodeInfoInit = function() {
                $scope.eventTypeCodeInfo = {
                    name: '',
                    color: '',
                    sort: ''
                };
            };

            $scope.eventTypeCodeInfoInit();

            $scope.addEventTypeCodeInfo = function() {

                var formData = new FormData();
                formData.append('name', $.trim($scope.eventTypeCodeInfo.name));
                formData.append('color', $scope.eventTypeCodeInfo.color);
                formData.append('sort', $scope.eventTypeCodeInfo.sort);

                var req = {
                    method: 'POST',
                    url: config.apiUrl + '/admin/eventTypeCodes',
                    headers: {
                        'Authorization': $scope.auth,
                        'Content-Type': undefined
                    },
                    data: angular.identity(formData)
                };

                BootstrapDialog.confirm({
                    title: '위 이벤트 타입 항목을 등록할까요?',
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
                                        message: '이벤트 타입 항목 등록 성공!'
                                    });
                                }

                                $scope.eventTypeCodeInfoInit();
                                $scope.eventTypeCodePageChanged();

                                $('.loading-modal').remove();
                                $('body').removeClass("loading");
                            }).error(function(err) {
                                BootstrapDialog.show({
                                    type: BootstrapDialog.TYPE_DANGER,
                                    message: '이미 존재하는 이벤트 타입 항목입니다!'
                                });

                                $('.loading-modal').remove();
                                $('body').removeClass("loading");
                            });
                        }
                    }
                });
            };

            $scope.eventTypeCodePageChanged = function() {
                $http.get(config.apiUrl + '/admin/eventTypeCodes?page='
                  + $scope.currentEventTypeCodePage + '&size=' + $scope.eventTypeCodeSize
                  + '&sort=' + $scope.searchEventTypeCodeSort,
                  { headers: {'Authorization': $scope.auth }})
                  .success(function (response) {
                      if(response.success) {
                          $scope.eventTypeCodes = response.data.content;
                          $scope.totalEventTypeCodeItems= response.data.totalElements;
                      }
                  }
                );
            };

            if($location.url() === '/content#eventTypeCode') {
                $scope.eventTypeCodePageChanged();
            }

            $scope.eventTypeCodeSortChangedValue = function() {
                $scope.eventTypeCodePageChanged();
            };

            $scope.eventTypeCodeColorBinding = function(_eventTypeCodeId) {
                $('#eventTypeCode_' + _eventTypeCodeId).minicolors({
                    control: $(this).attr('data-control') || 'hue',
                    defaultValue: $(this).attr('data-defaultValue') || '',
                    format: $(this).attr('data-format') || 'hex',
                    keywords: $(this).attr('data-keywords') || '',
                    inline: $(this).attr('data-inline') === 'true',
                    letterCase: $(this).attr('data-letterCase') || 'lowercase',
                    opacity: $(this).attr('data-opacity'),
                    position: $(this).attr('data-position') || 'bottom left',
                    swatches: $(this).attr('data-swatches') ? $(this).attr('data-swatches').split('|') : [],
                    change: function(value, opacity) {
                        if( !value ) return;
                        if( opacity ) value += ', ' + opacity;
                        if( typeof console === 'object' ) {
                            console.log(value);
                        }
                    },
                    theme: 'bootstrap'
                });
            };

            $scope.searchEventTypeCodes = function() {
                $scope.eventTypeCodePageChanged();
            };

            $scope.editEventTypeCodeInfo = function(_eventTypeCodeId, _eventTypeCodeName, _eventTypeCodeColor, _eventTypeCodeSort) {

                var formData = new FormData();
                formData.append('name', $.trim(_eventTypeCodeName));
                formData.append('color', _eventTypeCodeColor);
                formData.append('sort', _eventTypeCodeSort);

                var eventTypeCodeSort = '위치';
                if(_eventTypeCodeSort == 'form') {
                    eventTypeCodeSort = '형태';
                } else if(_eventTypeCodeSort == 'method') {
                    eventTypeCodeSort = '방식';
                }

                var req = {
                    method: 'POST',
                    url: config.apiUrl + '/admin/eventTypeCodes/' + _eventTypeCodeId,
                    headers: {
                        'Authorization': $scope.auth,
                        'Content-Type': undefined
                    },
                    data: angular.identity(formData)
                };

                BootstrapDialog.confirm({
                    title: '이벤트 타입 항목 수정' ,
                    message: '이름: ' + _eventTypeCodeName
                    + '\n색깔: ' + _eventTypeCodeColor
                    + '\n분류: ' + eventTypeCodeSort
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
                                        message: '이벤트 타입 항목 수정 성공!'
                                    });
                                }

                                $scope.eventTypeCodePageChanged();
                            });
                        }
                    }
                });
            };

            $scope.removeEventTypeCode = function(_eventTypeCodeId) {

                var req = {
                    method: 'DELETE',
                    url: config.apiUrl + '/admin/eventTypeCodes/' + _eventTypeCodeId,
                    headers: {
                        'Authorization': $scope.auth
                    }
                };

                BootstrapDialog.confirm({
                    title: '이벤트 타입 항목 삭제' ,
                    message: '이벤트 타입 항목 ID: ' + _eventTypeCodeId + '\n 위 이벤트 타입 항목을 삭제할까요?',
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
                                        message: '이벤트 타입 항목 삭제 성공!'
                                    });
                                }

                                $scope.eventTypeCodePageChanged();
                            });
                        }
                    }
                });
            };

            $scope.giftTab = function() {
                if($location.url() === '/content#gift') {
                    $('#event').removeClass('in active');
                    $('#eventTypeCode').removeClass('in active');
                    $('#gift').addClass('in active');
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

            if($location.url() === '/content#gift') {
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

        }
    ]);