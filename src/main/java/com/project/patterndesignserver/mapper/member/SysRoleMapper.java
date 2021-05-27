package com.project.patterndesignserver.mapper.member;

import com.project.patterndesignserver.model.member.SysRole;
import com.project.patterndesignserver.model.member.UserPermission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMapper {

    SysRole selectSysRoleById(SysRole sysRole);

    List<SysRole> selectSysRoleByRoleId(long roleId);

    List<SysRole> selectSysRoleByPermissionId(long permissionId);

    int addSysRole(SysRole sysRole);

    int deleteSysRole(long id);
}
