package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.StockGoodsInfo;
import cc.mrbird.febs.cos.entity.StockInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
public interface StockGoodsInfoMapper extends BaseMapper<StockGoodsInfo> {

    /**
     * 分页获取物资商品库房信息
     *
     * @param page           分页对象
     * @param stockGoodsInfo 物资商品库房信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryStockGoodsPage(Page<StockGoodsInfo> page, @Param("stockGoodsInfo") StockGoodsInfo stockGoodsInfo);

    // 分页获取物品出入库详情
    IPage<LinkedHashMap<String, Object>> stockInfoDetailPage(Page page, @Param("stockGoodsInfo") StockGoodsInfo stockInfo);

    List<LinkedHashMap<String, Object>> stockInfoByList(@Param("stockGoodsInfo") StockGoodsInfo stockInfo);

    List<LinkedHashMap<String, Object>> stockGoodsInfoByList(@Param("stockGoodsInfo") StockGoodsInfo stockGoodsInfo);

    // 分页获取物品出入库详情
    IPage<LinkedHashMap<String, Object>> stockGoodsInfoDetailPage(Page page, @Param("stockGoodsInfo") StockGoodsInfo stockGoodsInfo);


    // 入库统计
    List<LinkedHashMap<String, Object>> stockPutRate();

    // 入库类型统计
    List<LinkedHashMap<String, Object>> stockPutTypeRate();

    // 出库统计
    List<LinkedHashMap<String, Object>> stockOutRate();

    // 出库类型统计
    List<LinkedHashMap<String, Object>> stockOutTypeRate();

    // 本月数据统计
    LinkedHashMap<String, Object> stockGoodsInfoByMonth();
}
