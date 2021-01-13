package com.qf.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.qf.common.BaseResp;
import com.qf.pojo.Comments;
import com.qf.service.SearchService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    RestHighLevelClient client;

    @Value("${es.index}")
    private String index;


    @Override
    public BaseResp searchKey(String key, Integer page, Integer size) {
        //设置返回值
        BaseResp baseResp = new BaseResp();
        //创建索引请求
        SearchRequest searchRequest = new SearchRequest(index);
        //设置类型
        searchRequest.types("doc");
        //声明搜索源对象(searchSourceBuilder)
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //设置搜索方式  如果key值为空则为查询全部
        if(StringUtils.isEmpty(key)){
            searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        }else {
            //key值不为空,按条件插询
            searchSourceBuilder.query(QueryBuilders.multiMatchQuery(key,"info","openid","utel","username"));
        }

        //高亮展示设置
        //创建高亮展示对象
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        //设置要高亮展示的字段
        highlightBuilder.field("info");
        highlightBuilder.field("openid");
        highlightBuilder.field("utel");
        highlightBuilder.field("username");
        //设置前后缀
        highlightBuilder.preTags("<font style='color:red'>");
        highlightBuilder.postTags("</font>");

        //将highlightBuilder设置到搜索源中
        searchSourceBuilder.highlighter(highlightBuilder);

        //排序

        //分页设置
        int from =(page-1)*size;
        searchSourceBuilder.from(from);
        searchSourceBuilder.size(size);

        //将搜索条件添加到搜索请求中
        SearchRequest source = searchRequest.source(searchSourceBuilder);
        try {
            //使用高版本的客户端发起请求
            SearchResponse search = client.search(source);
            //解析返回值
            SearchHits hits = search.getHits();
            //获取数据的总条数
            long totalHits = hits.getTotalHits();
            baseResp.setTotal(totalHits);

            SearchHit[] hits1 = hits.getHits();
            List list = new ArrayList();
            //从hits1中获取数据,添加到list集合中
            for (SearchHit sea:hits1
                 ) {
                //获取并处理高亮字段
                Map<String, HighlightField> highlightFields = sea.getHighlightFields();

                String info = null;
                String openid = null;
                String utel = null;
                String username = null;

                if (highlightFields!=null){

                    HighlightField info1 = highlightFields.get("info");
                    HighlightField openid1 = highlightFields.get("openid");
                    HighlightField utel1 = highlightFields.get("utel");
                    HighlightField username1 = highlightFields.get("username");

                    if (info1!=null){
                        Text[] fragments = info1.getFragments();
                        StringBuffer stringBuffer = new StringBuffer();
                        for (Text te: fragments
                             ) {
                            info= stringBuffer.append(te).toString();
                        }
                    }
                    if (openid1!=null){
                        Text[] fragments = openid1.getFragments();
                        StringBuffer stringBuffer = new StringBuffer();
                        for (Text te: fragments
                             ) {
                            openid= stringBuffer.append(te).toString();
                        }
                    }
                    if (utel1!=null){
                        Text[] fragments = utel1.getFragments();
                        StringBuffer stringBuffer = new StringBuffer();
                        for (Text te: fragments
                             ) {
                            utel= stringBuffer.append(te).toString();
                        }
                    }
                    if (username1!=null){
                        Text[] fragments = username1.getFragments();
                        StringBuffer stringBuffer = new StringBuffer();
                        for (Text te: fragments
                             ) {
                            username= stringBuffer.append(te).toString();
                        }
                    }
                }
                //处理其他数据
                Map<String, Object> sourceAsMap = sea.getSourceAsMap();
                if (info!=null){
                    sourceAsMap.put("info",info);
                }
                if (openid!=null){
                    sourceAsMap.put("openid",openid);
                }
                if (utel!=null){
                    sourceAsMap.put("utel",utel);
                }
                if (username!=null){
                    sourceAsMap.put("username",username);
                }
                Object o = JSONObject.toJSON(sourceAsMap);
                Comments comments = JSONObject.parseObject(o.toString(), Comments.class);
                list.add(comments);

            }
            baseResp.setCode(200);
            baseResp.setMessage("查询成功");
            baseResp.setData(list);
            return baseResp;
        } catch (IOException e) {
            e.printStackTrace();
            baseResp.setCode(201);
            baseResp.setMessage("查询失败");
            return baseResp;
        }

    }
}
