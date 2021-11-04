package com.project.dnflol.Security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import com.project.dnflol.DTO.UserDTO;
import com.project.dnflol.Service.UserService;
import com.project.dnflol.util.AuthInfo;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
	private RedirectStrategy strategy = new DefaultRedirectStrategy();
	private RequestCache reqCache = new HttpSessionRequestCache();
	
	@Autowired
	UserService uServ;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
		UserDTO user = uServ.readById(auth.getName());
		AuthInfo authInfo = new AuthInfo(user.getUid(), user.getUname());
		HttpSession session = request.getSession();
		if (session != null) {
			session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}
		
		session.setAttribute("authInfo", authInfo);
		
		System.out.println((AuthInfo)session.getAttribute("authInfo"));
		
		clearAuthenticationAttributes(request);
		
		strategy.sendRedirect(request, response, "/");
	}
	
	protected void resultRedirectStrategy(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		SavedRequest savedRequest = reqCache.getRequest(request, response);
		if (savedRequest != null) {
			String targetUrl = savedRequest.getRedirectUrl();
			strategy.sendRedirect(request, response, targetUrl);
		} else {
			strategy.sendRedirect(request, response, "/");
		}
	}

	protected void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null)
			return;
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}
}
