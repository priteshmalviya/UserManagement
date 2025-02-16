package com.pritesh.usermanagement.converter;

import org.json.JSONArray;
import org.json.JSONException;

import com.pritesh.usermanagement.utils.Constants;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class JpaConverterForJSONArray implements AttributeConverter<JSONArray, String> {

  private Logger logger = LogManager.getLogger(JpaConverterForJSONArray.class);

  @Override
  public String convertToDatabaseColumn(JSONArray meta) {
    if (meta != null) {
      return meta.toString();
    } else {
      return new JSONArray().toString();
    }
  }

  @Override
  public JSONArray convertToEntityAttribute(String dbData) {
    if (dbData != null && !dbData.equals(Constants.BLANK_STRING) && !dbData.equals(Constants.EMPTY_JSON_ARRAY) && !dbData.equals(Constants.NULL_STRING)) {
      try {
        return new JSONArray(dbData);
      } catch (IllegalStateException e) {
        return new JSONArray();
      } catch (JSONException e) {
        logger.error("Error in convertToEntityAttribute,Error : {} ", e.getMessage());
      }
    }
    return new JSONArray();
  }
}
