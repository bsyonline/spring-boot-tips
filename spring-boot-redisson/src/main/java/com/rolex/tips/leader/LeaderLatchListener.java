package com.rolex.tips.leader;

import org.redisson.api.RedissonClient;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public interface LeaderLatchListener {
    /**
     * leader授予
     */
    void assignedLeader(RedissonClient redissonClient);

    /**
     * leader释放
     */
    void releasesLeader();
}
