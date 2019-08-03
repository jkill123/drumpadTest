package com.domain;

import javax.persistence.*;

@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String logo;
    private String fileOriginalName;

    private String fileDataBaseName;

    public Message() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getFileOriginalName() {
        return fileOriginalName;
    }

    public void setFileOriginalName(String fileOriginalName) {
        this.fileOriginalName = fileOriginalName;
    }

    public String getFileDataBaseName() {
        return fileDataBaseName;
    }

    public void setFileDataBaseName(String fileDataBaseName) {
        this.fileDataBaseName = fileDataBaseName;
    }
}
