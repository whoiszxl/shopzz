package com.whoiszxl.dozer;

import com.github.dozermapper.core.Mapper;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Dozer实体转换工具类
 *
 * @author whoiszxl
 * @date 2021/11/30
 */
public class DozerUtils {

    private Mapper mapper;

    public DozerUtils(Mapper mapper) {
        this.mapper = mapper;
    }

    public Mapper getMapper() {
        return mapper;
    }

    /**
     * 将一个对象转换为另一个对象
     * @param source 源对象
     * @param destinationClass 目标对象的class类型
     * @return
     */
    public <T> T map(Object source, Class<T> destinationClass) {
        if(source == null) {
            return null;
        }

        return mapper.map(source, destinationClass);
    }

    /**
     * 将一个对象转换为另一个对象
     * @param source 源对象
     * @param destination 目标对象
     * @return
     */
    public void map(Object source, Object destination) {
        if (source == null) {
            return;
        }
        mapper.map(source, destination);
    }

    /**
     * 将一个对象转换为另一个对象，并指定mapId区分
     * @param source 源对象
     * @param destination 目标对象
     * @param mapId xml中配置的区分不同映射关系的id
     * @return
     */
    public <T> T map(Object source, Class<T> destinationClass, String mapId) {
        if (source == null) {
            return null;
        }
        return mapper.map(source, destinationClass, mapId);
    }

    /**
     * 将一个对象转换为另一个对象，并指定mapId区分
     * @param source 源对象
     * @param destination 目标对象
     * @param mapId xml中配置的区分不同映射关系的id
     * @return
     */
    public void map(Object source, Object destination, String mapId) {
        if (source == null) {
            return;
        }
        mapper.map(source, destination, mapId);
    }

    /**
     * 将集合转成集合
     * List<A> -->  List<B>
     *
     * @param sourceList       源集合
     * @param destinationClass 待转类型
     * @param <T>
     * @return
     */
    public <T, E> List<T> mapList(Collection<E> sourceList, Class<T> destinationClass) {
        return mapPage(sourceList, destinationClass);
    }


    public <T, E> List<T> mapPage(Collection<E> sourceList, Class<T> destinationClass) {
        if (sourceList == null || sourceList.isEmpty() || destinationClass == null) {
            return Collections.emptyList();
        }
        List<T> destinationList = sourceList.stream()
                .filter(item -> item != null)
                .map((sourceObject) -> mapper.map(sourceObject, destinationClass))
                .collect(Collectors.toList());

        return destinationList;
    }

    public <T, E> Set<T> mapSet(Collection<E> sourceList, Class<T> destinationClass) {
        if (sourceList == null || sourceList.isEmpty() || destinationClass == null) {
            return Collections.emptySet();
        }
        return sourceList.stream().map((sourceObject) -> mapper.map(sourceObject, destinationClass)).collect(Collectors.toSet());
    }

}
