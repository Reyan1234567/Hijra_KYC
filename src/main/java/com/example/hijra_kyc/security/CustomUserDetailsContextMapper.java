package com.example.hijra_kyc.security;

import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;

import javax.naming.directory.Attributes;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

public class CustomUserDetailsContextMapper implements UserDetailsContextMapper {

    //    @Override
//    public UserDetails mapUserFromContext(DirContextOperations ctx, String username, Collection authorities) {
//        Attributes attrs = ctx.getAttributes();
//        String emptire = null;
//
//        try {
//            if (attrs.get("emptire") != null) {
//                emptire = attrs.get("emptire").get().toString();
//            }
//        } catch (Exception e) {
//            // ignore
//        }
//
//        // FIX for byte[] password issue
//        Object pwdAttr = ctx.getObjectAttribute("userPassword");
//        String pwd = "";
//        if (pwdAttr instanceof String) {
//            pwd = (String) pwdAttr;
//        } else if (pwdAttr instanceof byte[]) {
//            pwd = new String((byte[]) pwdAttr, StandardCharsets.UTF_8);
//        }
//
//        return new CustomLdapUserDetails(username, pwd, emptire == null ? "0" : emptire);
//    }
//
//    @Override
//    public void mapUserToContext(UserDetails user, DirContextAdapter ctx) {
//        throw new UnsupportedOperationException("Not needed");
//    }
//}
    @Override
    public UserDetails mapUserFromContext(DirContextOperations ctx, String username, Collection authorities) {
        Attributes attrs = ctx.getAttributes();

        String pwd = "";
        String first = getAttr(attrs, "givenName");
        String last = getAttr(attrs, "sn");
        String gender = getAttr(attrs, "gender");
        String phone = getAttr(attrs, "phoneNumber");
        String branch = getAttr(attrs, "branchId");  // string
        String userId = getAttr(attrs, "userId");

        // password can be either String or byte[]
        Object pwdAttr = ctx.getObjectAttribute("userPassword");
        if (pwdAttr instanceof String) {
            pwd = (String) pwdAttr;
        } else if (pwdAttr instanceof byte[]) {
            pwd = new String((byte[]) pwdAttr, StandardCharsets.UTF_8);
        }

        return new CustomLdapUserDetails(
                username, pwd, first, last, gender, phone, branch, userId
        );
    }

    private String getAttr(Attributes attrs, String key) {
        try {
            return attrs.get(key) == null ? null : attrs.get(key).get().toString();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void mapUserToContext(UserDetails user, DirContextAdapter ctx) {

    }
}
