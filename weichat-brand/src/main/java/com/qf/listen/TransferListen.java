package com.qf.listen;

import com.qf.dao.TransferMapper;
import com.qf.pojo.Director;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by 54110 on 2021/1/5.
 */
@Component
public class TransferListen {

    @Autowired
    TransferMapper transferMapper;

    @RabbitListener(queues = "transfer-queue")
    public void transfer(Map map){
        Object name = map.get("name");
        transferMapper.deleteByName(name);
    }
    @RabbitListener(queues = "transfer-queuetwo")
    public void transfer1(Map map){
        System.out.println("map==============="+map);
        Object name1 = map.get("name1");
        Object name2 = map.get("name2");

        try {
            Director director1 = transferMapper.findByName(name1);
            System.out.println(director1);
            director1.setName(name2.toString());
            transferMapper.updateDirector(director1);
        }catch (Exception e){

        }

    }
}
