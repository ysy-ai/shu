package co.daoting.mapper;

import co.daoting.model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  UserMapper 接口
 * </p>
 *
 * @author wn
 * @since 2020-04-21
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
    List<User> selectUserByUsername(String projectid);

}
