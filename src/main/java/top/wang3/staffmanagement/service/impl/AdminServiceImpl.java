package top.wang3.staffmanagement.service.impl;

import top.wang3.staffmanagement.mapper.AdminMapper;
import top.wang3.staffmanagement.mapper.impl.AdminMapperImpl;
import top.wang3.staffmanagement.model.Admin;
import top.wang3.staffmanagement.service.AdminService;

public class AdminServiceImpl implements AdminService {

    private final AdminMapper adminMapper = new AdminMapperImpl();

    @Override
    public Admin getAdminByName(String name) {
        try {
            Thread.sleep(1100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return adminMapper.selectAdminByName(name);
    }
}
