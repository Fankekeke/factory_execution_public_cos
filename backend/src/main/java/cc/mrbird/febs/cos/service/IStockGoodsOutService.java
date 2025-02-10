package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.StockGoodsOut;
import cc.mrbird.febs.cos.entity.StockOut;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author FanK
 */
public interface IStockGoodsOutService extends IService<StockGoodsOut> {

    // 库房出库
    Boolean stockOut(StockGoodsOut stockOut);
}
