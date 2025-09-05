package github.yuanlin.uniqueid;

/**
 * @author yuanlin.zhou
 * @date 2025/9/5 15:30
 * @description refer to https://github.com/beyondfengyu/SnowFlake/blob/master/SnowFlake.java
 */
public class SnowFlake {

    private final static long START_STAMP = 1480166465631L;

    private final static long SEQUENCE_BIT = 12;
    private final static long MACHINE_BIT = 5;
    private final static long DATACENTER_BIT = 5;

    private final static long MAX_SEQUENCE_NUM = ~(-1L << 12);
    private final static long MAX_MACHINE_NUM = ~(-1L << 5);
    private final static long MAX_DATACENTER_NUM = ~(-1L << 5);

    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTAMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

    private long datacenterId;
    private long machineId;
    private long sequence = 0L;
    private long lastStamp = -1L;

    public SnowFlake(long datacenterId, long machineId) {
        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
            throw new IllegalArgumentException();
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException();
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    public synchronized long nextId() {
        long curStamp = getNewStamp();
        if (curStamp < lastStamp) {
            throw new RuntimeException();
        }

        if (curStamp == lastStamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE_NUM;
            if (sequence == 0L) {
                curStamp = getNextMill();
            }
        } else {
            sequence = 0L;
        }

        lastStamp = curStamp;

        System.out.println((curStamp - START_STAMP) << TIMESTAMP_LEFT);
        System.out.println(datacenterId << DATACENTER_LEFT);
        System.out.println(machineId << MACHINE_LEFT);
        System.out.println(sequence);


        return (curStamp - START_STAMP) << TIMESTAMP_LEFT
                | datacenterId << DATACENTER_LEFT
                | machineId << MACHINE_LEFT
                | sequence;
    }

    private long getNextMill() {
        long mill = getNewStamp();
        while (mill <= lastStamp) {
            mill = getNewStamp();
        }
        return mill;
    }

    private long getNewStamp() {
        return System.currentTimeMillis();
    }
}
