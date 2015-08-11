/**
 * $Id$
 * @author amancheno
 * @date   24/7/2015 12:01:32
 *
 * Copyright (C) 2015 Scytl Secure Electronic Voting SA
 *
 * All rights reserved.
 *
 */
package es.instavino.flume;

import java.io.IOException;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.PollableSource;
import org.apache.flume.conf.Configurable;
import org.apache.flume.event.JSONEvent;
import org.apache.flume.source.AbstractSource;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jinstagram.Instagram;
import org.jinstagram.auth.model.Token;
import org.jinstagram.entity.users.feed.MediaFeedData;
import org.jinstagram.exceptions.InstagramException;

import es.instavino.flume.model.FlattenedImageInstagram;

/**
 *
 */
public class InstagramTagSource extends AbstractSource implements
Configurable, PollableSource {

    private String tagProp;

    private String accessTokenProp;

    private Instagram instagram = null;

    private HashtagEndpoint endpoint = null;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * @see org.apache.flume.PollableSource#process()
     */
    @Override
    public Status process() throws EventDeliveryException {

        Status status = Status.READY;

        try {
            Thread.sleep(5000);
            endpoint.recentFeed().forEach(d -> {

                Event event;
                try {
                    event = createEvent(d);
                    getChannelProcessor().processEvent(event);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }   );
        } catch (InstagramException | InterruptedException e) {
            throw new EventDeliveryException(e);
        }

        return status;

    }

    /**
     * @param d
     * @return
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonGenerationException
     */
    private Event createEvent(final MediaFeedData d)
            throws JsonGenerationException, JsonMappingException,
            IOException {
        JSONEvent e = new JSONEvent();
        FlattenedImageInstagram json = flattenMediaFeedData(d);
        e.setBody(objectMapper.writeValueAsBytes(json));
        return e;
    }

    /**
     * @param d
     * @return
     */
    private FlattenedImageInstagram flattenMediaFeedData(
            final MediaFeedData d) {
        FlattenedImageInstagram f = new FlattenedImageInstagram();
        
        //caption
       

        return f;
    }

    @Override
    public void start() {
        // Initialize the connection to Instagram
        Token token = new Token(accessTokenProp, null);
        instagram = new Instagram(token);
        try {
            String id =
                instagram.getRecentMediaTags(tagProp).getPagination()
                    .getMinTagId();
            endpoint = new HashtagEndpoint(instagram, tagProp, id);
        } catch (InstagramException e) {
            throw new Error(e);
        }

    }

    /**
     * @see org.apache.flume.conf.Configurable#configure(org.apache.flume.Context)
     */
    @Override
    public void configure(final Context context) {
        String tag = context.getString("tag");
        tagProp = tag;
        String accessToken =
                context.getString("accessToken",
                        "2097810379.1fb234f.9d58a854b30e4e0ba82f421ece38f316");
        accessTokenProp = accessToken;

    }

}
