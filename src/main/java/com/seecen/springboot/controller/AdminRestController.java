package com.seecen.springboot.controller;

import com.seecen.springboot.pojo.Admin;
import com.seecen.springboot.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author name
 * @date 2019/12/25 15:59
 */
@Api(tags = "用户操作")//指定整个接口类的描述
@RestController
public class AdminRestController {
    @Resource(name = "adminServiceWithCache")
    private AdminService adminService;

    @ApiOperation(value = "根据id查询用户",notes = "id不能为空")
    @ApiImplicitParam(name = "id",//数据名
                    required = true,//required是否必填
                    value = "用户id",//指定参数描述
                    dataType = "Long")//指定数据类型
    @GetMapping("/admin/{id}")
    public ResponseEntity<Admin> findAdminById(@PathVariable(name = "id") Long id){
        Admin admin = adminService.selectById(id);
        if (admin!=null){
            //如果查询到用户数据，则返回
            return new ResponseEntity<Admin>(admin, HttpStatus.OK);
        }
        //如果没有资源，返回404
        return new ResponseEntity<Admin>(admin, HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "根据id修改用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",
                    required = true,
                    value = "用户id", dataType = "Long"),
//            @ApiImplicitParam(name = "admin", required = true, dataTypeClass = Admin.class)
        }
    )
    @PutMapping("/admin/{id}")
    public ResponseEntity update(Admin admin){
        adminService.update2(admin);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "根据id删除用户",notes = "id不能为空")
    @ApiImplicitParam(name = "id",
            required = true,//required是否必填
            value = "用户id",
            dataType = "Long")
    @DeleteMapping("/admin/{id}")
    public ResponseEntity delete(@PathVariable(name = "id") Long id){
        adminService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    /*
    * @Valid 指定校验对应的参数
    * */
    @ApiOperation(value = "新增用户")
    @PostMapping("/admin")
    public ResponseEntity add(@Valid @RequestBody Admin admin){
        int i = adminService.insert(admin);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/admins")
    public ResponseEntity<List<Admin>> list(){
        List<Admin> admins = adminService.selectAll();
        return new ResponseEntity<List<Admin>>(admins,HttpStatus.OK);
    }
}
