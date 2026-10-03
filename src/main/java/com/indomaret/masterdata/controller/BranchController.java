package com.indomaret.masterdata.controller;

import com.indomaret.masterdata.dto.ApiResponse;
import com.indomaret.masterdata.dto.BranchRequest;
import com.indomaret.masterdata.entity.Branch;
import com.indomaret.masterdata.repository.BranchRepository;
import com.indomaret.masterdata.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/branches")
@RequiredArgsConstructor
public class BranchController {

    private final BranchRepository branchRepository;
    private final BranchService branchService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Branch>>> getAllBranches() {
        List<Branch> branches = branchRepository.findByIsActiveTrueAndIsDeletedFalse();
        return ResponseEntity.ok(new ApiResponse<>(200, "Berhasil mengambil data cabang", branches));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Branch>> updateBranch(
            @PathVariable Integer id,
            @RequestBody BranchRequest request,
            Principal principal) {
        Branch updatedBranch = branchService.updateBranch(id, request, principal.getName());
        return ResponseEntity.ok(new ApiResponse<>(200, "Branch berhasil diupdate", updatedBranch));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteBranch(
            @PathVariable Integer id,
            Principal principal) {
        branchService.deleteBranch(id, principal.getName());
        return ResponseEntity.ok(new ApiResponse<>(200, "Branch berhasil dihapus (soft-delete)", null));
    }
}
