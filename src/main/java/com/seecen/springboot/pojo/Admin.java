package com.seecen.springboot.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
/**
 * @author ZhangJiankun
 * @date 2019/12/10 11:19
 * 注释：
 */
@Data  //lombok 注解 包含get set toString equals hashcode
@AllArgsConstructor  //所有的有参构造
@NoArgsConstructor  //所有的无参构造
@ApiModel
public class Admin implements Serializable {

    @ApiModelProperty(value = "主键ID",dataType = "Integer",example = "1")
    private Long id;

    @ApiModelProperty(value = "账号",required = true)
    //指定字符串不能为空，“”
    @NotBlank(message = "用户名不能为空")
//    @Pattern()正则匹配
    private String account;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6,max = 18,message = "密码长度为6-18位")
    private String password;

    private String name;

    private String phone;

    private String email;

    private String status;

    private Date createtime;

    private String sex;

    private Long roleid;

    private String headPic;

    private Long operator;

    private String version;

}
