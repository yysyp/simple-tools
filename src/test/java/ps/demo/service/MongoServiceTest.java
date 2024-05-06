package ps.demo.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.assertions.Assertions;
import com.mongodb.client.result.DeleteResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.test.context.ActiveProfiles;
import org.thymeleaf.util.StringUtils;
import ps.demo.common.NewDateTimeTool;
import ps.demo.pojo.DemoStruct;


import java.time.*;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@ActiveProfiles(value = "sit") //-Dspring.profiles.active=ut,sit
class MongoServiceTest {

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    void findAll() {
        Document document = Document.parse("""
                {"basic": {"name": "xiaoh", "time": "2024-01-01T00:00:00Z", "ver": 1}, "user": "555", "data": {"x": 123, "y": "hhi"}}
                """);
        mongoTemplate.insert(document, "test");
        List<Document> documentList = mongoTemplate.findAll(Document.class, "test");
        log.info("documentList = {}", documentList);

        Query query = new Query();
        Criteria criteria = Criteria.where("user").regex(".*"+Pattern.quote("555")+".*", "si");

        query.addCriteria(criteria);

        DeleteResult deleteResult = mongoTemplate.remove(query, "test");
        log.info("delete count={}", deleteResult.getDeletedCount());

        Assertions.assertTrue(1==1);
    }

    @Test
    void findByKey() {
        String collectionName = "demo";
        Document document = Document.parse("""
                {
                	"basic": {
                		"name": "xiaoh",
                		"time": "2024-01-01T00:00:00Z",
                		"bizDate": "2024-01-01",
                		"ver": 1
                	},
                	"user": "555",
                	"data": {
                		"x": 123,
                		"y": "hhi"
                	}
                }
                """);
        mongoTemplate.insert(document, collectionName);
        List<Document> documentList = mongoTemplate.findAll(Document.class, collectionName);
        log.info("documentList = {}", documentList);


    }

    @Test
    void insert() {
        //DateUtil.convertTimeZone()
        for (int i = 0; i < 10; i++) {
            ZoneId zoneId = ZoneId.of("UTC");
            LocalDateTime localDateTime = NewDateTimeTool.getStartTimeOfDay(LocalDateTime.now(zoneId));
            LocalDate localDate = localDateTime.toLocalDate();
            ;
            ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
            Date date = Date.from(localDateTime.atZone(zoneId).toInstant());


            log.info("--date="+date.getTime() + " -- localDateTime.atZone(zoneId).toInstant().toEpochMilli()="+localDateTime.atZone(zoneId).toInstant().toEpochMilli()
            + "--localDateTime.toLocalDate()=" + localDate
            + "--zoneddatetime=" + ZonedDateTime.of(2024, 4, 15, 0, 0, 0, 0, zoneId).toInstant()
            );

            DemoStruct demoStruct = DemoStruct.builder().user(StringUtils.randomAlphanumeric(5)+i)
                    .basic(DemoStruct.Basic.builder().name(StringUtils.randomAlphanumeric(3))
                            .time(date)
                            //.bizDate(localDateTime.toLocalDate())
                            //.bizDate(zonedDateTime)
                            .bizDate(ZonedDateTime.of(2024, 4, 15, 0, 0, 0, 0, zoneId).toInstant())
                            .ver(RandomUtils.nextInt(1, 11)).build())
                    .theData(DemoStruct.TheData.builder().x(RandomUtils.nextInt(100, 1000))
                            .y(RandomUtils.nextInt(100, 1000)).build())
                    .build();

            String json = JSONUtil.toJsonStr(demoStruct);

            mongoTemplate.insert(Document.parse(json), "demo1");

        }

    }

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void findByDateTime() throws JsonProcessingException {
        ZoneId zoneId = ZoneId.of("UTC");
        LocalDateTime localDateTime = NewDateTimeTool.getStartTimeOfDay(LocalDateTime.now(zoneId));
        Query query = new Query();
        //Criteria criteria = Criteria.where("basic.time").is(Date.from(localDateTime.atZone(zoneId).toInstant()).getTime());
        //Criteria criteria = Criteria.where("basic.time").is(localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli());
        Criteria criteria = Criteria.where("basic.time").lte(localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli());

        query.addCriteria(criteria);

        List<Document> list = mongoTemplate.find(query, Document.class, "demo1");
        log.info("find count={}", list.size());
        log.info("-->>objectMapper serialize 0 = " + objectMapper.writeValueAsString(list.get(0)));

    }

    @Test
    void deleteAll() {
        Query query = new Query();
        DeleteResult deleteResult = mongoTemplate.remove(query, "demo1");
        log.info("---delete count = {}", deleteResult.getDeletedCount());
    }


    @Test
    void queryWithPagination() {
        int totalElements = 40;
        //int pageNumber = 0;
        int pageSize = 10;
        int totalPages = (totalElements + pageSize - 1) / pageSize;

        for (int i = 0; i < totalPages; i++) {
            int pageNumber = i;

            String collectionName = "demo1";
            Query query = new Query();
            Criteria criteria = Criteria.where("basic.time").gte(0l);
            query.addCriteria(criteria);

            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            query.with(pageable);
            Sort sort = Sort.by(Sort.Direction.DESC, new String[]{"basic.time"});
            query.with(sort);
            List<Document> list = this.mongoTemplate.find(query, Document.class, collectionName);
            Page<Document> page = PageableExecutionUtils.getPage(list, pageable, () -> {
                return this.mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Document.class, collectionName);
            });

            //return page;
            List<Document> contentOfPage = page.getContent();
            log.info("--->>mongo data query in pagination: totalElements={}, pageSize={}, totalPages={}, contentSize={}, content={}",
                    page.getTotalElements(),
                    pageSize,
                    page.getTotalPages(),
                    contentOfPage.size(),
                    contentOfPage);
        }
    }


}