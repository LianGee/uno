package com.bigdata.uno.service;

import java.util.List;

public interface InfoService {
    boolean inform(List<String> recipients, String title, String content);
}
