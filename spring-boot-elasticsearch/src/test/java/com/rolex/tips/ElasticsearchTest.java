package com.rolex.tips;

import com.google.gson.Gson;
import com.rolex.tips.model.User;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.main.MainResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.ParsedAvg;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.LongStream;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@Slf4j
public class ElasticsearchTest {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Test
    public void test01() throws IOException {
        MainResponse info = restHighLevelClient.info(RequestOptions.DEFAULT);
        log.info("{}", new Gson().toJson(info));
    }

    @Test
    public void test02() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("user");//创建索引
        request.alias(
                new Alias("user_alias")
        );

        request.mapping(
                "{\n" +
                        "        \"properties\":{\n" +
                        "            \"id\":{\n" +
                        "                \"type\":\"long\"\n" +
                        "            },\n" +
                        "            \"name\":{\n" +
                        "                \"type\":\"keyword\"\n" +
                        "            },\n" +
                        "            \"gender\":{\n" +
                        "                \"type\":\"integer\"\n" +
                        "            },\n" +
                        "            \"age\":{\n" +
                        "                \"type\":\"integer\"\n" +
                        "            },\n" +
                        "            \"remark\":{\n" +
                        "                \"type\":\"text\"\n" +
                        "            }\n" +
                        "        }\n" +
                        "}", XContentType.JSON);
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        log.info("{}", createIndexResponse.index());

    }

    @Test
    public void test03() throws IOException {
        String index = "user";
        String indexType = "_doc";
        User user1 = new User(1L, "John", 1, 20, "我叫John");
        IndexRequest indexRequest = Requests.indexRequest()
                .index(index)
                .type(indexType)
                .id(user1.getId() + "")
                .source(new Gson().toJson(user1), XContentType.JSON);
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        log.info("{}", indexResponse);

        GetRequest getRequest = new GetRequest(index, indexType, indexResponse.getId());
        GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        log.info("{}", getResponse);

        User user2 = new User(1L, "Alice", 2, 20, "我叫Alice");
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(index).type(indexType).id(getResponse.getId()).doc(new Gson().toJson(user2), XContentType.JSON);
        UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        log.info("{}", updateResponse);

        DeleteRequest deleteRequest = new DeleteRequest(index, indexType, updateResponse.getId());
        DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        log.info("{}", deleteResponse);

    }

    @Test
    public void test04() throws IOException {
        String index = "user";
        String indexType = "_doc";
        Gson gson = new Gson();
        BulkRequest bulkRequest = new BulkRequest();
        LongStream.range(1, 100)
                .forEach(l -> bulkRequest.add(new IndexRequest(index, indexType, l + "")
                        .source(gson.toJson(new User(l, "user" + l, new Random().nextInt(100) % 2 + 1, new Random().nextInt(100), "第" + l + "个用户")), XContentType.JSON)));
        BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        log.info("{}", bulkResponse);
    }

    @Test
    public void test05() throws IOException {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.must(QueryBuilders.rangeQuery("age").from(30).to(60))
                .must(QueryBuilders.matchQuery("remark", "5"));
        SearchRequest searchRequest = new SearchRequest("user");
        searchRequest.source(new SearchSourceBuilder().query(queryBuilder));
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        long totalHits = hits.getTotalHits();
        log.info("totalHits={}", totalHits);
        Arrays.stream(hits.getHits())
                .forEach(i -> {
                    log.info("{}", i.getSourceAsMap());
                });
    }

    @Test
    public void test06() throws IOException {
        String index = "user";
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("group_by_gender").field("gender");
        termsAggregationBuilder.subAggregation(AggregationBuilders.avg("average_age").field("age"));
        SearchSourceBuilder sourceBuilder = searchSourceBuilder.aggregation(termsAggregationBuilder);
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        Aggregations aggregations = searchResponse.getAggregations();
        Map<String, Aggregation> stringAggregationMap = aggregations.asMap();
        ParsedLongTerms groupByGender = (ParsedLongTerms) stringAggregationMap.get("group_by_gender");
        List<? extends Terms.Bucket> buckets = groupByGender.getBuckets();
        buckets.forEach(b -> {
            Map<String, Aggregation> subAggregationMap = b.getAggregations().asMap();
            ParsedAvg averageAge = (ParsedAvg) subAggregationMap.get("average_age");
            double value = averageAge.getValue();
            String type = b.getKeyAsString();
            log.info("type={}, value={}", type, value);
        });
    }

    @Test
    public void test07() throws IOException {
        String index = "user";
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.sort("id");
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        for (int i = 0, sum = 0; sum < searchResponse.getHits().totalHits; i++) {
            SearchRequest request = new SearchRequest(index);
            SearchSourceBuilder builder = new SearchSourceBuilder();
            builder.query(QueryBuilders.matchAllQuery());
            builder.size(5); // pageSize
            builder.sort("id");
            builder.from(sum);
            request.source(builder);
            SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
            SearchHit[] searchHits = response.getHits().getHits();
            for (SearchHit searchHit : searchHits) {
                log.info(searchHit.getSourceAsString());
            }
            sum += response.getHits().getHits().length;
            log.info("总量{}，已经查到{}", searchResponse.getHits().totalHits, sum);
        }
    }

    @Test
    public void test08() throws IOException {
        String index = "user";
        //初始化scroll
        final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L)); //设定滚动时间间隔
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.scroll(scroll);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.size(5); //设定每次返回多少条数据
        searchSourceBuilder.sort("id");
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        String scrollId = searchResponse.getScrollId();
        log.info("scrollId={}", scrollId);
        SearchHit[] searchHits = searchResponse.getHits().getHits();
        for (SearchHit searchHit : searchHits) {
            log.info(searchHit.getSourceAsString());
        }
        //遍历搜索命中的数据，直到没有数据
        while (searchHits != null && searchHits.length > 0) {
            SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
            scrollRequest.scroll(scroll);
            searchResponse = restHighLevelClient.scroll(scrollRequest, RequestOptions.DEFAULT);
            scrollId = searchResponse.getScrollId();
            searchHits = searchResponse.getHits().getHits();
            if (searchHits != null && searchHits.length > 0) {
                log.info("----------");
                for (SearchHit searchHit : searchHits) {
                    log.info(searchHit.getSourceAsString());
                }
            }
        }
    }

    @Test
    public void test09() throws IOException {
        String index = "user";
        DeleteIndexRequest request = new DeleteIndexRequest(index);
        AcknowledgedResponse acknowledgedResponse = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        log.info("{}", acknowledgedResponse.isAcknowledged());
    }

    @Test
    public void test10() throws IOException, InterruptedException {
        for (int i = 0; i < 10000; i++) {
            GetRequest request = new GetRequest("user", "_doc", "14");
            GetResponse getResponse = restHighLevelClient.get(request, RequestOptions.DEFAULT);
            log.info("第{}次查询：{}", (i + 1), getResponse);
            restHighLevelClient.close();
            Thread.sleep(2000);
        }
    }
}
