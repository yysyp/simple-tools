package ps.demo.service;


import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import ps.demo.common.StringXTool;

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
        try(MockedStatic<StringXTool> stringToolMockedStatic = Mockito.mockStatic(StringXTool.class)) {

            stringToolMockedStatic.when(() -> StringXTool.printOut(list, System.out))
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

    /**

    @Test
    public void testGetVal() {
        //Outside scope
        assertEquals("foo", ClassWithStaticMethod.getVal());

        try (MockedStatic mockStatic = mockStatic(ClassWithStaticMethod.class)) {

            mockStatic.when(ClassWithStaticMethod::getVal).thenReturn("bar");

            //Inside scope
            assertEquals("bar", ClassWithStaticMethod.getVal());
            mockStatic.verify(ClassWithStaticMethod::getVal);
        }

        //Outside scope
        assertEquals("foo", ClassWithStaticMethod.getVal());
    }

    @Test
    public void testAdd() {
        assertEquals(3, ClassWithStaticMethod.add(1, 2));

        try (MockedStatic mockStatic = mockStatic(ClassWithStaticMethod.class)) {

            mockStatic.when(() -> ClassWithStaticMethod.add(anyInt(), anyInt())).thenReturn(10);

            assertEquals(10, ClassWithStaticMethod.add(1, 2));
            mockStatic.verify(() -> ClassWithStaticMethod.add(1, 2));
        }

        assertEquals(3, ClassWithStaticMethod.add(1, 2));
    }

    **/
}
