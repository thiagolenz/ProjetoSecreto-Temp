package com.redfire.nutrieduc.commonsvc.svc.exception;

/**
 * @author thiagolenz
 * @since Aug 26, 2014
 *
 */
public class LoginFailedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1366093151114297994L;

	public LoginFailedException() {
		super("Erro ao Fazer Login: Usuário não autenticado");
	}
}
