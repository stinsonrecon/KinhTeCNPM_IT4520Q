const STATE_DEFAULT = "DEFAULT";
const STATE_ADD = "ADD";
const STATE_SEARCH = "SEARCH";
const STATE_UPLOAD = "UPLOAD";

app_hust.factory('heSo', function ($http, $translate) {
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
app_hust.controller('ctr-heSo', function ($scope, $rootScope, $translate, $compile, $timeout, $uibModal,
                                                                        $location, $window, FileUploader, $filter, $http,
                                                                        NgTableParams, $localStorage,heSo,_) {
    $scope.memberStat = $translate.instant("hust.heSo.label.memberStat");
    $scope.projectStat = $translate.instant("hust.heSo.label.projectStat");

    $scope.model = {
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

        if (isNullOrEmpty(dataCheck.followOrder))
        {
            bootboxAlertFocus($translate.instant("hust.heSo.message.error.followOrderInvalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }
        if (isNullOrEmpty(dataCheck.experience))
        {
            bootboxAlertFocus($translate.instant("hust.heSo.message.error.experienceInvalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }
        if (isNullOrEmpty(dataCheck.oopExperience))
        {
            bootboxAlertFocus($translate.instant("hust.heSo.message.error.oopExperienceInvalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }
        if (isNullOrEmpty(dataCheck.leaderExperience))
        {
            bootboxAlertFocus($translate.instant("hust.heSo.message.error.leaderExperienceInvalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }
        if (isNullOrEmpty(dataCheck.motivate))
        {
            bootboxAlertFocus($translate.instant("hust.heSo.message.error.motivateInvalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }
        if (isNullOrEmpty(dataCheck.stable))
        {
            bootboxAlertFocus($translate.instant("hust.heSo.message.error.stableInvalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }
        if (isNullOrEmpty(dataCheck.partTimeUsage))
        {
            bootboxAlertFocus($translate.instant("hust.heSo.message.error.partTimeUsageInvalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }
        if (isNullOrEmpty(dataCheck.hardCode))
        {
            bootboxAlertFocus($translate.instant("hust.heSo.message.error.hardCodeInvalid"),
                "", $translate.instant("hust.lable.hust.name"), "");
            return false;
        }
        return true;
    };

    $scope.isFieldValid = (field) => {
        return isNullOrEmpty($scope.model[field]) && $scope.frm_heSo[field]?.$touched ;
    };

    $scope.submitForm = () => {
        $scope.frm_heSo['followOrder']?.$setTouched();
        $scope.frm_heSo['experience']?.$setTouched();
        $scope.frm_heSo['oopExperience']?.$setTouched();
        $scope.frm_heSo['leaderExperience']?.$setTouched();
        $scope.frm_heSo['motivate']?.$setTouched();
        $scope.frm_heSo['stable']?.$setTouched();
        $scope.frm_heSo['partTimeUsage']?.$setTouched();
        $scope.frm_heSo['hardCode']?.$setTouched();
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

    $scope.actionSearch = async () => {
        $scope.submitForm();
        if($scope.validBeforeSearch() === true) {
            handleEFW_EF();
            handleES();

            let sendData = {
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

                "fileTempName" : 'he_so_PTMT_KN_noi_suy',
                "fileType" : '.xlsx'
            }

            console.log('senData', sendData);

            overLoading('Loading...');

            heSo.exportFile(sendData, function (result, status, header, config) {
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