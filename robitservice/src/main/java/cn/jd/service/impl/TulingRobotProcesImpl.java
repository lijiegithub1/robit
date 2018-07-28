package cn.jd.service.impl;

import cn.jd.pojo.TextContent.TextContentMessage;
import cn.jd.pojo.TextContent.TulingReq;
import cn.jd.pojo.TextContent.TulingResp;
import cn.jd.service.MusicProcess;
import cn.jd.service.utils.HttpUtils;
import cn.jd.service.utils.ParseUtils;
import com.alibaba.druid.util.StringUtils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * Created by lijie32 on 2018/7/28.
 */
@Service
public class TulingRobotProcesImpl extends BaseProcessImpl implements MusicProcess {
    private static Logger logger = LogManager.getLogger(TulingRobotProcesImpl.class);
    private String apiKey = "a091b12268a948c6987e17bb10c0a70d";
    private String userId ="7410e3a4dcada0a2";
    private static final String  URL= "http://openapi.tuling123.com/openapi/api/v2";
    @Override
    public String process(Map<String, String> parseMap) {
        String textXml = null;
        TextContentMessage textContent= new TextContentMessage();
        init(parseMap);
        try {

            String requestParams = buildRequestParsms(parseMap);
            Map<String, Object> result = HttpUtils.requestPost(URL, requestParams);
            String text = "啥！啥！ 啥！ 你说的是啥！！";
            if(StringUtils.equals("200",result.get("status").toString())){//状态正常
                //接收数据
                String response = (String)(result.get("response"));
                TulingResp tulingResp = JSON.parseObject(response, TulingResp.class);
                logger.info("tulingResp : " + tulingResp.toString());
                //组装发送消息对象
                String resultType = tulingResp.getResults().get(0).getResultType();
                if(StringUtils.equals("text",resultType)){//返回的为文本
                     text = tulingResp.getResults().get(0).getValues().getText();

                }

            }
            //向其中添加参数
            textContent.setCreateTime(createTime);
            textContent.setFromUserName(toUserName);
            textContent.setMsgType(ParseUtils.REQ_MESSAGE_TYPE_TEXT);
            textContent.setToUserName(fromUserName);
            textContent.setContent(text);
            //将结果转换成xml
            textXml = ParseUtils.textMessageToXml(textContent);
        }catch (Exception e){
            logger.error("图灵机器发生异常");
            textContent.setCreateTime(createTime);
            textContent.setFromUserName(toUserName);
            textContent.setMsgType(ParseUtils.REQ_MESSAGE_TYPE_TEXT);
            textContent.setToUserName(fromUserName);
            textContent.setContent("对不起，此功能出现异常");
        }

        return textXml;

    }

    /**
     * 构建请求的参数
     * @param parseMap
     * @return
     */
    public String buildRequestParsms(Map<String, String> parseMap) throws IOException {
        TulingReq tulingReq = new TulingReq();
        TulingReq.Perception perception = new TulingReq.Perception();
        /*先只支持一种*/
        TulingReq.InputText inputText = new TulingReq.InputText();
        inputText.setText(parseMap.get("Content"));
        perception.setInputText(inputText);

        tulingReq.setPerception(perception);

        tulingReq.setReqType(Integer.valueOf(parseMap.get("reqType")).intValue());
        TulingReq.UserInfo userInfo = new TulingReq.UserInfo();
        userInfo.setApiKey(apiKey);
        userInfo.setUserId(userId);
        tulingReq.setUserInfo(userInfo);

        String req =  JSON.toJSONString(tulingReq);

        logger.info("req" + req);
        return req;
    }
}
