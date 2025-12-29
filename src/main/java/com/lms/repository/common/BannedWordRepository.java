package com.lms.repository.common;

import com.lms.entity.common.BannedWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannedWordRepository extends JpaRepository<BannedWord, Integer> {
}
