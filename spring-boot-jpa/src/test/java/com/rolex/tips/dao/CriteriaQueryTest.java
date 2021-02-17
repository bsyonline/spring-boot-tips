/*
 * Copyright (C) 2019 bsyonline
 */
package com.rolex.tips.dao;

import com.rolex.tips.entity.FileSubDO;
import com.rolex.tips.entity.FileSubDO_;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * @author rolex
 * @since 2019
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CriteriaQueryTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    public void findAll() {
        String uid = "1";
        Date start = Date.from(LocalDate.of(2017, 2, 6).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date end = new Date();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<FileSubDO> query = criteriaBuilder.createQuery(FileSubDO.class);
        Root<FileSubDO> root = query.from(FileSubDO.class);
        List<Predicate> list = Lists.newArrayList();
        if (uid != null) {
//            list.add(criteriaBuilder.equal(root.get(FileSubDO_.uid), uid));
            list.add(criteriaBuilder.equal(root.get("uid"), uid));
        }
        if (start != null) {
            list.add(criteriaBuilder.greaterThanOrEqualTo(root.get(FileSubDO_.idt), start));
        }
        if (end != null) {
            list.add(criteriaBuilder.lessThanOrEqualTo(root.get(FileSubDO_.idt), end));
        }
        query.where(list.toArray(new Predicate[list.size()]));
        TypedQuery<FileSubDO> typedQuery = entityManager.createQuery(query);
        List<FileSubDO> orders = typedQuery.getResultList();
        System.out.println(orders);
    }

}
