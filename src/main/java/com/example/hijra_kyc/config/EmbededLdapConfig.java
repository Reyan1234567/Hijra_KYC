package com.example.hijra_kyc.config;

import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
import com.unboundid.ldap.listener.InMemoryListenerConfig;
import com.unboundid.ldif.LDIFReader;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.net.InetAddress;


@Slf4j
@Component
public class EmbededLdapConfig {
    private InMemoryDirectoryServer ds;

    @PostConstruct
    public void start() throws Exception {
        // Base DN
        InMemoryDirectoryServerConfig config = new InMemoryDirectoryServerConfig("dc=example,dc=com");

        // Bind credentials for admin
        config.addAdditionalBindCredentials("cn=admin,dc=example,dc=com", "adminpassword");

        // Explicitly bind to localhost:1389
        config.setListenerConfigs(
                InMemoryListenerConfig.createLDAPConfig(
                        "default",                       // Listener name
                        InetAddress.getByName("0.0.0.0"),// Listen on all interfaces
                        1389,                            // Port
                        null                             // No SSL
                )
        );


        // Disable schema checking
        config.setSchema(null);

        // Create server
        ds = new InMemoryDirectoryServer(config);

        // Load LDIF file
        ClassPathResource resource = new ClassPathResource("mock-AD.ldif");
        try (LDIFReader reader = new LDIFReader(resource.getInputStream())) {
            ds.importFromLDIF(true, reader);
        }

        // Start LDAP
        ds.startListening();
        log.info("LDIF CONFIGURED");
    }

    @PreDestroy
    public void stop() {
        if (ds != null) {
            ds.shutDown(true);
        }
    }
}
