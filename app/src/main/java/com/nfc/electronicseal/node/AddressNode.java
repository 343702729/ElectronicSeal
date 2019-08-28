package com.nfc.electronicseal.node;

import java.util.ArrayList;
import java.util.List;

public class AddressNode {
    private int id;
    private int pid;
    private int level;
    private String firstWord;
    private String name;
    private List<AddressNode> childNodes = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getFirstWord() {
        return firstWord;
    }

    public void setFirstWord(String firstWord) {
        this.firstWord = firstWord;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AddressNode> getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(List<AddressNode> childNodes) {
        this.childNodes = childNodes;
    }
}
