package vn.com.hust.dto;

import java.util.List;

/**
 * The Class MessagesResponse.
 */
public class MessagesResponse implements java.io.Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4048972425814552025L;

	/** The status. */
	private String status;

	private String code;
	
	/** The messages. */
	private String messages;

	/** The guide infor. */
	private String guideInfor;

	/** The list result. */
	private List listResult;

	private String qrCode;

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status
	 *            the new status
	 */
	public void setStatus(String status) {
		this.status = status;

		/**
		 * Gets the messages.
		 *
		 * @return the messages
		 */
	}

	/**
	 * Gets the messages.
	 *
	 * @return the messages
	 */
	public String getMessages() {
		return messages;
		/**
		 * Sets the messages.
		 *
		 * @param messages
		 *            the new messages
		 */
	}

	/**
	 * Sets the messages.
	 *
	 * @param messages the new messages
	 */
	public void setMessages(String messages) {
		this.messages = messages;
	}

	/**
	 * Gets the guide infor.
	 *
	 * @return the guide infor
	 */
	public String getGuideInfor() {
		return guideInfor;
	}

	/**
	 * Sets the guide infor.
	 *
	 * @param guideInfor the new guide infor
	 */
	public void setGuideInfor(String guideInfor) {
		this.guideInfor = guideInfor;
	}

	/**
	 * Gets the list result.
	 *
	 * @return the list result
	 */
	public List getListResult() {
		return listResult;
	}

	/**
	 * Sets the list result.
	 *
	 * @param listResult the new list result
	 */
	public void setListResult(List listResult) {
		this.listResult = listResult;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
}
