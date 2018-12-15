package utils;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class BeanHandle {

    private String paramName;
    private Object value;
    private Object lastObject;
    private Object curObject;
    private Field field;

    public BeanHandle(Object obj, String paramName, Object value) {
        this.paramName = paramName;
        this.value = value;
        this.lastObject = obj;
        this.curObject = obj;
        handleString();
    }

    /**
     *  通过属性字符串链获取对应Field
     */
    public void handleString() {
        try {
            int index = 1;
            String[] attrs = paramName.split("\\.");
            this.field = ReflectUtil.getFiled(this.curObject.getClass(), attrs[index]);

            int i = attrs.length - 2;

            while(i > 0){
                this.lastObject = this.curObject;
                curObject = ReflectUtil.getValue(lastObject, field.getName());
                if (curObject ==null){
                    this.curObject = this.field.getType().newInstance();
                    ReflectUtil.setValue(lastObject, attrs[index] , this.curObject);
                }
                this.field = ReflectUtil.getFiled(this.curObject.getClass(), attrs[++index]);
                i--;
            }
            setFiledValue(curObject, field, value);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     *  类型转换
     */
    private void setFiledValue(Object obj, Field field, Object value){
        // 如果是数组类型
        try {
            String type = field.getType().getSimpleName();
            field.setAccessible(true);
            if (value.getClass().isArray()){
                String[] strings = (String[])value;
                if (type.equals("String[]")){
                    field.set(obj, value);
                }else if (type.equals("Double[]") || type.equals("double[]")){
                    Double[] doubles = new Double[strings.length];
                    for (int i=0; i < strings.length; i++){
                        doubles[i] = Double.parseDouble(strings[i]);
                    }
                    field.set(obj, doubles);
                }else if (type.equals("Integer[]") || type.equals("int[]")){
                    Integer[] integers = new Integer[strings.length];
                    for (int i=0; i < strings.length; i++){
                        integers[i] = Integer.parseInt(strings[i]);
                    }
                    field.set(obj, integers);
                }else if (type.equals("Short[]") || type.equals("short[]")){
                    Short[] values = new Short[strings.length];
                    for (int i=0; i < strings.length; i++){
                        values[i] = Short.parseShort(strings[i]);
                    }
                    field.set(obj, values);
                }else if (type.equals("Float[]") || type.equals("float[]")){
                    Float[] values = new Float[strings.length];
                    for (int i=0; i < strings.length; i++){
                        values[i] = Float.parseFloat(strings[i]);
                    }
                    field.set(obj, values);
                }
                else if (type.equals("Byte[]") || type.equals("byte[]")){
                    Byte[] values = new Byte[strings.length];
                    for (int i=0; i < strings.length; i++){
                        values[i] = Byte.parseByte(strings[i]);
                    }
                    field.set(obj, values);
                }
                else if (type.equals("Long[]") || type.equals("long[]")){
                    Long[] values = new Long[strings.length];
                    for (int i=0; i < strings.length; i++){
                        values[i] = Long.parseLong(strings[i]);
                    }
                    field.set(obj, values);
                }
                else if (type.equals("Boolean[]") || type.equals("boolean[]")){
                    Boolean[] values = new Boolean[strings.length];
                    for (int i=0; i < strings.length; i++){
                        values[i] = Boolean.parseBoolean(strings[i]);
                    }
                    field.set(obj, values);
                }
            }else{
                String val = (String)value;
                if (type.equals("String")){
                    ReflectUtil.setValue(obj, field.getName(), value);
                }else if (type.equals("Double") || type.equals("double")){
                    ReflectUtil.setValue(obj, field.getName(), Double.parseDouble(val));
                }else if (type.equals("Integer") || type.equals("int")){
                    ReflectUtil.setValue(obj, field.getName(), Integer.parseInt(val));
                }else if (type.equals("Short") || type.equals("short")){
                    ReflectUtil.setValue(obj, field.getName(), Short.parseShort(val));
                }else if (type.equals("Float") || type.equals("float")){
                    ReflectUtil.setValue(obj, field.getName(), Float.parseFloat(val));
                }else if (type.equals("Byte") || type.equals("byte")){
                    ReflectUtil.setValue(obj, field.getName(), Byte.parseByte(val));
                }else if (type.equals("Long") || type.equals("long")){
                    ReflectUtil.setValue(obj, field.getName(), Long.parseLong(val));
                }else if (type.equals("Boolean") || type.equals("boolean")){
                    ReflectUtil.setValue(obj, field.getName(), Boolean.parseBoolean(val));
                }else if (type.equals("Date")){
                    ReflectUtil.setValue(obj, field.getName(),
                            new SimpleDateFormat("yyyy-MM-dd").parse(val));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
