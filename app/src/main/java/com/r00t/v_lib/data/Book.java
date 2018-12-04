package com.r00t.v_lib.data;

import java.io.Serializable;

public class Book implements Serializable {
    private String bib_key,preview,thumbnail_url,preview_url,info_url;

    public Book(String bib_key, String preview, String thumbnail_url, String preview_url,String info_url) {
        this.bib_key = bib_key;
        this.preview = preview;
        this.thumbnail_url = thumbnail_url;
        this.preview_url = preview_url;
        this.info_url = info_url;
    }

    public String getBib_key() {
        return bib_key;
    }

    public void setBib_key(String bib_key) {
        this.bib_key = bib_key;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }

    public String getPreview_url() {
        return preview_url;
    }

    public void setPreview_url(String preview_url) {
        this.preview_url = preview_url;
    }

    public String getInfo_url() {
        return info_url;
    }

    public void setInfo_url(String info_url) {
        this.info_url = info_url;
    }
    public String toString(){
        return bib_key + preview + thumbnail_url + preview_url + info_url;
    }
}
