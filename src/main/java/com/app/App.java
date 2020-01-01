package com.app;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

import com.utils.EchartsUtil;
import com.utils.FreemarkerUtil;
import org.apache.http.client.ClientProtocolException;

import com.alibaba.fastjson.JSON;

import freemarker.template.TemplateException;
import org.springframework.util.ResourceUtils;
import sun.misc.BASE64Decoder;
//https://www.cnblogs.com/ZeroMZ/p/11481813.html
//phantomjs /opt/echartsconvert/echarts-convert.js -s -p 6666
public class App {
    public static void main(String[] args) throws ClientProtocolException, IOException, TemplateException {
        // 变量
        String title = "水果";
        String[] categories = new String[] { "苹果", "香蕉", "西瓜" };
        int[] values = new int[] { 3, 2, 1 };

        // 模板参数
        HashMap<String, Object> datas = new HashMap<>();
        datas.put("categories", JSON.toJSONString(categories));
        datas.put("values", JSON.toJSONString(values));
        datas.put("title", title);



        // 生成option字符串
//        String option = FreemarkerUtil.generateString("option.ftl", "/", datas);

        String option = "{\n" +
                "    \"calculable\": true,\n" +
                "    \"toolbox\": {\n" +
                "        \"show\": true,\n" +
                "        \"feature\": {\n" +
                "            \"restore\": {\n" +
                "                \"show\": true,\n" +
                "                \"title\": \"还原\"\n" +
                "            },\n" +
                "            \"magicType\": {\n" +
                "                \"show\": true,\n" +
                "                \"title\": {\n" +
                "                    \"line\": \"折线图切换\",\n" +
                "                    \"stack\": \"堆积\",\n" +
                "                    \"bar\": \"柱形图切换\",\n" +
                "                    \"tiled\": \"平铺\"\n" +
                "                },\n" +
                "                \"type\": [\"line\", \"bar\"]\n" +
                "            },\n" +
                "            \"dataView\": {\n" +
                "                \"show\": true,\n" +
                "                \"title\": \"数据视图\",\n" +
                "                \"readOnly\": false,\n" +
                "                \"lang\": [\"Data View\", \"close\", \"refresh\"]\n" +
                "            },\n" +
                "            \"mark\": {\n" +
                "                \"show\": true,\n" +
                "                \"title\": {\n" +
                "                    \"mark\": \"辅助线开关\",\n" +
                "                    \"markClear\": \"清空辅助线\",\n" +
                "                    \"markUndo\": \"删除辅助线\"\n" +
                "                },\n" +
                "                \"lineStyle\": {\n" +
                "                    \"color\": \"#1e90ff\",\n" +
                "                    \"type\": \"dashed\",\n" +
                "                    \"width\": 2\n" +
                "                }\n" +
                "            },\n" +
                "            \"saveAsImage\": {\n" +
                "                \"show\": true,\n" +
                "                \"title\": \"保存为图片\",\n" +
                "                \"type\": \"png\",\n" +
                "                \"lang\": [\"点击保存\"]\n" +
                "            }\n" +
                "        }\n" +
                "    },\n" +
                "    \"tooltip\": {\n" +
                "        \"trigger\": \"axis\",\n" +
                "        \"formatter\": \"Temperature : \\u003cbr/\\u003e{b}km : {c}°C\"\n" +
                "    },\n" +
                "    \"legend\": {\n" +
                "        \"data\": [\"高度(km)与气温(°C)变化关系\"]\n" +
                "    },\n" +
                "    \"xAxis\": [{\n" +
                "        \"type\": \"value\",\n" +
                "        \"axisLabel\": {\n" +
                "            \"formatter\": \"{value} °C\"\n" +
                "        }\n" +
                "    }],\n" +
                "    \"yAxis\": [{\n" +
                "        \"type\": \"category\",\n" +
                "        \"boundaryGap\": false,\n" +
                "        \"axisLine\": {\n" +
                "            \"onZero\": false\n" +
                "        },\n" +
                "        \"axisLabel\": {\n" +
                "            \"formatter\": \"{value} km\"\n" +
                "        },\n" +
                "        \"data\": [\n" +
                "            0, 10, 20, 30, 40, 50, 60, 70, 80\n" +
                "        ]\n" +
                "    }],\n" +
                "    \"series\": [{\n" +
                "        \"smooth\": true,\n" +
                "        \"name\": \"高度(km)与气温(°C)变化关系\",\n" +
                "        \"type\": \"line\",\n" +
                "        \"itemStyle\": {\n" +
                "            \"normal\": {\n" +
                "                \"lineStyle\": {\n" +
                "                    \"shadowColor\": \"rgba(0,0,0,0.4)\"\n" +
                "                }\n" +
                "            },\n" +
                "            \"emphasis\": {}\n" +
                "        },\n" +
                "        \"data\": [\n" +
                "            15, -50, -56.5, -46.5, -22.1, -2.5, -27.7, -55.7, -76.5\n" +
                "        ]\n" +
                "    }]\n" +
                "}";
        // 根据option参数
        String base64 = EchartsUtil.generateEchartsBase64(option);

        System.out.println("BASE64:" + base64);
        generateImage(base64, "E:/test2223333.png");
    }

    public static void generateImage(String base64, String path) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        try (OutputStream out = new FileOutputStream(path)){
            // 解密
            byte[] b = decoder.decodeBuffer(base64);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            out.write(b);
            out.flush();
        }
    }
}
