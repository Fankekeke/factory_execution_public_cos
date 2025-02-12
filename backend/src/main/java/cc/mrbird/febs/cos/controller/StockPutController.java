package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.StockPut;
import cc.mrbird.febs.cos.service.IStockPutService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author FanK fan1ke2ke@gmail.com
 */
@RestController
@RequestMapping("/cos/stock-put")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StockPutController {

    private final IStockPutService stockPutService;

    /**
     * 分页获取入库记录
     * @param page
     * @param stockPut
     * @return
     */
    @GetMapping("/page")
    public R page(Page page, StockPut stockPut) {
        return R.ok(stockPutService.stockPutByPage(page, stockPut));
    }

    /**
     * 获取库房入库记录
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(stockPutService.list());
    }

    /**
     * 删除入库记录
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(stockPutService.removeByIds(ids));
    }

}
