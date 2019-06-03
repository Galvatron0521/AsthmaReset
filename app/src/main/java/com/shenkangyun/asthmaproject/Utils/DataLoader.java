package com.shenkangyun.asthmaproject.Utils;

import android.content.Context;

import com.shenkangyun.asthmaproject.BeanFolder.VoteSubmitItem;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class DataLoader {

    private Context mContext;
    //private VoteSubmitItem testItem;
    private ArrayList<VoteSubmitItem> testItems;
    //private String[] voteQuestion = { "你咋看日本首相安倍晋三参拜靖国神社？", "疫苗事故频发，你还会接种疫苗吗？", "爷爷支持孙子不上学称拆迁款多，咋看？", "新版《天龙八部》乔峰扛滑板，你咋看？", "今年春节,您怎么回家？", "你咋看数十万北京上班族每日跨省上班？", "代表建议禁低端产业进京，你咋看？" };
    //private String[][] voteAnswers = { { "1.抗议，二战亡魂不能安息", "2.冷静，人家有祭拜的理由", "3.看多了，麻木了" }, { "1.个例，该接种还是要接种", "2.怕怕，拒绝接种疫苗", "3.会接种，但是要用进口的", "4.不知道，只望尽快查处结果" }, { "1.羡慕，度数很多年也没钱", "2.可悲，暴发户的心态毁孩子", "3.天真，几百万就能养三代？", "4.路过，不表态" }, { "1.气死金庸，糟蹋经典", "2.帅哥美女，符合年轻人口味", "3.任务造型让人不能忍", "4.用来打发时间还行吧", "5.雷剧横行，这算啥" }, { "1.假期少，只能多花钱坐飞机", "2.穷，等抢火车票", "3.拖家带口的，开车回吧", "4.加班，回不了家", "5.我就在家乡工作，同情你们" }, { "1.心酸，万恶的背景放假！", "2.活该，都挤在北京有啥好", "3.只能说是自己选择的生活方式", "4.路过，我不在北京" }, { "1.顶，北京都要发展到8环了。", "2.帝都人民不食人间烟火的？", "3.神马叫低端产业？", "4.不明觉厉！" } };

    public DataLoader(Context context) {
        this.mContext = context;
    }

    /**
     * @return 数据
     */
    public ArrayList<VoteSubmitItem> getTestData() {

        InputStream fis;
        try {
            //fis = new FileInputStream(new File("/src/questions.xml"));
            //file not found
            fis = mContext.getResources().getAssets().open("questions.xml");
            testItems = readXML(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return testItems;

		/*testItems = new ArrayList<VoteSubmitItem>();
        for (int i = 0; i < 7; i++) {
			testItem = new VoteSubmitItem();
			testItem.itemId = i;
			testItem.voteQuestion = voteQuestion[i];
			for (int j = 0; j < voteAnswers[i].length; j++) {
				testItem.voteAnswers.add(voteAnswers[i][j]);
			}
			testItems.add(testItem);
		}
		return testItems;*/
    }

    public static ArrayList<VoteSubmitItem> readXML(InputStream inStream) {

        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser saxParser = spf.newSAXParser(); // 创建解析器
            XMLContentHandler handler = new XMLContentHandler();
            saxParser.parse(inStream, handler);
            inStream.close();
            return handler.getQuestions();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
