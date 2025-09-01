package github.yuanlin.ratelimit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author yuanlin.zhou
 * @date 2025/9/1 15:15
 * @description TODO
 */
public class RateLimiter {
    private double storedTokens;
    private double maxBurstSeconds;
    private double maxTokens;
    private double stableIntervalMicros;
    private long nextFreeTicketMicros = 0L;

    public RateLimiter(double tokensPerSecond, double maxBurstSeconds) {
        this.maxBurstSeconds = maxBurstSeconds;
        this.maxTokens = tokensPerSecond * maxBurstSeconds;
        this.stableIntervalMicros = SECONDS.toMicros(1L) / tokensPerSecond;
    }

    public boolean acquire() {
        rsync();
        if (storedTokens > 0) {
            storedTokens -= 1;
            return true;
        }
        return false;
    }

    private void rsync() {
        long now = MILLISECONDS.toMicros(System.currentTimeMillis());
        if (now > nextFreeTicketMicros) {
            double newTokens = (now - nextFreeTicketMicros) / stableIntervalMicros;
            storedTokens = Math.min(maxTokens, newTokens + storedTokens);
            nextFreeTicketMicros = now;
        }
    }
}
