package com.indomaret.masterdata.controller;

import com.indomaret.masterdata.dto.ApiResponse;
import com.indomaret.masterdata.entity.Store;
import com.indomaret.masterdata.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Store>>> searchStores(
            @RequestParam(required = false, defaultValue = "") String province) {
        List<Store> stores = storeService.searchStores(province);
        return ResponseEntity.ok(new ApiResponse<>(200, "Berhasil mengambil data toko", stores));
    }

    @PutMapping("/{id}/whitelist")
    public ResponseEntity<ApiResponse<Store>> updateWhitelist(
            @PathVariable Integer id,
            @RequestParam boolean status,
            Principal principal) {
        Store store = storeService.updateWhitelistStatus(id, status, principal.getName());
        return ResponseEntity.ok(new ApiResponse<>(200, "Status whitelist berhasil diubah", store));
    }
}
