package top.wang3.staffmanagement.mapper;

import top.wang3.staffmanagement.model.Admin;

public interface AdminMapper {
    Admin selectAdminByName(String name);
}
