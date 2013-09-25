package org.beh.und.xml;

import org.xmlpull.v1.*;
import org.beh.und.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * 通过pull解析xml
 * @author Arathi
 *
 */
public class PullHandler extends XMLHandler {
	// XmlPullParser parser;
	//public static ;
	
	/**
	 * 初始化怪物信息
	 * @return 成功返回获取到的怪物信息数量
	 */
	@Override
	public int initMonsters() {
		ArrayList<MonsterInfo> monster_info_list=null;
		try {
			// 建立XmlPullParser
			XmlPullParserFactory pullFactory;
			pullFactory = XmlPullParserFactory.newInstance();
			
			MonsterInfo monsterInfo=null;

			XmlPullParser parser = pullFactory.newPullParser();
			// 设置输入xml文件
			String resource = "/monster.xml";
			java.net.URL configFileResource = PullHandler.class
					.getResource(resource);
			URI uri = configFileResource.toURI();
			File file = new File(uri); // new File(resource);
			FileInputStream istream= new FileInputStream(file);
			InputStreamReader reader=new InputStreamReader(istream, "UTF-8");
			
			parser.setInput(reader);
			int eventType = parser.getEventType();
			String name;
//			String resistType;
//			int resistIndex = 0;
//			int actionOrder;
//			String strValue;
//			int intValue;
//			String resistStr = null, resistMaxStr=null;
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					//System.out.println("XML文档开始");
					monster_info_list=new ArrayList<MonsterInfo>();
					monster_info_list.add(null);
					break;
				case XmlPullParser.START_TAG:
					name = parser.getName();
					if (name.equals("monsterlist")==false){
						if (name.equals("monster")){
							monsterInfo=new MonsterInfo();
						}
						else{
							setMonsterParam(monsterInfo, name, parser.nextText());
						}
					}
					break;
				case XmlPullParser.END_TAG:
					name = parser.getName();
					if (name.equals("monster")){
						//monster闭合
						monster_info_list.add(monsterInfo);
					}
					break;
				default:
					break;
				}
				eventType = parser.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			return -1;
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return -2;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return -3;
		} catch (IOException e) {
			e.printStackTrace();
			return -4;
		}
//		System.out.println("载入"+(monster_info_list.size()-1)+"种怪物数据");
//		for (int i=1; i<monster_info_list.size(); i++){
//			System.out.println(monster_info_list.get(i));
//		}
		if (monster_info_list==null){
			return 0;
		}
		int monsterAmount=monster_info_list.size();
		if (monsterAmount>0){
			MonsterInfo.monsterInfoList=new MonsterInfo[monsterAmount];
			monster_info_list.toArray(MonsterInfo.monsterInfoList);
			System.out.println("载入"+(monsterAmount-1)+"种怪物数据");
		}
		return monsterAmount;
	}
	
	/**
	 * 初始化动作信息
	 * @return 成功返回获取到的动作信息数量
	 */
	@Override
	public int initActions(){
		ArrayList<ActionInfo> action_info_list=null;
		try {
			// 建立XmlPullParser
			XmlPullParserFactory pullFactory;
			pullFactory = XmlPullParserFactory.newInstance();
			
			ActionInfo actionInfo=null;

			XmlPullParser parser = pullFactory.newPullParser();
			// 设置输入xml文件
			String resource = "/action.xml";
			java.net.URL configFileResource = PullHandler.class
					.getResource(resource);
			URI uri = configFileResource.toURI();
			File file = new File(uri); // new File(resource);
			FileInputStream istream= new FileInputStream(file);
			InputStreamReader reader=new InputStreamReader(istream, "UTF-8");
			
			parser.setInput(reader);
			int eventType = parser.getEventType();
			String name;
//			int id;
//			String strValue;
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					action_info_list=new ArrayList<ActionInfo>();
					action_info_list.add(null);
					break;
				case XmlPullParser.START_TAG:
					name = parser.getName();
					//System.out.println(name);
					if (name.equals("actionlist")==false){
						if (name.equals("action")){
							//System.out.println("新建Action");
							actionInfo=new ActionInfo();
						}
						else{
							setActionParam(actionInfo, name, parser.nextText());
						}
					}
					break;
				case XmlPullParser.END_TAG:
					name = parser.getName();
					if (name.equals("action")){
						//monster闭合
						action_info_list.add(actionInfo);
					}
					break;
				default:
					break;
				}
				eventType = parser.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			return -1;
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return -2;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return -3;
		} catch (IOException e) {
			e.printStackTrace();
			return -4;
		}
		if (action_info_list==null){
			return 0;
		}
		int actionAmount=action_info_list.size();
		if ( actionAmount>0 ){
			ActionInfo.actionInfoList=new ActionInfo[actionAmount];
			action_info_list.toArray(ActionInfo.actionInfoList);
			System.out.println("载入"+(actionAmount-1)+"种动作数据");
		}
		return actionAmount;
	}

	/**
	 * 初始化动作信息
	 * @return 成功返回获取到的动作信息数量
	 */
	@Override
	public int initSkills(){
		ArrayList<SkillInfo> skill_info_list=null;
		try {
			// 建立XmlPullParser
			XmlPullParserFactory pullFactory;
			pullFactory = XmlPullParserFactory.newInstance();
			
			SkillInfo skillInfo=null;

			XmlPullParser parser = pullFactory.newPullParser();
			// 设置输入xml文件
			String resource = "/skill.xml";
			java.net.URL configFileResource = PullHandler.class
					.getResource(resource);
			URI uri = configFileResource.toURI();
			File file = new File(uri); // new File(resource);
			FileInputStream istream= new FileInputStream(file);
			InputStreamReader reader=new InputStreamReader(istream, "UTF-8");
			
			parser.setInput(reader);
			int eventType = parser.getEventType();
			String name;
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					skill_info_list=new ArrayList<SkillInfo>();
					skill_info_list.add(null);
					break;
				case XmlPullParser.START_TAG:
					name = parser.getName();
					if (name.equals("skilllist")==false){
						if (name.equals("skill")){
							skillInfo=new SkillInfo();
						}
						else{
							String paramValue=parser.nextText();
							boolean isActionParam=setActionParam(skillInfo, name, paramValue);
							if (!isActionParam){
								setSkillParam(skillInfo, name, paramValue);
							}
						}
					}
					break;
				case XmlPullParser.END_TAG:
					name = parser.getName();
					if (name.equals("skill")){
						//monster闭合
						skill_info_list.add(skillInfo);
					}
					break;
				default:
					break;
				}
				eventType = parser.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			return -1;
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return -2;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return -3;
		} catch (IOException e) {
			e.printStackTrace();
			return -4;
		}
		if (skill_info_list==null){
			return 0;
		}
		int skillAmount=skill_info_list.size();
		if ( skillAmount>0 ){
			SkillInfo.skillInfoList=new SkillInfo[skillAmount];
			skill_info_list.toArray(SkillInfo.skillInfoList);
			System.out.println("载入"+(skillAmount-1)+"种技能数据");
		}
		return skillAmount;
	}
}
