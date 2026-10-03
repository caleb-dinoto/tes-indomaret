package com.indomaret.masterdata.service;

import com.indomaret.masterdata.entity.Store;
import com.indomaret.masterdata.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    // Logika untuk mencari Store berdasarkan Provinsi (Whitelist otomatis ikut terbawa)
    public List<Store> searchStores(String provinceName) {
        if (provinceName == null || provinceName.trim().isEmpty()) {
            provinceName = ""; // Menghindari error jika pencarian kosong
        }
        return storeRepository.findStoresByProvinceNameOrWhitelisted(provinceName);
    }

    // Logika untuk mengubah status Whitelist pada sebuah Store
    public Store updateWhitelistStatus(Integer storeId, boolean isWhitelisted, String username) {
        Store store = storeRepository.findByIdAndIsDeletedFalse(storeId)
                .orElseThrow(() -> new RuntimeException("Toko tidak ditemukan!"));

        store.setIsWhitelisted(isWhitelisted);
        store.setUpdatedBy(username);

        return storeRepository.save(store);
    }
}