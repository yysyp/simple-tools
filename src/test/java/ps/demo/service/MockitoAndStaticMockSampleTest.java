package ps.demo.service;


import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.data.mongodb.core.MongoTemplate;
import ps.demo.common.StringTool;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MockitoAndStaticMockSampleTest {

    @Mock
    MongoTemplate mongoTemplate;

    @InjectMocks
    MongoService mongoService;


    @Test
    void testInsert() {
        //given
        Document document = new Document();
        String collectionName = "collectionName";

        //when
        when(mongoTemplate.insert(document, collectionName))
                .thenReturn(document);


        //then
        Document document1 = mongoService.insert(document, collectionName);
        assertThat(document1).isEqualTo(document);
    }

    //https://howtodoinjava.com/mockito/mock-static-methods/
    @Test
    void testFindAll() {
        //given
        String collectionName = "collectionName";
        List<Document> list = List.of(new Document("key1", "value1"));

        //when
        when(mongoTemplate.findAll(Document.class, collectionName))
                .thenReturn(list);

        //static mock begin
        try(MockedStatic<StringTool> stringToolMockedStatic = Mockito.mockStatic(StringTool.class)) {

            stringToolMockedStatic.when(() -> StringTool.printOut(list, System.out))
                    .then((invocationOnMock) -> {
                        return null;
                    });
                    //.thenReturn(list);

            //then
            List<Document> result = mongoService.findAll(collectionName);
            assertThat(result).hasSameElementsAs(list);
            assertThat(result).hasSameSizeAs(list);

        } //static mock end.

    }

}
