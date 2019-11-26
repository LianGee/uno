package com.bigdata.uno.service;

import com.bigdata.uno.common.doc.Doc;

public interface DocService {
    long save(Doc doc);

    Doc queryById(long id);

    boolean delete(long id);
}
