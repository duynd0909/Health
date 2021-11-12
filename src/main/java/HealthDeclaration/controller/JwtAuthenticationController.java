package HealthDeclaration.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import HealthDeclaration.common.response.utils.ResponseUtils;
import HealthDeclaration.modal.entity.User;
import HealthDeclaration.service.IUserService;
import HealthDeclaration.vo.ResponseMessage;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import HealthDeclaration.config.JwtTokenUtil;
import HealthDeclaration.modal.request.JwtRequest;
import HealthDeclaration.modal.response.JwtResponse;
import HealthDeclaration.service.serviceImpl.JwtUserDetailsService;

@RestController
@CrossOrigin
@Log4j2
public class JwtAuthenticationController {

	@Value("${jwt.secret}")
	private String secret;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private IUserService userService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@RequestMapping(value = "/api/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		Map<String, Object> mapResult = new HashMap<>();
		final UserDetails userDetails = userDetailsService.
				loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		User user = userService.getByUsername(userDetails.getUsername());
		mapResult.put("token", token);
		mapResult.put("username", userDetails.getUsername());
		mapResult.put("userInfor", user);
		mapResult.put("expiredTime", jwtTokenUtil.getExpirationDateFromToken(token).getTime());
		return ResponseEntity.ok(mapResult);
	}

	@RequestMapping(value = "/api/logout", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		final Date createdDate = new Date();
		final Date expirationDate = calculateExpirationDate(createdDate);

		final Claims claims = getAllClaimsFromToken(jwtTokenUtil.parseJwt(request));
		claims.setIssuedAt(createdDate);
		claims.setExpiration(expirationDate);

		return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	@RequestMapping(value = "/api/verifyToken", method = RequestMethod.GET)
	public ResponseEntity verifyToken(@RequestParam("token") String token, @RequestParam("username") String username) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			responseMessage.setSuccess(true);
			Boolean verifyToken = jwtTokenUtil.validateToken(token, username);
			responseMessage.setData(verifyToken);
		} catch (Exception e) {
			log.error(e);
			responseMessage.setSuccess(false);
			return  ResponseUtils.buildResponseMessage(false, responseMessage);
		}
		return  ResponseUtils.buildResponseMessage(true, responseMessage);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	private Date calculateExpirationDate(Date createdDate) {
		return new Date(createdDate.getTime() + 1 * 1000);
	}

//    @RequestMapping(value = "/api/verify", method = RequestMethod.POST)
//    public ResponseEntity<?> createAuthenticationToken(@RequestParam String token) throws Exception {
//
//    }

	private void authenticate(String username, String password) throws Exception {
		System.out.println("xxx");
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
