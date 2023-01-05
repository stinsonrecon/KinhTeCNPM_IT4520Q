let CONSTANT = {
    DATE_TIME_FORMAT : {
        TIME: "HH:mm:ss",
        DATE: "DD/MM/YYYY",
        DATE_TIME: "DD/MM/YYYY HH:mm:ss",
        WEEK: "WW",
        MONTH: "MM",
        QUARTER: "Q",
        YEAR: "YYYY",
        MONTH_YEAR: "MM/YYYY",
        WEEK_YEAR: "WW-YYYY",
        QUARTER_YEAR: "Q-YYYY",
    }
}


let sleepUntil = (ms) =>{
    return new Promise(resolve => setTimeout(resolve, ms));
}
let isNullOrEmptyUntil = (input) =>{
    return input === undefined || input === null || input === "";
}
let stringUtilNvlUntil = (val, nullValue) =>{
    if(val===undefined || val === null) return nullValue;
    return $.trim(val);
}
let isNumericUntil = (value) => {
    return /^\d+$/.test(value);
}
let convertViToEn = (value, toUpperCase = false) => {
    if(isNullOrEmptyUntil(value)){
        return value;
    }
    let str = String(value);
    str = str.toLowerCase();
    str = str.replace(/à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ/g, "a");
    str = str.replace(/è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ/g, "e");
    str = str.replace(/ì|í|ị|ỉ|ĩ/g, "i");
    str = str.replace(/ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ/g, "o");
    str = str.replace(/ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ/g, "u");
    str = str.replace(/ỳ|ý|ỵ|ỷ|ỹ/g, "y");
    str = str.replace(/đ/g, "d");
    // Some system encode vietnamese combining accent as individual utf-8 characters
    str = str.replace(/\u0300|\u0301|\u0303|\u0309|\u0323/g, ""); // Huyền sắc hỏi ngã nặng
    str = str.replace(/\u02C6|\u0306|\u031B/g, ""); // Â, Ê, Ă, Ơ, Ư
    return toUpperCase ? str.toUpperCase() : str;
}
let convertTimestampToDate = (value, format = CONSTANT.DATE_TIME_FORMAT.DATE , decimal = 1000) => {
    return moment.unix(value / decimal).format(format);
}

let convertStringToDate = (value, format)  => {
    if (value === null || value === undefined) {
        return null;
    }

    return moment(value, format);
}

let convertDateToString = (value, format)  => {
    if (value === null || value === undefined) {
        return null;
    }

    return moment(value).format(format);
}

let addDays = (strDate, day, format = CONSTANT.DATE_TIME_FORMAT.DATE)   =>{
    return moment(strDate, format).add(day, 'd').format(format);
}
let addMonths = (strDate, month, format = CONSTANT.DATE_TIME_FORMAT.DATE)  => {
    return moment(strDate, format).add(month, 'M').format(format);
}
let addYears = (strDate, year, format = CONSTANT.DATE_TIME_FORMAT.DATE)   =>{
    return moment(strDate, format).add(year, 'Y').format(format);
}
let getSysDate = (format = CONSTANT.DATE_TIME_FORMAT.DATE)   =>{
    return moment(new Date()).format(format);
}

let getSysDateTime = ()   =>{
    return moment(new Date()).format(CONSTANT.DATE_TIME_FORMAT.DATE_TIME);
}

/*
value: giá trị date dạng string,
fromFormart: format hiện tại của value
toFormat: format muốn đổi thành
*/
let changeFormatDateString = (value, fromFormart, toFormat)   =>{
    if (value === null || value === undefined) {
        return null;
    }

    return moment(moment(value, fromFormart)).format(toFormat);
}

/*
date1 date2: string,
format: định dạng của date1 và date2
typeRsult: loại giá trị trả vể, 1 trong các loại sau: days | months | years | hours | minutes | seconds
isFloat: Lấy cả phần thập phân
Kết quả trả về là date1 - date2
*/
let dateDiff =  (
    date1,
    date2,
    format = CONSTANT.DATE_TIME_FORMAT.DATE,
    typeRsult = 'days',
    isFloat = false
)   =>{
    if (
        date1 === null ||
        date1 === undefined ||
        date2 === null ||
        date2 === undefined
    ) {
        return null;
    }

    let valueDate1 = convertStringToDate(date1, format);
    let valueDate2 = convertStringToDate(date2, format);

    return valueDate1.diff(valueDate2, typeRsult, isFloat);
}

//d: Mon | Tue | Wed | Thu | Fri | Sat | Sun
//w: tuần
//y: năm
//Trả về ngày dạng string theo formatDate
let getDayOfWeek = (d, w, y, formatDate)   =>{
    const dayName = ['mon', 'tue', 'wed', 'thu', 'fri', 'sat', 'sun'];

    let indexDay = dayName.indexOf(String(d).toLowerCase());

    const currentMoment = moment();
    if (currentMoment.locale() === 'vi') {
        indexDay = indexDay + 1;
    }

    const day = moment(`${w}-${y}`, 'ww-YYYY').day(indexDay);

    return convertDateToString(day, formatDate);
}

//d: số thự tự của ngày trong tháng
//w: tuần
//y: năm
//Trả về ngày dạng string theo formatDate
let getDayOfMonth = (d, m, y, formatDate)   =>{
    let lastDay = moment(m + '-' + y, 'MM/YYYY').daysInMonth();

    if (Number(d) > lastDay) {
        d = lastDay;
    }

    let reuslt = moment(d + '/' + m + '/' + y, 'DD/MM/YYYY');

    return convertDateToString(reuslt, formatDate);
}

// example 1: isGreaterThan('12/07/2001', '11/07/2001') => return true
// example 2: isGreaterThan({
//     d1: {
//         value: '2001/07/12 12:30:00',
//         format: 'YYYY/MM/DD HH:mm:ss'
//     },
//     d2: {
//         value: '12/07/2001 15:00:00',
//         format: 'DD/MM/YYYY HH:mm:ss'
//     }
// }); => return false
let isGreaterThan = ()   =>{
    if (
        arguments.length > 1 &&
        typeof arguments[0] === 'string' &&
        typeof arguments[1] === 'string'
    ) {
        const arg1 = arguments[0];
        const arg2 = arguments[1];

        const d1 = moment(
            arg1,
            arg1.length === 'DD/MM/YYYY'.length
                ? 'DD/MM/YYYY'
                : arg1.length === 'DD/MM/YYYY HH:mm:ss'.length
                    ? 'DD/MM/YYYY HH:mm:ss'
                    : ''
        );

        const d2 = moment(
            arg2,
            arg2.length === 'DD/MM/YYYY'.length
                ? 'DD/MM/YYYY'
                : arg2.length === 'DD/MM/YYYY HH:mm:ss'.length
                    ? 'DD/MM/YYYY HH:mm:ss'
                    : ''
        );

        if (d1 === 'Invalid date' || d2 === 'Invalid date') {
            throw new Error('Ngày truyền vào không hợp lệ');
        }

        return d1 > d2;
    } else if (arguments.length === 1 && typeof arguments[0] === 'object') {
        const obj = arguments[0];

        const d1 = moment(obj.d1.value, obj.d1.format);
        const d2 = moment(obj.d2.value, obj.d2.format);

        if (d1 === 'Invalid date' || d2 === 'Invalid date') {
            throw new Error('Ngày truyền vào không hợp lệ');
        }

        return d1 > d2;
    } else {
        return false;
    }
}

let firstDayOfMonth = (format = CONSTANT.DATE_TIME_FORMAT.DATE)   =>{
    const date = new Date();
    const firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
    return moment(firstDay).format(format);
}

let getNameServiceById = (serviceCode,type) =>{
    let serviceName = "";
    if("CHPRE" === type){
        switch (serviceCode) {
            case "1" :
                serviceName = "Thay sim";
                break;
            case "2" :
                serviceName = "Chuyển chủ quyền";
                break;
            case "3" :
                serviceName = "Cập nhật thông tin cá nhân";
                break;
            case "4" :
                serviceName = "Chặn 1 chiều do mất máy";
                break;
            case "5" :
                serviceName = "Nối lại thông tin (mở lại mạng)";
                break;
            case "6" :
                serviceName = "Chuyển thuê bao trả trước sang trả sau";
                break;
            case "7" :
                serviceName = "Thay đổi dịch vụ VAS";
                break;
            case "8" :
                serviceName = "Dịch vụ khác";
                break;
            case "21" :
                serviceName = "Thay đổi số thuê bao";
                break;
            case "22" :
                serviceName = "Thay eSim";
                break;
            case "23" :
                serviceName = "Chuyển chủ quyền đơn phương";
                break;
        }
    }else  if("CHPRE" === type){
        switch (serviceCode) {
            case "9" :
                serviceName = "Thay sim";
                break;
            case "10" :
                serviceName = "Chuyển chủ quyền";
                break;
            case "11" :
                serviceName = "Thay đổi địa chỉ gửi thông báo thanh toán cước";
                break;
            case "12" :
                serviceName = "Chặn 1 chiều";
                break;
            case "13" :
                serviceName = "Chặn 2 chiều";
                break;
            case "14" :
                serviceName = "Nối lại thông tin khi chặn 1 chiều";
                break;
            case "15" :
                serviceName = "Nối lại thông tin khi chặn 2 chiều";
                break;
            case "16" :
                serviceName = "Chuyển thuê bao trả sau sang trả trước";
                break;
            case "17" :
                serviceName = "Thay đổi dịch vụ VAS";
                break;
            case "18" :
                serviceName = "Thay đổi gói cước";
                break;
            case "19" :
                serviceName = "In lại bản thông báo cước";
                break;
            case "20" :
                serviceName = "Dịch vụ khác";
                break;
        }
    }
    return serviceName;
}
let getStatusNameById = (status) =>{
    let statusName = "";
    switch (status) {
        case "0" :
            statusName = "Tạo mới";
            break;
        case "2" :
            statusName = "Đã thực hiện";
            break;
        case "3" :
            statusName = "Hủy";
            break;
        case "5" :
            statusName = "Đã review";
            break;
    }

    return statusName;
}
