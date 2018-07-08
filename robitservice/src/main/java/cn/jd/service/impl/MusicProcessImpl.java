package cn.jd.service.impl;


import cn.jd.pojo.music.Music;
import cn.jd.pojo.music.MusicMessage;
import cn.jd.service.MusicProcess;
import cn.jd.service.utils.ParseUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MusicProcessImpl extends BaseProcessImpl implements MusicProcess {
	//private static Logger LOG=Logger.getLogger(MusicProcess.class);
	private static Logger logger = LogManager.getLogger(MusicProcessImpl.class);
	@Override
	public String process(Map<String, String> parseMap) {
		init(parseMap);
		//1、接受参数并继续打印 
		//2 写逻辑  拼装参数 
		
		//3把对象转成 xml数据
		String Content = parseMap.get("Content");
		System.out.println("Content==>" + Content);
		MusicMessage music= new MusicMessage();
		//向其中添加参数 
		music.setCreateTime(createTime);
		music.setFromUserName(toUserName);
		music.setMsgType(ParseUtils.RESP_MESSAGE_TYPE_MUSIC);
		music.setToUserName(fromUserName);
		Music mus = new Music();
		mus.setDescription("但愿余生都是你这首歌");
		mus.setHQMusicUrl("https://y.qq.com/n/yqq/song/003HCCx43yOBx3.html");
		mus.setMusicUrl("https://y.qq.com/n/yqq/song/003HCCx43yOBx3.html");
		mus.setThumbMediaId("Za20Y5967zPRZeuftVFc3sahACWQ02HpIfvkfxkalsXPIk_8PN3GeYciqJ9FhpiQ");
		mus.setTitle("但愿余生都是你");
		music.setMusic(mus);
		//将结果转换成xml
		String musicXml = ParseUtils.textMessageToXml(music);
		logger.info("MusicToXml ===>" + musicXml);
		return musicXml;
	}
	
}
