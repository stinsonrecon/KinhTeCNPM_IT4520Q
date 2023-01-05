const STATE_DEFAULT = "DEFAULT";
const STATE_ADD = "ADD";
const STATE_SEARCH = "SEARCH";
const STATE_UPLOAD = "UPLOAD";

app_hust.factory('personalSalary', function ($http, $translate) {
    return {
        exportFile : function(data, callback) {
            var url = eim_url + "/hust/exportFile";
            $http.post(url, data, {
                responseType: 'arraybuffer'
            }).success(callback).error(function (data, status, headers, config) {
                closeOverLay();
                if ("403" == status) {
                    bootboxAlertFocus(status + " : " + data.message, "", $translate.instant("hust.lable.hust.name"), "");
                } else {
                    bootboxAlertFocus(status + " " + $translate.instant('hust.messages.error.connection'), "", $translate.instant("hust.lable.hust.name"), "");
                }
            });
        }
    }
});
app_hust.controller('ctr-personalSalary', function ($scope, $rootScope, $translate, $compile, $timeout, $uibModal,
                                          $location, $window, FileUploader, $filter, $http,
                                          NgTableParams, $localStorage,personalSalary,_) {
    $scope.salary1Title = $translate.instant("hust.personalSalary.label.salary1Title");
    $scope.salary2Title = $translate.instant("hust.personalSalary.label.salary2Title");
    $scope.salary3Title = $translate.instant("hust.personalSalary.label.salary3Title");

    $scope.model = {
        salary1 : '',
        staffNum1 : '',

        salary2 : '',
        staffNum2 : '',

        salary3 : '',
        staffNum3 : '',

        sumSalary1 : '',
        sumSalary2 : '',
        sumSalary3 : '',

        sumAllSalary : '',
        salaryPerMonth : '',
        salaryPerHour : '',
    };

    $scope.stateForm = STATE_DEFAULT;

    $scope.disableBtnMain = true;

    $scope.hiddenTree = true;

    $scope.validationOptions = {
        debounce : 500,
        preValidateFormElements : true,
        rules : {
        },
        messages : {
        }
    };

    $scope.validBeforeSearch = function() {
        let dataCheck = {...$scope.model};

        if (isNullOrEmpty(dataCheck.salary1))
        {
            bootboxAlertFocus($translate.instant("hust.personalSalary.message.error.salary1Invalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }
        if (isNullOrEmpty(dataCheck.staffNum1))
        {
            bootboxAlertFocus($translate.instant("hust.personalSalary.message.error.staffNum1Invalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }

        if (isNullOrEmpty(dataCheck.salary2))
        {
            bootboxAlertFocus($translate.instant("hust.personalSalary.message.error.salary2Invalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }
        if (isNullOrEmpty(dataCheck.staffNum2))
        {
            bootboxAlertFocus($translate.instant("hust.personalSalary.message.error.staffNum2Invalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }

        if (isNullOrEmpty(dataCheck.salary3))
        {
            bootboxAlertFocus($translate.instant("hust.personalSalary.message.error.salary3Invalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }
        if (isNullOrEmpty(dataCheck.staffNum3))
        {
            bootboxAlertFocus($translate.instant("hust.personalSalary.message.error.staffNum3Invalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }
        return true;
    };


    $scope.isFieldValid = (field) => {
        return isNullOrEmpty($scope.model[field]) && $scope.frm_personalSalary[field]?.$touched ;
    };

    $scope.submitForm = () => {
        $scope.frm_personalSalary['salary1']?.$setTouched();
        $scope.frm_personalSalary['staffNum1']?.$setTouched();
        $scope.frm_personalSalary['salary2']?.$setTouched();
        $scope.frm_personalSalary['staffNum2']?.$setTouched();
        $scope.frm_personalSalary['salary3']?.$setTouched();
        $scope.frm_personalSalary['staffNum3']?.$setTouched();
    }

    function handleSumSalary() {
        $scope.model.sumSalary1 = $scope.model.salary1 * $scope.model.staffNum1;
        $scope.model.sumSalary2 = $scope.model.salary2 * $scope.model.staffNum2;
        $scope.model.sumSalary3 = $scope.model.salary3 * $scope.model.staffNum3;

        $scope.model.sumAllSalary = $scope.model.sumSalary1 +
                                    $scope.model.sumSalary2 +
                                    $scope.model.sumSalary3;

        let sumStaff =  parseInt($scope.model.staffNum1) +
                        parseInt($scope.model.staffNum2) +
                        parseInt($scope.model.staffNum3);

        $scope.model.salaryPerMonth = $scope.model.sumAllSalary / sumStaff;
        $scope.model.salaryPerHour = $scope.model.salaryPerMonth / 20 / 8;
    }

    $scope.actionSearch = async () => {
        $scope.submitForm();
        if($scope.validBeforeSearch() === true) {
            handleSumSalary();

            let sendData = {
                salary1 : parseInt($scope.model.salary1),
                staffNum1 : parseInt($scope.model.staffNum1),

                salary2 : parseInt($scope.model.salary2),
                staffNum2 : parseInt($scope.model.staffNum2),

                salary3 : parseInt($scope.model.salary3),
                staffNum3 : parseInt($scope.model.staffNum3),

                sumSalary1 : parseInt($scope.model.sumSalary1),
                sumSalary2 : parseInt($scope.model.sumSalary2),
                sumSalary3 : parseInt($scope.model.sumSalary3),

                sumAllSalary : parseInt($scope.model.sumAllSalary),
                salaryPerMonth : parseInt($scope.model.salaryPerMonth),
                salaryPerHour : parseInt($scope.model.salaryPerHour),

                "fileTempName" : 'luong_binh_quan',
                "fileType" : '.xlsx'
            }
            console.log('sendData', sendData);

            overLoading('Loading...');

            personalSalary.exportFile(sendData, function (result, status, header, config) {
                if (result.status == '0' && result.status != 'undefined') {
                    closeOverLay();
                    bootboxAlertFocus($translate.instant('hust.messages.validate.report.' + result.messages), "", $translate.instant("hust.lable.hust.name"), "");
                } else {
                    closeOverLay();
                    download(new Blob([result]), header('MyDownloadFile') + header('FileType'), header('Content-Type'));
                }
            });

            closeOverLay();
        }
    };
});
app_hust.run (
    function (_) {
    }
);
app_hust.factory ("_", function ($window) {
        let _ = $window._;
        delete ($window._);
        return (_);
    }
);
function StringUtilNVL(val){
    if(val===undefined || val === null) return "";
    return $.trim(val);
}

function isNullOrEmpty(input){
    return input === undefined || input === null || input === "";
}

function createTimeStamp(){
    let theMoment = moment();
    let millisTimeStamp = theMoment.valueOf();
    return millisTimeStamp;
}
function isNumeric(value) {
    return /^\d+$/.test(value);
}