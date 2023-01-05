const STATE_DEFAULT = "DEFAULT";
const STATE_ADD = "ADD";
const STATE_SEARCH = "SEARCH";
const STATE_UPLOAD = "UPLOAD";

app_hust.factory('interactiveActorPoint', function ($http, $translate) {
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
app_hust.controller('ctr-interactiveActorPoint', function ($scope, $rootScope, $translate, $compile, $timeout, $uibModal,
                                          $location, $window, FileUploader, $filter, $http,
                                          NgTableParams, $localStorage,interactiveActorPoint,_) {
    $scope.actorTitle = $translate.instant("hust.softwarePriceDetermination.label.actorBTitle");

    $scope.model = {
        easy : '',
        medium : '',
        hard : '',
    };

    $scope.easyPoint = '';
    $scope.mediumPoint = '';
    $scope.hardPoint = '';

    $scope.sum = '';
    $scope.TAW = '';

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
        console.log('dataCheck', dataCheck);

        if (isNullOrEmpty(dataCheck.easy))
        {
            bootboxAlertFocus($translate.instant("hust.interactiveActorPoint.message.error.easyInvalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }
        if (isNullOrEmpty(dataCheck.medium))
        {
            bootboxAlertFocus($translate.instant("hust.interactiveActorPoint.message.error.mediumInvalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }
        if (isNullOrEmpty(dataCheck.hard))
        {
            bootboxAlertFocus($translate.instant("hust.interactiveActorPoint.message.error.hardInvalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }
        return true;
    };

    function handleActor () {
        $scope.sum = parseInt($scope.model.easy) + parseInt($scope.model.medium) + parseInt($scope.model.hard);

        $scope.easyPoint = $scope.model.easy * 1;
        $scope.mediumPoint = $scope.model.medium * 2;
        $scope.hardPoint = $scope.model.hard * 3;

        $scope.TAW = $scope.easyPoint + $scope.mediumPoint + $scope.hardPoint;
    }


    $scope.isFieldValid = (field) => {
        return isNullOrEmpty($scope.model[field]) && $scope.frm_interactiveActorPoint[field]?.$touched ;
    };

    $scope.submitForm = () => {
        $scope.frm_interactiveActorPoint['easy']?.$setTouched();
        $scope.frm_interactiveActorPoint['medium']?.$setTouched();
        $scope.frm_interactiveActorPoint['hard']?.$setTouched();
    }

    $scope.actionSearch = async () => {
        $scope.submitForm();
        if($scope.validBeforeSearch() === true) {
            handleActor();

            let sendData = {
                easy : $scope.model.easy.toString(),
                medium : $scope.model.medium.toString(),
                hard : $scope.model.hard.toString(),

                easyPoint: $scope.easyPoint.toString(),
                mediumPoint : $scope.mediumPoint.toString(),
                hardPoint : $scope.hardPoint.toString(),

                sum : $scope.sum.toString(),
                strTAW : $scope.TAW.toString(),

                "fileTempName" : 'bang_tinh_toan_diem_cac_tac_nhan_tuong_tac',
                "fileType" : '.xlsx'
            }

            overLoading('Loading...');

            interactiveActorPoint.exportFile(sendData, function (result, status, header, config) {
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