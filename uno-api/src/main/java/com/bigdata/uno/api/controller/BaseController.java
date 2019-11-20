package com.bigdata.uno.api.controller;

import com.bigdata.uno.api.util.LoginUtil;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseController {
    @Autowired
    protected LoginUtil loginUtil;
}
