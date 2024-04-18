package ps.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ps.demo.service.MongoService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/mongo")
public class MongoController {


    @Autowired
    MongoService mongoService;

    //http://localhost:18888/api/mongo/hi?p1=+
    @GetMapping("/hi")
    public ResponseEntity<String> hi(
            @RequestParam(name = "p1", required = true) String p1,
            HttpServletRequest request
    ) {
        log.info("===>>p1=[{}]", p1);
        log.info("===>>req={}", request.getParameterMap());
        return new ResponseEntity<>("["+p1+"]", HttpStatus.OK);
    }

    @GetMapping("/collection-name/{collectionName}/documents")
    public ResponseEntity<List<Document>> findByCollectionName(
            @PathVariable String collectionName
    ) {
        List<Document> list = mongoService.findAll(collectionName);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/collection-name/{collectionName}/documents/key")
    public ResponseEntity<List<Document>> findByCollectionNameByKey(
            @PathVariable String collectionName,
            @RequestParam String key
    ) {
        List<Document> list = mongoService.findByKey(collectionName, key);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    @PostMapping("/collection-name/{collectionName}")
    @Operation(summary = "To create a document in given collection in mongodb",
            description = """
                    ex: Request body put:
                    {"basic": {"name": "xiaoh", "time": "2024-01-01T00:00:00Z", "ver": 1}, "user": "13244", "data": {"x": 123, "y": "hhi"}}
                    """
    )
    public ResponseEntity<Document> insertByCollectionName(
            @PathVariable String collectionName,
            @RequestBody String jsonDoc
    ) {
        Document document = Document.parse(jsonDoc);
        Document inserted = mongoService.insert(document, collectionName);

        return new ResponseEntity<>(inserted, HttpStatus.OK);
    }

}
