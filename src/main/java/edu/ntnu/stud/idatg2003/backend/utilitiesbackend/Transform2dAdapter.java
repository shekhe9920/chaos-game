package edu.ntnu.stud.idatg2003.backend.utilitiesbackend;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import edu.ntnu.stud.idatg2003.backend.transformations.AffineTransform2D;
import edu.ntnu.stud.idatg2003.backend.transformations.JuliaTransform;
import edu.ntnu.stud.idatg2003.backend.transformations.Transform2D;
import java.lang.reflect.Type;

/**
 * The {@code Transform2DAdapter} class is responsible for serializing and deserializing
 * objects that implement the {@code Transform2D} interface.
 * <p>
 * This adapter handles the polymorphic nature of the {@code Transform2D} interface,
 * allowing different implementations to be correctly serialized and deserialized.
 * </p>
 *
 * @version 0.0.1
 * @since 0.0.8 (The version of Chaos-Game application when introduced)
 */
public class Transform2dAdapter implements
    JsonSerializer<Transform2D>, JsonDeserializer<Transform2D> {



  /**
   * Serializes a {@code Transform2D} object to JSON.
   *
   * @param src The source {@code Transform2D} object to serialize.
   * @param typeOfSrc The actual type (fully qualified class name) of the source object.
   * @param context The context of the serialization process.
   * @return A {@code JsonElement} representing the serialized form of the Transform2D object.
   * @since 0.0.0
   */
  @Override
  public JsonElement serialize(Transform2D src, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("type", src.getClass().getSimpleName());
    jsonObject.add("properties", context.serialize(src));
    return jsonObject;
  }




  /**
   * Deserializes a JSON element into a {@code Transform2D} object.
   *
   * @param json The JSON element to deserialize.
   * @param typeOfT The actual type (fully qualified class name) of the destination object.
   * @param context The context of the deserialization process.
   * @return A {@code Transform2D} object deserialized from the provided JSON element.
   * @throws JsonParseException If the JSON element is not correctly formatted or unknown.
   * @since 0.0.0
   */
  @Override
  public Transform2D deserialize(JsonElement json, Type typeOfT,
      JsonDeserializationContext context) throws JsonParseException {

    JsonObject jsonObject = json.getAsJsonObject();

    if (jsonObject == null) {
      throw new JsonParseException("JsonObject is null");
    }

    // getting the type field from the JSON object:
    JsonElement typeElement = jsonObject.get("type");
    if (typeElement == null) {
      throw new JsonParseException("Missing 'type' field in JSON object: " + jsonObject);
    }

    String type = typeElement.getAsString();
    JsonElement properties = jsonObject.get("properties");

    if (properties == null) {  // a check for the property field
      throw new JsonParseException("Missing 'properties' field in JSON object: " + jsonObject);
    }

    // Deserialize based on the type field:
    return switch (type) {
      case "AffineTransform2D" -> context.deserialize(properties, AffineTransform2D.class);
      case "JuliaTransform" -> context.deserialize(properties, JuliaTransform.class);
      default -> throw new JsonParseException("Unknown element type: " + type);
    };


  }


}
