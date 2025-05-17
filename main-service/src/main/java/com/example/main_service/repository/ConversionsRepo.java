package com.example.main_service.repository;

import com.example.main_service.entity.Conversions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversionsRepo extends JpaRepository<Conversions, Long> {
}
