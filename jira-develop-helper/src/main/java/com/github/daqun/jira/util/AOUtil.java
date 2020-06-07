package com.github.daqun.jira.util;

import com.google.common.collect.Lists;
import net.java.ao.Entity;
import net.java.ao.schema.Ignore;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname AOUtil
 * @Description
 * @Date 2019/3/2 15:34
 * @Created by chenq
 */
public class AOUtil {

    private static List<String> generateFieldsForEntity(Class x) {
        List<String> fieldNames = Lists.newArrayList();

        Method[] methods = x.getMethods();
        for (Method method : methods) {
            String methodName = method.getName();
            Matcher matcher = Pattern.compile("^get|^is").matcher(methodName);
            if (matcher.find() && method.getAnnotation(Ignore.class) == null) {
                // 获取匹配的字符，get/is
                String group = matcher.group();
                String fieldName = methodName.substring(group.length());
                String[] fieldNameSplitArray = StringUtils.splitByCharacterTypeCamelCase(fieldName);
                String enumName = Arrays.stream(fieldNameSplitArray).map(e -> StringUtils.upperCase(e)).reduce((a, b) -> a + "_" + b).get();
                Class<?> returnType = method.getReturnType();
                // Entity应用其他entity时，生成外键名
                if (Entity.class.isAssignableFrom(returnType)) {
                    enumName += "_ID";
                }
                fieldNames.add(enumName);
            }
        }

        return fieldNames;
    }

    /**
     * 生成entity的字段枚举类，并在控制台输出
     * @param clazz entity类
     */
    public static <T extends Entity> void generateEntityEnumLiteral(Class<T> clazz) {
        List<String> strings = generateFieldsForEntity(clazz);
        String format = String.format("enum COLUMN {\n %s \n}", StringUtils.join(strings, " ,") + ";");
        System.out.println(format);
    }



    public static void testReflect(Class x) {
        AnnotatedType[] annotatedInterfaces = x.getAnnotatedInterfaces();
        printArray(annotatedInterfaces);

        Class xx = x.getSuperclass();
        System.out.println(xx);

        Type[] genericInterfaces = x.getGenericInterfaces();
        System.out.println(genericInterfaces[0].getTypeName());

        Class[] classes = x.getDeclaredClasses();
        printArray(classes);
    }


    public static void printArray(Object[] objects) {
        Arrays.stream(objects).forEach(e -> System.out.println(e));
    }
}
