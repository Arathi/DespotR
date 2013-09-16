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
public class PullHandler {
	// XmlPullParser parser;
	//public static ;


	/**
	 * 初始化怪物信息
	 * @return 成功返回获取到的怪物信息数量
	 */
	public static int initMonsters() {
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
			String resistType;
			int resistIndex = 0;
			int actionOrder;
			String strValue;
			int intValue;
			String resistStr = null, resistMaxStr=null;
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					//System.out.println("XML文档开始");
					monster_info_list=new ArrayList<MonsterInfo>();
					monster_info_list.add(null);
					break;
				case XmlPullParser.START_TAG:
					name = parser.getName();
					//System.out.println("标签" + name + "开始");
					if (name.equals("monster")){
						monsterInfo=new MonsterInfo();
					}
					if (name.equals("id")){
						strValue = parser.nextText().substring(3);
						intValue = Util.toInteger(strValue);
						monsterInfo.setId(intValue);
					}
					if (name.endsWith("name")){
						strValue = parser.nextText();
						monsterInfo.setName(strValue);
					}
					if (name.equals("hp")){
						intValue = Util.toInteger(parser.nextText());
						monsterInfo.setHp(intValue);
					}
					if (name.equals("mp")){
						intValue = Util.toInteger(parser.nextText());
						monsterInfo.setMp(intValue);
					}
					if (name.equals("atk")){
						intValue = Util.toInteger(parser.nextText());
						monsterInfo.setAtk(intValue);
					}
					if (name.equals("def")){
						intValue = Util.toInteger(parser.nextText());
						monsterInfo.setDef(intValue);
					}
					if (name.equals("agi")){
						intValue = Util.toInteger(parser.nextText());
						monsterInfo.setAgi(intValue);
					}
					if (name.equals("jink")){
						intValue = Util.toInteger(parser.nextText());
						monsterInfo.setJink(intValue);
					}
					if (name.equals("inte")){
						intValue = Util.toInteger(parser.nextText());
						monsterInfo.setInte(intValue);
					}
					if (name.equals("exp")){
						intValue = Util.toInteger(parser.nextText());
						monsterInfo.setExp(intValue);
					}
					if (name.equals("gold")){
						intValue = Util.toInteger(parser.nextText());
						monsterInfo.setGold(intValue);
					}
					if (name.startsWith("resist")){
						resistType=name.substring(6);
						String resistPair=parser.nextText();
						//intValue=toInteger();
						if (resistType.equals("gira")){
							resistIndex=1;
						}
						if (resistType.equals("rariho")){
							resistIndex=2;
						}
						if (resistType.equals("mahoton")){
							resistIndex=3;
						}
						if (resistIndex>0){
							int delimter_index= resistPair.indexOf('/');
							if (delimter_index>=0 && delimter_index<resistPair.length()){
								resistStr=resistPair.substring(0, delimter_index);
								resistMaxStr=resistPair.substring(delimter_index+1);
								monsterInfo.setResist(resistIndex, Util.toInteger(resistStr), Util.toInteger(resistMaxStr));
							}
						}
					}
					if (name.startsWith("action")){
						actionOrder=Util.toInteger(name.substring(6));
						if (actionOrder>0 || actionOrder<=8){
							strValue = parser.nextText();
							monsterInfo.setActionString(actionOrder-1, strValue);
							intValue = ActionInfo.getActionId(strValue);
							monsterInfo.setAction(actionOrder-1, intValue);
						}
					}
					if (name.equals("actmode")){
						strValue=parser.nextText();
						if (strValue.equals("random")){
							monsterInfo.setActmode(MonsterInfo.ACTION_MODE_RANDOM);
						}
						else{
							monsterInfo.setActmode(MonsterInfo.ACTION_MODE_FIXED);
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
	public static int initActions(){
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
			int id;
			String strValue;
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					action_info_list=new ArrayList<ActionInfo>();
					action_info_list.add(null);
					break;
				case XmlPullParser.START_TAG:
					name = parser.getName();
					if (name.equals("action")){
						actionInfo=new ActionInfo();
					}
					if (name.equals("id")){
						id=Util.toInteger(parser.nextText());
						actionInfo.setActionId(id);
					}
					if (name.equals("string")){
						strValue=parser.nextText();
						actionInfo.setActionString(strValue);
					}
					if (name.equals("name")){
						strValue=parser.nextText();
						actionInfo.setName(strValue);
					}
					if (name.equals("desc")){
						strValue=parser.nextText();
						actionInfo.setDescription(strValue);
					}
					if (name.equals("code")){
						strValue=parser.nextText();
						actionInfo.setCode(strValue);
					}
					if (name.equals("type")){
						strValue=parser.nextText();
						String[] targetTypes=strValue.split(",");
						//actionInfo.setDescription(strValue);
						int i, size=targetTypes.length;
						for (i=0; i<size; i++){
							if (targetTypes[i].equals("enemy")){
								actionInfo.setTargetUnitSide(ActionInfo.TARGET_TYPE_ENEMY);
							}
							if (targetTypes[i].equals("ally")){
								actionInfo.setTargetUnitSide(ActionInfo.TARGET_TYPE_ALLY);
							}
							if (targetTypes[i].equals("none")){
								actionInfo.setTargetUnitSide(ActionInfo.TARGET_TYPE_NONE);
							}
							if (targetTypes[i].equals("single")){
								actionInfo.setTargetRange(ActionInfo.TARGET_RANGE_SINGlE);
							}
							if (targetTypes[i].equals("team")){
								actionInfo.setTargetRange(ActionInfo.TARGET_RANGE_TEAM);
							}
							if (targetTypes[i].equals("all")){
								actionInfo.setTargetRange(ActionInfo.TARGET_RANGE_ALL);
							}
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

}
