package com.example.user.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/api/colormatch")
public class ColorMatchingController {


    @Value("${prop.upload-folder}")
    private String UPLOAD_FOLDER;

    @Value("${prop.material_folder_prefix}")
    private String MATERIAL_FOLDER_PREFIX;

    @Value("${prop.server_prefix}")
    private String SERVER_PREFIX;

    @Value("${python.colormatching}")
    private String file;

    @RequestMapping("/")
    @ResponseBody
    public String colorMatching(String uname, String pid1, String pid2,
                                @RequestParam(value = "k", required = false, defaultValue = "8")Integer k,
                                @RequestParam(value = "mode", required = false, defaultValue = "match")String mode) {
        String savePath = UPLOAD_FOLDER;
        String thumbnailSavePath = savePath  + uname + "/thumbnail/";
        String pictureSavePath = savePath + uname + "/picture/";
        File thumbnailSavePathFile = new File(thumbnailSavePath);
        File pictureSavePathFile = new File(pictureSavePath);
        if (!thumbnailSavePathFile.exists()) {
            thumbnailSavePathFile.mkdirs();
        }
        if (!pictureSavePathFile.exists()) {
            pictureSavePathFile.mkdirs();
        }

        String reference = MATERIAL_FOLDER_PREFIX+pid2.substring(SERVER_PREFIX.length());
        String source = MATERIAL_FOLDER_PREFIX+pid1.substring(SERVER_PREFIX.length());
        //System.out.println("source="+source+";reference="+reference);
        String[] args = new String[] {"/usr/local/bin/python3", file, reference, source, pictureSavePath,
                String.valueOf(k), mode};
        try {
            Process process = Runtime.getRuntime().exec(args);
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            int re = process.waitFor();
            in.close();
            if (re == 1) {
                //System.out.println("调用脚本失败");
                return "fail";
            }
            return "success";
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "fail";
    }
}