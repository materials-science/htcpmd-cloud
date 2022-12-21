package cn.poryoung.htcpmd.center.domain.repository;

import java.util.List;
import cn.poryoung.htcpmd.center.domain.entity.HtcpmdBizJob;

/**
 * Htcpmd业务任务 Repository接口
 * 
 * @author PorYoung
 * @date 2022-12-13
 */
public interface HtcpmdBizJobRepository
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
     * 删除Htcpmd业务任务
     * 
     * @param id Htcpmd业务任务主键
     * @return 结果
     */
    public int deleteHtcpmdBizJobById(String id);

    /**
     * 批量删除Htcpmd业务任务
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHtcpmdBizJobByIds(String[] ids);
}
