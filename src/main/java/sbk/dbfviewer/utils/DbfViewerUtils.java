//different utils for work
package sbk.dbfviewer.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class DbfViewerUtils {
	public static String findCookie(Cookie[] cookies, String key){
		if(cookies != null){
			for(int i=0;i<cookies.length;i++){
				String name = cookies[i].getName();
				if(name.compareTo(key) == 0){
					try {
						return URLDecoder.decode(cookies[i].getValue(), "UTF-8");
					} catch (UnsupportedEncodingException e) {
						return null;
					}
				}
			}
		}
		return null;
	}
	public static boolean addCookie(String cookieName, String cookieValue, HttpServletResponse response){
		try {
			response.addCookie(new Cookie(cookieName, URLEncoder.encode(cookieValue,"UTF-8")));
		} catch (UnsupportedEncodingException e) {				
			return false;
		}	
		return true;
	}

}
