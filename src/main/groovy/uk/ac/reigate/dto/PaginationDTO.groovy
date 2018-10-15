package uk.ac.reigate.dto

import uk.ac.reigate.model.PageInfo

class PaginationDTO {
    
    int page
    
    int size
    
    String sort
    
    Long totalItems
    
    Long totalPages
    
    PaginationDTO() {
        this.page = 0
        this.size = 0
    }
    
    PaginationDTO(int page, int size) {
        this.page = page
        this.size = size
    }
    
    PaginationDTO(int page, int size, String sort) {
        this.page = page
        this.size = size
        this.sort = sort
    }
    
    PaginationDTO(int page, int size, Long totalItems, Long totalPages) {
        super();
        this.page = page;
        this.size = size;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }
    
    PaginationDTO(PageInfo pageInfo) {
        super();
        this.page = pageInfo.page;
        this.size = pageInfo.size;
        this.totalItems = pageInfo.totalResults
        this.totalPages = pageInfo.totalPages
    }
}
