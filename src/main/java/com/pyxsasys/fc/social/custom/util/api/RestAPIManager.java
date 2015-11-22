package com.pyxsasys.fc.social.custom.util.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pyxsasys.fc.social.custom.util.FCSocialProcessingUtil;

public class RestAPIManager<T> {

    private final Logger log = LoggerFactory.getLogger(RestAPIManager.class);

    public T getParsedJSON(String api, Class<T> clazz) throws Exception {
        log.debug("Connecting to the API and getting the JSON response [ " + api + " ]");
        String jsonRes = FCSocialProcessingUtil.getJsonStringResponse(api);
        return parseJsonToJava(jsonRes, clazz);
    }

    private T parseJsonToJava(String jsonRes, Class<T> clazz) throws Exception {
        ObjectMapper objMapper = new ObjectMapper();
        objMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        log.debug("Successfully converted the Fixtures JSON to Java object");
        return objMapper.readValue(jsonRes, clazz);
    }

}
