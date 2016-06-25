'use strict';

angular.module('webAdminApp')
    .controller('SupportCtrl', [
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

            $scope.contactTab = function() {
                if($location.url() === '/support' || $location.url() === '/support#contact') {
                    $('#contact').addClass('in active');
                    $('#notice').removeClass('in active');
                    $('#faq').removeClass('in active');
                    $('#mobile').removeClass('in active');
                    return true;
                }
                else
                    return false;
            };

            $scope.totalContactItems = 0;
            $scope.currentContactPage = 1;
            $scope.contactSize = 10;
            $scope.maxContactSize = 10;

            $scope.contactPageChanged = function() {
                $http.get(config.apiUrl + '/admin/contacts?page=' + $scope.currentContactPage + '&size=' + $scope.contactSize,
                  { headers: {'Authorization': $scope.auth }})
                  .success(function (response) {
                      if(response.success) {
                          $scope.contacts = response.data.content;
                          $scope.totalContactItems= response.data.totalElements;
                      }
                  }
                );
            };

            $scope.showInquiry = function(contents) {

                BootstrapDialog.show({
                    message: contents
                });
            };

            if($location.url() === '/support' || $location.url() === '/support#contact') {
                $scope.contactPageChanged();
            }

            $scope.tinymceOptions = {
                onChange: function(e) {
                    // put logic here for keypress and cut/paste changes
                },
                theme : 'modern',
                plugins : [
                    'advlist autolink lists link image charmap print preview hr anchor pagebreak',
                    'searchreplace wordcount visualblocks visualchars code fullscreen',
                    'insertdatetime media nonbreaking save table contextmenu directionality',
                    'emoticons template paste textcolor colorpicker textpattern imagetools'
                ],
                toolbar1: 'insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image',
                toolbar2: 'print preview media | forecolor backcolor emoticons',
                image_advtab: true,
                templates: [
                    { title: 'Test template 1', content: 'Test 1' },
                    { title: 'Test template 2', content: 'Test 2' }
                ],
                content_css: [
                    '//fast.fonts.net/cssapi/e6dc9b99-64fe-4292-ad98-6974f93cd2a2.css',
                    '//www.tinymce.com/css/codepen.min.css'
                ],
                language_url: '/scripts/tinymce/language/ko_KR.js'
            };

            $scope.noticeTab = function() {
                if($location.url() === '/support#notice') {
                    $('#contact').removeClass('in active');
                    $('#notice').addClass('in active');
                    $('#faq').removeClass('in active');
                    $('#mobile').removeClass('in active');
                    return true;
                }
                else
                    return false;
            };

            $scope.noticeId = 0;
            $scope.noticeTitle = '';
            $scope.noticeContent = '';
            $scope.totalNoticeItems = 0;
            $scope.currentNoticePage = 1;
            $scope.noticeSize = 10;
            $scope.maxNoticeSize = 10;

            $scope.noticePageChanged = function() {
                $http.get(config.apiUrl + '/admin/notices?page=' + $scope.currentNoticePage + '&size=' + $scope.noticeSize,
                  { headers: {'Authorization': $scope.auth }})
                  .success(function (response) {
                      if(response.success) {
                          $scope.notices = response.data.content;
                          $scope.totalNoticeItems= response.data.totalElements;
                      }
                  }
                );
            };

            if($location.url() === '/support#notice') {
                $scope.noticePageChanged();
            }

            $scope.getNoticeInfo = function(_noticeId) {
                $http.get(config.apiUrl + '/admin/notices/' + _noticeId,
                  { headers: {'Authorization': $scope.auth }})
                  .success(function (response) {
                      if(response.success) {
                          $scope.noticeId = response.data.id;
                          $scope.noticeTitle = response.data.title;
                          $scope.noticeContent = response.data.contents;
                      }
                  }
                );
            };

            $scope.setNoticeVisible = function(_noticeId, _visible) {

                var visible = (_visible != true);

                var formData = new FormData();
                formData.append('visible', visible);

                var req = {
                    method: 'POST',
                    url: config.apiUrl + '/admin/notices/' + _noticeId + '/visible',
                    headers: {
                        'Authorization': $scope.auth,
                        'Content-Type': undefined
                    },
                    data: angular.identity(formData)
                };

                BootstrapDialog.confirm({
                    title: 'Notice DP 세팅' ,
                    message: 'Notice ID: ' + _noticeId + ', DP: ' + visible + '\n 위와 같이 세팅할까요?',
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
                                        message: 'Notice DP 세팅 성공!'
                                    });
                                }

                                $scope.noticePageChanged();
                            });
                        }
                    }
                });
            };

            $scope.addNoticeInfo = function() {

                var contents = $scope.noticeContent;
                if(contents === '') {
                    BootstrapDialog.show({
                        type: BootstrapDialog.TYPE_DANGER,
                        message: '내용을 입력해 주세요!'
                    });
                    return;
                }

                if($scope.noticeId == 0) {
                    var req = {
                        method: 'POST',
                        url: config.apiUrl + '/admin/notices',
                        headers: {
                            'Authorization': $scope.auth,
                            'Content-Type': 'application/json'
                        },
                        data: {
                            'title': $scope.noticeTitle,
                            'contents': contents
                        }
                    };

                    BootstrapDialog.confirm({
                        title: 'Notice 등록 확인',
                        message: $scope.noticeTitle + '\n 위 항목을 등록할까요?',
                        type: BootstrapDialog.TYPE_WARNING,
                        closable: true,
                        draggable: true,
                        btnCancelLabel: 'Cancel',
                        btnOKLabel: 'OK',
                        btnOKClass: 'btn-warning',
                        callback: function (result) {
                            if (result) {
                                $http(req).success(function (response) {
                                    if (response.success) {
                                        BootstrapDialog.show({
                                            message: 'Notice 등록 성공!'
                                        });

                                        $scope.noticePageChanged();
                                    }
                                });
                            }
                        }
                    });
                } else {
                    var req = {
                        method: 'POST',
                        url: config.apiUrl + '/admin/notices/' + $scope.noticeId,
                        headers: {
                            'Authorization': $scope.auth,
                            'Content-Type': 'application/json'
                        },
                        data: {
                            'title': $scope.noticeTitle,
                            'contents': contents
                        }
                    };

                    BootstrapDialog.confirm({
                        title: 'Notice 수정 확인',
                        message: 'ID: ' + $scope.noticeId + ', Title: ' + $scope.noticeTitle + '\n 위 항목을 수정할까요?',
                        type: BootstrapDialog.TYPE_WARNING,
                        closable: true,
                        draggable: true,
                        btnCancelLabel: 'Cancel',
                        btnOKLabel: 'OK',
                        btnOKClass: 'btn-warning',
                        callback: function (result) {
                            if (result) {
                                $http(req).success(function (response) {
                                    if (response.success) {
                                        BootstrapDialog.show({
                                            message: 'Notice 수정 성공!'
                                        });

                                        $scope.noticePageChanged();
                                    }
                                });
                            }
                        }
                    });
                }
            };

            $scope.removeNotice = function(_noticeId) {

                var req = {
                    method: 'DELETE',
                    url: config.apiUrl + '/admin/notices/' + _noticeId,
                    headers: {
                        'Authorization': $scope.auth
                    }
                };

                BootstrapDialog.confirm({
                    title: 'Notice 삭제' ,
                    message: 'Notice ID: ' + _noticeId + '\n 위 항목을 삭제할까요?',
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
                                        message: 'Notice 삭제 성공!'
                                    });
                                }

                                $scope.noticePageChanged();
                            });
                        }
                    }
                });
            };

            $scope.faqTab = function() {
                if($location.url() === '/support#faq') {
                    $('#contact').removeClass('in active');
                    $('#notice').removeClass('in active');
                    $('#faq').addClass('in active');
                    $('#mobile').removeClass('in active');
                    return true;
                }
                else
                    return false;
            };

            $scope.faqId = 0;
            $scope.faqTitle = '';
            $scope.faqContent = '';
            $scope.totalFaqItems = 0;
            $scope.currentFaqPage = 1;
            $scope.faqSize = 10;
            $scope.maxFaqSize = 10;

            $scope.faqPageChanged = function() {
                $http.get(config.apiUrl + '/admin/faqs?page=' + $scope.currentFaqPage + '&size=' + $scope.faqSize,
                  { headers: {'Authorization': $scope.auth }})
                  .success(function (response) {
                      if(response.success) {
                          $scope.faqs = response.data.content;
                          $scope.totalFaqItems= response.data.totalElements;
                      }
                  }
                );
            };

            if($location.url() === '/support#faq') {
                $scope.faqPageChanged();
            }

            $scope.getFaqInfo = function(_faqId) {
                $http.get(config.apiUrl + '/admin/faqs/' + _faqId,
                  { headers: {'Authorization': $scope.auth }})
                  .success(function (response) {
                      if(response.success) {
                          $scope.faqId = response.data.id;
                          $scope.faqTitle = response.data.title;
                          $scope.faqContent= response.data.contents;
                      }
                  }
                );
            };

            $scope.setFaqVisible = function(_faqId, _visible) {

                var visible = (_visible != true);

                var formData = new FormData();
                formData.append('visible', visible);

                var req = {
                    method: 'POST',
                    url: config.apiUrl + '/admin/faqs/' + _faqId + '/visible',
                    headers: {
                        'Authorization': $scope.auth,
                        'Content-Type': undefined
                    },
                    data: angular.identity(formData)
                };

                BootstrapDialog.confirm({
                    title: 'FAQ DP 세팅' ,
                    message: 'FAQ ID: ' + _faqId + ', DP: ' + visible + '\n 위와 같이 세팅할까요?',
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
                                        message: 'FAQ DP 세팅 성공!'
                                    });
                                }

                                $scope.faqPageChanged();
                            });
                        }
                    }
                });
            };

            $scope.addFaqInfo = function() {

                var contents = $scope.faqContent;
                if(contents === '') {
                    BootstrapDialog.show({
                        type: BootstrapDialog.TYPE_DANGER,
                        message: '내용을 입력해 주세요!'
                    });
                    return;
                }

                if($scope.faqId == 0) {
                    var req = {
                        method: 'POST',
                        url: config.apiUrl + '/admin/faqs',
                        headers: {
                            'Authorization': $scope.auth,
                            'Content-Type': 'application/json'
                        },
                        data: {
                            'title': $scope.faqTitle,
                            'contents': contents
                        }
                    };

                    BootstrapDialog.confirm({
                        title: 'FAQ 등록 확인',
                        message: $scope.faqTitle + '\n 위 항목을 등록할까요?',
                        type: BootstrapDialog.TYPE_WARNING,
                        closable: true,
                        draggable: true,
                        btnCancelLabel: 'Cancel',
                        btnOKLabel: 'OK',
                        btnOKClass: 'btn-warning',
                        callback: function (result) {
                            if (result) {
                                $http(req).success(function (response) {
                                    if (response.success) {
                                        BootstrapDialog.show({
                                            message: 'FAQ 등록 성공!'
                                        });

                                        $scope.faqPageChanged();
                                    }
                                });
                            }
                        }
                    });
                } else {
                    var req = {
                        method: 'POST',
                        url: config.apiUrl + '/admin/faqs/' + $scope.faqId,
                        headers: {
                            'Authorization': $scope.auth,
                            'Content-Type': 'application/json'
                        },
                        data: {
                            'title': $scope.faqTitle,
                            'contents': contents
                        }
                    };

                    BootstrapDialog.confirm({
                        title: 'FAQ 수정 확인',
                        message: 'ID: ' + $scope.faqId + ', Title: ' + $scope.faqTitle + '\n 위 항목을 수정할까요?',
                        type: BootstrapDialog.TYPE_WARNING,
                        closable: true,
                        draggable: true,
                        btnCancelLabel: 'Cancel',
                        btnOKLabel: 'OK',
                        btnOKClass: 'btn-warning',
                        callback: function (result) {
                            if (result) {
                                $http(req).success(function (response) {
                                    if (response.success) {
                                        BootstrapDialog.show({
                                            message: 'FAQ 수정 성공!'
                                        });

                                        $scope.faqPageChanged();
                                    }
                                });
                            }
                        }
                    });
                }
            };

            $scope.removeFaq = function(_faqId) {

                var req = {
                    method: 'DELETE',
                    url: config.apiUrl + '/admin/faqs/' + _faqId,
                    headers: {
                        'Authorization': $scope.auth
                    }
                };

                BootstrapDialog.confirm({
                    title: 'FAQ 삭제' ,
                    message: 'FAQ ID: ' + _faqId + '\n 위 항목을 삭제할까요?',
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
                                        message: 'FAQ 삭제 성공!'
                                    });
                                }

                                $scope.faqPageChanged();
                            });
                        }
                    }
                });
            };

            $scope.mobileTab = function() {
                if($location.url() === '/support#mobile') {
                    $('#contact').removeClass('in active');
                    $('#notice').removeClass('in active');
                    $('#faq').removeClass('in active');
                    $('#mobile').addClass('in active');
                    return true;
                }
                else
                    return false;
            };

            $scope.getDeviceCodes = function() {
                $http.get(config.apiUrl + '/admin/codes/device',
                      { headers: {'Authorization': $scope.auth }}).success(function (response) {
                    if (response.success) {
                        $scope.androidCodes = response.data.androidCodes;
                        $scope.iosCodes = response.data.iosCodes;
                    }
                });
            };

            $scope.getDeviceCodes();

            $scope.editDeviceChildCodeValueById = function(_childCodeId, _childCodeName, _childCodeValue) {

                var req = {
                    method: 'POST',
                    url: config.apiUrl + '/admin/codes/' + _childCodeId,
                    headers: {
                        'Authorization': $scope.auth,
                        'Content-Type': 'application/json'
                    },
                    data: {
                        'value': _childCodeValue
                    }
                };

                BootstrapDialog.confirm({
                    title: '디바이스 코드 수정 확인' ,
                    message: _childCodeName + ': ' + _childCodeValue + '\n위 디바이스 코드를 수정하겠습니까?',
                    type: BootstrapDialog.TYPE_WARNING,
                    closable: true,
                    draggable: true,
                    btnCancelLabel: 'Cancel',
                    btnOKLabel: 'OK',
                    btnOKClass: 'btn-warning',
                    callback: function(result) {
                        if (result) {
                            $http(req).success(function (response) {
                                if(response.success) {
                                    BootstrapDialog.show({
                                        message: '수정 성공!'
                                    });
                                    $scope.getDeviceCodes();
                                }
                            });
                        }
                    }
                });
            };
        }
    ]);