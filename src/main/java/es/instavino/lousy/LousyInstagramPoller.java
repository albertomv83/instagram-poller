package es.instavino.lousy;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.jinstagram.Instagram;
import org.jinstagram.auth.model.Token;
import org.jinstagram.entity.tags.TagMediaFeed;
import org.jinstagram.entity.users.feed.MediaFeedData;
import org.jinstagram.exceptions.InstagramException;

public class LousyInstagramPoller {
	
	public static void main(String ... args) throws Exception {
		String filePath = "C:\\Users\\albertomv\\Google Drive\\Instavino\\instagramsVin.csv";
		String filePath2 = "C:\\Users\\albertomv\\Google Drive\\Instavino\\instagramsVinFiltrado.csv";
		LousyInstagramPoller lip = new LousyInstagramPoller();
		String ACCESS_TOKEN = "2097810379.1fb234f.9d58a854b30e4e0ba82f421ece38f316";
		Token token = new Token(ACCESS_TOKEN, null);
		Instagram instagram = new Instagram(token);
        final String TAG_NAME = "vin";
        String id = instagram.getRecentMediaTags(TAG_NAME).getPagination().getMinTagId();
        HashtagEndpoint endpoint = lip.new HashtagEndpoint(instagram, TAG_NAME, id);
       
        while(true) {
            Thread.sleep(5000);
            StringBuffer sb = new StringBuffer("");
            StringBuffer sb2 = new StringBuffer("");
            endpoint.recentFeed().forEach(d -> {
                try {
                    sb.append(convertInstagramToCSVLine(d));
                    if(d.getLocation()!=null){
                    	sb2.append(convertInstagramToCSVLine(d));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            try {
                PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)));
                out.print(sb.toString());
                out.close();
                PrintWriter out2 = new PrintWriter(new BufferedWriter(new FileWriter(filePath2, true)));
                out2.print(sb2.toString());
                out2.close();
            } catch (IOException e) {
                //exception handling left as an exercise for the reader
            }
        }
    }
	
	private static String convertInstagramToCSVLine(MediaFeedData d) {
		//id,time,location,latitude,longitude,link,imageLink,text
		StringBuffer sb = new StringBuffer("");
		if(d.getId()!=null){
			sb.append("\"").append(d.getId()).append("\"");
		}
		sb.append(",");
		if(d.getUser()!=null){
			sb.append("\"").append(d.getUser().getUserName()).append("\"");
		}
		sb.append(",");
		if(d.getCreatedTime()!=null){
			Date date = new Date(Long.parseLong(d.getCreatedTime())*1000);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
			sb.append("\"").append(sdf.format(date)).append("\"");
		}
		sb.append(",");
		if(d.getLocation()!=null && d.getLocation().getName()!=null){
			sb.append("\"").append(d.getLocation().getName().replace("\"", "").replace('\n', ' ').replace("\r", "")).append("\"");
		}
		sb.append(",");
		if(d.getLocation()!=null){
			sb.append("\"").append(d.getLocation().getLatitude()).append("\"");
		}
		sb.append(",");
		if(d.getLocation()!=null){
			sb.append("\"").append(d.getLocation().getLongitude()).append("\"");
		}
		sb.append(",");
		if(d.getLink()!=null){
			sb.append("\"").append(d.getLink()).append("\"");
		}
		sb.append(",");
		if(d.getImages()!=null){
			sb.append("\"").append(d.getImages().getStandardResolution().getImageUrl()).append("\"");
		}
		sb.append(",");
		if(d.getCaption()!=null && d.getCaption().getText()!=null){
			sb.append("\"").append(d.getCaption().getText().replace("\"", "").replace('\n', ' ').replace("\r", "")).append("\"");
		}
		sb.append("\r\n");
		return sb.toString();
	}
	public class HashtagEndpoint {
	    private final Instagram instagram;
	    private final String hashtag;
	    private String minTagId;

	    public HashtagEndpoint(Instagram instagram, String hashtag, String minTagId) {
	        this.instagram = instagram;
	        this.hashtag = hashtag;
	        this.minTagId = minTagId;
	    }

	    public List<MediaFeedData> recentFeed() throws InstagramException {
	        TagMediaFeed feed = instagram.getRecentMediaTags(hashtag, minTagId, null);
	        List<MediaFeedData> dataList = feed.getData();
	        if (dataList.size() == 0) return Collections.emptyList();

	        String maxTagId = feed.getPagination().getNextMaxTagId();
	        if (maxTagId != null && maxTagId.compareTo(minTagId) > 0) dataList.addAll(paginateFeed(maxTagId));
	        Collections.reverse(dataList);
//	        dataList.removeIf(d -> d.getId().compareTo(minTagId) < 0);

	        minTagId = feed.getPagination().getMinTagId();
	        return dataList;
	    }

	    private Collection<? extends MediaFeedData> paginateFeed(String maxTagId) throws InstagramException {
	        System.out.println("pagination required");

	        List<MediaFeedData> dataList = new ArrayList<MediaFeedData>();
	        do {
	            TagMediaFeed feed = instagram.getRecentMediaTags(hashtag, null, maxTagId);
	            maxTagId = feed.getPagination().getNextMaxTagId();
	            dataList.addAll(feed.getData());
	        } while (maxTagId.compareTo(minTagId) > 0);
	        return dataList;
	    }

	}

}
