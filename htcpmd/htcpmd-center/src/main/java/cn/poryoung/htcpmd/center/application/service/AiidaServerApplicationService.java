package cn.poryoung.htcpmd.center.application.service;

import cn.hutool.core.bean.BeanUtil;
import cn.poryoung.htcpmd.center.application.dto.AiidaServerLessRespDto;
import cn.poryoung.htcpmd.center.domain.entity.aiida.AiidaServerDoc;
import cn.poryoung.htcpmd.center.domain.service.AiidaServerDocDomainService;
import cn.poryoung.htcpmd.common.pojo.PageSupport;
import cn.poryoung.htcpmd.common.util.BaseApplicationService;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.common.security.annotation.RequiresRoles;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "AiidaServerApplicationService", description = "Aiida Server对象管理")
@RestController
@RequestMapping("/server")
public class AiidaServerApplicationService extends BaseApplicationService {
    @Autowired
    private AiidaServerDocDomainService aiidaServerDocDomainService;

    /**
     * 查询aiidaServer信息列表
     */
    @RequiresPermissions("htcpmd-center:aiidaServer:search")
    @GetMapping("/search")
    public TableDataInfo search(@RequestParam("label") String label) {
        Page<AiidaServerDoc> aiidaServerDocPage = aiidaServerDocDomainService.searchByLabel(label);
        return getDataTable(aiidaServerDocPage, AiidaServerLessRespDto.class);
    }


    /**
     * 查询aiidaServer信息列表
     */
    @RequiresPermissions("htcpmd-center:aiidaServer:list")
    @GetMapping("/list")
    public TableDataInfo list() {
        Page<AiidaServerDoc> aiidaServerDocPage = aiidaServerDocDomainService.findAll(PageSupport.getPageableBeginWith0());
        return getDataTable(aiidaServerDocPage, AiidaServerLessRespDto.class);
    }

    /**
     * 导出aiidaServer信息列表
     */
    @RequiresPermissions("htcpmd-center:aiidaServer:admin:export")
    @RequiresRoles("admin")
    @Log(title = "aiidaServer信息", businessType = BusinessType.EXPORT)
    @PostMapping("/admin/export")
    public void export(HttpServletResponse response, AiidaServerDoc aiidaServerDoc) {
        List<AiidaServerDoc> list = aiidaServerDocDomainService.findAll();
        ExcelUtil<AiidaServerLessRespDto> util = new ExcelUtil<AiidaServerLessRespDto>(AiidaServerLessRespDto.class);
        util.exportExcel(response, list.stream().map(o -> BeanUtil.copyProperties(o, AiidaServerLessRespDto.class)).collect(Collectors.toList()), "aiidaServer信息数据");
    }

    /**
     * 获取aiidaServer信息详细信息
     */
    @RequiresPermissions("htcpmd-center:aiidaServer:query")
    @GetMapping(value = "/{label}")
    public AjaxResult getInfo(@PathVariable("label") String label) {
        return success(BeanUtil.copyProperties(aiidaServerDocDomainService.findServerDocByLabel(label), AiidaServerLessRespDto.class));
    }
}
