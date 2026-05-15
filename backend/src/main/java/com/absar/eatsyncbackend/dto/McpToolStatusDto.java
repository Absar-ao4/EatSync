package com.absar.eatsyncbackend.dto;

public class McpToolStatusDto{
    private String toolName;
    private String status;
    private String note;

    public McpToolStatusDto(){
    }
    public McpToolStatusDto(
            String toolName,
            String status,
            String note
    ){
        this.toolName = toolName;
        this.status = status;
        this.note = note;
    }
    public String getToolName(){
        return toolName;
    }
    public String getStatus(){
        return status;
    }
    public String getNote(){
        return note;
    }
}