package com.indomaret.masterdata.repository;

import com.indomaret.masterdata.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Integer> {
    // Untuk mencari Cabang yang belum di soft-delete (dipakai waktu Update/Delete)
    Optional<Branch> findByIdAndIsDeletedFalse(Integer id);

    List<Branch> findByIsActiveTrueAndIsDeletedFalse();
}