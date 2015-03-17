package br.com.redfire.nutrieduc.common;

/**
 * Login response Bean class
 * @author thiagolenz
 * @since Aug 25, 2014
 */
public class LoginResponseBean {
	private String sessionId;
	private String sessionBean;
	
	public LoginResponseBean() {
	}
	
	public LoginResponseBean(String sessionId, String sessionBean) {
		super();
		this.sessionId = sessionId;
		this.sessionBean = sessionBean;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public Object getSessionBean() {
		return sessionBean;
	}
	public void setSessionBean(String sessionBean) {
		this.sessionBean = sessionBean;
	}
}
