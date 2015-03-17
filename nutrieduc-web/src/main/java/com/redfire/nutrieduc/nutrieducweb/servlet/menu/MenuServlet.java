package com.redfire.nutrieduc.nutrieducweb.servlet.menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.redfire.nutrieduc.entities.user.User;
import com.redfire.nutrieduc.entities.user.UserType;
import com.redfire.nutrieduc.restclient.BeanJsonConverter;

@WebServlet("/menuItens")
public class MenuServlet extends HttpServlet{
	private BeanJsonConverter beanJsonConverter = new BeanJsonConverter();

	/**
	 * 
	 */
	private static final long serialVersionUID = -4957053555453618160L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		User user = getUser(req);
		MenuItemResult menu = new MenuItemResult();
		if (user != null) {
			menu = loadMenuItens();
			filterMenuByUserType(user.getUserType(), menu);
		}
		resp.getWriter().write(beanJsonConverter.convertToString(menu));
		resp.setContentType("application/json");
	}

	private MenuItemResult loadMenuItens() throws IOException {
		MenuItemResult menu;
		InputStream is = MenuServlet.class.getClassLoader().getResourceAsStream("headerMenus.json");
		StringBuilder builder = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String line = "";
		while ((line = reader.readLine()) != null)
			builder.append(line);
		
		menu = (MenuItemResult) beanJsonConverter.convertToObject(builder.toString(), MenuItemResult.class);
		return menu;
	}
	
	private void filterMenuByUserType (UserType userType, MenuItemResult menuItemResult) {
		List<MenuItem> resulList = new ArrayList<>();
		for (MenuItem menu : menuItemResult.getMenus()) {
			if (userCanUseTheMenu(userType, menu)) {
				resulList.add(menu);
				filterMenuItem(userType, menu);
			}
		}
		menuItemResult.getMenus().clear();
		menuItemResult.getMenus().addAll(resulList);
	}
	
	private void filterMenuItem (UserType userType, MenuItem menuItem) {
		List<MenuItem> resulList = new ArrayList<>();
		for (MenuItem menu : menuItem.getSubMenus()){
			if (userCanUseTheMenu(userType, menu)) {
				resulList.add(menu);
				filterMenuItem(userType, menu);
			}
		}
		menuItem.getSubMenus().clear();
		menuItem.getSubMenus().addAll(resulList);
	}
	
	private boolean userCanUseTheMenu (UserType userType, MenuItem menu) {
		return menu.getUserTypes().isEmpty() || menu.getUserTypes().contains(userType); 
	}
	
	private User getUser (HttpServletRequest request) throws IOException {
		String jsonUser = (String) request.getSession().getAttribute("contextUser");
		return (User) beanJsonConverter.convertToObject(jsonUser, User.class);
	}
 }
