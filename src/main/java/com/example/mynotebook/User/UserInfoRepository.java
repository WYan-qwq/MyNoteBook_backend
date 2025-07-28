package com.example.mynotebook.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfoEntity, Integer>{
    boolean existsByEmailAndPassword(String email, String password);
    Optional<UserInfoEntity> findByEmailAndPassword(String email, String password);

    boolean existsByEmail(String email);
    Optional<UserInfoEntity> findByEmail(String email);
    List<UserInfoEntity> findByUsernameContainingIgnoreCase(String username);

    List<UserInfoEntity> findByFirstNameContainingIgnoreCase(String firstName);

    List<UserInfoEntity> findByLastNameContainingIgnoreCase(String lastName);

    List<UserInfoEntity> findByState(Boolean state);

    // If you also want to support precise search by id:
    Optional<UserInfoEntity> findByDeviceOwnerId(Integer id);

}
