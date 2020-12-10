package com.example.zkexperiments.config;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;

public class ApplicationInitializer {
    private final CuratorFramework curatorFramework;

    public ApplicationInitializer(CuratorFramework curatorFramework) {
        this.curatorFramework = curatorFramework;
    }

    @EventListener
    public void onContextStarted(ContextStartedEvent ev) {
        curatorFramework.start();
    }

    @EventListener
    public void onContextStopped(ContextClosedEvent ev) {
        curatorFramework.close();
    }
}
