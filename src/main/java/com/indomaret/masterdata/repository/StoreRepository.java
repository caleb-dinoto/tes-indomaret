package com.indomaret.masterdata.repository;

import com.indomaret.masterdata.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Integer> {

    // Memenuhi Story: Search by province, Active/Non-deleted only, & Whitelist
    // logic
    @Query("SELECT s FROM Store s " +
            "JOIN s.branch b " +
            "JOIN b.province p " +
            "WHERE (LOWER(p.name) LIKE LOWER(CONCAT('%', :provinceName, '%')) OR s.isWhitelisted = true) " +
            "AND s.isActive = true AND s.isDeleted = false " +
            "AND b.isActive = true AND b.isDeleted = false " +
            "AND p.isActive = true AND p.isDeleted = false")
    List<Store> findStoresByProvinceNameOrWhitelisted(@Param("provinceName") String provinceName);

    Optional<Store> findByIdAndIsDeletedFalse(Integer id);
}