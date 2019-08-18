package App.test.app.test.security;

import App.test.app.test.SpringApplicationContext;

public class SecurityConstants {
	public static final long EXPINRATION_TIME = 864000000; //10 days
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL = "/users";
	
	public static String getTokenSecret() {
		AppProperties appProperties = (AppProperties)SpringApplicationContext.getBean("AppProperties");
		return appProperties.getTokenSecrte();
	}
}
