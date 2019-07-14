package com.zzk.ssmdemo.beans;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName NewsMessage
 * @Description: TODO
 * @Author situliang
 * @Date 2019/7/10
 * @Version V1.0
 **/
@XStreamAlias("xml")
public class NewsMessage extends BaseMessage {

    @XStreamAlias("ArticleCount")
    private Integer articleCount;

    @XStreamAlias("Articles")
    private List<Article> articles = new ArrayList<>();

    public Integer getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(Integer articleCount) {
        this.articleCount = articleCount;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public NewsMessage(Map<String, String> requestMap, List<Article> articles) {
        super(requestMap);
        this.setMsgType("news");
        this.articleCount = articles.size();
        this.articles = articles;
    }
}
