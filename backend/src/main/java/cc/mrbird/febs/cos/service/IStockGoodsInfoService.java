package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.StockGoodsInfo;
import cc.mrbird.febs.cos.entity.StockInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
public interface IStockGoodsInfoService extends IService<StockGoodsInfo> {

    /**
     * 分页获取物资商品库房信息
     *
     * @param page           分页对象
     * @param stockGoodsInfo 物资商品库房信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryStockGoodsPage(Page<StockGoodsInfo> page, StockGoodsInfo stockGoodsInfo);

    // 入库
    Boolean stockPut(String goods, String custodian, String putUser, String content, BigDecimal price);

    // 分页获取物品出入库详情
    IPage<LinkedHashMap<String, Object>> stockInfoDetailPage(Page page, StockGoodsInfo stockGoodsInfo);

    // 获取库房信息
    List<LinkedHashMap<String, Object>> stockInfoByList(StockGoodsInfo stockGoodsInfo);
}
