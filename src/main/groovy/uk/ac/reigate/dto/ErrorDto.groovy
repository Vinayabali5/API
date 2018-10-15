package uk.ac.reigate.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "")
public class ErrorDto {
    
    @ApiModelProperty(value = "The error code.")
    @JsonProperty("code")
    Integer code = null;
    
    @ApiModelProperty(value = "The error message.")
    @JsonProperty("message")
    String message = null;
    
    @ApiModelProperty(value = "")
    @JsonProperty("fields")
    String fields = null;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ErrorDto error = (ErrorDto) o;
        return Objects.equals(code, error.code) && Objects.equals(message, error.message) && Objects.equals(fields, error.fields);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(code, message, fields);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Error {\n");
        
        sb.append("  code: ").append(code).append("\n");
        sb.append("  message: ").append(message).append("\n");
        sb.append("  fields: ").append(fields).append("\n");
        sb.append("}\n");
        return sb.toString();
    }
}
