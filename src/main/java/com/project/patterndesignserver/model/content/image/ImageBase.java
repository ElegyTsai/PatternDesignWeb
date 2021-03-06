package com.project.patterndesignserver.model.content.image;

import lombok.Data;

@Data
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
