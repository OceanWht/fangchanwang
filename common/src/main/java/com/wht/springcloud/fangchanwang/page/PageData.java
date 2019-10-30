package com.wht.springcloud.fangchanwang.page;

import java.util.List;

public class PageData<T> {

    private List<T> list;

    private Pagination pagination;

    public PageData(List<T> list, Pagination pagination) {
        this.list = list;
        this.pagination = pagination;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public static <T> PageData<T> buildPage(List<T> list,Integer pageSize,Integer pageNum,Integer count){
        Pagination pagination = new Pagination(pageSize,pageNum,count);
        return new PageData<>(list,pagination);
    }
}
