package com.china.rop.demo.dto;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

/**
 *
 * @author 王贵源(wangguiyuan@chinarecrm.com.cn)
 */
@Data
@Builder
public class OwnerStorage {
	@NotNull
	String idNo;
	@NotNull
	int status;
	@NotNull
	String storageOrderCode;

}
