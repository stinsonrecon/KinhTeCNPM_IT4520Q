var eim_url = "http://localhost:8088";  


app_hust.factory('ctk_service', function ($http) { 
    return { 
    	create_ticket: function (data, callback) {
            $http.post(eim_url+ '/ticket', data).success(callback);
        }
    }
});

app_hust.controller('ctrl-ticket', function ($scope, $rootScope ,$translate,$compile,$timeout, $uibModal, $location, $window,FileUploader,$filter, ctk_service) {	
	
	
	 $scope.model={};  
	 $scope.validationOptions = {
			  debounce: 1500,               
			    preValidateFormElements: true,
        rules: {
        	SubscriberName: {
                required: true,
                maxlength: 255
            },
            NPRegistrationName: {
                required: true,
                maxlength: 255
            },
            DocumentNumber: {
                required: true,
                maxlength: 11
            },
            DocumentIssueDate: {
                required: true,
                maxlength: 11
            },
            DocumentIssuePlace: {
                required: true,
                maxlength: 11
            },
            SubscriberRepresentative: {
                required: true,
                maxlength: 255
            }
        },
        messages: {
        	SubscriberName: {
                required:$translate.instant('hust.messages.validate.subscriberName.required'), 
                maxlength: "Tên thuê bao không vượt quá 255 ký tự."
            },
            NPRegistrationName: {
                required: "Yêu cầu nhập tên đăng ký NP",
                maxlength: "Tên đăng ký NP không vượt quá 255 ký tự."
            },
            DocumentNumber: {
                required: "Yêu cầu nhập số định danh",
                maxlength: "Số định danh không vượt quá 11 ký tự."
            },
            DocumentIssueDate: {
                required: "Yêu cầu nhập ngày cấp định danh",
                maxlength: "Ngày cấp định danh không vượt quá 11 ký tự."
            },
            DocumentIssuePlace: {
                required: "Yêu cầu nhập nơi cấp định danh",
                maxlength: "Nơi cấp định danh không vượt quá 11 ký tự."
            },
            SubscriberRepresentative: {
                required: "Yêu cầu nhập tên đại diện thuê bao",
                maxlength: "Tên đại diện thuê bao không vượt quá 255 ký tự."
	            }
	        }
	    } 
	
 
    $scope.DocumentTypeSource  = [
        { 'Id': '1', 'Title': 'Chứng minh thư' },
        { 'Id': '2', 'Title': 'Thẻ căn cước' } 
    ];
    $scope.DonorSource  = [
        { 'Id': '1', 'Title': 'Viettel' },
        { 'Id': '2', 'Title': 'Vinaphone' },
        { 'Id': '3', 'Title': 'Mobifone' } ,
        { 'Id': '4', 'Title': 'Gtel' } 
    ]; 
    
    $scope.CitizenSource  = [
        { 'Id': 'VN', 'Title': 'Việt Nam' },
        { 'Id': 'USA', 'Title': 'United States' } 
    ]; 
    $scope.ProvinceSource  = [
        { 'Id': '1', 'Title': 'Hà Nội' },
        { 'Id': '2', 'Title': 'TP. Hồ Chí Minh' },
        { 'Id': '3', 'Title': 'Đà Nẵng' } 
    ]; 
    $scope.DistrictSource  = [
        { 'Id': '1', 'Title': 'Cầu giấy' },
        { 'Id': '2', 'Title': 'Hoàn kiếm' },
        { 'Id': '3', 'Title': 'Từ Liêm' } 
    ]; 
    
    
    $scope.model.fileAttachments = [];
    var uploader = $scope.uploader = new FileUploader({
        url: eim_url+'/ticket/upload'
    });
    uploader.onSuccessItem = function (fileItem, response, status, headers) {
        $scope.model.fileAttachments.push(response);
    };
    
    
    $scope.cancel = function () {
         
    }
    $scope.save = function () {  
    	
    	 if ($scope.frm_createticket.validate()) {
	    	 if (uploader.queue.length > 0) {
	             uploader.uploadAll();
	             uploader.onCompleteAll = function () {
	            	 ctk_service.create_ticket($scope.model, function (rs) {
	                     console.log(rs);return;
	                 });
	             };
	         } else {
	        	 ctk_service.create_ticket($scope.model, function (rs) {
	                 console.log(rs);return;
	             });
	         } 
    	 }
    	 
    }
    
    
    

});