package org.test.fbpost;

import java.util.Date;

public class Memo {
    int id;
    String title;
    String content;
    Date createDate;

    public Memo(int id, String title, String content, Date createDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}