package com.whoiszxl.function;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.ververica.cdc.debezium.DebeziumDeserializationSchema;
import io.debezium.data.Envelope;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.util.Collector;
import org.apache.kafka.connect.data.Field;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;

import java.util.List;

/**
 * 自定义反序列化方案
 */
public class CustomDebeziumDeserializationSchema implements DebeziumDeserializationSchema<String> {

    @Override
    public void deserialize(SourceRecord sourceRecord, Collector<String> collector) throws Exception {
        //1. 获取数据库名和表名
        String topic = sourceRecord.topic();
        String[] fields = topic.split("\\.");
        String dbName = fields[1];
        String tableName = fields[2];

        //2. 获取更改前的值
        Struct value = (Struct) sourceRecord.value();
        Struct before = value.getStruct("before");
        JSONObject beforeJson = extractData(before);

        //3. 获取修改后的值
        Struct after = value.getStruct("after");
        JSONObject afterJson = extractData(after);

        //4. 获取操作类型
        Envelope.Operation operation = Envelope.operationFor(sourceRecord);
        String type = operation.toString().toLowerCase();
        if(Envelope.Operation.CREATE.toString().toLowerCase().equals(type)) {
            type = "insert";
        }

        JSONObject result = new JSONObject();
        result.put("dbName", dbName);
        result.put("tableName", tableName);
        result.put("before", beforeJson);
        result.put("after", afterJson);
        result.put("type", type);
        collector.collect(result.toString());
    }

    private JSONObject extractData(Struct structData) {
        JSONObject beforeJson = new JSONObject();
        if(structData != null) {
            Schema beforeSchema = structData.schema();
            List<Field> beforeFieldList = beforeSchema.fields();

            for (Field field : beforeFieldList) {
                Object beforeValue = structData.get(field);
                beforeJson.put(field.name(), beforeValue);
            }
        }

        return beforeJson;
    }

    @Override
    public TypeInformation<String> getProducedType() {
        return BasicTypeInfo.STRING_TYPE_INFO;
    }
}
