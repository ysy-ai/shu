package co.daoting.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@TableName("project_report")
public class ProjectReport {

    /**
     * 主键
     */
    private Long id;

    /**
     * 项目id，关联项目表projectid
     */
    private String projectid;

    /**
     * 上报次数
     */
    private Integer reportnums;

    /**
     * 事业部审核：0-未审核、1-通过、2-未通过
     */
    @TableField("deptCheck")
    private Integer deptCheck;

    /**
     * 事业部审核意见
     */
    @TableField("deptReason")
    private String deptReason;

    /**
     * 生产运营部审核：0-未审核、1-通过、2-未通过
     */
    @TableField("pdCheck")
    private Integer pdCheck;

    /**
     * 生产运营部审核意见
     */
    @TableField("pdReason")
    private String pdReason;

    /**
     * 添加时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


}
