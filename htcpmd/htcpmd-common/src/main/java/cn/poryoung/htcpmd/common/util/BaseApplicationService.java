package cn.poryoung.htcpmd.common.util;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.common.core.constant.HttpStatus;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.page.TableDataInfo;
import org.springframework.data.domain.Page;

import java.util.stream.Collectors;

public class BaseApplicationService extends BaseController {
    protected TableDataInfo getDataTable(Page<?> page) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setRows(page.getContent());
        rspData.setTotal(page.getTotalElements());
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("查询成功");

        return rspData;
    }

    protected TableDataInfo getDataTable(Page<?> page, Class<?> targetType) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setRows(page.getContent().stream().map(o -> BeanUtil.copyProperties(o, targetType)).collect(Collectors.toList()));
        rspData.setTotal(page.getTotalElements());
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("查询成功");

        return rspData;
    }
}
