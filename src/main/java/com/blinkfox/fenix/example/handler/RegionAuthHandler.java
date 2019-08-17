package com.blinkfox.fenix.example.handler;

import com.blinkfox.fenix.bean.BuildSource;
import com.blinkfox.fenix.config.annotation.Tagger;
import com.blinkfox.fenix.consts.Const;
import com.blinkfox.fenix.consts.SymbolConst;
import com.blinkfox.fenix.core.FenixHandler;
import com.blinkfox.fenix.helper.ParseHelper;
import com.blinkfox.fenix.helper.XmlNodeHelper;

import java.util.Map;

import org.dom4j.Node;

/**
 * 实现了 {@link FenixHandler} 接口的自定义地区权限控制的标签处理器.
 *
 * @author blinkfox on 2019-08-17.
 */
@Tagger(value = "regionAuth")
@Tagger(value = "andRegionAuth", prefix = " AND ")
public class RegionAuthHandler implements FenixHandler {

    /**
     * 命名参数的常量.
     */
    private static final String REGION_ANME = "region";

    /**
     * 根据 {@link BuildSource} 的相关参数来追加构建出对应 XML 标签的 JPQL 语句及参数信息.
     *
     * @param source 构建 SQL 片段和参数所需的 {@link BuildSource} 资源对象
     */
    @Override
    public void buildSqlInfo(BuildSource source) {
        // 从 source 参数中获取到 XML Node 节点、拼接 SQL 的 StringBuilder 对象和 params 参数.
        Node node = source.getNode();
        StringBuilder join = source.getSqlInfo().getJoin();
        Map<String, Object> params = source.getSqlInfo().getParams();

        // 获取 field 和 userId 属性的文本值，该方法会检测属性值是否为空，如果为空，会抛出异常.
        String fieldText = XmlNodeHelper.getAndCheckNodeText(node, "attribute::field");
        String userIdText = XmlNodeHelper.getAndCheckNodeText(node, "attribute::userId");

        // 根据上下文参数，解析出 userId 的真实值，我这里假设 userId 为 String 型，你可以根据实际情况转换成其他类型.
        String userId = (String) ParseHelper.parseExpressWithException(userIdText, source.getContext());

        // 假定从用户缓存中获取到该用户信息，从而得到用户地区级别和所在的地区编码.
        // User user = UserCache.get(userId);
        // 下面的用户信息应该从缓存中获取，这里为了演示逻辑代码，先设置为空.
        User user = null;
        int level = user.getLevel();
        String region = user.getRegion();

        // 如果是中央级（level == 0）的用户，由于是要查看全部数据，所以不用生成查询条件的 SQL 片段，直接返回即可.
        if (level == 0) {
            return;
        }

        // 如果该用户是省级（level == 1）用户，则生成 x.region LIKE :region，
        // 参数值为地区编码的前 2 位数的前缀匹配，因为前 2 位相同说明是同一个省份的.
        if (user.getLevel() == 1) {
            join.append(source.getPrefix()).append(fieldText)
                    .append(SymbolConst.LIKE).append(Const.COLON).append(REGION_ANME);
            params.put(REGION_ANME, region.substring(0, 2) + "%");
        } else if (user.getLevel() == 2) {
            // 如果该用户是地市级（level == 2）用户，则生成 x.region LIKE :region，
            // 参数值为地区编码的前 4 位数的前缀匹配，因为前 4 位相同说明是同一个地市的.
            join.append(source.getPrefix()).append(fieldText)
                    .append(SymbolConst.LIKE).append(Const.COLON).append(REGION_ANME);
            params.put(REGION_ANME, region.substring(0, 4) + "%");
        } else if (user.getLevel() == 3) {
            // 如果该用户是区县级（level == 3）用户，则直接生成等值查询即可。x.region = :region.
            join.append(source.getPrefix()).append(fieldText)
                    .append(SymbolConst.EQUAL).append(Const.COLON).append(REGION_ANME);
            params.put(REGION_ANME, region);
        }
    }

}
