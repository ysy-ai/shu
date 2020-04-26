package co.daoting.service.impl;

import co.daoting.common.ResultDTO;
import co.daoting.mapper.ProjectReportMapper;
import co.daoting.mapper.SpecifyReportMapper;
import co.daoting.mapper.UserMapper;
import co.daoting.model.entity.Project;
import co.daoting.mapper.ProjectMapper;
import co.daoting.model.entity.ProjectReport;
import co.daoting.model.entity.SpecifyReport;
import co.daoting.model.entity.User;
import co.daoting.service.ProjectService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Wrapper;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wn
 * @since 2020-04-21
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {
    @Autowired
    private  ProjectMapper projectMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ProjectReportMapper projectReportMapper;
    @Autowired
    private SpecifyReportMapper specifyReportMapper;
    /**
     * 新建项目
     */
    public ResultDTO addProject(Project project){
        Date date = new Date();
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        //创建项目账号和密码
        User user = new User();
        user.setUsername(project.getProjectid());
        user.setPassword("123456");
        //项目用户为3
        user.setType(3);
        user.setDeleted(true);
        user.setCreateTime(localDateTime);
        user.setUpdateTime(localDateTime);
        userMapper.insert(user);
        User user1 = userMapper.selectOne(new QueryWrapper<>(user));
        Project project3 = new Project();
        project3.setProjectid(project.getProjectid());
        //判断项目是否已经存在
        Project project2 = projectMapper.selectOne(new QueryWrapper<>(project3));
        if (ObjectUtils.allNotNull(project2)) {
            return new ResultDTO(201,"项目已存在,请重新创建");
        }
        //新建项目关联用户id
         project.setUserid(user1.getId());
        //新建项目状态为0
        project.setState(0);
        //项目创建时间
        project.setCreateTime(localDateTime);
        //更新时间默认为创建时间
        project.setUpdateTime(localDateTime);
        //添加新项目
        projectMapper.insert(project);
        //判断是否添加成功
        Project project1 = projectMapper.selectById(project.getId());
        System.out.println(project1);
        if (ObjectUtils.allNotNull(project1)) {
            return new ResultDTO(200,"添加成功");
        }else{
            return new ResultDTO(201,"请求失败");
        }
    }

    /**
     * 项目的新建确认
     */
    public ResultDTO confirmNew(String projectid){
        Project project = new Project();
        project.setProjectid(projectid);
        Project project1 = projectMapper.selectOne(new QueryWrapper<>(project));
        if (ObjectUtils.allNotNull(project1)) {
            project1.setState(1);
            projectMapper.updateById(project1);
        }else{
            return new ResultDTO(201,"请求失败");
        }
        return new ResultDTO();
    }

    /**
     * 项目细化分解上报
     */
    public ResultDTO progress(String projectid , String progress){
        Map<String , Object> map = new HashMap<>();
        map.put("projectid","Y001");
        List<SpecifyReport> specifyReports = specifyReportMapper.selectByMap(map);
        for (SpecifyReport specifyReport : specifyReports) {
            System.out.println(specifyReport);
        }
        System.out.println("===========");
        //通过id批量查询
        List list3 = new ArrayList();
        list3.add(1);
        list3.add(2);
        list3.add(3);
        list3.add(4);
        list3.add(5);
        list3.add(9);
        list3.add(10);
        list3.add(11);
        List list1 = specifyReportMapper.selectBatchIds(list3);
        list1.forEach(n-> System.out.println(n));
        System.out.println("==========分页查询");
        //分页查询
        IPage<SpecifyReport> specifyReportIPage = specifyReportMapper.selectPage(
                new Page<SpecifyReport>(0, 2),
                new QueryWrapper<SpecifyReport>()
                        .eq("completed", "20"));
        List<SpecifyReport> records = specifyReportIPage.getRecords();
        System.out.println(records.size());
        records.forEach(n-> System.out.println(n));



        Date date = new Date();
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        //根据项目id查出项目进度报告表id
        ProjectReport projectReport = projectReportMapper.selectOne(new QueryWrapper<>(new ProjectReport().setProjectid(projectid)));
        JSONArray array = JSONArray.parseArray(progress);
        SpecifyReport specifyReport = new SpecifyReport();
        List list = new ArrayList();
        for (int i = 0; i < array.size(); i++) {
            JSONObject object = array.getJSONObject(i);
            long specifyid = Long.parseLong(String.valueOf(object.get("specifyid")));
            //判断本次项目细化分解上报是否已经存在
            SpecifyReport specifyReport1 = specifyReportMapper.selectOne(new QueryWrapper<>(new SpecifyReport().setSpecifyid(specifyid)));
            System.out.println(!ObjectUtils.allNotNull(specifyReport1));
            if (ObjectUtils.allNotNull(specifyReport1)) {
                return new ResultDTO(201,"细化分解项id为："+specifyReport1.getSpecifyid()+"的项目已经存在，请重新上报");
            }
            list.add(specifyid);
            BigDecimal completed = new BigDecimal(object.get("completed").toString());
            //前端传进来的specifyid是不重复的，用做项目细化进度信息报告表的主键
            specifyReport.setId( specifyid);
            specifyReport.setSpecifyid(specifyid);
            specifyReport.setCompleted(completed);
            specifyReport.setProjectid(projectid);
            specifyReport.setReportid(projectReport.getId());
            specifyReport.setCreateTime(localDateTime);
            specifyReport.setUpdateTime(localDateTime);
            specifyReportMapper.insert(specifyReport);
            //更新项目细化分解项表的上报次数
            projectReport.setReportnums(projectReport.getReportnums()+1);
            projectReportMapper.updateById(projectReport);
        }
        for (Object arr : list) {
            long arr1 = (long) arr;
            SpecifyReport specifyReport1 = specifyReportMapper.selectOne(new QueryWrapper<>(new SpecifyReport().setSpecifyid(arr1)));
            if (!ObjectUtils.allNotNull(specifyReport1)) {
                return new ResultDTO(201,"细化分解项id为："+specifyReport1.getSpecifyid()+"的项目进度上报失败！请重新上报");
            }
        }
            return new ResultDTO(200,"项目进度上报成功");
    }

    /**
     * 查询用户
     */
    public ResultDTO selectUserByUsername(String projectid){
        System.out.println(":11111111111111");
        List<User> users = userMapper.selectUserByUsername(projectid);
        System.out.println("user"+users.size());
        users.forEach(n-> System.out.println(n));
        return new ResultDTO();
    }
    public static void main(String[] args) {
        /*String response = "{\n" +
                "\t\"process\": [{\n" +
                "\t\t\"orderNo\": 1,\n" +
                "\t\t\"completed\": 20\n" +
                "\t}]\n" +
                "}";
        JSONObject json = JSONObject.parseObject(response);
        JSONArray array = json.getJSONArray("process");
        System.out.println(array);
        for (int i = 0; i < array.size(); i++) {
            JSONObject object = array.getJSONObject(i);
            Integer orderNo = (Integer) object.get("orderNo");
            long completed = Long.parseLong(String.valueOf(object.get("completed")));
            System.out.println(orderNo);
            System.out.println(completed);
        }*/

    }
}
