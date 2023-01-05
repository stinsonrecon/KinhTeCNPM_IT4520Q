package vn.com.core.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude
public class PagnationRespBody<E> {

    private long numRec;

    private long totalPage;

    private Long totalRecord;

    private List<E> content;

    public PagnationRespBody() {
        super();
    }

    public PagnationRespBody<E> withContent(List<E> content) {
        this.content = content;
        return this;
    }

    public PagnationRespBody<E> withNumRec(long numRec) {
        this.numRec = numRec;
        return this;
    }

    public PagnationRespBody<E> withTotalPage(long totalPage) {
        this.totalPage = totalPage;
        return this;
    }

    public PagnationRespBody<E> withTotalRecord(long totalRecord) {
        this.totalRecord = totalRecord;
        return this;
    }
    public long getNumRec() {
        return numRec;
    }

    public void setNumRec(long numRec) {
        this.numRec = numRec;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public List<E> getContent() {
        return content;
    }

    public void setContent(List<E> content) {
        this.content = content;
    }

    public Long getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(Long totalRecord) {
        this.totalRecord = totalRecord;
    }
}
