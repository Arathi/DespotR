﻿# 关于DespotR的下一步计划 

---

***DespotR***暂时已经能运行起来了，下一步的计划就是为其添加更多功能，使之能更好的模拟*Dragon Quest*。

## 技能系统改造

单位系统、物品系统、技能系统之间的结构并不相互嵌套，单位的物品栏中只有物品的ID，物品的效果本身实际上是技能，单位和物品的技能表也只有技能的ID。

目前，物品系统和玩家单位暂未实现，技能系统主要是演示怪物之间自动战斗时使用，而技能的动作实际上是没有任何操作的，下一步计划中，实现5类技能：

* 火焰
* 治疗
* 催眠
* 沉默
* 龙火

同战斗系统类似的，一个技能的施放有以下步骤：

1. 检测沉默状态
2. 检测法力值
3. 扣除法力值
4. 检测对目标有效
5. 应用魔法效果

## 玩家单位的实现

分为两个部分：

* Hero数据类型
* 玩家对单位的控制器

## 物品系统的实现

暂未安排

## 物体编辑器的实现

目前直接编辑XML添加修改技能数据，似乎有点恶心。想象中的编辑器，能像WorldEdit的Object Editor那样就行了。

## SQLite数据改造

不仅要支持XML存储单位、怪物、技能数据，也要支持使用SQLite来保存保存这些信息。