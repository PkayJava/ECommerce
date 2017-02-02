package com.angkorteam.platform.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class PlatformFile implements Serializable {

    private Long fileId;
    private String name;
    private String label;
    private String path;
    private String mime;
    private String extension;
    private Integer length;
    private Integer version;

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlatformFile file = (PlatformFile) o;

        if (fileId != null ? !fileId.equals(file.fileId) : file.fileId != null) return false;
        if (name != null ? !name.equals(file.name) : file.name != null) return false;
        if (label != null ? !label.equals(file.label) : file.label != null) return false;
        if (path != null ? !path.equals(file.path) : file.path != null) return false;
        if (mime != null ? !mime.equals(file.mime) : file.mime != null) return false;
        if (extension != null ? !extension.equals(file.extension) : file.extension != null) return false;
        if (length != null ? !length.equals(file.length) : file.length != null) return false;
        return version != null ? version.equals(file.version) : file.version == null;
    }

    @Override
    public int hashCode() {
        int result = fileId != null ? fileId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (label != null ? label.hashCode() : 0);
        result = 31 * result + (path != null ? path.hashCode() : 0);
        result = 31 * result + (mime != null ? mime.hashCode() : 0);
        result = 31 * result + (extension != null ? extension.hashCode() : 0);
        result = 31 * result + (length != null ? length.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }
}
