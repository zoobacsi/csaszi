package hu.csaszi.gameengine.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class PropsUtil {

    private final static ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
    private GameProperties gameProperties;
    private GameProperties defaultGameProperties;
    private static PropsUtil instance;

    private PropsUtil(){

        try {
            gameProperties = objectMapper.readValue(IOUtil.getFile("game-properties.yaml"), GameProperties.class);
            defaultGameProperties = gameProperties.clone();

            //System.out.println(ReflectionToStringBuilder.toString(gameProperties, ToStringStyle.MULTI_LINE_STYLE));

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new Error("Cannot load properties file: game-properties.yaml");
        }
    }

    public static GameProperties getProperties (){

        if(instance == null) {
            instance = new PropsUtil();
        }

        return instance.gameProperties;
    }
}
