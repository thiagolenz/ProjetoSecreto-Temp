package com.redfire.nutrieduc.controller.test;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.redfire.nutrieduc.commonsvc.svc.controller.DefaultBeanValidator;
import com.redfire.nutrieduc.commonsvc.svc.exception.BusinessException;
import com.redfire.nutrieduc.commonsvc.svc.exception.MandatoryFieldException;
import com.redfire.nutrieduc.entities.user.SocialMediaProvider;
import com.redfire.nutrieduc.entities.user.User;
import com.redfire.nutrieduc.entities.user.UserSocialMediaLogin;
import com.redfire.nutrieduc.mock.HttpServletRequestMock;
import com.redfire.nutrieduc.userprofile.controller.UserSocialMediaController;
import com.redfire.nutrieduc.userprofile.service.UserLoginService;
import com.redfire.nutrieduc.userprofile.service.UserService;
import com.redfire.nutrieduc.userprofile.service.UserSocialMediaLoginService;

@RunWith(MockitoJUnitRunner.class)
public class UserSocialMediaControllerTest {
	
	private UserSocialMediaController socialMediaController;
	
	@Mock private UserService userService;
	@Mock private UserSocialMediaLoginService socialMediaLoginService;
	@Mock private DefaultBeanValidator beanValidator;
	
	@Before
	public void setup () throws Exception {
		socialMediaController = new UserSocialMediaController();
		setProperty("httpServletRequest", new HttpServletRequestMock(), socialMediaController);
		setProperty("socialMediaLoginService", socialMediaLoginService, socialMediaController);
		setProperty("beanValidator", beanValidator, socialMediaController);
		setProperty("userService", userService, socialMediaController);
	}
	
	@Mock
	private UserLoginService loginService;

	@Test
	public void testCreateNewUserSocialMedia () throws BusinessException, MandatoryFieldException {
		User user = new User();
		user.setSocialMediaProvider(SocialMediaProvider.FACEBOOK);
		user.setAccessToken("123");
		Mockito.when(socialMediaLoginService.find(Mockito.any(User.class))).thenReturn(null);
		Mockito.when(userService.createSocialMediaUser(Mockito.any(User.class), Mockito.anyString())).thenReturn(new User());
		User savedUser = socialMediaController.createSocialMediaUser(user, null);
		Assert.assertNotNull(savedUser);
	}

	@Test
	public void testAddSocialMediaExistingUser () throws BusinessException, MandatoryFieldException {
		User user = new User();
		user.setSocialMediaProvider(SocialMediaProvider.FACEBOOK);
		user.setAccessToken("123");
		UserSocialMediaLogin userMedia = new UserSocialMediaLogin();
		userMedia.setUser(new User());
		Mockito.when(socialMediaLoginService.find(Mockito.any(User.class))).thenReturn(userMedia);
		Mockito.when(socialMediaLoginService.create(Mockito.any(User.class), Mockito.anyString())).thenReturn(userMedia);
		User savedUser = socialMediaController.createSocialMediaUser(user, null);
		Assert.assertNotNull(savedUser);
	}
	
	@Test(expected=MandatoryFieldException.class)
	public void testValidateErrorsNoSocialMedia () throws BusinessException, MandatoryFieldException {
		socialMediaController.createSocialMediaUser(new User(), null);
	}
	
	@Test(expected=MandatoryFieldException.class)
	public void testValidateErrorsNoAccessToken () throws BusinessException, MandatoryFieldException {
		User user = new User();
		user.setSocialMediaProvider(SocialMediaProvider.FACEBOOK);
		socialMediaController.createSocialMediaUser(user, null);
	}
	
	public void setProperty(String property, Object value, Object source) throws Exception {
		Field declaredField = source.getClass().getDeclaredField(property);
		declaredField.setAccessible(true);
		declaredField.set(source, value);
	}
}
