package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.StockGoodsInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
    List<LinkedHashMap<String, Object>> queryStockGoodsPage(Page<StockGoodsInfo> page, @Param("stockGoodsInfo") StockGoodsInfo stockGoodsInfo);
}
