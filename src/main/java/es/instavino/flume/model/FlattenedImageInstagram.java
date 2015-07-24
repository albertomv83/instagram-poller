/**
 * $Id$
 * @author amancheno
 * @date   24/7/2015 14:04:16
 *
 * Copyright (C) 2015 Scytl Secure Electronic Voting SA
 *
 * All rights reserved.
 *
 */
package es.instavino.flume.model;

import java.util.List;

/**
 *
 */
public class FlattenedImageInstagram {

    String id;

    String link;

    String userName;

    String userFullName;

    String userBio;

    String userProfilePictureURL;

    String userWebsiteURL;

    String userID;

    String imageBASE64;

    String filter;

    int width;

    int height;

    List<String> tags;

    String createdTime;

    String locationName;

    String locationID;

    String locationLat;

    String locationLon;

    String captionText;

    String captionID;

    String captionCreatedTime;

    String captionFromID;

    String captionFromUsername;

    int likesCount;

    List<Like> likes;

    int commentsCount;

    List<Comment> comments;

}
