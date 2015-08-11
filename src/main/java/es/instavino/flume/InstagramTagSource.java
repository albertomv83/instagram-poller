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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

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
import org.jinstagram.entity.comments.CommentData;
import org.jinstagram.entity.common.User;
import org.jinstagram.entity.users.feed.MediaFeedData;
import org.jinstagram.exceptions.InstagramException;

import es.instavino.flume.model.Comment;
import es.instavino.flume.model.FlattenedImageInstagram;
import es.instavino.flume.model.Like;

/**
 *
 */
public class InstagramTagSource extends AbstractSource implements Configurable, PollableSource {

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

			});
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
	private Event createEvent(final MediaFeedData d) throws JsonGenerationException, JsonMappingException, IOException {
		JSONEvent e = new JSONEvent();
		FlattenedImageInstagram json = flattenMediaFeedData(d);
		e.setBody(objectMapper.writeValueAsBytes(json));
		return e;
	}

	/**
	 * @param d
	 * @return
	 */
	private FlattenedImageInstagram flattenMediaFeedData(final MediaFeedData d) {
		FlattenedImageInstagram f = new FlattenedImageInstagram();

		// caption
		if (d.getCaption() != null) {
			f.setCaptionText(d.getCaption().getText());
			f.setCaptionCreatedTime(d.getCaption().getCreatedTime());
			f.setCaptionFromID(Long.toString(d.getCaption().getFrom().getId()));
			f.setCaptionFromUsername(d.getCaption().getFrom().getUsername());
			f.setCaptionID(Long.toString(d.getCaption().getId()));
		}

		// comments
		List<Comment> comments = new ArrayList<Comment>();
		for (CommentData commentData : d.getComments().getComments()) {
			Comment c = new Comment();
			c.setCreatedTime(commentData.getCreatedTime());
			c.setID(Long.toString(commentData.getId()));
			c.setText(commentData.getText());
			c.setFromFullName(commentData.getCommentFrom().getFullName());
			c.setFromID(Long.toString(commentData.getCommentFrom().getId()));
			c.setFromUsername(commentData.getCommentFrom().getUsername());
			c.setFromProfilePicture(commentData.getCommentFrom().getProfilePicture());
			comments.add(c);
		}
		f.setComments(comments);
		f.setCommentsCount(d.getComments().getCount());

		// image & filter
		f.setFilter(d.getImageFilter());
		if (d.getImages() != null && d.getImages().getStandardResolution() != null) {
			f.setHeight(d.getImages().getStandardResolution().getImageHeight());
			f.setWidth(d.getImages().getStandardResolution().getImageWidth());
			try {
				URL url = new URL(d.getImages().getStandardResolution().getImageUrl());
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				InputStream is = null;
				try {
					is = url.openStream();
					byte[] byteChunk = new byte[4096];
					int n;
					while ((n = is.read(byteChunk)) > 0) {
						baos.write(byteChunk, 0, n);
					}
				} catch (IOException e) {
					System.err.printf("Failed while reading bytes from %s: %s", url.toExternalForm(), e.getMessage());
				} finally {
					if (is != null) {
						is.close();
					}
				}
				f.setImageBASE64(new String(Base64.getEncoder().encode(baos.toByteArray())));
			} catch (IOException e) {
				// image empty
			}
		}

		// location
		if (d.getLocation() != null) {
			f.setLocationID(Long.toString(d.getLocation().getId()));
			f.setLocationLat(Double.toString(d.getLocation().getLatitude()));
			f.setLocationLon(Double.toString(d.getLocation().getLongitude()));
			f.setLocationName(d.getLocation().getName());
		}
		// user
		if (d.getUser() != null) {
			f.setUserBio(d.getUser().getBio());
			f.setUserFullName(d.getUser().getFullName());
			f.setUserID(Long.toString(d.getUser().getId()));
			f.setUserName(d.getUser().getUserName());
			f.setUserProfilePictureURL(d.getUser().getProfilePictureUrl());
			f.setUserWebsiteURL(d.getUser().getWebsiteUrl());
		}

		// tags
		f.setTags(d.getTags());

		// likes
		f.setLikesCount(d.getLikes().getCount());
		List<Like> likes = new ArrayList<Like>();
		for (User user : d.getLikes().getLikesUserList()) {
			Like like = new Like();
			like.setFullName(user.getFullName());
			like.setID(Long.toString(user.getId()));
			like.setProfilePicture(user.getProfilePictureUrl());
			like.setUsername(user.getUserName());
		}
		f.setLikes(likes);

		// misc
		f.setCreatedTime(d.getCreatedTime());
		f.setId(d.getId());
		f.setLink(d.getLink());

		return f;
	}

	@Override
	public void start() {
		// Initialize the connection to Instagram
		Token token = new Token(accessTokenProp, null);
		instagram = new Instagram(token);
		try {
			String id = instagram.getRecentMediaTags(tagProp).getPagination().getMinTagId();
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
		String accessToken = context.getString("accessToken", "2097810379.1fb234f.9d58a854b30e4e0ba82f421ece38f316");
		accessTokenProp = accessToken;

	}

}
