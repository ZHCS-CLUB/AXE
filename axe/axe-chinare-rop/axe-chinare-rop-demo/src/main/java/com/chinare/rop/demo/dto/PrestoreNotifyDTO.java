package com.chinare.rop.demo.dto;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import lombok.Builder;
import lombok.Data;

/**
 * class WayBillNotify
 *
 * @author xiaoshan
 * @title WayBillNotify
 * @Date 2019/9/27
 */
@Data
@Builder
public class PrestoreNotifyDTO {
    private List<OwnerStorage> ownerStorages;
    @Min(value = 1, message = "运单状态不能为空")
    private int status;
    @NotEmpty(message = "运单号不能为空")
    private String waybillCode;

}
