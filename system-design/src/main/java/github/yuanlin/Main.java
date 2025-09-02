package github.yuanlin;


import github.yuanlin.consistenthash.ConsistentHash;
import github.yuanlin.consistenthash.impl.DefaultNode;
import github.yuanlin.ratelimit.RateLimiter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
//        testRateLimiter();
        testConsistentHash();
    }

    public static void testRateLimiter() {
        final RateLimiter rateLimiter = new RateLimiter(10, 1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    try {
                        int cnt = 100;
                        while (cnt-- > 0) {
                            if (rateLimiter.acquire()) {
                                System.out.println("Acquiring token successfully::" + i + "::" + cnt);
                            }
                        }
                        Thread.sleep(1000);
                        System.out.println();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();
    }

    public static void testConsistentHash() {
        ConsistentHash consistentHash = new ConsistentHash();
        consistentHash.addHost(new DefaultNode(10, "192.168.1.1", "192.168.1.1"));
        consistentHash.addHost(new DefaultNode(10, "192.168.1.2", "192.168.1.2"));
        consistentHash.addHost(new DefaultNode(1, "192.168.1.3", "192.168.1.3"));
        Map<String, Integer> summary = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            String host = consistentHash.getResourceByKey("admin" + i);
            System.out.println("admin" + i + ": " + host);;
            summary.put(host, summary.getOrDefault(host, 0) + 1);
        }
        System.out.println(summary.toString());
    }
}