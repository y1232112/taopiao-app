package com.example.taopiao.utils;

public class CityUtils {
    public static final String[] mCitiesStrings = {
            "A", "阿拉善盟", "鞍山", "安庆", "安阳", "阿坝", "安顺", "阿里", "安康", "阿克苏", "阿勒泰" ,"澳门" ,"安吉" ,"安丘" ,"安岳" ,"安平" ,"安溪" ,"安宁" ,"安化" ,"阿拉尔" ,"安福" ,"阿勒泰市",
            "阿图什市" ,"安州市" ,"阿荣旗" ,"安陆市" ,
            "B", "北京", "保定", "蚌埠", "包头", "巴彦淖尔", "本溪", "白山", "白城", "亳州", "滨州", "北海", "百色", "巴中", "毕节","保山" ,"宝鸡" ,"白银" ,"博尔塔拉" ,"巴州" ,"滨海" ,"宝应" ,"北流" ,"博爱" ,"北i碚" ,"宝丰" ,
            "博兴" ,"泌阳" ,"彬县" ,"璧山" ,"博山" ,"宾阳" ,"泊头市" ,"博罗县" ,"博白县" ,"北镇市" ,"北安市" ,"巴彦县","巴楚县","拜城县",
            "C", "重庆", "成都", "长沙","常州","长春","承德","沧州","长治","赤峰","朝阳","滁州","巢湖"," 池州","常德","郴州",
            "潮州","崇左"," 楚雄", " 昌都","昌吉","从化","常熟","慈溪","长兴","昌邑","苍南","长葛","崇州","赤壁","淳安","承德县","昌乐","曹妃甸","磁县","长垣","成安","昌黎","岑溪","荏平","曹县","城固","长汀",
            "潮安","长寿","常山","赤水","慈利","常宁市","茶陵","长丰县","苍溪县","长清区","崇明区","成武县","澄江县","澄迈县",
            "D", "大连", "大庆", "东莞", "大同", "丹东","大兴安岭","东营","德州","德阳","达州","大理","德宏","迪庆","定西","丹阳","敦煌","东阳","德清","大丰","东台",
            "当阳","东巷","登封","儋州","都江堰","大石桥","大冶","东兴","调兵山","灯塔","邓州","大通东方","东平","电白","东海","定州","郸城","大荔","达拉特旗","大竹","大洼","大邑","砀山","敦化","东光","道县",
            "大安市","定安县","垫江","东明县","定陶区","定边县","大厂回族自治区","定远县","大悟区","大足区","德惠市",
            "E", "鄂尔多斯", "恩施", "鄂州","峨眉山","额尔古纳","恩平","额敏县",
            "F", "福州", "佛山", "抚顺", "阜新", "阜阳", "抚州", "防城港","富阳","涪陵","福清","凤凰","阜宁","奉化","肥城","凤城","汾阳","阜康","丰城","范县","繁昌","肥乡","封丘","扶风","丰县","抚松","富顺",
            "费县","佛冈","丰宁","扶沟","凤台","奉新","方城","富源县","分宜","扶绥县","肥西县", "繁峙县","凤翔县","福安","福鼎市","府谷县","富民",
            "G", "广州", "桂林", "贵阳", "赣州","贵港","广元","广安","甘孜","甘南","果洛","固原","高邮","广饶","巩义","桂平","公主岭","高密","广汉","藁城", "高平","高碑店","个旧","高州","盖州","古交","格尔木",
            "高雄","灌云","灌南","赣榆","高安","广德","共青城","高阳","高陵","公安","鼓浪屿","固始县","光泽","光山县",
            "H", "杭州","合肥","海口","葫芦岛","哈尔滨","邯郸","衡水","呼和浩特","呼伦贝尔","鹤岗","黑河","淮安","湖州","淮南","淮北", "黄山","菏泽","鹤壁","黄石","黄冈","衡阳","怀化","惠州","河源","贺州","河池",
            "红河","汉中","海东","海北","黄州","海南州","海西","哈密","和田","花都","海宁","惠东","惠阳","鹤山","桦甸","海城","海门","华阴","海阳","霸州","侯马","河津","海安","霍州","黄骅","化州","海林","花莲","恒春",
            "海盐","合川","淮阳","汉阴","含山","和县","户县","辉县", "怀仁","滑县","惠安","韩城","横店","华亭","洪洞","河口","辉南","洪湖","海沧","霍邱","珲春","怀宁","怀远县","会泽县","河间县","合浦县","衡阳县","横山县",
            "衡东县","贺兰县","汉南县","海伦市","合江县","环县","黄陵县","桦川县","横山区","华容",
            "J", "济南","焦作","锦州","九江","晋城","晋中","吉林","鸡西","佳木斯","嘉兴","金华","景德镇","吉安","济宁","济源", "荆门","荆州","江门","揭阳","金昌","酒泉","江阴","嘉峪关","晋江","靖江","金坛","九寨沟","井冈山",
            "嘉善","江山","句容","建湖","晋州","胶州","建德","简阳","介休","即墨","嘉义市","集安","基隆","蛟河","建阳","金堂","","","","",
            "K", "昆明", "开封", "昆山",
            "L", "廊坊", "丽水","柳州","洛阳","莱芜","莱西","莱州","兰州","丽江","溧水县","连云港","聊城","临安","临海","临沂","陵水县","六安","六盘水","龙海","龙口","龙门县","龙岩","泸州","漯河","浏阳","乐山",
            "M", "马鞍山", "茂名", "眉山", "梅州","绵阳","闽侯","牡丹江",
            "N", "南安","南京", "南昌", "南宁", "宁波", "南充", "南平", "南通", "南阳", "那曲", "内江", "宁德", "怒江","宁海县", "宁河县",
            "P", "盘锦", "攀枝花", "平顶山", "平凉", "萍乡", "莆田", "濮阳","沛县","邳州","平湖","普洱","普宁","普兰店","蒲江","彭州",
            "Q", "青岛", "黔东南", "黔南", "黔西南", "庆阳", "清远", "秦皇岛", "钦州", "齐齐哈尔", "泉州", "曲靖", "衢州", "綦江县","启东","潜江",
            "R", "日喀则","日照","荣昌县","如东县","瑞安",
            "S", "上海", "深圳", "苏州","厦门", "沈阳", "石家庄", "三门峡", "三明", "三亚", "商洛", "商丘", "上饶", "山南", "汕头", "汕尾", "韶关", "绍兴", "邵阳", "十堰", "朔州", "四平", "绥化", "遂宁", "随州", "宿迁", "宿州",
            "上虞","嵊州","四方","松原","随州","双流",
            "T", "天津", "太原", "泰安", "泰州", "台州", "唐山", "天水", "铁岭", "铜川", "通化", "通辽", "铜陵", "铜仁", "台湾","太仓","泰兴","桐庐县","铜梁县","潼南县","天门",
            "W", "武汉", "乌鲁木齐", "无锡", "威海", "潍坊", "文山", "温州", "乌海", "芜湖", "乌兰察布", "武威", "梧州","瓦房店","吴江",
            "X", "厦门", "西安", "西宁", "襄樊", "湘潭", "湘西", "咸宁", "咸阳", "孝感", "邢台", "新乡", "信阳", "新余", "忻州", "西双版纳", "宣城", "许昌", "徐州", "香港", "锡林郭勒", "兴安","仙桃","咸阳","香河","襄阳","象山县",
            "新沂","兴化","宿迁","宿州","宣城","新津",
            "Y", "银川", "雅安", "延安", "延边", "盐城", "阳江", "阳泉", "扬州", "烟台", "宜宾", "宜昌", "宜春",
            "营口", "益阳", "永州", "岳阳", "榆林", "运城", "云浮", "玉树", "玉溪", "玉林","宜昌","伊春","宜兴","鹰潭","余姚","义乌",
            "Z", "杂多县", "赞皇县", "枣强县", "枣阳市", "枣庄", "泽库县", "增城市", "曾都区", "泽普县", "泽州县", "札达县", "扎赉特旗",
            "扎兰屯市", "扎鲁特旗", "扎囊县", "张北县", "张店区", "章贡区", "张家港", "张家界", "张家口", "漳平市",
            "漳浦县", "章丘市", "樟树市", "张湾区", "彰武县", "漳县", "张掖", "漳州", "长子县", "湛河区", "湛江",
            "站前区", "沾益县", "诏安县", "召陵区", "昭平县", "肇庆", "昭通", "赵县", "昭阳区", "招远市", "肇源县",
            "肇州县", "柞水县", "柘城县", "浙江", "镇安县", "振安区", "镇巴县", "正安县", "正定县", "正定新区",
            "正蓝旗", "正宁县", "蒸湘区", "正镶白旗", "正阳县", "郑州", "镇海区", "镇江", "浈江区", "镇康县",
            "镇赉县", "镇平县", "振兴区", "镇雄县", "镇原县", "志丹县", "治多县", "芝罘区", "枝江市",
            "芷江侗族自治县", "织金县", "中方县", "中江县", "钟楼区", "中牟县", "中宁县", "中山", "中山区",
            "钟山区", "钟山县", "中卫", "钟祥市", "中阳县", "中原区", "周村区", "周口", "周宁县", "舟曲县", "舟山",
            "周至县", "庄河市", "诸城市", "珠海", "珠晖区", "诸暨市", "驻马店", "准格尔旗", "涿鹿县", "卓尼",
            "涿州市", "卓资县", "珠山区", "竹山县", "竹溪县", "株洲", "株洲县", "淄博", "子长县", "淄川区", "自贡",
            "秭归县", "紫金县", "自流井区", "资溪县", "资兴市", "资阳","遵义","庄河","珠海","增城"
    };
}
