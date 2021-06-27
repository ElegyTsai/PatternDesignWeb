package com.project.patterndesignserver.service.colorMatch;

import com.project.patterndesignserver.util.result.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
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


    @Override
    public byte[] MatchOne(String sourceUrl, String referenceUrl, Integer k, String mode) {
        String savePath = UPLOAD_FOLDER + "out";
        String thumbnailSavePath = savePath + separator + "thumbnail";
        String pictureSavePath = savePath + separator + "picture";
        String fileName = UUID.randomUUID().toString().replace("-", "") + ".jpg";
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
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            int re = process.waitFor();
            in.close();
            if (re == 1) {
                //System.out.println("调用脚本失败");
                return null;
//                return new Result<>(ExceptionMsg.FAIL);
            }
            Result<byte[]> res = new Result<>();
            File file = new File(pictureSavePath + separator + fileName);
            FileInputStream inputStream = new FileInputStream(file);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, inputStream.available());
            res.setData(bytes);
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
