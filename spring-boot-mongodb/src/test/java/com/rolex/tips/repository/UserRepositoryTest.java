package com.rolex.tips.repository;

import com.rolex.tips.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void test01() {
        User user = new User();
        user.setId(1L);
        user.setName("John");
        user.setAge(20);
        user.setGender(1);
        user.setBirth(LocalDate.of(1990, 1, 5));
        userRepository.save(user);
    }

    @Test
    public void test02() {
        List<User> list = new ArrayList<>();
        IntStream.range(2, 21).forEach(i -> {
            int r = new Random().nextInt(100) + 1;
            User user = new User();
            user.setId(new Long(i));
            user.setName("user" + i);
            user.setAge(r);
            user.setGender(r % 2 + 1);
            user.setBirth(LocalDate.of(LocalDate.now().getYear() - r, new Random().nextInt(12) + 1, new Random().nextInt(28) + 1));
            list.add(user);
        });
        userRepository.saveAll(list);
    }

    @Test
    public void test03() {
        Query query = new Query(Criteria.where("name").is("John"));
        User user = mongoTemplate.findOne(query, User.class);
        System.out.println(user);
    }

}
