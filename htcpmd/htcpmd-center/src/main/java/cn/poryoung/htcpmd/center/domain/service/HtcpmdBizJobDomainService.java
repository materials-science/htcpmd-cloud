package cn.poryoung.htcpmd.center.domain.service;

import cn.poryoung.htcpmd.center.domain.entity.HtcpmdBizJob;

import java.util.List;

/**
 * Htcpmd业务任务 领域Service接口
 * 
 * @author PorYoung
 * @date 2022-12-13
 */
public interface HtcpmdBizJobDomainService
{
    /**
     * 查询Htcpmd业务任务
     * 
     * @param id Htcpmd业务任务主键
     * @return Htcpmd业务任务
     */
    public HtcpmdBizJob selectHtcpmdBizJobById(String id);

    /**
     * 查询Htcpmd业务任务列表
     * 
     * @param htcpmdBizJob Htcpmd业务任务
     * @return Htcpmd业务任务集合
     */
    public List<HtcpmdBizJob> selectHtcpmdBizJobList(HtcpmdBizJob htcpmdBizJob);

    /**
     * 新增Htcpmd业务任务
     * 
     * @param htcpmdBizJob Htcpmd业务任务
     * @return 结果
     */
    public int insertHtcpmdBizJob(HtcpmdBizJob htcpmdBizJob);

    /**
     * 修改Htcpmd业务任务
     * 
     * @param htcpmdBizJob Htcpmd业务任务
     * @return 结果
     */
    public int updateHtcpmdBizJob(HtcpmdBizJob htcpmdBizJob);

    /**
     * 批量删除Htcpmd业务任务
     * 
     * @param ids 需要删除的Htcpmd业务任务主键集合
     * @return 结果
     */
    public int deleteHtcpmdBizJobByIds(String[] ids);

    /**
     * 删除Htcpmd业务任务信息
     * 
     * @param id Htcpmd业务任务主键
     * @return 结果
     */
    public int deleteHtcpmdBizJobById(String id);
}
