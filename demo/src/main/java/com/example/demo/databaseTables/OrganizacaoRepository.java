package com.example.demo.databaseTables;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface OrganizacaoRepository extends JpaRepository<Organizacao, Long> {
    @Transactional
    default Organizacao updateOrInsert(Organizacao entity) {
        return save(entity);
    }

    Organizacao findById(long id);
}
