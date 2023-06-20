package com.class113.finalsh;

public class WorkInfo {
    private String workName;
    private String workDescription;

    public WorkInfo(){

    }
    public WorkInfo(String workName, String workDescription) {
        this.workName = workName;
        this.workDescription = workDescription;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getWorkDescription() {
        return workDescription;
    }

    public void setWorkDescription(String workDescription) {
        this.workDescription = workDescription;
    }

    @Override
    public String toString() {
        return "WorkInfo{" +
                "workName='" + workName + '\'' +
                ", workDescription='" + workDescription + '\'' +
                '}';
    }
}
