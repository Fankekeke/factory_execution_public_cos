package cc.mrbird.febs.cos.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 物资报废
 *
 * @author FanK fan1ke2ke@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ScrapInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 单号
     */
    private String num;

    /**
     * 报废主题
     */
    private String theme;

    /**
     * 部门
     */
    private String deptId;

    /**
     * 报废人
     */
    private Integer staffId;

    /**
     * 备注
     */
    private String content;

    /**
     * 创建时间
     */
    private String createDate;


}
