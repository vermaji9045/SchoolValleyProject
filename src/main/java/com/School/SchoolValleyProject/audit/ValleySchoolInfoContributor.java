package com.School.SchoolValleyProject.audit;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ValleySchoolInfoContributor implements InfoContributor {



    @Override
    public void contribute(Info.Builder builder) {
        Map<String, String> Map = new HashMap<String, String>();
        Map.put("App Name", "ValleySchool");
        Map.put("App Description", "Valley School Web Application for Students and Admin");
        Map.put("App Version", "1.0.0");
        Map.put("Contact Email", "info@valleyschool.com");
        Map.put("Contact Mobile", "+91-8410172908");
        builder.withDetail("valleyschool-info", Map);
    }
}
