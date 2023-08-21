package com.code.repository;

import com.code.model.Membros;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembrosRepository extends JpaRepository<Membros, Long> {
}
