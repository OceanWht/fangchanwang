package com.wht.springcloud.fangchanwang.page;

import com.google.common.collect.Lists;

import java.util.List;

public class Pagination {

    private int pageSize;

    private int pageNum;

    private int totalCount;

    private List<Integer> pages = Lists.newArrayList();

    public Pagination(int pageSize, int pageNum, int totalCount) {
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.totalCount = totalCount;
        for (int i = 0; i < pageNum; i++) {
            pages.add(i);
        }
        int pageCount = totalCount / pageSize + ((totalCount % pageSize == 0) ? 0 : 1);
        if (pageCount > pageNum) {
            for (int i = pageNum + 1; i <= pageCount; i++) {
                pages.add(i);
            }
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<Integer> getPages() {
        return pages;
    }

    public void setPages(List<Integer> pages) {
        this.pages = pages;
    }
}
