package cn.jd.service.impl;

import cn.jd.pojo.TuWen.TuWenMessage;
import cn.jd.service.MusicProcess;
import cn.jd.service.utils.ChineseToPinYin;
import cn.jd.service.utils.HttpUtils;
import cn.jd.service.utils.ParseUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lijie32 on 2018/7/28.
 */
@Service
public class WeatherProcesImpl extends BaseProcessImpl implements MusicProcess {
    private static Logger logger = LogManager.getLogger(WeatherProcesImpl.class);
    private String TIANQI_DAILY_WEATHER_URL = "https://api.seniverse.com/v3/weather/daily.json";

    private String TIANQI_API_SECRET_KEY = "yfcai0lhz2acyjy0"; //

    private String TIANQI_API_USER_ID = "U5D61A950F"; //

    @Override
    public String process(Map<String, String> parseMap) {
        init(parseMap);
        String content = parseMap.get("Content");
        String textXml = null;
        TuWenMessage  tuWenMessage = new TuWenMessage();
        try {
            if(ChineseToPinYin.isChinese(content)){//是中文
                content = ChineseToPinYin.getPingYin(content);  //转成拼音
            }
            String  url = generateGetDiaryWeatherURL(content,"zh-Hans","c","0","3");
            String weatherResult = HttpUtils.requestGet(url);
            buildTuWenMessage(tuWenMessage,weatherResult);

        }catch (Exception e){
            logger.error("调用天气发生异常 ,具体错误信息 ：" + e.getMessage() );
            buildTuWenMessage(tuWenMessage,e.getMessage());
        }
        //将结果转换成xml
        textXml =tuWenMessage.toXml();
        return textXml;
    }


    public void buildTuWenMessage(TuWenMessage  tuWenMessage,String weatherResult){
        tuWenMessage.setCreateTime(createTime);
        tuWenMessage.setFromUserName(toUserName);
        tuWenMessage.setToUserName(fromUserName);
        tuWenMessage.setMsgType(ParseUtils.RESP_MESSAGE_TYPE_NEWS);
        List<TuWenMessage.Item> items = new ArrayList<TuWenMessage.Item>();
        TuWenMessage.Item item = new TuWenMessage.Item();
        StringBuilder sb = new StringBuilder();
        if("暂不支持该地区，请重新输入".equals(weatherResult)){
            item.setTitle("天气预报");
            item.setUrl("http://www.weather.com.cn/weather1d/101010100.shtml");
            sb.append(weatherResult);
            item.setDescription(sb.toString());
            /*item.setPicUrl("E:\\心知天气\\"+dailWeather.get("code_day")+".png");*/
            item.setPicUrl("http://img05.tooopen.com/images/20140628/sy_64076786565.jpg");
            items.add(item);
        }else{
            JSONObject jsonObject = JSONObject.parseObject(weatherResult);
            JSONArray dailyWeathers = (JSONArray)((JSONObject)((JSONArray)jsonObject.get("results")).get(0)).get("daily");
            JSONObject locationWeather = (JSONObject)((JSONObject)((JSONArray)jsonObject.get("results")).get(0)).get("location");
            for (int i= 0; i< dailyWeathers.size();i++ ) {
                JSONObject dailWeather = (JSONObject)dailyWeathers.get(i);
                sb.append(dailWeather.get("date")).append("\n");
                sb.append("白天天气 : " + dailWeather.get("text_day")).append("\n");
                sb.append("夜间天气 : " + dailWeather.get("text_night")).append("\n");
                sb.append("温度: 最低 " +dailWeather.get("low") + "℃  最高 " +dailWeather.get("high")+"℃ ").append("\n");
                sb.append("风向 : " + dailWeather.get("wind_direction") + "   风速为 : "+ dailWeather.get("wind_speed") + "km/h").append("\n");
                item.setTitle(locationWeather.get("name") +  " 天气预报");
                item.setUrl("http://www.weather.com.cn/weather1d/101010100.shtml");
                item.setDescription(sb.toString());
            /*item.setPicUrl("E:\\心知天气\\"+dailWeather.get("code_day")+".png");*/
                item.setPicUrl("http://img05.tooopen.com/images/20140628/sy_64076786565.jpg");
                items.add(item);
            }
        }
        tuWenMessage.setItems(items);
        tuWenMessage.setArticleCount("1");
    }


    /**
     * Generate the URL to get diary weather
     * @param location
     * @param language
     * @param unit
     * @param start
     * @param days
     * @return
     */
    public String generateGetDiaryWeatherURL(
            String location,
            String language,
            String unit,
            String start,
            String days
    )  throws SignatureException, UnsupportedEncodingException {
        String timestamp = String.valueOf(new Date().getTime());
        String params = "ts=" + timestamp + "&ttl=30&uid=" + TIANQI_API_USER_ID;
        String signature = URLEncoder.encode(generateSignature(params, TIANQI_API_SECRET_KEY), "UTF-8");
        return TIANQI_DAILY_WEATHER_URL + "?" + params + "&sig=" + signature + "&location=" + location + "&language=" + language + "&unit=" + unit + "&start=" + start + "&days=" + days;
    }




    /**
     * Generate HmacSHA1 signature with given data string and key
     * @param data
     * @param key
     * @return
     * @throws SignatureException
     */
    private String generateSignature(String data, String key) throws SignatureException {
        String result;
        try {
            // get an hmac_sha1 key from the raw key bytes
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA1");
            // get an hmac_sha1 Mac instance and initialize with the signing key
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            // compute the hmac on input data bytes
            byte[] rawHmac = mac.doFinal(data.getBytes("UTF-8"));
            result = new sun.misc.BASE64Encoder().encode(rawHmac);
        }
        catch (Exception e) {
            throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
        }
        return result;
    }

    public static void main(String[] args) {
        String json = " {\"results\":[{\"location\":{\"id\":\"WX4FBXXFKE4F\",\"name\":\"北京\",\"country\":\"CN\",\"path\":\"北京,北京,中国\",\"timezone\":\"Asia/Shanghai\",\"timezone_offset\":\"+08:00\"},\"daily\":[{\"date\":\"2018-07-30\",\"text_day\":\"晴\",\"code_day\":\"0\",\"text_night\":\"晴\",\"code_night\":\"1\",\"high\":\"34\",\"low\":\"25\",\"precip\":\"\",\"wind_direction\":\"东南\",\"wind_direction_degree\":\"135\",\"wind_speed\":\"10\",\"wind_scale\":\"2\"},{\"date\":\"2018-07-31\",\"text_day\":\"多云\",\"code_day\":\"4\",\"text_night\":\"多云\",\"code_night\":\"4\",\"high\":\"34\",\"low\":\"26\",\"precip\":\"\",\"wind_direction\":\"南\",\"wind_direction_degree\":\"180\",\"wind_speed\":\"10\",\"wind_scale\":\"2\"}],\"last_update\":\"2018-07-29T11:00:00+08:00\"}]}";
        JSONObject jsonObject = JSONObject.parseObject(json);
        JSONArray dailyWeathers = (JSONArray)((JSONObject)((JSONArray)jsonObject.get("results")).get(0)).get("daily");
        StringBuilder sb = new StringBuilder();
        for (int i= 0; i< dailyWeathers.size();i++ ) {
            JSONObject dailWeather = (JSONObject)dailyWeathers.get(i);
            sb.append(dailWeather.get("date")).append("\n");
            sb.append("白天天气 : " + dailWeather.get("text_day")).append("\n");
            sb.append("夜间天气 : " + dailWeather.get("text_night")).append("\n");
            sb.append("温度: 最低 " +dailWeather.get("low") + "℃  最高 " +dailWeather.get("high")+"℃ ").append("\n");
            sb.append("风向 : " + dailWeather.get("wind_direction") + "   风速为 : "+ dailWeather.get("wind_speed") + "km/h").append("\n");
        }
        System.out.println("最终的结果 : " + sb.toString()) ;

    }

}
