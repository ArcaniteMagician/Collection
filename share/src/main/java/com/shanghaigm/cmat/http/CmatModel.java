package com.shanghaigm.cmat.http;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by CHUMI on 2016/6/20.
 */
public class CmatModel {

	private static final String TAG = "{\n" +
			"\t\"topics\":[\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我很容易迷惑。\",\n" +
			"\t\t\t\"type\": 9\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我不想成为一个喜欢批评的人，但是很难做到。\",\n" +
			"\t\t\t\"type\": 1\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我喜欢研究宇宙的道理、哲理。\",\n" +
			"\t\t\t\"type\": 5\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我很注意自己是否年轻，因为那是找乐子的本钱。\",\n" +
			"\t\t\t\"type\": 7\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我喜欢独立自主，一切都靠自己。\",\n" +
			"\t\t\t\"type\": 8\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"当我有困难时，我会试着不让人知道。\",\n" +
			"\t\t\t\"type\": 2\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"被人误解对于我而言，是一件非常痛苦的事。\",\n" +
			"\t\t\t\"type\": 4\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"施比受会给我带来更大的满足感。\",\n" +
			"\t\t\t\"type\": 2\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我常常设想最糟糕的结构，而使自己陷入苦恼中。\",\n" +
			"\t\t\t\"type\": 6\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我常常试探或考验朋友、伴侣的忠诚。\",\n" +
			"\t\t\t\"type\": 6\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我看不起那些不像我一样坚强的人，有时我会用种种方式羞辱他们。\",\n" +
			"\t\t\t\"type\": 8\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"身体上的舒适，对我非常重要。\",\n" +
			"\t\t\t\"type\": 9\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我能触碰生活中的悲哀和不幸。\",\n" +
			"\t\t\t\"type\": 4\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"别人不能完成他的分内事，会令我失望和愤怒。\",\n" +
			"\t\t\t\"type\": 1\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我时常拖延问题，不去解决。\",\n" +
			"\t\t\t\"type\": 9\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我喜欢戏剧性，多姿多彩的生活。\",\n" +
			"\t\t\t\"type\": 7\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我认为自己非常不完善。\",\n" +
			"\t\t\t\"type\": 4\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我对感官的需求特别强烈，喜欢美食、服装、身体的触觉刺激，并纵情享乐。\",\n" +
			"\t\t\t\"type\": 7\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"当别人请教我问题，我会巨细地分析得很清楚。\",\n" +
			"\t\t\t\"type\": 5\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我习惯推销自己，从不觉得难为情。\",\n" +
			"\t\t\t\"type\": 3\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"有时我会放纵和做出僭越的事。\",\n" +
			"\t\t\t\"type\": 7\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"帮助不到别人，会让我觉得痛苦。\",\n" +
			"\t\t\t\"type\": 2\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我不喜欢大家问我广泛、笼统的问题。\",\n" +
			"\t\t\t\"type\": 5\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"在某方面，我有放纵的倾向（如食物、药物等）。\",\n" +
			"\t\t\t\"type\": 8\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我宁愿适应别人，包括我的伴侣，而不会反抗他们。\",\n" +
			"\t\t\t\"type\": 9\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我最不喜欢的一件事就是虚伪。\",\n" +
			"\t\t\t\"type\": 6\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我知错能改，但由于执着好强，周围的人还是会感到压力。\",\n" +
			"\t\t\t\"type\": 8\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我常觉得很多事都是很好玩，很有趣，人生真是快乐。\",\n" +
			"\t\t\t\"type\": 7\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我有时很欣赏自己充满权威，有时却又优柔寡断，依赖别人。\",\n" +
			"\t\t\t\"type\": 6\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我习惯付出多于接受。\",\n" +
			"\t\t\t\"type\": 2\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"面对威胁时，我一是变得焦虑，一是对抗迎面而来的危险。\",\n" +
			"\t\t\t\"type\": 6\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我通常是等着别人来接近我，而不是我去接近他们。\",\n" +
			"\t\t\t\"type\": 5\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我喜欢当主角，希望得到大家的注意。\",\n" +
			"\t\t\t\"type\": 3\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"别人批评我，我也不会回应和辩解，因为我不想发生任何争执与冲突。\",\n" +
			"\t\t\t\"type\": 9\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我有时期待别人的指导，有时却忽略别人的忠告，径直去做我想做的事。\",\n" +
			"\t\t\t\"type\": 6\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我经常忘记自己的需要。\",\n" +
			"\t\t\t\"type\": 9\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"在重大危机中，我通常能克服我对自己的质疑和内心的焦虑。\",\n" +
			"\t\t\t\"type\": 6\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我是一个天生的推销员，说服别人对我来说是一件轻易的事。\",\n" +
			"\t\t\t\"type\": 3\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我不相信一个我一直都无法了解的人。\",\n" +
			"\t\t\t\"type\": 9\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我爱依惯例行事，不太喜欢改变。\",\n" +
			"\t\t\t\"type\": 8\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我很在乎家人，在家中表现得忠诚和包容。\",\n" +
			"\t\t\t\"type\": 9\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我被动而优柔寡断。\",\n" +
			"\t\t\t\"type\": 5\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我很有包容力，彬彬有礼，但跟人的感情互动不深。\",\n" +
			"\t\t\t\"type\": 5\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我沉默寡言，好像不会关心别人似的。\",\n" +
			"\t\t\t\"type\": 8\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"当沉浸在工作或我擅长的领域时，别人会觉得我冷酷无情。\",\n" +
			"\t\t\t\"type\": 6\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我常常保持警觉。\",\n" +
			"\t\t\t\"type\": 6\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我不喜欢要对人尽义务的感觉。\",\n" +
			"\t\t\t\"type\": 5\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"如果不能完美地表态，我宁愿不说。\",\n" +
			"\t\t\t\"type\": 5\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我的计划比我实际完成的还要多。\",\n" +
			"\t\t\t\"type\": 7\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我野心勃勃，喜欢挑战和登上高峰的经验。\",\n" +
			"\t\t\t\"type\": 8\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我倾向于独断专行并自己解决问题。\",\n" +
			"\t\t\t\"type\": 5\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我很多时候感到被遗弃。\",\n" +
			"\t\t\t\"type\": 4\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我常常表现得十分忧郁的样子，充满痛苦并且内向。\",\n" +
			"\t\t\t\"type\": 4\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"初见陌生人时，我会表现得很冷漠、高傲。\",\n" +
			"\t\t\t\"type\": 4\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我的面部表情严肃而生硬。\",\n" +
			"\t\t\t\"type\": 1\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我很飘忽，常常不知自己下一刻想要什么。\",\n" +
			"\t\t\t\"type\": 4\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我常对自己挑剔，期望不断改善自己的缺点，以成为一个完美的人。\",\n" +
			"\t\t\t\"type\": 1\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我感受特别深刻，并怀疑那些总是很快乐的人。\",\n" +
			"\t\t\t\"type\": 4\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我做事有效率，也会找捷径，模仿力特别强。\",\n" +
			"\t\t\t\"type\": 3\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我讲理，重实用。\",\n" +
			"\t\t\t\"type\": 1\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我有很强的创造天分和想象力，喜欢将事情重新整合。\",\n" +
			"\t\t\t\"type\": 4\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我不需要得到很多的注意力。\",\n" +
			"\t\t\t\"type\": 9\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我喜欢每件事都井然有序，但别人会认为我过分执着。\",\n" +
			"\t\t\t\"type\": 1\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我渴望拥有完美的心灵伴侣。\",\n" +
			"\t\t\t\"type\": 4\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我常夸耀自己，对自己的能力十分有信心。\",\n" +
			"\t\t\t\"type\": 3\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"如果周遭的人行为太过分时，我准会让他难堪。\",\n" +
			"\t\t\t\"type\": 8\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我外向、精力充沛，喜欢不断追求成就，这使我的自我感觉十分良好。\",\n" +
			"\t\t\t\"type\": 3\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我是一位忠实的朋友和伙伴。\",\n" +
			"\t\t\t\"type\": 6\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我知道如何让别人喜欢我。\",\n" +
			"\t\t\t\"type\": 2\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我很少看到别人的功劳和好处。\",\n" +
			"\t\t\t\"type\": 3\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我很容易知道别人的功劳和好处。\",\n" +
			"\t\t\t\"type\": 2\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我嫉妒心强，喜欢跟别人比较。\",\n" +
			"\t\t\t\"type\": 3\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我对别人做的事总是不放心，批评一番后，自己会动手再做。\",\n" +
			"\t\t\t\"type\": 1\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"别人会说我常戴着面具做人。\",\n" +
			"\t\t\t\"type\": 3\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"有时我会激怒对方，引来莫名其妙的吵架，其实我是想试探对方爱不爱我。\",\n" +
			"\t\t\t\"type\": 6\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我会极力保护我所爱的人。\",\n" +
			"\t\t\t\"type\": 8\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我常常刻意保持兴奋的情绪。\",\n" +
			"\t\t\t\"type\": 3\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我只喜欢与有趣的人为友，对一些闷蛋却懒得交往，即使他们看起来很有深度。\",\n" +
			"\t\t\t\"type\": 7\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我常往外跑，四处帮助别人。\",\n" +
			"\t\t\t\"type\": 2\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"有时我会讲求效率而牺牲完美和原则。\",\n" +
			"\t\t\t\"type\": 3\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我似乎不太懂得幽默，没有弹性。\",\n" +
			"\t\t\t\"type\": 1\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我待人热情而有耐心。\",\n" +
			"\t\t\t\"type\": 2\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"在人群中我时常感到害羞和不安。\",\n" +
			"\t\t\t\"type\": 5\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我喜欢效率，讨厌拖泥带水。\",\n" +
			"\t\t\t\"type\": 8\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"帮助别人达致快乐和成功是我重要的成就。\",\n" +
			"\t\t\t\"type\": 2\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"付出时，别人若不欣然接纳，我便会有挫折感。\",\n" +
			"\t\t\t\"type\": 2\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我的肢体硬邦邦的，不习惯别人热情的付出。\",\n" +
			"\t\t\t\"type\": 1\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我对大部分的社交集会不太有兴趣，除非那是我熟识的和喜爱的人。\",\n" +
			"\t\t\t\"type\": 5\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"很多时候我会有很强烈的寂寞感。\",\n" +
			"\t\t\t\"type\": 2\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"人们很乐意向我表白他们所遭遇的问题。\",\n" +
			"\t\t\t\"type\": 2\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我不但不会说甜言蜜语，而且别人会觉得我唠叨不停。\",\n" +
			"\t\t\t\"type\": 1\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我常担心自由被剥夺，因此不爱作承诺。\",\n" +
			"\t\t\t\"type\": 7\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我喜欢告诉别人我所做的事和所知的一切。\",\n" +
			"\t\t\t\"type\": 3\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我很容易认同别人为我所做的事和所知的一切。\",\n" +
			"\t\t\t\"type\": 9\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我要求光明正大，为此不惜与人发生冲突。\",\n" +
			"\t\t\t\"type\": 8\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我很有正义感，有时会支持不利的一方。\",\n" +
			"\t\t\t\"type\": 8\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我重视小节而效率不高。\",\n" +
			"\t\t\t\"type\": 1\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我容易感到沮丧和麻木更多于愤怒。\",\n" +
			"\t\t\t\"type\": 9\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我不喜欢那些侵略性或过度情绪化的人。\",\n" +
			"\t\t\t\"type\": 5\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我非常情绪化，一天的喜怒哀乐多变。\",\n" +
			"\t\t\t\"type\": 4\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我不想别人知道我的感受与想法，除非我告诉他们。\",\n" +
			"\t\t\t\"type\": 5\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我喜欢刺激和紧张的关系，而不是稳定和依赖的关系。\",\n" +
			"\t\t\t\"type\": 1\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我很少用心去听别人的心情，只喜欢说说俏皮话和笑话。\",\n" +
			"\t\t\t\"type\": 7\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我是循规蹈矩的人，秩序对我十分有意义。\",\n" +
			"\t\t\t\"type\": 1\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我很难找到一种我真正感到被爱的关系。\",\n" +
			"\t\t\t\"type\": 4\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"假如我想要结束一段关系，我不是直接告诉对方，就是激怒他来让他离开我。\",\n" +
			"\t\t\t\"type\": 1\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我温和平静，不自夸，不爱与人竞争。\",\n" +
			"\t\t\t\"type\": 9\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"content\": \"我有时善良可爱，有时又粗野暴躁，很难捉摸。\",\n" +
			"\t\t\t\"type\": 9\n" +
			"\t\t}\n" +
			"\t],\n" +
			"\t\"commonChoice\": [\n" +
			"\t\t{\n" +
			"\t\t\t\"name\": \"是\",\n" +
			"\t\t\t\"value\": 1\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"name\": \"不是\",\n" +
			"\t\t\t\"value\": 0\n" +
			"\t\t}\n" +
			"\t]\n" +
			"}";

	private static final String PREF_USERNAME = "code02";
	private static final String PREF_ACCESS_TOKEN = "code04";
	private static final String PREF_USER_ACCOUNT = "code05";
	private static final String PREF_STORE_CODE = "code08"; //经销商代码
	private static final String PREF_POSITION_CODE = "code09"; //岗位代码
	private static final String PREF_ACCOUNT_CODE = "code11"; //用户代码
	private static final String PREF_SIGN_CODE = "code15"; //signCode

	private Context context;
	private SecurityUtils securityUtils;

	public CmatModel(Context context) {
		this.context = context;
		securityUtils = SecurityUtils.getInstance("cmatmodel");
		// 每次清空
		//SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		//preferences.edit().clear().apply();
	}

	/**
	 * 取值方法
	 *
	 * @param key 键
	 * @return 值
	 */
	private String getPrefValue(String key){
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		String value = pref.getString(key, null);
		if(value != null){
			return securityUtils.decrypt(value);
		}else {
			return null;
		}
	}

	/**
	 * 存值方法
	 *
	 * @param key 键
	 * @param value 值
	 * @return 是否成功
	 */
	private boolean setPrefValue(String key, String value){
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		return preferences.edit().putString(key, securityUtils.encrypt(value)).commit();
	}

	// 存储用户UserName
	public boolean setUserName(String userName) {
		return setPrefValue(PREF_USERNAME, userName);
	}

	public String getUserName() {
		return getPrefValue(PREF_USERNAME);
	}

	// 存储用户UserAccount
	public boolean setUserAccount(String userAccount) {
		return setPrefValue(PREF_USER_ACCOUNT, userAccount);
	}

	public String getUserAccount() {
		return getPrefValue(PREF_USER_ACCOUNT);
	}

	// 存储用户AccessToken
	public boolean setAccessToken(String accessToken) {
		return setPrefValue(PREF_ACCESS_TOKEN, accessToken);
	}

	public String getAccessToken() {
		return getPrefValue(PREF_ACCESS_TOKEN);
	}

	// 存储用户SignCode
	public boolean setSignCode(String signCode) {
		return setPrefValue(PREF_SIGN_CODE, signCode);
	}

	public String getSignCode() {
		return getPrefValue(PREF_SIGN_CODE);
	}

	/*
	 * 存储StoreCode
	 */
	public boolean setStoreCode(String storeCode){
		return setPrefValue(PREF_STORE_CODE, storeCode);
	}

	/*
	 * 获取StoreCode
	 */
	public String getStoreCode(){
		return getPrefValue(PREF_STORE_CODE);
	}

	/*
	 * 存储PositionCode
	 */
	public boolean setPositionCode(String brandCode){
		return setPrefValue(PREF_POSITION_CODE, brandCode);
	}

	/*
	 * 获取PositionCode
	 */
	public String getPositionCode(){
		return getPrefValue(PREF_POSITION_CODE);
	}

	/*
	 * 存储用户AccountCode
	 */
	public boolean setAccountCode(String accountCode) {
		return setPrefValue(PREF_ACCOUNT_CODE, accountCode);
	}

	/*
	 * 获取当前登陆用户AccountCode
	 */
	public String getAccountCode() {
		return getPrefValue(PREF_ACCOUNT_CODE);
	}

}
