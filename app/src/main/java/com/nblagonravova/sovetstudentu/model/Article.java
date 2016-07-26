package com.nblagonravova.sovetstudentu.model;

public class Article {
    private String mTitle;
    private String mImagePath;
    private String mAuthor;
    private String mPublicationDate;
    private String mContentPath;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getImagePath() {
        return mImagePath;
    }

    public void setImagePath(String imagePath) {
        mImagePath = imagePath;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public String getPublicationDate() {
        return mPublicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        mPublicationDate = publicationDate;
    }

    public String getContentPath() {
        return mContentPath;
    }

    public void setContentPath(String contentPath) {
        mContentPath = contentPath;
    }
}
