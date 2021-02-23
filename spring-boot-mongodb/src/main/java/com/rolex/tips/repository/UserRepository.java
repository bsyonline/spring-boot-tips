package com.rolex.tips.repository;

import com.rolex.tips.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public interface UserRepository extends MongoRepository<User, Long> {

}
