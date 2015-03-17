package com.redfire.nutrieduc.appsession.storage;

import java.util.HashMap;
import java.util.Map;

import com.redfire.nutrieduc.appsession.servlets.UserLoginStorageBean;

/**
 * Class responsible to save and recover the user session id 
 * @author thiagolenz
 * @since Aug 25, 2014
 *
 */
public class UserLocalStorage {
	private static Map<String, Object> users = new HashMap<String, Object>();
	
	// TODO SALVAR O IP QUE FEZ O LOGIN PARA VERIFICAR SE É O MESMO IP DA REQUISICAO
	public synchronized static void put(String key, Object value) {
		users.put(key, value);
	}
	
	public synchronized static UserLoginStorageBean get(String key) {
		return (UserLoginStorageBean) users.get(key);
	}
}
