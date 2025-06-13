package org.atlas.neuroguard.infra.config;

public class SuccessResponse {
    public String message;
    public long timestamp;

    public SuccessResponse(String message) {
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }
}