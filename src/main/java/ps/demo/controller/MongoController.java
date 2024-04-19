package ps.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Setter;
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
    @GetMapping("/higet")
    public ResponseEntity<String> higet(
            @RequestParam(name = "par", required = true) String par,
            HttpServletRequest request
    ) {
        log.info("===>>par={}", par);
        log.info("===>>req={}", request.getParameterMap());
        return new ResponseEntity<>("["+par+"]", HttpStatus.OK);
    }

    @PostMapping("/hipost")
    public ResponseEntity<String> hipost(
            @RequestParam(name = "par", required = false) String par,
            @RequestBody(required = false) String reqBody,
            HttpServletRequest request
    ) {
        log.info("===>>reqPar={}", par);
        log.info("===>>reqBody={}", reqBody);

        return new ResponseEntity<>(par+reqBody, HttpStatus.OK);
    }

    @PutMapping("/hiput")
    public ResponseEntity<String> hiput(
            @RequestParam(name = "par", required = false) String par,
            @RequestBody(required = false) String reqBody,
            HttpServletRequest request
    ) {
        log.info("===>>reqPar={}", par);
        log.info("===>>reqBody={}", reqBody);

        return new ResponseEntity<>(par+reqBody, HttpStatus.OK);
    }

    @DeleteMapping("/hidel")
    public ResponseEntity<String> hidel(
            @RequestParam(name = "par", required = false) String par,
            @RequestBody(required = false) String reqBody,
            HttpServletRequest request
    ) {
        log.info("===>>reqPar={}", par);
        log.info("===>>reqBody={}", reqBody);

        return new ResponseEntity<>(par+reqBody, HttpStatus.OK);
    }

    @PatchMapping("/hipatch")
    public ResponseEntity<String> hipatch(
            @RequestParam(name = "par", required = false) String par,
            @RequestBody(required = false) String reqBody,
            HttpServletRequest request
    ) {
        log.info("===>>reqPar={}", par);
        log.info("===>>reqBody={}", reqBody);

        return new ResponseEntity<>(par+reqBody, HttpStatus.OK);
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
