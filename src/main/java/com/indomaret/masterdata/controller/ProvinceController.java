package com.indomaret.masterdata.controller;

import com.indomaret.masterdata.dto.ApiResponse;
import com.indomaret.masterdata.entity.Province;
import com.indomaret.masterdata.repository.ProvinceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/provinces")
@RequiredArgsConstructor
public class ProvinceController {

    private final ProvinceRepository provinceRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Province>>> getAllProvinces() {
        List<Province> provinces = provinceRepository.findByIsActiveTrueAndIsDeletedFalse();
        return ResponseEntity.ok(new ApiResponse<>(200, "Berhasil mengambil data provinsi", provinces));
    }
}
