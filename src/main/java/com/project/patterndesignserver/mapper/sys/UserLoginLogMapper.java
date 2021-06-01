package com.project.patterndesignserver.mapper.sys;

import com.project.patterndesignserver.model.sys.UserLoginLog;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface UserLoginLogMapper {

    int saveLog(UserLoginLog userLoginLog);

    List<UserLoginLog> queryLogByUsername(String username);
}
