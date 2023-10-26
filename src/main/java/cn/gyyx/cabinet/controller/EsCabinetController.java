package cn.gyyx.cabinet.controller;


import cn.gyyx.cabinet.domain.EsCabinet;
import cn.gyyx.cabinet.pojo.CommonResult;
import cn.gyyx.cabinet.service.EsCabinetService;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Api(tags = "EsCabinetController")
@Tag(name = "EsCabinetController",description = "搜索电池管理")
@RequestMapping("/esCabinet")
public class EsCabinetController {
    private EsCabinetService esCabinetService;

    public EsCabinetController(EsCabinetService esCabinetService) {
        this.esCabinetService = esCabinetService;
    }

    @ApiOperation(value = "导入所有数据库中商品到ES")
    @RequestMapping(value = "/importAll", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Integer> importAllList() {
        int count = esCabinetService.importAll();
        return CommonResult.success(count);
    }

    @ApiOperation(value = "根据id删除商品")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Object> delete(@PathVariable Long id) {
        esCabinetService.delete(id);
        return CommonResult.success(null);
    }

    @ApiOperation(value = "根据id批量删除商品")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Object> delete(@RequestParam("ids") List<Long> ids) {
        esCabinetService.delete(ids);
        return CommonResult.success(null);
    }

    @ApiOperation(value = "根据id创建商品")
    @RequestMapping(value = "/create/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<EsCabinet> create(@PathVariable Long id) {
        EsCabinet esCabinet = esCabinetService.create(id);
        if (esCabinet != null) {
            return CommonResult.success(esCabinet);
        } else {
            return CommonResult.failed();
        }
    }

}
