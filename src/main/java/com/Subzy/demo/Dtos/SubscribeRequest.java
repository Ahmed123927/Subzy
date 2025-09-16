package com.Subzy.demo.Dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscribeRequest {
    private Long clientId;
    private Long planId;
    private boolean autoRenew;
}

