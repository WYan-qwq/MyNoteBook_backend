package com.example.mynotebook.Plan;
import com.example.mynotebook.User.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlanRepository extends JpaRepository<PlanEntity, Integer> {
    List<PlanEntity> findByUser_IdAndDateOrderByHourAscMinuteAscIdAsc(Integer userId, LocalDate date);
    List<PlanEntity> findByUser_IdAndDateBetweenOrderByDateAscHourAscMinuteAscIdAsc(
            Integer userId, LocalDate start, LocalDate end
    );
    List<PlanEntity> findByUser_IdAndDateOrderByHourAscMinuteAsc(Integer userId, LocalDate date);

}

