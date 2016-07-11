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

            $scope.editOpenStart = function() {
                $scope.editPopupStart.opened = true;
            };

            $scope.editOpenEnd = function() {
                $scope.editPopupEnd.opened = true;
            };

            $scope.editOpenPublication = function() {
                $scope.editPopupPublication.opened = true;
            };

            $scope.editPopupStart = {
                opened: false
            };

            $scope.editPopupEnd = {
                opened: false
            };

            $scope.editPopupPublication = {
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
                    startDate: '',
                    endDate: '',
                    publicationDate: '',
                    publicationContent1: '',
                    publicationContent2: '',
                    publicationType: '',
                    premium: false,
                    visible: true,
                    gifts: [],
                    eventTypeCodeIds: []
                };
                $scope.gift1 = { product: '', count: 0 };
                $scope.gift2 = { product: '', count: 0 };
                $scope.gift3 = { product: '', count: 0 };
                $scope.gift4 = { product: '', count: 0 };
                $scope.gift5 = { product: '', count: 0 };
                $scope.gift6 = { product: '', count: 0 };
                $scope.gift7 = { product: '', count: 0 };
                $scope.gift8 = { product: '', count: 0 };
                $scope.gift9 = { product: '', count: 0 };
                $scope.gift10 = { product: '', count: 0 };
                $('#eventFile').val('');
            };

            $scope.editEventInfoInit = function() {
                $scope.editEventInfo = {
                    title: '',
                    description: '',
                    company: '',
                    eventTarget: '',
                    eventPage: '',
                    prizePage: '',
                    startDate: '',
                    endDate: '',
                    publicationDate: '',
                    publicationContent1: '',
                    publicationContent2: '',
                    publicationType: '',
                    premium: false,
                    visible: true,
                    gifts: [],
                    eventTypeCodeIds: []
                };
                $scope.editGift1 = { product: '', count: 0 };
                $scope.editGift2 = { product: '', count: 0 };
                $scope.editGift3 = { product: '', count: 0 };
                $scope.editGift4 = { product: '', count: 0 };
                $scope.editGift5 = { product: '', count: 0 };
                $scope.editGift6 = { product: '', count: 0 };
                $scope.editGift7 = { product: '', count: 0 };
                $scope.editGift8 = { product: '', count: 0 };
                $scope.editGift9 = { product: '', count: 0 };
                $scope.editGift10 = { product: '', count: 0 };
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

            $scope.editEventTypePlacesInit = function() {
                $http.get(config.apiUrl + '/admin/eventTypeCodes/place',
                  { headers: {'Authorization': $scope.auth }})
                  .success(function (response) {
                      if(response.success) {
                          $scope.editEventTypePlaces = response.data;
                      }
                  }
                );
            };

            $scope.editEventTypeFormsInit = function() {
                $http.get(config.apiUrl + '/admin/eventTypeCodes/form',
                  { headers: {'Authorization': $scope.auth }})
                  .success(function (response) {
                      if(response.success) {
                          $scope.editEventTypeForms = response.data;
                      }
                  }
                );
            };

            $scope.editEventTypeMethodsInit = function() {
                $http.get(config.apiUrl + '/admin/eventTypeCodes/method',
                  { headers: {'Authorization': $scope.auth }})
                  .success(function (response) {
                      if(response.success) {
                          $scope.editEventTypeMethods = response.data;
                      }
                  }
                );
            };

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

            $scope.addEventInfoModal = function() {
                $scope.eventInfoInit();
                $scope.eventTypePlacesInit();
                $scope.eventTypeFormsInit();
                $scope.eventTypeMethodsInit();
                $('#addEventInput').click();
            };

            $scope.saveNewEventInfo = function() {
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

                                $('.loading-modal').remove();
                                $('body').removeClass("loading");
                            });
                        }
                    }
                });
            };

            $scope.editEventInfoModal = function(_editEventId) {
                $scope.editEventInfoInit();
                $scope.editEventTypePlacesInit();
                $scope.editEventTypeFormsInit();
                $scope.editEventTypeMethodsInit();

                $http.get(config.apiUrl + '/admin/events/' + _editEventId,
                  { headers: {'Authorization': $scope.auth }})
                  .success(function (response) {
                      if(response.success) {
                          $scope.editEventId = response.data.id;
                          $scope.editEventInfo.title = response.data.title;
                          $scope.editEventInfo.description = response.data.description;
                          $scope.editEventInfo.company = response.data.company;
                          $scope.editEventInfo.eventTarget = response.data.eventTarget;
                          $scope.editEventInfo.eventPage = response.data.eventPage;
                          $scope.editEventInfo.prizePage = response.data.prizePage;
                          if(response.data.startDate != null) {
                              $scope.editEventInfo.startDate = new Date(response.data.startDate);
                          }
                          if(response.data.endDate != null) {
                              $scope.editEventInfo.endDate = new Date(response.data.endDate);
                          }
                          if(response.data.publicationDate != null) {
                              $scope.editEventInfo.publicationDate = new Date(response.data.publicationDate);
                          }
                          $scope.editEventInfo.publicationContent1 = response.data.publicationContent1;
                          $scope.editEventInfo.publicationContent2 = response.data.publicationContent2;
                          $scope.editEventInfo.publicationType = response.data.publicationType;
                          $scope.editEventInfo.premium = response.data.premium;
                          $scope.editEventInfo.visible = response.data.visible;
                          for(var i = 0; i < response.data.eventTypeCodeIds.length; i++) {
                              for(var j = 0; j < $scope.editEventTypePlaces.length; j++) {
                                  if(response.data.eventTypeCodeIds[i] == $scope.editEventTypePlaces[j].id) {
                                      $scope.editEventTypePlaces[j].checked = true;
                                  }
                              }
                              for(var j = 0; j < $scope.editEventTypeForms.length; j++) {
                                  if(response.data.eventTypeCodeIds[i] == $scope.editEventTypeForms[j].id) {
                                      $scope.editEventTypeForms[j].checked = true;
                                  }
                              }
                              for(var j = 0; j < $scope.editEventTypeMethods.length; j++) {
                                  if(response.data.eventTypeCodeIds[i] == $scope.editEventTypeMethods[j].id) {
                                      $scope.editEventTypeMethods[j].checked = true;
                                  }
                              }
                          }
                          for(var i = 0; i < response.data.gifts.length; i++) {
                              if(i == 0) $scope.editGift1 = response.data.gifts[i];
                              if(i == 1) $scope.editGift2 = response.data.gifts[i];
                              if(i == 2) $scope.editGift3 = response.data.gifts[i];
                              if(i == 3) $scope.editGift4 = response.data.gifts[i];
                              if(i == 4) $scope.editGift5 = response.data.gifts[i];
                              if(i == 5) $scope.editGift6 = response.data.gifts[i];
                              if(i == 6) $scope.editGift7 = response.data.gifts[i];
                              if(i == 7) $scope.editGift8 = response.data.gifts[i];
                              if(i == 8) $scope.editGift9 = response.data.gifts[i];
                              if(i == 9) $scope.editGift10 = response.data.gifts[i];
                          }
                          $('#editEventInput').click();
                      }
                  }
                );
            };

            $scope.saveEditEventInfo = function() {
                var editEventTypeCodeIndex = 0;
                for(var i = 0; i < $scope.editEventTypePlaces.length; i++) {
                    if($scope.editEventTypePlaces[i].checked == true) {
                        $scope.editEventInfo.eventTypeCodeIds[editEventTypeCodeIndex] = $scope.editEventTypePlaces[i].id;
                        editEventTypeCodeIndex++;
                    }
                }
                for(var i = 0; i < $scope.editEventTypeForms.length; i++) {
                    if($scope.editEventTypeForms[i].checked == true) {
                        $scope.editEventInfo.eventTypeCodeIds[editEventTypeCodeIndex] = $scope.editEventTypeForms[i].id;
                        editEventTypeCodeIndex++;
                    }
                }
                for(var i = 0; i < $scope.editEventTypeMethods.length; i++) {
                    if($scope.editEventTypeMethods[i].checked == true) {
                        $scope.editEventInfo.eventTypeCodeIds[editEventTypeCodeIndex] = $scope.editEventTypeMethods[i].id;
                        editEventTypeCodeIndex++;
                    }
                }

                var editGiftIndex = 0;
                var editGiftProduct1 = $.trim($scope.editGift1.product);
                var editGiftProduct2 = $.trim($scope.editGift2.product);
                var editGiftProduct3 = $.trim($scope.editGift3.product);
                var editGiftProduct4 = $.trim($scope.editGift4.product);
                var editGiftProduct5 = $.trim($scope.editGift5.product);
                var editGiftProduct6 = $.trim($scope.editGift6.product);
                var editGiftProduct7 = $.trim($scope.editGift7.product);
                var editGiftProduct8 = $.trim($scope.editGift8.product);
                var editGiftProduct9 = $.trim($scope.editGift9.product);
                var editGiftProduct10 = $.trim($scope.editGift10.product);
                if (editGiftProduct1 != '') {
                    $scope.editEventInfo.gifts[editGiftIndex] = { product: editGiftProduct1, count: $scope.editGift1.count };
                    editGiftIndex++;
                }
                if (editGiftProduct2 != '') {
                    $scope.editEventInfo.gifts[editGiftIndex] = { product: editGiftProduct2, count: $scope.editGift2.count };
                    editGiftIndex++;
                }
                if (editGiftProduct3 != '') {
                    $scope.editEventInfo.gifts[editGiftIndex] = { product: editGiftProduct3, count: $scope.editGift3.count };
                    editGiftIndex++;
                }
                if (editGiftProduct4 != '') {
                    $scope.editEventInfo.gifts[editGiftIndex] = { product: editGiftProduct4, count: $scope.editGift4.count };
                    editGiftIndex++;
                }
                if (editGiftProduct5 != '') {
                    $scope.editEventInfo.gifts[editGiftIndex] = { product: editGiftProduct5, count: $scope.editGift5.count };
                    editGiftIndex++;
                }
                if (editGiftProduct6 != '') {
                    $scope.editEventInfo.gifts[editGiftIndex] = { product: editGiftProduct6, count: $scope.editGift6.count };
                    editGiftIndex++;
                }
                if (editGiftProduct7 != '') {
                    $scope.editEventInfo.gifts[editGiftIndex] = { product: editGiftProduct7, count: $scope.editGift7.count };
                    editGiftIndex++;
                }
                if (editGiftProduct8 != '') {
                    $scope.editEventInfo.gifts[editGiftIndex] = { product: editGiftProduct8, count: $scope.editGift8.count };
                    editGiftIndex++;
                }
                if (editGiftProduct9 != '') {
                    $scope.editEventInfo.gifts[editGiftIndex] = { product: editGiftProduct9, count: $scope.editGift9.count };
                    editGiftIndex++;
                }
                if (editGiftProduct10 != '') {
                    $scope.editEventInfo.gifts[editGiftIndex] = { product: editGiftProduct10, count: $scope.editGift10.count };
                }

                var req = {
                    method: 'POST',
                    url: config.apiUrl + '/admin/events/' + $scope.editEventId,
                    headers: {
                        'Authorization': $scope.auth,
                        'Content-Type': 'application/json'
                    },
                    data: {
                        'title': $scope.editEventInfo.title,
                        'description': $scope.editEventInfo.description,
                        'company': $scope.editEventInfo.company,
                        'eventTarget': $scope.editEventInfo.eventTarget,
                        'eventPage': $scope.editEventInfo.eventPage,
                        'prizePage': $scope.editEventInfo.prizePage,
                        'startDate': $scope.editEventInfo.startDate,
                        'endDate': $scope.editEventInfo.endDate,
                        'publicationDate': $scope.editEventInfo.publicationDate,
                        'publicationContent1': $scope.editEventInfo.publicationContent1,
                        'publicationContent2': $scope.editEventInfo.publicationContent2,
                        'publicationType': $scope.editEventInfo.publicationType,
                        'premium': $scope.editEventInfo.premium,
                        'visible': $scope.editEventInfo.visible,
                        'gifts': $scope.editEventInfo.gifts,
                        'eventTypeCodeIds': $scope.editEventInfo.eventTypeCodeIds
                    }
                };

                BootstrapDialog.confirm({
                    title: '이벤트 수정 확인',
                    message: 'ID: ' + $scope.editEventId + '\n 제목: ' + $scope.editEventInfo.title + '\n 위 항목을 수정할까요?',
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

                                    $('.loading-modal').remove();
                                    $('body').removeClass("loading");
                                }
                            });
                        }
                    }
                });
            };

            $scope.searchEvents = function() {
                $scope.eventPageChanged();
            };

            $scope.setEventPremium = function(_eventId, _premium) {

                var premium = (_premium != true);

                var formData = new FormData();
                formData.append('premium', premium);

                var req = {
                    method: 'POST',
                    url: config.apiUrl + '/admin/events/' + _eventId + '/premium',
                    headers: {
                        'Authorization': $scope.auth,
                        'Content-Type': undefined
                    },
                    data: angular.identity(formData)
                };

                BootstrapDialog.confirm({
                    title: '이벤트 Premium 세팅' ,
                    message: '이벤트 ID: ' + _eventId + '\nPremium: ' + premium + '\n 위와 같이 세팅할까요?',
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
                                        message: '이벤트 Premium 세팅 성공!'
                                    });
                                }

                                $scope.eventPageChanged();
                            });
                        }
                    }
                });
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
                    message: '이벤트 ID: ' + _eventId + '\nDP: ' + visible + '\n 위와 같이 세팅할까요?',
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
                    + '\n컬러: ' + _eventTypeCodeColor
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