package cn.linguy.service;

import cn.linguy.domain.SysLog;

import java.util.List;

public interface SysLogService {

    void save(SysLog sysLog);

    List<SysLog> findAll(int page, int size);
}
