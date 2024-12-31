package com.zys.elec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.zys.elec.entity.ElectricityRecord;
import com.zys.elec.entity.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ElectricityRecordRepository extends JpaRepository<ElectricityRecord, Long> {

    Optional<List<ElectricityRecord>> findByUser(User user);

    Optional<List<ElectricityRecord>> findByRecordDate(LocalDate date);

    Optional<List<ElectricityRecord>> findByRecordDateBetween(LocalDate startDate, LocalDate endDate);

    Optional<List<ElectricityRecord>> findByRecordDateBetweenAndUser(LocalDate startDate, LocalDate endDate,
            User userId);

    Optional<List<ElectricityRecord>> findByUserAndRecordDate(User user, LocalDate recordDate);


    @Query("SELECT er FROM ElectricityRecord er WHERE er.id NOT IN (SELECT p.electricityRecord.id FROM Predict p)")
    Optional<List<ElectricityRecord>> findRecordsWithoutPredicts();

}
