package org.example.ecommercewebsite.utils;

public class PaginationHelper {
    private  int currentPage;
    private  long totalPages;
    private  int limitItem;
    private  int skip;

    public  PaginationHelper (int currentPage, int limitItem, long totalItem)      {
        this.currentPage = currentPage;
        this.limitItem = limitItem;
        this.skip = (currentPage-1) * limitItem;
        this.totalPages = (long) Math.ceil((double) totalItem /limitItem);
    }

    public int getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public long getTotalPages() {
        return totalPages;
    }
    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }
    public int getLimitItem() {
        return limitItem;
    }
    public void setLimitItem(int limitItem) {
        this.limitItem = limitItem;
    }

    public int getSkip() {
        return skip;
    }
    public void setSkip(int skip) {
        this.skip = skip;
    }


}

