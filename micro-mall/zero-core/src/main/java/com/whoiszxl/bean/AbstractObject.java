package com.whoiszxl.bean;

import com.whoiszxl.enums.CloneDirectionEnum;
import com.whoiszxl.utils.BeanCopierUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 抽象基类
 *
 * @author whoiszxl
 * @date 2021/3/17
 */
@Slf4j
public class AbstractObject {

    public static final String VO = "VO";
    public static final String DTO = "DTO";

    /**
     * 浅克隆
     * @param clazz 目标类
     * @return 目标对象
     * @throws Exception
     */
    public <T> T clone(Class<T> clazz) {
        T target = null;
        try {
            target = clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            log.error("对象clone失败：", e);
        }
        BeanCopierUtils.copyProperties(this, target);
        return target;
    }

    /**
     * 浅克隆
     * @param target 目标对象
     * @return 目标对象
     * @throws Exception
     */
    public <T> T clone(T target) throws Exception {
        BeanCopierUtils.copyProperties(this, target);
        return target;
    }



    /**
     * 深度克隆
     * @param clazz
     * @param direction
     * @return
     * @throws Exception
     */
    public <T> T clone(Class<T> clazz, Integer cloneDirection) throws Exception {
        // 先完成基本字段的浅克隆
        T target = clazz.getDeclaredConstructor().newInstance();
        BeanCopierUtils.copyProperties(this, target);

        // 完成所有List类型的深度克隆
        // CategoryDTO
        Class<?> thisClazz = this.getClass();

        Field[] fields = thisClazz.getDeclaredFields();

        for(Field field : fields) {
            field.setAccessible(true);

            //不是list类型直接跳出
            if(field.getType() != List.class) {
                continue;
            }

            //为空直接跳出
            List<?> list = (List<?>) field.get(this);
            if(ObjectUtils.isEmpty(list)) {
                continue;
            }

            // 获取List集合中的泛型类型
            // RelationDTO
            Class<?> listGenericClazz = getListGenericType(field);
            // 获取要克隆的目标类型
            // 假设CloneDirection是反向，此时获取到的就是RelationVO

            Class<?> cloneTargetClazz = getCloneTargetClazz(listGenericClazz, cloneDirection);
            // 将list集合克隆到目标list集合中去
            List clonedList = new ArrayList();
            cloneList(list, clonedList, cloneTargetClazz, cloneDirection);

            // 获取设置克隆好的list的方法名称
            // setRelations
            Method setFieldMethod = getSetCloneListFieldMethodName(field, clazz);
            setFieldMethod.invoke(target, clonedList);
            // target是CategoryVO对象，此时就是调用CategoryVO的setRelations方法，
            // 将克隆好的List<CategoryVO>给设置进去
        }

        return target;
    }

    /**
     * 将一个list克隆到另外一个list
     * @param sourceList
     * @param targetList
     * @param cloneTargetClazz
     * @param cloneDirection
     * @throws Exception
     */
    private void cloneList(List sourceList, List targetList,
                           Class cloneTargetClazz, Integer cloneDirection) throws Exception {
        for(Object object : sourceList) {
            AbstractObject targetObject = (AbstractObject) object;
            // 将集合中的RelationDTO，调用其clone()方法，将其往RelationVO去克隆
            AbstractObject clonedObject = (AbstractObject) targetObject.clone(
                    cloneTargetClazz, cloneDirection);
            // RelationVO的集合
            targetList.add(clonedObject);
        }
    }

    /**
     * 获取list集合的泛型类型
     * @param field 字段
     * @return
     * @throws Exception
     */
    private Class<?> getListGenericType(Field field) throws Exception {
        // genericType = List<RelationDTO>，不是List
        Type genericType = field.getGenericType();
        if(genericType instanceof ParameterizedType){
            ParameterizedType parameterizedType = (ParameterizedType) genericType;
            return (Class<?>)parameterizedType.getActualTypeArguments()[0];
        }
        return null;
    }

    /**
     * 获取目标类名
     * @param className
     * @param cloneDirection
     * @return
     * @throws Exception
     */
    private Class<?> getCloneTargetClazz(Class<?> clazz, Integer cloneDirection) throws Exception {
        String cloneTargetClassName = null;

        // ReflectionDTO
        String className = clazz.getName();

        if(cloneDirection.equals(CloneDirectionEnum.POSITIVE.getCode())) {
            if(className.endsWith(VO)) {
                cloneTargetClassName = className.substring(0, className.length() - 2) + "DTO";
            } else if(className.endsWith(DTO)) {
                cloneTargetClassName = className.substring(0, className.length() - 3);
            }
        }

        if(cloneDirection.equals(CloneDirectionEnum.REVERSE.getCode())) {
            if(!className.endsWith(DTO) && !className.endsWith(VO)) {
                cloneTargetClassName =  className + "DTO";
            } else if(className.endsWith(DTO)) {
                cloneTargetClassName = className.substring(0, className.length() - 3) + "VO";
            }
        }

        return Class.forName(cloneTargetClassName);
    }

    /**
     * 获取设置克隆好的list的方法名称
     * @param className
     * @param cloneDirection
     * @return
     * @throws Exception
     */
    private Method getSetCloneListFieldMethodName(Field field, Class<?> clazz) throws Exception {
        String name = field.getName();
        String setMethodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);

        Method setFieldMethod = null;

        for(Method method : clazz.getDeclaredMethods()) {
            if(method.getName().equals(setMethodName)) {
                setFieldMethod = method;
                break;
            }
        }

        return setFieldMethod;
    }

}
