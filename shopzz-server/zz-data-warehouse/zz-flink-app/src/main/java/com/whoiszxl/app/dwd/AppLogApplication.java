package com.whoiszxl.app.dwd;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whoiszxl.constants.KafkaTopicConstants;
import com.whoiszxl.utils.KafkaUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;

public class AppLogApplication {

    public static String groupId = "flink_dwh_group_id";

    public static void main(String[] args) {
        //1. 获取执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        //2. 获取log的数据源
        DataStreamSource<String> kafkaDss = env.addSource(KafkaUtil.getKafkaConsumer(KafkaTopicConstants.ODS_LOG_LOG, groupId));

        //3. 过滤脏数据
        OutputTag<String> outputTag = new OutputTag<String>("dirty_data") {};
        SingleOutputStreamOperator<JSONObject> kafkaJsonProcess = kafkaDss.process(new ProcessFunction<String, JSONObject>() {

            @Override
            public void processElement(String value, Context ctx, Collector<JSONObject> out) throws Exception {
                try {
                    JSONObject jsonObject = JSON.parseObject(value);
                    out.collect(jsonObject);
                } catch (Exception e) {
                    ctx.output(outputTag, value);
                }
            }
        });

        //4. 分流
        OutputTag<String> startTag = new OutputTag<String>("start") {};
        OutputTag<String> displayTag = new OutputTag<String>("display") {};
        SingleOutputStreamOperator<String> pageDs = kafkaJsonProcess.process(new ProcessFunction<JSONObject, String>() {

            @Override
            public void processElement(JSONObject value, Context ctx, Collector<String> out) throws Exception {
                String start = value.getString("appStart");
                if (StringUtils.isNotBlank(start)) {
                    ctx.output(startTag, value.toJSONString());
                } else {
                    out.collect(value.toJSONString());

                    JSONArray displays = value.getJSONArray("displayList");
                    if (CollectionUtils.isNotEmpty(displays)) {
                        String pageId = value.getJSONObject("appPage").getString("currentPageId");

                        for (int i = 0; i < displays.size(); i++) {
                            JSONObject display = displays.getJSONObject(i);

                            //添加页面id
                            display.put("page_id", pageId);

                            //将输出写出到曝光侧输出流
                            ctx.output(displayTag, display.toJSONString());
                        }
                    }
                }
            }
        });

        DataStream<String> startDs = pageDs.getSideOutput(startTag);
        DataStream<String> displayDs = pageDs.getSideOutput(displayTag);

        startDs.print("Start>>>>>>>>>>>");
        pageDs.print("Page>>>>>>>>>>>");
        displayDs.print("Display>>>>>>>>>>>>");

        startDs.addSink(KafkaUtil.getKafkaProducer(KafkaTopicConstants.DWD_START_LOG));
        pageDs.addSink(KafkaUtil.getKafkaProducer(KafkaTopicConstants.DWD_PAGE_LOG));
        displayDs.addSink(KafkaUtil.getKafkaProducer(KafkaTopicConstants.DWD_DISPLAY_LOG));

        try {
            env.execute("AppLogApplication");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
