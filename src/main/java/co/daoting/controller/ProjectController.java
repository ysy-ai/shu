package co.daoting.controller;


import co.daoting.common.CommonUtils;
import co.daoting.common.ResultDTO;
import co.daoting.model.entity.Project;
import co.daoting.model.entity.User;
import co.daoting.service.ProjectService;
import com.alibaba.druid.util.StringUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wn
 * @since 2020-04-21
 */
@RestController
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @RequestMapping("/addProject")
    @ApiOperation(value = "addProject", notes = "新建项目")
    public ResultDTO addProject(String projectid, String projectname, String contractvalue, String deptid, String startdate, String period) {
        // 参数校验 projeceid
        if (StringUtils.isEmpty(projectid)) return new ResultDTO(201, "缺少参数");
        // 参数校验 projectname
        if (StringUtils.isEmpty(projectname)) return new ResultDTO(201, "缺少参数");
        // 参数校验 contractvalue
        if (StringUtils.isEmpty(contractvalue)) return new ResultDTO(201, "缺少参数");
        // 参数校验 deptid
        if (StringUtils.isEmpty(deptid)) return new ResultDTO(201, "缺少参数");
        // 参数校验 startdate
        if (StringUtils.isEmpty(startdate)) return new ResultDTO(201, "缺少参数");
        // 参数校验 period
        if (StringUtils.isEmpty(period)) return new ResultDTO(201, "缺少参数");
        //入参封装
        Project project = new Project();
        project.setProjectid(projectid);
        project.setProjectname(projectname);
        project.setContractvalue(new BigDecimal(contractvalue));
        project.setDeptid(deptid);
        project.setStartdate(LocalDate.parse(startdate, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        project.setPeriod(period);
        return projectService.addProject(project);
    }

    @RequestMapping("/confirmNew")
    @ApiOperation(value = "confirmNew", notes = "项目的新建确认")
    public ResultDTO confirmNew(String projectid) {
        // 参数校验 projectid
        if (StringUtils.isEmpty(projectid)) return new ResultDTO(201, "缺少参数");
        return projectService.confirmNew(projectid);
    }

    @RequestMapping("/progress")
    @ApiOperation(value = "progress", notes = "项目细化分解上报")
    public ResultDTO progress(String projectid, @RequestBody String progress) {
        // 参数校验 projectid
        if (StringUtils.isEmpty(projectid)) return new ResultDTO(201, "缺少参数");
        // 参数校验 progress
        if (StringUtils.isEmpty(progress)) return new ResultDTO(201, "缺少参数");
        return projectService.progress(projectid, progress);
    }

    /**
     * 查询用户
     */
    @RequestMapping("/selectUserByUsername")
    @ApiOperation(value = "selectUserByUsername", notes = "查询用户")
    public ResultDTO selectUserByUsername(String projectid) {
        projectService.selectUserByUsername(projectid);
        return new ResultDTO();
    }

    /**
     * 计算年龄
     */
    @RequestMapping("/countAge")
    public int countAge(String strDate) throws Exception {
        CommonUtils commonUtils = new CommonUtils();
        Date parse = commonUtils.parse(strDate);
        return CommonUtils.getAge(parse);
    }

    /**
     * MD5加密
     */
    @RequestMapping("/md")
    public String md(String s){
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            byte[] strTemp = s.getBytes();
            // 使用MD5创建MessageDigest对象
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte b = md[i];
                str[k++] = hexDigits[b >> 4 & 0xf];
                str[k++] = hexDigits[b & 0xf];
            }
            return new String(str).toUpperCase();
        } catch (Exception e) {
            return s;
        }
    }

    public static void main(String[] args) {
        String str = "你好,我是李华,哈哈";
        String[] split = str.split(",");
        for (String s : split) {
            System.out.println(s);
        }
        Random random = new Random();
        String result = "";
        List list = new ArrayList();
        for (int i = 0; i < 20; i++){
            for (int j = 0; j < 6; j++) {
                result += random.nextInt(10);
            }
            list.add(result+"  ");
        }
        list.forEach(n-> System.out.println(n+"  "));
    }
}

