package com.chinare.axe.utils;

import java.util.List;

import org.nutz.lang.Lang;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * VXE table的保存数据封装
 * 
 * @author 王贵源(kerbores@gmail.com)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VxeTable<T> {
    @Default
    @ApiModelProperty(required = true, value = "新增记录数据")
    List<T> insertRecords = Lang.list();

    @Default
    @ApiModelProperty(required = true, value = "删除记录数据")
    List<T> removeRecords = Lang.list();

    @Default
    @ApiModelProperty(required = true, value = "更新记录数据")
    List<T> updateRecords = Lang.list();

}
