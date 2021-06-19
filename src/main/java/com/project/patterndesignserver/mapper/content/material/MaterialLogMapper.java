package com.project.patterndesignserver.mapper.content.material;

import com.project.patterndesignserver.model.content.material.MaterialLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MaterialLogMapper {

    int insertLog(MaterialLog log);

    int deleteByUserId(long id);

    List<MaterialLog>  queryByUserId(long id);
}
