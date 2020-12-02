package com.example.zkexperiments;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.retry.RetryForever;

import java.util.UUID;

public class Main {
    private static final String LEADER_LATCH_PATH = "/leader_election";

    public static void main(String[] args) throws Throwable {
        UUID uuid = UUID.randomUUID();

        CuratorFramework client = null;
        LeaderLatch leaderLatch = null;
        try {
            client = CuratorFrameworkFactory.newClient(
                    "h0010794:2181,h0010795:2181,h0010796:2181",
                    new RetryForever(5000));
            client.start();

            System.out.println("Started the node: " + uuid.toString());

            leaderLatch = new LeaderLatch(client, LEADER_LATCH_PATH, uuid.toString());
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

            if (client != null) {
                try {
                    client.close();
                } catch (Throwable ignore) {}
            }
        }
    }
}
