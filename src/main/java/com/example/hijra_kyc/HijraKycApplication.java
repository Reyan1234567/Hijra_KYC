package com.example.hijra_kyc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.ldap.LdapAutoConfiguration.class,
        org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapAutoConfiguration.class,
        org.springframework.boot.actuate.autoconfigure.ldap.LdapHealthContributorAutoConfiguration.class,
        org.springframework.boot.autoconfigure.data.ldap.LdapRepositoriesAutoConfiguration.class
})
@EnableScheduling
public class HijraKycApplication {
    public static void main(String[] args) {
        System.out.println("Hijra have arrived");
        SpringApplication.run(HijraKycApplication.class, args);
    }

}
