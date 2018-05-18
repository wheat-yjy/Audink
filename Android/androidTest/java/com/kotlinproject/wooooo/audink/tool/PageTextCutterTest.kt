package com.kotlinproject.wooooo.audink.tool

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PageTextCutterTest {

    @Test
    fun getCuttedText() {
        val context = InstrumentationRegistry.getTargetContext()
        val pageAdapter = PagePaintAdapter(context, 64)
        val cutter = PageTextCutter(pageAdapter)
        val lineCount = pageAdapter.pageLineCount
        cutter.load(testText)
        val pg1 = cutter.getCuttedText()
    }

    @Test
    fun getCuttedNextPageText() {
        val context = InstrumentationRegistry.getTargetContext()
        val pageAdapter = PagePaintAdapter(context, 64)
        val cutter = PageTextCutter(pageAdapter)
        val lineCount = pageAdapter.pageLineCount
        cutter.load(testText)
        val pg1 = cutter.getCuttedText()
        cutter.apply {
            nextPage()
            nextPage()
            nextPage()
            nextPage()
            nextPage()
            nextPage()
        }
        val pgn1 = cutter.getCuttedText()
        cutter.apply {
            nextPage()
            nextPage()
            nextPage()
            nextPage()
        }
        val pgn2 = cutter.getCuttedText()
        cutter.apply {
            nextPage()
            nextPage()
            nextPage()
            nextPage()
        }
        val pgn3 = cutter.getCuttedText()
    }

    @Test
    fun getCuttedPrePageText() {
        val context = InstrumentationRegistry.getTargetContext()
        val pageAdapter = PagePaintAdapter(context, 64)
        val cutter = PageTextCutter(pageAdapter)
        val lineCount = pageAdapter.pageLineCount
        cutter.load(testText)
        val pg1 = cutter.getCuttedText()
        cutter.nextPage()
        val pg2 = cutter.getCuttedText()
        cutter.prePage()
        val pg1p = cutter.getCuttedText()
        cutter.prePage()
        val pg0p = cutter.getCuttedText()
    }


    private val testText = """
　　易君恕急了，“这位朋友可不能不见！我去总理衙门就是受他所托，他还等着回话呢！”
　　“你是朝廷的几品大员？”老太太愤然道，“白丁一个，这样的大事也敢应承，我看你怎么回复人家？”
　　“我……”易君恕也感到为难。
　　“唉，”老太太烦躁地摆了摆手，“去吧！”
　　“是！”易君恕这才敢站起身来，心烦意乱地朝外面走去。
　　大门旁边，倒座南房的外客厅里，一位客人正在焦急地踱步，等待着和易君恕见面。此人正是邓伯雄，他年约二十四五岁，身材魁梧，虎背熊腰，头戴青缎便帽，脑后垂着一条粗黑的大辫子，身穿元青直罗长衫，外罩青缎马褂，足蹬双梁布鞋。国字形脸盘儿，浓眉大眼，肤色黑里透红，面颊和颧骨如斧凿刀削，棱角分明。
　　院子里一串脚步声，易君恕迎了过来，急步跨进外客厅：“啊，伯雄，让你久等了！”
　　“君恕兄！”邓伯雄迫不及待地说，“我在粤东会馆等不见你，心里着急，就冒昧地来到府上，怎么样？李中堂他……”
　　“唉！”易君恕未曾回答，便先叹了口气，“李鸿章这个人惯于结党营私，因为家父这一层关系，开始对我倒还客气，以为我要投靠于他，谋个一官半职；而谈到公事，他却一口回绝，不许我们干预朝政，甚至还怒而逐客！”
　　“啊？！”邓伯雄骤然一惊，大失所望。
　　“伯雄，”易君恕说，“我辜负了你的重托，深感惭愧！”
　　“不，君恕兄，你已经尽力了，大清的朝政被这种误国奸臣把持，又可奈何！”邓伯雄喟然叹道，怏怏地拱了拱手，“那么，我就告辞了！”这时，栓子从院子里匆匆走来，说，“大少爷，老太太请客人到上房叙话……”
　　“噢？”易君恕一愣。刚才母亲责罚他，没有让邓伯雄撞见，倒也罢了，岂料母亲还要和客人见面，不知老人家要说些什么，心里便发慌，犹犹豫豫地说，“伯雄，这……”
　　“我初次造访，理应拜望伯母，”邓伯雄却说，“烦请兄长引见！”
　　易君恕无可奈何，只好带着邓伯雄往里面走去，硬着头皮进了上房。到了隔扇前，又为难地向邓伯雄解释说：“家母长年卧病，行动不便，只好请你到卧房里叙话……”
　　上房东间里，安如和杏枝已经回东厢房去了，老太太强打精神，支撑着在床上坐起来，等着和客人见面。
　　“娘，”易君恕陪着客人进了里屋，介绍说，“这位就是孩儿的好友邓冠英，表字伯雄。”
　　“愚侄拜见伯母大人！”邓伯雄朝着老太太深深一揖。
　　老太太端详着面前的这位年轻人，见他仪表端正，举止庄重，倒不是那种虚华浮浪子弟，便说：“邓公子免礼！我老病缠身，礼貌不周，邓公子不要见怪，请坐吧！”
　　“伯母太客气了，”邓伯雄道，“我进京已有两月，至今才来看望伯母，还请老人家海涵！”
　　栓子搬过来两把椅子，请大少爷和客人坐下，又捧上茶来。
　　老太太望着邓伯雄，问道：“我听君恕说，邓公子是广东人？”
　　“是，伯母，”邓伯雄答道，“敝乡广东新安县。”
　　“噢，”老太太说，“过去我家老爷子在世的时候，也有一些广东的朋友来往，他们说话，语音侏漓，听不明白，不像邓公子的官话说得这么好。”
　　“伯母过奖，”邓伯雄道，“愚侄祖上本来也是中原人……”
　　“噢？中原何方人氏？”老太太问道。
　　“这……说来话长，”邓伯雄尽管忧心忡忡，但既然老人家问他，还是恭敬地答道，“我始祖‘曼’公，乃轩辕黄帝二十七世孙，殷商之际受封于邓城，在今天的湖北、河南交界之处，以南阳为郡，国名曰‘邓’，为天下邓氏之始。后来，邓氏一支迁居江西吉水县白沙村，至北宋年间，‘曼’公八十六世孙‘汉黻’公，官拜承务郎，于开宝六年宦游岭南，到了今天的新安县境内，看到屯门、元朗一带山川秀美，水土肥沃，民风淳朴，不禁乐而忘返。待卸任之后，便举家南迁，定居于岑田，筑室耕读。由此，‘汉黻’公成为新安邓氏始祖，至今已九百余年，子孙遍及新安、东莞各地，愚侄为‘汉黻’公第二十四世孙，仍然居住在先祖最初迁粤之地岑田，现称锦田。而祖籍吉水、南阳也未敢忘怀，说到底，邓氏的根柢在中原，中国百姓千家万户，也都是轩辕子孙！”
　　“邓公子说得好，”老太太点了点头，对这个年轻人深表赞许，“有道是‘四海之内皆兄弟’，我儿君恕与邓公子天南地北，相隔几千里，素昧平生，如今有缘相识，也是幸事！”
　　“是，伯母，”邓伯雄道，“愚侄来自边远省份，在京师人地生疏，举目无亲。那天前往府学胡同拜谒文丞相祠，与君恕兄偶然相遇，得到他诸多指点，一见如故，遂成为知己之交，也真是有缘。君恕兄学问优长，待人宽厚，视我如兄弟，愚侄深感三生有幸！”
　　易君恕听他这样夸赞自己，心中很是不安，白皙的面庞微微地红了，但在母亲面前却又不敢辩白，嘴张了张，惶惶然欲言又止。
　　“邓公子不必夸他了！”老太太果然没有因此而沾沾自喜，反而不以为然地看了儿子一眼，说，“我这儿子很是不成器，小时候就好读书而不求甚解，志大才疏，好高骛远，如今已经二十八岁，功也未成，名也未就。今年是戊戌正科，他放着朝廷的会试不考，倒一门心思读起了外国书，研究什么‘西学’，又能成得了什么大事？”
　　“娘，”易君恕终于忍不住，辩解道，“您长年大门不出，二门不迈，不知道外边的情形，如今有识之士都在研究西学，倡言变法，康南海多次上书，说变法先要废科举……”
　　“我怎么不知道？”老太太见儿子竟然当着客人的面和她顶嘴，脸色便阴沉起来，说，“康有为自个儿就是科举出身，乙未科进士，六品工部主事，他已然功成名就，说话才有分量。依我看，这世界无论如何变化，朝廷开科取士总是正途，废不了的！你看人家邓公子，千里迢迢从广东来到北京，不也是为了博取功名、光宗耀祖吗？”
    """.trimIndent()
}