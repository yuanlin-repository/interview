package github.yuanlin;


import github.yuanlin.ratelimit.RateLimiter;

public class Main {
    public static void main(String[] args) {
        testRateLimiter();
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
}