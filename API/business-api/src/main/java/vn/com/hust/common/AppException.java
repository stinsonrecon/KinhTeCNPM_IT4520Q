package vn.com.hust.common;

public class AppException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields
	  private String mstrReason;
	  private String mstrContext;
	  private String mstrInfo;

	  // Constructors
	  public AppException(String strReason) {
		  this.mstrReason = strReason;
	  }
	  public AppException(String strReason, String strContext) {
		  this.mstrReason = strReason;
		  this.mstrContext = strContext;
	  }
	  public AppException(String strReason, String strContext, String strInfo) {
		  this.mstrReason = strReason;
		  this.mstrContext = strContext;
		  this.mstrInfo = strInfo;
	  }
	  public AppException(Exception e, String strContext) {
		  super(e);
		  this.mstrContext = strContext;
	  }
	  public AppException(Exception e, String strContext, String strInfo) {
		  super(e);
		  this.mstrContext = strContext;
		  this.mstrInfo = strInfo;
	  }

	  public String getMessage() {
		  
		  return "mstrReason:" + getReason() + "mstrContext:" + getContext() + "mstrInfo:" + getInfo();
	  }
	  public String toString() {
		  
		  return "mstrReason:" + getReason() + "mstrContext:" + getContext() + "mstrInfo:" + getInfo();
	  }
	  public String getContext() {
		  
		  return this.mstrContext;
	  }
	  public void setContext(String mstrContext) {
		  this.mstrContext = mstrContext;
	  }
	  public String getInfo() {
		  
		  return this.mstrInfo;
	  }
	  public void setInfo(String mstrInfo) {
		  this.mstrInfo = mstrInfo;
	  }
	  public String getReason() {
		  
		  return this.mstrReason;
	  }
	  public void setReason(String strReason) {
		  this.mstrReason = strReason;
	  }
}
