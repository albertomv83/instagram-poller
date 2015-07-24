/**
 * $Id$
 * @author amancheno
 * @date   24/7/2015 12:48:30
 *
 * Copyright (C) 2015 Scytl Secure Electronic Voting SA
 *
 * All rights reserved.
 *
 */
package es.instavino.flume;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.jinstagram.Instagram;
import org.jinstagram.entity.tags.TagMediaFeed;
import org.jinstagram.entity.users.feed.MediaFeedData;
import org.jinstagram.exceptions.InstagramException;

/**
 *
 */
public class HashtagEndpoint {
    private final Instagram instagram;

    private final String hashtag;

    private String minTagId;

    public HashtagEndpoint(final Instagram instagram,
            final String hashtag, final String minTagId) {
        this.instagram = instagram;
        this.hashtag = hashtag;
        this.minTagId = minTagId;
    }

    public List<MediaFeedData> recentFeed() throws InstagramException {
        TagMediaFeed feed =
                instagram.getRecentMediaTags(hashtag, minTagId, null);
        List<MediaFeedData> dataList = feed.getData();
        if (dataList.size() == 0) {
            return Collections.emptyList();
        }

        String maxTagId = feed.getPagination().getNextMaxTagId();
        if (maxTagId != null && maxTagId.compareTo(minTagId) > 0) {
            dataList.addAll(paginateFeed(maxTagId));
        }
        Collections.reverse(dataList);
        // dataList.removeIf(d -> d.getId().compareTo(minTagId) < 0);

        minTagId = feed.getPagination().getMinTagId();
        return dataList;
    }

    private Collection<? extends MediaFeedData> paginateFeed(
        String maxTagId) throws InstagramException {
        System.out.println("pagination required");

        List<MediaFeedData> dataList = new ArrayList<MediaFeedData>();
        do {
            TagMediaFeed feed =
                    instagram.getRecentMediaTags(hashtag, null, maxTagId);
            maxTagId = feed.getPagination().getNextMaxTagId();
            dataList.addAll(feed.getData());
        } while (maxTagId.compareTo(minTagId) > 0);
        return dataList;
    }

}
