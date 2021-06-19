package com.project.patterndesignserver.service.image;

import com.project.patterndesignserver.util.result.Result;

import java.util.List;

public interface LatestUsedMaterialsService {
    public Result<String> add(String url);

    public Result<List> query(int rank);
}
