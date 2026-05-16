package com.absar.eatsyncbackend.dto;

import java.util.List;

public class AddonGroupDto {
    private String groupId;
    private String groupName;
    private int maxAddons;
    private int maxFreeAddons;
    private List<AddonChoiceDto> choices;

    public AddonGroupDto() {
    }

    public AddonGroupDto(
            String groupId,
            String groupName,
            int maxAddons,
            int maxFreeAddons,
            List<AddonChoiceDto> choices
    ) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.maxAddons = maxAddons;
        this.maxFreeAddons = maxFreeAddons;
        this.choices = choices;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public int getMaxAddons() {
        return maxAddons;
    }

    public int getMaxFreeAddons() {
        return maxFreeAddons;
    }

    public List<AddonChoiceDto> getChoices() {
        return choices;
    }
}