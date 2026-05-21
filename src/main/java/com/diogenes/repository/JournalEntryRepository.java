package com.diogenes.repository;

import com.diogenes.model.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JournalEntryRepository extends JpaRepository<JournalEntry, Long> {
    List<JournalEntry> findTop5ByOrderByEntryDateDescCreatedAtDesc();
}
