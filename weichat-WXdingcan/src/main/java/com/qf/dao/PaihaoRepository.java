package com.qf.dao;

import com.qf.pojo.Paihao;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * 排号操作的repository
 *
 */
@Mapper
public interface PaihaoRepository extends JpaRepository<Paihao, Integer> {
    List<Paihao> findByDayAndType(String day, Integer type);

    List<Paihao> findByOpenidAndDay(String openid, String day);

    //查询当天已经就位的客户，并按照号码排序
    List<Paihao> findByDayAndRuzuoAndTypeOrderByNum(String day, Integer ruzuo, Integer type);


    //自定义
    Optional<Paihao> findById(Integer id);

    List<Paihao> findByRuzuo(Integer ruzhuo);
}
