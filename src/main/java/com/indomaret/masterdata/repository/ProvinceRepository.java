package com.indomaret.masterdata.repository;

import com.indomaret.masterdata.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository 
public interface ProvinceRepository extends JpaRepository<Province, Integer> {
    //mengambil semua data provinsi yang aktif & blm dihapus
    List<Province> findByIsActiveTrueAndIsDeletedFalse();
    Optional<Province> findByIdAndIsActiveTrueAndIsDeletedFalse(Integer id);
}
