package co.daoting.service;

import co.daoting.common.ResultDTO;
import co.daoting.model.entity.Project;
import co.daoting.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wn
 * @since 2020-04-21
 */
public interface ProjectService extends IService<Project> {
    /**
     * 新建项目
     */
    ResultDTO addProject(Project project);
    /**
     * 项目的新建确认
     */
    ResultDTO confirmNew(String projectid);
    /**
     * 项目细化分解上报
     */
    public ResultDTO progress(String projectid , String progress);

    //查询用户
    ResultDTO selectUserByUsername(String projectid);
}
