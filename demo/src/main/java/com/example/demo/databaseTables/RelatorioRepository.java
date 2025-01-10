package com.example.demo.databaseTables;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RelatorioRepository extends JpaRepository<Relatorio, Long> {
    @Transactional
    default Relatorio updateOrInsert(Relatorio relatorio) {
        return save(relatorio);
    }

    Relatorio findById(long id);

}
