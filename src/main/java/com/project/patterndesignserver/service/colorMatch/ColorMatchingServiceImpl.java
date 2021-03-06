package com.project.patterndesignserver.service.colorMatch;

import com.project.patterndesignserver.util.result.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ColorMatchingServiceImpl implements ColorMatchingService{


    @Value("${serverResource.path}")
    private String UPLOAD_FOLDER;
    @Value("${image.separator}")
    private String separator;

    @Value("${prop.material_folder_prefix}")
    private String MATERIAL_FOLDER_PREFIX;

    @Value("${prop.server_prefix}")
    private String SERVER_PREFIX;

    @Value("${python.colormatching}")
    private String file;

    @Value("${python.runner}")
    private String python;
    @Value("${serverResource.url}")
    private String urlprefix;

    @Override
    public String MatchOne(String sourceUrl, String referenceUrl, Integer k, String mode) {
        String savePath = UPLOAD_FOLDER + "out";
        String thumbnailSavePath = savePath + separator + "thumbnail";
        String pictureSavePath = savePath + separator + "picture";
        String fileName = UUID.randomUUID().toString().replace("-", "") + ".png";
        File thumbnailSavePathFile = new File(thumbnailSavePath);
        File pictureSavePathFile = new File(pictureSavePath);
        if (!thumbnailSavePathFile.exists()) {
            thumbnailSavePathFile.mkdirs();
        }
        if (!pictureSavePathFile.exists()) {
            pictureSavePathFile.mkdirs();
        }

        String reference = MATERIAL_FOLDER_PREFIX + referenceUrl.substring(SERVER_PREFIX.length());
        reference = reference.replace("/", separator);
        String source = MATERIAL_FOLDER_PREFIX + sourceUrl.substring(SERVER_PREFIX.length());
        source = source.replace("/", separator);
        //System.out.println("source="+source+";reference="+reference);
        String[] args = new String[]{python, file, reference, source, pictureSavePath, fileName,
                String.valueOf(k), mode};
        try {
            for (String s : args) {
                System.out.println(s);
            }

            Process process = Runtime.getRuntime().exec(args);
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            int re = process.waitFor();
//            while ((line = in.readLine()) != null) {
//                System.out.println(line);
//            }
            in.close();
            if (re == 1) {
                //System.out.println("??????????????????");
                return null;
//                return new Result<>(ExceptionMsg.FAIL);
            }
            String url = urlprefix+"out/picture/"+fileName;
//            Result<byte[]> res = new Result<>();
//            File file = new File(pictureSavePath + separator + fileName);
//            FileInputStream inputStream = new FileInputStream(file);
//            byte[] bytes = new byte[inputStream.available()];
//            inputStream.read(bytes, 0, inputStream.available());
//            res.setData(bytes);
            System.out.println(url);
            return url;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<String> MatchAll(List<String> sourceUrls, List<String> referenceUrls, Integer k, String mode) {
        List<String> res = new ArrayList<>();
        try {
            for (int i = 0; i < sourceUrls.size(); i++) {
                res.add(MatchOne(sourceUrls.get(i),referenceUrls.get(i),k,mode));
            }
            return res;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
