package com.didi.util;

import com.didi.admin.mapper.TAdminMapper;
import com.didi.admin.service.impl.TAdminServiceImpl;
import com.didi.auth.mapper.TAuthMapper;
import com.didi.auth.service.impl.TAuthServiceImpl;
import com.didi.entrty.admin.TAdmin;
import com.didi.entrty.role.TRole;
import com.didi.role.mapper.TRoleMapper;
import com.didi.role.service.impl.TRoleServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
   private TAdminServiceImpl adminService;
    @Autowired
   private TRoleServiceImpl roleService;
    @Autowired
   private TAuthServiceImpl authService;
    private Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);
    @Override
    public UserDetails loadUserByUsername(String rowUserName) throws UsernameNotFoundException {
        TAdmin admin = adminService.getAdminByUserName(rowUserName);
        Integer adminId = admin.getId();
        List<TRole> assignedRole = roleService.getAssignedRole(adminId);
        List<String> authNames = authService.getAssignedAuthNameByAdminId(adminId);
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (TRole role:assignedRole) {
            String roleName = "ROLE_"+role.getName();
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(roleName);
            authorities.add(simpleGrantedAuthority);
        }
        for (String authName:authNames) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authName);
            authorities.add(simpleGrantedAuthority);
        }
        SecurityAdmin securityAdmin  =new SecurityAdmin(admin,authorities);
        logger.info("securityAdmin"+securityAdmin.toString());
        return securityAdmin;
    }
}
