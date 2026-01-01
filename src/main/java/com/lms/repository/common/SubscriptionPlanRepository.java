package com.lms.repository.common;

import com.lms.entity.common.SubscriptionPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlan, Integer> {
    List<SubscriptionPlan> findByIsActive(Boolean isActive);
    boolean existsById(Integer planId);
    List<SubscriptionPlan> findByDurationDaysBetweenAndIsActive(Integer from, Integer to, Boolean isActive);
    SubscriptionPlan findByIdAndIsActive(Integer planId, Boolean isActive);

    @Query("""
          SELECT s
          FROM SubscriptionPlan s
          WHERE s.name like %:name%
    """)
    List<SubscriptionPlan> findByNameContaining(@Param("name") String name);

    @Query("""
    SELECT s
    FROM SubscriptionPlan s
    WHERE s.name LIKE %:name%
      AND s.isActive = :isActive
    """)
    List<SubscriptionPlan> findByNameContainingAndIsActive(
            @Param("name") String name,
            @Param("isActive") Boolean isActive
    );
}
