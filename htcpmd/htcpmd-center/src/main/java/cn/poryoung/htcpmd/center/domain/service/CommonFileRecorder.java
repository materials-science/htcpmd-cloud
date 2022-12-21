package cn.poryoung.htcpmd.center.domain.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.poryoung.htcpmd.center.domain.entity.CommonFile;
import cn.poryoung.htcpmd.center.domain.repository.CommonFileRepository;
import cn.xuyanwu.spring.file.storage.FileInfo;
import cn.xuyanwu.spring.file.storage.recorder.FileRecorder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonFileRecorder implements FileRecorder {
    @Autowired
    private CommonFileRepository commonFileRepository;

    public FileInfo getById(String id) {
        CommonFile commonFile = commonFileRepository.selectCommonFileById(id);
        FileInfo fileInfo = BeanUtil.copyProperties(commonFile, FileInfo.class, "attr");
        if (StrUtil.isNotBlank(commonFile.getAttr())) {
            fileInfo.setAttr(JSONUtil.toBean(commonFile.getAttr(), Dict.class));
        }

        return fileInfo;

    }

    public boolean deleteById(String id) {
        int ret = commonFileRepository.deleteCommonFileById(id);
        return ret == 1;
    }

    @Override
    public boolean record(FileInfo fileInfo) {
        CommonFile commonFile = BeanUtil.copyProperties(fileInfo, CommonFile.class, "attr");
        if (fileInfo.getAttr() != null) {
            commonFile.setAttr(JSONUtil.toJsonStr(fileInfo.getAttr()));
        }
        int ret = commonFileRepository.insertCommonFile(commonFile);

        return ret == 1;
    }

    @Override
    public FileInfo getByUrl(String url) {
        CommonFile commonFile = commonFileRepository.selectCommonFileByUrl(url);
        FileInfo fileInfo = BeanUtil.copyProperties(commonFile, FileInfo.class, "attr");
        if (StrUtil.isNotBlank(commonFile.getAttr())) {
            fileInfo.setAttr(JSONUtil.toBean(commonFile.getAttr(), Dict.class));
        }

        return fileInfo;

    }

    @Override
    public boolean delete(String url) {
        commonFileRepository.deleteCommonFileByUrl(url);
        return false;
    }
}
