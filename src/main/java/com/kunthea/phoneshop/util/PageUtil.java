package com.kunthea.phoneshop.util;

import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;


public interface PageUtil {
    int DEFAULT_PAGE_LIMIT = 2;
    int DEFAULT_PAGE_OFFSET = 1;
    String PAGE_NUMBER = "_page";
    String PAGE_LIMIT = "_limit";

    static Pageable getPageable(int pagenumber, int pagesize) {
        if(pagenumber < DEFAULT_PAGE_OFFSET){
            pagenumber = DEFAULT_PAGE_OFFSET;
        }
        if(pagesize < 1){
            pagesize = DEFAULT_PAGE_LIMIT;
        }
        Pageable pageable = PageRequest.of(pagenumber-1, pagesize);
        return pageable;
    }
}
