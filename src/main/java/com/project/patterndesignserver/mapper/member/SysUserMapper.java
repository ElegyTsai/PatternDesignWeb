package com.project.patterndesignserver.mapper.member;

import com.project.patterndesignserver.model.member.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysUserMapper {
    SysUser selectSysUserById(SysUser sysUser);

    List<SysUser> selectAllSysUserByUserId(long id);

    List<SysUser> selectAllSysUserByRoleId(long id);

    int addSysUser(SysUser sysUser);

    int deleteSysUser(SysUser sysUser);

}
