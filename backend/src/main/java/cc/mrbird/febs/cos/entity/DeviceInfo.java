package cc.mrbird.febs.cos.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 设备管理
 *
 * @author FanK fan1ke2ke@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DeviceInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 设备编号
     */
    private String code;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 负责人
     */
    private String deviceCharge;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 设备备注
     */
    private String content;

    /**
     * 设备图片
     */
    private String images;

    /**
     * 设备状态（0.废弃 1.正常 2.维修保养中）
     */
    private String status;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 设备类型
     */
    private String type;

    /**
     * 设备地址
     */
    private String address;


}
