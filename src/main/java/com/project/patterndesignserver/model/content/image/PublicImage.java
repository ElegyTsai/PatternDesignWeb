package com.project.patterndesignserver.model.content.image;

import lombok.Data;

import java.util.ArrayList;
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

public class PublicImage extends ImageBase{

    private String permission;
    private String tag;
    private List<String> tags = new ArrayList<>();

    public List<String> getTags(){
        String [] tagsString = tag.split("\\|");
        for(String t : tagsString){
            tags.add(t);
        }
        return tags;
    }
    public void setTagByTagsList(List<String> tags){
        StringBuilder sb = new StringBuilder();
        if(tags.isEmpty()){
            this.tag="";
            return;
        }
        for(String s : tags){
            sb.append('|');
            sb.append(s);
        }
        this.tag=sb.substring(1).toString();
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString(){
        return "id: "+getId()+", imageName: "+getImageName()+"";
    }
}

