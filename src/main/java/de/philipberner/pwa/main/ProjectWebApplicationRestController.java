package de.philipberner.pwa.main;

import com.google.gson.Gson;
import de.philipberner.pwa.data.Data;
import de.philipberner.pwa.storage.Storage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import java.util.Arrays;
import java.util.Date;

/**
 * @author Philip Berner
 * @version 1.0
 */
@RestController
public class ProjectWebApplicationRestController {

    /**
     * Gets all Data from Storage
     * HTTP
     *      Content-Type: JSON
     *      Method: GET
     *      Status: 200
     * @return a Http-Response with Json Data
     */
    @GetMapping(value = "/api/getData", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getData() {
        //makes a data clone
        Data[] data = Storage.getData();
        Data[] temp = Arrays.stream(data)
                .map(Data::new)
                .toArray(Data[]::new);

        for (Data datum : temp) {
            long date = datum.getDate();
            //Escapes the Name and Comment for XSS
            datum.setName(HtmlUtils.htmlEscape(datum.getName()));
            datum.setComment(HtmlUtils.htmlEscape(datum.getComment()));
            datum.setDate(date);
        }
        //returns Http-Response
        return ResponseEntity.status(HttpStatus.OK).body(temp);
    }

    /**
     * adds a Data Object
     * HTTP
     *      Content-Type: JSON
     *      Method: POST
     *      Status: 204, 422
     * @param data the Data, that ís added - must be structured like Data-Objekt
     * @return Http-Response
     */
    @PostMapping(value = "/api/addData", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addData(@RequestBody String data) {
        Gson gson = new Gson();
        //Converts JSON-String to Data Object with Gson
        Data addData = gson.fromJson(data, Data.class);
        //updates the Time of the Data
        addData.setDate(new Date().getTime());
        //checks for success
        boolean success = Storage.addData(addData);
        //Returns response
        return success ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() : ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Data.id needs to be unique");
    }

    /**
     * removes a Data Object
     * HTTP
     *      Content-Type: Plain Text
     *      Method: POST
     *      Status: 204, 422
     * @param id is an id fron an existing Data-Object in the Storage, that should get removed
     * @return HTTP-response
     */
    @PostMapping(value = "/api/removeData", consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<?> removeData(@RequestBody String id) {
        //parse String to int
        int removeId = Integer.parseInt(id);
        //tries to remove and checks for success
        boolean success = Storage.removeData(new Data(removeId, null, 0));
        //returns Http-Response
        return success ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() : ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("There is no Data with this id");
    }

    /**
     * Changes an existing Data Object
     * HTTP
     *      Content-Type: JSON
     *      Method: POST
     *      Status: 204, 422
     * @param requestData the Json-String - must be an Array of 2 Data-Objects
     * @return HTTP-Response
     */
    @PostMapping(value = "/api/changeData", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeData(@RequestBody String requestData) {
        Gson gson = new Gson();
        //Converts Json to Data Array
        Data[] dataArray = gson.fromJson(requestData, Data[].class);
        //checks for length
        if (dataArray.length != 2) {
            //returns 422 Response
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There must be exactly two elements, first the old one and than the new one");
        }
        //updates time
        dataArray[1].setDate(new Date().getTime());
        //makes requested change
        boolean success = Storage.changeData(dataArray[0], dataArray[1]);
        //returns response
        return success ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() : ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Data.id needs to be unique and the old Data must exist");
    }
}
