package com.example.zkexperiments;

import com.example.zkexperiments.config.ApplicationConfig;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.UUID;

public class Main {
    private static final String APP_LEADER_LATCH_PATH = "/leader_election";

    public static void main(String[] args) throws Throwable {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        LeaderLatch leaderLatch = null;
        try {
            // Send the start signal
            context.start();

            CuratorFramework client = context.getBean(CuratorFramework.class);
            UUID appUuid = UUID.randomUUID();

            System.out.println("Started the node: " + appUuid.toString());

            leaderLatch = new LeaderLatch(client, APP_LEADER_LATCH_PATH, appUuid.toString());
            leaderLatch.start();
            leaderLatch.await();

            System.out.println("Became the leader");

            System.in.read();
        } finally {
            if (leaderLatch != null) {
                try {
                    leaderLatch.close();
                } catch (Throwable ignore) {}
            }

            // Send the stop signal
            context.close();
        }
    }
}
