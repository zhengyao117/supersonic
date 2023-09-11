package com.tencent.supersonic.chat.corrector;

import com.tencent.supersonic.chat.api.pojo.SemanticCorrectInfo;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class SelectFieldAppendCorrectorTest {

    @Test
    void corrector() {
        SelectFieldAppendCorrector corrector = new SelectFieldAppendCorrector();
        SemanticCorrectInfo semanticCorrectInfo = SemanticCorrectInfo.builder()
                .sql("select 歌曲名 from 歌曲库 where datediff('day', 发布日期, '2023-08-09') <= 1 and 歌手名 = '邓紫棋' "
                        + "and sys_imp_date = '2023-08-09' and 歌曲发布时 = '2023-08-01' order by 播放量 desc limit 11")
                .build();

        corrector.correct(semanticCorrectInfo);

        Assert.assertEquals(
                "SELECT 歌曲名, 歌手名, 播放量, 歌曲发布时, 发布日期 FROM 歌曲库 WHERE "
                        + "datediff('day', 发布日期, '2023-08-09') <= 1 AND 歌手名 = '邓紫棋' "
                        + "AND sys_imp_date = '2023-08-09' AND 歌曲发布时 = '2023-08-01'"
                        + " ORDER BY 播放量 DESC LIMIT 11", semanticCorrectInfo.getSql());

    }
}
