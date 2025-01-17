package com.example.demo.databaseTables;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
@Repository
public interface EmbarcacaoRepository extends JpaRepository<Embarcacao, Long>{
    @Transactional
    default Embarcacao updateOrInsert(Embarcacao entity) {
        return save(entity);
    }

    Embarcacao findById(long id);

    @Query("SELECT e FROM Embarcacao e WHERE e.mmsi = :mmsi")
    List<Embarcacao> findByMmsi(@Param("mmsi") String mmsi);
}
