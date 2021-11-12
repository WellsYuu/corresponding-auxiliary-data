package com.xiangxue.ch8b.assist;

import com.xiangxue.ch8b.assist.Consts;
import com.xiangxue.ch8b.vo.SrcDocVo;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;


/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：生成待处理文档
 */
public class CreatePendingDocs {

    /**
     * 生成一批待处理文档
     * @param docCount 生成的文档数量
     * @return 待处理文档列表
     */
    public static List<SrcDocVo> makePendingDoc(int count){
        Random r = new Random();
        //文档列表
        List<SrcDocVo> docList = new LinkedList<>();
        for(int i=0;i<count;i++){
            List<Integer> questionList = new LinkedList<Integer>();
            //每个文档中包含的题目列表
            for(int j=0;j< Consts.QUESTION_COUNT_IN_DOC;j++){
                int questionId = r.nextInt(Consts.SIZE_OF_QUESTION_BANK);
                questionList.add(questionId);
            }
            SrcDocVo pendingDocVo = new SrcDocVo("pending_"+i,
                    questionList);
            docList.add(pendingDocVo);
        }
        return docList;
    }

}
