package cc.mrbird.febs.cos.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 生产流程
 *
 * @author FanK fan1ke2ke@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ProductionProcessInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 生产流程单号
     */
    private String code;

    /**
     * 生产流程单
     */
    private String name;

    /**
     * 总流程数
     */
    private Integer stepNum;

    /**
     * 当前流程（-1为报废）
     */
    private Integer currentStep;

    /**
     * 生产备注
     */
    private String content;

    /**
     * 当前状态（0.未提交 1.已提交 2.返工）
     */
    private String status;

    /**
     * 最后操作人
     */
    private String laseOperator;

    /**
     * 创建时间
     */
    private String createDate;


}
