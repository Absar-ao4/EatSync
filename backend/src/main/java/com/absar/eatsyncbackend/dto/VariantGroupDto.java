package com.absar.eatsyncbackend.dto;

import java.util.List;

public class VariantGroupDto {
    private String groupId;
    private String name;
    private List<VariantOptionDto> options;

    public VariantGroupDto() {
    }

    public VariantGroupDto(
            String groupId,
            String name,
            List<VariantOptionDto> options
    ) {
        this.groupId = groupId;
        this.name = name;
        this.options = options;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getName() {
        return name;
    }

    public List<VariantOptionDto> getOptions() {
        return options;
    }
}