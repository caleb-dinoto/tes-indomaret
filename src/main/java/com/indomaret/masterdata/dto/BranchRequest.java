package com.indomaret.masterdata.dto;

import lombok.Data;

@Data
public class BranchRequest {
    private String name;
    private Boolean isActive;
}