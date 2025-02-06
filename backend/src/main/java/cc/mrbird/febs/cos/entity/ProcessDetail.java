package cc.mrbird.febs.cos.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 生产流程阶段消耗
 *
 * @author FanK fan1ke2ke@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ProcessDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 流程ID（从1开始）
     */
    private Integer stepIndex;

    /**
     * 生产流程单号
     */
    private String processCode;

    /**
     * 出库单号
     */
    private String outNum;

    /**
     * 采用设备ID
     */
    private Integer deviceId;

    /**
     * 采用设备
     */
    @TableField(exist = false)
    private String deviceName;
}
