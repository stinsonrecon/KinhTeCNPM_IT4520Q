var MAX_LENGTH_SUBSCRIBER_NUMBER = 10;
var MIN_LENGTH_SUBSCRIBER_NUMBER = 9;

var MAX_YEAR_DOCUMENT = -15;

var PRESENT_YEAR = 0;

var MIN_YEAR_BIRTHDAY = -14;
var MAX_YEAR_BIRTHDAY = -100;

var EMPTY_VALUE  = "";
var BREAK_LINE = "<br/>";

App.jFilter = function (response) {
}
App.popupCenter = function (url, title, w, h) {
    var dualScreenLeft = window.screenLeft != undefined ? window.screenLeft : screen.left;
    var dualScreenTop = window.screenTop != undefined ? window.screenTop : screen.top;
    var width = window.innerWidth ? window.innerWidth : document.documentElement.clientWidth ? document.documentElement.clientWidth : screen.width;
    var height = window.innerHeight ? window.innerHeight : document.documentElement.clientHeight ? document.documentElement.clientHeight : screen.height;
    var left = ((width / 2) - (w / 2)) + dualScreenLeft;
    var top = ((height / 2) - (h / 2)) + dualScreenTop;
    var newWindow = window.open(url, title, 'scrollbars=yes, width=' + w + ', height=' + h + ', top=' + top + ', left=' + left);
    if (window.focus) {
        newWindow.focus();
    }
}

 
$(document).ready(function () { 
      
});

function bootboxAlertTitle(tilte, listMessage){
	var dialog = bootbox.dialog({
		title: tilte,
		message: listMessage,
		buttons: {
		    cancel: {
		        label: "OK",
		        className: 'btn-danger'
		    }
		}
		});
}

function bootboxConfirm(title, message, callBackOK, callbackKO){
	var resultConfirm = false;
	
	bootbox.confirm({
	    title: title,
	    message : "<i class='fa fa-question-circle' style='font-size:20px; color:orange; margin-right:20px;'></i>" +message,
	    buttons: {
			cancel: {
						label: '<i class="fa fa-times "></i> Hủy   ',
						className: 'btn-primary pull-right  margin-left-10px button-customer-confirm'
					},
	        confirm: {
	            label: '<i class="fa fa-check "></i> Đồng ý',
	            className: 'btn-primary button-customer-confirm'
	            
	        }
	    },
	    callback: function (result) {
	    	if(result){
	    		callBackOK();
	    	}else{
	    		callbackKO();
	    	}
	    }
	}).find(".modal-dialog").css({
		'width': '450px',
	    'margin-top': function() {
	        var w = $( window ).height();
//	        var b = $(".modal-dialog").height();
	        var h = w/5;
	        return h+"px";
	    }
	});
	
	return resultConfirm;
}

function bootboxConfirmWithTextButton(title, message, callBackOK, callbackKO, textBtnOK, textBtnCancel){
	var resultConfirm = false;
	
	bootbox.confirm({
	    title: title,
	    message: message,
	    buttons: {
			cancel: {
						label: '<i class="fa fa-times "></i> '+textBtnCancel,
						className: 'btn-primary pull-right  margin-left-10px ',
						style: 'width: 90px'
					},
	        confirm: {
	            label: '<i class="fa fa-check "></i> '+textBtnOK,
	            className: 'btn-primary button-customer-confirm',
				style: 'width: 100px'
	            
	        }
	    },
	    callback: function (result) {
	    	if(result){
	    		callBackOK();
	    	}else{
	    		callbackKO();
	    	}
	    }
	});
	
	return resultConfirm;
}

function bootboxConfirm3(title, message, callBackOK, callbackKO, callbackCancel){
	
	var resultConfirm = bootbox.dialog({
	    title: title,
	    message : "<i class='fa fa-question-circle' style='font-size:20px; color:orange; margin-right:20px;'></i>" +message,
	    buttons: {
	    	noclose: {
	            label: '<i class="glyphicon glyphicon-ban-circle "></i> Bỏ qua   ',
				className: 'btn-primary pull-right  margin-left-10px button-customer-confirm',
	            callback: function(){            	
	            	callbackKO();
	            }
	        },
	        cancel: {
	            label: '<i class="fa fa-times "></i> Hủy   ',
	            className: 'btn-primary pull-right  margin-left-10px button-customer-confirm',
	            callback: function(){
	            	callbackCancel();
	            }
	        },
	        
	        ok: {
	            label: '<i class="fa fa-check "></i> Đồng ý',
	            className: 'btn-primary button-customer-confirm',
	            callback: function(){
	            	callBackOK();
	            }
	        }
	    }
	}).find(".modal-dialog").css({
		'width': '450px',
	    'margin-top': function() {
	        var w = $( window ).height();
//	        var b = $(".modal-dialog").height();
	        var h = w/5;
	        return h+"px";
	    }
	});
	return resultConfirm;
}


function ConvertFileNameNoneUTF8(str){
	if (typeof str != 'string')
		return null;
	
	str = str.replace(/(á|à|ả|ã|ạ|ă|ắ|ằ|ẳ|ẵ|ặ|â|ấ|ầ|ẩ|ẫ|ậ)/g, 'a');
	str = str.replace(/(A|À|Ả|Ã|Ạ|Ă|Ắ|Ằ|Ẳ|Ẵ|Ặ|Â|Ấ|Ầ|Ẩ|Ẫ|Ậ)/g, 'A');
	str = str.replace(/đ/g, 'd');
	str = str.replace(/Đ/g, 'D');
	str = str.replace(/(é|è|ẻ|ẽ|ẹ|ê|ế|ề|ể|ễ|ệ)/g, 'e');
	str = str.replace(/(É|È|Ẻ|Ẽ|Ẹ|Ê|Ế|Ề|Ể|Ễ|Ệ)/g, 'E');
	str = str.replace(/(í|ì|ỉ|ĩ|ị)/g, 'i');
	str = str.replace(/(Í|Ì|Ỉ|Ĩ|Ị)/g, 'I');
	str = str.replace(/(ó|ò|ỏ|õ|ọ|ô|ố|ồ|ổ|ỗ|ộ|ơ|ớ|ờ|ở|ỡ|ợ)/g, 'o');
	str = str.replace(/(Ó|Ò|Ỏ|Õ|Ọ|Ô|Ố|Ồ|Ổ|Ỗ|Ộ|Ơ|Ớ|Ờ|Ở|Ỡ|Ợ)/g, 'O');
	str = str.replace(/(ú|ù|ủ|ũ|ụ|ư|ứ|ừ|ử|ữ|ự)/g, 'u');
	str = str.replace(/(Ú|Ù|Ủ|Ũ|Ụ|Ư|Ứ|Ừ|Ử|Ữ|Ự)/g, 'U');
	str = str.replace(/(ý|ỳ|ỷ|ỹ|ỵ)/g, 'y');
	str = str.replace(/(Ý|Ỳ|Ỷ|Ỹ|Ỵ)/g, 'Y');

	return str;
};

//function return false if dateStart greater endDate
function checkDateIsAfterDate(startDateIn, endDateIn){
	if($.trim(startDateIn) == "" || $.trim(endDateIn) == ""){
		return true;
	}
	
	var dateStart = moment(startDateIn, "DD/MM/YYYY");
	var dateEnd  = moment(endDateIn, "DD/MM/YYYY");
	
	if(dateStart > dateEnd){
		return false;
	}
	
	return true;
}

//return true if str is number
function isNumbericInteger(strNumberIn) {
	var strNumber = StringUtilNVL(strNumberIn);
	if(strNumber == ""){
		return false;
	}
	
    return /^\+?([0-9]\d*)$/.test(strNumber);
}


//return true if str is letter and space
function isLetterAndSpaceUnicode(strLetterIn) {
	var strLetter = ConvertFileNameNoneUTF8(StringUtilNVL(strLetterIn).toLocaleLowerCase());
	if(strLetter == ""){
		return false;
	}
	
    return /^[a-zA-Z\s]*$/.test(strLetter);
}


//return true if str is letter and without space
function isLetterAndNumber(strLetterIn) {
	var strLetter = StringUtilNVL(strLetterIn);
	if(strLetter == ""){
		return false;
	}
	
    return /^[0-9a-zA-Z]+$/.test(strLetter);
}

//return true if str is letter and space
function isLetterAndNumberWithSpace(strLetterIn) {

	var strLetter = ConvertFileNameNoneUTF8(StringUtilNVL(strLetterIn).toLocaleLowerCase());
	if(strLetter == ""){
		return false;
	}
	
    return /^[0-9a-zA-Z,.!-\s]*$/.test(strLetter);
    
}

//return true if string length < lengthInput
function checkLengthStringInput(strInput, lengthIn){
	var strLetter = StringUtilNVL(strInput);
	if(strLetter.length > lengthIn){
		return false;
	}
	return true;
}

function StringUtilNVL(val) {
	if (val == undefined || val == null)
		return "";
	return $.trim(val);
}

//validate msisdn return true if validate OK.
function validateMSISDN(msisdnInput){
	var resultValidateMSISDN = true;
	
	var msisdn = StringUtilNVL(msisdnInput);
	var msisdnLength = msisdn.length;
	
	if(msisdnLength > MAX_LENGTH_SUBSCRIBER_NUMBER || msisdnLength < MIN_LENGTH_SUBSCRIBER_NUMBER){
		resultValidateMSISDN = false;
	}
	
	if(!isNumbericInteger(msisdn)){
		resultValidateMSISDN = false;
	}
	
	return resultValidateMSISDN;
}

//validate ngay sinh
function validateBirthDay(dateInput){
	var resultValidateBirthday = true;
	if(checkDateGreaterThanYear(dateInput, PRESENT_YEAR)){
		resultValidateBirthday = false;
	}
	
	if(checkDateGreaterThanYear(dateInput, MIN_YEAR_BIRTHDAY)){
		resultValidateBirthday = false;
	}
	
	if(checkDateLessThanYear(dateInput, MAX_YEAR_BIRTHDAY)){
		resultValidateBirthday = false;
	}
			
	return resultValidateBirthday;
}

//validate ngay cấp,
//return true, nếu ngày cấp không lớn hơn ngày hiện tại, và không nhỏ hơn 15 năm.
function validateDocumentIssueDate(dateInput){
	var resultValidateDocIssueDate = true;
	if(checkDateGreaterThanYear(dateInput, PRESENT_YEAR)){
		resultValidateDocIssueDate = false;
	}
	
	if(checkDateLessThanYear(dateInput, MAX_YEAR_DOCUMENT)){
		resultValidateDocIssueDate = false;
	}
	
	return resultValidateDocIssueDate;
}

//kiểm tra nếu ngày lớn hơn(>) ngày cách ngày hiện tại (số) năm
//trả về true nếu ngày lớn hơn
function checkDateGreaterThanYear(dateInput, year){
	var date = StringUtilNVL(dateInput);
	var dayInPastYear = moment().add(year, 'years');
    var checkResult =  moment(date, "DD/MM/YYYY")  >= moment(dayInPastYear, "DD/MM/YYYY");
    return checkResult;
}

//kiểm tra nếu ngày nhỏ hơn(<) ngày cách ngày hiện tại (số) năm
//trả về true nếu ngày nhỏ hơn
function checkDateLessThanYear(dateInput, year){
	var date = StringUtilNVL(dateInput);
	var dayInPastYear = moment().add(year, 'years');
	var checkResult =   moment(date, "DD/MM/YYYY") <=  moment(dayInPastYear, "DD/MM/YYYY");
	
	return checkResult;
}

//formate msisdn with 0 and +84
function formatMsisdnWithoutZero(msisdn) {
	var msisdnValue = null;

	if (msisdn == null ||  $.trim(msisdn) == "") {
		return msisdnValue;
	}

	msisdn =  $.trim(msisdn)
	if (msisdn.startsWith("0")) {
		msisdnValue = msisdn.substring(1);
	} else if (msisdn.startsWith("84")) {
		msisdnValue = msisdn.substring(2);
	} else if(msisdn.startsWith("+84")){
		msisdnValue = msisdn.substring(3);
	}
	else {
		msisdnValue = msisdn;
	}

//	if (msisdnValue.length < 9 || msisdnValue.length > 10) {
//		msisdnValue = null;
//	}
	
	return msisdnValue;
}

function isDateValidate(dateIn){
	 var valueDate = $.trim(dateIn);
	 return moment(valueDate, 'DD/MM/YYYY',true).isValid();
}

function overLoading(message){
	App.blockUI({
		message: message
	});
	$(".blockOverlay").css("background-color", "#000000");
	$(this).css("opacity", "1");
}

function overLoadingPopup(id, message,isProgress){
	App.blockUI({
		target: id,
		boxed : false,
		message: message,
		isProgress: isProgress
	});
	$(".blockOverlay").css("background-color", "#000000");
	$(this).css("opacity", "1");
}

function closeOverLayPopup(id){
	App.unblockUI(id);
}

function closeOverLay(){
	$.unblockUI();
}

function setStrongLabel(messageIn){
	var result = "<strong> "+messageIn+ "</strong> ";
	return result;
}

function separateName(fullName){
	var res = fullName.split(" ");
	var lastName = $.trim(res[res.length-1]);
	var firstName = $.trim(fullName.replace(lastName, ""));
	
	var fileNameObject = {
			'firstName': firstName, 
			'lastName': lastName
	};
	return fileNameObject;
}

function bootboxAlertInformation(title, message){
	bootbox.alert({ 
		  size: "medium",
		  title: title,
		  message: message, 
		  callback: function(){ 
			 }
		})
}

function setStrongLabelWithTitle(titleIn ,messageIn){
	
	var result = "<div align='center'> " +
			"<strong> "+titleIn+ "</strong> " 
				+BREAK_LINE+ messageIn +"</div>";
	return result;
}

function removeErrorClassElement(divElementId){
	$( "#"+divElementId).removeClass( "has-error" );
    $("#"+divElementId+"-error").remove();
}

function setErrorClassElement(divElementId, divMessageError){
    var strHtml = "";
    var divErrorMessage = divElementId+"-error";
    
    strHtml += "<label id='"+divErrorMessage+"' class='help-block'>";
    strHtml += divMessageError
    strHtml += "</label>";

    if(!$("#"+divElementId).hasClass("has-error")){
    	$("#"+divElementId).addClass("has-error").append(strHtml);
    	$("#"+divErrorMessage).show();
    }
}

//check field is not empty
//return true if field not empty
//return false if field empty
function StringUtilNVLIsNotEmpty(val){
	if(val==undefined || val == null || $.trim(val)=="") return false;
	return true;
}

function checkModelVisible(model){
	if(model==undefined || model == null) return true;
	return false;
}

function createTimeStamp(){
	var theMoment = moment();
	var millisTimeStamp = theMoment.valueOf();
	return millisTimeStamp;
}

function bootboxAlertFocus(message, focusId, title, icon) {
	var mesIcon = 'fa fa-exclamation-circle';
	var color = 'orange';
	if (icon != null && icon != undefined && icon != '') {
		if (icon == 'success') {
			mesIcon = 'fa fa-check-circle';
		}
		if (icon == 'LimitedReached') {
			mesIcon = 'fa fa-times-circle';
			color = 'red';
		}
	}
	bootbox.alert({
		size : "medium",
		message : "<i class='"+mesIcon+"' style='font-size:20px; color:" +color+ "; margin-right:20px;'></i>" +message,
		title : title,
	}).on('hidden.bs.modal', function(event) {
		var myEl = document.getElementById(focusId);
		var angularEl = angular.element(myEl);
		angularEl.focus();
	}).find(".modal-dialog").css({
//	    'background-color': '#f99',
//	    'font-weight' : 'bold',
//	    'color': '#F00',
//	    'font-size': '2em',transferToShop
		//700px
	    'width': function() {
		    let wSceen = $( window ).width();
		    var wDialog = wSceen/2.5;
		    return wDialog+"px";
	    },
	    'margin-top': function() {
	        var w = $( window ).height();
//	        var b = $(".modal-dialog").height();
	        var h = w/20;
	        return h+"px";
	    },
		'min-width': '200px',
		'margin-left': 'auto',
		'margin-right': 'auto'
	});
};

function bootboxConfirmCountDown(id,title, message, callBackOK, callbackKO){
	var resultConfirm = false;

	bootbox.confirm({
		id: id,
		title: title,
		message : "<i class='fa fa-question-circle' style='font-size:20px; color:orange; margin-right:20px;'></i>" +message ,
		buttons: {
			cancel: {
				label: '<i class="fa fa-times "></i> Hủy   ',
				className: 'btn-primary pull-right  margin-left-10px button-customer-confirm'
			},
			confirm: {
				label: '<i class="fa fa-check "></i> Đồng ý',
				className: 'btn-primary button-customer-confirm'

			}
		},
		callback: function (result) {
			if(result){
				callBackOK();
			}else{
				callbackKO();
			}
		}
	}).find(".modal-dialog").css({
		'width': '650px',
		'z-index' : '100000'
	});
	document.getElementById(id).style.background = "rgba(0,0,0,.5)"
	return resultConfirm;
}
