package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.StockGoodsPut;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
public interface IStockGoodsPutService extends IService<StockGoodsPut> {

    /**
     * 分页获取物资商品库房信息
     *
     * @param page           分页对象
     * @param stockGoodsPut 物资商品库房信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryStockGoodsPutPage(Page<StockGoodsPut> page, StockGoodsPut stockGoodsPut);
}
