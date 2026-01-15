package com.itxiop.transport.infrastructure.apirest.converters;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import com.itxiop.transport.infrastructure.apirest.model.City;
import com.itxiop.transport.infrastructure.apirest.model.Route;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

public class MarshallingCSVConverter<T> implements HttpMessageConverter<T> {
  
      @Override
      public boolean canRead(Class<?> clazz, MediaType mediaType) {
          return false;
      }
  
      @Override
      public boolean canWrite(Class<?> clazz, MediaType mediaType) {
          return new MediaType("text", "csv").equals(mediaType);
      }
  
      @Override
      public List<MediaType> getSupportedMediaTypes() {
          return Arrays.asList(new MediaType("text", "csv"));
      }
  
      @Override
      public T read(Class<? extends T> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
          return null;
      }
  
      @Override
      public void write(T t, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {

        StringBuilder csvBuilder = new StringBuilder();
        Field[] fields = t.getClass().getDeclaredFields();

          processFields(t, fields, csvBuilder);
          csvBuilder.deleteCharAt(csvBuilder.length() - 1); // Remove trailing comma

        // Write CSV to output message
        try (OutputStream outputStream = outputMessage.getBody()) {
          outputStream.write(csvBuilder.toString().getBytes());
        }
        
      }

    private static <T> void processFields(T t, Field[] fields, StringBuilder csvBuilder) {
        // Write field names
        for (Field field : fields) {
          csvBuilder.append(field.getName()).append(",");
        }
        csvBuilder.deleteCharAt(csvBuilder.length() - 1); // Remove trailing comma
        csvBuilder.append("\n");

        // Write field values
        for (Field field : fields) {

          try {
            field.setAccessible(true); // You need this if fields are private
            Object value = field.get(t);
            if(value instanceof City)
              csvBuilder.append(value != null ? ((City)value).getCode() : "").append(",");
            else if(value instanceof Route)
             csvBuilder.append(value != null ? ((Route)value).getOrigin().getCode().concat("-").concat(((Route)value).getDestination().getCode()) : "").append(",");
            else
                csvBuilder.append(value != null ? value : "").append(",");
          } catch (IllegalAccessException e) {
            throw new HttpMessageNotWritableException("Error reading field value", e);
          }
        }
    }
}
