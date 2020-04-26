package co.daoting.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
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
@TableName("specify_report")
public class SpecifyReport {

    /**
     * 主键
     */
    private Long id;

    /**
     * 项目进度报告表id：project_report.id
     */
    private Long reportid;

    /**
     * 项目id：关联分解项表projectid
     */
    private String projectid;

    /**
     * 序号：关联分解项表orderNo
     */
    @TableField("specifyid")
    private Long specifyid;

    /**
     * 本月完成工作量
     */
    private BigDecimal completed;

    /**
     * 添加时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


}
