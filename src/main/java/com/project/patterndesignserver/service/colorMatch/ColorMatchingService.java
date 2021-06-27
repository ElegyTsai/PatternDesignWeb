package com.project.patterndesignserver.service.colorMatch;

import org.springframework.web.bind.annotation.RequestParam;

public interface ColorMatchingService {

    public byte [] MatchOne(String sourceUrl, String referenceUrl, Integer k, String mode);
}
