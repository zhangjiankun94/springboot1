package com.seecen.springboot.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author name
 * @date 2019/12/26 17:29
 * 描述：统一返回结果对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultInfo<T> implements Serializable {
    //状态码
    private int code;
    //消息内容
    private String message;
    //返回数据
    private T data;
}
