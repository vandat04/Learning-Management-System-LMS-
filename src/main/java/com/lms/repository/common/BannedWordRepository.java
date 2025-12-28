package com.lms.repository.common;

import com.lms.entity.common.BannedWord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BannedWordRepository extends JpaRepository<BannedWord, Integer> {
}
