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
public interface LeaderSelectorListener {

    void takeLeadership(RedissonClient redissonClient) throws InterruptedException;

    void stateChanged(RedissonClient client, LeaderSelector.State newState);
}
