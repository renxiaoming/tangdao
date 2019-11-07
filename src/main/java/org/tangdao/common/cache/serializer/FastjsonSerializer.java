/**
 *
 */
package org.tangdao.common.cache.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 使用 fastjson 进行对象的 JSON 格式序列化
 * 
 * @author Ryan Ru(ruyangit@gmail.com)
 */
public class FastjsonSerializer implements Serializer {

    @Override
    public String name() {
        return "fastjson";
    }

    @Override
    public byte[] serialize(Object obj) {
        return JSON.toJSONString(obj, SerializerFeature.WriteClassName).getBytes();
    }

    @Override
    public Object deserialize(byte[] bytes) {
        return JSON.parse(new String(bytes), Feature.SupportAutoType);
    }

}
