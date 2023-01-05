var eim_url = "http://localhost:7889";
// var logout_url = "http://localhost:7889/account/logout";

var MAX_TOTAL_FILE_LENGTH_BYTE = 6291456; // 6Megabyte
var MAX_TOTAL_FILE_LENGTH_MEGABYTE = 6; // MB
var MAX_FILE_LENGTH = 3145728; // 1MB
var MAX_FILE_UPLOADER = 10; // max file uploader in queue
var MAX_FILE_UPLOADER_NPR = 10; // max file uploader in queue
var MNP_MAX_FILE_NAME_UPLOAD = 30; // max file uploader in queue

var MNP_MAX_FILE_SIZE = 204800; // 200KB
var isShowTokenRenewal = false;
var FIRST_PAGE;
var LAST_PAGE;
var NEXT_PAGE;
var PREV_PAGE;
var TOTAL_RECORD;
var DATA_TABLE_NOT_FOUND;
var LABEL_SEARCH;
const PASSWORD_PATTERN = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,15}$/;
var app_hust = angular.module('app-hust', ['ui.bootstrap', 'ngRoute', 'ngTable', 'pascalprecht.translate', 'ngCookies', 'ngValidate', 'angularjs-datetime-picker', 'angular-confirm', 'angularFileUpload', 'ngStorage', 'dx', 'ngSanitize', 'ui.select', 'ui.select.pagination.groups', 'TreeWidget', 'smart-table', 'ivh.treeview', 'ivh.dropdown', 'scrollable-table']);
app_hust.factory('LanguageService', function ($http, $translate, LANGUAGES) {
    return {
        getBy: function (language) {
            if (language == undefined) {
                language = Cookies.get('hust_lang');
            }
            var promise = $http.get('/i18n/' + language + '.json').then(function (response) {
                return LANGUAGES;
            });
            return promise;
        }
    };
});

app_hust.factory("interceptors", [function () {
    return {
        'response': function (response) {
            return response;
        },
    };
}]);

app_hust.config(['$httpProvider', function ($httpProvider) {
    $httpProvider.defaults.useXDomain = true;
    delete $httpProvider.defaults.headers.common['X-Requested-With'];
    $httpProvider.interceptors.push('interceptors');
}]);

app_hust.config(function (ivhTreeviewOptionsProvider) {
    ivhTreeviewOptionsProvider.set({
        defaultSelectedState: false,
        validate: true,
        twistieExpandedTpl: '<span class="arrow-down"></span>',
        twistieCollapsedTpl: '<span class="arrow-right"></span>',
        twistieLeafTpl: '&nbsp;&nbsp;',
    });
});

app_hust.config(function ($routeProvider, $validatorProvider, $translateProvider) {
    // var lang = Cookies.get('hust_lang');
    // $translateProvider.translations("en", en_lang);
    // $translateProvider.translations("en", vi_lang);
    $translateProvider.translations("vi", vi_lang);
    $translateProvider.preferredLanguage('vi');
    $translateProvider.fallbackLanguage('vi');
    $validatorProvider.setDefaults({
        errorElement: 'span', errorClass: 'help-block', highlight: function (element) {
            $(element).parent().addClass('has-error');
        }, unhighlight: function (element) {
            $(element).parent().removeClass('has-error');
        }, success: function (label) {
            label.parent().removeClass('has-error');
        }
    });
});

function run($rootScope, $http, $location, $localStorage, $window, $interval, $uibModal, $translate) {
    $rootScope.expToken = function () {
        var now = (new Date()).getTime();
        var exp = Math.floor(($localStorage.clientContext.createTimeClient - now) / 1000) + $localStorage.clientContext.interval;
        return exp;
    }

    if ($localStorage.clientContext && $rootScope.expToken() > 10) {
        $http.defaults.headers.common.Authorization = $localStorage.clientContext.transId;
        $http.defaults.headers.common['shopId'] = $localStorage.clientContext.shop.shopId;
        $http.defaults.headers.common['user'] = $localStorage.clientContext.username;
        $.ajaxSetup({
            headers: {
                'Content-Type': 'application/json;charset=utf-8',
                "Authorization" : $localStorage.clientContext.transId,
                "shopId" : $localStorage.clientContext.shop.shopId,
                "user" : $localStorage.clientContext.username
            }
        });
        $.each($localStorage.clientContext.sitemaps, function (index, value) {
            var func = value.strCode
            if (page.func == func) {
                var privileges = value.privileges;
                $.each(privileges, function (p_index, p_value) {
                    var c = "*[data-action='" + p_value.code + "']";
                    var item = {}
                    item[c] = {
                        display: "inline-block !important"
                    };
                    $.injectCSS(item);
                })
            }
        })
    }

    // if ($localStorage.clientContext && $rootScope.expToken <= 0) {
    //     $window.location.href = '/account/login';
    // }

    // $rootScope.$on('$locationChangeStart', function (event, next, current) {
    //     var publicPages = ['/account/login'];
    //     var restrictedPage = publicPages.indexOf($location.path()) === -1;
    //     if (restrictedPage && !$localStorage.clientContext) {
    //         $window.location.href = '/account/login';
    //     }
    // });
    $rootScope.checAction = function (p_action) {
        var result = false;
        $.each($localStorage.clientContext.sitemaps, function (index, value) {
            var func = value.strCode
            if (page.func == func) {
                var privileges = value.privileges;
                $.each(privileges, function (p_index, p_value) {
                    if (p_value.code == p_action) {
                        result = true;
                    }
                })
            }
        })
        return result;
    }

    $rootScope.downloadFileUploadClient = function (item) {
        var fileData = new Blob([item._file]);
        var fileName = item.file.name;
        download(fileData, fileName, 'application/json');
    }

    $rootScope.viewFileImageClient = function (item, idModal, classImgPreview) {
        $('.' + classImgPreview).attr('src', item.dataImg);
        $('#' + idModal).modal('show');
    }

    $rootScope.removeZezoFirstPosition = function (e) {

        var inputValue = $.trim(e.target.value);
        var valueWithoutZezo = "";
        var keyCode = (e.keyCode ? e.keyCode : e.which);
        if ((keyCode == 96 || keyCode == 48) && inputValue.length == 0) {
            e.preventDefault();
        }

        if (inputValue.startsWith("0")) {
            valueWithoutZezo = inputValue.substring(1, inputValue.length);
            e.target.value = valueWithoutZezo;
        }
        ;

        // neu bat dau = 84 va lon hon 9, cat bo dau so 84
        if (inputValue.startsWith("84") && inputValue.length > 9) {
            valueWithoutZezo = inputValue.substring(2, inputValue.length);
            e.target.value = valueWithoutZezo;
        }
        ;

        if (inputValue.startsWith("+84")) {
            valueWithoutZezo = inputValue.substring(3, inputValue.length);
            e.target.value = valueWithoutZezo;
        }
        ;

    }
    // checktimeout = $interval(function () {
    //     //console.log("check",(($rootScope.expToken() || 0) - 120));
    //     if ($localStorage.clientContext.isLogoutAll) {
    //         $window.location.href = '/account/login';
    //     }
    //     if ((($rootScope.expToken() || 0) - 120) <= 120) {
    //         if (!$localStorage.clientContext.isRefToken) {
    //             let payload = {
    //                 /*'transId': $localStorage.clientContext.transId, 'userName': $localStorage.clientContext.username*/
    //             }
    //             $http({
    //                 method: 'POST', url: eim_url + "/refreshToken", headers: {
    //                     'Content-Type': 'application/json'
    //                 }, data: payload
    //             }).success(function (res) {
    //                 if (res.createTime == '' || res.createTime == 'null' || res.createTime == null) {
    //                     $window.location.href = '/account/login';
    //                     $localStorage.clientContext.isLogoutAll = true;
    //                 } else {
    //                     $localStorage.clientContext.createTimeClient = (new Date()).getTime();
    //                     $localStorage.clientContext.interval = res.interval;
    //                     $localStorage.clientContext.createTime = res.createTime;
    //                     $localStorage.clientContext.isRefToken = true;
    //                     $localStorage.clientContext.transId = res?.transId;
    //                 }
    //             }).error(function (qwe) {
    //                 $window.location.href = '/account/login';
    //                 $localStorage.clientContext.isLogoutAll = true;
    //             });
    //         } else {
    //             //$window.location.href = '/account/login';
    //             if (!isShowTokenRenewal) {
    //                 isShowTokenRenewal = true;
    //                 bootboxConfirmCountDown('idCountDown', $translate.instant("hust.lable.hust.name"), 'Phiên làm việc sắp hết hạn,sau' + ' <span id="timer" style="font-weight: bold"></span> ' + 'phút hệ thống sẽ tự thoát.Bạn có muốn tiếp tục không?', async () => {
    //                     let payload = {
    //                        /* 'transId': $localStorage.clientContext.transId,
    //                         'userName': $localStorage.clientContext.username*/
    //                     }
    //                     $http({
    //                         method: 'POST', url: eim_url + "/refreshToken", headers: {
    //                             'Content-Type': 'application/json'
    //                         }, data: payload
    //                     }).success(function (res) {
    //                         if (res.createTime == '' || res.createTime == 'null' || res.createTime == null) {
    //                             isShowTokenRenewal = false;
    //                             $window.location.href = '/account/login';
    //                             $localStorage.clientContext.isLogoutAll = true;
    //                         } else {
    //                             $localStorage.clientContext.createTimeClient = (new Date()).getTime();
    //                             $localStorage.clientContext.interval = res.interval;
    //                             $localStorage.clientContext.createTime = res.createTime;
    //                             $localStorage.clientContext.isRefToken = true;
    //                             isShowTokenRenewal = false;
    //                             $localStorage.clientContext.transId = res?.transId;
    //                         }
    //                     }).error(function (qwe) {
    //                         isShowTokenRenewal = false;
    //                         $window.location.href = '/account/login';
    //                         $localStorage.clientContext.isLogoutAll = true;
    //                     });
    //                 }, () => {
    //                     isShowTokenRenewal = false;
    //                     $window.location.href = '/account/login';
    //                     $localStorage.clientContext.isLogoutAll = true;
    //                 });
    //
    //             } else {
    //                 display = document.querySelector('#timer');
    //                 let minutes = parseInt((($rootScope.expToken() || 0) - 120) / 60, 10)
    //                 let seconds = parseInt((($rootScope.expToken() || 0) - 120) % 60, 10);
    //                 minutes = minutes < 10 ? "0" + minutes : minutes;
    //                 seconds = seconds < 10 ? "0" + seconds : seconds;
    //                 display.textContent = minutes + ":" + seconds;
    //                 if ((($rootScope.expToken() || 0) - 120) <= 0) {
    //                     isShowTokenRenewal = false;
    //                     $localStorage.clientContext.isLogoutAll = true;
    //                     $window.location.href = '/account/login';
    //                 }
    //
    //             }
    //         }
    //     } else {
    //         $('#idCountDown').modal('hide');
    //         isShowTokenRenewal = false;
    //     }
    // }, 1000);
}

app_hust.run(run);

app_hust.filter('attrFilter', function () {
    return function (attributes, key) {
        var result = "";
        $.each(attributes, function (index, item) {
            if (item.code == key) {
                result = item.value;
            }
        });
        return result;
    };
});

app_hust.controller('ctrl-topfragment', function ($scope, $rootScope, $translate, $compile, $timeout, $uibModal, $location, $window, $filter, $localStorage, $http, $interval) {

    $rootScope.vaidChangePass = {
        rules: {
            Password: {
                required: true, maxlength: 255
            }, NewPassword: {
                required: true, maxlength: 255
            }, ReNewPassword: {
                required: true, equalTo: "#NewPassword"
            }
        }, messages: {
            Password: {
                required: "Yêu cầu nhập mật khẩu cũ.", maxlength: "Mật khẩu cũ không vượt quá 255 ký tự."
            }, NewPassword: {
                required: "Yêu cầu nhập mật khẩu mới.", maxlength: "Mật khẩu mới không vượt quá 255 ký tự."
            }, ReNewPassword: {
                required: "Yêu cầu nhập xác mật khẩu mới.", equalTo: "Mật khẩu xác nhận không đúng."
            }
        }
    }

    $scope.show_flag = false;
    $scope.show_alert = false;
    $scope.fullName = "HUSTER";
    $scope.shopName = "IT-LTU";
    $scope.organization = "Đại học Bách Khoa - HUST";
    // $scope.logout = function () {
    //     delete $localStorage.clientContext;
    //     $http.defaults.headers.common.Authorization = '';
    //     $http({
    //         method: 'POST', url: logout_url
    //     }).success(function (response) {
    //         window.location.href = response.loginpage;
    //     }).error(function (qwe) {
    //         $window.location.href = '/account/login';
    //     });
    // }
    // $scope.changepass = function () {
    //     var modalInstance = $uibModal.open({
    //         animation: true,
    //         templateUrl: 'changepassword.html',
    //         controller: function ($scope, $rootScope, $compile, $uibModal, $confirm, $uibModalInstance, $localStorage) {
    //             $scope.username = $localStorage.clientContext.username;
    //             $scope.save = function () {
    //                 if ($scope.changepassform.validate()) {
    //                     let password = $scope.model.newPass;
    //                     let patternPassword = password.match(PASSWORD_PATTERN); // is format
    //                     if (patternPassword == null) {
    //                         bootboxAlertFocus("Mật khẩu tối thiểu 8 và tối đa 15 ký tự, ít nhất một chữ cái viết hoa, một chữ cái viết thường, một số và một ký tự đặc biệt!", "", $translate.instant("hust.lable.hust.name"), "");
    //                     } else {
    //                         let changPassParam = {
    //                             'oldPass': encryptData($scope.model.oldPass),
    //                             'newPass': encryptData($scope.model.newPass)
    //                         }
    //                         $http({
    //                             method: 'POST', url: eim_url + "/changePass", data: changPassParam
    //                         }).success(function (res) {
    //                             if (res.errorMessage != null) {
    //                                 bootboxAlertFocus(res.errorMessage, "", $translate.instant("hust.lable.hust.name"), "");
    //                             } else {
    //                                 $uibModalInstance.dismiss('cancel');
    //                                 bootboxAlertFocus("Đổi mật khẩu thành công.", "", $translate.instant("hust.lable.hust.name"), "");
    //                             }
    //
    //                         }).error(function (qwe) {
    //                             bootboxAlertFocus("Lỗi kết nối dịch vụ.", "", $translate.instant("hust.lable.hust.name"), "");
    //                         });
    //                     }
    //                 }
    //             }
    //             $scope.cancel = function () {
    //                 $uibModalInstance.dismiss('cancel');
    //
    //             }
    //
    //         },
    //         backdrop: true,
    //         size: '70'
    //     });
    //     modalInstance.result.then(function (d) {
    //         $scope.reload();
    //     }, function () {
    //     });
    // }
    $scope.lock = function () {

    }
    $scope.profile = function () {

    }

    // TU DIEN DUNG CHUGN
    FIRST_PAGE = $translate.instant('hust.common.paging.first');
    LAST_PAGE = $translate.instant('hust.common.paging.last');
    NEXT_PAGE = $translate.instant('hust.common.paging.next');
    PREV_PAGE = $translate.instant('hust.common.paging.prev');
    TOTAL_RECORD = $translate.instant('hust.common.tableRecord.total');
    DATA_TABLE_NOT_FOUND = $translate.instant('hust.common.data.not.found');
    LABEL_SEARCH = $translate.instant('hust.common.filter');
});
app_hust.controller('ctrl-navfragment', function ($scope, $rootScope, $translate, $compile, $timeout, $uibModal, $location, $window, $filter, $localStorage, $http) {
    $scope.searchInput = '';
    $scope.currentPath = $window.location.pathname;
    $scope.sitemapsOld = [];

    $scope.sitemaps = [
        {
            "id": 1,
            "strActionCommand": null,
            "blIsSeparated": false,
            "strUrl": "/",
            "strIcon": "fa fa-home",
            "strClass": null,
            "strCode": "HOME01",
            "strName": "Trang chủ",
            "strNameEn": "",
            "strDescription": null,
            "strType": "DEFAULT",
            "blEnableUrl": false,
            "blSetAjax": false,
            "blShowSub": false,
            "intParentId": null,
            "parent": null,
            "childrent": [],
            "attributes": [
                {
                    "id": 8,
                    "code": "SITEMAP_ICON",
                    "name": null,
                    "value": "fa fa-home",
                    "status": null,
                    "groupId": null,
                    "groupCode": null,
                    "groupName": null,
                    "formatId": null,
                    "formatCode": null,
                    "formatName": null
                },
                {
                    "id": 1,
                    "code": "SITEMAP_URL",
                    "name": null,
                    "value": "/",
                    "status": null,
                    "groupId": null,
                    "groupCode": null,
                    "groupName": null,
                    "formatId": null,
                    "formatCode": null,
                    "formatName": null
                }
            ],
            "privileges": [
                {
                    "id": 0,
                    "code": "EXECUTE",
                    "name": null
                },
                {
                    "id": 0,
                    "code": "DELETE",
                    "name": null
                },
                {
                    "id": 0,
                    "code": "UPDATE",
                    "name": null
                },
                {
                    "id": 0,
                    "code": "INSERT",
                    "name": null
                },
                {
                    "id": 0,
                    "code": "VIEW",
                    "name": null
                },
                {
                    "id": 0,
                    "code": "ACCESS",
                    "name": null
                }
            ],
            "strStatus": "1",
            "ord": 1
        },
        {
            "id": 2,
            "strActionCommand": null,
            "blIsSeparated": false,
            "strUrl": null,
            "strIcon": "fa fa-th",
            "strClass": null,
            "strCode": "REPORT_01",
            "strName": "Báo cáo",
            "strNameEn": "",
            "strDescription": null,
            "strType": "DEFAULT",
            "blEnableUrl": false,
            "blSetAjax": false,
            "blShowSub": false,
            "intParentId": null,
            "parent": null,
            "childrent": [
                {
                    "id": 10,
                    "strActionCommand": null,
                    "blIsSeparated": false,
                    "strUrl": "/hust/interactive-actor-point",
                    "strIcon": "  ",
                    "strClass": null,
                    "strCode": "REPORT_001",
                    "strName": "Bảng tính toán điểm các tác nhân (Actor) tương tác",
                    "strNameEn": "",
                    "strDescription": null,
                    "strType": "DEFAULT",
                    "blEnableUrl": false,
                    "blSetAjax": false,
                    "blShowSub": false,
                    "intParentId": 2,
                    "parent": null,
                    "childrent": [],
                    "attributes": [
                        {
                            "id": 8,
                            "code": "SITEMAP_ICON",
                            "name": null,
                            "value": "  ",
                            "status": null,
                            "groupId": null,
                            "groupCode": null,
                            "groupName": null,
                            "formatId": null,
                            "formatCode": null,
                            "formatName": null
                        },
                        {
                            "id": 1,
                            "code": "SITEMAP_URL",
                            "name": null,
                            "value": "/hust/interactive-actor-point",
                            "status": null,
                            "groupId": null,
                            "groupCode": null,
                            "groupName": null,
                            "formatId": null,
                            "formatCode": null,
                            "formatName": null
                        }
                    ],
                    "privileges": [
                        {
                            "id": 0,
                            "code": "EXECUTE",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "DELETE",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "INSERT",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "UPDATE",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "VIEW",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "ACCESS",
                            "name": null
                        }
                    ],
                    "strStatus": "1",
                    "ord": 1
                },
                {
                    "id": 11,
                    "strActionCommand": null,
                    "blIsSeparated": false,
                    "strUrl": "/hust/use-cases-point",
                    "strIcon": null,
                    "strClass": null,
                    "strCode": "REPORT_002",
                    "strName": "Bảng tính toán điểm các trường hợp sử dụng",
                    "strNameEn": "",
                    "strDescription": null,
                    "strType": "DEFAULT",
                    "blEnableUrl": false,
                    "blSetAjax": false,
                    "blShowSub": false,
                    "intParentId": 2,
                    "parent": null,
                    "childrent": [],
                    "attributes": [
                        {
                            "id": 1,
                            "code": "SITEMAP_URL",
                            "name": null,
                            "value": "/hust/use-cases-point",
                            "status": null,
                            "groupId": null,
                            "groupCode": null,
                            "groupName": null,
                            "formatId": null,
                            "formatCode": null,
                            "formatName": null
                        }
                    ],
                    "privileges": [
                        {
                            "id": 0,
                            "code": "EXECUTE",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "DELETE",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "UPDATE",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "INSERT",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "VIEW",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "ACCESS",
                            "name": null
                        }
                    ],
                    "strStatus": "1",
                    "ord": 2,
                },
                {
                    "id": 12,
                    "strActionCommand": null,
                    "blIsSeparated": false,
                    "strUrl": "/hust/software-price-determination",
                    "strIcon": null,
                    "strClass": null,
                    "strCode": "REPORT_003",
                    "strName": "Bảng xác định giá phần mềm",
                    "strNameEn": "",
                    "strDescription": null,
                    "strType": "DEFAULT",
                    "blEnableUrl": false,
                    "blSetAjax": false,
                    "blShowSub": false,
                    "intParentId": 2,
                    "parent": null,
                    "childrent": [],
                    "attributes": [
                        {
                            "id": 1,
                            "code": "SITEMAP_URL",
                            "name": null,
                            "value": "/hust/software-price-determination",
                            "status": null,
                            "groupId": null,
                            "groupCode": null,
                            "groupName": null,
                            "formatId": null,
                            "formatCode": null,
                            "formatName": null
                        }
                    ],
                    "privileges": [
                        {
                            "id": 0,
                            "code": "EXECUTE",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "DELETE",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "UPDATE",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "INSERT",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "VIEW",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "ACCESS",
                            "name": null
                        }
                    ],
                    "strStatus": "1",
                    "ord": 2,
                },
                {
                    "id": 13,
                    "strActionCommand": null,
                    "blIsSeparated": false,
                    "strUrl": "/hust/technology-comprehension-calculation",
                    "strIcon": null,
                    "strClass": null,
                    "strCode": "REPORT_004",
                    "strName": "Bảng tính toán hệ số phức tạp kỹ thuật-công nghệ",
                    "strNameEn": "",
                    "strDescription": null,
                    "strType": "DEFAULT",
                    "blEnableUrl": false,
                    "blSetAjax": false,
                    "blShowSub": false,
                    "intParentId": 2,
                    "parent": null,
                    "childrent": [],
                    "attributes": [
                        {
                            "id": 1,
                            "code": "SITEMAP_URL",
                            "name": null,
                            "value": "/hust/technology-comprehension-calculation",
                            "status": null,
                            "groupId": null,
                            "groupCode": null,
                            "groupName": null,
                            "formatId": null,
                            "formatCode": null,
                            "formatName": null
                        }
                    ],
                    "privileges": [
                        {
                            "id": 0,
                            "code": "EXECUTE",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "DELETE",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "UPDATE",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "INSERT",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "VIEW",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "ACCESS",
                            "name": null
                        }
                    ],
                    "strStatus": "1",
                    "ord": 2,
                },
                {
                    "id": 14,
                    "strActionCommand": null,
                    "blIsSeparated": false,
                    "strUrl": "/hust/he-so-PTMT-KN-noi-suy",
                    "strIcon": null,
                    "strClass": null,
                    "strCode": "REPORT_005",
                    "strName": "Hệ số PTMT-KN-nội suy",
                    "strNameEn": "",
                    "strDescription": null,
                    "strType": "DEFAULT",
                    "blEnableUrl": false,
                    "blSetAjax": false,
                    "blShowSub": false,
                    "intParentId": 2,
                    "parent": null,
                    "childrent": [],
                    "attributes": [
                        {
                            "id": 1,
                            "code": "SITEMAP_URL",
                            "name": null,
                            "value": "/hust/he-so-PTMT-KN-noi-suy",
                            "status": null,
                            "groupId": null,
                            "groupCode": null,
                            "groupName": null,
                            "formatId": null,
                            "formatCode": null,
                            "formatName": null
                        }
                    ],
                    "privileges": [
                        {
                            "id": 0,
                            "code": "EXECUTE",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "DELETE",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "UPDATE",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "INSERT",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "VIEW",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "ACCESS",
                            "name": null
                        }
                    ],
                    "strStatus": "1",
                    "ord": 2,
                },
                {
                    "id": 15,
                    "strActionCommand": null,
                    "blIsSeparated": false,
                    "strUrl": "/hust/personal-salary",
                    "strIcon": null,
                    "strClass": null,
                    "strCode": "REPORT_006",
                    "strName": "Bảng tính lương bình quân/người/giờ",
                    "strNameEn": "",
                    "strDescription": null,
                    "strType": "DEFAULT",
                    "blEnableUrl": false,
                    "blSetAjax": false,
                    "blShowSub": false,
                    "intParentId": 2,
                    "parent": null,
                    "childrent": [],
                    "attributes": [
                        {
                            "id": 1,
                            "code": "SITEMAP_URL",
                            "name": null,
                            "value": "/hust/personal-salary",
                            "status": null,
                            "groupId": null,
                            "groupCode": null,
                            "groupName": null,
                            "formatId": null,
                            "formatCode": null,
                            "formatName": null
                        }
                    ],
                    "privileges": [
                        {
                            "id": 0,
                            "code": "EXECUTE",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "DELETE",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "UPDATE",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "INSERT",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "VIEW",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "ACCESS",
                            "name": null
                        }
                    ],
                    "strStatus": "1",
                    "ord": 2,
                },
            ],
            "attributes": [
                {
                    "id": 8,
                    "code": "SITEMAP_ICON",
                    "name": null,
                    "value": "fa fa-th",
                    "status": null,
                    "groupId": null,
                    "groupCode": null,
                    "groupName": null,
                    "formatId": null,
                    "formatCode": null,
                    "formatName": null
                }
            ],
            "privileges": [
                {
                    "id": 0,
                    "code": "EXECUTE",
                    "name": null
                },
                {
                    "id": 0,
                    "code": "DELETE",
                    "name": null
                },
                {
                    "id": 0,
                    "code": "UPDATE",
                    "name": null
                },
                {
                    "id": 0,
                    "code": "INSERT",
                    "name": null
                },
                {
                    "id": 0,
                    "code": "VIEW",
                    "name": null
                },
                {
                    "id": 0,
                    "code": "ACCESS",
                    "name": null
                }
            ],
            "strStatus": "1",
            "ord": 2,
        },
    ];
    $scope.sitemapsOld = [
        {
            "id": 1,
            "strActionCommand": null,
            "blIsSeparated": false,
            "strUrl": "/",
            "strIcon": "fa fa-home",
            "strClass": null,
            "strCode": "HOME01",
            "strName": "Trang chủ",
            "strNameEn": "",
            "strDescription": null,
            "strType": "DEFAULT",
            "blEnableUrl": false,
            "blSetAjax": false,
            "blShowSub": false,
            "intParentId": null,
            "parent": null,
            "childrent": [],
            "attributes": [
                {
                    "id": 8,
                    "code": "SITEMAP_ICON",
                    "name": null,
                    "value": "fa fa-home",
                    "status": null,
                    "groupId": null,
                    "groupCode": null,
                    "groupName": null,
                    "formatId": null,
                    "formatCode": null,
                    "formatName": null
                },
                {
                    "id": 1,
                    "code": "SITEMAP_URL",
                    "name": null,
                    "value": "/",
                    "status": null,
                    "groupId": null,
                    "groupCode": null,
                    "groupName": null,
                    "formatId": null,
                    "formatCode": null,
                    "formatName": null
                }
            ],
            "privileges": [
                {
                    "id": 0,
                    "code": "EXECUTE",
                    "name": null
                },
                {
                    "id": 0,
                    "code": "DELETE",
                    "name": null
                },
                {
                    "id": 0,
                    "code": "UPDATE",
                    "name": null
                },
                {
                    "id": 0,
                    "code": "INSERT",
                    "name": null
                },
                {
                    "id": 0,
                    "code": "VIEW",
                    "name": null
                },
                {
                    "id": 0,
                    "code": "ACCESS",
                    "name": null
                }
            ],
            "strStatus": "1",
            "ord": 1
        },
        {
            "id": 2,
            "strActionCommand": null,
            "blIsSeparated": false,
            "strUrl": null,
            "strIcon": "fa fa-th",
            "strClass": null,
            "strCode": "REPORT_01",
            "strName": "Báo cáo",
            "strNameEn": "",
            "strDescription": null,
            "strType": "DEFAULT",
            "blEnableUrl": false,
            "blSetAjax": false,
            "blShowSub": false,
            "intParentId": null,
            "parent": null,
            "childrent": [
                {
                    "id": 10,
                    "strActionCommand": null,
                    "blIsSeparated": false,
                    "strUrl": "/hust/interactive-actor-point",
                    "strIcon": "  ",
                    "strClass": null,
                    "strCode": "REPORT_001",
                    "strName": "Bảng tính toán điểm các tác nhân (Actor) tương tác",
                    "strNameEn": "",
                    "strDescription": null,
                    "strType": "DEFAULT",
                    "blEnableUrl": false,
                    "blSetAjax": false,
                    "blShowSub": false,
                    "intParentId": 2,
                    "parent": null,
                    "childrent": [],
                    "attributes": [
                        {
                            "id": 8,
                            "code": "SITEMAP_ICON",
                            "name": null,
                            "value": "  ",
                            "status": null,
                            "groupId": null,
                            "groupCode": null,
                            "groupName": null,
                            "formatId": null,
                            "formatCode": null,
                            "formatName": null
                        },
                        {
                            "id": 1,
                            "code": "SITEMAP_URL",
                            "name": null,
                            "value": "/hust/interactive-actor-point",
                            "status": null,
                            "groupId": null,
                            "groupCode": null,
                            "groupName": null,
                            "formatId": null,
                            "formatCode": null,
                            "formatName": null
                        }
                    ],
                    "privileges": [
                        {
                            "id": 0,
                            "code": "EXECUTE",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "DELETE",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "INSERT",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "UPDATE",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "VIEW",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "ACCESS",
                            "name": null
                        }
                    ],
                    "strStatus": "1",
                    "ord": 1
                },
                {
                    "id": 11,
                    "strActionCommand": null,
                    "blIsSeparated": false,
                    "strUrl": "/hust/use-cases-point",
                    "strIcon": null,
                    "strClass": null,
                    "strCode": "REPORT_002",
                    "strName": "Bảng tính toán điểm các trường hợp sử dụng",
                    "strNameEn": "",
                    "strDescription": null,
                    "strType": "DEFAULT",
                    "blEnableUrl": false,
                    "blSetAjax": false,
                    "blShowSub": false,
                    "intParentId": 2,
                    "parent": null,
                    "childrent": [],
                    "attributes": [
                        {
                            "id": 1,
                            "code": "SITEMAP_URL",
                            "name": null,
                            "value": "/hust/use-cases-point",
                            "status": null,
                            "groupId": null,
                            "groupCode": null,
                            "groupName": null,
                            "formatId": null,
                            "formatCode": null,
                            "formatName": null
                        }
                    ],
                    "privileges": [
                        {
                            "id": 0,
                            "code": "EXECUTE",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "DELETE",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "UPDATE",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "INSERT",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "VIEW",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "ACCESS",
                            "name": null
                        }
                    ],
                    "strStatus": "1",
                    "ord": 2,
                },
                {
                    "id": 12,
                    "strActionCommand": null,
                    "blIsSeparated": false,
                    "strUrl": "/hust/software-price-determination",
                    "strIcon": null,
                    "strClass": null,
                    "strCode": "REPORT_003",
                    "strName": "Bảng xác định giá phần mềm",
                    "strNameEn": "",
                    "strDescription": null,
                    "strType": "DEFAULT",
                    "blEnableUrl": false,
                    "blSetAjax": false,
                    "blShowSub": false,
                    "intParentId": 2,
                    "parent": null,
                    "childrent": [],
                    "attributes": [
                        {
                            "id": 1,
                            "code": "SITEMAP_URL",
                            "name": null,
                            "value": "/hust/software-price-determination",
                            "status": null,
                            "groupId": null,
                            "groupCode": null,
                            "groupName": null,
                            "formatId": null,
                            "formatCode": null,
                            "formatName": null
                        }
                    ],
                    "privileges": [
                        {
                            "id": 0,
                            "code": "EXECUTE",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "DELETE",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "UPDATE",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "INSERT",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "VIEW",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "ACCESS",
                            "name": null
                        }
                    ],
                    "strStatus": "1",
                    "ord": 2,
                },
                {
                    "id": 13,
                    "strActionCommand": null,
                    "blIsSeparated": false,
                    "strUrl": "/hust/technology-comprehension-calculation",
                    "strIcon": null,
                    "strClass": null,
                    "strCode": "REPORT_004",
                    "strName": "Bảng tính toán hệ số phức tạp kỹ thuật-công nghệ",
                    "strNameEn": "",
                    "strDescription": null,
                    "strType": "DEFAULT",
                    "blEnableUrl": false,
                    "blSetAjax": false,
                    "blShowSub": false,
                    "intParentId": 2,
                    "parent": null,
                    "childrent": [],
                    "attributes": [
                        {
                            "id": 1,
                            "code": "SITEMAP_URL",
                            "name": null,
                            "value": "/hust/technology-comprehension-calculation",
                            "status": null,
                            "groupId": null,
                            "groupCode": null,
                            "groupName": null,
                            "formatId": null,
                            "formatCode": null,
                            "formatName": null
                        }
                    ],
                    "privileges": [
                        {
                            "id": 0,
                            "code": "EXECUTE",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "DELETE",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "UPDATE",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "INSERT",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "VIEW",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "ACCESS",
                            "name": null
                        }
                    ],
                    "strStatus": "1",
                    "ord": 2,
                },
                {
                    "id": 14,
                    "strActionCommand": null,
                    "blIsSeparated": false,
                    "strUrl": "/hust/he-so-PTMT-KN-noi-suy",
                    "strIcon": null,
                    "strClass": null,
                    "strCode": "REPORT_005",
                    "strName": "Hệ số PTMT-KN-nội suy",
                    "strNameEn": "",
                    "strDescription": null,
                    "strType": "DEFAULT",
                    "blEnableUrl": false,
                    "blSetAjax": false,
                    "blShowSub": false,
                    "intParentId": 2,
                    "parent": null,
                    "childrent": [],
                    "attributes": [
                        {
                            "id": 1,
                            "code": "SITEMAP_URL",
                            "name": null,
                            "value": "/hust/he-so-PTMT-KN-noi-suy",
                            "status": null,
                            "groupId": null,
                            "groupCode": null,
                            "groupName": null,
                            "formatId": null,
                            "formatCode": null,
                            "formatName": null
                        }
                    ],
                    "privileges": [
                        {
                            "id": 0,
                            "code": "EXECUTE",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "DELETE",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "UPDATE",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "INSERT",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "VIEW",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "ACCESS",
                            "name": null
                        }
                    ],
                    "strStatus": "1",
                    "ord": 2,
                },
                {
                    "id": 15,
                    "strActionCommand": null,
                    "blIsSeparated": false,
                    "strUrl": "/hust/personal-salary",
                    "strIcon": null,
                    "strClass": null,
                    "strCode": "REPORT_006",
                    "strName": "Bảng tính lương bình quân/người/giờ",
                    "strNameEn": "",
                    "strDescription": null,
                    "strType": "DEFAULT",
                    "blEnableUrl": false,
                    "blSetAjax": false,
                    "blShowSub": false,
                    "intParentId": 2,
                    "parent": null,
                    "childrent": [],
                    "attributes": [
                        {
                            "id": 1,
                            "code": "SITEMAP_URL",
                            "name": null,
                            "value": "/hust/personal-salary",
                            "status": null,
                            "groupId": null,
                            "groupCode": null,
                            "groupName": null,
                            "formatId": null,
                            "formatCode": null,
                            "formatName": null
                        }
                    ],
                    "privileges": [
                        {
                            "id": 0,
                            "code": "EXECUTE",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "DELETE",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "UPDATE",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "INSERT",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "VIEW",
                            "name": null
                        },
                        {
                            "id": 0,
                            "code": "ACCESS",
                            "name": null
                        }
                    ],
                    "strStatus": "1",
                    "ord": 2,
                },
            ],
            "attributes": [
                {
                    "id": 8,
                    "code": "SITEMAP_ICON",
                    "name": null,
                    "value": "fa fa-th",
                    "status": null,
                    "groupId": null,
                    "groupCode": null,
                    "groupName": null,
                    "formatId": null,
                    "formatCode": null,
                    "formatName": null
                }
            ],
            "privileges": [
                {
                    "id": 0,
                    "code": "EXECUTE",
                    "name": null
                },
                {
                    "id": 0,
                    "code": "DELETE",
                    "name": null
                },
                {
                    "id": 0,
                    "code": "UPDATE",
                    "name": null
                },
                {
                    "id": 0,
                    "code": "INSERT",
                    "name": null
                },
                {
                    "id": 0,
                    "code": "VIEW",
                    "name": null
                },
                {
                    "id": 0,
                    "code": "ACCESS",
                    "name": null
                }
            ],
            "strStatus": "1",
            "ord": 2,
        },
    ];

    $scope.active_nav = function (subitem) {
        if (subitem.strUrl == $scope.currentPath) {
            $timeout(function () {
                $('#navitem-' + subitem.id).parent().css('display', 'block');
                $('#navitem-' + subitem.id).parent().parents('.nav-item').addClass('open');
            });
        }
    }
    $scope.getListMenu = function () {
        let arrMenu = [];
        $scope.sitemapsOld.map(item => {
            arrMenu.push(item)
            if (item.childrent.length > 0) {

                item.childrent.map(i => {

                    if (i.childrent.length > 0) {

                        i.childrent.map(k => {
                            arrMenu.push(k)
                        });
                    }
                    arrMenu.push(i)
                });
            }
        });
        return arrMenu;
    }
    $scope.onSearchMenu = function () {
        if ($scope.searchInput) {
            let arrSearch = [];
            let arrSearchTwo = [];
            let arrNew = [];
            let arrMenu = $scope.getListMenu();
            arrMenu.map(item => {
                if (item.strName.toUpperCase().includes($scope.searchInput.toUpperCase())) {
                    arrSearch.push(item);
                    arrSearchTwo.push(item);
                }
            })
            arrSearch.map(item => {
                let flag = false;
                arrSearchTwo.map(row => {
                    if (item.intParentId == row.id) {
                        flag = true
                    }
                })
                if (!flag) {
                    arrNew.push(item)
                }
            })
            $scope.sitemaps = arrNew;
            const boxes = document.querySelectorAll('.sub-menu');

            boxes.forEach(box => {
                box.style.display = 'block';
            });
        } else {
            $scope.sitemaps = $scope.sitemapsOld
            const boxes = document.querySelectorAll('.sub-menu');

            boxes.forEach(box => {
                box.style.display = 'none';
            });
        }
    }

    dowloadFileFromURL = function (data) {
        overLoading("Downloading file...");
        var url = eim_url + '/bs/downLoadFileFromUrl';
        $http.post(url, data, {
            responseType: 'arraybuffer'
        }).success(function (response, status, headers, config) {
            overLoading("Downloading file...");
            download(new Blob([response]), headers('FileNameDownload'), headers["content-type"]);
            closeOverLay();
        }).error(function (response) {
            console.log(response);
            closeOverLay();
        });
    }

});

app_hust.directive('ngThumb', ['$window', function ($window) {
    var helper = {
        support: !!($window.FileReader && $window.CanvasRenderingContext2D), isFile: function (item) {
            return angular.isObject(item) && item instanceof $window.File;
        }, isImage: function (file) {
            var type = '|' + file.type.slice(file.type.lastIndexOf('/') + 1) + '|';
            return '|jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1;
        }
    };

    return {
        restrict: 'A', template: '<canvas/>', link: function (scope, element, attributes) {
            if (!helper.support) return;

            var params = scope.$eval(attributes.ngThumb);

            if (!helper.isFile(params.file)) return;
            if (!helper.isImage(params.file)) return;

            var canvas = element.find('canvas');
            var reader = new FileReader();

            reader.onload = onLoadFile;
            reader.readAsDataURL(params.file);

            function onLoadFile(event) {
                var img = new Image();
                img.onload = onLoadImage;
                img.src = event.target.result;
            }

            function onLoadImage() {
                var width = params.width || this.width / this.height * params.height;
                var height = params.height || this.height / this.width * params.width;
                canvas.attr({
                    width: width, height: height
                });
                canvas[0].getContext('2d').drawImage(this, 0, 0, width, height);
            }
        }
    };
}]);

app_hust.directive('numbersOnly', function () {
    return {
        require: 'ngModel', link: function (scope, element, attr, ngModelCtrl) {
            function fromUser(text) {
                if (text) {
                    var transformedInput = text.replace(/[^0-9]/g, '');

                    if (transformedInput !== text) {
                        ngModelCtrl.$setViewValue(transformedInput);
                        ngModelCtrl.$render();
                    }
                    return transformedInput;
                }
                return undefined;
            }

            ngModelCtrl.$parsers.push(fromUser);
        }
    };
});

app_hust.directive('myEnter', function () {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
            if (event.which === 13) {
                scope.$apply(function () {
                    scope.$eval(attrs.myEnter);
                });

                event.preventDefault();
            }
        });
    };
});

app_hust.directive('icheck', ['$timeout', '$parse', function ($timeout, $parse) {
    return {
        restrict: 'A', require: '?ngModel', link: function (scope, element, attr, ngModel) {
            $timeout(function () {
                var value = attr.value;

                function update(checked) {
                    if (attr.type === 'radio') {
                        ngModel.$setViewValue(value);
                    } else {
                        ngModel.$setViewValue(checked);
                    }
                }

                $(element).iCheck({
                    checkboxClass: attr.checkboxClass || 'icheckbox_square-green',
                    radioClass: attr.radioClass || 'iradio_square-green'
                }).on('ifChanged', function (e) {
                    scope.$apply(function () {
                        update(e.target.checked);
                    });
                });

                scope.$watch(attr.ngChecked, function (checked) {
                    if (typeof checked === 'undefined') checked = !!ngModel.$viewValue;
                    update(checked)
                }, true);

                scope.$watch(attr.ngModel, function (model) {
                    $(element).iCheck('update');
                }, true);

            })
        }
    }
}]);

app_hust.directive('notSpecialCharacters', function () {
    return {
        restrict: 'A', require: '?ngModel', link: function (scope, element, attrs, modelCtrl) {
            modelCtrl.$parsers.push(function (inputValue) {
                if (inputValue === undefined) return '';
                let transformedInput = inputValue.replace(/[^0-9a-zA-ZàáảãạâầấẩẫậăằắẳẵặèéẻẽẹêềếểễệìíỉĩịòóỏõọôồốổỗộơờớởỡợùúủũụưừứửữựỳýỷỹỵđÀÁẢÃẠÂẦẤẨẪẬĂẰẮẲẴẶÈÉẺẼẸÊỀẾỂỄỆÌÍỈĨỊÒÓỎÕỌÔỒỐỔỖỘƠỜỚỞỠỢÙÚỦŨỤƯỪỨỬỮỰỲÝỶỸỴĐ ]/g, '');
                if (transformedInput !== inputValue) {
                    modelCtrl.$setViewValue(transformedInput);
                    modelCtrl.$render();
                }
                return transformedInput;
            });
        }
    };
});
app_hust.directive('notSpecialCharactersButUnderscore', function () {
    return {
        restrict: 'A', require: '?ngModel', link: function (scope, element, attrs, modelCtrl) {
            modelCtrl.$parsers.push(function (inputValue) {
                if (inputValue === undefined) return '';
                let transformedInput = inputValue.replace(/[^0-9a-zA-ZàáảãạâầấẩẫậăằắẳẵặèéẻẽẹêềếểễệìíỉĩịòóỏõọôồốổỗộơờớởỡợùúủũụưừứửữựỳýỷỹỵđÀÁẢÃẠÂẦẤẨẪẬĂẰẮẲẴẶÈÉẺẼẸÊỀẾỂỄỆÌÍỈĨỊÒÓỎÕỌÔỒỐỔỖỘƠỜỚỞỠỢÙÚỦŨỤƯỪỨỬỮỰỲÝỶỸỴĐ_ ]/g, '');
                if (transformedInput !== inputValue) {
                    modelCtrl.$setViewValue(transformedInput);
                    modelCtrl.$render();
                }
                return transformedInput;
            });
        }
    };
});
app_hust.directive('formatNamePerson', function () {
    return {
        restrict: 'A', require: '?ngModel', link: function (scope, element, attrs, modelCtrl) {
            modelCtrl.$parsers.push(function (inputValue) {
                if (inputValue === undefined) return '';
                let transformedInput = inputValue.replace(/[^a-zA-ZàáảãạâầấẩẫậăằắẳẵặèéẻẽẹêềếểễệìíỉĩịòóỏõọôồốổỗộơờớởỡợùúủũụưừứửữựỳýỷỹỵđÀÁẢÃẠÂẦẤẨẪẬĂẰẮẲẴẶÈÉẺẼẸÊỀẾỂỄỆÌÍỈĨỊÒÓỎÕỌÔỒỐỔỖỘƠỜỚỞỠỢÙÚỦŨỤƯỪỨỬỮỰỲÝỶỸỴĐ ]/g, '');
                if (transformedInput !== inputValue) {
                    modelCtrl.$setViewValue(transformedInput);
                    modelCtrl.$render();
                }
                return transformedInput;
            });
        }
    };
});
app_hust.directive('onlyDigits', function () {
    return {
        restrict: 'A', require: '?ngModel', link: function (scope, element, attrs, modelCtrl) {
            modelCtrl.$parsers.push(function (inputValue) {
                if (inputValue === undefined) return '';
                let transformedInput = inputValue.replace(/[^0-9]/g, '');
                if (transformedInput !== inputValue) {
                    modelCtrl.$setViewValue(transformedInput);
                    modelCtrl.$render();
                }
                return transformedInput;
            });
        }
    };
});

app_hust.directive('removeStartZeroNumber', function () {
    return {
        restrict: 'A', require: '?ngModel', link: function (scope, element, attrs, modelCtrl) {
            modelCtrl.$parsers.push(function (inputValue) {
                if (isNullOrEmptyUntil(inputValue)) return '';
                let transformedInput = inputValue;
                if (inputValue.length > 1 && inputValue.charAt(0) === '0') {
                    transformedInput = inputValue.substring(1, inputValue.length);
                }
                if (transformedInput !== inputValue) {
                    modelCtrl.$setViewValue(transformedInput);
                    modelCtrl.$render();
                }
                return transformedInput;
            });
        }
    };
});
app_hust.directive('formatCurrency', function ($filter, $locale) {
    let decimalSep = $locale.NUMBER_FORMATS.DECIMAL_SEP;
    let toNumberRegex = new RegExp('[^0-9\\' + decimalSep + ']', 'g');
    let filterFunc = function (value) {
        if (isNullOrEmptyUntil(value) || isNaN(value)) {
            return '';
        } else {
            return $filter('number')(value);
        }
    };

    function getCaretPosition(input) {
        if (!input) return 0;
        if (input.selectionStart !== undefined) {
            return input.selectionStart;
        } else if (document.selection) {
            // Curse you IE
            input.focus();
            let selection = document.selection.createRange();
            selection.moveStart('character', input.value ? -input.value.length : 0);
            return selection.text.length;
        }
        return 0;
    }

    function setCaretPosition(input, pos) {
        if (!input) return 0;
        if (input.offsetWidth === 0 || input.offsetHeight === 0) {
            return; // Input's hidden
        }
        if (input.setSelectionRange) {
            input.focus();
            input.setSelectionRange(pos, pos);
        } else if (input.createTextRange) {
            // Curse you IE
            let range = input.createTextRange();
            range.collapse(true);
            range.moveEnd('character', pos);
            range.moveStart('character', pos);
            range.select();
        }
    }

    function toNumber(currencyStr) {
        return parseFloat(currencyStr.replace(toNumberRegex, ''), 10);
    }

    return {
        restrict: 'A', require: 'ngModel', link: function postLink(scope, elem, attrs, modelCtrl) {
            modelCtrl.$formatters.push(filterFunc);
            modelCtrl.$parsers.push(function (newViewValue) {
                if (isNullOrEmptyUntil(newViewValue)) {
                    return '';
                } else {
                    let oldModelValue = modelCtrl.$modelValue;
                    let newModelValue = toNumber(newViewValue);
                    modelCtrl.$viewValue = filterFunc(newModelValue);
                    let pos = getCaretPosition(elem[0]);
                    elem.val(modelCtrl.$viewValue);
                    let newPos = pos + modelCtrl.$viewValue.length - newViewValue.length;
                    if (isNullOrEmptyUntil(oldModelValue) || isNaN(oldModelValue)) {
                        newPos -= 3;
                    }
                    setCaretPosition(elem[0], newPos);
                    return newModelValue;
                }
            });
        }
    };
});


app_hust.directive('decimalInputFormat', function () {
      return {
          require: '?ngModel',
          link: function (scope, element, attrs, ngModelCtrl) {
              if (!ngModelCtrl) {
                  return;
              }

              ngModelCtrl.$parsers.push(function (val) {
                  console.log("attrs", attrs);
                  if (angular.isUndefined(val) ) {
                      var val = '';
                  }

                  var clean = val.replace(/[^-0-9\.]/g, '');
                  if (val.length > 1 && val.charAt(0) === ".") {
                      clean = clean.substring(1, clean.length);
                  }
                  var negativeCheck = clean.split('-');
                  var decimalCheck = clean.split('.');
                  if (!angular.isUndefined(negativeCheck[1])) {
                      negativeCheck[1] = negativeCheck[1].slice(0, negativeCheck[1].length);
                      clean = negativeCheck[0] + '-' + negativeCheck[1];
                      if (negativeCheck[0].length > 0) {
                          clean = negativeCheck[0];
                      }

                  }

                  if (!angular.isUndefined(decimalCheck[1])) {
                      decimalCheck[1] = decimalCheck[1].slice(0, attrs?.decimalInputFormat || 2 );
                      clean = decimalCheck[0] + '.' + decimalCheck[1];
                  }



                  if (val !== clean) {
                      ngModelCtrl.$setViewValue(clean);
                      ngModelCtrl.$render();
                  }
                  return clean;
              });

              element.bind('keypress', function (event) {
                  if (event.keyCode === 32) {
                      event.preventDefault();
                  }
              });
          }
      };
  });

function encryptData(input) {
    var sha256 = new jsSHA('SHA-1', 'TEXT');
    if (input == undefined) {
        input = "";
    }
    sha256.update(input);
    var hash = sha256.getHash("B64");

    return hash;
}

function valid_numbers(e) {
    var key = e.keyCode;
    if (key >= 48 && key <= 57) return true; else return false;
}
