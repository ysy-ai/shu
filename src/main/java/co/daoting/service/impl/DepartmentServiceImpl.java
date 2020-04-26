package co.daoting.service.impl;

import co.daoting.model.entity.Department;
import co.daoting.mapper.DepartmentMapper;
import co.daoting.service.DepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wn
 * @since 2020-04-22
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

}
