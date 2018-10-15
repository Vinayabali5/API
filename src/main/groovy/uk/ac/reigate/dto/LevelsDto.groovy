package uk.ac.reigate.dto

import uk.ac.reigate.model.PageInfo

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty;

class LevelsDto implements Serializable {
    
    PaginationDTO page
    
    @Deprecated
    @JsonIgnore
    long currentPage;
    
    @Deprecated
    @JsonIgnore
    long totalPages;
    
    List<LevelDto> levels;
    
    public LevelsDto(PageInfo pageInfo, List<LevelDto> levels) {
        super();
        this.page = new PaginationDTO(pageInfo)
        this.levels = levels;
    }
    
    @Deprecated
    public LevelsDto(long currentPage, long totalPages, List<LevelDto> levels) {
        super();
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.levels = levels;
    }
    
    @Deprecated
    public long getCurrentPage() {
        return currentPage;
    }
    
    @Deprecated
    public void setCurrentPage(long currentPage) {
        this.currentPage = currentPage;
    }
    
    @Deprecated
    public long getTotalPages() {
        return totalPages;
    }
    
    @Deprecated
    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }
    
    public List<LevelDto> getLevels() {
        return levels;
    }
    
    public void setLevels(List<LevelDto> levels) {
        this.levels = levels;
    }
}
