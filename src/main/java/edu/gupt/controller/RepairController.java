package edu.gupt.controller;

import edu.gupt.result.Result;
import edu.gupt.service.EquipmentRepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/repair")
public class RepairController {

    @Autowired
    private EquipmentRepairService repairService;

/*    @GetMapping("/orders")
    public Result<PageResult<RepairOrderVO>> getRepairOrders(
        @RequestParam(defaultValue = "1") Integer page,
        @RequestParam(defaultValue = "5") Integer pageSize)
    {
        PageResult<RepairOrderVO> result = repairService.getOrders(page, pageSize);
        return Result.success(result);
    }*/
}