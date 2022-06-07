package com.rolex.tips.tx.listener;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rolex.tips.tx.mapper.UserMapper;
import com.rolex.tips.tx.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Component
@Slf4j
public class UserRegTransactionListener implements TransactionListener {
    @Autowired
    UserMapper userMapper;

    @Override
    @Transactional
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        log.info("execute local tx, user registry, message={}", message);
        User user = JSON.parseObject(message.getBody(), User.class);
        try {
            userMapper.insert(user);
            log.info("user registry success");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("user registry failed");
            // 表示系统异常，向客户端返回失败，可通过明确的 ROLLBACK 指令进行判断
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
        // 返回 UNKNOW 因为此时事务还没有提交
        return LocalTransactionState.UNKNOW;
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        log.info("check local tx, messageExt={}", messageExt);
        User user = JSON.parseObject(messageExt.getBody(), User.class);
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("name", user.getName());
        User u = userMapper.selectOne(userQueryWrapper);
        log.info("query result: {}", u);
        if (u != null) {
            log.info("commit tx");
            return LocalTransactionState.COMMIT_MESSAGE;
        } else {
            log.info("UNKNOW");
            return LocalTransactionState.UNKNOW;
        }
    }
}
