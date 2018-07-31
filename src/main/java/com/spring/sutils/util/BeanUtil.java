package com.spring.sutils.util;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jiangyan 2015-11-22 16:33:31
 */
public class BeanUtil {

	/**
	 * 将一个 JavaBean 对象转化为一个 Map
	 * 
	 * @param bean
	 *            要转化的JavaBean 对象
	 * @return 转化出来的 Map 对象
	 * @throws IntrospectionException
	 *             如果分析类属性失败
	 * @throws IllegalAccessException
	 *             如果实例化 JavaBean 失败
	 * @throws InvocationTargetException
	 *             如果调用属性的 setter 方法失败
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map convertBeanToMap(Object bean)
			throws IntrospectionException, IllegalAccessException, InvocationTargetException {
		Class type = bean.getClass();
		Map returnMap = new HashMap();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);

		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor descriptor : propertyDescriptors) {
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean);
				if (result != null) {
					returnMap.put(propertyName, result);
				} else {
					returnMap.put(propertyName, "");
				}
			}
		}
		return returnMap;
	}

	/**
	 * 将一个 JavaBean 对象转化为一个 List<NameValuePair>
	 *
	 * @param bean
	 *            要转化的JavaBean 对象
	 * @param nullAndEmptyToEmpty
	 *            空属性值和空字符串是否转换为空字符串返回
	 * @return 转化出来的 List<NameValuePair> 对象
	 * @throws IntrospectionException
	 *             如果分析类属性失败
	 * @throws IllegalAccessException
	 *             如果实例化 JavaBean 失败
	 * @throws InvocationTargetException
	 *             如果调用属性的 setter 方法失败
	 */
	public static List<NameValuePair> convertBeanToNVP(Object bean, Boolean nullAndEmptyToEmpty)
			throws IntrospectionException, InvocationTargetException, IllegalAccessException {
		List<NameValuePair> list = new ArrayList<>();

		Class type = bean.getClass();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);

		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor descriptor : propertyDescriptors) {
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean);
				if (result != null) {
					NameValuePair temp = new BasicNameValuePair(propertyName, StringUtil.obj2Str(result));
					list.add(temp);
				} else {
					if (nullAndEmptyToEmpty) {
						NameValuePair temp = new BasicNameValuePair(propertyName, "");
						list.add(temp);
					}
				}
			}
		}
		return list;
	}

	/**
	 * 将一个 Map 对象转化为一个 JavaBean
	 * 
	 * @param type
	 *            要转化的类型
	 * @param map
	 *            包含属性值的 map
	 * @return 转化出来的 JavaBean 对象
	 * @throws IntrospectionException
	 *             如果分析类属性失败
	 * @throws IllegalAccessException
	 *             如果实例化 JavaBean 失败
	 * @throws InstantiationException
	 *             如果实例化 JavaBean 失败
	 * @throws InvocationTargetException
	 *             如果调用属性的 setter 方法失败
	 */
	@SuppressWarnings("rawtypes")
	public static Object convertMapToBean(Class type, Map map)
			throws IntrospectionException, IllegalAccessException,
			InstantiationException, InvocationTargetException {
		BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
		Object obj = type.newInstance(); // 创建 JavaBean 对象

		// 给 JavaBean 对象的属性赋值
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor descriptor : propertyDescriptors) {
			String propertyName = descriptor.getName();

			if (map.containsKey(propertyName)) {
				// 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
				try {
					Object value = map.get(propertyName);

					Object[] args = new Object[1];
					args[0] = value;

					descriptor.getWriteMethod().invoke(obj, args);
				} catch (Exception ignored) {
					ignored.printStackTrace();
				}
			}
		}
		return obj;
	}

	public static void main(String[] args) throws Exception {

	}


    /**
     * 将一个 JavaBean 对象转化为一个 QueryString
     *
     * @param bean
     *            要转化的JavaBean 对象
     * @param nullAndEmptyToEmpty
     *            空属性值和空字符串是否转换为空字符串返回
     * @return 转化出来的 List<NameValuePair> 对象
     * @throws IntrospectionException
     *             如果分析类属性失败
     * @throws IllegalAccessException
     *             如果实例化 JavaBean 失败
     * @throws InvocationTargetException
     *             如果调用属性的 setter 方法失败
     */
    public static String convertBeanToQueryString(Object bean, Boolean nullAndEmptyToEmpty)
            throws IntrospectionException, InvocationTargetException, IllegalAccessException,
            UnsupportedEncodingException {
        StringBuilder stringBuilder = new StringBuilder("");

        Class type = bean.getClass();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor descriptor : propertyDescriptors) {
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean);
                if (!StringUtil.isEmptyOrNull(result)) {
                    stringBuilder.append(propertyName).append("=")
                            .append(URLEncoder.encode(StringUtil.obj2Str(result), "UTF-8")).append("&");
                } else {
                    if (nullAndEmptyToEmpty) {
                        stringBuilder.append(propertyName).append("=").append("").append("&");
                    }
                }
            }
        }
        return stringBuilder.toString();
    }

}
