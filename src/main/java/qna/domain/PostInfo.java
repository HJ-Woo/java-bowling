package qna.domain;

import javax.persistence.*;

@Embeddable
public class PostInfo {
    @Lob
    private String contents;

    @ManyToOne
    private User writer;

    private boolean deleted = false;

    public PostInfo() {

    }

    public PostInfo(String contents) {
        this.contents = contents;
    }

    public PostInfo(String contents, User writer) {
        this.contents = contents;
        this.writer = writer;
    }

    public boolean isOwner(User loginUser) {
        return writer.equals(loginUser);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void delete() {
        deleted = true;
    }

    public void writeBy(User writer) {
        this.writer = writer;
    }

    public User getWriter() {
        return writer;
    }

    @Override
    public String toString() {
        return "PostInfo{" +
                "contents='" + contents + '\'' +
                ", writer=" + writer +
                ", deleted=" + deleted +
                '}';
    }

}
