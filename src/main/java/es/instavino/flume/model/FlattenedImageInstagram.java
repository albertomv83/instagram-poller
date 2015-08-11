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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public String getUserBio() {
		return userBio;
	}

	public void setUserBio(String userBio) {
		this.userBio = userBio;
	}

	public String getUserProfilePictureURL() {
		return userProfilePictureURL;
	}

	public void setUserProfilePictureURL(String userProfilePictureURL) {
		this.userProfilePictureURL = userProfilePictureURL;
	}

	public String getUserWebsiteURL() {
		return userWebsiteURL;
	}

	public void setUserWebsiteURL(String userWebsiteURL) {
		this.userWebsiteURL = userWebsiteURL;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getImageBASE64() {
		return imageBASE64;
	}

	public void setImageBASE64(String imageBASE64) {
		this.imageBASE64 = imageBASE64;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getLocationID() {
		return locationID;
	}

	public void setLocationID(String locationID) {
		this.locationID = locationID;
	}

	public String getLocationLat() {
		return locationLat;
	}

	public void setLocationLat(String locationLat) {
		this.locationLat = locationLat;
	}

	public String getLocationLon() {
		return locationLon;
	}

	public void setLocationLon(String locationLon) {
		this.locationLon = locationLon;
	}

	public String getCaptionText() {
		return captionText;
	}

	public void setCaptionText(String captionText) {
		this.captionText = captionText;
	}

	public String getCaptionID() {
		return captionID;
	}

	public void setCaptionID(String captionID) {
		this.captionID = captionID;
	}

	public String getCaptionCreatedTime() {
		return captionCreatedTime;
	}

	public void setCaptionCreatedTime(String captionCreatedTime) {
		this.captionCreatedTime = captionCreatedTime;
	}

	public String getCaptionFromID() {
		return captionFromID;
	}

	public void setCaptionFromID(String captionFromID) {
		this.captionFromID = captionFromID;
	}

	public String getCaptionFromUsername() {
		return captionFromUsername;
	}

	public void setCaptionFromUsername(String captionFromUsername) {
		this.captionFromUsername = captionFromUsername;
	}

	public int getLikesCount() {
		return likesCount;
	}

	public void setLikesCount(int likesCount) {
		this.likesCount = likesCount;
	}

	public List<Like> getLikes() {
		return likes;
	}

	public void setLikes(List<Like> likes) {
		this.likes = likes;
	}

	public int getCommentsCount() {
		return commentsCount;
	}

	public void setCommentsCount(int commentsCount) {
		this.commentsCount = commentsCount;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
    
    

}
