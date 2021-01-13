package com.qf.service;

import com.qf.common.ResultVO;
import com.qf.dao.PaihaoRepository;
import com.qf.pojo.Paihao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public interface PaihaoService {

    ResultVO findAndFlush(Integer id);

    ResultVO findByRuzhuo();
}
