package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.StockGoodsPut;
import cc.mrbird.febs.cos.service.IStockGoodsPutService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author FanK
 */
@RestController
@RequestMapping("/cos/stock-goods-put")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StockGoodsPutController {

    private final IStockGoodsPutService stockGoodsPutService;

    /**
     * 分页获取物资商品库房信息
     *
     * @param page          分页对象
     * @param stockGoodsPut 物资商品库房信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<StockGoodsPut> page, StockGoodsPut stockGoodsPut) {
        return R.ok(stockGoodsPutService.queryStockGoodsPutPage(page, stockGoodsPut));
    }

    /**
     * 获取商品库房入库记录
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(stockGoodsPutService.list());
    }

    /**
     * 获取物资商品库房信息详情
     *
     * @param id 主键
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(stockGoodsPutService.getById(id));
    }

    /**
     * 新增物资商品库房信息
     *
     * @param stockGoodsPut 物资商品库房信息
     * @return 结果
     */
    @PostMapping
    public R save(StockGoodsPut stockGoodsPut) {
        stockGoodsPut.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(stockGoodsPutService.save(stockGoodsPut));
    }

    /**
     * 修改物资商品库房信息
     *
     * @param stockGoodsPut 物资商品库房信息
     * @return 结果
     */
    @PutMapping
    public R edit(StockGoodsPut stockGoodsPut) {
        return R.ok(stockGoodsPutService.updateById(stockGoodsPut));
    }

    /**
     * 删除物资商品库房信息
     *
     * @param ids 主键
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(stockGoodsPutService.removeByIds(ids));
    }
}
