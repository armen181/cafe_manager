package net.ddns.armen181.cafe.cafe_manager.repository;


import net.ddns.armen181.cafe.cafe_manager.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEMail(String userName);
}
