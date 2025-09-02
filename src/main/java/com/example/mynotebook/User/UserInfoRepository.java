package com.example.mynotebook.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfoEntity, Integer> {
    Optional<UserInfoEntity> findByEmail(String email);
    boolean existsByEmail(String email);

    @Modifying
    @Query("update UserInfoEntity u set u.userName = :userName where u.id = :userId")
    int updateUserName(@Param("id") Integer id, @Param("userName") String userName);

    @Modifying
    @Query("update UserInfoEntity u set u.picture = :picture where u.id = :userId")
    int updatePicture(@Param("id") Integer id, @Param("picture") String picture);

    @Modifying
    @Query("update UserInfoEntity u set u.password = :password where u.id = :userId")
    int updatePassword(@Param("id") Integer id, @Param("password") String password);
}

