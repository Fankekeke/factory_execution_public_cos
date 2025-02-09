package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.StockGoodsInfo;
import cc.mrbird.febs.cos.entity.StockInfo;
import cc.mrbird.febs.cos.service.IStockGoodsInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author FanK
 */
@RestController
@RequestMapping("/cos/stock-goods-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StockGoodsInfoController {

    private final IStockGoodsInfoService stockGoodsInfoService;

    /**
     * 分页获取物资商品库房信息
     *
     * @param page           分页对象
     * @param stockGoodsInfo 物资商品库房信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<StockGoodsInfo> page, StockGoodsInfo stockGoodsInfo) {
        return R.ok(stockGoodsInfoService.queryStockGoodsPage(page, stockGoodsInfo));
    }

    /**
     * 分页获取物品出入库详情
     *
     * @param page
     * @param stockInfo
     * @return
     */
    @GetMapping("/detail/page")
    public R stockInfoDetailPage(Page page, StockGoodsInfo stockInfo) {
        return R.ok(stockGoodsInfoService.stockInfoDetailPage(page, stockInfo));
    }

    /**
     * 分页获取库房信息
     *
     * @param stockInfo
     * @return
     */
    @GetMapping("/list")
    public R page(StockGoodsInfo stockInfo) {
        return R.ok(stockGoodsInfoService.stockInfoByList(stockInfo));
    }

    /**
     * 入库
     *
     * @param goods
     * @param custodian
     * @param putUser
     * @param content
     * @return
     */
    @PostMapping("/put")
    public R put(String goods, String custodian, String putUser, String content, BigDecimal price) {
        return R.ok(stockGoodsInfoService.stockPut(goods, custodian, putUser, content, price));
    }

    /**
     * 获取物资商品库房信息详情
     *
     * @param id 主键
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(stockGoodsInfoService.getById(id));
    }

    /**
     * 新增物资商品库房信息
     *
     * @param stockGoodsInfo 物资商品库房信息
     * @return 结果
     */
    @PostMapping
    public R save(StockGoodsInfo stockGoodsInfo) {
        stockGoodsInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(stockGoodsInfoService.save(stockGoodsInfo));
    }

    /**
     * 修改物资商品库房信息
     *
     * @param stockGoodsInfo 物资商品库房信息
     * @return 结果
     */
    @PutMapping
    public R edit(StockGoodsInfo stockGoodsInfo) {
        return R.ok(stockGoodsInfoService.updateById(stockGoodsInfo));
    }

    /**
     * 删除物资商品库房信息
     *
     * @param ids 主键
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(stockGoodsInfoService.removeByIds(ids));
    }
}
