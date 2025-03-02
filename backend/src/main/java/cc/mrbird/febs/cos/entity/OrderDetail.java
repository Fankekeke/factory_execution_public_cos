package cc.mrbird.febs.cos.entity;

import java.math.BigDecimal;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 订单详情
 *
 * @author FanK fan1ke2ke@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 订单编号
     */
    private String orderCode;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品类型
     */
    private Integer typeId;

    /**
     * 型号
     */
    private String type;
    private String unit;

    /**
     * 商品数量
     */
    private Integer amount;

    /**
     * 总价格
     */
    private BigDecimal goodsPrice;

    /**
     * 单价
     */
    private BigDecimal unitPrice;


}
