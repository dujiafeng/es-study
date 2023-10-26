package cn.gyyx.cabinet;


import cn.gyyx.cabinet.pojo.Cabinet;
import cn.gyyx.cabinet.service.ICabinetService;
import com.alibaba.fastjson.JSON;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
class CabinetDocumentTest {

    private RestHighLevelClient client;

    @Autowired
    private ICabinetService cabinetService;

    @Test
    void testAddDocument() throws IOException {
        // 1.查询数据库hotel数据
        Cabinet cabinet = cabinetService.getById(1);

        // 3.转JSON
        String json = JSON.toJSONString(cabinet);

        // 1.准备Request
        IndexRequest request = new IndexRequest("cabinet").id(cabinet.getId().toString());
        // 2.准备请求参数DSL，其实就是文档的JSON字符串
        request.source(json, XContentType.JSON);
        // 3.发送请求
        client.index(request, RequestOptions.DEFAULT);
    }

    @Test
    void testGetDocumentById() throws IOException {

    }

    @Test
    void testDeleteDocumentById() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest();
        deleteRequest.id("1");
        client.delete(deleteRequest,RequestOptions.DEFAULT);
    }

    @Test
    void testUpdateById() throws IOException {

    }

    @Test
    void testBulkRequest() throws IOException {


        Cabinet byId = cabinetService.getById(1);
        List<Cabinet> list = cabinetService.list();
        BulkRequest request = new BulkRequest();
        for (Cabinet cabinet : list) {
            String json = JSON.toJSONString(cabinet);
            request.add(new IndexRequest("cabinet").id(cabinet.getId().toString()).source(json, XContentType.JSON));
        }

        // 3.发送请求
        client.bulk(request, RequestOptions.DEFAULT);
    }

    @BeforeEach
    void setUp() {
        HttpHost httpHost = HttpHost.create("http://localhost:9200");
        RestClientBuilder builder = RestClient.builder(httpHost);
        //开始设置用户名和密码
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("elastic", "dujiafeng"));
        builder.setHttpClientConfigCallback(f -> f.setDefaultCredentialsProvider(credentialsProvider));
        client = new RestHighLevelClient(builder);
    }

    @AfterEach
    void tearDown() throws IOException {
        client.close();
    }



}
