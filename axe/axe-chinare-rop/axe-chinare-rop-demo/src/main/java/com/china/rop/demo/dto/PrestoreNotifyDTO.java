package com.china.rop.demo.dto;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import lombok.Builder;
import lombok.Data;

/**
 *
 * @author 王贵源(wangguiyuan@chinarecrm.com.cn)
 */
@Data
@Builder
public class PrestoreNotifyDTO {
    private List<OwnerStorage> ownerStorages;
    @Min(value = 1, message = "运单状态不能为空")
     int status;
    @NotEmpty(message = "运单号不能为空")
     String waybillCode;

}
