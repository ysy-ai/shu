package co.daoting.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author wn
 * @since 2020-04-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Project  {

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 项目id
     */
    private String projectid;

    /**
     * 项目名称
     */
    private String projectname;

    /**
     * 合同总产值（万）
     */
    private BigDecimal contractvalue;

    /**
     * 所属部门
     */
    private String deptid;

    /**
     * 项目开工时间
     */
    private LocalDate startdate;

    /**
     * 项目工期
     */
    private String period;

    /**
     * 关联用户id：user.id
     */
    private Long userid;

    /**
     * 实施状态：0-新项目、1-进行中、2-已完成
     */
    private Integer state;

    /**
     * 添加时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


}
