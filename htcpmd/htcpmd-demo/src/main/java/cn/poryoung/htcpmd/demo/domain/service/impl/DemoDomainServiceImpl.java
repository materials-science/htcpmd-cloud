package cn.poryoung.htcpmd.demo.domain.service.impl;

import cn.poryoung.htcpmd.demo.domain.entity.DemoDo;
import cn.poryoung.htcpmd.demo.domain.service.DemoDomainService;
import org.springframework.stereotype.Service;

@Service
public class DemoDomainServiceImpl implements DemoDomainService {
    @Override
    public DemoDo getDemoInfo(String id) {
        DemoDo demoDo = new DemoDo();
        demoDo.setId(id);
        demoDo.setDemoName("demo");
        demoDo.setSecret("secret");

        return demoDo;
    }
}
