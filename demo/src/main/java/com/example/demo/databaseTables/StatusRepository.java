package com.example.demo.databaseTables;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long>{
    @Transactional
    default Status updateOrInsert(Status entity) {
        return save(entity);
    }

    List<Status> findByDataAfter(Date date);

    List<Status> findAll();
}
