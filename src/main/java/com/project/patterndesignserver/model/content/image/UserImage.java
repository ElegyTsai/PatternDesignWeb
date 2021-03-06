package com.project.patterndesignserver.model.content.image;

import lombok.Data;

import java.util.List;

/*
public class ImageBase {
    private long id;
    private String imageName;
    private String thumbnailPath;
    private String imagePath;
    private String MD5;
    private boolean available;

    @Override
    public String toString(){
        return "id: "+id+", imageName: "+imageName;
    }
}
 */
@Data
public class UserImage extends ImageBase{
    private long userId;
    private String UUID;
    private String myFileName;
    private String myGroup;
}
