package com.bigdata.uno.common.model.cas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResponse {
    private AuthenticationSuccess authenticationSuccess;
}
