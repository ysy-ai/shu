package co.daoting.service;

import co.daoting.common.ResultDTO;
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
public interface UserService extends IService<User> {

    // 登录
    ResultDTO login(String username, String password, String verifyCode);

    // 更改密码
    ResultDTO changePassword(Long userid, String passwordOld, String passwordNew);

}
