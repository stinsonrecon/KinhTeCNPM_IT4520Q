package vn.com.hust.utils;

public class CommonConstant
{

	public static final String BEGIN_LOG = " -- BEGIN -----------";
	public static final String END_LOG = " -- END ---------------";
	public static final String PARAMETER = " -PARAM INPUT -";
	public static final String ERROR_LOG = "ERROR:";

	public static final int CHANNEL_ID = 10;

	public static final String SUCCESS_TEXT = "SUCCESS";

	public static final String UN_SUCCESS_TEXT = "UNSUCCESS";

	public static final String UN_SUCCESS_INT = "-1";

	public static final String CHECK_NULL_INPUT = "-1";
	public static final String EMPTY_STRING = ""; // SimpleDateFormat MM/DD/YY
	public static final String MMDDYYYY = "mm/DD/yyyy";
	public static final String ddMMyyyy = "dd/MM/yyyy";
	public static final String YYYYMMDD_S = "yyyy-MM-dd HH:mm:ss.S";
	public static final String POCODE_PREFIX = "PO";
	public static final String POCODE_PREFIX_OR = "OR";
	public static final String ORDER_TYPE_CODE = "Order";
	public static final int SEQ_LENGTH = 6;

	public static final int STATUS_DEFAULT = 0;
	public static final int STATUS_APPROVE = 1;
	public static final int STATUS_REJECT = 3;
	public static final int STATUS_PENDING = 2;

	public static final String UNKNOW_ERROR_CODE = "9999";
	public static final String CREATE_TRANSID_ERROR_CODE = "ERR-0001";
	public static final String CREATE_TRANSID_ERROR_DES = "Error when create transactionID,StockExportForStaff";
	public static final String IE_STAFF_ERROR_CODE = "ERR-0002";
	public static final String IE_STAFF_ERROR_DES = "Error when import goods to Stock of Employee,StockExportForStaff";
	public static final String IE_SERIAL_STAFF_ERROR_CODE = "ERR-0003";
	public static final String IE_SERIAL_STAFF_ERROR_DES = "Error when import goods serial to Stock of Employee,StockExportForStaff";
	public static final String SUCCESS_TIBCO = "00000";
	public static final String NOT_FOUND_ANY_STOCK_ERROR_CODE = "ERR-0004";
	public static final String NOT_FOUND_ANY_STOCK_ERROR_DES = "Could not found any stock";
	public static final String GET_ALL_GOOD_ERROR_CODE = "ERR-0005";
	public static final String GET_ALL_GOOD_ERROR_DES = "Could not found any good on stock";
	public static final String GET_GOOD_ONSTOCK_ERROR_CODE = "ERR-0015";
	public static final String GET_GOOD_ONSTOCK_ERROR_DES = "Errro occurred when get goods on stock";
	public static final String GET_INTERNAL_STOCK_ERROR_CODE = "ERR-0006";
	public static final String GET_INTERNAL_STOCK_ERROR_DES = "Error occurred when find type of goods";
	public static final String NOT_FOUND_INTERNAL_STOCK_ERROR_CODE = "ERR-0007";
	public static final String NOT_FOUND_INTERNAL_STOCK_ERROR_DES = "Could not found any type of goods";
	public static final String GET_GOOD_DETAIL_ERROR_CODE = "ERR-0008";
	public static final String GET_GOOD_DETAIL_ERROR_DES = "Error occurred when get good detail";
	public static final String NOT_FOUND_GOOD_DETAIL_ERROR_CODE = "ERR-0009";
	public static final String NOT_FOUND_GOOD_DETAIL_ERROR_DES = "Could not found any GOOD_DETAIL";
	public static final String GET_SERIAL_ERROR_CODE = "ERR-0010";
	public static final String GET_SERIAL_ERROR_DES = "Error occurred when get serial";

	public static final String INPUT_SERIAL_ERROR_CODE = "ERR-0011";
	public static final String INPUT_SERIAL_ERROR_DES = "The value of input serial is invalid, fromSerial must be less than toSerial";
	public static final String CONFIRM_SHIPMENT_GETDETAIL_ERROR_CODE = "ERR-0012";
	public static final String CONFIRM_SHIPMENT_GETDETAIL_ERROR_DES = "Error occured when get shipment detail";
	public static final String CONFIRM_SHIPMENT_IMPORT_ERROR_CODE = "ERR-0013";
	public static final String CONFIRM_SHIPMENT_IMPORT_ERROR_DES = "Error occured when import goods";
	public static final String CONFIRM_SHIPMENT_GETINFOR_ERROR_CODE = "ERR-0014";
	public static final String CONFIRM_SHIPMENT_GETINFOR_ERROR_DES = "Error occured when get shipment";

	public static final String STAFF_ONSTOCK_GETINFOR_ERROR_CODE = "ERR-0016";
	public static final String STAFF_ONSTOCK_GETINFOR_ERROR_DES = "Error occured when get staff infor";
	public static final String CONFIRM_SHIPMENT_NOT_FOUND_ERROR_CODE = "ERR-0017";
	public static final String CONFIRM_SHIPMENT_NOT_FOUND_ERROR_DES = "Not found any shipment";
	public static final String NOT_FOUND_GOOD_ERROR_CODE = "ERR-0018";
	public static final String NOT_FOUND_GOOD_ERROR_DES = "Could not found any goods";

	public static final String EXPORT_SERIAL_ERROR_CODE = "ERR-0019";
	public static final String EXPORT_SERIAL_ERROR_DES = "Each good must have a range of serial number.";

	public static final String CREATE_REPORT_ERROR_CODE = "ERR-0020";
	public static final String CREATE_REPORT_ERROR_DES = "Error occured when generate report.";
	public static final String CREATE_REPORT_INV_FILETYPE_ERROR_CODE = "ERR-0021";
	public static final String CREATE_REPORT_INV_FILETYPE_ERROR_DES = "File type could not supported.";

	public static final String CONFIRM_IMPORT_UNRANKING_ERROR_CODE = "ERR-0022";
	public static final String CONFIRM_IMPORT_UNRANKING_ERROR_DES = "Error when confirm import stock trans.";
	public static final String NOT_PERMISS_PASS_OCR = "NOT_PERMISS_PASS_OCR";
	public static final String PCK_ERROR_01 = "PCK-A0001";
	public static final String PCK_ERROR_02 = "PCK-A0002";
	public static final String PCK_ERROR_03 = "PCK-A0003";
	public static final String PCK_ERROR_04 = "PCK-A0004";
	public static final String PCK_ERROR_05 = "PCK-A0005";
	public static final String PCK_ERROR_06 = "PCK-A0006";
	public static final String PCK_ERROR_07 = "PCK-A0007";
	public static final String PCK_ERROR_08 = "PCK-A0008";
	public static final String PCK_ERROR_09 = "PCK-A0009";
	public static final String PCK_ERROR_10 = "PCK-A0010";
	public static final String PCK_ERROR_11 = "PCK-A0011";
	public static final String PCK_ERROR_12 = "PCK-A0012";
	public static final String PCK_ERROR_13 = "PCK-A0013";
	public static final String PCK_ERROR_14 = "PCK-A0014";
	public static final String PCK_ERROR_15 = "PCK-A0015";

	public static final String FILE_IMAGE_CONTENT_ERROR = "FILE_IMAGE_CONTENT_ERROR";

	public static final int IMG_WIDTH = 800;
	public static final int IMG_HEIGHT = 600;
	public static final String JPG = "jpg";
	public static final String PNG = "png";
	public static final String JPEG = "jpeg";

	public static final String EMPTY = "";

	//DatBD2 update
	/*
	 * public static final String CUSTOMER_TYPE_CONSUMER = "1"; public static final
	 * String CUSTOMER_TYPE_CONSUMER_STR = "Cá nhân"; public static final String
	 * CUSTOMER_TYPE_FOREIGNER = "2"; public static final String
	 * CUSTOMER_TYPE_FOREIGNER_STR = "Người nước ngoài"; public static final String
	 * CUSTOMER_TYPE_CORPORATE = "3"; public static final String
	 * CUSTOMER_TYPE_CORPORATE_STR = "Doanh nghiệp/Tổ chức"; public static final
	 * String CUSTOMER_TYPE_EMPLOYEE = "4"; public static final String
	 * CUSTOMER_TYPE_EMPLOYEE_STR = "Nhân viên";
	 */
	//DatBD2 update
	public static final String CUSTOMER_TYPE_CONSUMER = "1";
	public static final String CUSTOMER_TYPE_CONSUMER_STR = "Cá nhân";
	public static final String CUSTOMER_TYPE_FOREIGNER = "1";
	public static final String CUSTOMER_TYPE_FOREIGNER_STR = "Cá nhân";
	public static final String CUSTOMER_TYPE_CORPORATE = "2";
	public static final String CUSTOMER_TYPE_CORPORATE_STR = "Tổ chức";
	public static final String CUSTOMER_TYPE_EMPLOYEE = "2";
	public static final String CUSTOMER_TYPE_EMPLOYEE_STR = "Tổ chức";
	//end

	public static final String CUSTOMER_TYPE_CONSUMER_NEW = "1";
	public static final String CUSTOMER_TYPE_CONSUMER_STR_NEW = "Cá nhân";
	public static final String CUSTOMER_TYPE_CORPORATE_NEW = "2";
	public static final String CUSTOMER_TYPE_CORPORATE_STR_NEW = "Tổ chức";

	public static final String ACTION_CREATE_CUSTOMER = "12";
	public static final String ACTION_CONNECTER_SUB_BS = "14";
	public static final String ACTION_UPDATE_CUSTOMER = "13";
	public static final String ACTION_CHANGE_SIM = "05";
	public static final String ACTION_CHANGE_SOVEREIGNTY = "22";
	public static final String ACTION_CHANGE_SOVE_INNER = "45";

	public static final String MDN_TIBCO = "84";

	public static final String SOURCE_PREPAID_REGISTRATION = "EPOS";

	public static final String ID_Type_PREPAID_REGISTRATION = "ID Card (Vietnam)";

	public static final String FLAG_3G = "3G";
	public static final String FLAG_4G = "4G";

	public static final String FEMALE = "Female";

	public static final String MALE = "Male";

	public static final String DECIMAL_FORMAT = "0.#####";

	public static final int INDEX_SUB_NAME = 1;
	public static final int INDEX_SUB_BIRTHDAY = 2;
	//DatBD2 update
	public static final int INDEX_SUB_TYPECARD=3;
	//end
	public static final int INDEX_SUB_ISSUE_NUMBER = 4;
	public static final int INDEX_SUB_GENDER = 5;
	public static final int INDEX_SUB_CUSTOMER_TYPE = 6;
	//DatBD2 update
	public static final int INDEX_SUB_SUB_USE_TYPE=7;
	//end
	public static final int INDEX_SUB_COMPANY_NAME = 8;
	//DatBD2 update
	public static final int INDEX_SUB_TYPE_CARD_COMPANY=9;
	//end
	public static final int INDEX_SUB_TAX_CODE = 10;
	public static final int INDEX_SUB_ADDRESS = 11;
	public static final int INDEX_SUB_ADDRESS_COMPANY=12;
	public static final int INDEX_SUB_PROVINCE = 13;
	public static final int INDEX_SUB_DISTRICT = 14;
	public static final int INDEX_SUB_MDN = 15;
	public static final int INDEX_SUB_ICCID = 16;
	public static final int INDEX_SUB_PLACE_OF_ISSUE = 17;
	public static final int INDEX_SUB_DATE_OF_ISSUE = 18;
	public static final int INDEX_SUB_COUNTRY = 19;
	public static final int INDEX_SUB_IMGID = 20;//cmt1
	public static final int INDEX_SUB_IMGID_2 = 21;//cmt2
	public static final int INDEX_SUB_CUST_IMG = 22;//chan dung
	public static final int INDEX_SUB_BUS_PERMIT_NO_IMG1 = 23;//gpkd1
	//DatBD2 update
	public static final int INDEX_SUB_BUS_PERMIT_NO_IMG2=24;//gpkd2

	public static final int INDEX_SUB_CONTRACT_IMG1 = 25;//hop dong kh1
	public static final int INDEX_SUB_CONTRACT_IMG2 = 26;//hop dong kh2

	public static final int INDEX_SUB_FILEID = 27; //gkhd1
	public static final int INDEX_SUB_FILEID_2 = 28;//gkhd2
	public static final int INDEX_SUB_FILEID_3 = 29;//gkhd3
	public static final int INDEX_SUB_PARENT_IMG=30;//dskh con
	public static final int INDEX_SUB_AUTHORIZED_IMG = 31;//uy quyen
	public static final int INDEX_SUB_IMG_SIM_OR_SUBS = 32;//sim goc
	//end

	public static final int INDEX_SUB_IMG_CKCS_1 = 33;//cksc1
	public static final int INDEX_SUB_IMG_CKCS_2 = 34;//cksc2

	public static final int INDEX_SUB_STATUS = 25;
	public static final int INDEX_SUB_DEP_ERROR = 26;

	public static final String INDEX_SUB_STATUS_BATCH = "0";
	public static final String INDEX_SUB_STATUS_BATCH_SUCCESS= "1";
	public static final int INDEX_TEMP_SHOP = 1;
	public static final int INDEX_TEMP_PROVINCE = 2;

	public static final String AUTHORIZED_IMG = "1";
	public static final String BUS_PERMIT_NO_IMG1 = "2";
	public static final String CONTRACT_IMG1 = "3";
	public static final String CONTRACT_IMG2 = "4";
	public static final String CUST_IMG = "5";
	public static final String IMG_ID = "6";
	public static final String IMG_ID_2 = "7";
	public static final String FILE_ID = "8";
	public static final String FILE_ID_2 = "9";
	//DatBD2
	public static final String BUS_PERMIT_NO_IMG2="10";
	public static final String PARENT_IMG="11";
	//end

	// template export
	public static final String TEMPLATE_TYPE = ".jrxml";
	public static final String TEMPLATE_JASPER_TYPE = ".jasper";

	public static final String PROVISIONING_1 = "1";
	public static final String PROVISIONING_2 = "2";
	public static final String PROVISIONING_0 = "0";
	public static final String PROVISIONING_4 = "4";

	public static final String SQL_RESLUT_SUCCESS = "1";
	public static final String SQL_RESLUT_FAILED = "0";

	//index upload agency info
	public static final int UPLOAD_ACTION 		= 1;
	public static final int UPLOAD_SUBSCRIBER	= 2;
	public static final int UPLOAD_PARENT_SUBSCRIBER	= 3;
	public static final int UPLOAD_AGENT_TYPE	= 4;
	public static final int UPLOAD_AGENT_CODE	= 5;
	public static final int UPLOAD_BUSINESS_NAME= 6;
	public static final int UPLOAD_TAXCODE		= 7;
	public static final int UPLOAD_REGION		= 8;
	public static final int UPLOAD_OWNER_NAME	= 9;
	public static final int UPLOAD_SECRET_QUEST	= 10;
	public static final int UPLOAD_BIRTHDAY		= 11;
	public static final int UPLOAD_CONTACT_NUMBER	= 12;
	public static final int UPLOAD_BUS_ADDRESS	= 13;
	public static final int UPLOAD_ICCID		= 14;
	public static final int UPLOAD_REPORT_EMAIL	= 15;
	public static final int UPLOAD_EMAIL		= 16;
	public static final int UPLOAD_PROVINCE		= 17;
	public static final int UPLOAD_DISTRICT		= 18;
	public static final int UPLOAD_IDCARD		= 19;
	public static final int UPLOAD_ISSUE_DATE	= 20;
	public static final int UPLOAD_ISSUE_PLACE	= 21;
	public static final int UPLOAD_TOWN			= 22;
	public static final int UPLOAD_LEVEL_AGENT	= 23;

	// Microsite preorder
	public static final int PREORDER_MSISDN = 1;
	public static final int PREORDER_SUB_TYPE = 2;
	public static final int PREORDER_SUB_NAME = 3;
	public static final int PREORDER_BIRTH_DATE = 4;
	public static final int PREORDER_DOC_TYPE = 5;
	public static final int PREORDER_DOC_NUMBER = 6;
	public static final int PREORDER_CONTACT_NUMBER = 7;
	public static final int PREORDER_PROVINCE = 8;
	public static final int PREORDER_DISTRICT = 9;
	public static final int PREORDER_PRECINCT = 10;
	public static final int PREORDER_ADDRESS = 11;
	public static final int PREORDER_DELIVER = 12;
	public static final int PREORDER_CHANNEL = 13;
	public static final int PREORDER_DESCRIPTION = 14;

}
