package com.project.patterndesignserver.module.pool;

import com.google.gson.JsonObject;
import com.project.patterndesignserver.util.ImageUtil;
import com.project.patterndesignserver.util.JsonStringUtil;
import com.project.patterndesignserver.util.UUIDUtil;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MattingTask {
    private String fileName;
    private long lastUpdateTime;
    private long createTime;
    private long sessionId;
    private String status;
    private int operationCount;



    private List<List<Integer>> clicks = new ArrayList<>();
    private byte[] cacheImage;
    private String operation;

    @Override
    public boolean equals(Object mattingTask2){
        if(mattingTask2 instanceof MattingTask)
        return this.sessionId == (((MattingTask) mattingTask2).getSessionId());
        else return false;
    }
    @Override
    public int hashCode(){
        return (int)sessionId > 0? (int)sessionId:-(int)sessionId;
    }

    public byte[] getCacheImage() {
        return cacheImage;
    }
    public void setCacheImage(byte[] cacheImage){
        this.cacheImage = cacheImage;
    }
    public void clearCacheImage(){
        this.cacheImage = null;
    }

    public MattingTask(MultipartFile file, String path) throws IOException,Exception{
        this.fileName = ImageUtil.saveWithRandomName(file, path);
        this.createTime = System.currentTimeMillis();
        updateTimeNow();
        sessionId = UUIDUtil.getUUIDOfNumber();
        setStatusAsActive();
    }
    public MattingTask(Long sessionId) throws IOException,Exception{
        this.sessionId = sessionId;
        this.createTime = System.currentTimeMillis();
        updateTimeNow();
        setStatusAsActive();
    }
    public MattingTask() throws IOException,Exception{
        this.createTime = System.currentTimeMillis();
        updateTimeNow();
        setStatusAsActive();
    }

    public void updateTimeNow(){
        this.lastUpdateTime=System.currentTimeMillis();
    }
    public void addClicks(int x,int y, boolean mouse){
        List<Integer> click = new ArrayList<>();
        click.add(x);
        click.add(y);
        click.add(mouse? 1:0);
        clicks.add(click);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("operation","addClicks");
        jsonObject.addProperty("x",x);
        jsonObject.addProperty("y",y);
        jsonObject.addProperty("mouse",mouse);
        this.operation = jsonObject.toString();
        operationCount++;
        setStatusAsRunning();
    }
    public boolean undo(){
        if(clicks.isEmpty()) return false;
        clicks.remove(clicks.size()-1);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("operation","undo");
        this.operation = jsonObject.toString();
        operationCount++;
        setStatusAsRunning();
        return true;
    }
    public boolean mask(){
        if(clicks.isEmpty()) return false;
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("operation","getMask");
        this.operation = jsonObject.toString();
        operationCount++;
        setStatusAsRunning();
        return true;
    }
    public boolean taskInitialize(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("operation","initialize");
//        jsonObject.addProperty("clicks",JsonStringUtil.getJsonFromArray(List<>));
        this.operation = jsonObject.toString();
        setStatusAsActive();
        return true;
    }
    public boolean taskFinalize(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("operation","finalize");
        this.operation = jsonObject.toString();
        setStatusAsPending();
        return true;
    }
    public boolean taskRedo(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("operation","redo");
        this.operation = jsonObject.toString();
        setStatusAsActive();
        return true;
    }

    //四种状态，一级池内待命 active 正在运行 running 缓冲数据待读取 waiting  二级池内待删除 pending
    public void setStatusAsActive(){ this.status = "active"; }
    public void setStatusAsError(){ this.status = "error"; }
    public void setStatusAsWaiting(){
        this.status = "waiting";
    }
    public void setStatusAsRunning(){
        this.status = "running";
    }
    public void setStatusAsPending(){
        this.status = "pending";
    }
    public boolean taskIsWaiting(){
        return this.status.equals("waiting");
    }
    public boolean taskIsActive(){
        return this.status.equals("active");
    }
    public boolean taskIsRunning(){
        return this.status.equals("running");
    }
    public boolean taskIsPending(){
        return this.status.equals("pending");
    }
    public boolean taskIsError(){ return this.status.equals("error"); }

    public String getFileName() {
        return fileName;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public long getSessionId() {
        return sessionId;
    }

    public String getStatus() {
        return status;
    }
    public String serialized(){
        String s = JsonStringUtil.<MattingTask>toJsonString(this);
        return s;
    }
    public List<List<Integer>> getClicks() {
        return clicks;
    }

    public String getOperation() {
        return operation;
    }
    public static MattingTask deserialized(String s){
        return JsonStringUtil.getObjectFromJson(s,MattingTask.class);
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setClicks(List<List<Integer>> clicks) {
        this.clicks = clicks;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public long getCreateTime() {
        return createTime;
    }

    public int getOperationCount() {
        return operationCount;
    }

    public void setOperationCount(int operationCount) {
        this.operationCount = operationCount;
    }
}
