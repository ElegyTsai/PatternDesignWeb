package com.project.patterndesignserver.model.content.material;

import lombok.Data;

@Data
public class MaterialLog {
    private long logId;
    private long user_Id;
    private String url;
    private long timeOfLastUsing;
}
