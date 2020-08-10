package com.example.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.facade.AdminFacade;
import com.example.facade.ClientFacade;
import com.example.facade.CompanyFacade;
import com.example.facade.CustomerFacade;

@Service
@Aspect
public class SessionManager {
	
	private Map<String, Session> sessions = new HashMap<String, Session>();
	
	public Map<String, Session> getSessions() {
		return sessions;
	}
	
	public String generateSession(ClientFacade clientFacade) {
		String token = UUID.randomUUID().toString();
		sessions.put(token, new Session(clientFacade, System.currentTimeMillis()));
		return token;
	}
	
	public boolean removeSession(String token) {
		return sessions.remove(token) != null;
	}
	
	@Around("anyCustomerLogin(string)")
	public Object accessCustomer(ProceedingJoinPoint pjp, String string) throws Throwable {
		long nowTime = System.currentTimeMillis();
		Session session = sessions.get(string);
		if (session != null && session.getFacade() instanceof CustomerFacade)
			if (nowTime - session.getLastAction() < 1000 * 60 * 30) {
				Object[] args = pjp.getArgs();
				if (args.length > 1)
					args[1] = session.getFacade();
				session.setLastAction(nowTime);
				return pjp.proceed(args);
			}
		
		sessions.remove(string);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	
	@Around("anyCompanyLogin(string)")
	public Object accessCompany(ProceedingJoinPoint pjp, String string) throws Throwable {
		long nowTime = System.currentTimeMillis();
		Session session = sessions.get(string);
		if (session != null && session.getFacade() instanceof CompanyFacade)
			if (nowTime - session.getLastAction() < 1000 * 60 * 30) {
				Object[] args = pjp.getArgs();
				if (args.length > 1)
					args[1] = session.getFacade();
				session.setLastAction(nowTime);
				return pjp.proceed(args);
			}
		
		sessions.remove(string);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	
	@Around("anyAdminLogin(string)")
	public Object accessAdmin(ProceedingJoinPoint pjp, String string) throws Throwable {
		long nowTime = System.currentTimeMillis();
		Session session = sessions.get(string);
		if (session != null && session.getFacade() instanceof AdminFacade)
			if (nowTime - session.getLastAction() < 1000 * 60 * 30) {
				Object[] args = pjp.getArgs();
				if (args.length > 1)
					args[1] = session.getFacade();
				session.setLastAction(nowTime);
				return pjp.proceed(args);
			}
		
		sessions.remove(string);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	
	
	@Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
	public void anyInRestControllerClass() {}
	
	@Pointcut("anyInRestControllerClass() && @annotation(com.example.web.annotations.CustomerLogin) && args(string,..)")
	public void anyCustomerLogin(String string) {}
	
	@Pointcut("anyInRestControllerClass() && @annotation(com.example.web.annotations.CompanyLogin) && args(string,..)")
	public void anyCompanyLogin(String string) {}
	
	@Pointcut("anyInRestControllerClass() && @annotation(com.example.web.annotations.AdminLogin) && args(string,..)")
	public void anyAdminLogin(String string) {}
	
	// Non casting aspect :/
//	@Around("anyLoginRequired(string)")
//	public Object access(ProceedingJoinPoint pjp, String string) throws Throwable {
//		long nowTime = System.currentTimeMillis();
//		Session session = sessions.get(string);
//		if (session != null)
//			if (nowTime - session.getLastAction() < 1000 * 60 * 30) {
//				Object[] args = pjp.getArgs();
//				if (args.length > 1)
//					args[1] = session.getFacade();
//				session.setLastAction(nowTime);
//				return pjp.proceed(args);
//			}
//		
//		sessions.remove(string);
//		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//	}
	
//	@Pointcut("anyInRestControllerClass() && @annotation(com.example.web.LoginRequired) && args(string,..)")
//	public void anyLoginRequired(String string) {}
	
	// Non aspect method
//	public ClientFacade access(String token) {
//		if (token != null) {
//			long nowTime = System.currentTimeMillis();
//			Session session = sessions.get(token);
//			if (nowTime - session.getLastAction() > 1000 * 60 * 30) {
//				session.setLastAction(nowTime);
//				return session.getFacade();
//			}
//		}
//		return null;
//	}
	
}
