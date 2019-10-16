package cn.linguy.service.impl;

import cn.linguy.dao.SysLogDao;
import cn.linguy.domain.SysLog;
import cn.linguy.service.SysLogService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    private SysLogDao sysLogDao;

    @Override
    public void save(SysLog sysLog) {
        sysLogDao.save(sysLog);
    }

    @Override
    public List<SysLog> findAll(int page, int size) {
        PageHelper.startPage(page, size);
        return sysLogDao.findAll();
    }

}

