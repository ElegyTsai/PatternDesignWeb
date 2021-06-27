package com.project.patterndesignserver.service.colorMatch;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ColorMatchingService {

    public String MatchOne(String sourceUrl, String referenceUrl, Integer k, String mode);

    public List<String> MatchAll(List<String> sourceUrls, List<String> referenceUrls, Integer k, String mode);
}
