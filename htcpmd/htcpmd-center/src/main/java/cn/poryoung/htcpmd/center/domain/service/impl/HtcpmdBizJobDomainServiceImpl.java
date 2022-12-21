package cn.poryoung.htcpmd.center.domain.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.poryoung.htcpmd.center.domain.repository.HtcpmdBizJobRepository;
import cn.poryoung.htcpmd.center.domain.entity.HtcpmdBizJob;
import cn.poryoung.htcpmd.center.domain.service.HtcpmdBizJobDomainService;

/**
 * Htcpmd业务任务 领域Service业务层实现
 * 
 * @author PorYoung
 * @date 2022-12-13
 */
@Service
public class HtcpmdBizJobDomainServiceImpl implements HtcpmdBizJobDomainService
{
    @Autowired
    private HtcpmdBizJobRepository htcpmdBizJobRepository;

    /**
     * 查询Htcpmd业务任务
     * 
     * @param id Htcpmd业务任务主键
     * @return Htcpmd业务任务
     */
    @Override
    public HtcpmdBizJob selectHtcpmdBizJobById(String id)
    {
        return htcpmdBizJobRepository.selectHtcpmdBizJobById(id);
    }

    /**
     * 查询Htcpmd业务任务列表
     * 
     * @param htcpmdBizJob Htcpmd业务任务
     * @return Htcpmd业务任务
     */
    @Override
    public List<HtcpmdBizJob> selectHtcpmdBizJobList(HtcpmdBizJob htcpmdBizJob)
    {
        return htcpmdBizJobRepository.selectHtcpmdBizJobList(htcpmdBizJob);
    }

    /**
     * 新增Htcpmd业务任务
     * 
     * @param htcpmdBizJob Htcpmd业务任务
     * @return 结果
     */
    @Override
    public int insertHtcpmdBizJob(HtcpmdBizJob htcpmdBizJob)
    {
        htcpmdBizJob.setCreateTime(DateUtils.getNowDate());
        return htcpmdBizJobRepository.insertHtcpmdBizJob(htcpmdBizJob);
    }

    /**
     * 修改Htcpmd业务任务
     * 
     * @param htcpmdBizJob Htcpmd业务任务
     * @return 结果
     */
    @Override
    public int updateHtcpmdBizJob(HtcpmdBizJob htcpmdBizJob)
    {
        htcpmdBizJob.setUpdateTime(DateUtils.getNowDate());
        return htcpmdBizJobRepository.updateHtcpmdBizJob(htcpmdBizJob);
    }

    /**
     * 批量删除Htcpmd业务任务
     * 
     * @param ids 需要删除的Htcpmd业务任务主键
     * @return 结果
     */
    @Override
    public int deleteHtcpmdBizJobByIds(String[] ids)
    {
        return htcpmdBizJobRepository.deleteHtcpmdBizJobByIds(ids);
    }

    /**
     * 删除Htcpmd业务任务信息
     * 
     * @param id Htcpmd业务任务主键
     * @return 结果
     */
    @Override
    public int deleteHtcpmdBizJobById(String id)
    {
        return htcpmdBizJobRepository.deleteHtcpmdBizJobById(id);
    }
}
