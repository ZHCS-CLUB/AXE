package com.china.rop.demo.dto;

import lombok.Builder;
import lombok.Data;

/**
 *
 * @author 王贵源(wangguiyuan@chinarecrm.com.cn)
 */
@Data
@Builder
public class OwnerStorage {
	String idNo;
	int status;
	String storageOrderCode;

}
