package co.daoting.service.impl;

import co.daoting.common.CommonUtils;
import co.daoting.common.ResultDTO;
import co.daoting.common.ResultEnum;
import co.daoting.mapper.DepartmentMapper;
import co.daoting.mapper.ProjectMapper;
import co.daoting.mapper.UserMapper;
import co.daoting.model.entity.Department;
import co.daoting.model.entity.Project;
import co.daoting.model.entity.User;
import co.daoting.model.vo.UserDeptProVO;
import co.daoting.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wn
 * @since 2020-04-21
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private DepartmentMapper departmentMapper;

    @Resource
    private ProjectMapper projectMapper;

    @Override
    public ResultDTO login(String username, String password, String verifyCode) {

        // 返回的对象
        UserDeptProVO userDeptProVO = new UserDeptProVO();

        // user对象
        User user = new User();

        // 返回的结果
        ResultDTO resultDTO = null;

        if (username == null) {
            if (CommonUtils.isEmptyAllString(password, verifyCode)) {
                resultDTO = new ResultDTO(ResultEnum.MISS_API_PARAM);

                return resultDTO;
            }
        } else {
            if (verifyCode != null) {
                if (!verifyCode.equals(CommonUtils.cleanXSS(verifyCode))) {
                    resultDTO = new ResultDTO(201, "请勿使用非法攻击手段");

                    return resultDTO;
                }
            }

            user = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        }

        if (user == null) {
            // 用户未注册
            resultDTO = new ResultDTO(ResultEnum.WRONG_LOGIN_OR_PASSWORD);

            return resultDTO;
        }

        // 对密码进行MD5加密
        String newpwd = CommonUtils.md5(password);

        if (!newpwd.equals(user.getPassword())) {
            resultDTO = new ResultDTO(ResultEnum.WRONG_LOGIN_OR_PASSWORD);

            return resultDTO;
        }

        // 用户名和密码都比对成功
        // 查询用户对应部门
        Department department = departmentMapper.selectOne(new QueryWrapper<Department>().eq("deptid", user.getDeptid()));

        if (department == null) {
            resultDTO = new ResultDTO(204, "该用户无所属部门");
        }

        // 查询用户对应的项目
        Project project = projectMapper.selectOne(new QueryWrapper<Project>().eq("projectid", user.getProjectid()));

        if (project == null) {
            if (resultDTO == null) {

                resultDTO = new ResultDTO(204, "该用户无项目");
            }

            resultDTO.setMessage(resultDTO.getMessage() + "，该用户无项目");
        }

        userDeptProVO.setUser(user);
        userDeptProVO.setUserid(user.getId());
        userDeptProVO.setUsername(user.getUsername());
        userDeptProVO.setDeptid(Long.parseLong(user.getDeptid()));
        userDeptProVO.setDeptname(department.getDeptname());
        userDeptProVO.setProjectid(Long.parseLong(user.getProjectid()));
        userDeptProVO.setProjectname(project.getProjectname());

        if (resultDTO == null) {
            resultDTO = new ResultDTO();
        }

        resultDTO.setResult(userDeptProVO);

        return resultDTO;
    }

    @Override
    public ResultDTO changePassword(Long userid, String passwordOld, String passwordNew) {

        // 返回的结果
        ResultDTO resultDTO = null;

        // user对象
        User user = new User();

        if (userid == null) {
            if (CommonUtils.isEmptyAllString(passwordOld, passwordNew)) {
                resultDTO = new ResultDTO(ResultEnum.MISS_API_PARAM);

                return resultDTO;
            }
        } else {
            if (passwordOld == null) {
                resultDTO = new ResultDTO(201, "旧密码不能为空");

                return resultDTO;
            }

            if (passwordNew == null) {
                resultDTO = new ResultDTO(201, "新密码不能为空");

                return resultDTO;
            }

            user = userMapper.selectById(userid);
        }

        if (user == null) {
            resultDTO = new ResultDTO(201, "不存在该用户");

            return resultDTO;
        }

        passwordOld = CommonUtils.md5(passwordOld);

        // 比较旧密码是否相等
        if (!passwordOld.equals(user.getPassword())) {
            resultDTO = new ResultDTO(201, "旧密码不匹配");

            return resultDTO;
        }

        passwordNew = CommonUtils.md5(passwordNew);
        user.setPassword(passwordNew);
        user.setUpdateTime(LocalDateTime.now());

        int i = userMapper.updateById(user);

        if (i <= 0) {
            resultDTO = new ResultDTO(ResultEnum.SERVER_EXCEPTION);

            return resultDTO;
        }

        resultDTO = new ResultDTO();

        return resultDTO;
    }
}
