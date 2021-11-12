package HealthDeclaration.common.base.service;

import HealthDeclaration.config.JwtTokenUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class BaseService {

	private ModelMapper modelMapper;

	public ModelMapper getModelMapper() {
		return modelMapper;
	}

	@Autowired
	public void setModelMapper(final ModelMapper pModelMapper) {
		modelMapper = pModelMapper;
	}

	
	public String getLoggedInUsername() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

	public String getLoggedInUserRoles() {
		List<String> roleList = new ArrayList<>();
		Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		authorities.forEach(element -> {
			roleList.add(element.getAuthority());
		});
		return roleList.get(0);
	}
}
