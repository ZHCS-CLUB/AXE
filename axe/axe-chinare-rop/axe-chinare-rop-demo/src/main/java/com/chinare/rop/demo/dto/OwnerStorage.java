package com.chinare.rop.demo.dto;

import lombok.Builder;
import lombok.Data;

/**
 *
 * @author 王贵源(wangguiyuan@chinarecrm.com.cn)
 */
@Data
@Builder
public class OwnerStorage {
    private String idNo;
    private int status;
    private String storageOrderCode;

}
