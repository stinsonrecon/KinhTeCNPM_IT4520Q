const STATE_DEFAULT = "DEFAULT";
const STATE_ADD = "ADD";
const STATE_SEARCH = "SEARCH";
const STATE_UPLOAD = "UPLOAD";

app_hust.factory('softwarePriceDetermination', function ($http, $translate) {
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
app_hust.controller('ctr-softwarePriceDetermination', function ($scope, $rootScope, $translate, $compile, $timeout, $uibModal,
                                                           $location, $window, FileUploader, $filter, $http,
                                                           NgTableParams, $localStorage,softwarePriceDetermination,_) {
    $scope.actorTitle = $translate.instant("hust.softwarePriceDetermination.label.actorBTitle");

    $scope.useCaseGroupTitle = $translate.instant("hust.softwarePriceDetermination.label.useCaseGroupTitle");
    $scope.heSoB = $translate.instant("hust.softwarePriceDetermination.label.heSoBTitle");
    $scope.heSoM = $translate.instant("hust.softwarePriceDetermination.label.heSoMTitle");
    $scope.heSoT = $translate.instant("hust.softwarePriceDetermination.label.heSoTTitle");

    $scope.technologyComprehensionTitle = $translate.instant("hust.softwarePriceDetermination.label.technologyComprehensionTitle");

    $scope.noiSuyTitle = $translate.instant("hust.softwarePriceDetermination.label.noiSuyTitle");
    $scope.memberStat = $translate.instant("hust.softwarePriceDetermination.label.memberStat");
    $scope.projectStat = $translate.instant("hust.softwarePriceDetermination.label.projectStat");

    $scope.salaryTitle = $translate.instant("hust.softwarePriceDetermination.label.salaryTitle");
    $scope.salary1Title = $translate.instant("hust.softwarePriceDetermination.label.salary1Title");
    $scope.salary2Title = $translate.instant("hust.softwarePriceDetermination.label.salary2Title");
    $scope.salary3Title = $translate.instant("hust.softwarePriceDetermination.label.salary3Title");

    $scope.model = {
        // BẢNG TÍNH TOÁN ĐIỂM CÁC TÁC NHÂN (ACTORS) TƯƠNG TÁC START
        // Output: TAW
        easy : '',
        medium : '',
        hard : '',
        // BẢNG TÍNH TOÁN ĐIỂM CÁC TÁC NHÂN (ACTORS) TƯƠNG TÁC START END

        // BẢNG TÍNH TOÁN ĐIỂM CÁC TRƯỜNG HỢP SỬ DỤNG START
        // Output: TBF
        easyB : '',
        mediumB : '',
        hardB : '',

        easyM : '',
        mediumM : '',
        hardM : '',

        easyT : '',
        mediumT : '',
        hardT : '',
        // BẢNG TÍNH TOÁN ĐIỂM CÁC TRƯỜNG HỢP SỬ DỤNG END

        // BẢNG TÍNH TOÁN HỆ SỐ PHỨC TẠP KỸ THUẬT-CÔNG NGHỆ START
        // Output: TCF
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

        TCF : '',
        // BẢNG TÍNH TOÁN HỆ SỐ PHỨC TẠP KỸ THUẬT-CÔNG NGHỆ END

        // HỆ SỐ PTMT-KN-NỘI SUY START
        // Output: EFW, EF, ES, P
        followOrder : '',
        experience : '',
        oopExperience : '',
        leaderExperience : '',
        motivate : '',
        stable : '',
        partTimeUsage : '',
        hardCode : '',

        followOrderPoint : '',
        experiencePoint : '',
        oopExperiencePoint : '',
        leaderExperiencePoint : '',
        motivatePoint : '',
        stablePoint : '',
        partTimeUsagePoint : '',
        hardCodePoint : '',

        followOrderES : '',
        experienceES : '',
        oopExperienceES : '',
        leaderExperienceES : '',
        motivateES : '',
        stableES : '',
        partTimeUsageES : '',
        hardCodeES : '',

        strEFW : '',
        strEF : '',
        strES : '',
        strP : '',
        // HỆ SỐ PTMT-KN-NỘI SUY END

        // BẢNG TÍNH LƯƠNG BÌNH QUÂN / NGƯỜI/ GIỜ START
        // Output: Mức lương lao động bình quân (H)
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
        // BẢNG TÍNH LƯƠNG BÌNH QUÂN / NGƯỜI/ GIỜ END

        // Xác định giá phần mềm
        strUUCP : '',
        strAUCP : '',
        strE : '',
        strG : '',
    };

    // BẢNG TÍNH TOÁN ĐIỂM CÁC TÁC NHÂN (ACTORS) TƯƠNG TÁC START
    // Output: TAW
    $scope.easyPoint = '';
    $scope.mediumPoint = '';
    $scope.hardPoint = '';

    $scope.sum = '';
    $scope.TAW = '';
    // BẢNG TÍNH TOÁN ĐIỂM CÁC TÁC NHÂN (ACTORS) TƯƠNG TÁC START END

    // BẢNG TÍNH TOÁN ĐIỂM CÁC TRƯỜNG HỢP SỬ DỤNG START
    // Output: TBF
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
    // BẢNG TÍNH TOÁN ĐIỂM CÁC TRƯỜNG HỢP SỬ DỤNG END

    // BẢNG TÍNH TOÁN HỆ SỐ PHỨC TẠP KỸ THUẬT-CÔNG NGHỆ START
    // Output: TCF
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
    // BẢNG TÍNH TOÁN HỆ SỐ PHỨC TẠP KỸ THUẬT-CÔNG NGHỆ END

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

        // BẢNG TÍNH TOÁN ĐIỂM CÁC TÁC NHÂN (ACTORS) TƯƠNG TÁC START
        // Output: TAW
        if (isNullOrEmpty(dataCheck.easy))
        {
            bootboxAlertFocus($translate.instant("hust.softwarePriceDetermination.message.error.easyInvalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }
        if (isNullOrEmpty(dataCheck.medium))
        {
            bootboxAlertFocus($translate.instant("hust.softwarePriceDetermination.message.error.mediumInvalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }
        if (isNullOrEmpty(dataCheck.hard))
        {
            bootboxAlertFocus($translate.instant("hust.softwarePriceDetermination.message.error.hardInvalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }
        // BẢNG TÍNH TOÁN ĐIỂM CÁC TÁC NHÂN (ACTORS) TƯƠNG TÁC START END

        // BẢNG TÍNH TOÁN ĐIỂM CÁC TRƯỜNG HỢP SỬ DỤNG START
        // Output: TBF
        if (isNullOrEmpty(dataCheck.easyB))
        {
            bootboxAlertFocus($translate.instant("hust.softwarePriceDetermination.message.error.easyBInvalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }
        if (isNullOrEmpty(dataCheck.mediumB))
        {
            bootboxAlertFocus($translate.instant("hust.softwarePriceDetermination.message.error.mediumBInvalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }
        if (isNullOrEmpty(dataCheck.hardB))
        {
            bootboxAlertFocus($translate.instant("hust.softwarePriceDetermination.message.error.hardBInvalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }

        if (isNullOrEmpty(dataCheck.easyM))
        {
            bootboxAlertFocus($translate.instant("hust.softwarePriceDetermination.message.error.easyMInvalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }
        if (isNullOrEmpty(dataCheck.mediumM))
        {
            bootboxAlertFocus($translate.instant("hust.softwarePriceDetermination.message.error.mediumMInvalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }
        if (isNullOrEmpty(dataCheck.hardM))
        {
            bootboxAlertFocus($translate.instant("hust.softwarePriceDetermination.message.error.hardMInvalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }

        if (isNullOrEmpty(dataCheck.easyT))
        {
            bootboxAlertFocus($translate.instant("hust.softwarePriceDetermination.message.error.easyTInvalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }
        if (isNullOrEmpty(dataCheck.mediumT))
        {
            bootboxAlertFocus($translate.instant("hust.softwarePriceDetermination.message.error.mediumTInvalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }
        if (isNullOrEmpty(dataCheck.hardT))
        {
            bootboxAlertFocus($translate.instant("hust.softwarePriceDetermination.message.error.hardTInvalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }
        // BẢNG TÍNH TOÁN ĐIỂM CÁC TRƯỜNG HỢP SỬ DỤNG END

        // HỆ SỐ PTMT-KN-NỘI SUY START
        // Output: EFW, EF, ES, P
        if (isNullOrEmpty(dataCheck.followOrder))
        {
            bootboxAlertFocus($translate.instant("hust.softwarePriceDetermination.message.error.followOrderInvalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }
        if (isNullOrEmpty(dataCheck.experience))
        {
            bootboxAlertFocus($translate.instant("hust.softwarePriceDetermination.message.error.experienceInvalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }
        if (isNullOrEmpty(dataCheck.oopExperience))
        {
            bootboxAlertFocus($translate.instant("hust.softwarePriceDetermination.message.error.oopExperienceInvalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }
        if (isNullOrEmpty(dataCheck.leaderExperience))
        {
            bootboxAlertFocus($translate.instant("hust.softwarePriceDetermination.message.error.leaderExperienceInvalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }
        if (isNullOrEmpty(dataCheck.motivate))
        {
            bootboxAlertFocus($translate.instant("hust.softwarePriceDetermination.message.error.motivateInvalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }
        if (isNullOrEmpty(dataCheck.stable))
        {
            bootboxAlertFocus($translate.instant("hust.softwarePriceDetermination.message.error.stableInvalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }
        if (isNullOrEmpty(dataCheck.partTimeUsage))
        {
            bootboxAlertFocus($translate.instant("hust.softwarePriceDetermination.message.error.partTimeUsageInvalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }
        if (isNullOrEmpty(dataCheck.hardCode))
        {
            bootboxAlertFocus($translate.instant("hust.softwarePriceDetermination.message.error.hardCodeInvalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }
        // HỆ SỐ PTMT-KN-NỘI SUY END

        // BẢNG TÍNH LƯƠNG BÌNH QUÂN / NGƯỜI/ GIỜ START
        // Output: Mức lương lao động bình quân (H)
        if (isNullOrEmpty(dataCheck.salary1))
        {
            bootboxAlertFocus($translate.instant("hust.softwarePriceDetermination.message.error.salary1Invalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }
        if (isNullOrEmpty(dataCheck.staffNum1))
        {
            bootboxAlertFocus($translate.instant("hust.softwarePriceDetermination.message.error.staffNum1Invalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }

        if (isNullOrEmpty(dataCheck.salary2))
        {
            bootboxAlertFocus($translate.instant("hust.softwarePriceDetermination.message.error.salary2Invalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }
        if (isNullOrEmpty(dataCheck.staffNum2))
        {
            bootboxAlertFocus($translate.instant("hust.softwarePriceDetermination.message.error.staffNum2Invalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }

        if (isNullOrEmpty(dataCheck.salary3))
        {
            bootboxAlertFocus($translate.instant("hust.softwarePriceDetermination.message.error.salary3Invalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }
        if (isNullOrEmpty(dataCheck.staffNum3))
        {
            bootboxAlertFocus($translate.instant("hust.softwarePriceDetermination.message.error.staffNum3Invalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }
        // BẢNG TÍNH LƯƠNG BÌNH QUÂN / NGƯỜI/ GIỜ END
        return true;
    };

    $scope.isFieldValid = (field) => {
        return isNullOrEmpty($scope.model[field]) && $scope.frm_softwarePriceDetermination[field]?.$touched ;
    };

    $scope.submitForm = () => {
        $scope.frm_softwarePriceDetermination['easy']?.$setTouched();
        $scope.frm_softwarePriceDetermination['medium']?.$setTouched();
        $scope.frm_softwarePriceDetermination['hard']?.$setTouched();

        $scope.frm_softwarePriceDetermination['easyB']?.$setTouched();
        $scope.frm_softwarePriceDetermination['mediumB']?.$setTouched();
        $scope.frm_softwarePriceDetermination['hardB']?.$setTouched();

        $scope.frm_softwarePriceDetermination['easyM']?.$setTouched();
        $scope.frm_softwarePriceDetermination['mediumM']?.$setTouched();
        $scope.frm_softwarePriceDetermination['hardM']?.$setTouched();

        $scope.frm_softwarePriceDetermination['easyT']?.$setTouched();
        $scope.frm_softwarePriceDetermination['mediumT']?.$setTouched();
        $scope.frm_softwarePriceDetermination['hardT']?.$setTouched();

        $scope.frm_softwarePriceDetermination['followOrder']?.$setTouched();
        $scope.frm_softwarePriceDetermination['experience']?.$setTouched();
        $scope.frm_softwarePriceDetermination['oopExperience']?.$setTouched();
        $scope.frm_softwarePriceDetermination['leaderExperience']?.$setTouched();
        $scope.frm_softwarePriceDetermination['motivate']?.$setTouched();
        $scope.frm_softwarePriceDetermination['stable']?.$setTouched();
        $scope.frm_softwarePriceDetermination['partTimeUsage']?.$setTouched();
        $scope.frm_softwarePriceDetermination['hardCode']?.$setTouched();

        $scope.frm_softwarePriceDetermination['salary1']?.$setTouched();
        $scope.frm_softwarePriceDetermination['staffNum1']?.$setTouched();
        $scope.frm_softwarePriceDetermination['salary2']?.$setTouched();
        $scope.frm_softwarePriceDetermination['staffNum2']?.$setTouched();
        $scope.frm_softwarePriceDetermination['salary3']?.$setTouched();
        $scope.frm_softwarePriceDetermination['staffNum3']?.$setTouched();
    }

    function handleActor () {
        $scope.sum = parseInt($scope.model.easy) + parseInt($scope.model.medium) + parseInt($scope.model.hard);

        $scope.easyPoint = $scope.model.easy * 1;
        $scope.mediumPoint = $scope.model.medium * 2;
        $scope.hardPoint = $scope.model.hard * 3;

        $scope.TAW = $scope.easyPoint + $scope.mediumPoint + $scope.hardPoint;
    }

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

    function handleEFW_EF() {
        $scope.model.followOrderPoint = $scope.model.followOrder * 1.5;
        $scope.model.experiencePoint = $scope.model.experience * 0.5;
        $scope.model.oopExperiencePoint = $scope.model.oopExperience * 1;
        $scope.model.leaderExperiencePoint = $scope.model.leaderExperience * 0.5;
        $scope.model.motivatePoint = $scope.model.motivate * 1;
        $scope.model.stablePoint = $scope.model.stable * 2;
        $scope.model.partTimeUsagePoint = $scope.model.partTimeUsage * (-1);
        $scope.model.hardCodePoint = $scope.model.hardCode * (-1);

        $scope.model.strEFW =  $scope.model.followOrderPoint +
            $scope.model.experiencePoint +
            $scope.model.oopExperiencePoint +
            $scope.model.leaderExperiencePoint +
            $scope.model.motivatePoint +
            $scope.model.stablePoint +
            $scope.model.partTimeUsagePoint +
            $scope.model.hardCodePoint;

        $scope.model.strEF = 1.4 + (-0.03 * $scope.model.strEFW);
        $scope.model.strEF = $scope.model.strEF.toFixed(4);
    }

    function handleES() {
        $scope.model.followOrderES =
            $scope.model.followOrderPoint > 3 ? 1 :
                (
                    $scope.model.followOrderPoint > 2 ? 0.6 :
                        (
                            $scope.model.followOrderPoint > 1 ? 0.1 :
                                (
                                    $scope.model.followOrderPoint > 0 ? 0.05 :
                                        (
                                            $scope.model.followOrderPoint <= 0 ? 0 : null
                                        )
                                )
                        )
                );
        $scope.model.experienceES =
            $scope.model.experiencePoint > 3 ? 1 :
                (
                    $scope.model.experiencePoint > 2 ? 0.6 :
                        (
                            $scope.model.experiencePoint > 1 ? 0.1 :
                                (
                                    $scope.model.experiencePoint > 0 ? 0.05 :
                                        (
                                            $scope.model.experiencePoint <= 0 ? 0 : null
                                        )
                                )
                        )
                );
        $scope.model.oopExperienceES =
            $scope.model.oopExperiencePoint > 3 ? 1 :
                (
                    $scope.model.oopExperiencePoint > 2 ? 0.6 :
                        (
                            $scope.model.oopExperiencePoint > 1 ? 0.1 :
                                (
                                    $scope.model.oopExperiencePoint > 0 ? 0.05 :
                                        (
                                            $scope.model.oopExperiencePoint <= 0 ? 0 : null
                                        )
                                )
                        )
                )
        $scope.model.leaderExperienceES =
            $scope.model.leaderExperiencePoint > 3 ? 1 :
                (
                    $scope.model.leaderExperiencePoint > 2 ? 0.6 :
                        (
                            $scope.model.leaderExperiencePoint > 1 ? 0.1 :
                                (
                                    $scope.model.leaderExperiencePoint > 0 ? 0.05 :
                                        (
                                            $scope.model.leaderExperiencePoint <= 0 ? 0 : null
                                        )
                                )
                        )
                );
        $scope.model.motivateES =
            $scope.model.motivatePoint > 3 ? 1 :
                (
                    $scope.model.motivatePoint > 2 ? 0.6 :
                        (
                            $scope.model.motivatePoint > 1 ? 0.1 :
                                (
                                    $scope.model.motivatePoint > 0 ? 0.05 :
                                        (
                                            $scope.model.motivatePoint <= 0 ? 0 : null
                                        )
                                )
                        )
                );
        $scope.model.stableES =
            $scope.model.stablePoint > 3 ? 1 :
                (
                    $scope.model.stablePoint > 2 ? 0.6 :
                        (
                            $scope.model.stablePoint > 1 ? 0.1 :
                                (
                                    $scope.model.stablePoint > 0 ? 0.05 :
                                        (
                                            $scope.model.stablePoint <= 0 ? 0 : null
                                        )
                                )
                        )
                );
        $scope.model.partTimeUsageES =
            $scope.model.partTimeUsagePoint > 3 ? 1 :
                (
                    $scope.model.partTimeUsagePoint > 2 ? 0.6 :
                        (
                            $scope.model.partTimeUsagePoint > 1 ? 0.1 :
                                (
                                    $scope.model.partTimeUsagePoint > 0 ? 0.05 :
                                        (
                                            $scope.model.partTimeUsagePoint <= 0 ? 0 : null
                                        )
                                )
                        )
                );
        $scope.model.hardCodeES =
            $scope.model.hardCodePoint > 3 ? 1 :
                (
                    $scope.model.hardCodePoint > 2 ? 0.6 :
                        (
                            $scope.model.hardCodePoint > 1 ? 0.1 :
                                (
                                    $scope.model.hardCodePoint > 0 ? 0.05 :
                                        (
                                            $scope.model.hardCodePoint <= 0 ? 0 : null
                                        )
                                )
                        )
                );

        $scope.model.strES =    $scope.model.followOrderES +
            $scope.model.experienceES +
            $scope.model.oopExperienceES +
            $scope.model.leaderExperienceES +
            $scope.model.motivateES +
            $scope.model.stableES +
            $scope.model.partTimeUsageES +
            $scope.model.hardCodeES;

        $scope.model.strP =
            $scope.model.strES >= 3 ? 20 :
                (
                    $scope.model.strES >= 1 ? 32 :
                        (
                            $scope.model.strES < 1 ? 48 : null
                        )
                );
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

    function handleFinalPrice() {
        $scope.model.strUUCP = $scope.TAW + $scope.TBF;
        $scope.model.strAUCP = $scope.model.strUUCP * $scope.model.TCF * $scope.model.strEF;
        $scope.model.strAUCP = $scope.model.strAUCP.toFixed(4);
        $scope.model.strE = 10 / 6 * $scope.model.strAUCP;
        $scope.model.strE = $scope.model.strE.toFixed(4);
        $scope.model.strG = 1.4 * $scope.model.strE * $scope.model.strP * $scope.model.salaryPerHour;
        $scope.model.strG = Math.round($scope.model.strG);
    }

    $scope.actionSearch = async () => {
        $scope.submitForm();
        if($scope.validBeforeSearch() === true) {
            handleActor();
            
            handleBType();
            handleMType();
            handleTType();
            $scope.TBF = $scope.sumBPoint + $scope.sumMPoint + $scope.sumTPoint;

            handleResult();

            handleEFW_EF();
            handleES();

            handleSumSalary();

            handleFinalPrice();
            
            let sendData = {
                // BẢNG TÍNH TOÁN ĐIỂM CÁC TÁC NHÂN (ACTORS) TƯƠNG TÁC START
                // Output: TAW
                easy : $scope.model.easy.toString(),
                medium : $scope.model.medium.toString(),
                hard : $scope.model.hard.toString(),

                easyPoint: $scope.easyPoint.toString(),
                mediumPoint : $scope.mediumPoint.toString(),
                hardPoint : $scope.hardPoint.toString(),

                sum : $scope.sum.toString(),
                strTAW : $scope.TAW.toString(),
                // BẢNG TÍNH TOÁN ĐIỂM CÁC TÁC NHÂN (ACTORS) TƯƠNG TÁC START END

                // BẢNG TÍNH TOÁN ĐIỂM CÁC TRƯỜNG HỢP SỬ DỤNG START
                // Output: TBF
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
                // BẢNG TÍNH TOÁN ĐIỂM CÁC TRƯỜNG HỢP SỬ DỤNG END


                // BẢNG TÍNH TOÁN HỆ SỐ PHỨC TẠP KỸ THUẬT-CÔNG NGHỆ START
                // Output: TCF
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
                // BẢNG TÍNH TOÁN HỆ SỐ PHỨC TẠP KỸ THUẬT-CÔNG NGHỆ END

                // HỆ SỐ PTMT-KN-NỘI SUY START
                // Output: EFW, EF, ES, P
                followOrder : $scope.model.followOrder.toString(),
                experience : $scope.model.experience.toString(),
                oopExperience : $scope.model.oopExperience.toString(),
                leaderExperience : $scope.model.leaderExperience.toString(),
                motivate : $scope.model.motivate.toString(),
                stable : $scope.model.stable.toString(),
                partTimeUsage : $scope.model.partTimeUsage.toString(),
                hardCode : $scope.model.hardCode.toString(),

                followOrderPoint : $scope.model.followOrderPoint.toString(),
                experiencePoint : $scope.model.experiencePoint.toString(),
                oopExperiencePoint : $scope.model.oopExperiencePoint.toString(),
                leaderExperiencePoint : $scope.model.leaderExperiencePoint.toString(),
                motivatePoint : $scope.model.motivatePoint.toString(),
                stablePoint : $scope.model.stablePoint.toString(),
                partTimeUsagePoint : $scope.model.partTimeUsagePoint.toString(),
                hardCodePoint : $scope.model.hardCodePoint.toString(),

                followOrderES : $scope.model.followOrderES.toString(),
                experienceES : $scope.model.experienceES.toString(),
                oopExperienceES : $scope.model.oopExperienceES.toString(),
                leaderExperienceES : $scope.model.leaderExperienceES.toString(),
                motivateES : $scope.model.motivateES.toString(),
                stableES : $scope.model.stableES.toString(),
                partTimeUsageES : $scope.model.partTimeUsageES.toString(),
                hardCodeES : $scope.model.hardCodeES.toString(),

                strEFW : $scope.model.strEFW.toString(),
                strEF : $scope.model.strEF.toString(),
                strES : $scope.model.strES.toString(),
                strP : $scope.model.strP.toString(),
                // HỆ SỐ PTMT-KN-NỘI SUY END

                // BẢNG TÍNH LƯƠNG BÌNH QUÂN / NGƯỜI/ GIỜ START
                // Output: Mức lương lao động bình quân (H)
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
                // BẢNG TÍNH LƯƠNG BÌNH QUÂN / NGƯỜI/ GIỜ END

                // Xác định giá phần mềm
                strUUCP : $scope.model.strUUCP.toString(),
                strAUCP : $scope.model.strAUCP.toString(),
                strE : $scope.model.strE.toString(),
                strG : $scope.model.strG,
                
                "fileTempName" : 'bang_xac_dinh_gia_phan_mem',
                "fileType" : '.xlsx'
            }
            console.log('sendData', sendData);
            overLoading('Loading...');

            softwarePriceDetermination.exportFile(sendData, function (result, status, header, config) {
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