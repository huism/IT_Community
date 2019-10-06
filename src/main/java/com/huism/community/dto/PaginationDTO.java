package com.huism.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {

    private List<QuestionDTO> questions;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showLastPage;

    private Integer currentPage;
    private Integer totalPage;
    private List<Integer> pages = new ArrayList<>();

    public void setPagination(Integer totalCount, Integer page, Integer size) {

        // 总页数
        totalPage = (totalCount%size == 0)?(totalCount/size):(totalCount/size+1);
        // 防止页数溢出
        if(page < 1){
            page=1;
        }else if(page > totalPage){
            page=totalPage;
        }
        this.currentPage = page;

        pages.add(page);
        for(int i =1;i<=3;i++){
            if(page - i > 0 ){
                pages.add(0,page-i);
            }
            if(page +i <= totalPage){
                pages.add(page+i);
            }
        }

        // 是否展示上一页
        showPrevious = page != 1;
        // 是否展示下一页
        showNext = !page.equals(totalPage);
        // 是否展示第一页
        showFirstPage = !pages.contains(1);
        // 是否展示最后一页
        showLastPage = !pages.contains(totalPage);
    }
}
