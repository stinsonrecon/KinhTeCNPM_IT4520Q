const STATE_DEFAULT = "DEFAULT";
const STATE_ADD = "ADD";
const STATE_SEARCH = "SEARCH";
const STATE_UPLOAD = "UPLOAD";

app_hust.factory('useCasesPoint', function ($http, $translate) {
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
app_hust.controller('ctr-useCasesPoint', function ($scope, $rootScope, $translate, $compile, $timeout, $uibModal,
                                                           $location, $window, FileUploader, $filter, $http,
                                                           NgTableParams, $localStorage,useCasesPoint,_) {
    $scope.heSoB = $translate.instant("hust.useCasesPoint.label.heSoBTitle");
    $scope.heSoM = $translate.instant("hust.useCasesPoint.label.heSoMTitle");
    $scope.heSoT = $translate.instant("hust.useCasesPoint.label.heSoTTitle");

    $scope.model = {
        easyB : '',
        mediumB : '',
        hardB : '',

        easyM : '',
        mediumM : '',
        hardM : '',

        easyT : '',
        mediumT : '',
        hardT : '',
    };

    $scope.sumB = '';
    $scope.sumBPoint = '';
    $scope.easyBPoint = '';
    $scope.mediumBPoint = '';
    $scope.hardBPoint = '';

    $scope.sumM = '';
    $scope.sumMPoint = '';
    $scope.easyMPoint = '';
    $scope.mediumMPoint = '';
    $scope.hardMPoint = '';

    $scope.sumT = '';
    $scope.sumTPoint = '';
    $scope.easyTPoint = '';
    $scope.mediumTPoint = '';
    $scope.hardTPoint = '';

    $scope.TBF = '';

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

        if (isNullOrEmpty(dataCheck.easyB))
        {
            bootboxAlertFocus($translate.instant("hust.useCasesPoint.message.error.easyBInvalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }
        if (isNullOrEmpty(dataCheck.mediumB))
        {
            bootboxAlertFocus($translate.instant("hust.useCasesPoint.message.error.mediumBInvalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }
        if (isNullOrEmpty(dataCheck.hardB))
        {
            bootboxAlertFocus($translate.instant("hust.useCasesPoint.message.error.hardBInvalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }

        if (isNullOrEmpty(dataCheck.easyM))
        {
            bootboxAlertFocus($translate.instant("hust.useCasesPoint.message.error.easyMInvalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }
        if (isNullOrEmpty(dataCheck.mediumM))
        {
            bootboxAlertFocus($translate.instant("hust.useCasesPoint.message.error.mediumMInvalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }
        if (isNullOrEmpty(dataCheck.hardM))
        {
            bootboxAlertFocus($translate.instant("hust.useCasesPoint.message.error.hardMInvalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }

        if (isNullOrEmpty(dataCheck.easyT))
        {
            bootboxAlertFocus($translate.instant("hust.useCasesPoint.message.error.easyTInvalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }
        if (isNullOrEmpty(dataCheck.mediumT))
        {
            bootboxAlertFocus($translate.instant("hust.useCasesPoint.message.error.mediumTInvalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }
        if (isNullOrEmpty(dataCheck.hardT))
        {
            bootboxAlertFocus($translate.instant("hust.useCasesPoint.message.error.hardTInvalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }
        return true;
    };

     function handleBType () {
        $scope.sumB = parseInt($scope.model.easyB) + parseInt($scope.model.mediumB) + parseInt($scope.model.hardB);

        $scope.easyBPoint = $scope.model.easyB * 5;
        $scope.mediumBPoint = $scope.model.mediumB * 10;
        $scope.hardBPoint = $scope.model.hardB * 15;

        $scope.sumBPoint = $scope.easyBPoint + $scope.mediumBPoint + $scope.hardBPoint;
    }

     function handleMType () {
        $scope.sumM = parseInt($scope.model.easyM) + parseInt($scope.model.mediumM) + parseInt($scope.model.hardM);

        $scope.easyMPoint = $scope.model.easyM * 5 * 1.2;
        $scope.mediumMPoint = $scope.model.mediumM * 10 * 1.2;
        $scope.hardMPoint = $scope.model.hardM * 15 * 1.2;

        $scope.sumMPoint = $scope.easyMPoint + $scope.mediumMPoint + $scope.hardMPoint;
    }

    function handleTType () {
        $scope.sumT = parseInt($scope.model.easyT) + parseInt($scope.model.mediumT) + parseInt($scope.model.hardT);

        $scope.easyTPoint = $scope.model.easyT * 5 * 1.5;
        $scope.mediumTPoint = $scope.model.mediumT * 10 * 1.5;
        $scope.hardTPoint = $scope.model.hardT * 15 * 1.5;

        $scope.sumTPoint = $scope.easyTPoint + $scope.mediumTPoint + $scope.hardTPoint;
    }

    $scope.isFieldValid = (field) => {
        return isNullOrEmpty($scope.model[field]) && $scope.frm_useCasesPoint[field]?.$touched ;
    };

    $scope.submitForm = () => {
        $scope.frm_useCasesPoint['easyB']?.$setTouched();
        $scope.frm_useCasesPoint['mediumB']?.$setTouched();
        $scope.frm_useCasesPoint['hardB']?.$setTouched();

        $scope.frm_useCasesPoint['easyM']?.$setTouched();
        $scope.frm_useCasesPoint['mediumM']?.$setTouched();
        $scope.frm_useCasesPoint['hardM']?.$setTouched();

        $scope.frm_useCasesPoint['easyT']?.$setTouched();
        $scope.frm_useCasesPoint['mediumT']?.$setTouched();
        $scope.frm_useCasesPoint['hardT']?.$setTouched();
    }

    $scope.actionSearch = async () => {
        $scope.submitForm();
        if($scope.validBeforeSearch() === true) {
            handleBType();
            handleMType();
            handleTType();

            $scope.TBF = $scope.sumBPoint + $scope.sumMPoint + $scope.sumTPoint;

            let sendData = {
                easyB : $scope.model.easyB.toString(),
                mediumB : $scope.model.mediumB.toString(),
                hardB : $scope.model.hardB.toString(),

                easyM : $scope.model.easyM.toString(),
                mediumM : $scope.model.mediumM.toString(),
                hardM : $scope.model.hardM.toString(),

                easyT : $scope.model.easyT.toString(),
                mediumT : $scope.model.mediumT.toString(),
                hardT : $scope.model.hardT.toString(),

                easyBPoint: $scope.easyBPoint.toString(),
                mediumBPoint : $scope.mediumBPoint.toString(),
                hardBPoint : $scope.hardBPoint.toString(),

                easyMPoint : $scope.easyMPoint.toString(),
                mediumMPoint : $scope.mediumMPoint.toString(),
                hardMPoint : $scope.hardMPoint.toString(),

                easyTPoint : $scope.easyTPoint.toString(),
                mediumTPoint : $scope.mediumTPoint.toString(),
                hardTPoint : $scope.hardTPoint.toString(),

                sumB : $scope.sumB.toString(),
                sumM : $scope.sumM.toString(),
                sumT : $scope.sumT.toString(),

                sumBPoint : $scope.sumBPoint.toString(),
                sumMPoint : $scope.sumMPoint.toString(),
                sumTPoint : $scope.sumTPoint.toString(),

                strTBF : $scope.TBF.toString(),

                "fileTempName" : 'bang_tinh_toan_diem_cac_truong_hop_su_dung',
                "fileType" : '.xlsx'
            }

            console.log('sendData', sendData);

            overLoading('Loading...');

            useCasesPoint.exportFile(sendData, function (result, status, header, config) {
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