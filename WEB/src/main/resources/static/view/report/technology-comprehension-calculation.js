const STATE_DEFAULT = "DEFAULT";
const STATE_ADD = "ADD";
const STATE_SEARCH = "SEARCH";
const STATE_UPLOAD = "UPLOAD";

app_hust.factory('technologyComprehensionCalculation', function ($http, $translate) {
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
app_hust.controller('ctr-technologyComprehensionCalculation', function ($scope, $rootScope, $translate, $compile, $timeout, $uibModal,
                                                                $location, $window, FileUploader, $filter, $http,
                                                                NgTableParams, $localStorage,technologyComprehensionCalculation,_) {
    $scope.model = {
        heThongPhanTan : 3,
        dapUngTucThoi : 3,
        suDungTrucTuyen : 3,
        xuLyBenTrong : 3,
        taiSuDung : 3,
        deCaiDat : 3,
        deSuDung : 3,
        khaNangChuyenDoi : 3,
        deThayDoi : 3,
        suDungDongThoi : 3,
        baoMatDacBiet : 3,
        truyCapTrucTiep : 3,
        daoTaoDacBiet : 3,

        heThongPhanTanKQ : '',
        deCaiDatKQ : '',
        deSuDungKQ : '',
        khaNangChuyenDoiKQ : '',

        sum : '',

        TCF : ''
    };
    $scope.stateForm = STATE_DEFAULT;

    $scope.disableBtnMain = true;

    $scope.likert = [
        {
            'code' : 0,
            'value' : '0 - Không quan trọng'
        },
        {
            'code' : 1,
            'value' : '1 - Khá là không quan trọng'
        },
        {
            'code' : 2,
            'value' : '2 - Quan trọng bình thường'
        },
        {
            'code' : 3,
            'value' : '3 - Quan trọng'
        },
        {
            'code' : 4,
            'value' : '4 - Khá là quan trọng'
        },
        {
            'code' : 5,
            'value' : '5 - Có vai trò tác động căn bản(Cực kỳ quan trọng)'
        },
    ];

    $scope.validationOptions = {
        debounce : 500,
        preValidateFormElements : true,
        rules : {
        },
        messages : {
        }
    };

    function handleResult() {
        $scope.model.heThongPhanTanKQ = $scope.model.heThongPhanTan * 2
        $scope.model.deCaiDatKQ = $scope.model.deCaiDat * 0.5
        $scope.model.deSuDungKQ = $scope.model.deSuDung * 0.5
        $scope.model.khaNangChuyenDoiKQ = $scope.model.khaNangChuyenDoi * 2

        $scope.model.sum =  $scope.model.dapUngTucThoi +
                            $scope.model.suDungTrucTuyen +
                            $scope.model.xuLyBenTrong +
                            $scope.model.taiSuDung +
                            $scope.model.deThayDoi +
                            $scope.model.suDungDongThoi +
                            $scope.model.baoMatDacBiet +
                            $scope.model.truyCapTrucTiep +
                            $scope.model.daoTaoDacBiet +
                            $scope.model.heThongPhanTanKQ +
                            $scope.model.deCaiDatKQ +
                            $scope.model.deSuDungKQ +
                            $scope.model.khaNangChuyenDoiKQ;

        $scope.model.TCF = 0.6 + (0.01 * $scope.model.sum);
    }

    $scope.actionSearch = async () => {
        handleResult();

        let sendData = {
            heThongPhanTan : $scope.model.heThongPhanTan.toString(),
            dapUngTucThoi : $scope.model.dapUngTucThoi.toString(),
            suDungTrucTuyen : $scope.model.suDungTrucTuyen.toString(),
            xuLyBenTrong : $scope.model.xuLyBenTrong.toString(),
            taiSuDung : $scope.model.taiSuDung.toString(),
            deCaiDat : $scope.model.deCaiDat.toString(),
            deSuDung : $scope.model.deSuDung.toString(),
            khaNangChuyenDoi : $scope.model.khaNangChuyenDoi.toString(),
            deThayDoi : $scope.model.deThayDoi.toString(),
            suDungDongThoi : $scope.model.suDungDongThoi.toString(),
            baoMatDacBiet : $scope.model.baoMatDacBiet.toString(),
            truyCapTrucTiep : $scope.model.truyCapTrucTiep.toString(),
            daoTaoDacBiet : $scope.model.daoTaoDacBiet.toString(),

            heThongPhanTanKQ : $scope.model.heThongPhanTanKQ.toString(),
            deCaiDatKQ : $scope.model.deCaiDatKQ.toString(),
            deSuDungKQ : $scope.model.deSuDungKQ.toString(),
            khaNangChuyenDoiKQ : $scope.model.khaNangChuyenDoiKQ.toString(),

            sum : $scope.model.sum.toString(),

            strTCF : $scope.model.TCF.toString(),

            "fileTempName" : 'bang_tinh_toan_he_so_phuc_tap_kt_cn',
            "fileType" : '.xlsx'
        }

        console.log('sendData', sendData);

        overLoading('Loading...');

        technologyComprehensionCalculation.exportFile(sendData, function (result, status, header, config) {
            if (result.status == '0' && result.status != 'undefined') {
                closeOverLay();
                bootboxAlertFocus($translate.instant('hust.messages.validate.report.' + result.messages), "", $translate.instant("hust.lable.hust.name"), "");
            } else {
                closeOverLay();
                download(new Blob([result]), header('MyDownloadFile') + header('FileType'), header('Content-Type'));
            }
        });

        closeOverLay();
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