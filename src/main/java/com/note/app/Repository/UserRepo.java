package com.note.app.Repository;

import com.note.app.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UserRepo extends JpaRepository<User,Long> {
    @Query("select u from User u where u.username like %:username%")
    User findByName(String username);
}
