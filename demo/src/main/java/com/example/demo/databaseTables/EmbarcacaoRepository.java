package com.example.demo.databaseTables;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
@Repository
public interface EmbarcacaoRepository extends JpaRepository<Embarcacao, Long>{
    @Transactional
    default Embarcacao updateOrInsert(Embarcacao entity) {
        return save(entity);
    }
}
