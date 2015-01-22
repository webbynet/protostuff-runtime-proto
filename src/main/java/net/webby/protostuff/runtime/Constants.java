package net.webby.protostuff.runtime;

public final class Constants {

  public static final int ID_BOOL = 1, ID_BYTE = 2, ID_CHAR = 3, ID_SHORT = 4, 
      ID_INT32 = 5, ID_INT64 = 6, ID_FLOAT = 7, ID_DOUBLE = 8, 
      ID_STRING = 9, ID_BYTES = 10, ID_BYTE_ARRAY = 11, 
      ID_BIGDECIMAL = 12, ID_BIGINTEGER = 13, ID_DATE = 14,
      ID_ARRAY = 15, // 1-15 is encoded as 1 byte on protobuf and protostuff format
      ID_OBJECT = 16, 
      ID_ARRAY_MAPPED = 17, 
      ID_CLASS = 18, 
      ID_CLASS_MAPPED = 19, 
      ID_CLASS_ARRAY = 20, 
      ID_CLASS_ARRAY_MAPPED = 21, 
      
      ID_ENUM_SET = 22, 
      ID_ENUM_MAP = 23, 
      ID_ENUM = 24, 
      ID_COLLECTION = 25, 
      ID_MAP = 26, 
      
      ID_POLYMORPHIC_COLLECTION = 28, 
      ID_POLYMORPHIC_MAP = 29, 
      ID_DELEGATE = 30, 
      ID_THROWABLE = 52, 
      
      // pojo fields limited to 126 if not explicitly using @Tag annotations
      ID_POJO = 127;
  
  public static final int ID_ARRAY_LEN = 3;
  public static final int ID_ARRAY_DIMENSION = 2;
  public static final int ID_ARRAY_VALUE = 1;
  
  public static final int ID_MAP_ENTRY = 1;
  public static final int ID_MAP_KEY = 1;
  public static final int ID_MAP_VALUE = 2;
}
