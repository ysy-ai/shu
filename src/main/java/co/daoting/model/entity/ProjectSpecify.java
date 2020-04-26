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
@TableName("project_specify")
public class ProjectSpecify {

    /**
     * 主键
     */
    private Long id;

    /**
     * 项目id
     */
    private String projectid;

    /**
     * 序号
     */
    @TableField("orderNo")
    private Integer orderNo;

    /**
     * 分项内容
     */
    private String subitem;

    /**
     * 数量
     */
    private Long quantity;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 分项系数
     */
    private BigDecimal coefficient;

    /**
     * 上报次数
     */
    private Integer reportnums;
    /**
     * 总完成工作量
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
