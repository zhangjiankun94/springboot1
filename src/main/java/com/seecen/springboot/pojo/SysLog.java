package com.seecen.springboot.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author name
 * @date 2019/12/25 10:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysLog {
    private Long id;
    private String type;
    private Long operator;
    private String ip;
    private String content;
    private String data;
    private Date time;
}
