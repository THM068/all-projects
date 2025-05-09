package com.master.masterspringboot.database.entity.repos;

import com.master.masterspringboot.database.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
}
