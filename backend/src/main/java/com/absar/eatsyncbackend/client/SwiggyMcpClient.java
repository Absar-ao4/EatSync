package com.absar.eatsyncbackend.client;

import org.springframework.stereotype.Component;

@Component
public class SwiggyMcpClient {

    public void callTool(
            String toolName,
            String arguments
    ) {
        System.out.println("Swiggy MCP tool requested: " + toolName);
        System.out.println("Arguments: " + arguments);
    }
}