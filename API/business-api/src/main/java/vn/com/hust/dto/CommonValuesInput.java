package vn.com.hust.dto;

import java.util.List;

/**
 * The Class CommonValuesInput.
 */
public class CommonValuesInput implements java.io.Serializable
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5290089917596268791L;

	/** The staff id. */
	private String staffId;

	/** The staff name. */
	private String staffName;

	/** The shop code. */
	private String shopCode;

	/** The shop name. */
	private String shopName;

	private String shopId;

	private String type;

	private String status;

	private String code;

	private String stockId;

	private String goodId;

	private String stateId;

	private String accountCode;

	private String userAction;

	private String dateAction;

	private List<String> listIdInput;

	private String remoteAddr;

	/**
	 * Gets the staff id.
	 *
	 * @return the staff id
	 */
	public String getStaffId()
	{
		return staffId;
	}

	/**
	 * Sets the staff id.
	 *
	 * @param staffId
	 *            the new staff id
	 */
	public void setStaffId(String staffId)
	{
		this.staffId = staffId;
	}

	/**
	 * Gets the staff name.
	 *
	 * @return the staff name
	 */
	public String getStaffName()
	{
		return staffName;
	}

	/**
	 * Sets the staff name.
	 *
	 * @param staffName
	 *            the new staff name
	 */
	public void setStaffName(String staffName)
	{
		this.staffName = staffName;
	}

	/**
	 * Gets the shop code.
	 *
	 * @return the shop code
	 */
	public String getShopCode()
	{
		return shopCode;
	}

	/**
	 * Sets the shop code.
	 *
	 * @param shopCode
	 *            the new shop code
	 */
	public void setShopCode(String shopCode)
	{
		this.shopCode = shopCode;
	}

	/**
	 * Gets the shop name.
	 *
	 * @return the shop name
	 */
	public String getShopName()
	{
		return shopName;
	}

	/**
	 * Sets the shop name.
	 *
	 * @param shopName
	 *            the new shop name
	 */
	public void setShopName(String shopName)
	{
		this.shopName = shopName;
	}

	/**
	 * @return the shopId
	 */
	public String getShopId()
	{
		return shopId;
	}

	/**
	 * @param shopId
	 *            the shopId to set
	 */
	public void setShopId(String shopId)
	{
		this.shopId = shopId;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getStockId()
	{
		return stockId;
	}

	public void setStockId(String stockId)
	{
		this.stockId = stockId;
	}

	public String getGoodId()
	{
		return goodId;
	}

	public void setGoodId(String goodId)
	{
		this.goodId = goodId;
	}

	public String getStateId()
	{
		return stateId;
	}

	public void setStateId(String stateId)
	{
		this.stateId = stateId;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public List<String> getListIdInput()
	{
		return listIdInput;
	}

	public void setListIdInput(List<String> listIdInput)
	{
		this.listIdInput = listIdInput;
	}

	public String getAccountCode()
	{
		return accountCode;
	}

	public void setAccountCode(String accountCode)
	{
		this.accountCode = accountCode;
	}

	public String getUserAction()
	{
		return userAction;
	}

	public void setUserAction(String userAction)
	{
		this.userAction = userAction;
	}

	public String getDateAction()
	{
		return dateAction;
	}

	public void setDateAction(String dateAction)
	{
		this.dateAction = dateAction;
	}

	public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}
}
