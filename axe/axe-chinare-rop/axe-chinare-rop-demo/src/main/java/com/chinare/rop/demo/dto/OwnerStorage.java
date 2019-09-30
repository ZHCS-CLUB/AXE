package com.chinare.rop.demo.dto;

import lombok.Builder;
import lombok.Data;

/**
 * class OwnerStorage
 *
 * @author xiaoshan
 * @title OwnerStorage
 * @Date 2019/9/27
 */
@Data
@Builder
public class OwnerStorage {
    private String idNo;
    private int status;
    private String storageOrderCode;

}
