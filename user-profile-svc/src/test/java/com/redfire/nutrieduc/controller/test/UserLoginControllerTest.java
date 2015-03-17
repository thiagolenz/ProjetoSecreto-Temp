package com.redfire.nutrieduc.controller.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.redfire.nutrieduc.commonsvc.svc.exception.LoginFailedException;
import com.redfire.nutrieduc.entities.user.SocialMediaProvider;
import com.redfire.nutrieduc.entities.user.User;
import com.redfire.nutrieduc.mock.HttpServletRequestMock;
import com.redfire.nutrieduc.userprofile.controller.UserLoginController;
import com.redfire.nutrieduc.userprofile.service.UserLoginService;

@RunWith(MockitoJUnitRunner.class)
public class UserLoginControllerTest {
	
	private UserLoginController loginController;
	
	@Mock
	private UserLoginService loginService;
	
	@Before
	public void setup () {
		loginController = new UserLoginController();
		loginController.setUserLoginService(loginService);
		loginController.setHttpServletRequest(new HttpServletRequestMock());
	}

	@Test
	public void testLoginNormal () throws LoginFailedException {
		User user = new User();
		user.setEmail("123");
		user.setPassword("password");
		Mockito.when(loginService.login(Mockito.any(User.class))).thenReturn(new User());
		User logged = loginController.authenticate(user);
		Assert.assertNotNull(logged);
	}
	
	@Test
	public void testSocialMediaLogin () throws LoginFailedException {
		User user = new User();
		user.setSocialMediaProvider(SocialMediaProvider.FACEBOOK);
		user.setAccessToken("123");
		Mockito.when(loginService.socialMediaLogin(Mockito.any(User.class), Mockito.anyString())).thenReturn(new User());
		User logged = loginController.authenticate(user);
		Assert.assertNotNull(logged);
	}
}
