package com.lms.repository.common;

import com.lms.entity.common.ErrorSystemLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorSystemLogRepository extends JpaRepository<ErrorSystemLog, Integer> {
    ErrorSystemLog findErrorSystemLogById(Integer userId);
}
