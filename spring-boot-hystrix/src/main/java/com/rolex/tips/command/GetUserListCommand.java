/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.tips.command;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixObservableCommand;
import com.rolex.tips.model.User;
import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * @author rolex
 * @since 2021
 */
@Slf4j
public class GetUserListCommand extends HystrixObservableCommand<User> {

    private Long[] ids;

    public GetUserListCommand(Long... ids) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("UserService")) // group的名字
                .andCommandKey(HystrixCommandKey.Factory.asKey("GetUserListCommand")) // command的名字，不指定默认就是类名
        );
        this.ids = ids;
    }

    @Override
    protected Observable<User> construct() {
        return Observable.create(new Observable.OnSubscribe<User>() {
            @Override
            public void call(Subscriber<? super User> observer) {
                try {
                    for (Long id : ids) {
                        String url = "http://127.0.0.1:8082/users/" + id;
                        // remote call
                        observer.onNext(new User(id, "John", 1, null));
                    }
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io());
    }

    @Override
    protected Observable<User> resumeWithFallback() {
        log.info("get user info too slowly and fallback");
        return Observable.create(new Observable.OnSubscribe<User>() {
            @Override
            public void call(Subscriber<? super User> observer) {
                observer.onNext(new User(-1L, "none", 1, null));
                observer.onCompleted();
            }
        });
    }
}
