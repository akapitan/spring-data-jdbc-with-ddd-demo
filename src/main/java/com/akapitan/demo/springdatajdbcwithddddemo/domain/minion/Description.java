package com.akapitan.demo.springdatajdbcwithddddemo.domain.minion;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import org.postgresql.util.PGobject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;

public class Description {

  private static final String JSON_APPEARANCE = "apperance";
  public static final String JSON_PERSONALITY = "personality";

  Map<String, String> appearance = new HashMap<>();
  Map<String, String> personality = new HashMap<>();

  @WritingConverter
  public enum DescriptionToString implements Converter<Description, PGobject> {

    INSTANCE;

    @Override
    public PGobject convert(Description source) {

      JSONObject json = new JSONObject();

      json.put(JSON_APPEARANCE, source.appearance);
      json.put(JSON_PERSONALITY, source.appearance);

      PGobject jsonObject = new PGobject();
      jsonObject.setType("jsonb");
      try {
        jsonObject.setValue(json.toString());
      } catch (SQLException e) {
        throw new UnsupportedOperationException(e);
      }

      return jsonObject;
    }
  }

  @ReadingConverter
  public enum StringToDescription implements Converter<PGobject, Description> {

    INSTANCE;

    @Override
    public Description convert(PGobject jsonString) {

      JSONObject json = new JSONObject(jsonString.getValue());
      Description description = new Description();
      json.getJSONObject(JSON_APPEARANCE).toMap()
          .forEach((k, v) -> description.appearance.put(k, v.toString()));
      json.getJSONObject(JSON_PERSONALITY).toMap()
          .forEach((k, v) -> description.personality.put(k, v.toString()));

      return description;
    }
  }
}
