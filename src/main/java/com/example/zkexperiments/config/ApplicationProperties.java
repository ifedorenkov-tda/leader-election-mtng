package com.example.zkexperiments.config;

import org.springframework.beans.factory.annotation.Value;

public class ApplicationProperties {
    @Value("${com.example.zkexperiments.discovery.hosts}")
    private String discHosts;

    public String getDiscHosts() {
        return discHosts;
    }

    public void setDiscHosts(String discHosts) {
        this.discHosts = discHosts;
    }
}
