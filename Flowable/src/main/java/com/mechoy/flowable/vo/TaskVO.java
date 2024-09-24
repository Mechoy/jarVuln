package com.mechoy.flowable.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ 审批列表查询结果
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskVO {
    private String id;
    private String day;
    private String name;
}
