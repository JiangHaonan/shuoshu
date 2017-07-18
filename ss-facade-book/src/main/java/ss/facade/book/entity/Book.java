package ss.facade.book.entity;

import ss.common.core.entity.BaseEntity;

/**
 * Created by mutou on 2017/5/24.
 */
public class Book extends BaseEntity<Book>{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 小标题
	 */
	private String subtitle;

	/**
	 * 出版日期
	 */
    private String pubdate;

    /**
     * 作者
     */
    private String author;

    /**
     * 图片
     */
    private String image;

    /**
     * 书名
     */
    private String title;

    /**
     * 概要
     */
    private String summary;
    
    /**
     * 是否是豆瓣的图书链接
     */

    /**
     * json格式
     * "publisher":["青岛出版社"],
     * "pubdate":["2005-01-01"],
     * "author":["片山恭一", "豫人"],
     * "price":["18.00元"],
     * "title":["满月之夜白鲸现"],
     * "binding":["平装(无盘)"],
     * "translator":["豫人"],
     * "pages":["180"]
     */
    private String attrs;

    /**
     * 出版社
     */
    private String publisher;


    /**
     * 标签，JSON格式
     * {"count":106, "name":"片山恭一"},
     * {"count":50, "name":"日本"},
     * {"count":42, "name":"日本文学"},
     * {"count":30, "name":"满月之夜白鲸现"},
     * {"count":28, "name":"小说"},
     * {"count":10, "name":"爱情"},
     * {"count":7, "name":"純愛"},
     * {"count":6, "name":"外国文学"}
     */
    private String tags;

    /**
     * 图书上传标志：
     * 0：豆瓣添加
     * 1：fastdfs添加
     */
    private String uploadType;
    
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

	public String getAttrs() {
		return attrs;
	}

	public void setAttrs(String attrs) {
		this.attrs = attrs;
	}

	public String getUploadType() {
		return uploadType;
	}

	public void setUploadType(String uploadType) {
		this.uploadType = uploadType;
	}

	
}
