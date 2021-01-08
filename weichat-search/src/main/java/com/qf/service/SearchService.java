package com.qf.service;

import com.qf.common.BaseResp;

public interface SearchService {
    BaseResp searchKey(String key, Integer page, Integer size);
}
