/**
 * $Id$
 * @author amancheno
 * @date   24/7/2015 14:28:11
 *
 * Copyright (C) 2015 Scytl Secure Electronic Voting SA
 *
 * All rights reserved.
 *
 */
package es.instavino.flume.model;

/**
 *
 */
public class Comment {

    String ID;

    String text;

    String createdTime;

    String fromUsername;

    String fromFullName;

    String fromID;

    String fromProfilePicture;

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getFromUsername() {
		return fromUsername;
	}

	public void setFromUsername(String fromUsername) {
		this.fromUsername = fromUsername;
	}

	public String getFromFullName() {
		return fromFullName;
	}

	public void setFromFullName(String fromFullName) {
		this.fromFullName = fromFullName;
	}

	public String getFromID() {
		return fromID;
	}

	public void setFromID(String fromID) {
		this.fromID = fromID;
	}

	public String getFromProfilePicture() {
		return fromProfilePicture;
	}

	public void setFromProfilePicture(String fromProfilePicture) {
		this.fromProfilePicture = fromProfilePicture;
	}

    
}
