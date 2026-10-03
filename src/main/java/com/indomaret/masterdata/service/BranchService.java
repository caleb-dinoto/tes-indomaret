package com.indomaret.masterdata.service;

import com.indomaret.masterdata.dto.BranchRequest;
import com.indomaret.masterdata.entity.Branch;
import com.indomaret.masterdata.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BranchService {

    private final BranchRepository branchRepository;

    // Logika untuk UPDATE Branch
    public Branch updateBranch(Integer id, BranchRequest request, String username) {
        Branch branch = branchRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Cabang tidak ditemukan!"));

        branch.setName(request.getName());
        branch.setIsActive(request.getIsActive());
        branch.setUpdatedBy(username); // Memenuhi syarat Traceability (siapa yang edit)

        return branchRepository.save(branch);
    }

    // Logika untuk DELETE Branch (Soft Delete)
    public void deleteBranch(Integer id, String username) {
        Branch branch = branchRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Cabang tidak ditemukan!"));

        branch.setIsDeleted(true); // Data tidak benar-benar dihapus dari database
        branch.setIsActive(false);
        branch.setUpdatedBy(username); // Catat siapa yang menghapus
        branch.setDeletedAt(java.time.LocalDateTime.now()); // Jika kamu punya field deletedAt

        branchRepository.save(branch);
    }
}