package com.example.hijra_kyc.security;

import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;

import javax.naming.directory.Attributes;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

public class CustomUserDetailsContextMapper implements UserDetailsContextMapper {

    @Override
    public UserDetails mapUserFromContext(DirContextOperations ctx, String username, Collection authorities) {
        Attributes attrs = ctx.getAttributes();
        String emptire = null;

        try {
            if (attrs.get("emptire") != null) {
                emptire = attrs.get("emptire").get().toString();
            }
        } catch (Exception e) {
            // ignore
        }

        // FIX for byte[] password issue
        Object pwdAttr = ctx.getObjectAttribute("userPassword");
        String pwd = "";
        if (pwdAttr instanceof String) {
            pwd = (String) pwdAttr;
        } else if (pwdAttr instanceof byte[]) {
            pwd = new String((byte[]) pwdAttr, StandardCharsets.UTF_8);
        }

        return new CustomLdapUserDetails(username, pwd, emptire == null ? "0" : emptire);
    }

    @Override
    public void mapUserToContext(UserDetails user, DirContextAdapter ctx) {
        throw new UnsupportedOperationException("Not needed");
    }
}
