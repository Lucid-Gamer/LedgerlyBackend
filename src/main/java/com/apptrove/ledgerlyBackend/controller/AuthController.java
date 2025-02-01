package com.apptrove.ledgerlyBackend.controller;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apptrove.ledgerlyBackend.payload.ApiResponse;
import com.apptrove.ledgerlyBackend.payload.LoginModel;
import com.apptrove.ledgerlyBackend.security.util.JwtUtil;
import com.apptrove.ledgerlyBackend.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RequestMapping("/ldgr/T1000")
@RestController
public class AuthController {
	
	private static final Logger logger = LogManager.getLogger(AuthController.class);

	@Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private Environment env;
	
    @PostMapping(path = "/S1001")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginModel loginModel,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
    	try {
    		Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginModel.getUsername(), loginModel.getPassword()));
    		boolean userLoggedIn = userService.isUserLoggedIn(loginModel.getUsername());
    		
    		if (loginModel.isClearSession() && userLoggedIn) {
				userService.clearLastSession(loginModel.getUsername());
			}
    		
        	if (!userLoggedIn || loginModel.isClearSession()) {
        		httpServletRequest.getSession().invalidate();
        		httpServletRequest.getSession(true);
        		String domainName = httpServletRequest.getServerName();
        		String ipAddress = httpServletRequest.getRemoteAddr();
        		String sessionId = httpServletRequest.getSession().getId();
        		String token = jwtUtil.generateToken(authentication,httpServletRequest,httpServletResponse);
        		userService.loginUser(loginModel.getUsername(), domainName, sessionId, ipAddress,token);
        		httpServletRequest.getSession().setAttribute("token", token);
            	return new ResponseEntity<ApiResponse<String>>(new ApiResponse<String>(token, env.getProperty("login.success.message"), env.getProperty("login.user.authenticated")),HttpStatus.OK);
			} else {
				return new ResponseEntity<ApiResponse<String>>(new ApiResponse<String>("User Already Logged In", env.getProperty("login.user.already.logged.message"), env.getProperty("login.user.failed")),HttpStatus.CONFLICT);
			}
    		
		} catch (Exception e) {
			logger.error("An error occurred: "+e.getMessage());
			return new ResponseEntity<ApiResponse<String>>(new ApiResponse<String>(e.getMessage(), env.getProperty("login.fail.message"), env.getProperty("login.user.notAuthenticated")),HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @PostMapping("/S1002")
    public ResponseEntity<ApiResponse<String>> logoutUser(@RequestBody Map<String,Object> reqObj,HttpServletRequest httpServletRequest) {
    	String sessionId = "";
        try {
			String token = httpServletRequest.getHeader("Authorization").substring(7);
			if (token != null && token != "" && reqObj.containsKey("username")) {
				
				for (Cookie cookie : httpServletRequest.getCookies()) {
					if (cookie.getName().equals("sessionId")) {
						sessionId = cookie.getValue();
					}
				}
				
//				 httpServletRequest.getSession().getId();
				String domainName = httpServletRequest.getServerName();
				String ipAddress = httpServletRequest.getRemoteAddr();
				String username = reqObj.get("username").toString();
				
				boolean flag = this.userService.logoutUser(username, token, domainName, ipAddress, sessionId);
				if (flag) {
					return new ResponseEntity<ApiResponse<String>>(new ApiResponse<String>("User logout successfull", env.getProperty("logout.success.message"), env.getProperty("logout.user.success.code")),HttpStatus.OK);
				} else {
					return new ResponseEntity<ApiResponse<String>>(new ApiResponse<String>("User Session Expired", env.getProperty("logout.fail.message"), env.getProperty("logout.user.fail.code")),HttpStatus.CONFLICT);
				}
			}
			return new ResponseEntity<ApiResponse<String>>(new ApiResponse<String>("User Session Expired", env.getProperty("logout.fail.message"), env.getProperty("logout.user.fail.code")),HttpStatus.CONFLICT);
		} catch (Exception e) {
			logger.error("An error occurred: "+e.getMessage());
			return new ResponseEntity<ApiResponse<String>>(new ApiResponse<String>(e.getMessage(), env.getProperty("logout.fail.message"), env.getProperty("logout.user.fail.code")),HttpStatus.INTERNAL_SERVER_ERROR);
		}
        
    }
    
    
}
